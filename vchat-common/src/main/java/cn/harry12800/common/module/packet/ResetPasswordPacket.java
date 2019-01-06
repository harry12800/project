
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

public class ResetPasswordPacket extends GoBackPacket {

	// public Logger logger = Logger.getLogger(LoginPacket.class);
	static {
		HeaderBodyMap.register(ProtocolConstant.SID_USER, ProtocolConstant.CID_USER_RESETPASSWORD_REQ,Request.class);
		HeaderBodyMap.register(ProtocolConstant.SID_USER, ProtocolConstant.CID_USER_RESETPASSWORD_REQ+1,Response.class);
	}

	public ResetPasswordPacket() {
		
	}

	@SuppressWarnings("unchecked")
	public ResetPasswordPacket(long userId,String oldpwd,String newPwd) {
		requestPacket = new RequestPacket();
		requestPacket.header = new Header(ProtocolConstant.SID_USER,ProtocolConstant.CID_USER_RESETPASSWORD_REQ);
		requestPacket.body = new Request(userId,oldpwd,newPwd);
		short seqNo = SequenceNumberMaker.getInstance().make();
		requestPacket.header.reserved = seqNo;
		requestPacket.body.setNeedMonitor(true);
	}

	public static class Request extends ReqBody {
		@Tag(2)
		/**
		 * 用户名
		 */
		/**
		 * 要向哪个会话发消息
		 */
		public long userId;
		@Tag(3)
		public String oldPwd;
		@Tag(4)
		public String newPwd;
	 
		public Request() {
		}

		public Request(long userId,String oldpwd,String newPwd) {
			this.userId =userId;
			this.oldPwd = oldpwd;
			this.newPwd = newPwd;
		}

		@Override
		public String toString() {
			return "Request [userId=" + userId + ", oldPwd=" + oldPwd + ", newPwd=" + newPwd + "]";
		}

	}

	public static class Response extends RespBody {
		/**
		 * id
		 */
		@Tag(2)
		public long ok;
	}

	public static Header copyHeader() {
		return new Header(ProtocolConstant.SID_USER,ProtocolConstant.CID_USER_RESETPASSWORD_REQ);
	}
}
