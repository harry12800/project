package cn.harry12800.vchat.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import cn.harry12800.common.module.packet.LoginPacket;
import cn.harry12800.common.module.packet.PullMsgPacket;
import cn.harry12800.common.module.user.dto.ShowAllUserResponse;
import cn.harry12800.j2se.component.rc.RCProgressBar;
import cn.harry12800.j2se.component.utils.ImageUtils;
import cn.harry12800.j2se.dialog.MessageDialog;
import cn.harry12800.j2se.module.tray.TrayUtil;
import cn.harry12800.j2se.popup.ListItem;
import cn.harry12800.j2se.popup.PopupFrame;
import cn.harry12800.j2se.style.J2seColor;
import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.style.ui.GradientProgressBarUI;
import cn.harry12800.j2se.utils.FontUtil;
import cn.harry12800.j2se.utils.IconUtil;
import cn.harry12800.j2se.utils.OSUtil;
import cn.harry12800.upgrade.PlatUpdate;
import cn.harry12800.vchat.app.Launcher;
import cn.harry12800.vchat.app.websocket.PullWebInfo;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.panels.ContactsPanel;
import cn.harry12800.vchat.panels.LeftPanel;
import cn.harry12800.vchat.panels.RightPanel;
import cn.harry12800.vchat.utils.ClipboardUtil;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Created by harry12800 on 17-5-28.
 */
@SuppressWarnings("restriction")
public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5594533338258527370L;
	public static int DEFAULT_WIDTH = 900;
	public static int DEFAULT_HEIGHT = 650;
	public static Border LIGHT_GRAY_BORDER = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

	public int currentWindowWidth = DEFAULT_WIDTH;
	public int currentWindowHeight = DEFAULT_HEIGHT;

	private LeftPanel leftPanel;
	private RightPanel rightPanel;
	RCProgressBar progressBar = new RCProgressBar();
	private static MainFrame context;

	private AudioStream messageSound; // 消息到来时候的提示间
	private JPanel southPanel = new JPanel();

	private MainFrame() {
		context = this;
		initComponents();
		initView();
		ImageUtils.addImage(MainFrame.class);
		// 连接WebSocket
		// startWebSocket();
		TrayUtil.getTray().setFrame(context);
		registerHotKey();
	}

	/**
	 * 播放消息提示间
	 */
	public void playMessageSound() {
		try {
			InputStream inputStream = getClass().getResourceAsStream("/wav/msg.wav");
			messageSound = new AudioStream(inputStream);
			AudioPlayer.player.start(messageSound);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void updateSystem() {
		new Thread() {
			public void run() {
				platUpdate.updateSystem();
			};
		}.start();
	}

	private void registerHotKey() {
		int SCREEN_SHOT_CODE = 10001;
		try {
			JIntellitype.getInstance().registerHotKey(SCREEN_SHOT_CODE, JIntellitype.MOD_ALT, 'V');
			JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
				@Override
				public void onHotKey(int markCode) {
					if (markCode == SCREEN_SHOT_CODE) {
						if (MainFrame.this.isVisible()) {
							MainFrame.this.setVisible(false);
						} else {
							MainFrame.this.setExtendedState(JFrame.NORMAL);
							MainFrame.this.toFront();
							MainFrame.this.setVisible(true);
						}
					}
				}
			});
		} catch (Exception e) {
			System.err.println("热键已经被注册！");
		}
	}

	public void showPopup1(PopupMenu menu, TrayIcon trayIcon2, Point point) {
		//		menu.setBorder(new LineBorder(Colors.SCROLL_BAR_TRACK_LIGHT));
		//		menu.setBackground(Colors.FONT_WHITE);
		//		menu.setBorder(LIGHT_GRAY_BORDER);
		MenuItem mit0 = new MenuItem("打开主界面");
		MenuItem mit1 = new MenuItem("版本更新");
		MenuItem mit2 = new MenuItem("退出");
		menu.add(mit0);
		menu.add(mit1);
		menu.addSeparator();
		menu.add(mit2);
		int defaultWidth = 150;
		int h = 79;
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int x = point.x;
		int y = point.y;
		if (x < 0) {
			x = 0;
		}
		if (x > size.width - defaultWidth) {
			x = point.x - defaultWidth;
		}
		if (y > size.height - h) {
			y -= h;
		}
		//		menu.show(origin, x, y);
		setVisible(true);
		requestFocus();
	}

	static PopupFrame popupFrame = null;

	protected synchronized static void showPopup(Point point) {
		if (popupFrame == null) {
			popupFrame = new PopupFrame();
			ListItem font = new ListItem("选择字体", 188, 25);
			PopupFrame fontPop = new PopupFrame();
			font.addPop(fontPop);
			ListItem font1 = new ListItem("微软雅黑", 188, 25);
			ListItem font2 = new ListItem("萝莉体", 188, 25);
			ListItem font3 = new ListItem("华文行楷", 188, 25);
			ListItem font4 = new ListItem("汉仪舒同体简", 188, 25);
			ListItem font5 = new ListItem("汉仪中圆繁", 188, 25);
			ListItem font6 = new ListItem("汉仪小隶书简", 188, 25);
			ListItem showItem = new ListItem("打开主面板", 188, 25);
			fontPop.addItem(font1);
			fontPop.addItem(font2);
			fontPop.addItem(font3);
			fontPop.addItem(font4);
			fontPop.addItem(font5);
			fontPop.addItem(font6);
			font1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//					J2seFont.setDefinedFont(J2seFont.微软雅黑);
					MainFrame.getContext().repaint();
				}
			});
			font2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//					J2seFont.setDefinedFont(J2seFont.萝莉体);
					MainFrame.getContext().repaint();
				}
			});
			font3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//					J2seFont.setDefinedFont(J2seFont.华文行楷);
					MainFrame.getContext().repaint();
				}
			});
			font4.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//					J2seFont.setDefinedFont(J2seFont.汉仪舒同体简);
					MainFrame.getContext().repaint();
				}
			});
			font5.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//					J2seFont.setDefinedFont(J2seFont.汉仪中圆繁);
					MainFrame.getContext().repaint();
				}
			});
			font6.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//					J2seFont.setDefinedFont(J2seFont.汉仪小隶书简);
					MainFrame.getContext().repaint();
				}
			});
			ListItem styleItem = new ListItem("选择风格", 188, 25);
			ListItem ssItem = new ListItem("默认风格", 188, 25);
			ListItem blueItem = new ListItem("蓝色风格", 188, 25);
			ListItem pinkItem = new ListItem("红色风格", 188, 25);
			PopupFrame stylePop = new PopupFrame();
			styleItem.addPop(stylePop);
			stylePop.addItem(ssItem);
			stylePop.addItem(blueItem);
			stylePop.addItem(pinkItem);

			ListItem updateItem = new ListItem("版本更新", 188, 25);
			ListItem exitItem = new ListItem("退出", 188, 25);

			showItem.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					MainFrame.getContext().setVisible(true);
				}
			});
			exitItem.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JIntellitype.getInstance().cleanUp();
					System.exit(1);
				}
			});
			blueItem.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					J2seColor.setBackgroudColor(Color.BLUE);
					MainFrame.getContext().repaint();
				}
			});
			ssItem.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					J2seColor.setBackgroudColor(new Color(153, 133, 245));
					MainFrame.getContext().repaint();
				}
			});
			pinkItem.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					J2seColor.setBackgroudColor(Color.PINK);
					MainFrame.getContext().repaint();
				}
			});
			updateItem.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//					MainFrame.getContext().updateSystem();
				}
			});
			popupFrame.addItem(font);
			popupFrame.addItem(styleItem);
			popupFrame.addSeparator();
			popupFrame.addItem(showItem);
			popupFrame.addItem(updateItem);
			popupFrame.addSeparator();
			popupFrame.addItem(exitItem);
			popupFrame.show(point);
		} else {
			popupFrame.show(point);
		}
	}

	public static final String updateServerPath = "http://120.78.177.24:8000/download?path=";

	//	public void download() {
	//		new Thread(new Runnable() {
	//			@Override
	//			public void run() {
	//				DownloadTask task = new DownloadTask(new HttpUtil.ProgressListener() {
	//					@Override
	//					public void onProgress(int progress) {
	//						progressBar.setValue(progress);
	//					}
	//				});
	//
	//				task.setListener(new HttpResponseListener<byte[]>() {
	//					@Override
	//					public void onResult(byte[] ret) {
	//						saveFile(ret);
	//					}
	//				});
	//
	//				task.execute(updateServerPath);
	//			}
	//		}).start();
	//	}

	//	private UpdateResultListener updateResultListener;

	/**
	 * 保存下载文件
	 *
	 * @param ret
	 */
	//	private void saveFile(byte[] ret) {
	//		if (ret == null) {
	//			updateResultListener.onFailed();
	//			return;
	//		}
	//
	//		File oldFile = new File("wechat.jar");
	//		if (oldFile.exists()) {
	//			oldFile.renameTo(new File("wechat_old.jar"));
	//		}
	//
	//		File file = new File("wechat.jar");
	//		try (FileOutputStream outputStream = new FileOutputStream(file);) {
	//			outputStream.write(ret);
	//			System.out.println("文件保存在：" + file.getAbsolutePath());
	//
	//			if (updateResultListener != null) {
	//				updateResultListener.onSuccess();
	//			}
	//		} catch (Exception e) {
	//			File oFile = new File("wechat_old.jar");
	//			oFile.renameTo(new File("wechat.jar"));
	//
	//			JOptionPane.showMessageDialog(null, "更新失败，正在还原...", "更新失败", JOptionPane.ERROR_MESSAGE);
	//			if (updateResultListener != null) {
	//				updateResultListener.onFailed();
	//			}
	//
	//			e.printStackTrace();
	//		}
	//	}

	/**
	 * 清除剪切板缓存文件
	 */
	public void clearClipboardCache() {
		ClipboardUtil.clearCache();
	}

	public static MainFrame getContext() {
		if (context == null) {
			context = new MainFrame();
		}
		return context;
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		setTitle(Launcher.currentUser.getUsername() + "-聊天笔记");
		// 任务栏图标
		if (OSUtil.getOsType() != OSUtil.Mac_OS) {
			setIconImage(IconUtil.getIcon(this, "/image/ic_launcher.png").getImage());
		}

		UIManager.put("Label.font", FontUtil.getDefaultFont());
		UIManager.put("Panel.font", FontUtil.getDefaultFont());
		UIManager.put("TextArea.font", FontUtil.getDefaultFont());

		UIManager.put("Panel.background", Colors.WINDOW_BACKGROUND);
		UIManager.put("CheckBox.background", Colors.WINDOW_BACKGROUND);

		leftPanel = new LeftPanel();
		leftPanel.setPreferredSize(new Dimension(260, currentWindowHeight));

		rightPanel = new RightPanel();
		String tip = "账号：" + Launcher.currentUser.getUsername() + "\r\nAuthor：harry12800\r\nQQ:804151219\r\n开发者常用功能";
		TrayUtil.getTray().setTip(tip);
		MenuItem mit1 = new MenuItem("版本更新");
		mit1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.getContext().updateSystem();
			}
		});
		TrayUtil.getTray().addMenuItem(mit1);
		
		if("周国柱".equals(Launcher.currentUser.getUsername())){
			PullWebInfo.pull();
			MenuItem mit2= new MenuItem("系统管理");
			mit2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					platUpdate.pubVersion();
				}
			});
			TrayUtil.getTray().addMenuItem(mit2);
		}
	}

	private void initView() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

		if (OSUtil.getOsType() != OSUtil.Mac_OS) {
			// 隐藏标题栏
			setUndecorated(true);

			String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
			try {
				UIManager.setLookAndFeel(windows);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		setListeners();

		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
		//		south.setVisible(false);
		progressBar.setMaximum(100);
		progressBar.setMinimum(0);
		progressBar.setValue(50);
		progressBar.setUI(new GradientProgressBarUI());
		southPanel.setLayout(new GridBagLayout());
		southPanel.setPreferredSize(new Dimension(currentWindowWidth, 10));
		southPanel.add(progressBar, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1));
		//		progressBarPanel.add(panel, new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(1, 1));
		add(southPanel, BorderLayout.SOUTH);
		centerScreen();
		startPlatUpdate();
		startPullMsg();
	}

	private void startPullMsg() {
		try {
			// 构建请求
			PullMsgPacket p = new PullMsgPacket(Launcher.currentUser.getId());
			Launcher.client.sendRequest(p.requestPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	PlatUpdate platUpdate = PlatUpdate.getInstance();

	public void startPlatUpdate() {
		platUpdate.startPlatUpdate();
	}

	/**
	 * 使窗口在屏幕中央显示
	 */
	private void centerScreen() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		this.setLocation((tk.getScreenSize().width - currentWindowWidth) / 2,
				(tk.getScreenSize().height - currentWindowHeight) / 2);
	}

	private void setListeners() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				currentWindowWidth = (int) e.getComponent().getBounds().getWidth();
				currentWindowHeight = (int) e.getComponent().getBounds().getHeight();
			}
		});
	}

	@Override
	public void dispose() {
		// 移除托盘图标
		SystemTray.getSystemTray().remove(TrayUtil.getTray().trayIcon);
		super.dispose();
	}

	public void showAllUser(cn.harry12800.common.module.packet.PullAllUserPacket.Response body) {
		ContactsPanel.getContext().initData(body);
	}
	public void alert(String info) {
		new MessageDialog(MainFrame.getContext(), "温馨提示", info);
	}

	public void showProssbar(boolean visible) {
		southPanel.setVisible(visible);
	}

	public void reLogin() {
		try {
			LoginPacket p = new LoginPacket(Launcher.currentUser.getUserId(), Launcher.currentUser.getPassword());
			Launcher.client.sendRequest(p.requestPacket);
		} catch (Exception e1) {
			e1.printStackTrace();
			new MessageDialog(MainFrame.getContext(), "提示", "无法连接服务器");
		}
	}
}
