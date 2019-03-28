package test.rpc.socketreg.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import test.rpc.socketreg.server.RpcRequest;
import test.rpc.socketreg.client.zk.IDiscovery;

public class RemoteInvocationHandler implements InvocationHandler {
//	private String host;
//	private int port;
	private IDiscovery discovery;
	private String version ;

	public RemoteInvocationHandler(IDiscovery discovery, String version) {
		this.discovery = discovery;
		this.version = version;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		//代理访问方法实际就是向服务端发送 请求   请求执行服务端的方法
		//构建要发送的对象 
		RpcRequest req = new RpcRequest();
		req.setClassName(method.getDeclaringClass().getName());
		req.setMethodName(method.getName());
		req.setVersion(version);
		req.setArgs(args);
		
		//封装好 请求对象后 需要 发现 服务的注册地址 
		String serviceName = req.getClassName();
		if(null!=version&&version.length()>0){
			serviceName+="-"+version;
		}
		String addr=discovery.discovery(serviceName);
		String [] addrs=addr.split(":");
		//封装好后 传输给 服务端 
		RpcTtansport trans=new RpcTtansport(addrs[0],Integer.parseInt(addrs[1]));
		return trans.send(req);
	}

}
