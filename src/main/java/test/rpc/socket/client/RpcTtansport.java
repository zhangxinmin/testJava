package test.rpc.socket.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import test.rpc.socket.server.RpcRequest;

public class RpcTtansport {
	private String host;
	private int port ;

	public RpcTtansport(String host, int port) {
		this.host=host;
		this.port=port;
	}
	public Object send( RpcRequest req){
		Socket socket = newSocket();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(req);
			oos.flush();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Object res=ois.readObject();
			return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
		
		return null;
		
	}
	private Socket newSocket() {
		Socket socket =null;
		try {
			socket = new Socket(host, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return socket;
	}

}
