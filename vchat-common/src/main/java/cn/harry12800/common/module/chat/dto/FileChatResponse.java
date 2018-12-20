package cn.harry12800.common.module.chat.dto;

import cn.harry12800.common.core.serial.Serializer;

public class FileChatResponse extends Serializer {

	public String ok;
	@Override
	protected void read() {
		ok = readString();
	}

	@Override
	protected void write() {
		writeString(ok);
	}

	@Override
	public String toString() {
		return "FileChatResponse [ok=" + ok + "]";
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}


}
