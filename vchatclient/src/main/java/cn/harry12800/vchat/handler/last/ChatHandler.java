package cn.harry12800.vchat.handler.last;

import cn.harry12800.common.core.packet.MessagePacket;
import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.module.packet.LoginPacket.Response;
import cn.harry12800.vchat.handler.IpCommand;
import cn.harry12800.vchat.handler.ModuleHanlder;

@ModuleHanlder
public class ChatHandler {

	@IpCommand( desc = "发送私人消息回包", ip = "0.0.0.0", bodyType = MessagePacket.PacketResponse.class)
	public void privateChat(Packet<MessagePacket.PacketResponse> t) {
		System.err.println(t);
	}
	@IpCommand( desc = "登录消息回包", ip = "0.0.1.4", bodyType = Response.class)
	public void loginack(Packet<Response> t) {
		System.err.println(t);
	}
}
