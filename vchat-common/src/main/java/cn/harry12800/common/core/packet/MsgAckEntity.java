package cn.harry12800.common.core.packet;

import io.protostuff.Tag;

public class MsgAckEntity {
	@Tag(1)
	public int seqNo;
	@Tag(2)
	public String fromId;

	@Override
	public String toString() {
		return String.format("seqNo:%d, fromId:%s", seqNo, fromId);
	}

}
