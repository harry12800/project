package cn.harry12800.common.module.chat.dto;

import cn.harry12800.common.core.serial.Serializer;

/**
 * 私聊请求
 * @author harry12800
 *
 */
public class FileChatRequest extends Serializer {

	/**
	 * 要向哪个会话发消息
	 */
	private long targetUserId;
	private long senderUserId;
	private short total;
	private short index;
	private long position;
	private String name;
	private String messageId;
	private byte[] data;
	 
	@Override
	protected void read() {
//		this.senderUserId = readString();
//		this.targetUserId = readString();
		this.index = readShort();
		this.total = readShort();
		this.position = readLong();
		this.name = readString();
		this.messageId = readString();
		data = new byte[readBuffer.readableBytes()];
		readBuffer.readBytes(data);
	}

	@Override
	protected void write() {
//		writeString(senderUserId);
//		writeString(targetUserId);
		writeShort(index);
		writeShort(total);
		writeLong(position);
		writeString(name);
		writeString(messageId);
		if (data == null)
			data = new byte[0];
		writeBuffer.writeBytes(data);
	}

	 

	public long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(long targetUserId) {
		this.targetUserId = targetUserId;
	}

	public long getSenderUserId() {
		return senderUserId;
	}

	public void setSenderUserId(long senderUserId) {
		this.senderUserId = senderUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getTotal() {
		return total;
	}

	public void setTotal(short total) {
		this.total = total;
	}

	public short getIndex() {
		return index;
	}

	public void setIndex(short index) {
		this.index = index;
	}

	public long getPosition() {
		return position;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "FileChatRequest [targetUserId=" + targetUserId + ", total=" + total + ", index=" + index + ", position=" + position + ", name=" + name + ", messageId=" + messageId + "]";
	}
	
}
