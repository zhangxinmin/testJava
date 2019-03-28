package test.rpc.socketreg.client.zk;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance extends AbstractLoadBalance{

	@Override
	protected String doSelect(List<String> repos) {
		int i=new Random().nextInt(repos.size());
		return repos.get(i);
	}

}
