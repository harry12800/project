package cn.harry12800.common.core.packet;

import cn.harry12800.common.core.config.ProtocolConstant;
import cn.harry12800.common.core.config.SysConstant;
import cn.harry12800.common.core.packet.base.DefaultHeader;
import cn.harry12800.common.core.packet.base.GoBackPacket;
import cn.harry12800.common.core.packet.base.ReqBody;
import cn.harry12800.common.core.packet.base.RequestPacket;
import cn.harry12800.common.core.packet.base.RespBody;

/**
 * MsgServerPacket:请求(返回)登陆消息服务器 yugui 2014-05-04
 */

public class GroupUserListPacket extends GoBackPacket {

//	private Logger logger = Logger.getLogger(GroupUserListPacket.class);

	public GroupUserListPacket(PacketRequest.Entity entity) {
		requestPacket = new RequestPacket();
		requestPacket.body = new PacketRequest(entity);
		requestPacket.body.setNeedMonitor(true);
		requestPacket. header = new DefaultHeader(ProtocolConstant.SID_GROUP,
				ProtocolConstant.CID_GROUP_USER_LIST_REQUEST);

		requestPacket.header.setLength(SysConstant.PROTOCOL_HEADER_LENGTH + 0);

	}


	public  static class PacketRequest extends ReqBody {
		public static class Entity {
			public String groupId;
		}

		public PacketRequest.Entity entity;

		public PacketRequest(PacketRequest.Entity entity) {
			this.entity = entity;

		}
	}

	public static  class PacketResponse extends RespBody {
		public static class Entity {
			String groupId;
			int result;
			GroupEntity group = new GroupEntity();
		}

		public PacketResponse.Entity entity = new PacketResponse.Entity();
	}

}
