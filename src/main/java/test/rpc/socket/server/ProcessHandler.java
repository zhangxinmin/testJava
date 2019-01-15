package test.rpc.socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;


public class ProcessHandler implements Runnable {
	private Socket socket;
	private Object service;

	public ProcessHandler(Socket socket2, Object service2) {
		this.socket=socket2;
		this.service=service2;
	}

	@Override
	public void run() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			//获取要调用的服务 和方法 参数
			RpcRequest request= (RpcRequest)ois.readObject();
			Object res=invoke(request);
			ObjectOutputStream oos= new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(res);
			oos.flush();
			oos.close();
				
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(ois!=null ){
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private Object invoke( RpcRequest request) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String className=request.getClassName();
		String  mName = request.getMethodName();
		Object[] args = request.getArgs();
		Class[] argTypes=new Class[args.length];
		for(int i=0;i<args.length;i++){
			argTypes[i]=args[i].getClass();
		}
		
		Method m = Class.forName(className).getMethod(mName, argTypes);
		return m.invoke(service, args);
	}

}
