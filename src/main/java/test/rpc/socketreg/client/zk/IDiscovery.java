package test.rpc.socketreg.client.zk;

/**
 * 
 * 定义发现服务的借口
 * @author zxm
 *
 */
public interface IDiscovery {
public String discovery(String serviceName);
}
