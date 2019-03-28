package test.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class BaseAPI {
	public static void main(String[] args) throws Exception {
		final ZooKeeper zk = getZk();
		System.out.println(zk.getState());
		//zk.delete("/mytest", stat.getVersion());
		//添加节点
		System.out.println(
				zk.create("/mytest", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, 
						CreateMode.PERSISTENT));//临时节点    返回节点路径  /mytest
		//
		Stat stat= new Stat();
		byte[] bytes = zk.getData("/mytest", null, stat);
		System.out.println(new String(bytes));
		
		stat = zk.setData("/mytest", "ttt".getBytes(), stat.getVersion());
		System.out.println(stat);
		
		zk.exists("/mytest", new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				System.out.println("=====>"+event.getPath() +"=="+event.getType() +event);
				try {
					zk.exists(event.getPath(), true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		System.out.println("path:"+zk.create("/mytest/test1", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, 
				CreateMode.PERSISTENT));
		
		
		//zk.delete("/mytest/test1");//版本不一致就会报错
		System.in.read();
		zk.delete("/mytest", stat.getVersion());//版本不一致就会报错
		
		
		
		zk.close();
	}
	//获取连接
	public static ZooKeeper getZk() throws Exception{
		final CountDownLatch countDownLatch=new CountDownLatch(1);
		ZooKeeper zk= new ZooKeeper("192.168.68.137:2181,192.168.68.135:2181,192.168.68.136:2181",
				4000,	new Watcher(){
					@Override
					public void process(WatchedEvent event) {
						System.out.println("连接成功");
						countDownLatch.countDown();
					}
			
		});
		countDownLatch.await();
		return zk;
	} 

}
