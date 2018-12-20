package cn.harry12800.common.module.user.dto;

import cn.harry12800.common.core.serial.Serializer;

/**
 * 注册请求
 * @author harry12800
 *
 */
public class ShowAllUserRequest extends Serializer {
	private long userId;

	@Override
	protected void read() {
		userId = readLong();
	}

	@Override
	protected void write() {
		writeLong(userId);
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
