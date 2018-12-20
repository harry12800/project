package cn.harry12800.vchat.server.server;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cn.harry12800.common.core.packet.base.Packet;



/**
 * 会话管理者
 * @author harry12800
 *
 */
public class SessionManager {

	/**
	 * 在线会话
	 */
	private static final ConcurrentHashMap<Long, Session> onlineSessions = new ConcurrentHashMap<>();

	/**
	 * 加入
	 * @param userId
	 * @param channel
	 * @return
	 */
	public static boolean putSession(Long userId, Session session) {
		if (!onlineSessions.containsKey(userId)) {
			boolean success = onlineSessions.putIfAbsent(userId, session) == null ? true : false;
			return success;
		}
		return false;
	}

	/**
	 * 移除
	 * @param userId
	 */
	public static Session removeSession(Long userId) {
		return onlineSessions.remove(userId);
	}

	/**
	 * 发送消息[protoBuf协议]
	 * @param <T>
	 * @param userId
	 * @param message
	 */
	public static   void sendMessage(Long userId, Packet<?> message) {
		Session session = onlineSessions.get(userId);
		System.out.println(session);
		if (session != null && session.isConnected()) {
			session.write(message);
		}
	}

	/**
	 * 是否在线
	 * @param userId
	 * @return
	 */
	public static boolean isOnlineUser(Long userId) {
		return onlineSessions.containsKey(userId);
	}

	/**
	 * 获取所有在线用户
	 * @return
	 */
	public static Set<Long> getOnlineUsers() {
		return Collections.unmodifiableSet(onlineSessions.keySet());
	}
}
