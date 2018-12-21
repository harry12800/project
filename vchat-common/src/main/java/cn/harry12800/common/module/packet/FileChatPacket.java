
package cn.harry12800.common.module.packet;

import cn.harry12800.common.core.codc.HeaderBodyMap;
import cn.harry12800.common.core.config.ProtocolConstant;
import cn.harry12800.common.core.config.SequenceNumberMaker;
import cn.harry12800.common.core.packet.base.GoBackPacket;
import cn.harry12800.common.core.packet.base.Header;
import cn.harry12800.common.core.packet.base.ReqBody;
import cn.harry12800.common.core.packet.base.RequestPacket;
import cn.harry12800.common.core.packet.base.RespBody;
import cn.harry12800.common.module.chat.dto.FileChatRequest;
import io.protostuff.Tag;

/**
 * MsgServerPacket:请求(返回)登陆消息服务器 yugui 2014-05-04
 */

public class FileChatPacket extends GoBackPacket {

	// public Logger logger = Logger.getLogger(LoginPacket.class);
	static Header reqHeader;
	static Header respHeader;
	static {
		HeaderBodyMap.register(ProtocolConstant.SID_FILE, ProtocolConstant.CID_FILE_SEND,Request.class);
		HeaderBodyMap.register(ProtocolConstant.SID_FILE, ProtocolConstant.CID_FILE_SEND+1,Response.class);
		respHeader = new Header(ProtocolConstant.SID_FILE,ProtocolConstant.CID_FILE_SEND+1);
	}

	public FileChatPacket() {
		
	}
	@SuppressWarnings("unchecked")
	public FileChatPacket(FileChatRequest request) {
		requestPacket = new RequestPacket();
		requestPacket.header = new Header(ProtocolConstant.SID_FILE,ProtocolConstant.CID_FILE_SEND);
		requestPacket.body = new Request(request);
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
		public long targetUserId;
		@Tag(3)
		public long senderUserId;
		@Tag(4)
		public short total;
		@Tag(5)
		public short index;
		@Tag(6)
		public long position;
		@Tag(7)
		public String name;
		@Tag(8)
		public String messageId;
		@Tag(9)
		public byte[] data;

		public Request() {
		}

		public Request(FileChatRequest request) {
			targetUserId = request.getTargetUserId();
			senderUserId =request.getSenderUserId();
			total = request.getTotal();
			index = request.getIndex();
			position = request.getPosition();
			name = request.getName();
			messageId = request.getMessageId();
			data = request.getData();
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
		return new Header(ProtocolConstant.SID_FILE,ProtocolConstant.CID_FILE_SEND);
	}
}
