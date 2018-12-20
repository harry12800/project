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

public class MessagePacket extends GoBackPacket{

//	private Logger logger = Logger.getLogger(MessagePacket.class);
static {
	HeaderBodyMap.register(ProtocolConstant.SID_MSG,ProtocolConstant.CID_MSG_DATA+1,PacketResponse.class);
	HeaderBodyMap.register(ProtocolConstant.SID_MSG,ProtocolConstant.CID_MSG_DATA,MessageNotifyPacket.packetNotify.class);
}
	public MessagePacket() {
		requestPacket = new RequestPacket();
		requestPacket.body =  new PacketRequest(null);
		requestPacket.body .setNeedMonitor(true);
	}

	public MessagePacket(MessageEntity entity) {
		requestPacket = new RequestPacket();
		requestPacket.body =  new PacketRequest(entity);
		requestPacket.body .setNeedMonitor(true);
		requestPacket. header = new DefaultHeader(ProtocolConstant.SID_MSG,ProtocolConstant.CID_MSG_DATA);
		requestPacket.header.setLength(SysConstant.PROTOCOL_HEADER_LENGTH + 0);
	}

	public static class PacketRequest extends ReqBody {
	@Tag(2)
    public MessageEntity entity;

	public PacketRequest(){}
		public PacketRequest(MessageEntity message) {
			entity = message;

		}
	}

	
	public static class PacketResponse extends RespBody {
        @Tag(2)
		public MsgAckEntity msgAck = new MsgAckEntity();
	}
}
