package test.rpc.socketreg.client;

import java.lang.reflect.Proxy;

import test.rpc.socketreg.server.ISayHello;
import test.rpc.socketreg.client.zk.IDiscovery;

public class RpcClientProxy {

	public RpcClientProxy() {
		// TODO Auto-generated constructor stub
	}
    /**
     * 创建 代理客户端
     * @param class1
     * @param string
     * @param i
     * @return
     */
	public <T>T newProxy(Class<T> service,String version,IDiscovery discovery) {
		return (T) Proxy.newProxyInstance(service.getClassLoader(),
				new Class[]{service}, new RemoteInvocationHandler(  discovery,version));
	}

}
