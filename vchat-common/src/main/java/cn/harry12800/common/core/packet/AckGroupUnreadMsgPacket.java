package cn.harry12800.common.core.packet;

import java.util.ArrayList;
import java.util.List;

import cn.harry12800.common.core.config.ProtocolConstant;
import cn.harry12800.common.core.packet.base.DefaultHeader;
import cn.harry12800.common.core.packet.base.GoBackPacket;
import cn.harry12800.common.core.packet.base.ReqBody;
import cn.harry12800.common.core.packet.base.RequestPacket;
import cn.harry12800.common.core.packet.base.RespBody;
import io.protostuff.Tag;


/**
 * MsgServerPacket:请求(返回)登陆消息服务器 yugui 2014-05-04
 */

public class AckGroupUnreadMsgPacket extends GoBackPacket {

//	private Logger logger = Logger.getLogger(AckGroupUnreadMsgPacket.class);

	public AckGroupUnreadMsgPacket(String contactId) {
		requestPacket = new RequestPacket();
		requestPacket.body = new PacketRequest(contactId);
		requestPacket.body.setNeedMonitor(true);
		requestPacket.header = new DefaultHeader(ProtocolConstant.SID_GROUP,
				ProtocolConstant.CID_GROUP_MSG_READ_ACK);

	}

	public AckGroupUnreadMsgPacket() {
		requestPacket = new RequestPacket();
		requestPacket.body.setNeedMonitor(true);
		requestPacket.header = new DefaultHeader(ProtocolConstant.SID_GROUP,
				ProtocolConstant.CID_GROUP_MSG_READ_ACK);
	}

	public static class PacketRequest extends ReqBody {
		@Tag(2)
		public String groupId;

		public PacketRequest(){}
		public PacketRequest(String groupId) {
			this.groupId = groupId;
		}
	}

	public static class PacketResponse extends RespBody {

		@Tag(2)
		public List<MessageEntity> entityList = new ArrayList<MessageEntity>();
	}
}
