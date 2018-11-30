package cn.harry12800.scan.redis;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class SentinalJedisUtilDemo {
	private static final int MAX_DB_COUNT = 16;
	private static final List<JedisSentinelPool> POOL = Collections.synchronizedList(Arrays.asList(new JedisSentinelPool[MAX_DB_COUNT]));

	private static final String MASTER_NAME = "mymaster";
	private static final String PASSWORD = "Lenovo..";

	//哨兵地址
	private static final String SENTINAL_Addr1 = "10.3.9.122:7502";
	private static final String SENTINAL_Addr2 = "10.3.9.123:7502";
	private static final String SENTINAL_Addr3 = "10.3.9.124:7502";

	// 连接池
	private static int MAX_ACTIVE = 100;
	private static int MAX_IDLE = 100;
	private static int MIN_IDLE = 8;
	private static int TIMEOUT = 2000;

	public static JedisSentinelPool getJedisSentinelPool() {
		int db_index = 0;
		return getSentinelJedisPool(db_index);
	}

	public static synchronized JedisSentinelPool getSentinelJedisPool(int db_index) {
		if (POOL.get(db_index) == null) {
			JedisPoolConfig pool_config = new JedisPoolConfig();
			pool_config.setMaxTotal(MAX_ACTIVE);
			pool_config.setMaxIdle(MAX_IDLE);
			pool_config.setMinIdle(MIN_IDLE);

			// String host = props.getHost();
			// int port = props.getPort();
			int timeout = TIMEOUT;
			String password = StringUtils.trimToNull(PASSWORD);
			int max_index = MAX_DB_COUNT - 1;
			int db = (db_index < 0) ? 0 : ((db_index > max_index) ? max_index : db_index);
			String[] node_arr = new String[] { SENTINAL_Addr1, SENTINAL_Addr2, SENTINAL_Addr3 };
			String client_name = null;

			Set<String> sentinels = new TreeSet<>(Arrays.asList(node_arr));

			JedisSentinelPool jedis_pool = new JedisSentinelPool(MASTER_NAME, sentinels, pool_config, timeout, password, db, client_name);
			POOL.set(db_index, jedis_pool);
		}

		return POOL.get(db_index);
	}

	public static void main(String[] args) {
		try (Jedis jedis = SentinalJedisUtilDemo.getJedisSentinelPool().getResource()) {
			for (int i = 0; i < 100; i++) {
				String key = "foo-" + i;
				String value = key;
				int seconds = 100;
				jedis.setex(key, seconds, value);
			}
		}
	}
}
