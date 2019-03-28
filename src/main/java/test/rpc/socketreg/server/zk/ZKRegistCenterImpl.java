package test.rpc.socketreg.server.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class ZKRegistCenterImpl implements IRegistCenter{
	CuratorFramework cf = null;
	
	{
		cf = CuratorFrameworkFactory.builder().connectString(ZKConfig.CONN_STR)
				.sessionTimeoutMs(4000)
				.retryPolicy(new ExponentialBackoffRetry(3000, 2)).build();
		
		cf.start();
	}
	

	@Override
	public void regist(String name, String addr) {
		//如果节点不存在就穿件节点
		String servicePath = ZKConfig.ZK_REGISTER_PATH+"/"+name;
		try {
			if(null==cf.checkExists().forPath(servicePath)){
				cf.create().creatingParentContainersIfNeeded()
				.withMode(CreateMode.PERSISTENT)
				.forPath(servicePath,"0".getBytes());
			}
			//创建地址节点
			String addrPath=servicePath+"/"+addr;
			String rsNode = cf.create()
					.withMode(CreateMode.EPHEMERAL)
					.forPath(addrPath,"1".getBytes());
			System.out.println("成功注册服务 ："+name+"。地址："+addr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}
