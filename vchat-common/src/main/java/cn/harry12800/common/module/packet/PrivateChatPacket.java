
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

public class PrivateChatPacket extends GoBackPacket {

	// public Logger logger = Logger.getLogger(LoginPacket.class);
	static Header reqHeader;
	static Header respHeader;
	static {
		HeaderBodyMap.register(ProtocolConstant.SID_MSG, ProtocolConstant.CID_MSG_DATA,Request.class);
		HeaderBodyMap.register(ProtocolConstant.SID_MSG, ProtocolConstant.CID_MSG_DATA+1,Response.class);
		reqHeader = new Header(ProtocolConstant.SID_MSG,ProtocolConstant.CID_MSG_DATA);
		respHeader = new Header(ProtocolConstant.SID_MSG,ProtocolConstant.CID_MSG_DATA+1);
	}
	public PrivateChatPacket() {

	}

	@SuppressWarnings("unchecked")
	public PrivateChatPacket(long userId,String content) {
		requestPacket = new RequestPacket();
		requestPacket.header = reqHeader;
		requestPacket.body = new Request(userId,content);
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

		/**
		 * 内容
		 */
		@Tag(3)
		public String content;
		public Request() {
		}
		public Request(long targetUserId, String content) {
			super();
			this.targetUserId = targetUserId;
			this.content = content;
		}
	}

	public static class Response extends RespBody {
		/**
		 * id
		 */
	 
	}
}
