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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.harry12800.j2se.component.rc.RCButton;
import cn.harry12800.j2se.style.layout.VerticalFlowLayout;
import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.FontUtil;
import cn.harry12800.vchat.app.Launcher;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.frames.MainFrame;
import cn.harry12800.vchat.utils.AvatarUtil;

/**
 * Created by harry12800 on 26/06/2017.
 */
@SuppressWarnings("serial")
public class MePanel extends JPanel {
	private JPanel contentPanel;
	private JLabel imageLabel;
	private JLabel nameLabel;
	private RCButton button;

	public MePanel() {
		initComponents();
		initView();
		setListeners();
	}

	private void initComponents() {
		contentPanel = new JPanel();
		contentPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.CENTER, 0, 20, true, false));

		imageLabel = new JLabel();
		ImageIcon icon = new ImageIcon(AvatarUtil.createOrLoadUserAvatar(Launcher.currentUser.getUsername())
				.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		imageLabel.setIcon(icon);

		nameLabel = new JLabel();
		nameLabel.setText(Launcher.currentUser.getUsername());
		nameLabel.setFont(FontUtil.getDefaultFont(20));

		button = new RCButton("退出登录", Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
		button.setBackground(Colors.PROGRESS_BAR_START);
		button.setPreferredSize(new Dimension(200, 35));
		button.setFont(FontUtil.getDefaultFont(16));

	}

	private void initView() {
		this.setLayout(new GridBagLayout());

		JPanel avatarNamePanel = new JPanel();
		avatarNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
		avatarNamePanel.add(imageLabel, BorderLayout.WEST);
		avatarNamePanel.add(nameLabel, BorderLayout.CENTER);

		contentPanel.add(avatarNamePanel);
		contentPanel.add(button);

		add(contentPanel, new GBC(0, 0).setWeight(1, 1).setAnchor(GBC.CENTER).setInsets(0, 0, 250, 0));
	}

	private void setListeners() {
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int ret = JOptionPane.showConfirmDialog(MainFrame.getContext(), "确认退出登录？", "确认退出",
						JOptionPane.YES_NO_OPTION);
				if (ret == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(MainFrame.getContext(), "退出登录", "退出登录",
							JOptionPane.INFORMATION_MESSAGE);
				}

				super.mouseClicked(e);
			}
		});
	}

}
