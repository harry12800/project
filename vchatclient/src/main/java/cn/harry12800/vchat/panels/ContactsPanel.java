package cn.harry12800.vchat.panels;

import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.harry12800.common.module.packet.PullAllUserPacket;
import cn.harry12800.common.module.user.dto.ShowAllUserResponse;
import cn.harry12800.common.module.user.dto.UserResponse;
import cn.harry12800.j2se.component.rc.RCListView;
import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.vchat.adapter.ContactsItemsAdapter;
import cn.harry12800.vchat.app.Launcher;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.db.model.ContactsUser;
import cn.harry12800.vchat.db.service.ContactsUserService;
import cn.harry12800.vchat.entity.ContactsItem;
import cn.harry12800.vchat.utils.AvatarUtil;

/**
 * Created by harry12800 on 17-5-30.
 */
@SuppressWarnings("serial")
public class ContactsPanel extends ParentAvailablePanel {
	private static ContactsPanel context;

	private RCListView contactsListView;
	private List<ContactsItem> contactsItemList = new ArrayList<>();
	private ContactsUserService contactsUserService = Launcher.contactsUserService;
	Logger logger = Logger.getLogger(this.getClass());
	private String currentUsername;

	public ContactsPanel(JPanel parent) {
		super(parent);
		context = this;

		initComponents();
		initView();
		initData();
		contactsListView.setAdapter(new ContactsItemsAdapter(contactsItemList));

		// TODO: 从服务器获取通讯录后，调用下面方法更新UI
		notifyDataSetChanged();
		System.out.println("``1");
	}

	private void initComponents() {
		contactsListView = new RCListView();
	}

	private void initView() {
		setLayout(new GridBagLayout());
		contactsListView.setContentPanelBackground(Colors.DARK);
		add(contactsListView, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1));
	}

	private void initData() {
		contactsItemList.clear();
		try {
			PullAllUserPacket p = new PullAllUserPacket();
			Launcher.client.sendRequest(p.requestPacket);
		} catch (Exception e) {
			e.printStackTrace();
			// MainFrame.("无法连接服务器");
		}
		List<ContactsUser> contactsUsers = contactsUserService.findAll();
		for (ContactsUser contactsUser:contactsUsers) {
			ContactsItem item = new ContactsItem(contactsUser.getUserId(),
					contactsUser.getUsername(), "d");
			contactsItemList.add(item);
		}

	}

	public void notifyDataSetChanged() {
		((ContactsItemsAdapter) contactsListView.getAdapter()).processData();
		contactsListView.notifyDataSetChanged(false);

		// 通讯录更新后，获取头像
		getContactsUserAvatar();
	}

	public static ContactsPanel getContext() {
		return context;
	}

	/**
	 * 获取通讯录中用户的头像
	 */
	private void getContactsUserAvatar() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (ContactsItem user : contactsItemList) {
					if (!AvatarUtil.customAvatarExist(user.getName())) {
						final String username = user.getName();
						// logger.debug("获取头像:" + username);
						getUserAvatar(username, true);
					}
				}

				// 自己的头像每次启动都去获取
				currentUsername = Launcher.currentUser.getUsername();
				getUserAvatar(currentUsername, true);
			}
		}).start();

	}

	/**
	 * 更新指定用户头像
	 * 
	 * @param username
	 *            用户名
	 * @param hotRefresh
	 *            是否热更新，hotRefresh = true， 将刷新该用户的头像缓存
	 */
	@Autowired
	public void getUserAvatar(String username, boolean hotRefresh) {
		// TODO: 服务器获取头像，这里从资源文件夹中获取
		try {
			String name = "/avatar/" + username + ".png";
			//			System.out.println(name);
			URL url = getClass().getResource(name);
			BufferedImage image = ImageIO.read(url);
			processAvatarData(image, username);
		} catch (Exception e) {
			// e.printStackTrace();
		}

		if (hotRefresh) {
			AvatarUtil.refreshUserAvatarCache(username);
			if (username.equals(Launcher.currentUser.getUsername())) {
				MyInfoPanel.getContext().reloadAvatar();
			}
		}
	}

	/**
	 * 处理头像数据
	 * 
	 * @param image
	 * @param username
	 */
	private void processAvatarData(BufferedImage image, String username) {
		if (image != null) {
			AvatarUtil.saveAvatar(image, username);
		} else {
			AvatarUtil.deleteCustomAvatar(username);
		}
	}

	public void initData(ShowAllUserResponse userResponse) {
		contactsItemList.clear();
		contactsListView.setAdapter(new ContactsItemsAdapter(contactsItemList));
		List<UserResponse> users = userResponse.getUsers();
		for (UserResponse user : users) {
			ContactsItem item = new ContactsItem(user.getUserId(), user.getRealName(), "d");
			contactsItemList.add(item);
			ContactsUser contactsUser = new ContactsUser(Launcher.currentUser.getUserId(), user.getRealName(), user.getRealName(),user.getUserId());
			contactsUserService.insertOrUpdate(contactsUser );
		}
		notifyDataSetChanged();
		Launcher.loadUsers(users);
//		downloadAvatar(users);
//		roomItemsListView.notifyDataSetChanged(true);
	}
}
