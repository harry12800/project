package cn.harry12800.vchat.panels;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.components.RCBorder;

/**
 * Created by harry12800 on 17-5-29.
 */
@SuppressWarnings("serial")
public class TabOperationPanel extends ParentAvailablePanel {
	private JLabel chatLabel;
	private JLabel contactsLabel;
	private JLabel meLabel;
	private JLabel diaryLabel;
	private TabItemClickListener clickListener;
	private ImageIcon chatIconActive;
	private ImageIcon chatIconNormal;
	private ImageIcon contactIconNormal;
	private ImageIcon contactIconActive;
	private ImageIcon meIconNormal;
	private ImageIcon meIconActive;

	private LeftPanel parent;

	private static TabOperationPanel context;

	public TabOperationPanel(JPanel parent) {
		super(parent);
		context = this;
		initComponents();
		initView();
	}

	public static TabOperationPanel getContext() {
		return context;
	}

	private void initComponents() {
		Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
		clickListener = new TabItemClickListener();
		RCBorder rcBorder = new RCBorder(RCBorder.RIGHT);
		rcBorder.setHeightScale(0.2F);

		chatIconActive = new ImageIcon(getClass().getResource("/image/chat_active.png"));
		chatIconNormal = new ImageIcon(getClass().getResource("/image/chat_normal.png"));
		chatLabel = new JLabel();
		chatLabel.setIcon(chatIconActive);
		chatLabel.setBorder(rcBorder);
		chatLabel.setHorizontalAlignment(JLabel.CENTER);
		chatLabel.setCursor(handCursor);
		chatLabel.addMouseListener(clickListener);
		chatLabel.setToolTipText("消息");

		contactIconNormal = new ImageIcon(getClass().getResource("/image/contacts_normal.png"));
		contactIconActive = new ImageIcon(getClass().getResource("/image/contacts_active.png"));
		contactsLabel = new JLabel();
		contactsLabel.setIcon(contactIconNormal);
		contactsLabel.setBorder(rcBorder);
		contactsLabel.setHorizontalAlignment(JLabel.CENTER);
		contactsLabel.setCursor(handCursor);
		contactsLabel.addMouseListener(clickListener);
		contactsLabel.setToolTipText("联系人");

		meIconNormal = new ImageIcon(getClass().getResource("/image/me_normal.png"));
		meIconActive = new ImageIcon(getClass().getResource("/image/me_active.png"));
		meLabel = new JLabel();
		meLabel.setIcon(meIconNormal);
		meLabel.setHorizontalAlignment(JLabel.CENTER);
		meLabel.setCursor(handCursor);
		meLabel.setToolTipText("收藏");
		meLabel.addMouseListener(clickListener);

		diaryLabel = new JLabel();
		diaryLabel.setIcon(meIconNormal);
		diaryLabel.setHorizontalAlignment(JLabel.CENTER);
		diaryLabel.setCursor(handCursor);
		diaryLabel.addMouseListener(clickListener);
		diaryLabel.setToolTipText("日记");
		parent = (LeftPanel) getParentPanel();
	}

	private void initView() {
		setLayout(new GridBagLayout());
		setBackground(Colors.DARK);
		setBorder(new RCBorder(RCBorder.BOTTOM));
		add(chatLabel, new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(1, 1).setInsets(0, 10, 0, 10));
		add(contactsLabel, new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(1, 1).setInsets(0, 10, 0, 10));
		add(meLabel, new GBC(2, 0).setFill(GBC.HORIZONTAL).setWeight(1, 1).setInsets(0, 10, 0, 10));
		add(diaryLabel, new GBC(3, 0).setFill(GBC.HORIZONTAL).setWeight(1, 1).setInsets(10, 10, 10, 10));
	}

	@Override
	protected void printBorder(Graphics g) {
		super.printBorder(g);
	}

	class TabItemClickListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// 搜索框内容清空
			SearchPanel.getContext().clearSearchText();

			if (e.getComponent() == chatLabel) {
				chatLabel.setIcon(chatIconActive);
				contactsLabel.setIcon(contactIconNormal);
				meLabel.setIcon(meIconNormal);
				diaryLabel.setIcon(meIconNormal);
				parent.getListPanel().showPanel(ListPanel.CHAT);

			} else if (e.getComponent() == contactsLabel) {
				chatLabel.setIcon(chatIconNormal);
				contactsLabel.setIcon(contactIconActive);
				meLabel.setIcon(meIconNormal);
				diaryLabel.setIcon(meIconNormal);
				parent.getListPanel().showPanel(ListPanel.CONTACTS);
			} else if (e.getComponent() == meLabel) {
				chatLabel.setIcon(chatIconNormal);
				contactsLabel.setIcon(contactIconNormal);
				meLabel.setIcon(meIconActive);
				diaryLabel.setIcon(meIconNormal);
				parent.getListPanel().showPanel(ListPanel.COLLECTIONS);
			} else if (e.getComponent() == diaryLabel) {
				chatLabel.setIcon(chatIconNormal);
				contactsLabel.setIcon(contactIconNormal);
				meLabel.setIcon(meIconNormal);
				diaryLabel.setIcon(meIconActive);
				parent.getListPanel().showPanel(ListPanel.DIARY);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
	}

	public void showChatPanel() {
		chatLabel.setIcon(chatIconActive);
		contactsLabel.setIcon(contactIconNormal);
		meLabel.setIcon(meIconNormal);
		diaryLabel.setIcon(meIconNormal);
		parent.getListPanel().showPanel(ListPanel.CHAT);
	}
}
