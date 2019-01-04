package cn.harry12800.api.redis;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.harry12800.api.SpringUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(JedisUtil.class);

	private static final int MAX_DB_COUNT = 16;
	private static final List<JedisPool> cPoolList = Collections.synchronizedList(Arrays.asList(new JedisPool[MAX_DB_COUNT]));

	public static JedisPool getJedisPool() {
		int db_index = 0;
		return JedisUtil.getJedisPool(db_index);
	}

	public static synchronized JedisPool getJedisPool(int db_index) {
		if (cPoolList.get(db_index) == null) {
//			RedisProperties props = AppContext
//					.singleton()
//					.getBeanContext()
//					.getBean(RedisProperties.class);
			RedisProperties props = SpringUtil.getBean(RedisProperties.class);
			RedisProperties.Pool pool = Objects.isNull(props.getPool()) ? new RedisProperties.Pool() : props.getPool();
			JedisPoolConfig pool_config = new JedisPoolConfig();
			pool_config.setMaxTotal(pool.getMaxActive());
			pool_config.setMaxIdle(pool.getMaxIdle());
			pool_config.setMinIdle(pool.getMinIdle());
			pool_config.setMaxWaitMillis(pool.getMaxWait());

			String host = props.getHost();
			int port = props.getPort();
			int timeout = props.getTimeout();
			String password = StringUtils.trimToNull(props.getPassword());
			int max_index = MAX_DB_COUNT - 1;
			int db = (db_index < 0) ? 0 : ((db_index > max_index) ? max_index : db_index);
			String client_name = null;
			JedisPool jedis_pool = new JedisPool(pool_config, host, port, timeout, password, db, client_name);
			cPoolList.set(db_index, jedis_pool);
		}

		return cPoolList.get(db_index);
	}

	public static Jedis getResource() {
		return JedisUtil.getJedisPool().getResource();
	}

	public static Jedis getResource(int db_index) {
		return JedisUtil.getJedisPool(db_index).getResource();
	}
	/**
	 * 回收Jedis对象资源
	 * 
	 * @param jedis
	 */
	public synchronized static void returnResource(Jedis jedis) {
		if (jedis != null) {
			JedisUtil.returnResource(jedis);
		}
	}
 
	/**
	 * Jedis对象出异常的时候，回收Jedis对象资源
	 * 
	 * @param jedis
	 */
	public synchronized static void returnBrokenResource(Jedis jedis) {
		if (jedis != null) {
			JedisUtil.returnBrokenResource(jedis);
		}
 
	}
 
}
