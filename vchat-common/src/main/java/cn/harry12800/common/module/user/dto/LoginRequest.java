package cn.harry12800.common.module.user.dto;

import cn.harry12800.common.core.serial.Serializer;

/**
 * 登录请求
 * @author harry12800
 *
 */
public class LoginRequest extends Serializer {

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String passward;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassward() {
		return passward;
	}

	public void setPassward(String passward) {
		this.passward = passward;
	}

	@Override
	protected void read() {
		this.userName = readString();
		this.passward = readString();
	}

	@Override
	protected void write() {
		writeString(userName);
		writeString(passward);
	}
}
