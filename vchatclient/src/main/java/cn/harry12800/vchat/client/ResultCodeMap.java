package cn.harry12800.vchat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 错误提示
 * @author harry12800
 *
 */
public class ResultCodeMap {

	private Properties properties = new Properties();

	public ResultCodeMap() {
		InputStream in = getClass().getResourceAsStream("/code.properties");
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
		try {
			properties.load(bf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取内容提示
	 * @param code
	 * @return
	 */
	public String getTipContent(int code) {
		Object object = properties.get(code + "");
		if (object == null) {
			return "错误码:" + code;
		}
		return object.toString();
	}

}
