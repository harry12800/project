package cn.harry12800.vchat.adapter;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.FontUtil;
import cn.harry12800.j2se.utils.IconUtil;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.components.RCBorder;
import cn.harry12800.vchat.frames.CreateGroupDialog;

/**
 * Created by harry12800 on 17-5-30.
 */
@SuppressWarnings("serial")
public class SelectUserItemViewHolder extends ViewHolder {
	public JLabel avatar = new JLabel();
	public JLabel username = new JLabel();
	public JLabel icon = new JLabel();

	public SelectUserItemViewHolder() {
		initComponents();
		initView();
	}

	private void initComponents() {
		// panelItem = new JPanel();
		setPreferredSize(new Dimension(CreateGroupDialog.DIALOG_WIDTH / 2 - 20, 50));
		setBorder(new RCBorder(RCBorder.BOTTOM, Colors.LIGHT_GRAY));
		setOpaque(true);
		setForeground(Colors.FONT_BLACK);

		username.setFont(FontUtil.getDefaultFont(13));
		username.setForeground(Colors.FONT_BLACK);

		icon.setIcon(IconUtil.getIcon(this, "/image/uncheck.png"));

		setLayout(new GridBagLayout());
		add(avatar, new GBC(0, 0).setWeight(1, 1).setFill(GBC.BOTH).setInsets(0, 5, 0, 0));
		add(username, new GBC(1, 0).setWeight(100, 1).setFill(GBC.BOTH).setInsets(0, 5, 0, 0));
		add(icon, new GBC(2, 0).setWeight(1, 1).setFill(GBC.BOTH).setInsets(0, 0, 0, 5));
	}

	private void initView() {

	}
}
