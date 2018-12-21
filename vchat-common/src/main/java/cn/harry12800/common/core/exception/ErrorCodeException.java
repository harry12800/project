package cn.harry12800.common.core.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 错误码携带异常
 * 
 * @author harry12800
 *
 */
public class ErrorCodeException extends  Exception {

	/** 
	 * 
	 */
	private static final long serialVersionUID = 4143519479094905222L;

	static Properties p = new Properties();
	static {
		try {
			InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("code.properties");
			p.load(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 错误代码
	 */
	private final int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public ErrorCodeException(int errorCode) {
		this.errorCode = errorCode;
	}

	public static String getMessage(int errorCode) {
		return p.getProperty("" + errorCode);
	}
}
