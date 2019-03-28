package test.rpc.socket.server;

import java.io.Serializable;

/**
 * RPC传输对象
 * @author zxm
 *
 */
public class RpcRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	private String className;
	private String methodName;
	private Object[] args;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
