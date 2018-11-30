package cn.harry12800.vchat.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import cn.harry12800.j2se.style.ui.Colors;

/**
 * Created by harry12800 on 17-5-29.
 */
@SuppressWarnings("serial")
public class RightPanel extends JPanel {
	private static RightPanel context;
	private TitlePanel titlePanel;
	private RoomMembersPanel roomMembersPanel;

	private ChatPanel chatPanel;
	private DiaryPanel diaryPanel;
	private TipPanel tipPanel;
	private UserInfoPanel userInfoPanel;

	private JPanel contentPanel;

	private CardLayout cardLayout;

	public static final String CHAT = "CHAT";
	public static final String TIP = "TIP";
	public static final String USER_INFO = "USER_INFO";
	public static final String DIARY = "DIARY";
	public static String current = "";

	public RightPanel() {
		context = this;
		initComponents();
		initView();

	}

	private void initComponents() {
		cardLayout = new CardLayout();
		contentPanel = new JPanel();
		contentPanel.setLayout(cardLayout);

		titlePanel = new TitlePanel(this);
		chatPanel = new ChatPanel(this);
		diaryPanel = new DiaryPanel(this);
		roomMembersPanel = new RoomMembersPanel(this);
		tipPanel = new TipPanel(this);
		userInfoPanel = new UserInfoPanel(this);

		setBorder(new LineBorder(Colors.SCROLL_BAR_TRACK_LIGHT));
	}

	private void initView() {
		contentPanel.add(tipPanel, TIP);
		contentPanel.add(userInfoPanel, USER_INFO);
		contentPanel.add(chatPanel, CHAT);
		contentPanel.add(diaryPanel, DIARY);

		this.setBackground(Colors.FONT_WHITE);
		this.setLayout(new BorderLayout());
		add(titlePanel, BorderLayout.NORTH);
		add(roomMembersPanel, BorderLayout.EAST);
		add(contentPanel, BorderLayout.CENTER);

		// add(chatPanel, BorderLayout.CENTER);
		// add(tipPanel, BorderLayout.CENTER);
	}

	public void showPanel(String who) {
		if (current.equals(who))
			return;
		current = who;
		cardLayout.show(contentPanel, who);
	}

	public RoomMembersPanel getRoomMembersPanel() {
		return roomMembersPanel;
	}

	public JPanel getTipPanel() {
		return tipPanel;
	}

	public UserInfoPanel getUserInfoPanel() {
		return userInfoPanel;
	}

	public static RightPanel getContext() {
		return context;
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

}
