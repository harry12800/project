
package cn.harry12800.common.module.packet;

import java.util.ArrayList;
import java.util.List;

import cn.harry12800.common.core.codc.HeaderBodyMap;
import cn.harry12800.common.core.config.ProtocolConstant;
import cn.harry12800.common.core.config.SequenceNumberMaker;
import cn.harry12800.common.core.packet.base.GoBackPacket;
import cn.harry12800.common.core.packet.base.Header;
import cn.harry12800.common.core.packet.base.ReqBody;
import cn.harry12800.common.core.packet.base.RequestPacket;
import cn.harry12800.common.core.packet.base.RespBody;
import cn.harry12800.common.module.packet.entity.MsgEntity;
import io.protostuff.Tag;

/**
 * MsgServerPacket:请求(返回)登陆消息服务器 yugui 2014-05-04
 */

public class PullMsgPacket extends GoBackPacket {

	// public Logger logger = Logger.getLogger(LoginPacket.class);
	static Header reqHeader;
	static Header respHeader;
	static {
		HeaderBodyMap.register(ProtocolConstant.SID_MSG, ProtocolConstant.CID_MSG_UNREAD_CNT_REQUEST,Request.class);
		HeaderBodyMap.register(ProtocolConstant.SID_MSG, ProtocolConstant.CID_MSG_UNREAD_CNT_REQUEST+1,Response.class);
		reqHeader = new Header(ProtocolConstant.SID_MSG,ProtocolConstant.CID_MSG_UNREAD_CNT_REQUEST);
		respHeader = new Header(ProtocolConstant.SID_MSG,ProtocolConstant.CID_MSG_UNREAD_CNT_REQUEST+1);
	}

	public PullMsgPacket() { }

	@SuppressWarnings("unchecked")
	public PullMsgPacket(long userId) {
		requestPacket = new RequestPacket();
		requestPacket.header = reqHeader;
		requestPacket.body = new Request(userId);
		short seqNo = SequenceNumberMaker.getInstance().make();
		requestPacket.header.reserved = seqNo;
		requestPacket.body.setNeedMonitor(true);
	}

	public static class Request extends ReqBody {
		@Tag(2)
		/**
		 * 用户名
		 */
		public  long userid;

		public Request() {
		}

		public Request(long userid) {
			super();
			this.userid = userid;
		}

	}

	public static class Response extends RespBody {
		/**
		 * id
		 */
		@Tag(2)
		public List<MsgEntity> msgs = new ArrayList<MsgEntity>();

		public Response() { }

		@Override
		public String toString() {
			return "Response [msgs=" + msgs + "]";
		}
	 
	}
}
