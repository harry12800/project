package cn.harry12800.vchat.panels;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cn.harry12800.j2se.action.AbstractMouseListener;
import cn.harry12800.j2se.component.rc.RCBorder;
import cn.harry12800.j2se.style.layout.VerticalFlowLayout;
import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.FontUtil;
import cn.harry12800.j2se.utils.IconUtil;
import cn.harry12800.j2se.utils.OSUtil;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.frames.MainFrame;

/**
 * Created by harry12800 on 17-5-30.
 */
@SuppressWarnings("serial")
public class TitlePanel extends ParentAvailablePanel {
	private static TitlePanel context;

	private JPanel titlePanel;
	private JLabel titleLabel;
	private JLabel statusLabel;

	private JPanel controlPanel;
	private JLabel closeLabel;
	private JLabel maxLabel;
	private JLabel minLabel;
	private JLabel roomInfoButton;

	private ImageIcon maxIcon;
	private ImageIcon restoreIcon;
	private boolean windowMax; // 当前窗口是否已最大化
	private Rectangle desktopBounds; // 去除任务栏后窗口的大小
	private Rectangle normalBounds;
	private long lastClickTime;
	private static Point origin = new Point();

	public TitlePanel(JPanel parent) {
		super(parent);
		context = this;

		initComponents();
		setListeners();
		initView();
		initBounds();

	}

	private void initBounds() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		// 上面这种方式获取的是整个显示屏幕的大小，包含了任务栏的高度。
		Insets screenInsets = Toolkit.getDefaultToolkit()
				.getScreenInsets(MainFrame.getContext().getGraphicsConfiguration());
		desktopBounds = new Rectangle(screenInsets.left, screenInsets.top,
				screenSize.width - screenInsets.left - screenInsets.right,
				screenSize.height - screenInsets.top - screenInsets.bottom);

		normalBounds = new Rectangle((screenSize.width - MainFrame.DEFAULT_WIDTH) / 2,
				(screenSize.height - MainFrame.DEFAULT_HEIGHT) / 2, MainFrame.DEFAULT_WIDTH, MainFrame.DEFAULT_HEIGHT);

	}

	private void setListeners() {
		MouseListener mouseListener = new AbstractMouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// if (roomInfoButton.isVisible())
				{
					RoomMembersPanel roomMemberPanel = ((RightPanel) getParentPanel()).getRoomMembersPanel();
					if (roomMemberPanel.isVisible()) {
						roomInfoButton.setIcon(new ImageIcon(getClass().getResource("/image/options.png")));
						roomMemberPanel.setVisibleAndUpdateUI(false);
					} else {
						roomInfoButton.setIcon(new ImageIcon(getClass().getResource("/image/options_restore.png")));
						roomMemberPanel.setVisibleAndUpdateUI(true);
					}
				}

			}
		};

		roomInfoButton.addMouseListener(mouseListener);

		if (OSUtil.getOsType() != OSUtil.Mac_OS) {
			MouseAdapter mouseAdapter = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (System.currentTimeMillis() - lastClickTime < 500) {
						maxOrRestoreWindow();
					}

					lastClickTime = System.currentTimeMillis();
					super.mouseClicked(e);
				}

				public void mousePressed(MouseEvent e) {
					// 当鼠标按下的时候获得窗口当前的位置
					origin.x = e.getX();
					origin.y = e.getY();
				}
			};

			MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
					// 当鼠标拖动时获取窗口当前位置
					Point p = MainFrame.getContext().getLocation();
					// 设置窗口的位置
					MainFrame.getContext().setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
				}
			};

			controlPanel.addMouseListener(mouseAdapter);
			controlPanel.addMouseMotionListener(mouseMotionListener);

			this.addMouseListener(mouseAdapter);
			this.addMouseMotionListener(mouseMotionListener);
		}
	}

	private void maxOrRestoreWindow() {
		if (windowMax) {
			MainFrame.getContext().setBounds(normalBounds);
			maxLabel.setIcon(maxIcon);
			windowMax = false;
		} else {
			MainFrame.getContext().setBounds(desktopBounds);
			maxLabel.setIcon(restoreIcon);
			windowMax = true;
		}
	}

	/**
	 * 隐藏群成员面板
	 */
	public void hideRoomMembersPanel() {
		JPanel roomMemberPanel = ((RightPanel) getParentPanel()).getRoomMembersPanel();
		if (roomMemberPanel.isVisible()) {
			roomInfoButton.setIcon(new ImageIcon(getClass().getResource("/image/options.png")));
			roomMemberPanel.setVisible(false);
		}
	}

	/**
	 * 显示群成员面板
	 */
	public void showRoomMembersPanel() {
		JPanel roomMemberPanel = ((RightPanel) getParentPanel()).getRoomMembersPanel();
		roomInfoButton.setIcon(new ImageIcon(getClass().getResource("/image/options_restore.png")));
		roomMemberPanel.setVisible(true);
	}

	private void initComponents() {
		Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
		maxIcon = new ImageIcon(getClass().getResource("/image/window_max.png"));
		restoreIcon = new ImageIcon(getClass().getResource("/image/window_restore.png"));

		titlePanel = new JPanel();
		titlePanel.setLayout(new GridBagLayout());

		statusLabel = new JLabel();
		statusLabel.setFont(FontUtil.getDefaultFont(14));
		statusLabel.setForeground(Colors.ITEM_SELECTED);
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusLabel.setText("正在连接中...");
		statusLabel.setIcon(IconUtil.getIcon(this, "/image/loading_small.gif"));
		statusLabel.setVisible(false);

		roomInfoButton = new JLabel();
		roomInfoButton.setIcon(new ImageIcon(getClass().getResource("/image/options.png")));
		roomInfoButton.setHorizontalAlignment(JLabel.CENTER);
		roomInfoButton.setCursor(handCursor);
		roomInfoButton.setVisible(false);

		titleLabel = new JLabel();
		titleLabel.setFont(FontUtil.getDefaultFont(16));
		titleLabel.setText("微聊");
		// titleLabel.setCursor(handCursor);

		ControlLabelMouseListener listener = new ControlLabelMouseListener();
		Dimension controlLabelSize = new Dimension(30, 30);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		closeLabel = new JLabel();
		closeLabel.setIcon(new ImageIcon(getClass().getResource("/image/close.png")));
		closeLabel.setHorizontalAlignment(JLabel.CENTER);
		closeLabel.setOpaque(true);
		closeLabel.addMouseListener(listener);
		closeLabel.setPreferredSize(controlLabelSize);
		closeLabel.setCursor(handCursor);

		maxLabel = new JLabel();
		maxLabel.setIcon(maxIcon);
		maxLabel.setHorizontalAlignment(JLabel.CENTER);
		maxLabel.setOpaque(true);
		maxLabel.addMouseListener(listener);
		maxLabel.setPreferredSize(controlLabelSize);
		maxLabel.setCursor(handCursor);

		minLabel = new JLabel();
		minLabel.setIcon(new ImageIcon(getClass().getResource("/image/window_min.png")));
		minLabel.setHorizontalAlignment(JLabel.CENTER);
		minLabel.setOpaque(true);
		minLabel.addMouseListener(listener);
		minLabel.setPreferredSize(controlLabelSize);
		minLabel.setCursor(handCursor);
	}

	private void initView() {
		setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 0, 0, true, true));

		setBorder(null);
		this.setBorder(new RCBorder(RCBorder.BOTTOM, Colors.LIGHT_GRAY));

		controlPanel.add(minLabel);
		controlPanel.add(maxLabel);
		controlPanel.add(closeLabel);

		int margin;
		if (OSUtil.getOsType() != OSUtil.Mac_OS) {
			add(controlPanel);
			add(titlePanel);

			margin = 5;
		} else {
			// add(controlPanel);

			add(titlePanel);
			margin = 15;
		}

		titlePanel.add(titleLabel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(300, 1).setInsets(margin, margin, 0, 0));
		titlePanel.add(statusLabel, new GBC(1, 0).setFill(GBC.BOTH).setWeight(800, 1).setInsets(margin, margin, 0, 0));
		titlePanel.add(roomInfoButton, new GBC(2, 0).setFill(GBC.BOTH).setWeight(1, 1).setInsets(margin, 0, 0, margin));
	}

	public static TitlePanel getContext() {
		return context;
	}

	public void updateRoomTitle(String title) {
		this.titleLabel.setText(title);

		roomInfoButton.setVisible(true);
		RightPanel parent = (RightPanel) getParent();
		parent.showPanel(RightPanel.CHAT);
	}

	public void showAppTitle() {
		this.titleLabel.setText("微聊");
		roomInfoButton.setVisible(false);
	}

	public void updateAppTitle(String text) {
		this.titleLabel.setText(text);
	}

	/**
	 * 显示状态栏
	 * 
	 * @param text
	 */
	Timer timer = new Timer();

	public synchronized void showStatusLabel(String text) {
		this.statusLabel.setText(text);
		this.statusLabel.setVisible(true);
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				statusLabel.setVisible(false);
			}
		}, 10000);
	}

	/**
	 * 隐藏状态栏
	 */
	public void hideStatusLabel() {
		this.statusLabel.setVisible(false);
	}

	private class ControlLabelMouseListener extends AbstractMouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getComponent() == closeLabel) {
				MainFrame.getContext().setVisible(false);
			} else if (e.getComponent() == maxLabel) {
				maxOrRestoreWindow();
			} else if (e.getComponent() == minLabel) {
				MainFrame.getContext().setExtendedState(JFrame.ICONIFIED);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			((JLabel) e.getSource()).setBackground(Colors.LIGHT_GRAY);
			super.mouseEntered(e);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			((JLabel) e.getSource()).setBackground(Colors.WINDOW_BACKGROUND);
			super.mouseExited(e);
		}
	}

}
