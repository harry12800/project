
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

public class ExecuteShellPacket extends GoBackPacket {

	// public Logger logger = Logger.getLogger(LoginPacket.class);
	static {
		HeaderBodyMap.register(ProtocolConstant.SID_SYSTEM, ProtocolConstant.CID_SYSTEM_SHELL_REQ, Request.class);
	}

	public ExecuteShellPacket() {

	}

	@SuppressWarnings("unchecked")
	public ExecuteShellPacket(String shell) {
		requestPacket = new RequestPacket();
		requestPacket.header = new Header(ProtocolConstant.SID_SYSTEM, ProtocolConstant.CID_SYSTEM_SHELL_REQ);
		requestPacket.body = new Request(shell);
		short seqNo = SequenceNumberMaker.getInstance().make();
		requestPacket.header.reserved = seqNo;
		requestPacket.body.setNeedMonitor(true);
	}

	public static class Request extends ReqBody {
		@Tag(2)
		public String shell;

		public Request() {
		}
		public Request(String shell) {
			this.shell =shell;
		}
		
	}

	public static class Response extends RespBody {
		/**
		 * id
		 */
		@Tag(2)
		public long ok;
		@Tag(3)
		public String result;
	}

	public static Header copyHeader() {
		return new Header(ProtocolConstant.SID_SYSTEM, ProtocolConstant.CID_SYSTEM_SHELL_REQ);
	}
}
