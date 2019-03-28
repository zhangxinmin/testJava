package test.rpc.socket.client;

import java.lang.reflect.Proxy;

import test.rpc.socket.server.ISayHello;

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
	public <T>T newProxy(Class<T> service, String host, int port) {
		return (T) Proxy.newProxyInstance(service.getClassLoader(),
				new Class[]{service}, new RemoteInvocationHandler(host,port));
	}

}
