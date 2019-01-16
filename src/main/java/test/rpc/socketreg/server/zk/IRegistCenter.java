package test.rpc.socketreg.server.zk;

public interface IRegistCenter {
	/**
	 * 注册服务 根据名称和
	 * @param name 名称
	 * @param addr 地址
	 */
	public void regist(String name,String addr);

}
