package test.rpc.socketreg.client;

import java.lang.reflect.Proxy;

import test.rpc.socketreg.server.ISayHello;
import test.rpc.socketreg.client.zk.IDiscovery;
import test.rpc.socketreg.client.zk.ZKDidscoveryImpl;
import test.rpc.socketreg.server.zk.ZKConfig;

public class ClientDemo {
    public static void main(String[] args) {
		RpcClientProxy clientProxy = new RpcClientProxy();
		IDiscovery discovery= new ZKDidscoveryImpl(ZKConfig.CONN_STR);
		ISayHello service = clientProxy.newProxy(ISayHello.class,null,discovery);
		System.out.println(service.sayHello("张222三"));
	}
}
