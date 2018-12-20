package cn.harry12800.common.core.packet;

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

public class MessageNotifyPacket extends GoBackPacket {

//	private Logger logger = Logger.getLogger(MessageNotifyPacket.class);
	static{
		HeaderBodyMap.register(ProtocolConstant.SID_MSG,ProtocolConstant.CID_MSG_DATA,packetNotify.class);
	}
	public MessageNotifyPacket() {
		// todo eric remove this
		requestPacket = new RequestPacket();
		requestPacket.body.setNeedMonitor(true);
	}

	public MessageNotifyPacket(MessageEntity msg) {
		requestPacket = new RequestPacket();
        requestPacket.body = new PacketAck(msg);
        requestPacket.body.mNeedMonitor = true;
		requestPacket.header = new DefaultHeader(ProtocolConstant.SID_MSG,
				ProtocolConstant.CID_MSG_DATA_ACK);
		int contentLength = 4 + (4 + msg.fromId.length());
		requestPacket.header.setLength(SysConstant.PROTOCOL_HEADER_LENGTH + contentLength);

	}

	public static class PacketAck extends ReqBody {
		@Tag(2)
		public MsgAckEntity msgAck = new MsgAckEntity();

		public PacketAck(MessageEntity msg) {
			msgAck.seqNo = msg.seqNo;
			msgAck.fromId = msg.fromId;
		}
	}

	public static class packetNotify extends RespBody {
		@Tag(2)
		public MessageEntity msg = new MessageEntity();
	}
}
