package cn.harry12800.vchat.panels;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.harry12800.j2se.style.ui.Colors;

/**
 * Created by harry12800 on 17-5-30.
 */
@SuppressWarnings("serial")
public class CollectionsPanel extends ParentAvailablePanel {
	private JLabel tipLabel;

	public CollectionsPanel(JPanel parent) {
		super(parent);

		initComponents();
		initView();
	}

	private void initComponents() {
		tipLabel = new JLabel("暂无收藏");
		tipLabel.setForeground(Colors.FONT_GRAY);
	}

	private void initView() {
		this.setBackground(Colors.DARK);
		setLayout(new FlowLayout());
		add(tipLabel);
	}
}
