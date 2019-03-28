package test.rpc.socket.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import test.rpc.socket.server.RpcRequest;

public class RemoteInvocationHandler implements InvocationHandler {
	private String host;
	private int port;

	public RemoteInvocationHandler(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		//代理访问方法实际就是向服务端发送 请求   请求执行服务端的方法
		//构建要发送的对象 
		RpcRequest req = new RpcRequest();
		req.setClassName(method.getDeclaringClass().getName());
		req.setMethodName(method.getName());
		req.setArgs(args);
		
		//封装好后 传输给 服务端 
		RpcTtansport trans=new RpcTtansport(host,port);
		
		return trans.send(req);
	}

}
