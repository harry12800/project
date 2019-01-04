package cn.harry12800.db.entity;

import java.util.Arrays;
import java.util.Date;

public class FileResource {
	private long id;
	private long providerId;
	private long recipientId;
	private Date grantTime;
	private int resourceType;
	private byte[] data;
	private String resourceName;
	private String path;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public Date getGrantTime() {
		return grantTime;
	}
	public void setGrantTime(Date grantTime) {
		this.grantTime = grantTime;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	@Override
	public String toString() {
		return "ShareResource [id=" + id + ", providerId=" + providerId + ", recipientId=" + recipientId + ", grantTime=" + grantTime + ", resourceType=" + resourceType + ", data="
				+ Arrays.toString(data) + ", resourceName=" + resourceName + ", path=" + path + "]";
	}
	 
}
