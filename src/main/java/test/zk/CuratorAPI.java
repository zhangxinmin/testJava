package test.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

public class CuratorAPI {
	public static void main(String[] args) throws Exception {
		CuratorFramework cf = CuratorFrameworkFactory
				.builder().connectString("192.168.68.137:2181")
				.sessionTimeoutMs(4000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.namespace("curator").build();
		cf.start();
	    Stat stat=new Stat();
	   byte[] res= cf.getData().storingStatIn(stat).forPath("/mytest");
	   System.out.println(new String(res));
	   cf.setData().withVersion(stat.getVersion()).forPath("/mytest", "123".getBytes());
	    
		cf.close(); 
	}

}
