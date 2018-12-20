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

public class UnreadMsgContactListPacket extends GoBackPacket {

//	private Logger logger = Logger.getLogger(UnreadMsgContactListPacket.class);

	static {
		HeaderBodyMap.register(ProtocolConstant.SID_MSG,
				ProtocolConstant.CID_MSG_UNREAD_CNT_REQUEST+1,PacketResponse.class);
	}
	public UnreadMsgContactListPacket() {
		requestPacket = new RequestPacket();
		requestPacket.body =   new PacketRequest();
		requestPacket.body.setNeedMonitor(true);
        requestPacket. header = new DefaultHeader(ProtocolConstant.SID_MSG,
                ProtocolConstant.CID_MSG_UNREAD_CNT_REQUEST);

	}


	public static class PacketRequest extends ReqBody {
		public PacketRequest() {


		}
	}

	public static class PacketResponse extends RespBody {

		@Tag(2)
		public List<String> entityList = new ArrayList<String>();

        @Override
        public String toString() {
            return "PacketResponse{" +
                    "entityList=" + entityList +
                    '}';
        }
    }
}
