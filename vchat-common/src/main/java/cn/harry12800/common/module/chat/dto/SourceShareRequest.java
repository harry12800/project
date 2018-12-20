package cn.harry12800.common.module.chat.dto;

import cn.harry12800.common.core.serial.Serializer;

/**
 * 私聊请求
 * @author harry12800
 *
 */
public class SourceShareRequest extends Serializer {

	private long providerId;
	private long recipientId;
	private int resourceType;
	private String resourceName;
	private String path;
	private byte[] data;

	@Override
	protected void read() {
		providerId = readLong();
		recipientId = readLong();
		resourceType = readInt();
		resourceName = readString();
		path = readString();
		data = new byte[readBuffer.readableBytes()];
		readBuffer.readBytes(data);
	}

	@Override
	protected void write() {
		writeLong(providerId);
		writeLong(recipientId);
		writeInt(resourceType);
		writeString(resourceName);
		writeString(path);
		writeBuffer.writeBytes(data);
	}

	public long getProviderId() {
		return providerId;
	}

	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}

	public long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(long recipientId) {
		this.recipientId = recipientId;
	}

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
