package cn.harry12800.vchat.server.module.handler;

import cn.harry12800.common.core.annotion.SocketCommand;
import cn.harry12800.common.core.annotion.SocketModule;
import cn.harry12800.common.core.model.Result;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.module.ModuleId;
import cn.harry12800.common.module.UserCmd;
import cn.harry12800.common.module.user.dto.LoginRequest;
import cn.harry12800.common.module.user.dto.PullMsgResponse;
import cn.harry12800.common.module.user.dto.ShowAllUserResponse;
import cn.harry12800.common.module.user.dto.UserResponse;

/**
 * 用户模块
 * @author harry12800
 *
 */
@SocketModule(module = ModuleId.USER)
public interface UserHandler {

	/**
	 * 登录帐号
	 * @param channel
	 * @param data {@link LoginRequest}
	 * @return
	 */
	@SocketCommand(cmd = UserCmd.LOGIN,desc="登录帐号")
	public Result<UserResponse> login(Session session, byte[] data);
	
	/**
	 * 查询所有用户
	 * @param channel
	 * @param data {@link LoginRequest}
	 * @return
	 */
	@SocketCommand(cmd = UserCmd.SHOW_ALL_USER,desc="查询所有用户")
	public Result<ShowAllUserResponse> showAllUser(Session session, byte[] data);

	/**
	 * 拉取未读消息请求
	 * @param channel
	 * @param data {@link LoginRequest}
	 * @return
	 */
	@SocketCommand(cmd = UserCmd.PULL_MSG,desc="拉取未读消息请求")
	public Result<PullMsgResponse> pullMsg(Session session, byte[] data);

}
