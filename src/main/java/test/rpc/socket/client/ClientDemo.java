package test.rpc.socket.client;

import java.lang.reflect.Proxy;

import test.rpc.socket.server.ISayHello;

public class ClientDemo {
    public static void main(String[] args) {
		RpcClientProxy clientProxy = new RpcClientProxy();
		ISayHello service = clientProxy.newProxy(ISayHello.class,"127.0.0.1",8888);
		System.out.println(service.sayHello("张222三"));
	}
}
