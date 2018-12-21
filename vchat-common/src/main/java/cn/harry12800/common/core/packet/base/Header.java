package cn.harry12800.common.core.packet.base;

import io.protostuff.Tag;

public class Header {
	@Tag(1)
	public short version=1; // 版本号
	@Tag(2)
	public short dataType=0; // 群消息，还是单聊消息，其他消息
	@Tag(3)
	public long destination=0; // 目的地
	@Tag(4)
	public int serviceId=0; // SID
	@Tag(5)
	public int commandId=0; // CID
	@Tag(6)
	public short reserved=0; // 保留，可用于如序列号等

	public Header() { }
	

	
	public Header(int serviceId, int commandId) {
		super();
		this.serviceId = serviceId;
		this.commandId = commandId;
	}


	@Override
	public String toString() {
		return "Header [dataType=" + dataType + ", serviceId=" + serviceId + ", commandId=" + commandId + ", version="
				+ version + ", reserved=" + reserved + "]";
	}
}
