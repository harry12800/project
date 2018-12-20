package cn.harry12800.common.core.packet;

import java.util.ArrayList;
import java.util.List;

import cn.harry12800.common.core.codc.HeaderBodyMap;
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

public class GroupUnreadMsgPacket extends GoBackPacket{

//	private Logger logger = Logger.getLogger(GroupUnreadMsgPacket.class);

	static {
		HeaderBodyMap.register(ProtocolConstant.SID_GROUP,
				ProtocolConstant.CID_GROUP_UNREAD_MSG_REQUEST+1,PacketResponse.class);
	}
	public GroupUnreadMsgPacket() {
	 	requestPacket = new RequestPacket();
		requestPacket.body.setNeedMonitor(true);
	}

	public GroupUnreadMsgPacket(PacketRequest.Entity entity) {
		requestPacket = new RequestPacket();
		requestPacket.header = new DefaultHeader(ProtocolConstant.SID_GROUP,
				ProtocolConstant.CID_GROUP_UNREAD_MSG_REQUEST);
		requestPacket.body = new PacketRequest(entity);
		requestPacket.body.setNeedMonitor(true);
	}

	public static  class PacketRequest extends ReqBody {
		public static class Entity {
			public String groupId;
		}

		public PacketRequest(){}
		@Tag(2)
		public  PacketRequest.Entity entity;

		public PacketRequest( PacketRequest.Entity entity) {
			this.entity = entity;

		}
	}
	public  static class PacketResponse extends RespBody {

		@Tag(2)
		public List<MessageEntity> entityList = new ArrayList<MessageEntity>();
	}

}
