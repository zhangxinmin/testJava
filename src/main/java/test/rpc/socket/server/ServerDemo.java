package test.rpc.socket.server;

public class ServerDemo {
	public static void main(String[] args) {
		ISayHello service = new SayHelloImpl();
		RpcServer server = new RpcServer();
		server.publish(service, 8888);
	}
}
