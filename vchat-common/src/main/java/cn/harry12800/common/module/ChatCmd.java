package cn.harry12800.common.module;

/**
 * 聊天模块
 * @author harry12800
 *
 */
public interface ChatCmd {

	/**
	 * 广播消息
	 */
	short PUBLIC_CHAT = 1;

	/**
	 * 私人消息
	 */
	short PRIVATE_CHAT = 2;
	
	/**
	 * 文件消息
	 */
	short FILE_CHAT = 3;
	/**
	 * 文件消息
	 */
	short PRIVATE_CHATTING = 4;
	short FILE_CHAT_RESULT = 5;
	short SHAKE = 6;
	//===============分割线===================================

	/**
	 * 消息推送命令
	 */
	short PUSHCHAT = 101;
	/**
	 * 好友掉线推送命令
	 */
	short OFFLINE = 102;



	
}
