package test.rpc.socketreg.server;

import test.rpc.socketreg.server.zk.IRegistCenter;
import test.rpc.socketreg.server.zk.ZKRegistCenterImpl;

public class ServerDemo {
	public static void main(String[] args) {
		ISayHello service = new SayHelloImpl();
		String addr="127.0.0.1:8080";
		IRegistCenter center = new ZKRegistCenterImpl();
		RpcServer server = new RpcServer(center,addr);
		server.bind(service);
		server.publish();
//		ISayHello service = new SayHelloImpl2();
//		String addr="127.0.0.1:8081";
//		IRegistCenter center = new ZKRegistCenterImpl();
//		RpcServer server = new RpcServer(center,addr);
//		server.bind(service);
//		server.publish();
	}
}
