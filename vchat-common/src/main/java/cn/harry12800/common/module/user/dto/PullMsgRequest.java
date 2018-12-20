package cn.harry12800.common.module.user.dto;

import cn.harry12800.common.core.serial.Serializer;

public class PullMsgRequest extends Serializer {

	private long userid;

	@Override
	protected void read() {
		userid = readLong();
	}

	@Override
	protected void write() {
		writeLong(userid);
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

}
