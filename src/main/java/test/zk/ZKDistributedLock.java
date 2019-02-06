package test.zk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
/**这里模拟实现分布式锁
 * 主要逻辑
 * 1、利用临时 序列化节点 当多个线程同时访问 一个 共享资源时  在该 节点下 创建各自的临时节点  （连接断开后临时节点会自动删除）
 * 2、获取到节点下所有的序列节点  取最小的  如果和当前锁节点 相同 则 获得锁   否则  阻塞等待比自己小的节点删除   删出后会出发监听程序 终止当前线程阻塞  从而获得所锁
 * 3、获得锁的线程 执行完逻辑后 删除节点并关闭和zk 的连接  将锁交给 下一个序列节点的线程
 * 
 * 最后变附带有测试城程序
 * @author zxm
 *
 */

public class ZKDistributedLock implements Lock,Watcher {
	private String curentLock;  //锁节点
	private String preLock; // 前一个节点
	private ZooKeeper zk =null;
	private final String lockRootPath="/zklocks";//锁数据存放的根路径
	private String lockPath=null;  //当前资源锁名称  构造锁是传入  和  lockRootPath 一起构成锁的路径  加这个路径为了实现 不同的锁再不同的路径下
	private CountDownLatch latch =null;//获取所失败时用于阻塞 并在前一个锁节点 删除时  激活
	
	public ZKDistributedLock(String name){
		try {
			//保存锁的名字
			this.lockPath = name;
			//创建zk连接
			zk = new ZooKeeper("192.168.68.137:2181,192.168.68.135:2181,192.168.68.136:2181", 3000,	 this);
			if(zk.exists(lockRootPath,false)==null){
				zk.create(lockRootPath,"1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				zk.create(lockRootPath+"/"+lockPath, "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}else if(zk.exists(lockRootPath+"/"+lockPath,false)==null){
				zk.create(lockRootPath+"/"+lockPath, "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	@Override
	public void process(WatchedEvent event) {
		System.out.println("监听被执行"+event.getPath()+"   "+event.getType()+"   "+event.getState() +"===latch"+latch);
		
		if(EventType.NodeDeleted ==event.getType() &&latch!=null){
			System.out.println("释放锁");
			latch.countDown();
		}
	}

	@Override
	public void lock() {
		if(tryLock()){
			System.out.println("线程 "+Thread.currentThread()+"获得锁"+curentLock);
			return;
		}
		try {
			waitLock();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean waitLock() throws Exception{
		Stat stat=zk.exists(preLock,true);
		if(null!=stat){
			System.out.println("线程 "+Thread.currentThread()+"等待锁 并阻塞"  +"prelock:"+preLock);
			latch=new CountDownLatch(1);
			latch.await();
			System.out.println("线程 "+Thread.currentThread()+"阻塞打开"+latch);
			
		}
		return true;
	}

	@Override
	public boolean tryLock() {
		try {
			curentLock=zk.create(lockRootPath+"/"+lockPath+"/", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		    System.out.println("线程 "+Thread.currentThread()+"尝试竞争锁"+curentLock);
			List<String> children = zk.getChildren(lockRootPath+"/"+lockPath, false);
		    SortedSet<String> set=new TreeSet<String>();
			//SortedSet<String> set=null;
		    for(String child:children){
		    	//这里一定要 加上前边的路径   一起加到set中
		    	set.add(lockRootPath+"/"+lockPath+"/"+child);
		    }
		    if(curentLock.equals(set.first())){
		    	return true;
		    }
		    
		    SortedSet< String > less = ((TreeSet)set).headSet(curentLock);
		    preLock=less.last();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		System.out.println("线程释放锁"+Thread.currentThread()+"--"+curentLock);
		try {
			Stat stat = zk.exists(curentLock, false);
			zk.delete(curentLock, stat.getVersion());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				zk.close();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	

	public static void main(String[] args) {
		//测试 锁  启动三个线程同时去获得所
		List<LockThread> list =  new ArrayList<LockThread> ();
		list.add(new LockThread("testlock"));
		list.add(new LockThread("testlock"));
		list.add(new LockThread("testlock"));
		list.add(new LockThread("testlock"));
		list.add(new LockThread("testlock"));
		for(int i=0;i<list.size();i++){
			list.get(i).start();
		}

	}
}
class LockThread extends Thread{
	private String lockname=null;
	

	public LockThread(String lockname) {
		super();
		this.lockname = lockname;
	}

	@Override
	public void run() {
		Lock lock = new ZKDistributedLock(lockname);
		//加锁
		lock.lock();
		System.out.println(Thread.currentThread()+"线程逻辑执行开始");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread()+"线程逻辑执行完成");
		//释放锁
		lock.unlock();
		
	}
	
}
