package cn.harry12800.vchat.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.FontUtil;
import cn.harry12800.j2se.utils.IconUtil;
import cn.harry12800.vchat.app.Launcher;
import cn.harry12800.vchat.components.GBC;

/**
 * Created by harry12800 on 26/06/2017.
 */
@SuppressWarnings("serial")
public class AboutPanel extends JPanel {
	private JLabel imageLabel;
	private JLabel versionLabel;

	public AboutPanel() {
		initComponents();
		initView();
	}

	private void initComponents() {
		imageLabel = new JLabel();
		ImageIcon icon = IconUtil.getIcon(this, "/image/ic_launcher.png", 100, 100);
		imageLabel.setIcon(icon);

		versionLabel = new JLabel();
		versionLabel.setText("微聊 v" + Launcher.APP_VERSION);
		versionLabel.setFont(FontUtil.getDefaultFont(20));
		versionLabel.setForeground(Colors.FONT_GRAY_DARKER);
	}

	private void initView() {
		this.setLayout(new GridBagLayout());

		JPanel avatarNamePanel = new JPanel();
		avatarNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
		avatarNamePanel.add(imageLabel, BorderLayout.WEST);
		avatarNamePanel.add(versionLabel, BorderLayout.CENTER);

		add(avatarNamePanel,
				new GBC(0, 0).setWeight(1, 1).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setInsets(50, 0, 0, 0));
	}
}
