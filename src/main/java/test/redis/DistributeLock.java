package test.redis;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class DistributeLock {
	public static String getLock(String key,int timeout) {
		try {
			Jedis jedis =RedisManager.getJedis();
			String value=UUID.randomUUID().toString();
			long curTime = new Date().getTime();
			while((new Date().getTime()-curTime)<timeout){
				Long res = jedis.setnx(key, value);
				if(res==1){//设置成功则返回 value
					jedis.expire(key,timeout);//为锁设置超时时间
					return value;
				}
				//容错代码 如果 某个现成获取了 设置了 key值还没有设置过期时间 就宕机了 这里补充设置
				if(jedis.ttl(key)==-1)
					jedis.expire(key,timeout);
				
				Thread.sleep(500);//阻塞现成 每 500ms尝试一次
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 释放锁
	 * @param key
	 * @param value
	 * @throws Exception 
	 */
	public boolean releaseLock(String key,String value) {
		Jedis jedis;
		try {
			jedis = RedisManager.getJedis();
			while(true){
				jedis.watch(key);//可以监视一个key 如果这个key 被修改了 那么后边的事务代码不会执行
				String val=jedis.get(key);
				if(value.equals(val)){//值没有变 则删除key
					Transaction trans = jedis.multi();
					jedis.del(key);
					List<Object> res=trans.exec();
					if(res==null){
						continue;//释放失败重新释放
					}
					return true;
				}
				jedis.unwatch();
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String lockId = DistributeLock.getLock("dao", 1000);
		if(null!=lockId)
			System.out.println("获取锁成功");
		
		String lockId2 = DistributeLock.getLock("dao", 1000);
		if(null!=lockId2)
			System.out.println("获取锁成功");
		else
			System.out.println("获取锁失败");

	}

}
