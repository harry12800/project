package cn.harry12800.scan.redis;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import cn.harry12800.api.SpringUtil;
import cn.harry12800.api.redis.RedisProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public final class SentinelJedisUtil {
	private static final int MAX_DB_COUNT = 16;
	private static final List<JedisSentinelPool> POOL = Collections.synchronizedList(Arrays.asList(new JedisSentinelPool[MAX_DB_COUNT]));

	public static JedisSentinelPool getSentinelJedisPool() {
		int db_index = 0;
		return SentinelJedisUtil.getSentinelJedisPool(db_index);
	}

	public static synchronized JedisSentinelPool getSentinelJedisPool(int db_index) {
		if (POOL.get(db_index) == null) {
			RedisProperties props = SpringUtil.getBean(RedisProperties.class);
			RedisProperties.Pool pool = Objects.isNull(props.getPool()) ? new RedisProperties.Pool() : props.getPool();
			JedisPoolConfig pool_config = new JedisPoolConfig();
			pool_config.setMaxTotal(pool.getMaxActive());
			pool_config.setMaxIdle(pool.getMaxIdle());
			pool_config.setMinIdle(pool.getMinIdle());
			pool_config.setMaxWaitMillis(pool.getMaxWait());

			// String host = props.getHost();
			// int port = props.getPort();
			int timeout = props.getTimeout();
			String password = StringUtils.trimToNull(props.getPassword());
			int db_max = MAX_DB_COUNT - 1;
			int db = (db_index < 0) ? 0 : ((db_index > db_max) ? db_max : db_index);
			String client_name = null;
			String master = props.getSentinel().getMaster();
			String nodes = props.getSentinel().getNodes();
			String[] node_arr = StringUtils.split(nodes, ",");
			Set<String> sentinels = new TreeSet<>(Arrays.asList(node_arr));
			System.err.println("--------------------" + password);
			System.err.println("--------------------" + master);
			JedisSentinelPool jedis_pool = new JedisSentinelPool(master, sentinels, pool_config, timeout, password, db, client_name);
			POOL.set(db_index, jedis_pool);
		}

		return POOL.get(db_index);
	}

	public static Jedis getResource() {
		return SentinelJedisUtil.getSentinelJedisPool().getResource();
	}

	public static Jedis getResource(int db_index) {
		return SentinelJedisUtil.getSentinelJedisPool(db_index).getResource();
	}

}
