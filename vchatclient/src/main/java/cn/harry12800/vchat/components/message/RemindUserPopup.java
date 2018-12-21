package cn.harry12800.vchat.components.message;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import cn.harry12800.j2se.component.rc.adapter.ViewHolder;
import cn.harry12800.vchat.utils.AvatarUtil;

/**
 * Created by harry12800 on 21/06/2017.
 */
@SuppressWarnings("serial")
public class RemindUserPopup extends JPopupMenu {
	private List<String> users;
	private long roomId = 0;
	private UserSelectedCallBack selectedCallBack;

	public RemindUserPopup() {
	}

	public RemindUserPopup(List<String> users) {
		this.users = users;
	}

	public void show(Component invoker, int x, int y, long roomId) {
		if (roomId!= this.roomId) {
			this.removeAll();
			this.initComponents();
			this.revalidate();
			this.roomId = roomId;
		}

		super.show(invoker, x, y);
	}

	public void reset() {
		this.roomId = 0;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	private void initComponents() {
		if (this.users != null) {
			this.setAutoscrolls(true);

			JMenuItem item = null;
			for (String user : users) {
				item = new JMenuItem(user);
				item.setUI(new RCRemindUserMenuItemUI(120, 25));
				// Image avatar = AvatarUtil.createOrLoadUserAvatar(user).getScaledInstance(15,
				// 15, Image.SCALE_SMOOTH);
				// item.setUI(new RCRemindUserMenuItemUI(80, 25, avatar));
				item.setIcon(new ImageIcon(
						AvatarUtil.createOrLoadUserAvatar(user).getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
				item.setIconTextGap(-2);

				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (selectedCallBack != null) {
							selectedCallBack.onSelected(((JMenuItem) e.getSource()).getText());
						}
					}
				});
				add(item);
			}
		}
	}

	public void setSelectedCallBack(UserSelectedCallBack selectedCallBack) {
		this.selectedCallBack = selectedCallBack;
	}

	public interface UserSelectedCallBack {
		void onSelected(String username);
	}

	@SuppressWarnings("unused")
	private class UserItem extends ViewHolder {
		public UserItem() {
			add(new JLabel(System.currentTimeMillis() + ""));
		}
	}
}
