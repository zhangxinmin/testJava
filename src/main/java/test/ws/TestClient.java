package test.ws;

import test.ws.client.TestWSService;

public class TestClient {
public static void main(String[] args) {
	TestWSService ser = new TestWSService();
	test.ws.client.TestWS soap = ser.getTestWSPort();

    String str= soap.hello("dfasdfa");

    System.out.println(str);
}
}
