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

public class RecentContactsPacket extends GoBackPacket {

//	private Logger logger = Logger.getLogger(RecentContactsPacket.class);
static{
	HeaderBodyMap.register(
			ProtocolConstant.SID_BUDDY_LIST,
			ProtocolConstant.CID_BUDDY_LIST_REQUEST+1,PacketResponse.class
	);
}
	public RecentContactsPacket() {

		requestPacket = new RequestPacket();
		requestPacket.body = new PacketRequest();
		requestPacket.body.setNeedMonitor(true);
		requestPacket.header = new DefaultHeader(ProtocolConstant.SID_BUDDY_LIST,
				ProtocolConstant.CID_BUDDY_LIST_REQUEST);
	}


	public static class PacketRequest extends ReqBody {
		public PacketRequest() {


		}
	}

	public static class UserEntity {
		public String id;
		public int userUpdated;

		@Override
		public String toString() {
			return String.format("id:%s, userUpdated:%d", id, userUpdated);
		}
	}

	public static class PacketResponse extends RespBody {
		@Tag(2)
		public List<UserEntity> entityList = new ArrayList<RecentContactsPacket.UserEntity>();

        @Override
        public String toString() {
            return "PacketResponse{" +
                    "entityList=" + entityList +
                    '}';
        }
    }
}
