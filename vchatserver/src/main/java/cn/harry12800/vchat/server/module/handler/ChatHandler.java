package cn.harry12800.vchat.server.module.handler;

import cn.harry12800.common.core.annotion.SocketCommand;
import cn.harry12800.common.core.annotion.SocketModule;
import cn.harry12800.common.core.model.Result;
import cn.harry12800.common.module.ChatCmd;
import cn.harry12800.common.module.ModuleId;
import cn.harry12800.common.module.chat.dto.PrivateChatRequest;
import cn.harry12800.common.module.chat.dto.PublicChatRequest;

/**
 * 聊天
 * @author harry12800
 *
 */
@SocketModule(module = ModuleId.CHAT)
public interface ChatHandler {

	/**
	 * 广播消息
	 * @param userId
	 * @param data {@link PublicChatRequest}
	 * @return
	 */
	@SocketCommand(cmd = ChatCmd.PUBLIC_CHAT,desc="广播消息")
	public Result<?> publicChat(long userId, byte[] data);

	/**
	 * 私人消息
	 * @param userId
	 * @param data {@link PrivateChatRequest}
	 * @return
	 */
	@SocketCommand(cmd = ChatCmd.PRIVATE_CHAT,desc="私人消息")
	public Result<?> privateChat(long userId, byte[] data);
 
	/**
	 * 私人消息
	 * @param userId
	 * @param data {@link PrivateChatRequest}
	 * @return
	 */
	@SocketCommand(cmd = ChatCmd.FILE_CHAT,desc="发送文件")
	public Result<?> FileChat(long userId, byte[] data);
}
