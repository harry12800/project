package cn.harry12800.common.core.packet;

import java.util.List;

import cn.harry12800.common.core.config.ProtocolConstant;
import cn.harry12800.common.core.packet.base.DefaultHeader;
import cn.harry12800.common.core.packet.base.GoBackPacket;
import cn.harry12800.common.core.packet.base.ReqBody;
import cn.harry12800.common.core.packet.base.RequestPacket;
import cn.harry12800.common.core.packet.base.RespBody;

/**
 * MsgServerPacket:请求(返回)登陆消息服务器 yugui 2014-05-04
 */

public class CreateTempGroupPacket extends GoBackPacket{

//	private Logger logger = Logger.getLogger(CreateTempGroupPacket.class);

	public CreateTempGroupPacket() {
		requestPacket = new RequestPacket();
		requestPacket.body.setNeedMonitor(true);

	}

	public CreateTempGroupPacket(String groupName, String groupAvatarUrl,
			List<String> memberList) {
		requestPacket = new RequestPacket();
		requestPacket.body= new PacketRequest(groupName, groupAvatarUrl, memberList);
		requestPacket.body.setNeedMonitor(true);
		requestPacket.header = new DefaultHeader(ProtocolConstant.SID_GROUP,
				ProtocolConstant.CID_GROUP_CREATE_TMP_GROUP_REQUEST);
	}


	public static class PacketRequest extends ReqBody {
		String groupName;
		String groupAvatarUrl;
		List<String> memberList;

		public PacketRequest(String groupName, String groupAvatarUrl,
				List<String> memberList) {
			this.groupName = groupName;
			this.groupAvatarUrl = groupAvatarUrl;
			this.memberList = memberList;

		}
	}

	public static class PacketResponse extends RespBody {
		public int result;
		public GroupEntity entity = new GroupEntity();
	}
}
