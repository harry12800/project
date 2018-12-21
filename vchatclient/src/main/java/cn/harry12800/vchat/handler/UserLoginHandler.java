package cn.harry12800.vchat.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.harry12800.common.core.annotion.SocketCommand;
import cn.harry12800.common.core.annotion.SocketModule;
import cn.harry12800.common.core.model.ResultCode;
import cn.harry12800.common.module.ModuleId;
import cn.harry12800.common.module.UserCmd;
import cn.harry12800.common.module.chat.dto.MsgResponse;
import cn.harry12800.common.module.chat.dto.PrivateChatResponse;
import cn.harry12800.common.module.user.dto.ShowAllUserResponse;
import cn.harry12800.common.module.user.dto.UserResponse;
import cn.harry12800.j2se.dialog.MessageDialog;
import cn.harry12800.vchat.app.Launcher;
import cn.harry12800.vchat.client.ResultCodeMap;
import cn.harry12800.vchat.frames.LoginFrame;
import cn.harry12800.vchat.frames.MainFrame;
import cn.harry12800.vchat.panels.ChatPanel;

/**
 * 用户模块
 * @author -harry12800-
 */
@Component
@SocketModule(module = ModuleId.USER)
public class UserLoginHandler {

	private ResultCodeMap resultCodeTip = new ResultCodeMap();

	/**
	 * 创建并登录帐号
	 * 
	 * @param resultCode
	 * @param data
	 *            {@link null}
	 */
	@SocketCommand(cmd = UserCmd.REGISTER_AND_LOGIN, desc = "创建并登录帐号")
	public void registerAndLogin(int resultCode, byte[] data) {
		if (resultCode == ResultCode.SUCCESS) {
			UserResponse userResponse = new UserResponse();
			userResponse.readFromBytes(data);
		} else {
		}
	}

	/**
	 * 登录帐号
	 * 
	 * @param resultCode
	 * @param data
	 *            {@link null}
	 */
	@SocketCommand(cmd = UserCmd.LOGIN, desc = "登录帐号")
	public void login(int resultCode, byte[] data) {
		if (resultCode == ResultCode.SUCCESS) {
			UserResponse userResponse = new UserResponse();
			userResponse.readFromBytes(data);
			if (Launcher.currentUser == null) {
//				LoginFrame.getContext().loginSuccess(userResponse);
			} else {

			}
		} else {
			if (Launcher.currentUser == null) {
				LoginFrame.getContext().loginFail(resultCodeTip.getTipContent(resultCode));
			} else {
				new MessageDialog(MainFrame.getContext(), "提示", resultCodeTip.getTipContent(resultCode));
			}
		}
	}

	/**
	 * 登录帐号
	 * 
	 * @param resultCode
	 * @param data
	 *            {@link null}
	 */
	@SocketCommand(cmd = UserCmd.SHOW_ALL_USER, desc = "登录帐号")
	public void showAllUser(int resultCode, byte[] data) {
		if (resultCode == ResultCode.SUCCESS) {
			ShowAllUserResponse userResponse = new ShowAllUserResponse();
			userResponse.readFromBytes(data);
//			MainFrame.getContext().showAllUser(userResponse);
		} else {
			LoginFrame.getContext().loginFail(resultCodeTip.getTipContent(resultCode));
		}
	}

	@SocketCommand(cmd = UserCmd.PULL_MSG, desc = "收到推送聊天信息")
	public void receieveMessage(int resultCode, byte[] data) {
		if (resultCode == ResultCode.SUCCESS) {
			PrivateChatResponse msg = new PrivateChatResponse();
			msg.readFromBytes(data);
			List<MsgResponse> msgs = msg.getMsgs();
			for (MsgResponse msgResponse : msgs) {
//				ChatPanel.getContext().showReceiveMsg(msgResponse);
			}
		} else {
			ChatPanel.getContext().showReceiveMsgFail(resultCodeTip.getTipContent(resultCode));
		}
	}
}
