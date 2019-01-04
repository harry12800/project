package cn.harry12800.common.module.chat.dto;

import cn.harry12800.common.core.serial.Serializer;

public class OfflineResponse extends Serializer {
	@Override
	public String toString() {
		return "OfflineResponse [userId=" + userId + "]";
	}

	public long userId;

	@Override
	protected void read() {
		userId = readLong();
	}

	@Override
	protected void write() {
		writeLong(userId);
	}
}
