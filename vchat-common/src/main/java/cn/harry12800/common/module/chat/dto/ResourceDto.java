package cn.harry12800.common.module.chat.dto;

import cn.harry12800.common.core.serial.Serializer;

public class ResourceDto extends Serializer {
	private long id;
	private long providerId;
	private long recipientId;
	private long grantTime;
	private int resourceType;
	private String resourceName;
	private byte[] data;

	@Override
	public String toString() {
		return "ResourceDto [id=" + id + ", providerId=" + providerId + ", recipientId=" + recipientId + ", grantTime=" + grantTime + ", resourceType=" + resourceType + ", resourceName="
				+ resourceName + "]";
	}

	@Override
	protected void read() {
		id = readLong();
		providerId = readLong();
		recipientId = readLong();
		grantTime = readLong();
		resourceType = readInt();
		resourceName = readString();
		System.err.println("readBuffer:" + readBuffer.readableBytes());
		data = new byte[readBuffer.readableBytes()];
		readBuffer.readBytes(data);
	}

	@Override
	protected void write() {
		writeLong(id);
		writeLong(providerId);
		writeLong(recipientId);
		writeLong(grantTime);
		writeInt(resourceType);
		writeString(resourceName);
		if (data == null)
			data = new byte[0];
		writeBuffer.writeBytes(data);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
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

	public long getGrantTime() {
		return grantTime;
	}

	public void setGrantTime(long grantTime) {
		this.grantTime = grantTime;
	}

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
}
