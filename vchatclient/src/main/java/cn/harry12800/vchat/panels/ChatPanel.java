package cn.harry12800.vchat.panels;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;

import org.apache.log4j.Logger;

import cn.harry12800.common.core.model.Request;
import cn.harry12800.common.module.ChatCmd;
import cn.harry12800.common.module.ModuleId;
import cn.harry12800.common.module.chat.dto.FileChatRequest;
import cn.harry12800.common.module.chat.dto.MsgResponse;
import cn.harry12800.common.module.chat.dto.PrivateChatRequest;
import cn.harry12800.j2se.component.rc.RCBorder;
import cn.harry12800.j2se.module.tray.ETrayType;
import cn.harry12800.j2se.module.tray.TrayInfo;
import cn.harry12800.j2se.module.tray.TrayListener;
import cn.harry12800.j2se.module.tray.TrayUtil;
import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.Clip;
import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.StringUtils;
import cn.harry12800.vchat.adapter.message.BaseMessageViewHolder;
import cn.harry12800.vchat.adapter.message.MessageAdapter;
import cn.harry12800.vchat.adapter.message.MessageAttachmentViewHolder;
import cn.harry12800.vchat.adapter.message.MessageRightAttachmentViewHolder;
import cn.harry12800.vchat.adapter.message.MessageRightImageViewHolder;
import cn.harry12800.vchat.app.App;
import cn.harry12800.vchat.app.Launcher;
import cn.harry12800.vchat.app.config.Contants;
import cn.harry12800.vchat.app.websocket.PullWebInfo.Letter;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.components.RCListView;
import cn.harry12800.vchat.components.message.FileEditorThumbnail;
import cn.harry12800.vchat.components.message.RemindUserPopup;
import cn.harry12800.vchat.db.model.CurrentUser;
import cn.harry12800.vchat.db.model.FileAttachment;
import cn.harry12800.vchat.db.model.ImageAttachment;
import cn.harry12800.vchat.db.model.Message;
import cn.harry12800.vchat.db.model.Room;
import cn.harry12800.vchat.db.service.FileAttachmentService;
import cn.harry12800.vchat.db.service.ImageAttachmentService;
import cn.harry12800.vchat.db.service.MessageService;
import cn.harry12800.vchat.db.service.RoomService;
import cn.harry12800.vchat.entity.FileAttachmentItem;
import cn.harry12800.vchat.entity.ImageAttachmentItem;
import cn.harry12800.vchat.entity.MessageItem;
import cn.harry12800.vchat.frames.MainFrame;
import cn.harry12800.vchat.frames.components.JsonUtil;
import cn.harry12800.vchat.helper.MessageViewHolderCacheHelper;
import cn.harry12800.vchat.listener.ExpressionListener;
import cn.harry12800.vchat.tasks.DownloadTask;
import cn.harry12800.vchat.tasks.HttpResponseListener;
import cn.harry12800.vchat.tasks.UploadTaskCallback;
import cn.harry12800.vchat.utils.AvatarUtil;
import cn.harry12800.vchat.utils.ClipboardUtil;
import cn.harry12800.vchat.utils.FileCache;
import cn.harry12800.vchat.utils.HttpUtil;
import cn.harry12800.vchat.utils.MimeTypeUtil;

/**
 * 右侧聊天面板
 * <p>
 * Created by harry12800 on 17-5-30.
 */
@SuppressWarnings("serial")
public class ChatPanel extends ParentAvailablePanel {
	private MessagePanel messagePanel;
	private MessageEditorPanel messageEditorPanel;
	private static ChatPanel context;
	public static String CHAT_ROOM_OPEN_ID = "";

	// APP启动时，已加载过远程未读消息的Rooms
	private static List<String> remoteHistoryLoadedRooms = new ArrayList<>();

	private List<MessageItem> messageItems = new ArrayList<>();
	private MessageAdapter adapter;
	private CurrentUser currentUser;
	private Room room; // 当前房间

	private long firstMessageTimestamp = 0L; // 如果是从消息搜索列表中进入房间的，这个属性不为0

	// 房间的用户
	public List<String> roomMembers = new ArrayList<>();

	private MessageService messageService = Launcher.messageService;
	private RoomService roomService = Launcher.roomService;
	private ImageAttachmentService imageAttachmentService = Launcher.imageAttachmentService;
	private FileAttachmentService fileAttachmentService = Launcher.fileAttachmentService;
	public static List<String> uploadingOrDownloadingFiles = new ArrayList<>();
	private FileCache fileCache;

	// 每次加载的消息条数
	private static final int PAGE_LENGTH = 10;

	String roomId;

	private Logger logger = Logger.getLogger(this.getClass());

	private RemindUserPopup remindUserPopup = new RemindUserPopup();
	private MessageViewHolderCacheHelper messageViewHolderCacheHelper;

	private static final int MAX_SHARE_ATTACHMENT_UPLOAD_COUNT = 1024;

	private Queue<String> shareAttachmentUploadQueue = new ArrayDeque<>(MAX_SHARE_ATTACHMENT_UPLOAD_COUNT);

	public ChatPanel(JPanel parent) {
		super(parent);
		context = this;
		currentUser = Launcher.currentUser;
		messageViewHolderCacheHelper = new MessageViewHolderCacheHelper();

		initComponents();
		initView();
		setListeners();

		initData();

		fileCache = new FileCache();
	}

	private void initComponents() {
		messagePanel = new MessagePanel(this);
		messagePanel.setBorder(new RCBorder(RCBorder.BOTTOM, Colors.LIGHT_GRAY));
		adapter = new MessageAdapter(messageItems, messagePanel.getMessageListView(), messageViewHolderCacheHelper);
		messagePanel.getMessageListView().setAdapter(adapter);

		messageEditorPanel = new MessageEditorPanel(this);
		messageEditorPanel.setPreferredSize(new Dimension(MainFrame.DEFAULT_WIDTH, MainFrame.DEFAULT_WIDTH / 4));
	}

	private void initView() {
		this.setLayout(new GridBagLayout());
		add(messagePanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 4));
		add(messageEditorPanel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1));

		if (roomId == null) {
			messagePanel.setVisible(false);
			messageEditorPanel.setVisible(false);
		}
	}

	public static ChatPanel getContext() {
		return context;
	}

	private void initData() {
		if (roomId != null) {
			// 如果是从搜索列表进入房间的
			if (firstMessageTimestamp != 0L) {
				loadMessageWithEarliestTime(firstMessageTimestamp);
			} else {
				// 加载本地消息
				loadLocalHistory(); // 初次打开房间时加载历史消息

				// TODO：从服务器获取本地最后一条消息之后的消，并追回到消息列表
			}
		}
	}

	private void setListeners() {
		messagePanel.getMessageListView().setScrollToTopListener(new RCListView.ScrollToTopListener() {
			@Override
			public void onScrollToTop() {
				// 当滚动到顶部时，继续拿前面的消息
				if (roomId != null) {
					List<Message> messages = messageService.findOffset(roomId, messageItems.size(), PAGE_LENGTH);

					if (messages.size() > 0) {
						for (int i = messages.size() - 1; i >= 0; i--) {
							MessageItem item = new MessageItem(messages.get(i), currentUser.getUserId());
							messageItems.add(0, item);
						}
					}
					// 如果本地没有拿到消息，则从服务器拿消息
					else {
						// TODO: 从服务器获取更多消息
					}

					messagePanel.getMessageListView().notifyItemRangeInserted(0, messages.size());
				}
			}
		});

		JTextPane editor = messageEditorPanel.getEditor();
		Document document = editor.getDocument();

		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// CTRL + 回车换行
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						document.insertString(editor.getCaretPosition(), "\n", null);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				}

				// 回车发送消息
				else if (!e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage();
					e.consume();
				}

				// 输入@，弹出选择用户菜单
				else if (e.getKeyChar() == '@') {
					Point point = editor.getCaret().getMagicCaretPosition();
					point = point == null ? new Point(10, 0) : point;
					List<String> users = exceptSelfFromRoomMember();
					users.add(0, "all");
					remindUserPopup.setUsers(users);
					remindUserPopup.show((Component) e.getSource(), point.x, point.y, roomId);
				}

				// 输入退格键，删除最后一个@user
				else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					String str = editor.getText();
					if (str.matches(".*@\\w+\\s")) {
						try {
							int startPos = str.lastIndexOf("@");
							String rmStr = str.substring(startPos);
							editor.getDocument().remove(startPos + 1, rmStr.length() - 1);
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
					}
				}
			}

		});

		remindUserPopup.setSelectedCallBack(new RemindUserPopup.UserSelectedCallBack() {
			@Override
			public void onSelected(String username) {
				JTextPane editor = messageEditorPanel.getEditor();
				editor.replaceSelection(username + " ");
			}
		});

		// 发送按钮
		messageEditorPanel.getSendButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});

		// 上传文件按钮
		messageEditorPanel.getUploadFileLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("请选择上传文件或图片");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

				fileChooser.showDialog(MainFrame.getContext(), "上传");
				File selectedFile = fileChooser.getSelectedFile();
				if (selectedFile != null) {
					String path = selectedFile.getAbsolutePath();
					sendFileMessage(path);
					showSendingMessage();
				}

				super.mouseClicked(e);
			}
		});

		// 插入表情
		messageEditorPanel.setExpressionListener(new ExpressionListener() {
			@Override
			public void onSelected(String code) {
				editor.replaceSelection(code);
			}
		});
	}

	/**
	 * 解析输入框中的内容并发送消息
	 */
	private void sendMessage() {
		List<Object> inputDatas = parseEditorInput();
		boolean isImageOrFile = false;
		for (Object data : inputDatas) {
			if (data instanceof String && !data.equals("\n")) {
				sendTextMessage(null, (String) data);
			} else if (data instanceof JLabel) {
				isImageOrFile = true;
				JLabel label = (JLabel) data;
				ImageIcon icon = (ImageIcon) label.getIcon();
				String path = icon.getDescription();
				if (path != null && !path.isEmpty()) {
					/*
					 * sendFileMessage(path); showSendingMessage();
					 */

					shareAttachmentUploadQueue.add(path);
				}
			} else if (data instanceof FileEditorThumbnail) {
				isImageOrFile = true;

				FileEditorThumbnail component = (FileEditorThumbnail) data;
				System.out.println(component.getPath());
				shareAttachmentUploadQueue.add(component.getPath());

			}
		}

		if (isImageOrFile) {
			// 先上传第一个图片/文件
			dequeueAndUpload();
		}

		messageEditorPanel.getEditor().setText("");
	}

	/**
	 * 解析输入框中的输入数据
	 *
	 * @return
	 */
	private List<Object> parseEditorInput() {
		List<Object> inputData = new ArrayList<>();

		Document doc = messageEditorPanel.getEditor().getDocument();
		int count = doc.getRootElements()[0].getElementCount();

		// 是否是纯文本，如果发现有图片或附件，则不是纯文本
		boolean pureText = true;

		for (int i = 0; i < count; i++) {
			Element root = doc.getRootElements()[0].getElement(i);

			int elemCount = root.getElementCount();

			for (int j = 0; j < elemCount; j++) {
				try {
					Element elem = root.getElement(j);
					String elemName = elem.getName();
					switch (elemName) {
					case "content": {
						int start = elem.getStartOffset();
						int end = elem.getEndOffset();
						String text = doc.getText(elem.getStartOffset(), end - start);
						inputData.add(text);
						break;
					}
					case "component": {
						pureText = false;
						Component component = StyleConstants.getComponent(elem.getAttributes());
						inputData.add(component);
						break;
					}
					case "icon": {
						pureText = false;

						ImageIcon icon = (ImageIcon) StyleConstants.getIcon(elem.getAttributes());
						inputData.add(icon);
						break;
					}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// 如果是纯文本，直接返回整个文本，否则如果出消息中有换行符\n出现，那么每一行都会被解析成一句话，会造成一条消息被分散成多个消息发送
		if (pureText) {
			inputData.clear();
			inputData.add(messageEditorPanel.getEditor().getText());
		}

		return inputData;
	}

	/**
	 * 从待上传附件队列中出队一个，并上传
	 */
	public synchronized void dequeueAndUpload() {
		String path = shareAttachmentUploadQueue.poll();

		if (path != null) {
			System.out.println("上传文件：" + path);

			sendFileMessage(path);
			showSendingMessage();
		}
	}

	/**
	 * @return
	 */
	private List<String> exceptSelfFromRoomMember() {
		List<String> users = new ArrayList<>();
		users.addAll(roomMembers);
		users.remove(currentUser.getUsername());
		return users;
	}

	/**
	 * 进入指定房间
	 *
	 * @param roomId
	 * @param firstMessageTimestamp
	 */
	public void enterRoom(String roomId, long firstMessageTimestamp) {
		if (roomId == null || roomId.isEmpty()) {
			return;
		}
		this.firstMessageTimestamp = firstMessageTimestamp;
		this.roomId = roomId;
		CHAT_ROOM_OPEN_ID = roomId;
		this.room = roomService.findById(roomId);

		// 更新消息列表
		this.notifyDataSetChanged();
		// 更新房间标题，尤其是成员数
		updateRoomTitle();
		//		RoomMembersPanel.getContext().setRoomId(roomId);

		messageEditorPanel.getEditor().setText("");
		updateUnreadCount(0);

	}

	/**
	 * 进入指定房间
	 *
	 * @param roomId
	 */
	public void enterRoom(String roomId) {
		enterRoom(roomId, 0L);
	}

	/**
	 * 更新房间标题
	 */
	public void updateRoomTitle() {
		String title = room.getName();
		if (!room.getType().equals("d")) {
			// 加载本地群成员
			loadLocalRoomMembers();

			title += " (" + (roomMembers.size()) + ")";
		}

		// 更新房间标题
		TitlePanel.getContext().updateRoomTitle(title);
	}

	/**
	 * 加载指定 firstMessageTimestamp 以后的消息
	 *
	 * @param firstMessageTimestamp
	 */
	private void loadMessageWithEarliestTime(long firstMessageTimestamp) {
		List<Message> messages = messageService.findBetween(roomId, firstMessageTimestamp, System.currentTimeMillis());
		if (messages.size() > 0) {
			for (Message message : messages) {
				if (!message.isDeleted()) {
					MessageItem item = new MessageItem(message, currentUser.getUserId());
					this.messageItems.add(item);
				}
			}

			messagePanel.getMessageListView().notifyDataSetChanged(false);
			messagePanel.getMessageListView().setAutoScrollToTop();
		}

		// TODO: 从服务器获取本地最后一条消息之后的消息，并追回到消息列表
	}

	/**
	 * 加载本地历史消息
	 */
	private void loadLocalHistory() {
		List<Message> messages = messageService.findByPage(roomId, messageItems.size(), PAGE_LENGTH);

		if (messages.size() > 0) {
			for (Message message : messages) {
				MessageItem item = new MessageItem(message, currentUser.getUserId());
				messageItems.add(item);
			}
		}
		// 如果本地没有拿到消息，则从服务器拿消息
		else {
			// TODO: 从服务器拿消息
		}

		messagePanel.getMessageListView().notifyDataSetChanged(false);

		messagePanel.getMessageListView().setAutoScrollToBottom();
	}

	/**
	 * 更新数据库中的房间未读消息数，以及房间列表中的未读消息数
	 *
	 * @param count
	 */
	public void updateUnreadCount(int count) {
		room = roomService.findById(roomId);
		if (count < 0) {
			System.out.println(count);
		} else if (count == 0) {
			TrayUtil.getTray().popTrayInfo(roomId, ETrayType.CHAT);
		}
		room.setUnreadCount(count);
		room.setTotalReadCount(room.getMsgSum());
		roomService.update(room);

		// 通知UI更新未读消息数
		RoomsPanel.getContext().updateRoomItem(room.getRoomId());
	}

	/**
	 * 更新列表中的未读消息及消息总数
	 */
	public void updateTotalAndUnreadCount(int totalAdded, int unread) {
		if (unread < 0) {
			System.out.println(unread);
		}
		room.setUnreadCount(unread);
		room.setMsgSum(room.getMsgSum() + totalAdded);
		roomService.update(room);
	}

	/**
	 * 通知数据改变，需要重绘整个列表
	 */
	public void notifyDataSetChanged() {
		messagePanel.getMessageListView().setVisible(false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 重置ViewHolder缓存
				messageViewHolderCacheHelper.reset();

				messageItems.clear();
				initData();
				messagePanel.setVisible(true);
				messageEditorPanel.setVisible(true);
				messagePanel.getMessageListView().setVisible(true);

				TitlePanel.getContext().hideRoomMembersPanel();
			}
		}).start();
	}

	/**
	 * 添加一条消息到最后，或者更新已有消息
	 */
	public void addOrUpdateMessageItem() {
		Message message = messageService.findLastMessage(roomId);
		if (message == null || message.isDeleted()) {
			return;
		}

		// 已有消息更新状态
		int pos = findMessageItemPositionInViewReverse(message.getId());
		if (pos > -1) {
			messageItems.get(pos).setUpdatedAt(message.getTimestamp());
			messagePanel.getMessageListView().notifyItemChanged(pos);
			return;
		}

		// 插入新的消息
		MessageItem messageItem = new MessageItem(message, currentUser.getUserId());
		this.messageItems.add(messageItem);
		messagePanel.getMessageListView().notifyItemInserted(messageItems.size() - 1, false);

		// 只有当滚动条在最底部最，新消到来后才自动滚动到底部
		JScrollBar scrollBar = messagePanel.getMessageListView().getVerticalScrollBar();
		if (scrollBar.getValue() == (scrollBar.getModel().getMaximum() - scrollBar.getModel().getExtent())) {
			messagePanel.getMessageListView().setAutoScrollToBottom();
		}
	}

	/**
	 * 添加一条消息到消息列表最后
	 *
	 * @param item
	 */
	private void addMessageItemToEnd(MessageItem item) {
		this.messageItems.add(item);
		messagePanel.getMessageListView().notifyItemInserted(messageItems.size() - 1, true);
		messagePanel.getMessageListView().setAutoScrollToBottom();

	}

	/**
	 * 发送文本消息
	 * <p>
	 * 如果messageId不为null, 则认为重发该消息，否则发送一条新的消息
	 */
	public void sendTextMessage(String messageId, String content) {
		Message dbMessage = null;
		if (messageId == null) {
			MessageItem item = new MessageItem();
			if (content == null || content.equals("")) {
				return;
			}

			messageId = randomMessageId();
			item.setMessageContent(content);
			item.setTimestamp(System.currentTimeMillis());
			item.setSenderId(currentUser.getUserId());
			item.setSenderUsername(currentUser.getUsername());
			item.setId(messageId);
			item.setMessageType(MessageItem.RIGHT_TEXT);

			dbMessage = new Message();
			dbMessage.setId(messageId);
			dbMessage.setMessageContent(content);
			dbMessage.setRoomId(roomId);
			dbMessage.setSenderId(currentUser.getUserId());
			dbMessage.setSenderUsername(currentUser.getUsername());
			dbMessage.setTimestamp(item.getTimestamp());
			dbMessage.setNeedToResend(false);

			addMessageItemToEnd(item);

			messageService.insert(dbMessage);

		}
		// 已有消息重发
		else {
			Message msg = messageService.findById(messageId);

			msg.setTimestamp(System.currentTimeMillis());
			msg.setUpdatedAt(0);
			msg.setNeedToResend(false);
			messageService.insertOrUpdate(msg);

			content = msg.getMessageContent();

			int pos = findMessageItemPositionInViewReverse(msg.getId());
			if (pos > -1) {
				messageItems.get(pos).setNeedToResend(false);
				messageItems.get(pos).setUpdatedAt(0);
				messageItems.get(pos).setTimestamp(System.currentTimeMillis());
				messagePanel.getMessageListView().notifyItemChanged(pos);
			}
		}

		//		content = StringEscapeUtils.escapeJava(content);

		// TODO: 发送消息到服务器
		// 发送
		if(roomId.length()!=32) {
			sendToServer(content);
		}else {
			sendToWebSocketServer(content);
		}
		
		// TODO：完善发送消息回调：
		boolean sentSuccess = true;
		if (sentSuccess) {
			// 如果发送成功，收到服务器响应后更新消息的updatedAt属性，该属性如果小于0，说明消息发送失败
			dbMessage.setUpdatedAt(System.currentTimeMillis());
			messageService.insertOrUpdate(dbMessage);
			// 更新消息列表
			addOrUpdateMessageItem();

			// 更新房间信息以及房间列表
			Room room = roomService.findById(dbMessage.getRoomId());
			room.setLastMessage(dbMessage.getMessageContent());
			room.setLastChatAt(dbMessage.getTimestamp());
			room.setMsgSum(room.getMsgSum() + 1);
			room.setUnreadCount(0);
			room.setTotalReadCount(room.getMsgSum());
			roomService.update(room);
			RoomsPanel.getContext().updateRoomItem(dbMessage.getRoomId());
			updateUnreadCount(0);
		}

		// 10秒后如果发送不成功，则显示重发按钮
		/*
		 * MessageResendTask task = new MessageResendTask(); task.setListener(new
		 * ResendTaskCallback(10000) {
		 * 
		 * @Override public void onNeedResend(String messageId) { Message msg =
		 * messageService.findById(messageId); if (msg.getUpdatedAt() == 0) { // 更新消息列表
		 * 
		 * int pos = findMessageItemPositionInViewReverse(messageId); if (pos > -1) {
		 * messageItems.get(pos).setNeedToResend(true); msg.setNeedToResend(true);
		 * messageService.update(msg);
		 * messagePanel.getMessageListView().notifyItemChanged(pos); }
		 * 
		 * 
		 * // 更新房间列表 // 注意这里不能用类的成员room，因为可能已经离开了原来的房间 Room room =
		 * roomService.findById(msg.getRoomId()); room.setLastMessage("[有消息发送失败]");
		 * room.setLastChatAt(msg.getTimestamp()); roomService.update(room);
		 * RoomsPanel.getContext().updateRoomItem(msg.getRoomId()); } } });
		 * task.execute(messageId);
		 */
	}

	private void sendToWebSocketServer(String content) {
		String path2 = Contants.getPath(Contants.sendTextWebsocketPath);
		Letter letter = new Letter();
		letter.from = "harry12800";
		letter.to = roomId;
		letter.time = new Date();
		letter.data = content;
		Map<String, String> headers = new HashMap<>(0);
		try {
			String post = HttpUtil.postJson(path2, headers, JsonUtil.object2String(letter));
			System.out.println(post);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void sendToServer(String content) {
		try {
			PrivateChatRequest request = new PrivateChatRequest();
			request.setContext(content);
			request.setTargetUserId(Launcher.getIdByUserId(roomId));
			// 构建请求
			Request req = Request.valueOf(ModuleId.CHAT, ChatCmd.PRIVATE_CHAT, request.getBytes());
			Launcher.client.sendRequest(req);
		} catch (Exception e) {

		}
	}

	private void showSendingMessage() {
		Room room = roomService.findById(roomId);
		room.setLastMessage("[发送中...]");
		roomService.update(room);
		RoomsPanel.getContext().updateRoomItem(roomId);
	}

	/**
	 * 倒序查找指定的消息在消息列表中的位置中的位置
	 *
	 * @param messageId
	 * @return 查找成功，返回该消息在消息列表中的位置，否则返回-1
	 */
	private int findMessageItemPositionInViewReverse(String messageId) {
		for (int i = messageItems.size() - 1; i >= 0; i--) {
			// 找到消息列表中对应的消息
			if (messageItems.get(i).getId().equals(messageId)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * 随机生成MessageId
	 *
	 * @return
	 */
	private String randomMessageId() {
		String raw = UUID.randomUUID().toString().replace("-", "");
		return raw;
	}

	/**
	 * 发送文件消息
	 *
	 * @param path
	 */
	private void sendFileMessage(String path) {
		// TODO: 通知服务器要开始上传文件

		notifyStartUploadFile(path, randomMessageId());
		// TODO: 收到服务器响应，调用下面方法开始上传文件
		// WebSocketClient.getContext().sendFileMessage(roomId, path);
	}

	/**
	 * 重发文件消息
	 *
	 * @param messageId
	 * @param type
	 */
	public void resendFileMessage(String messageId, String type) {
		Message dbMessage = messageService.findById(messageId);
		String path = null;

		if (type.equals("file")) {
			if (dbMessage.getFileAttachmentId() != null) {
				path = fileAttachmentService.findById(dbMessage.getFileAttachmentId()).getLink();
			} else {
				path = null;
			}
		} else {
			if (dbMessage.getImageAttachmentId() != null) {
				path = imageAttachmentService.findById(dbMessage.getImageAttachmentId()).getImageUrl();
			} else {
				path = null;

			}
		}

		if (path != null) {
			int index = findMessageItemPositionInViewReverse(messageId);

			if (index > -1) {
				messageItems.remove(index);
				messagePanel.getMessageListView().notifyItemRemoved(index);
				messageService.delete(dbMessage.getId());
			}

			sendFileMessage(path);
			showSendingMessage();
		}
	}

	/**
	 * 通知开始上传文件
	 *
	 * @param uploadFilename
	 * @param fileId
	 */
	public void notifyStartUploadFile(String uploadFilename, String fileId) {
		uploadFile(uploadFilename, fileId);
		uploadingOrDownloadingFiles.add(fileId);
	}

	/**
	 * 上传文件
	 *
	 * @param uploadFilename
	 */
	private void uploadFile(String uploadFilename, String fileId) {
		final MessageItem item = new MessageItem();
		String type = MimeTypeUtil.getMime(uploadFilename.substring(uploadFilename.lastIndexOf(".")));
		final boolean isImage = type.startsWith("image/");

		// 发送的是图片
		int[] bounds;
		String name = uploadFilename.substring(uploadFilename.lastIndexOf(File.separator) + 1); // 文件名

		FileAttachment fileAttachment = null;
		ImageAttachment imageAttachment = null;
		Message dbMessage = new Message();
		dbMessage.setProgress(-1);

		if (isImage) {
			bounds = getImageSize(uploadFilename);
			imageAttachment = new ImageAttachment();
			imageAttachment.setId(fileId);
			imageAttachment.setWidth(bounds[0]);
			imageAttachment.setHeight(bounds[1]);
			imageAttachment.setImageUrl(uploadFilename);
			imageAttachment.setTitle(name);
			item.setImageAttachment(new ImageAttachmentItem(imageAttachment));
			dbMessage.setImageAttachmentId(imageAttachment.getId());
			imageAttachmentService.insertOrUpdate(imageAttachment);

			item.setMessageType(MessageItem.RIGHT_IMAGE);
		} else {

			fileAttachment = new FileAttachment();
			fileAttachment.setId(fileId);
			System.out.println(File.separator);
			fileAttachment.setLink(uploadFilename);
			fileAttachment.setTitle(name);
			item.setFileAttachment(new FileAttachmentItem(fileAttachment));
			dbMessage.setFileAttachmentId(fileAttachment.getId());
			fileAttachmentService.insertOrUpdate(fileAttachment);

			item.setMessageType(MessageItem.RIGHT_ATTACHMENT);
		}

		final String messageId = randomMessageId();
		item.setMessageContent(name);
		item.setTimestamp(System.currentTimeMillis());
		item.setSenderId(currentUser.getUserId());
		item.setSenderUsername(currentUser.getUsername());
		item.setId(messageId);
		item.setProgress(0);

		dbMessage.setId(messageId);
		dbMessage.setMessageContent(name);
		dbMessage.setRoomId(roomId);
		dbMessage.setSenderId(currentUser.getUserId());
		dbMessage.setSenderUsername(currentUser.getUsername());
		dbMessage.setTimestamp(item.getTimestamp());
		dbMessage.setUpdatedAt(item.getTimestamp());

		addMessageItemToEnd(item);

		messageService.insertOrUpdate(dbMessage);

		File file = new File(uploadFilename);
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null, "文件不存在", "上传失败", JOptionPane.ERROR_MESSAGE);
		} else {
			// 分块上传
			final List<byte[]> dataParts = cuttingFile(file);
			final int[] index = { 1 };

			// TODO：向服务器上传文件
			final short[] uploadedBlockCount = { 0 };
			final long[] len = { 0 };

			UploadTaskCallback callback = new UploadTaskCallback() {
				@Override
				public void onTaskSuccess() {
					// 当收到上一个分块的响应后，才能开始上传下一个分块，否则容易造成分块接收顺序错乱
					uploadedBlockCount[0]++;
					if (uploadedBlockCount[0] < dataParts.size()) {
						len[0] = len[0] + dataParts.get(uploadedBlockCount[0] - 1).length;
						sendDataPart(file.getName(), len[0], messageId, uploadedBlockCount[0], dataParts, this);
					}

					int progress = (int) ((index[0] * 1.0f / dataParts.size()) * 100);
					index[0]++;

					// 上传完成
					if (progress == 100) {
						uploadingOrDownloadingFiles.remove(fileId);

						Room room = roomService.findById(roomId);
						room.setLastMessage(dbMessage.getMessageContent());
						roomService.update(room);
						RoomsPanel.getContext().updateRoomItem(roomId);

						if (uploadFilename.startsWith(ClipboardUtil.CLIPBOARD_TEMP_DIR)) {
							File file = new File(uploadFilename);
							file.delete();
						}
					}

					for (int i = messageItems.size() - 1; i >= 0; i--) {
						if (messageItems.get(i).getId().equals(item.getId())) {
							messageItems.get(i).setProgress(progress);
							messageService.updateProgress(messageItems.get(i).getId(), progress);

							BaseMessageViewHolder viewHolder = getViewHolderByPosition(i);
							if (viewHolder != null) {
								if (isImage) {
									MessageRightImageViewHolder holder = (MessageRightImageViewHolder) viewHolder;
									if (progress >= 100) {
										holder.sendingProgress.setVisible(false);
									}
								} else {
									MessageRightAttachmentViewHolder holder = (MessageRightAttachmentViewHolder) viewHolder;

									// 隐藏"等待上传"，并显示进度条
									holder.sizeLabel.setVisible(false);
									holder.progressBar.setVisible(true);
									holder.progressBar.setValue(progress);

									if (progress >= 100) {
										holder.progressBar.setVisible(false);
										holder.sizeLabel.setVisible(true);
										holder.sizeLabel.setText(fileCache.fileSizeString(uploadFilename));
									}
								}

							}
							break;
						}
					}

					logger.debug("file uploading, progress = " + progress + "%");
				}

				@Override
				public void onTaskError() {
				}
			};

			sendDataPart(file.getName(), len[0], messageId, (short) 0, dataParts, callback);
		}
	}

	/**
	 * 发送块数据
	 * @param name  文件的名字
	 * @param position 文件块的起始位置
	 * @param messageId  消息id
	 * @param partIndex  第几部分
	 * @param dataParts 字节块数据
	 * @param callback  回调
	 */
	private void sendDataPart(String name, long position, String messageId, short partIndex, List<byte[]> dataParts, UploadTaskCallback callback) {
		// TODO： 发送第 partIndex 个分块到服务器
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("发送第" + partIndex + "个分块，共" + dataParts.size() + "个分块");
				try {
					FileChatRequest request = new FileChatRequest();
					request.setData(dataParts.get(partIndex));
					request.setSenderUserId(currentUser.getUserId());
					request.setIndex(partIndex);
					request.setMessageId(messageId);
					request.setPosition(position);
					request.setTargetUserId(roomId);
					request.setTotal((short) dataParts.size());
					request.setName(name);
					Request req = Request.valueOf(ModuleId.CHAT, ChatCmd.FILE_CHAT, request.getBytes());
					Launcher.client.sendRequest(req);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				callback.onTaskSuccess();
			}
		}).start();

	}

	/**
	 * 获取图片的宽高
	 *
	 * @param file
	 * @return
	 */
	private int[] getImageSize(String file) {
		try {
			BufferedImage image = ImageIO.read(new File(file));
			int width = image.getWidth();
			int height = image.getHeight();
			return new int[] { width, height };
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new int[] { 0, 0 };
	}

	/**
	 * 分割大文件，分块上传
	 *
	 * @param file
	 * @return
	 */
	private static List<byte[]> cuttingFile(File file) {
		long size = file.length();

		int partSize = 512000;
		// int partSize = 4140;
		int blockCount;
		blockCount = (int) (size % partSize == 0 ? size / partSize : size / partSize + 1);
		List<byte[]> dataParts = new ArrayList<>(blockCount);
		try (FileInputStream inputStream = new FileInputStream(file)) {
			byte[] buffer = new byte[partSize];
			int len;
			while ((len = inputStream.read(buffer)) > -1) {
				byte[] dataPart = Arrays.copyOf(buffer, len);
				dataParts.add(dataPart);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataParts;
	}

	private BaseMessageViewHolder getViewHolderByPosition(int position) {
		if (position < 0) {
			return null;
		}
		try {
			return (BaseMessageViewHolder) messagePanel.getMessageListView().getItem(position);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 打开文件，如果文件不存在，则下载
	 *
	 * @param messageId
	 */
	public void downloadOrOpenFile(String messageId) {
		Message message = messageService.findById(messageId);
		FileAttachment fileAttachment;
		if (message == null) {
			// 如果没有messageId对应的message, 尝试寻找messageId对应的file
			// attachment，因为在自己上传文件时，此时是以fileId作为临时的messageId
			fileAttachment = fileAttachmentService.findById(messageId);
		} else {
			fileAttachment = fileAttachmentService.findById(message.getFileAttachmentId());
		}

		if (fileAttachment == null) {
			JOptionPane.showMessageDialog(null, "无效的附件消息", "消息无效", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String filepath = fileCache.tryGetFileCache(fileAttachment.getId(), fileAttachment.getTitle());
		if (filepath == null) {
			// 服务器上的文件
			if (fileAttachment.getLink().startsWith("/file-upload")) {
				downloadFile(fileAttachment, messageId);
			}
			// 本地的文件
			else {
				openFileWithDefaultApplication(fileAttachment.getLink());
			}
		} else {
			openFileWithDefaultApplication(filepath);
		}
	}

	/**
	 * 下载文件
	 *
	 * @param fileAttachment
	 * @param messageId
	 */
	private void downloadFile(FileAttachment fileAttachment, String messageId) {
		final DownloadTask task = new DownloadTask(new HttpUtil.ProgressListener() {
			@Override
			public void onProgress(int progress) {
				int pos = findMessageItemPositionInViewReverse(messageId);
				MessageAttachmentViewHolder holder = (MessageAttachmentViewHolder) getViewHolderByPosition(pos);

				logger.debug("文件下载进度：" + progress);
				if (pos < 0 || holder == null) {
					return;
				}

				if (progress >= 0 && progress < 100) {
					if (holder.sizeLabel.isVisible()) {
						holder.sizeLabel.setVisible(false);
					}
					if (!holder.progressBar.isVisible()) {
						holder.progressBar.setVisible(true);
					}

					holder.progressBar.setValue(progress);
				} else if (progress >= 100) {
					holder.progressBar.setVisible(false);
					holder.sizeLabel.setVisible(true);
				}
			}
		});

		task.setListener(new HttpResponseListener<byte[]>() {
			@Override
			public void onSuccess(byte[] data) {
				// System.out.println(data);
				String path = fileCache.cacheFile(fileAttachment.getId(), fileAttachment.getTitle(), data);

				int pos = findMessageItemPositionInViewReverse(messageId);
				MessageAttachmentViewHolder holder = (MessageAttachmentViewHolder) getViewHolderByPosition(pos);

				if (pos < 0 || holder == null) {
					return;
				}
				if (path == null) {
					holder.sizeLabel.setVisible(true);
					holder.sizeLabel.setText("文件获取失败");
					holder.progressBar.setVisible(false);
				} else {
					holder.sizeLabel.setVisible(true);
					System.out.println("文件已缓存在 " + path);
					holder.sizeLabel.setText(fileCache.fileSizeString(path));
				}
			}

			@Override
			public void onFailed() {
				int pos = findMessageItemPositionInViewReverse(messageId);
				MessageAttachmentViewHolder holder = (MessageAttachmentViewHolder) getViewHolderByPosition(pos);
				holder.sizeLabel.setVisible(true);
				holder.sizeLabel.setText("文件获取失败");
				holder.progressBar.setVisible(false);
			}
		});

		String url = Launcher.HOSTNAME + fileAttachment.getLink() + "?rc_uid=" + currentUser.getUserId() + "&rc_token="
				+ currentUser.getAuthToken();
		task.execute(url);
	}

	/**
	 * 使用默认程序打开文件
	 *
	 * @param path
	 */
	private void openFileWithDefaultApplication(String path) {
		try {
			Desktop.getDesktop().open(new File(path));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "文件打开失败，没有找到关联的应用程序", "打开失败", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		} catch (IllegalArgumentException e2) {
			JOptionPane.showMessageDialog(null, "文件不存在，可能已被删除", "打开失败", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 加载本地房间用户
	 */
	public void loadLocalRoomMembers() {
		roomMembers.clear();
		String members = room.getMember();

		if (members != null) {
			String[] userArr = members.split(",");
			for (int i = 0; i < userArr.length; i++) {
				if (!roomMembers.contains(userArr[i])) {
					roomMembers.add(userArr[i]);
				}
			}
		}
	}

	/**
	 * 删除消息
	 *
	 * @param messageId
	 */
	public void deleteMessage(String messageId) {
		int pos = findMessageItemPositionInViewReverse(messageId);
		if (pos > -1) {
			messageItems.remove(pos);
			messagePanel.getMessageListView().notifyItemRemoved(pos);
			messageService.markDeleted(messageId);
		}
	}

	/**
	 * 粘贴
	 */
	public void paste() {
		messageEditorPanel.getEditor().paste();
		messageEditorPanel.getEditor().requestFocus();
		MainFrame.getContext().setExtendedState(JFrame.NORMAL);
		MainFrame.getContext().toFront();
		MainFrame.getContext().setVisible(true);
	}

	public void restoreRemoteHistoryLoadedRooms() {
		remoteHistoryLoadedRooms.clear();
	}

	public void showWebSocketMsg(Letter letter) {
		if (currentUser.getUserId().equals(letter.from))
			return;
		System.out.println("收到消息");
		Message message = new Message();
		message.setId(StringUtils.getUUID());
		message.setRoomId(letter.from);
		if ("抖动".equals(letter.data)) {
			Clip.shakeFrame(MainFrame.getContext(), 8);
		}
		TrayInfo trayInfo = new TrayInfo();
		trayInfo.e = new TrayListener() {
			public void exe(TrayInfo e) {
				MainFrame.getContext().setExtendedState(JFrame.NORMAL);
				MainFrame.getContext().toFront();
				TabOperationPanel.getContext().showChatPanel();
				MainFrame.getContext().setVisible(true);
			}
		};
		trayInfo.id = letter.from;
		trayInfo.type = ETrayType.CHAT;
		String senderUserName = letter.from;
		System.out.println("用户：" + senderUserName);
		trayInfo.icon = new ImageIcon(
				AvatarUtil.createOrLoadUserAvatar(senderUserName).getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		//		AvatarUtil.createOrLoadUserAvatar(Launcher.getUserNameByUserId(msg.getFromId()));
		TrayUtil.getTray().pushTrayInfo(trayInfo);
		message.setMessageContent(letter.data);
		message.setSenderId(letter.from);
		message.setTimestamp(new Date().getTime());
		message.setSenderUsername(senderUserName);
		messageService.insertOrUpdate(message);
		Room friendRoom = roomService.findById(letter.from);
		friendRoom.setLastMessage(message.getMessageContent());
		friendRoom.setUnreadCount(friendRoom.getUnreadCount() + 1);
		roomService.insertOrUpdate(friendRoom);
		MessageItem item = new MessageItem(message, friendRoom.getRoomId());
		item.setMessageType(MessageItem.LEFT_TEXT);
		System.out.println(room + ":null");
		if (room != null && friendRoom.getType().equals("d") && room.getName().equals(message.getSenderUsername())) {
			addMessageItemToEnd(item);
		}
		RoomsPanel.getContext().updateRoomItem(friendRoom.getRoomId());
	}
	
	public void showReceiveMsg(MsgResponse msg) {
		String userId = Launcher.getUserIdById(msg.getFromId());

		System.err.println(userId);
		System.err.println(currentUser.getUserId());
		if (currentUser.getUserId().equals(userId))
			return;
		System.out.println("收到消息");
		Message message = new Message();
		message.setId(StringUtils.getUUID());
		message.setRoomId(userId);
		String string = new String(msg.getData());
		//		string = StringEscapeUtils.unescapeJava(string);
		System.out.println(string);
		if ("抖动".equals(string)) {
			Clip.shakeFrame(MainFrame.getContext(), 8);
		}
		TrayInfo trayInfo = new TrayInfo();
		trayInfo.e = new TrayListener() {
			public void exe(TrayInfo e) {
				MainFrame.getContext().setExtendedState(JFrame.NORMAL);
				MainFrame.getContext().toFront();
				TabOperationPanel.getContext().showChatPanel();
				MainFrame.getContext().setVisible(true);
			}
		};
		trayInfo.id = userId;
		trayInfo.type = ETrayType.CHAT;
		String senderUserName = Launcher.getUserNameByUserId(trayInfo.id);
		System.out.println("用户：" + senderUserName);
		trayInfo.icon = new ImageIcon(
				AvatarUtil.createOrLoadUserAvatar(senderUserName).getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		//		AvatarUtil.createOrLoadUserAvatar(Launcher.getUserNameByUserId(msg.getFromId()));
		TrayUtil.getTray().pushTrayInfo(trayInfo);
		message.setMessageContent(string);
		message.setSenderId(msg.getFromId() + "");
		message.setTimestamp(new Date().getTime());
		message.setSenderUsername(senderUserName);
		messageService.insertOrUpdate(message);
		Room friendRoom = roomService.findById(userId);
		friendRoom.setLastMessage(message.getMessageContent());
		friendRoom.setUnreadCount(friendRoom.getUnreadCount() + 1);
		roomService.insertOrUpdate(friendRoom);
		MessageItem item = new MessageItem(message, friendRoom.getRoomId());
		item.setMessageType(MessageItem.LEFT_TEXT);
		System.out.println(room + ":null");
		if (room != null && friendRoom.getType().equals("d") && room.getName().equals(message.getSenderUsername())) {
			addMessageItemToEnd(item);
		}
		RoomsPanel.getContext().updateRoomItem(friendRoom.getRoomId());
	}

	public void showReceiveMsgFail(String tipContent) {

	}

	public void showReceiveFileMsg(FileChatRequest msg) {
		String homePath = App.basePath;
		String dirPath = homePath + File.separator + "data" + File.separator + "chat"
				+ File.separator + currentUser.getUsername() + File.separator + Launcher.getUserNameByUserId(msg.getSenderUserId());
		if (!new File(dirPath).exists()) {
			new File(dirPath).mkdirs();
		}
		File file = new File(dirPath, msg.getName());
		System.out.println(msg.getPosition());
		FileUtils.writeFile(file, msg.getPosition(), msg.getData());
		System.out.println("index:" + msg.getIndex() + "   total:" + msg.getTotal() + "   len:" + msg.getData().length);
		/**
		 * 判断是否是最后一条
		 */
		if (msg.getIndex() + 1 != msg.getTotal())
			return;
		TrayInfo trayInfo = new TrayInfo();
		trayInfo.e = new TrayListener() {
			public void exe(TrayInfo e) {
				MainFrame.getContext().setExtendedState(JFrame.NORMAL);
				MainFrame.getContext().toFront();
				TabOperationPanel.getContext().showChatPanel();
				MainFrame.getContext().setVisible(true);
			}
		};
		trayInfo.id = msg.getSenderUserId() + "";
		trayInfo.type = ETrayType.CHAT;
		String senderUserName = Launcher.getUserNameByUserId(msg.getSenderUserId());
		System.out.println("用户：" + senderUserName);
		trayInfo.icon = new ImageIcon(
				AvatarUtil.createOrLoadUserAvatar(senderUserName).getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		//		AvatarUtil.createOrLoadUserAvatar(Launcher.getUserNameByUserId(msg.getFromId()));
		TrayUtil.getTray().pushTrayInfo(trayInfo);
		Message message = new Message();
		message.setId(StringUtils.getUUID());
		message.setRoomId(msg.getSenderUserId() + "");
		message.setSenderUsername(senderUserName);
		message.setMessageContent(msg.getName());
		//		string = StringEscapeUtils.unescapeJava(string);
		message.setSenderId(msg.getSenderUserId() + "");
		message.setTimestamp(new Date().getTime());
		String suffix = StringUtils.getSuffix(msg.getName());
		String mime = MimeTypeUtil.getMime(suffix);
		if (mime.startsWith("image")) {
			message.setImageAttachmentId(msg.getMessageId());
			messageService.insertOrUpdate(message);
			Room friendRoom = roomService.findById(msg.getSenderUserId() + "");
			friendRoom.setLastMessage(message.getMessageContent());
			friendRoom.setUnreadCount(friendRoom.getUnreadCount() + 1);
			roomService.insertOrUpdate(friendRoom);
			ImageAttachment attachment = new ImageAttachment();
			attachment.setId(msg.getMessageId());
			attachment.setTitle(msg.getName());
			attachment.setImageUrl(file.getAbsolutePath());
			imageAttachmentService.insertOrUpdate(attachment);
			MessageItem item = new MessageItem(message, friendRoom.getRoomId());
			item.setMessageType(MessageItem.LEFT_IMAGE);
			if (room != null && friendRoom.getType().equals("d") && room.getName().equals(message.getSenderUsername())) {
				addMessageItemToEnd(item);
			}
			RoomsPanel.getContext().updateRoomItem(friendRoom.getRoomId());
		} else {
			message.setFileAttachmentId(msg.getMessageId());
			messageService.insertOrUpdate(message);
			Room friendRoom = roomService.findById(msg.getSenderUserId() + "");
			friendRoom.setLastMessage(message.getMessageContent());
			friendRoom.setUnreadCount(friendRoom.getUnreadCount() + 1);
			roomService.insertOrUpdate(friendRoom);
			FileAttachment attachment = new FileAttachment();
			attachment.setId(msg.getMessageId());
			attachment.setTitle(msg.getName());
			attachment.setLink(file.getAbsolutePath());
			fileAttachmentService.insertOrUpdate(attachment);
			MessageItem item = new MessageItem(message, friendRoom.getRoomId());
			item.setMessageType(MessageItem.LEFT_ATTACHMENT);
			if (room != null && friendRoom.getType().equals("d") && room.getName().equals(message.getSenderUsername())) {
				addMessageItemToEnd(item);
			}
			RoomsPanel.getContext().updateRoomItem(friendRoom.getRoomId());
		}
		System.out.println("加进来了2");

	}
}
