package cn.harry12800.vchat.handler.last;

import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.module.packet.LoginPacket.Response;
import cn.harry12800.common.module.packet.PrivateChatPacket;
import cn.harry12800.common.module.packet.PullAllUserPacket;
import cn.harry12800.vchat.app.Launcher;
import cn.harry12800.vchat.frames.LoginFrame;
import cn.harry12800.vchat.frames.MainFrame;
import cn.harry12800.vchat.handler.IpCommand;
import cn.harry12800.vchat.handler.ModuleHanlder;
import cn.harry12800.vchat.panels.ChatPanel;

@ModuleHanlder
public class ChatHandler {

	@IpCommand( desc = "拉取联系人回包", ip = "0.0.2.15", bodyType = PullAllUserPacket.Response.class)
	public void privateChatAck(Packet<PullAllUserPacket.Response> t) {
		System.err.println(t);
		MainFrame.getContext().showAllUser(t.body);
	}
	@IpCommand( desc = "登录消息回包", ip = "0.0.1.4", bodyType = Response.class)
	public void loginack(Packet<Response> t) {
		System.err.println(t);
		if (Launcher.currentUser == null) {
			LoginFrame.getContext().loginSuccess(t.body);
		} else {

		}
	}
	@IpCommand( desc = "接收私人消息", ip = "0.0.3.1", bodyType = PrivateChatPacket.Request.class)
	public void privateChat(Packet<PrivateChatPacket.Request> t) {
		ChatPanel.getContext().showReceiveMsg(t.body);
	}
}
