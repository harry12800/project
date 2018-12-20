package cn.harry12800.common.core.session;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.protobuf.GeneratedMessage;

import cn.harry12800.common.core.model.Response;
import cn.harry12800.common.core.serial.Serializer;

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
	public static boolean putSession(long userId, Session session) {
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
	public static Session removeSession(long userId) {
		return onlineSessions.remove(userId);
	}

	/**
	 * 发送消息[自定义协议]
	 * @param <T>
	 * @param userId
	 * @param message
	 */
	public static <T extends Serializer> void sendMessage(long userId, short module, short cmd, T message) {
		Session session = onlineSessions.get(userId);
		if (session != null && session.isConnected()) {
			Response response = new Response(module, cmd, message.getBytes());
			session.write(response);
		}
	}

	/**
	 * 发送消息[protoBuf协议]
	 * @param <T>
	 * @param userId
	 * @param message
	 */
	public static <T extends GeneratedMessage> void sendMessage(long userId, short module, short cmd, T message) {
		Session session = onlineSessions.get(userId);
		if (session != null && session.isConnected()) {
			Response response = new Response(module, cmd, message.toByteArray());
			session.write(response);
		}
	}

	/**
	 * 是否在线
	 * @param userId
	 * @return
	 */
	public static boolean isOnlineUser(long userId) {
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
