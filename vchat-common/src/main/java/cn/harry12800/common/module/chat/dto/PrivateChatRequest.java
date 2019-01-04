package cn.harry12800.common.module.chat.dto;

import cn.harry12800.common.core.serial.Serializer;

/**
 * 私聊请求
 * @author harry12800
 *
 */
public class PrivateChatRequest extends Serializer {

	/**
	 * 要向哪个会话发消息
	 */
	private long targetUserId;

	/**
	 * 内容
	 */
	private String context;

	public long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(long targetUserId) {
		this.targetUserId = targetUserId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	protected void read() {
		this.targetUserId = readLong();
		this.context = readString();
	}

	@Override
	protected void write() {
		writeLong(targetUserId);
		writeString(context);
	}
}
