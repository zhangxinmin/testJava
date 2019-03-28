package test.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;

public class TestLUA {
	public static String lua="local num = redis.call('incr',KEYS[1]) "+
							"if (tonumber(num)==1) then "+
							"    redis.call('expire',KEYS[1],ARGV[1]) "+
							"    return 1 "+
							"elseif (tonumber(num)>tonumber(ARGV[2])) then "+
							"    return 0 "+
							"else "+
							"    return 1 "+ 
							"end";
	public static String sha =null;
	public static String loadScript() throws Exception{
		//
		Jedis jedis=RedisManager.getJedis();
		//将脚本缓存到服务端并返回摘要信息
		String sha = jedis.scriptLoad(lua);
		System.out.println("sha===>"+sha);
		return sha;
	}
	
	public static Object testLua1() throws Exception{
		if(null==sha){
			System.out.println("加载脚本");
			sha=loadScript();
		}
		
		Jedis jedis=RedisManager.getJedis();
		//String sha = jedis.scriptLoad(lua);
		List<String> keys = new ArrayList<String>();
		keys.add("ip:limit:127.0.0.1");
		
		List<String> args = new ArrayList<String>();
		args.add("30");
		args.add("10");
		//以摘要方式执行脚本
		return jedis.eval(sha, keys, args); 
		
	}
	
	public static Object testLua2() throws Exception{
		
		Jedis jedis=RedisManager.getJedis();
		List<String> keys = new ArrayList<String>();
		keys.add("ip:limit:127.0.0.1");
		
		List<String> args = new ArrayList<String>();
		args.add("30");
		args.add("10");
		
		return jedis.eval(lua, keys, args); //这样每次都传递脚本到服务端增加网络消耗
		
	}
	
	public static void main(String[] args) throws Exception {
		for(int i=0;i<13;i++){
			System.out.println(testLua1());
			//System.out.println(testLua2());

		}
		
	}

}
