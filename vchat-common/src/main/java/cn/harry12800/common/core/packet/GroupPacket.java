package cn.harry12800.common.core.packet;

import java.util.ArrayList;
import java.util.List;

import cn.harry12800.common.core.codc.HeaderBodyMap;
import cn.harry12800.common.core.config.ProtocolConstant;
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

public class GroupPacket extends GoBackPacket {

//	private Logger logger = Logger.getLogger(GroupPacket.class);

	static {
		HeaderBodyMap.register(ProtocolConstant.SID_GROUP,ProtocolConstant.CID_GROUP_LIST_REQUEST+1,PacketResponse.class);
		HeaderBodyMap.register(ProtocolConstant.SID_GROUP,ProtocolConstant.CID_GROUP_DIALOG_LIST_REQUEST+1,PacketResponse.class);
	}
	public GroupPacket() {
		requestPacket.body.setNeedMonitor(true);
	}

	public GroupPacket(int sessionType) {
        requestPacket= new RequestPacket();
		requestPacket.body =   new PacketRequest(sessionType);
//		(sessionType == IMSession.SESSION_GROUP) ?
		int cid =  ProtocolConstant.CID_GROUP_LIST_REQUEST;
//				: ProtocolConstant.CID_GROUP_DIALOG_LIST_REQUEST;
		Header header = new DefaultHeader(ProtocolConstant.SID_GROUP,cid);
		requestPacket.header = header;
		requestPacket.body.setNeedMonitor(true);
	}
	public static class PacketRequest extends ReqBody {
        public PacketRequest(){}
		public PacketRequest(int groupType) {
		}
	}

	public static class PacketResponse extends RespBody {
		@Tag(2)
		public List<GroupEntity> entityList = new ArrayList<GroupEntity>();

        @Override
        public String toString() {
            return "PacketResponse{" +
                    "entityList=" + entityList +
                    '}';
        }
    }
}
