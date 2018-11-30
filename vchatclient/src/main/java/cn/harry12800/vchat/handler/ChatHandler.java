package cn.harry12800.vchat.handler;

import cn.harry12800.common.core.annotion.SocketCommand;
import cn.harry12800.common.core.annotion.SocketModule;
import cn.harry12800.common.core.model.ResultCode;
import cn.harry12800.common.module.ChatCmd;
import cn.harry12800.common.module.ModuleId;
import cn.harry12800.common.module.chat.dto.FileChatRequest;
import cn.harry12800.common.module.chat.dto.FileChatResponse;
import cn.harry12800.common.module.chat.dto.MsgResponse;
import cn.harry12800.lnk.client.ResultCodeMap;
import cn.harry12800.vchat.panels.ChatPanel;

@SocketModule(module = ModuleId.CHAT)
public class ChatHandler {

	private ResultCodeMap resultCodeTip = new ResultCodeMap();

	@SocketCommand(cmd = ChatCmd.PUBLIC_CHAT, desc = "发送广播消息回包")
	public void publicChat(int resultCode, byte[] data) {
		if (resultCode == ResultCode.SUCCESS) {
			// swingclient.getTips().setText("发送成功");
		} else {
			// swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

	@SocketCommand(cmd = ChatCmd.PRIVATE_CHAT, desc = "发送私人消息回包")
	public void privateChat(int resultCode, byte[] data) {
		if (resultCode == ResultCode.SUCCESS) {
			MsgResponse msg = new MsgResponse();
			msg.readFromBytes(data);
			ChatPanel.getContext().showReceiveMsg(msg);
		} else {
			ChatPanel.getContext().showReceiveMsgFail(resultCodeTip.getTipContent(resultCode));
		}
	}

	@SocketCommand(cmd = ChatCmd.PUSHCHAT, desc = "收到推送聊天信息")
	public void receieveMessage(int resultCode, byte[] data) {
		if (resultCode == ResultCode.SUCCESS) {
			MsgResponse msg = new MsgResponse();
			msg.readFromBytes(data);
			ChatPanel.getContext().showReceiveMsg(msg);
		} else {
			ChatPanel.getContext().showReceiveMsgFail(resultCodeTip.getTipContent(resultCode));
		}
	}

	@SocketCommand(cmd = ChatCmd.FILE_CHAT, desc = "收到文件信息")
	public void receieveFileMessage(int resultCode, byte[] data) {
		if (resultCode == ResultCode.SUCCESS) {
			FileChatRequest msg = new FileChatRequest();
			msg.readFromBytes(data);
			System.err.println(msg);
			ChatPanel.getContext().showReceiveFileMsg(msg);
		} else {
			ChatPanel.getContext().showReceiveMsgFail(resultCodeTip.getTipContent(resultCode));
		}
	}

	@SocketCommand(cmd = ChatCmd.FILE_CHAT_RESULT, desc = "收到发送文件回执信息")
	public void receieveFileResultMessage(int resultCode, byte[] data) {
		if (resultCode == ResultCode.SUCCESS) {
			FileChatResponse msg = new FileChatResponse();
			msg.readFromBytes(data);
			System.err.println(msg);
			//			ChatPanel.getContext().showReceiveMsg(msg);
		} else {
			ChatPanel.getContext().showReceiveMsgFail(resultCodeTip.getTipContent(resultCode));
		}
	}
}
