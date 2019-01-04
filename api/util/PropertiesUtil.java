package cn.harry12800.api.util;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * Created by harry12800 on 2018/4/19.
 */
public class PropertiesUtil {

	/**
	 * 读取properties文件
	 */
	private final static Properties p = new Properties();
	/**
	 * 读取ZooKeeper数据,键表示xdata服务器在zk上注册的路径。值表示API_HOST，也就是订阅的服务器路径
	 */
	public final static List<String> apiHosts = new Vector<>();

	static {
		try {
			p.load(PropertiesUtil.class.getResourceAsStream("/scan.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 根据key得到value的值
	public static String getValue(String key) {
		return p.getProperty(key);
	}
}
