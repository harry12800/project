package cn.harry12800.common.module.chat.dto;

import java.util.ArrayList;
import java.util.List;

import cn.harry12800.common.core.serial.Serializer;

public class PrivateChatResponse extends Serializer {
	List<MsgResponse> msgs = new ArrayList<MsgResponse>();

	@Override
	protected void read() {
		msgs = readList(MsgResponse.class);
	}

	@Override
	protected void write() {
		writeList(msgs);
	}

	public List<MsgResponse> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<MsgResponse> msgs) {
		this.msgs = msgs;
	}

}
