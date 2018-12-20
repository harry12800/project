package cn.harry12800.common.core.packet;

import java.util.ArrayList;
import java.util.List;

import cn.harry12800.common.core.codc.HeaderBodyMap;
import cn.harry12800.common.core.config.ProtocolConstant;
import cn.harry12800.common.core.config.SysConstant;
import cn.harry12800.common.core.packet.base.DefaultHeader;
import cn.harry12800.common.core.packet.base.GoBackPacket;
import cn.harry12800.common.core.packet.base.Header;
import cn.harry12800.common.core.packet.base.ReqBody;
import cn.harry12800.common.core.packet.base.RequestPacket;
import cn.harry12800.common.core.packet.base.RespBody;
import io.protostuff.Tag;

/**
 * MsgServerPacket:请求(返回)登陆消息服务器 yugui 2014-05-04
 */

public class UnreadMsgGroupListPacket extends GoBackPacket{

//	private Logger logger = Logger.getLogger(UnreadMsgGroupListPacket.class);

	static Header header ;
	static{
		header = new DefaultHeader(ProtocolConstant.SID_GROUP,
				ProtocolConstant.CID_GROUP_UNREAD_CNT_REQUEST);
		int contentLength = 0;
		header.setLength(SysConstant.PROTOCOL_HEADER_LENGTH + contentLength);
		HeaderBodyMap.register(ProtocolConstant.SID_GROUP,
				ProtocolConstant.CID_GROUP_UNREAD_CNT_REQUEST+1,PacketResponse.class);
	}

	public UnreadMsgGroupListPacket() {
        requestPacket= new RequestPacket();
		requestPacket.header = header;
		requestPacket.body = new PacketRequest();
		requestPacket.body.setNeedMonitor(true);
	}


	public static class PacketRequest extends ReqBody {

		public PacketRequest() {
		}
	}

	public static class PacketResponse extends RespBody {
		public static class Entity {
			@Tag(1)
			public String groupId;
			@Tag(2)
			public int unreadCnt;
		}

		@Tag(2)
		public List<Entity> entityList = new ArrayList<Entity>();

		@Override
		public String toString() {
			return "PacketResponse{" +
					"entityList=" + entityList +
					'}';
		}
	}
}
