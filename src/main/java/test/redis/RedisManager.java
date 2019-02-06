package test.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

public class RedisManager {
	private static JedisPool jedisPool;
	static{
		JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(20);
		jedisPoolConfig.setMaxIdle(10);
		jedisPool = new JedisPool(jedisPoolConfig,"192.168.159.10" ,6379);
		
	}
	public static Jedis getJedis() throws Exception {
		if(null!=jedisPool){
			return jedisPool.getResource();
		}
		throw new Exception("连接池未初始化");
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(getJedis());
	}

	
	
}
