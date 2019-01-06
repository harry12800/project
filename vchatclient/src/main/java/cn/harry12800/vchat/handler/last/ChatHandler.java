package cn.harry12800.vchat.handler.last;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.harry12800.common.core.exception.ErrorCodeException;
import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.module.packet.FileChatPacket;
import cn.harry12800.common.module.packet.LoginPacket.Response;
import cn.harry12800.common.module.packet.PrivateChatPacket;
import cn.harry12800.common.module.packet.PullAllUserPacket;
import cn.harry12800.common.module.packet.ResetPasswordPacket;
import cn.harry12800.common.module.packet.ShakeWindowPacket;
import cn.harry12800.common.module.packet.UserOnlineOROffLinePacket;
import cn.harry12800.j2se.tip.TipFrame;
import cn.harry12800.j2se.tip.TipFrame.Builder;
import cn.harry12800.vchat.app.Launcher;
import cn.harry12800.vchat.frames.LoginFrame;
import cn.harry12800.vchat.frames.MainFrame;
import cn.harry12800.vchat.handler.IpCommand;
import cn.harry12800.vchat.handler.ModuleHanlder;
import cn.harry12800.vchat.panels.ChangePasswordPanel;
import cn.harry12800.vchat.panels.ChatPanel;

@ModuleHanlder
public class ChatHandler {

	private static Logger LOG = LoggerFactory.getLogger(ChatHandler.class);

	@IpCommand(desc = "拉取联系人回包", ip = "0.0.2.15", bodyType = PullAllUserPacket.Response.class)
	public void pullAllUserAck(Packet<PullAllUserPacket.Response> t) {
		MainFrame.getContext().showAllUser(t.body);
	}

	@IpCommand(desc = "登录消息回包", ip = "0.0.1.4", bodyType = Response.class)
	public void loginack(Packet<Response> t) {
		if (t.header.dataType != 0) {
			LoginFrame.getContext().loginFail(ErrorCodeException.getMessage(t.header.dataType));
			return;
		}
		if (Launcher.currentUser == null) {
			LoginFrame.getContext().loginSuccess(t.body);
		} else {
		}
	}

	@IpCommand(desc = "用户上下线包", ip = "0.0.1.11", bodyType = UserOnlineOROffLinePacket.OnlineOFFLineNotify.class)
	public void userOnlineOrOffline(Packet<UserOnlineOROffLinePacket.OnlineOFFLineNotify> t) {
		Builder builder = TipFrame.createBuilder();
		if(t.body.onOrOff)
			builder.dataList.add(t.body.userName + "已上线！");
		else{
			builder.dataList.add(t.body.userName + "已下线！");
		}
		builder.actionName = "确定";
		builder.hasHead = true;
		builder.headTitle = "上下线通知";
		TipFrame.show(builder);
	}

	@IpCommand(desc = "摇晃窗口", ip = "0.0.3.11", bodyType = ShakeWindowPacket.Request.class)
	public void shakeWindow(Packet<ShakeWindowPacket.Request> t) {
		MainFrame.getContext().shake();
	}

	@IpCommand(desc = "摇晃窗口", ip = "0.0.3.12", bodyType = ShakeWindowPacket.Response.class)
	public void shakeWindowAck(Packet<ShakeWindowPacket.Response> t) {
		if (t.header.dataType != 0) {
			return;
		}
		ChatPanel.getContext().shakeWindowAck(t.body.ok);
	}

	@IpCommand(desc = "接收私人消息", ip = "0.0.3.1", bodyType = PrivateChatPacket.Request.class)
	public void privateChat(Packet<PrivateChatPacket.Request> t) {
		ChatPanel.getContext().showReceiveMsg(t.body);
	}

	@IpCommand(desc = "接收私人消息回包", ip = "0.0.3.2", bodyType = PrivateChatPacket.Response.class)
	public void privateChatAck(Packet<PrivateChatPacket.Response> t) {

	}

	@IpCommand(desc = "接收文件消息回包", ip = "0.0.6.1", bodyType = FileChatPacket.Request.class)
	public void fileChatAck(Packet<FileChatPacket.Request> t) {
		LOG.info("收到文件信息！" + t.body);
		ChatPanel.getContext().showReceiveFileMsg(t.body);
	}

	@IpCommand(desc = "接收文件", ip = "0.0.6.2", bodyType = FileChatPacket.Response.class)
	public void fileChat(Packet<FileChatPacket.Response> t) {

	}

	@IpCommand(desc = "修改密码回执", ip = "0.0.8.2", bodyType = ResetPasswordPacket.Response.class)
	public void resetpassword(Packet<ResetPasswordPacket.Response> t) {
		LOG.info("修改密码：" + t.body.ok + "");
		ChangePasswordPanel.getContext().result(t.body.ok);
	}
}
