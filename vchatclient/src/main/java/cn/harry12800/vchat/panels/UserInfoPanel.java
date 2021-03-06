package cn.harry12800.vchat.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.harry12800.j2se.component.rc.RCButton;
import cn.harry12800.j2se.style.layout.VerticalFlowLayout;
import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.FontUtil;
import cn.harry12800.vchat.app.Launcher;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.db.model.ContactsUser;
import cn.harry12800.vchat.db.model.Room;
import cn.harry12800.vchat.db.service.ContactsUserService;
import cn.harry12800.vchat.db.service.RoomService;
import cn.harry12800.vchat.utils.AvatarUtil;

/**
 * Created by harry12800 on 2017/6/15.
 */
@SuppressWarnings("serial")
public class UserInfoPanel extends ParentAvailablePanel {
	private static Logger LOG = LoggerFactory.getLogger(UserInfoPanel.class);
	private JPanel contentPanel;
	private JLabel imageLabel;
	private JLabel nameLabel;
	private RCButton button;

	private String username;
	private RoomService roomService = Launcher.roomService;
	private ContactsUserService contactsUserService = Launcher.contactsUserService;

	public UserInfoPanel(JPanel parent) {
		super(parent);
		initComponents();
		initView();
		setListeners();
	}

	private void initComponents() {
		contentPanel = new JPanel();
		contentPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.CENTER, 0, 20, true, false));

		imageLabel = new JLabel();
		ImageIcon icon = new ImageIcon(
				AvatarUtil.createOrLoadUserAvatar("song").getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		imageLabel.setIcon(icon);

		nameLabel = new JLabel();
		nameLabel.setText("Song");
		nameLabel.setFont(FontUtil.getDefaultFont(20));

		button = new RCButton("发消息", Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
		button.setBackground(Colors.PROGRESS_BAR_START);
		button.setPreferredSize(new Dimension(200, 40));
		button.setFont(FontUtil.getDefaultFont(16));

	}

	private void initView() {
		this.setLayout(new GridBagLayout());

		JPanel avatarNamePanel = new JPanel();
		avatarNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
		avatarNamePanel.add(imageLabel, BorderLayout.WEST);
		avatarNamePanel.add(nameLabel, BorderLayout.CENTER);

		// add(avatarNamePanel, new
		// GBC(0,0).setAnchor(GBC.CENTER).setWeight(1,1).setInsets(0,0,0,0));
		// add(button, new
		// GBC(0,1).setAnchor(GBC.CENTER).setWeight(1,1).setInsets(0,0,0,0));
		contentPanel.add(avatarNamePanel);
		contentPanel.add(button);

		add(contentPanel, new GBC(0, 0).setWeight(1, 1).setAnchor(GBC.CENTER).setInsets(0, 0, 250, 0));
	}

	public void setUsername(String username) {
		this.username = username;
		nameLabel.setText(username);

		ImageIcon icon = new ImageIcon(
				AvatarUtil.createOrLoadUserAvatar(username).getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		imageLabel.setIcon(icon);
	}

	private void setListeners() {
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				openOrCreateDirectChat();
				super.mouseClicked(e);
			}
		});
	}

	private void openOrCreateDirectChat() {
		ContactsUser user = contactsUserService.find("username", username).get(0);
		LOG.info(user + "");
		long userId = user.getFriendId();
		LOG.info(userId + "");
		long creatorId = Launcher.currentUser.getId();
		LOG.info(creatorId + "");
		Room room = roomService.findRelativeRoomIdByUserId(userId, creatorId);
		LOG.info(room + "");
		// 房间已存在，直接打开，否则发送请求创建房间
		if (room != null) {
			TabOperationPanel.getContext().showChatPanel();
			ChatPanel.getContext().enterRoom(room.getRoomId());
		} else {
			createDirectChat(user);
		}
	}

	/**
	 * 创建直接聊天
	 *
	 * @param user
	 */
	private void createDirectChat(ContactsUser user) {
		Room room = new Room();
		room.setCreatorId(Launcher.currentUser.getId());
		room.setName(user.getUsername());
		room.setMember(user.getFriendId());
		room.setTopic(user.getUsername());
		room.setType("d");
		roomService.insertOrUpdate(room);
		room = roomService.findRelativeRoomIdByUserId(user.getFriendId(), Launcher.currentUser.getId());
		System.err.println("aa" + room.getRoomId());
		Launcher.loadUser2Room(user.getFriendId(), room.getRoomId());
		RoomsPanel.getContext().addNewRoomItemToTop(room);
		TabOperationPanel.getContext().showChatPanel();
		// RoomsPanel.getContext().activeItem(0);
		ChatPanel.getContext().enterRoom(room.getRoomId());
	}

}
