package test.ws;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class TestWS {
	public String hello(String name){
		return "hello,"+name;
	}

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8888/hello", new TestWS());
		System.out.println("server ready...");
	}
}
