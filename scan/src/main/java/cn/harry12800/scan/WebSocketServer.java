package cn.harry12800.scan;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import cn.harry12800.scan.http.MyResponse;
import cn.harry12800.scan.redis.JedisUtil;
import cn.harry12800.scan.util.JsonUtil;
import cn.harry12800.tools.StringUtils;
import redis.clients.jedis.Jedis;

//@ServerEndpoint("/websocket/{user}")
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

	public static ConcurrentHashMap<String, WebSocketServer> smaps = new ConcurrentHashMap<String, WebSocketServer>();
	public static ConcurrentHashMap<WebSocketServer, String> maps = new ConcurrentHashMap<WebSocketServer, String>();
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("onOpen:" + this);
		System.out.println("onOpen:" + session);
		System.out.println("onOpen:" + session.getId());
		this.session = session;
		webSocketSet.add(this); // 加入set中
		addOnlineCount(); // 在线数加1
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}
	// //连接打开时执行
	// @OnOpen
	// public void onOpen(@PathParam("user") String user, Session session) {
	// currentUser = user;
	// System.out.println("Connected ... " + session.getId());
	// }

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 从set中删除
		String string = maps.get(this);
		smaps.remove(string);
		maps.remove(this);
		subOnlineCount(); // 在线数减1
		System.err.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message
	 *            客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);
		System.out.println("onMessage:" + this);
		System.out.println("onMessage:" + session);
		System.out.println("onMessage:" + session.getId());
		try (Jedis jedis = JedisUtil.getJedisPool().getResource()) {
			MyResponse data = JsonUtil.string2Json(message, MyResponse.class);
			switch (data.code) {

			case 21: // 无身份来链接的
				System.out.println("无身份来链接的");
				String uuid = StringUtils.getUUID();
				smaps.put(uuid, this);
				maps.put(this, uuid);
				MyResponse r = MyResponse.newConnection();
				r.setContent(uuid);
				String msg = JsonUtil.object2String(r);
				try {
					sendMessage(msg);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;

			case 20:
				System.out.println(" 带身份来链接的");
				smaps.put(data.msg, this);
				maps.put(this, data.msg);
				r = MyResponse.newConnection();
				r.setContent(data.msg);
				msg = JsonUtil.object2String(r);
				try {
					sendMessage(msg);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			case 11:
				System.out.println("拉去消息");
				uuid = maps.get(this);
				System.out.println(uuid);
				List<String> lrange = jedis.lrange("letter:" + uuid + ":history", 0, -1);
				System.out.println(lrange.size());
				for (String string : lrange) {
					r = MyResponse.newConnection();
					r.setCode(10);
					r.setContent(string);
					msg = JsonUtil.object2String(r);
					try {
						sendMessage(msg);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				break;
			case 10:
				System.out.println("发送消息");
				Letter letter = JsonUtil.string2Json(data.content + "", Letter.class);
				String string = maps.get(this);
				letter.from = string;
				letter.time = new Date();
				System.out.println(letter);
				String object2String = JsonUtil.object2String(letter);
				jedis.rpush("letter:" + letter.from + ":history", object2String);
				jedis.rpush("letter:" + letter.to, object2String);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessageBody(Letter letter) {

		try (Jedis jedis = JedisUtil.getJedisPool().getResource();) {
			MyResponse r = MyResponse.newConnection();
			r.setCode(10);
			String object2String2 = JsonUtil.object2String(letter);
			r.setContent(object2String2);
			String object2String = JsonUtil.object2String(r);
			try {
				sendMessage(object2String);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			jedis.rpush("letter:" + letter.from + ":history", object2String2);
			jedis.rpush("letter:" + letter.to + ":history", object2String2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class Letter {
		public String from;
		public String to;
		public Date time;
		public String data;

		@Override
		public String toString() {
			return "Letter [from=" + from + ", to=" + to + ", time=" + time + ", data=" + data + "]";
		}

	}

	/**
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.err.println("发生错误");
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	/**
	 * 群发自定义消息
	 */
	public static void sendInfo(String message) throws IOException {
		System.out.println(message);
		for (WebSocketServer item : webSocketSet) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				continue;
			}
		}
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocketServer.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocketServer.onlineCount--;
	}
}
