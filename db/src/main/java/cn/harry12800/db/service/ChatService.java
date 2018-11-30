package cn.harry12800.db.service;

import cn.harry12800.common.module.chat.dto.MsgResponse;
import cn.harry12800.common.module.user.dto.PullMsgResponse;

/**
 * 聊天接口
 * @author harry12800
 *
 */
public interface ChatService {

	/**
	 * 群发消息
	 * @param userId
	 * @param content
	 */
	public void publicChat(long userId, String content);

	/**
	 * 私聊
	 * @param userId
	 * @param targetuserId
	 * @param content
	 */
	public MsgResponse privateChat(long userId, long targetUserId, String content);

	/**
	 * laiq
	 * @param userid
	 * @return
	 */
	public PullMsgResponse pullMsg(long userid);

}
