package test.rpc.socketreg.server;

import test.rpc.socketreg.server.anno.RpcAnnotation;

@RpcAnnotation(value=ISayHello.class)
public class SayHelloImpl implements ISayHello {

	@Override
	public String sayHello(String name) {
		return "hello,"+name;
	}

}
