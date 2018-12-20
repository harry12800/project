package cn.harry12800.common.core.packet;

import java.util.ArrayList;
import java.util.List;

import cn.harry12800.common.core.codc.HeaderBodyMap;
import cn.harry12800.common.core.config.ProtocolConstant;
import cn.harry12800.common.core.config.SysConstant;
import cn.harry12800.common.core.packet.base.DefaultHeader;
import cn.harry12800.common.core.packet.base.GoBackPacket;
import cn.harry12800.common.core.packet.base.ReqBody;
import cn.harry12800.common.core.packet.base.RequestPacket;
import cn.harry12800.common.core.packet.base.RespBody;
import io.protostuff.Tag;

/**
 * MsgServerPacket:请求(返回)登陆消息服务器 yugui 2014-05-04
 */

public class AckUnreadMsgPacket extends GoBackPacket  {

//	private Logger logger = Logger.getLogger(AckUnreadMsgPacket.class);

	static {
		HeaderBodyMap.register(ProtocolConstant.SID_MSG,
				ProtocolConstant.CID_MSG_READ_ACK+1,PacketResponse.class);
	}
	public AckUnreadMsgPacket(String contactId) {

		requestPacket = new RequestPacket();
		requestPacket.body = new PacketRequest(contactId);
		requestPacket.body.setNeedMonitor(true);
		requestPacket.header = new DefaultHeader(ProtocolConstant.SID_MSG,
				ProtocolConstant.CID_MSG_READ_ACK);
		requestPacket.header .setLength(SysConstant.PROTOCOL_HEADER_LENGTH + (4));

	}

	public AckUnreadMsgPacket() {
		requestPacket = new RequestPacket();
		requestPacket.body = new PacketRequest("");
		requestPacket.body.setNeedMonitor(true);
	}


	public static class PacketRequest extends ReqBody {
		public String contactId;

		public PacketRequest(String contactId) {
			this.contactId = contactId;

		}
	}

	public static class PacketResponse extends RespBody {

		@Tag(2)
		public List<MessageEntity> entityList = new ArrayList<MessageEntity>();
	}
}
