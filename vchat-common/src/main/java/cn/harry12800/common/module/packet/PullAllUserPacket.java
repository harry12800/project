
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
import cn.harry12800.common.module.packet.entity.UserEnity;
import io.protostuff.Tag;

/**
 * MsgServerPacket:请求(返回)登陆消息服务器 yugui 2014-05-04
 */

public class PullAllUserPacket extends GoBackPacket {

	// public Logger logger = Logger.getLogger(LoginPacket.class);
	static Header reqHeader;
	static Header respHeader;
	static {
		HeaderBodyMap.register(ProtocolConstant.SID_BUDDY_LIST, ProtocolConstant.CID_BUDDY_LIST_ALL_USER_REQUEST,Request.class);
		HeaderBodyMap.register(ProtocolConstant.SID_BUDDY_LIST, ProtocolConstant.CID_BUDDY_LIST_ALL_USER_REQUEST+1,Response.class);
		reqHeader = new Header(ProtocolConstant.SID_BUDDY_LIST,ProtocolConstant.CID_BUDDY_LIST_ALL_USER_REQUEST);
		respHeader = new Header(ProtocolConstant.SID_BUDDY_LIST,ProtocolConstant.CID_BUDDY_LIST_ALL_USER_REQUEST+1);
	}

	@SuppressWarnings("unchecked")
	public PullAllUserPacket() {
		requestPacket = new RequestPacket();
		requestPacket.header = reqHeader;
		requestPacket.body = new Request();
		short seqNo = SequenceNumberMaker.getInstance().make();
		requestPacket.header.reserved = seqNo;
		requestPacket.body.setNeedMonitor(true);
	}

	public static class Request extends ReqBody {

	}

	public static class Response extends RespBody {
		/**
		 * id
		 */
		@Tag(2)
		public List<UserEnity> users = new ArrayList<UserEnity>();

		public Response() { }

		@Override
		public String toString() {
			return "Response [users=" + users + "]";
		}
	 
	}
}
