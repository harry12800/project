package cn.harry12800.vchat.panels;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.harry12800.common.module.packet.ResetPasswordPacket;
import cn.harry12800.j2se.component.rc.RCButton;
import cn.harry12800.j2se.component.rc.RCPasswordField;
import cn.harry12800.j2se.style.layout.VerticalFlowLayout;
import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.FontUtil;
import cn.harry12800.j2se.utils.IconUtil;
import cn.harry12800.vchat.app.Launcher;

/**
 * 修改头像面板
 * <p>
 * Created by harry12800 on 23/06/2017.
 */
@SuppressWarnings("serial")
public class ChangePasswordPanel extends JPanel {
	private static ChangePasswordPanel context;
	private RCPasswordField oldTextField;
	private RCPasswordField newTextField;
	private RCPasswordField textFieldConfirm;
	private RCButton okButton;
	private JPanel contentPanel;
	private JLabel statusLabel;

	public ChangePasswordPanel() {
		context = this;

		initComponents();
		initView();
		setListener();
		newTextField.requestFocus();
	}

	private void initComponents() {
		
		
		oldTextField = new RCPasswordField();
		oldTextField.setPlaceholder("旧密码");
		oldTextField.setPreferredSize(new Dimension(200, 35));
		oldTextField.setFont(FontUtil.getDefaultFont(14));
		oldTextField.setForeground(Colors.FONT_BLACK);
		oldTextField.setMargin(new Insets(0, 15, 0, 0));

		newTextField = new RCPasswordField();
		newTextField.setPlaceholder("新密码");
		newTextField.setPreferredSize(new Dimension(200, 35));
		newTextField.setFont(FontUtil.getDefaultFont(14));
		newTextField.setForeground(Colors.FONT_BLACK);
		newTextField.setMargin(new Insets(0, 15, 0, 0));

		textFieldConfirm = new RCPasswordField();
		textFieldConfirm.setPlaceholder("再次输入密码");
		textFieldConfirm.setPreferredSize(new Dimension(200, 35));
		textFieldConfirm.setFont(FontUtil.getDefaultFont(14));
		textFieldConfirm.setForeground(Colors.FONT_BLACK);
		textFieldConfirm.setMargin(new Insets(0, 15, 0, 0));

		okButton = new RCButton("确认修改", Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
		okButton.setPreferredSize(new Dimension(100, 35));

		statusLabel = new JLabel();
		statusLabel.setForeground(Colors.FONT_GRAY_DARKER);
		// statusLabel.setVisible(false);

		contentPanel = new JPanel();
	}

	private void initView() {
		contentPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 0, 10, true, false));
		contentPanel.add(oldTextField);
		contentPanel.add(newTextField);
		contentPanel.add(textFieldConfirm);
		contentPanel.add(okButton);
		contentPanel.add(statusLabel);

		add(contentPanel);
	}

	private void setListener() {

		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doResetPassword();

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
					doResetPassword();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		};
		newTextField.addKeyListener(keyListener);
		textFieldConfirm.addKeyListener(keyListener);
	}

	private void doResetPassword() {
		if (okButton.isEnabled()) {

			String password = new String(newTextField.getPassword());
			String oldPassword = new String(oldTextField.getPassword());
			String passwordConfirm = new String(textFieldConfirm.getPassword());

			if (password.isEmpty()) {
				showErrorMessage("请输入新密码");
				newTextField.requestFocus();
				return;
			} else if (passwordConfirm.isEmpty()) {
				showErrorMessage("请再次输入密码");
				textFieldConfirm.requestFocus();
				return;
			}

			if (!password.equals(passwordConfirm)) {
				showErrorMessage("两次输入密码不一致");
				textFieldConfirm.requestFocus();
				return;
			}

			statusLabel.setVisible(false);
			okButton.setEnabled(false);
			okButton.setIcon(IconUtil.getIcon(this, "/image/sending.gif"));
			okButton.setText("修改中...");
			ResetPasswordPacket p = new ResetPasswordPacket(Launcher.currentUser.getId(), oldPassword, password);
			try {
				Launcher.client.sendRequest(p.requestPacket);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void restoreOKButton() {
		okButton.setText("确认修改");
		okButton.setIcon(null);
		okButton.setEnabled(true);
	}

	public void showSuccessMessage() {
		statusLabel.setText("密码修改成功，请重新登录");
		statusLabel.setIcon(IconUtil.getIcon(this, "/image/check.png"));
		statusLabel.setVisible(true);
	}

	public void showErrorMessage(String message) {
		statusLabel.setText(message);
		statusLabel.setIcon(new ImageIcon(
				IconUtil.getIcon(this, "/image/fail.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
		statusLabel.setVisible(true);
	}

	public static ChangePasswordPanel getContext() {
		return context;
	}

	public void result(long ok) {
		okButton.setEnabled(true);
		if(ok == 0L){
			showSuccessMessage();
		}else if(ok==1L){
			showErrorMessage("意外信息");
		}else if(ok==2L){
			showErrorMessage("旧秘密验证错误！");
		}
	}
}
