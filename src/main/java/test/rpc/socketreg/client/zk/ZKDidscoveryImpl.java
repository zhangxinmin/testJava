package test.rpc.socketreg.client.zk;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException.NoNodeException;

import test.rpc.socketreg.server.zk.ZKConfig;

public class ZKDidscoveryImpl implements IDiscovery {
	private String connStr=null;
	private CuratorFramework cf = null;
	//用于缓存 服务的地址列表信息
	List<String> repos=new ArrayList<>();
	
	
	public ZKDidscoveryImpl(String connStr) {
		this.connStr=connStr;
		cf = CuratorFrameworkFactory.builder().connectString(connStr)
				.sessionTimeoutMs(4000)
				.retryPolicy(new ExponentialBackoffRetry(3000, 2)).build();
		
		cf.start();
	}

	@Override
	public String discovery(String serviceName) {
		try {
			repos = cf.getChildren().forPath(ZKConfig.ZK_REGISTER_PATH+"/"+serviceName);
			//如果找不到子节点会报错  NoNodeException
			//动态发现服务节点的变化
	        registerWatcher(ZKConfig.ZK_REGISTER_PATH+"/"+serviceName);
			
			ILoadBalance balance = new RandomLoadBalance();
			return balance.select(repos);
		} catch (NoNodeException e) {
			throw new RuntimeException("没有发现可用的服务"+e);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		return null;
	}

	private void registerWatcher(final String string) {
		 PathChildrenCache cache= new PathChildrenCache(cf, string, true);
		 PathChildrenCacheListener listener= new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
					throws Exception {
				repos=cf.getChildren().forPath(string);
			}
		};
		cache.getListenable().addListener(listener);
		try {
			cache.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("注册 服务列表监听失败"+e);
		}
		
	}

}
