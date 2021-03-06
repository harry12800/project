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

public class AllContactsPacket extends GoBackPacket {

//	private Logger logger = Logger.getLogger(AllContactsPacket.class);
static {
    HeaderBodyMap.register(
            ProtocolConstant.SID_BUDDY_LIST,
            ProtocolConstant.CID_BUDDY_LIST_ALL_USER_REQUEST+1,PacketResponse.class
    );
}
	public AllContactsPacket() {
		requestPacket = new RequestPacket();
		requestPacket.body = new PacketRequest();
		requestPacket.body.setNeedMonitor(true);
		requestPacket. header = new DefaultHeader(ProtocolConstant.SID_BUDDY_LIST,
				ProtocolConstant.CID_BUDDY_LIST_ALL_USER_REQUEST);
		int contentLength = 0;

	}

	public static class PacketRequest extends ReqBody {
		public PacketRequest() {


		}
	}


	public static class PacketResponse extends RespBody {

		@Tag(2)
		public List<ContactEntity> entityList = new ArrayList<ContactEntity>();
	}
}
