package cn.harry12800.vchat.panels;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import cn.harry12800.common.core.model.Request;
import cn.harry12800.common.module.ModuleId;
import cn.harry12800.common.module.UserCmd;
import cn.harry12800.common.module.user.dto.ShowAllUserRequest;
import cn.harry12800.common.module.user.dto.ShowAllUserResponse;
import cn.harry12800.common.module.user.dto.UserResponse;
import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.lnk.core.FileUtils;
import cn.harry12800.vchat.adapter.RoomItemViewHolder;
import cn.harry12800.vchat.adapter.RoomItemsAdapter;
import cn.harry12800.vchat.app.Launcher;
import cn.harry12800.vchat.app.config.Contants;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.components.RCListView;
import cn.harry12800.vchat.db.model.Room;
import cn.harry12800.vchat.db.service.RoomService;
import cn.harry12800.vchat.entity.RoomItem;
import cn.harry12800.vchat.utils.AvatarUtil;
import cn.harry12800.vchat.utils.HttpUtil;

/**
 * 左侧聊天列表 Created by harry12800 on 17-5-30.
 */
@SuppressWarnings("serial")
public class RoomsPanel extends ParentAvailablePanel {
	private static RoomsPanel context;

	private RCListView roomItemsListView;
	private List<RoomItem> roomItemList = new ArrayList<>();
	private RoomService roomService = Launcher.roomService;

	private boolean isActive = false;

	public RoomsPanel(JPanel parent) {
		super(parent);
		context = this;
		initComponents();
		initView();
		initData();
		roomItemsListView.setAdapter(new RoomItemsAdapter(roomItemList));
	}

	private void initComponents() {
		roomItemsListView = new RCListView();
	}

	private void initView() {
		setLayout(new GridBagLayout());
		roomItemsListView.setContentPanelBackground(Colors.DARK);
		add(roomItemsListView, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1));
		// add(scrollPane, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1));
	}

	public void initData(ShowAllUserResponse userResponse) {
		List<UserResponse> users = userResponse.getUsers();
		for (UserResponse user : users) {
			RoomItem item = new RoomItem();
			item.setRoomId(user.getUserId());
			item.setTimestamp(Instant.now().getEpochSecond());
			item.setTitle(user.getNickName());
			item.setType("d");
			Room room = roomService.findById(user.getUserId());
			if (room == null) {
				room = new Room();
			}
			room.setCreatorId(user.getUserId());
			room.setRoomId(user.getUserId());
			room.setName(user.getNickName());
			room.setTopic(user.getNickName());
			room.setType("d");
			item.setUnreadCount(room.getUnreadCount());
			item.setLastMessage(room.getLastMessage());
			roomService.insertOrUpdate(room);
			roomItemList.add(item);
		}
		Launcher.loadUsers(users);
		downloadAvatar(users);
		roomItemsListView.notifyDataSetChanged(true);
	}
	public void addWebSocketRoom(String  userId){
		RoomItem item = new RoomItem();
		item.setRoomId(userId);
		item.setTimestamp(Instant.now().getEpochSecond());
		item.setTitle(userId);
		item.setType("d");
		boolean contains = roomItemList.contains(item);
		if(!contains) {
			Room room = roomService.findById(userId);
			if (room == null) {
				room = new Room();
			}
			room.setCreatorId(userId);
			room.setRoomId(userId);
			room.setName(userId);
			room.setTopic(userId);
			room.setType("d");
			item.setUnreadCount(room.getUnreadCount());
			item.setLastMessage(room.getLastMessage());
			roomItemList.add(item);
			roomService.insertOrUpdate(room);
			roomItemsListView.notifyDataSetChanged(true);
		}
		
	}
	private void downloadAvatar(List<UserResponse> users) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (UserResponse userResponse : users) {
					String userName = userResponse.getNickName();
					String path = Contants.getPath(Contants.downloadPath);
					try {
						byte[] download = HttpUtil.download(path + "?path=/root/avatar/" + userName + ".png");
						if (download != null) {
							File file = new File(AvatarUtil.CUSTOM_AVATAR_CACHE_ROOT + "/" + userName + ".png");
							FileUtils.writeBytes2File(download, file);
						}
					} catch (IOException e) {
//						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private void initData() {
		roomItemList.clear();
		try {
			ShowAllUserRequest request = new ShowAllUserRequest();
			Request req = Request.valueOf(ModuleId.USER, UserCmd.SHOW_ALL_USER, request.getBytes());
			Launcher.client.sendRequest(req);
		} catch (Exception e) {
			e.printStackTrace();
			// MainFrame.("无法连接服务器");
		}

		// TODO: 从数据库中加载房间列表
		// Room harry12800 = roomService.findRelativeRoomIdByUserId("song");
		// List<Room> rooms = roomService.findAll();
		// for (Room room : rooms) {
		// RoomItem item = new RoomItem();
		// item.setRoomId(room.getRoomId());
		// item.setTimestamp(room.getLastChatAt());
		// item.setTitle(room.getName());
		// item.setType(room.getType());
		// item.setLastMessage(room.getLastMessage());
		// item.setUnreadCount(room.getUnreadCount());
		//
		// roomItemList.add(item);
		// }
	}

	/**
	 * 重绘整个列表
	 */
	public void notifyDataSetChanged(boolean keepSize) {
		initData();
		roomItemsListView.notifyDataSetChanged(keepSize);
	}

	/**
	 * 更新房间列表 当这条消息所在的房间在当前房间列表中排在第一位时，此时房间列表项目顺序不变，无需重新排列 因此无需更新整个房间列表，只需更新第一个项目即可
	 *
	 * @param msgRoomId
	 */
	public void updateRoomsList(String msgRoomId) {
		String roomId = (String) ((RoomItemViewHolder) (roomItemsListView.getItem(0))).getTag();
		if (roomId.equals(msgRoomId)) {
			Room room = roomService.findById(roomId);
			for (RoomItem roomItem : roomItemList) {
				if (roomItem.getRoomId().equals(roomId)) {
					roomItem.setUnreadCount(room.getUnreadCount());
					roomItem.setTimestamp(room.getLastChatAt());
					roomItem.setLastMessage(room.getLastMessage());
					break;
				}
			}

			roomItemsListView.notifyItemChanged(0);
		} else {
			notifyDataSetChanged(false);
		}
	}

	/**
	 * 更新指定位置的房间项目
	 * 
	 * @param roomId
	 */
	public void updateRoomItem(String roomId) {
		if (roomId == null || roomId.isEmpty()) {
			notifyDataSetChanged(true);
			return;
		}

		for (int i = 0; i < roomItemList.size(); i++) {
			RoomItem item = roomItemList.get(i);
			if (item.getRoomId().equals(roomId)) {
				Room room = roomService.findById(item.getRoomId());
				if (room != null) {
					item.setLastMessage(room.getLastMessage());
					item.setTimestamp(room.getLastChatAt());
					item.setUnreadCount(room.getUnreadCount());
					roomItemsListView.notifyItemChanged(i);
				}
				break;
			}
		}
	}

	/**
	 * 激活指定的房间项目
	 * 
	 * @param position
	 */
	public void activeItem(int position) {
		if (isActive)
			return;
		isActive = true;
		RoomItemViewHolder holder = (RoomItemViewHolder) roomItemsListView.getItem(position);
		setItemBackground(holder, Colors.ITEM_SELECTED);
		isActive = false;
	}

	/**
	 * 设置每个房间项目的背影色
	 * 
	 * @param holder
	 * @param color
	 */
	private void setItemBackground(RoomItemViewHolder holder, Color color) {
		holder.setBackground(color);
		holder.nameBrief.setBackground(color);
		holder.timeUnread.setBackground(color);
	}

	public static RoomsPanel getContext() {
		return context;
	}

}
