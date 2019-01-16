package test.rpc.socketreg.server;

import test.rpc.socketreg.server.anno.RpcAnnotation;

@RpcAnnotation(value=ISayHello.class)
public class SayHelloImpl2 implements ISayHello {

	@Override
	public String sayHello(String name) {
		return "hello,v2 "+name;
	}

}
