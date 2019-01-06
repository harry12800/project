
package cn.harry12800.common.module.packet;

import cn.harry12800.common.core.codc.HeaderBodyMap;
import cn.harry12800.common.core.config.ProtocolConstant;
import cn.harry12800.common.core.config.SequenceNumberMaker;
import cn.harry12800.common.core.packet.base.GoBackPacket;
import cn.harry12800.common.core.packet.base.Header;
import cn.harry12800.common.core.packet.base.ReqBody;
import cn.harry12800.common.core.packet.base.RequestPacket;
import io.protostuff.Tag;

/**
 * MsgServerPacket:请求(返回)登陆消息服务器 yugui 2014-05-04
 */

public class UserOnlineOROffLinePacket extends GoBackPacket {

	// public Logger logger = Logger.getLogger(LoginPacket.class);
	static {
		HeaderBodyMap.register(ProtocolConstant.SID_LOGIN, ProtocolConstant.CID_LOGIN_ONLINE_OFFLINE_NOTIFY,
				OnlineOFFLineNotify.class);
	}

	public UserOnlineOROffLinePacket() {
	}

	@SuppressWarnings("unchecked")
	public UserOnlineOROffLinePacket(long userId, String userName, boolean onOrOff) {
		requestPacket = new RequestPacket();
		requestPacket.header = new Header(ProtocolConstant.SID_LOGIN, ProtocolConstant.CID_LOGIN_ONLINE_OFFLINE_NOTIFY);
		requestPacket.body = new OnlineOFFLineNotify(userId, userName, onOrOff);
		short seqNo = SequenceNumberMaker.getInstance().make();
		requestPacket.header.reserved = seqNo;
		requestPacket.body.setNeedMonitor(true);
	}

	public static class OnlineOFFLineNotify extends ReqBody {
		@Tag(2)
		public boolean onOrOff;
		/**
		 * 用户名
		 */
		@Tag(4)
		public String userName;
		/**
		 * 密码
		 */
		@Tag(3)
		public long userId;

		public OnlineOFFLineNotify() {
		}

		public OnlineOFFLineNotify(long userId, String userName, boolean onOrOff) {
			this.userName = userName;
			this.userId = userId;
			this.onOrOff = onOrOff;
		}

	}
}
