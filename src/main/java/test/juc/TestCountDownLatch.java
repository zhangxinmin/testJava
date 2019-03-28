package test.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * countlatch 计数弹簧锁 
 * 适用场景  如果一个线程需要阻塞 到 其他N个线程执行完成指定的任务再执行  则适用 countdownlatch 来实现
 * 需要阻塞的线程 调用 countdownlatch（N） 定义需要N个任务 执行完后 才能执行
 * 然后启动 其他N个线程    然后立马调用 CountDownLatch.await()阻塞当前线程  
 * 然后 每个线程的 任务执行完成后  调用 countdown 直到N个线程都调用了该方法  主线程会被唤醒 继续执行
 * 实例如下   这里模拟 启动一个服务程序  启动前需要做两项检查 （每个检查分别用一个线程实现） 
 * 检查完成后才能启动程序
 * 
 * 这个锁可以同时锁定多个线程 。  就是多个线程里调用 await方法实现阻塞 然后 完成countdown后多个线程同时并发执行
 * 可以 模拟单例模式的并发访问
 * @author zxm
 *
 */
public class TestCountDownLatch {
	private static  CountDownLatch latch= new CountDownLatch(2);
	
	public static void main(String[] args) {
		System.out.println("开始启动程序。。。");
		System.out.println("开始执行启动前检查。。。");
		
		Executor executor = Executors.newFixedThreadPool(2);
		
		executor.execute(new Checker(latch, "网络检查"));
		executor.execute(new Checker(latch, "接口检查"));
		 try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("启动完成");
		
	}
	
	
}

class Checker implements Runnable{
	private CountDownLatch latch;
	private String name;
	
	public Checker(CountDownLatch latch ,String name){
		this.name= name ;
		this.latch=latch;
	}

	@Override
	public void run() {
		//执行检查
		check();
		//检查完计数器-1
		latch.countDown();
	}
	
	private void check(){
		try {
			System.out.println("执行检查"+name+".....");
			Thread.sleep(3000);
			System.out.println("完成检查"+name);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}


