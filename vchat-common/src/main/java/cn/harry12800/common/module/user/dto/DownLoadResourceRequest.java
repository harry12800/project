package cn.harry12800.common.module.user.dto;

import cn.harry12800.common.core.serial.Serializer;

public class DownLoadResourceRequest extends Serializer {

	private long resourceId;

	@Override
	protected void read() {
		resourceId = readLong();
	}

	@Override
	protected void write() {
		writeLong(resourceId);
	}

	public long getResourceId() {
		return resourceId;
	}

	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}

}
