package cn.harry12800.common.core.packet.base;

import cn.harry12800.common.core.config.SequenceNumberMaker;
import cn.harry12800.common.core.config.SysConstant;

public class DefaultHeader extends Header {

	// private Logger logger = Logger.getLogger(DefaultHeader.class);

	public DefaultHeader() {
	}

	public DefaultHeader(int serviceId, int commandId) {
		version = ((short) SysConstant.PROTOCOL_VERSION);
		this.serviceId = (short) serviceId;
		this.commandId = (short) commandId;
		short seqNo = SequenceNumberMaker.getInstance().make();
		reserved = (seqNo);
	}
}
