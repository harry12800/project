package cn.harry12800.vchat.panels;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import cn.harry12800.common.core.model.Request;
import cn.harry12800.common.module.ChatCmd;
import cn.harry12800.common.module.ModuleId;
import cn.harry12800.common.module.chat.dto.PrivateChatRequest;
import cn.harry12800.j2se.component.rc.RCButton;
import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.FontUtil;
import cn.harry12800.j2se.utils.IconUtil;
import cn.harry12800.j2se.utils.OSUtil;
import cn.harry12800.vchat.app.Launcher;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.components.RCTextEditor;
import cn.harry12800.vchat.components.ScrollUI;
import cn.harry12800.vchat.components.message.ChatEditorPopupMenu;
import cn.harry12800.vchat.frames.ScreenShot;
import cn.harry12800.vchat.listener.ExpressionListener;

/**
 * Created by harry12800 on 17-5-30.
 */
@SuppressWarnings("serial")
public class MessageEditorPanel extends ParentAvailablePanel {
	private JPanel controlLabel;
	private JLabel fileLabel;
	private JLabel expressionLabel;
	private JLabel cutLabel;
	private JLabel shakeLabel;
	private JScrollPane textScrollPane;
	private RCTextEditor textEditor;
	private JPanel sendPanel;
	private RCButton sendButton;
	private ChatEditorPopupMenu chatEditorPopupMenu;

	private ImageIcon fileNormalIcon;
	private ImageIcon fileActiveIcon;

	private ImageIcon emotionNormalIcon;
	private ImageIcon emotionActiveIcon;

	private ImageIcon cutNormalIcon;
	private ImageIcon cutActiveIcon;

	private ImageIcon shakeIcon;
	private ImageIcon shakeActiveIcon;

	private ExpressionPopup expressionPopup;

	public MessageEditorPanel(JPanel parent) {
		super(parent);

		initComponents();
		initView();
		setListeners();

		if (OSUtil.getOsType() == OSUtil.Windows) {
			registerHotKey();
		}
	}


	private void initComponents() {
		Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
		controlLabel = new JPanel();
		controlLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 7));

		fileLabel = new JLabel();
		fileNormalIcon = IconUtil.getIcon(this, "/image/file.png");
		fileActiveIcon = IconUtil.getIcon(this, "/image/file_active.png");
		fileLabel.setIcon(fileNormalIcon);
		fileLabel.setCursor(handCursor);
		fileLabel.setToolTipText("发送文件/图片");

		shakeLabel = new JLabel();
		shakeIcon = IconUtil.getIcon(this, "/image/shake.png");
		shakeActiveIcon = IconUtil.getIcon(this, "/image/shake_active.png");
		shakeLabel.setIcon(shakeIcon);
		shakeLabel.setCursor(handCursor);
		shakeLabel.setToolTipText("发送抖动");

		expressionLabel = new JLabel();
		emotionNormalIcon = IconUtil.getIcon(this, "/image/emotion.png");
		emotionActiveIcon = IconUtil.getIcon(this, "/image/emotion_active.png");
		expressionLabel.setIcon(emotionNormalIcon);
		expressionLabel.setCursor(handCursor);
		expressionLabel.setToolTipText("表情");

		cutLabel = new JLabel();
		cutNormalIcon = IconUtil.getIcon(this, "/image/cut.png");
		cutActiveIcon = IconUtil.getIcon(this, "/image/cut_active.png");
		cutLabel.setIcon(cutNormalIcon);
		cutLabel.setCursor(handCursor);
		if (OSUtil.getOsType() == OSUtil.Windows) {
			cutLabel.setToolTipText("截图(Alt + S)");
		} else {
			cutLabel.setToolTipText("截图(当前系统下不支持全局热键)");
		}

		textEditor = new RCTextEditor();
		textEditor.setBackground(Colors.WINDOW_BACKGROUND);
		textEditor.setFont(FontUtil.getDefaultFont(14));
		textEditor.setMargin(new Insets(0, 15, 0, 0));
		textScrollPane = new JScrollPane(textEditor);
		textScrollPane.getVerticalScrollBar().setUI(new ScrollUI(Colors.SCROLL_BAR_THUMB, Colors.WINDOW_BACKGROUND));
		textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		textScrollPane.setBorder(null);

		sendPanel = new JPanel();
		sendPanel.setLayout(new BorderLayout());

		sendButton = new RCButton("发 送");
		sendPanel.add(sendButton, BorderLayout.EAST);
		sendButton.setForeground(Colors.DARKER);
		sendButton.setFont(FontUtil.getDefaultFont(13));
		sendButton.setPreferredSize(new Dimension(75, 23));
		sendButton.setToolTipText("Enter发送消息，Ctrl+Enter换行");

		chatEditorPopupMenu = new ChatEditorPopupMenu();

		expressionPopup = new ExpressionPopup();
	}

	private void initView() {
		this.setLayout(new GridBagLayout());

		controlLabel.add(expressionLabel);
		controlLabel.add(fileLabel);
		controlLabel.add(cutLabel);
		controlLabel.add(shakeLabel);

		add(controlLabel, new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(1, 1));
		add(textScrollPane, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 15));
		add(sendPanel, new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 0, 10, 10));
	}
	private void registerHotKey() {
		int SCREEN_SHOT_CODE = 10004;
		try {
			JIntellitype.getInstance().registerHotKey(SCREEN_SHOT_CODE, JIntellitype.MOD_ALT, 'S');
			JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
				@Override
				public void onHotKey(int markCode) {
					if (markCode == SCREEN_SHOT_CODE) {
						screenShot();
					}
				}
			});
		} catch (Exception e) {
			System.err.println("截图热键已经被注册！");
		}
	}

	private void setListeners() {
		shakeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				shakeLabel.setIcon(shakeActiveIcon);
				super.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				shakeLabel.setIcon(shakeIcon);
				super.mouseExited(e);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				sendShake();
				super.mouseClicked(e);
			}
		});

		fileLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				fileLabel.setIcon(fileActiveIcon);
				super.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				fileLabel.setIcon(fileNormalIcon);
				super.mouseExited(e);
			}
		});

		expressionLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				expressionLabel.setIcon(emotionActiveIcon);
				super.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				expressionLabel.setIcon(emotionNormalIcon);
				super.mouseExited(e);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				expressionPopup.show((Component) e.getSource(), e.getX() - 200, e.getY() - 320);
				super.mouseClicked(e);
			}
		});

		cutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cutLabel.setIcon(cutActiveIcon);
				super.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				cutLabel.setIcon(cutNormalIcon);
				super.mouseExited(e);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				screenShot();
				super.mouseClicked(e);
			}
		});

		textEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					chatEditorPopupMenu.show((Component) e.getSource(), e.getX(), e.getY());
				}
				super.mouseClicked(e);
			}
		});
	}

	protected void sendShake() {
		try {
			PrivateChatRequest request = new PrivateChatRequest();
			request.setContext("抖动");
			long idByUserId = Launcher.getIdByUserId(ChatPanel.getContext().roomId);
			request.setTargetUserId(idByUserId);
			// 构建请求
			Request req = Request.valueOf(ModuleId.CHAT, ChatCmd.PRIVATE_CHAT, request.getBytes());
			Launcher.client.sendRequest(req);
		} catch (Exception e) {

		}
	}

	private void screenShot() {
		try {
			ScreenShot ssw = new ScreenShot();
			ssw.setVisible(true);
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}

	public void setExpressionListener(ExpressionListener listener) {
		expressionPopup.setExpressionListener(listener);
	}

	public RCTextEditor getEditor() {
		return textEditor;
	}

	public JButton getSendButton() {
		return sendButton;
	}

	public JLabel getUploadFileLabel() {
		return fileLabel;
	}

}
