package cn.harry12800.common.module.chat.dto;

import cn.harry12800.common.core.serial.Serializer;

/**
 * 私聊请求
 * @author harry12800
 *
 */
public class ShakeRequest extends Serializer {
	/**
	 * 要向哪个会话发消息
	 */
	private long targetUserId; 
	@Override
	protected void read() {
		this.targetUserId = readLong();
	}

	@Override
	protected void write() {
		writeLong(targetUserId);
	}

	public long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(long targetUserId) {
		this.targetUserId = targetUserId;
	}
	
}
