package cn.harry12800.vchat.adapter;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import cn.harry12800.j2se.component.rc.RCBorder;
import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.FontUtil;
import cn.harry12800.vchat.components.GBC;

/**
 * Created by harry12800 on 17-5-30.
 */
@SuppressWarnings("serial")
public class ContactsItemViewHolder extends ViewHolder {
	public JLabel avatar = new JLabel();
	public JLabel roomName = new JLabel();

	public ContactsItemViewHolder() {
		initComponents();
		initView();
	}

	private void initComponents() {
		setPreferredSize(new Dimension(100, 50));
		setBackground(Colors.DARK);
		setBorder(new RCBorder(RCBorder.BOTTOM));
		setOpaque(true);
		setForeground(Colors.FONT_WHITE);

		roomName.setFont(FontUtil.getDefaultFont(13));
		roomName.setForeground(Colors.FONT_WHITE);
	}

	private void initView() {
		setLayout(new GridBagLayout());
		add(avatar, new GBC(0, 0).setWeight(1, 1).setFill(GBC.BOTH).setInsets(0, 5, 0, 0));
		add(roomName, new GBC(1, 0).setWeight(10, 1).setFill(GBC.BOTH));
	}
}
