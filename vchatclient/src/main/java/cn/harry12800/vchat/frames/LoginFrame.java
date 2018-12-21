package cn.harry12800.vchat.frames;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.apache.ibatis.session.SqlSession;

import cn.harry12800.common.core.model.Request;
import cn.harry12800.common.module.ModuleId;
import cn.harry12800.common.module.UserCmd;
import cn.harry12800.common.module.packet.LoginPacket;
import cn.harry12800.common.module.user.dto.LoginRequest;
import cn.harry12800.common.module.user.dto.UserResponse;
import cn.harry12800.j2se.action.AbstractMouseListener;
import cn.harry12800.j2se.component.rc.RCButton;
import cn.harry12800.j2se.component.rc.RCPasswordField;
import cn.harry12800.j2se.component.rc.RCTextField;
import cn.harry12800.j2se.module.tray.TrayUtil;
import cn.harry12800.j2se.style.layout.VerticalFlowLayout;
import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.Config;
import cn.harry12800.j2se.utils.FontUtil;
import cn.harry12800.j2se.utils.IconUtil;
import cn.harry12800.j2se.utils.OSUtil;
import cn.harry12800.vchat.app.Launcher;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.db.model.CurrentUser;
import cn.harry12800.vchat.db.service.CurrentUserService;
import cn.harry12800.vchat.utils.DbUtils;

/**
 * Created by harry12800 on 08/06/2017.
 */
@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	private static final int windowWidth = 300;
	private static final int windowHeight = 400;

	private JPanel controlPanel;
	private JLabel closeLabel;
	private JPanel editPanel;
	private RCTextField usernameField;
	private RCPasswordField passwordField;
	private RCButton loginButton;
	private JLabel statusLabel;
	private JLabel titleLabel;

	private static Point origin = new Point();

	private SqlSession sqlSession;
	private CurrentUserService currentUserService;
	private static LoginFrame context;

	public static LoginFrame getContext() {
		if (context == null) {
			System.out.println("new LoginFrame");
			context = new LoginFrame();
		}
		return context;
	}

	private LoginFrame() {
		context = this;
		initService();
		initComponents();
		initView();
		centerScreen();
		setListeners();
		initResource();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (LoginFrame.class) {
					if (!isDoLogining) {
						isDoLogining = true;
						autoLogin();
					}
				}

			}
		}).start();
	}

	private void initResource() {

		PopupMenu menu = new PopupMenu();
		MenuItem mit0 = new MenuItem("打开主界面");
		MenuItem mit2 = new MenuItem("退出");
		menu.add(mit0);
		menu.addSeparator();
		menu.add(mit2);

		mit0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TrayUtil.getTray().getFrame().setExtendedState(JFrame.NORMAL);
				TrayUtil.getTray().getFrame().toFront();
				TrayUtil.getTray().getFrame().setVisible(true);
			}
		});
		mit2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		TrayUtil.getTray().setMenu(menu);
		TrayUtil.getTray().setFrame(context);
	}

	private void autoLogin() {
		if ("true".equals(Config.getProp("autoLogin")) || Config.getProp("autoLogin") == null) {
			doLogin();
		}
	}

	public LoginFrame(String username) {
		this();
		if (username != null && !username.isEmpty()) {
			usernameField.setText(username);
		}
	}

	private void initService() {
		sqlSession = DbUtils.getSqlSession();
		currentUserService = new CurrentUserService(sqlSession);
	}

	private void initComponents() {
		setTitle("聊天笔记登录……");
		Dimension windowSize = new Dimension(windowWidth, windowHeight);
		setMinimumSize(windowSize);
		setMaximumSize(windowSize);
		setIconImage(IconUtil.getIcon(this, "/image/ic_launcher.png").getImage());
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		// controlPanel.setBounds(0,5, windowWidth, 30);

		closeLabel = new JLabel();
		closeLabel.setIcon(IconUtil.getIcon(this, "/image/close.png"));
		closeLabel.setHorizontalAlignment(JLabel.CENTER);
		// closeLabel.setPreferredSize(new Dimension(30,30));
		closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		titleLabel = new JLabel();
		titleLabel.setText("登  录");
		titleLabel.setFont(FontUtil.getDefaultFont(16));

		editPanel = new JPanel();
		editPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 0, 5, true, false));

		Dimension textFieldDimension = new Dimension(200, 35);
		usernameField = new RCTextField();
		usernameField.setPlaceholder("用户名");
		usernameField.setPreferredSize(textFieldDimension);
		usernameField.setFont(FontUtil.getDefaultFont(14));
		usernameField.setForeground(Colors.FONT_BLACK);
		usernameField.setMargin(new Insets(0, 15, 0, 0));
		usernameField.setText(Config.getProp("username"));

		passwordField = new RCPasswordField();
		passwordField.setPreferredSize(textFieldDimension);
		passwordField.setPlaceholder("密码");
		// passwordField.setBorder(new RCBorder(RCBorder.BOTTOM,
		// Colors.LIGHT_GRAY));
		passwordField.setFont(FontUtil.getDefaultFont(14));
		passwordField.setForeground(Colors.FONT_BLACK);
		passwordField.setMargin(new Insets(0, 15, 0, 0));
		passwordField.setText("123456");
		loginButton = new RCButton("登 录", Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
		loginButton.setFont(FontUtil.getDefaultFont(14));
		loginButton.setPreferredSize(new Dimension(200, 40));

		statusLabel = new JLabel();
		statusLabel.setForeground(Colors.RED);
		statusLabel.setText("密码不正确");
		statusLabel.setVisible(false);
	}

	public boolean isDoLogining = false;

	private void initView() {
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new LineBorder(Colors.LIGHT_GRAY));
		contentPanel.setLayout(new GridBagLayout());

		controlPanel.add(closeLabel);

		if (OSUtil.getOsType() != OSUtil.Mac_OS) {
			setUndecorated(true);
			contentPanel.add(controlPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1).setInsets(5, 0, 0, 0));
		}

		JPanel titlePanel = new JPanel();
		titlePanel.add(titleLabel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.add(loginButton, new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(1, 1).setInsets(10, 0, 0, 0));

		editPanel.add(usernameField);
		editPanel.add(passwordField);
		editPanel.add(buttonPanel);

		editPanel.add(statusLabel);

		add(contentPanel);
		contentPanel.add(titlePanel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(10, 10, 0, 10));
		contentPanel.add(editPanel, new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 10).setInsets(10, 10, 0, 10));
	}

	/**
	 * 使窗口在屏幕中央显示
	 */
	private void centerScreen() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		this.setLocation((tk.getScreenSize().width - windowWidth) / 2, (tk.getScreenSize().height - windowHeight) / 2);
	}

	private void setListeners() {
		closeLabel.addMouseListener(new AbstractMouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(1);
				super.mouseClicked(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				closeLabel.setBackground(Colors.LIGHT_GRAY);
				super.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				closeLabel.setBackground(Colors.WINDOW_BACKGROUND);
				super.mouseExited(e);
			}
		});

		if (OSUtil.getOsType() != OSUtil.Mac_OS) {
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					// 当鼠标按下的时候获得窗口当前的位置
					origin.x = e.getX();
					origin.y = e.getY();
				}
			});

			addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
					// 当鼠标拖动时获取窗口当前位置
					Point p = LoginFrame.this.getLocation();
					// 设置窗口的位置
					LoginFrame.this.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
				}
			});
		}

		loginButton.addMouseListener(new AbstractMouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (loginButton.isEnabled()) {
					synchronized (LoginFrame.class) {
						isDoLogining = true;
						doLogin();
					}
				}

				super.mouseClicked(e);
			}
		});

		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					doLogin();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		};
		usernameField.addKeyListener(keyListener);
		passwordField.addKeyListener(keyListener);
	}

	private void doLogin() {

		// TODO: 登录逻辑
		String name = usernameField.getText();
		String pwd = new String(passwordField.getPassword());

		if (name == null || name.isEmpty()) {
			showMessage("请输入用户，注意首字母可能为大写");
		} else if (pwd == null || pwd.isEmpty()) {
			showMessage("请输入密码");
		} else {
			statusLabel.setVisible(false);
			loginButton.setEnabled(false);
			usernameField.setEditable(false);
			passwordField.setEditable(false);
			try {
				LoginRequest loginRequest = new LoginRequest();
				LoginPacket loginPacket = new LoginPacket(name,pwd);
				Request request = Request.valueOf(ModuleId.USER, UserCmd.LOGIN, loginRequest.getBytes());
//				Launcher.client.sendRequest(request);
				Launcher.client.sendRequest(loginPacket.requestPacket);
			} catch (Exception e) {
				showMessage("无法连接服务器");
				loginButton.setEnabled(true);
				usernameField.setEditable(true);
				passwordField.setEditable(true);
			}
		}
	}

	private void showMessage(String message) {
		if (!statusLabel.isVisible()) {
			statusLabel.setVisible(true);
		}
		statusLabel.setText(message);
		loginButton.setEnabled(true);
		usernameField.setEditable(true);
		passwordField.setEditable(true);
	}

	public void loginSuccess(UserResponse userResponse) {
		this.dispose();
		context = null;
		System.out.println(userResponse);
		CurrentUser currentUser = new CurrentUser();
		currentUser.setUserId(userResponse.getUserId());
		currentUser.setUsername(userResponse.getNickName());
		currentUser.setPassword(new String(passwordField.getPassword()));
		currentUserService.insertOrUpdate(currentUser);
		Launcher.currentUser = currentUser;
		Launcher.iduserIdMaps.put(userResponse.getId(), userResponse.getUserId());
		Config.setProp("username", userResponse.getUserId());
		MainFrame frame = MainFrame.getContext();
		TrayUtil.getTray().setFrame(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void loginFail(String tipContent) {
		showMessage(tipContent);
	}
}
