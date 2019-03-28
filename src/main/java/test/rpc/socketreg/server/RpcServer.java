package test.rpc.socketreg.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import test.rpc.socketreg.server.anno.RpcAnnotation;
import test.rpc.socketreg.server.zk.IRegistCenter;

/**
 * 将RPC发布成对外服务
 * @author zxm
 *
 */
public class RpcServer {
	//用来处理客户端的请求
	private static ExecutorService executorService =
			Executors.newCachedThreadPool();
	
	private IRegistCenter center ;
	private String addr;
	
	//保存服务名称和服务的对应关系 以便知道通过名字 调用指定的服务  还可以有版本号允许调用 同一个服务的指定版本
	private Map serviceMap=new  HashMap();
	
	public RpcServer() {
	
	}
	
	public RpcServer(IRegistCenter center, String addr) {
		this.center=center;
		this.addr=addr;
	}
	
	/**
	 * 绑定服务到当前server
	 */
	public void bind(Object... services){
		for(Object service:services){
			RpcAnnotation anno= service.getClass().getAnnotation(RpcAnnotation.class);
			String serviceName =anno.value().getName();
			String version=anno.version();
			if(null!=version && !"".equals(version )){
				serviceName +="-"+version;
			}
			serviceMap.put(serviceName, service);
		}
		
	}

	public void publish(){
		
		ServerSocket serverSocket = null;
		try {
			int port = Integer.parseInt(addr.split(":")[1]);
			serverSocket =  new ServerSocket(port);
			//发布后 再注册中心注册服务
			for(Object ser:serviceMap.keySet()){
				String serName=(String)ser;
				center.regist(serName, addr);
			}
			while(true){
				Socket socket = serverSocket.accept();
				executorService.submit(new ProcessHandler(socket,serviceMap));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
