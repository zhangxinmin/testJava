package test.rpc.socketreg.client.zk;

import java.util.List;

public interface ILoadBalance {
	
	public String select(List<String> repos);

}
