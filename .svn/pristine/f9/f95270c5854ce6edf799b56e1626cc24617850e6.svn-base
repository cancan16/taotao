package com.taotao.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @ClassName RedisService
 * @Author volc
 * @Description 封住用于redis缓存的数据操作，通用方法
 * @Date 2017年2月27日 下午8:42:36
 */
@Service
public class RedisService {

    // 保证common的通用性，如果spring容器中没有ShardedJedisPool bean就不使用redis如果有就使用
    @Autowired(required = false)
    private ShardedJedisPool shardedJedisPool;

    /**
     * @MethodName execute 
     * @Author volc
     * @Description 泛型方法用于处理存入缓存和从缓存中取数据
     * @Date 2017年2月27日 下午9:04:59
     */
    private <T> T execute(Function<T, ShardedJedis> fun) {
	ShardedJedis shardedJedis = null;
	try {
	    // 从连接池中获取到jedis分片对象
	    shardedJedis = shardedJedisPool.getResource();
	    // 数据放入到redis中
	    return fun.callback(shardedJedis); // insert成功返回OK
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (null != shardedJedis) {
		// 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
		shardedJedis.close();
	    }
	}
	return null;
    }

    /**
     * @MethodName set 
     * @Author volc
     * @Description 缓存数据到redis
     * @Date 2017年2月27日 下午8:45:51
     */
    public String set(final String key, final String value) {
	return this.execute(new Function<String, ShardedJedis>() { // 匿名实现类存在作用域，不能使用非final参数
	    @Override
	    public String callback(ShardedJedis e) {
		return e.set(key, value); // 添加成功返回OK
	    }
	});
	/*
	 * ShardedJedis shardedJedis = null; try { // 从连接池中获取到jedis分片对象
	 * shardedJedis = shardedJedisPool.getResource(); // 数据放入到redis中 return
	 * shardedJedis.set(key, value); // insert成功返回OK } catch (Exception e) {
	 * e.printStackTrace(); } finally { if (null != shardedJedis) { //
	 * 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态 shardedJedis.close(); } } return null;
	 */
    }

    /**
     * @MethodName get 
     * @Author volc
     * @Description 从redis缓存中取数据
     * @Date 2017年2月27日 下午8:48:47
     */
    public String get(final String key) {
	return this.execute(new Function<String, ShardedJedis>() {
	    @Override
	    public String callback(ShardedJedis e) {
		return e.get(key); // 返回key对应的value值
	    }
	});
	/*
	 * ShardedJedis shardedJedis = null; try { // 从连接池中获取到jedis分片对象
	 * shardedJedis = shardedJedisPool.getResource(); // 从redis中取数据 return
	 * shardedJedis.get(key); // 返回key对应的value值 } catch (Exception e) {
	 * e.printStackTrace(); } finally { if (null != shardedJedis) { //
	 * 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态 shardedJedis.close(); } } return null;
	 */
    }

    /**
     * @MethodName del
     * @Author volc
     * @Description 根据key删除缓存
     * @Date 2017年2月27日 下午9:21:09
     */
    public Long del(final String key) {
	return this.execute(new Function<Long, ShardedJedis>() {
	    @Override
	    public Long callback(ShardedJedis e) {
		return e.del(key); // 根据key删除redis缓存
	    }
	});
    }

    /**
     * @MethodName expire
     * @Author volc
     * @Description 设置缓存的生存时间，到期会从缓存中移除
     * @Date 2017年2月27日 下午9:24:18
     */
    public Long expire(final String key, final Integer seconds) {
	return this.execute(new Function<Long, ShardedJedis>() {
	    @Override
	    public Long callback(ShardedJedis e) {
		return e.expire(key, seconds);
	    }
	});
    }

    /**
     * @MethodName set
     * @Author volc
     * @Description 设置String类型的值，存入缓存时设置缓存数据的生存时间
     * @Date 2017年2月27日 下午9:26:10
     */
    public String set(final String key, final String value, final Integer seconds) {
	return this.execute(new Function<String, ShardedJedis>() {
	    @Override
	    public String callback(ShardedJedis e) {
		String result = e.set(key, value);
		// 设置这个缓存数据的生存时间
		e.expire(key, seconds);
		return result;
	    }
	});
    }
}
