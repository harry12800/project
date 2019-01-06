
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

public class LogoutPacket extends GoBackPacket {

	// public Logger logger = Logger.getLogger(LoginPacket.class);
	static {
		HeaderBodyMap.register(ProtocolConstant.SID_LOGIN, ProtocolConstant.CID_LOGIN_REQ_LOGINOUT,Request.class);
		HeaderBodyMap.register(ProtocolConstant.SID_LOGIN, ProtocolConstant.CID_LOGIN_RES_LOGINOUT,Response.class);
	}
	public LogoutPacket() { }

	@SuppressWarnings("unchecked")
	public LogoutPacket(long userId) {
		requestPacket = new RequestPacket();
		requestPacket.header = new Header(ProtocolConstant.SID_LOGIN,ProtocolConstant.CID_LOGIN_REQ_LOGINOUT);
		requestPacket.body = new Request(userId);
		short seqNo = SequenceNumberMaker.getInstance().make();
		requestPacket.header.reserved = seqNo;
		requestPacket.body.setNeedMonitor(true);
		responsePacket.body = new Response();
		responsePacket.header =  new Header(ProtocolConstant.SID_LOGIN,ProtocolConstant.CID_LOGIN_RES_LOGINOUT);
	}

	public static class Request extends ReqBody {
		@Tag(2)
		/**
		 * 用户名
		 */
		public long userId;

		public Request() {
		}

		public Request(long userId) {
			this.userId =userId;
		}

	}

	public static class Response extends RespBody {
		/**
		 * id
		 */
		@Tag(2)
		public int ok;
	}
}
