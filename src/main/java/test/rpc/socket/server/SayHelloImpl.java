package test.rpc.socket.server;

public class SayHelloImpl implements ISayHello {

	@Override
	public String sayHello(String name) {
		return "hello,"+name;
	}

}
