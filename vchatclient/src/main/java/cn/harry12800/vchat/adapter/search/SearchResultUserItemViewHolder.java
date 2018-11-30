package cn.harry12800.vchat.adapter.search;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.FontUtil;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.components.HighLightLabel;
import cn.harry12800.vchat.components.RCBorder;

/**
 * 搜索结果每一个通讯录、房间项目 Created by harry12800 on 17-5-30.
 */
@SuppressWarnings("serial")
public class SearchResultUserItemViewHolder extends SearchResultItemViewHolder {
	public JLabel avatar = new JLabel();
	public HighLightLabel name = new HighLightLabel();

	public SearchResultUserItemViewHolder() {
		initComponents();
		initView();
	}

	private void initComponents() {
		setPreferredSize(new Dimension(100, 50));
		setBackground(Colors.DARK);
		setBorder(new RCBorder(RCBorder.BOTTOM));
		setOpaque(true);
		setForeground(Colors.FONT_WHITE);

		name.setFont(FontUtil.getDefaultFont(14));
		name.setForeground(Colors.FONT_WHITE);

	}

	private void initView() {
		setLayout(new GridBagLayout());
		add(avatar, new GBC(0, 0).setWeight(2, 1).setFill(GBC.BOTH).setInsets(0, 5, 0, 0));
		add(name, new GBC(1, 0).setWeight(100, 1).setFill(GBC.BOTH).setInsets(3, 5, 0, 0));

	}
}
