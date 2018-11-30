package cn.harry12800.vchat.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.vchat.components.RCListView;

/**
 * Created by harry12800 on 17-5-30.
 */
@SuppressWarnings("serial")
public class MessagePanel extends ParentAvailablePanel {
	RCListView listView;

	public MessagePanel(JPanel parent) {
		super(parent);

		initComponents();
		setListeners();
		initView();
	}

	private void initComponents() {
		listView = new RCListView(0, 15);
		listView.setScrollBarColor(Colors.WINDOW_BACKGROUND, Colors.WINDOW_BACKGROUND);
		listView.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		listView.setScrollHiddenOnMouseLeave(listView);

	}

	private void setListeners() {
		/*
		 * listView.addMouseListener(new AbstractMouseListener(){
		 * 
		 * @Override public void mouseClicked(MouseEvent e) {
		 * RoomMembersPanel.getContext().setVisible(false); super.mouseClicked(e); } });
		 */
	}

	private void initView() {
		this.setLayout(new BorderLayout());
		add(listView, BorderLayout.CENTER);
		/*
		 * listView.repaint(); listView.setVisible(true);
		 */

		/*
		 * addComponentListener(new ComponentAdapter() {
		 * 
		 * @Override public void componentResized(ComponentEvent e) {
		 * listView.repaint(); } });
		 */
	}

	public RCListView getMessageListView() {
		return listView;
	}
}
