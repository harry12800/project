
package cn.harry12800.common.module.packet;

import cn.harry12800.common.core.codc.HeaderBodyMap;
import cn.harry12800.common.core.config.ProtocolConstant;
import cn.harry12800.common.core.config.SequenceNumberMaker;
import cn.harry12800.common.core.packet.base.GoBackPacket;
import cn.harry12800.common.core.packet.base.Header;
import cn.harry12800.common.core.packet.base.ReqBody;
import cn.harry12800.common.core.packet.base.RequestPacket;
import cn.harry12800.common.core.packet.base.RespBody;
import io.protostuff.Tag;

/**
 * MsgServerPacket:请求(返回)登陆消息服务器 yugui 2014-05-04
 */

public class ShakeWindowPacket extends GoBackPacket {

	// public Logger logger = Logger.getLogger(LoginPacket.class);
	static Header reqHeader;
	static Header respHeader;
	static {
		HeaderBodyMap.register(ProtocolConstant.SID_MSG, ProtocolConstant.CID_MSG_SHAKE_WINDOW_REQUEST, Request.class);
		HeaderBodyMap.register(ProtocolConstant.SID_MSG, ProtocolConstant.CID_MSG_SHAKE_WINDOW_REQUEST + 1,
				Response.class);
		respHeader = new Header(ProtocolConstant.SID_MSG, ProtocolConstant.CID_MSG_SHAKE_WINDOW_REQUEST + 1);
	}

	public ShakeWindowPacket() {

	}

	@SuppressWarnings("unchecked")
	public ShakeWindowPacket(long userId) {
		requestPacket = new RequestPacket();
		requestPacket.header = new Header(ProtocolConstant.SID_MSG, ProtocolConstant.CID_MSG_SHAKE_WINDOW_REQUEST);
		requestPacket.body = new Request(userId);
		short seqNo = SequenceNumberMaker.getInstance().make();
		requestPacket.header.reserved = seqNo;
		requestPacket.body.setNeedMonitor(true);
	}

	public static class Request extends ReqBody {
		@Tag(2)
		/**
		 * 要向哪个会话发消息
		 */
		public long targetUserId;

		public Request() {
		}

		public Request(long targetUserId) {
			super();
			this.targetUserId = targetUserId;
		}

	}

	public static class Response extends RespBody {

	}

	public static Header copyHeader() {
		return new Header(ProtocolConstant.SID_MSG, ProtocolConstant.CID_MSG_SHAKE_WINDOW_REQUEST);
	}
}
