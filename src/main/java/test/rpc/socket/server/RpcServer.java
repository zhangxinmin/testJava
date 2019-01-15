package test.rpc.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 将RPC发布成对外服务
 * @author zxm
 *
 */
public class RpcServer {
	private static ExecutorService executorService =
			Executors.newCachedThreadPool();
	
	public RpcServer() {
	
	}
	
	public void publish(ISayHello service, int port){
		
		ServerSocket serverSocket = null;
		try {
			serverSocket =  new ServerSocket(port);
			while(true){
				Socket socket = serverSocket.accept();
				executorService.submit(new ProcessHandler(socket,service));
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
