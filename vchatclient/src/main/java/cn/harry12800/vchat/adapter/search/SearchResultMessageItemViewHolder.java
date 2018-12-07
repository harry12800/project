package cn.harry12800.vchat.adapter.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.harry12800.j2se.component.rc.RCBorder;
import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.FontUtil;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.components.HighLightLabel;

/**
 * 搜索结果中的每一个消息项目 Created by harry12800 on 17-6-22.
 */
@SuppressWarnings("serial")
public class SearchResultMessageItemViewHolder extends SearchResultItemViewHolder {
	public JLabel avatar = new JLabel();
	public JLabel roomName = new JLabel();
	public HighLightLabel brief = new HighLightLabel();
	public JPanel nameBrief = new JPanel();
	public JLabel time = new JLabel();

	public SearchResultMessageItemViewHolder() {
		initComponents();
		initView();

	}

	private void initComponents() {
		setPreferredSize(new Dimension(100, 64));
		setBackground(Colors.DARK);
		setBorder(new RCBorder(RCBorder.BOTTOM));
		setOpaque(true);
		setForeground(Colors.FONT_WHITE);

		roomName.setFont(FontUtil.getDefaultFont(14));
		roomName.setForeground(Colors.FONT_WHITE);

		brief.setForeground(Colors.FONT_GRAY);
		brief.setFont(FontUtil.getDefaultFont(12));

		nameBrief.setLayout(new BorderLayout());
		nameBrief.setBackground(Colors.DARK);
		nameBrief.add(roomName, BorderLayout.NORTH);
		nameBrief.add(brief, BorderLayout.CENTER);

		time.setForeground(Colors.FONT_GRAY);
		time.setFont(FontUtil.getDefaultFont(12));
	}

	private void initView() {
		setLayout(new GridBagLayout());
		add(avatar, new GBC(0, 0).setWeight(2, 1).setFill(GBC.BOTH).setInsets(0, 5, 0, 0));
		add(nameBrief, new GBC(1, 0).setWeight(100, 1).setFill(GBC.BOTH).setInsets(5, 5, 0, 0));
		add(time, new GBC(2, 0).setWeight(1, 1).setFill(GBC.BOTH).setInsets(5, 0, 0, 0).setAnchor(GBC.NORTH));

	}

}
