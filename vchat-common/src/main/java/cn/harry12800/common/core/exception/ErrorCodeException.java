package cn.harry12800.common.core.exception;

import java.util.Properties;

import cn.harry12800.tools.FileUtils;

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

	static Properties p;
	static {
		p  = FileUtils.loadProps("code.properties");
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
