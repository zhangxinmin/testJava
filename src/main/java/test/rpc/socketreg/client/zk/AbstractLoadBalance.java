package test.rpc.socketreg.client.zk;

import java.util.List;


public abstract class AbstractLoadBalance implements ILoadBalance{

	@Override
	public String select(List<String> repos) {
		if(null== repos||repos.size()==0){
			return null;
		}
		if(repos.size()==1){
			return repos.get(0);
		}
		return doSelect(repos);
	}
    
	protected abstract String doSelect(List<String> repos);

}
