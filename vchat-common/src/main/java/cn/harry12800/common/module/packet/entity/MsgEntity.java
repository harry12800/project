package cn.harry12800.common.module.packet.entity;

import java.util.Date;

import io.protostuff.Tag;

public class MsgEntity {
	@Tag(1)
	public long id;
	@Tag(2)
	public int online;
	@Tag(3)
	public long fromId;
	@Tag(4)
	public long toId;
	@Tag(5)
	public Date sendTime;
	@Tag(6)
	public int dataType;
	@Tag(7)
	public byte[] data;
}
