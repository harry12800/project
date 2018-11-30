package cn.harry12800.vchat.adapter.message;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.style.ui.GradientProgressBarUI;
import cn.harry12800.j2se.utils.FontUtil;
import cn.harry12800.vchat.components.RCProgressBar;
import cn.harry12800.vchat.components.SizeAutoAdjustTextArea;
import cn.harry12800.vchat.components.message.AttachmentPanel;
import cn.harry12800.vchat.components.message.RCAttachmentMessageBubble;
import cn.harry12800.vchat.frames.MainFrame;

/**
 * Created by harry12800 on 16/06/2017.
 */
@SuppressWarnings("serial")
public class MessageAttachmentViewHolder extends BaseMessageViewHolder {
	public SizeAutoAdjustTextArea attachmentTitle;
	public RCProgressBar progressBar = new RCProgressBar(); // 进度条
	public JPanel timePanel = new JPanel(); // 时间面板
	public JPanel messageAvatarPanel = new JPanel(); // 消息 + 头像组合面板
	public AttachmentPanel attachmentPanel = new AttachmentPanel(); // 附件面板
	public JLabel attachmentIcon = new JLabel(); // 附件类型icon
	public JLabel sizeLabel = new JLabel();
	public RCAttachmentMessageBubble messageBubble;

	public MessageAttachmentViewHolder() {
		initComponents();
		setListeners();
	}

	private void setListeners() {
		MouseAdapter listener = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				messageBubble.setActiveStatus(true);
				super.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				messageBubble.setActiveStatus(false);
				super.mouseExited(e);
			}
		};

		attachmentPanel.addMouseListener(listener);
		attachmentTitle.addMouseListener(listener);

	}

	private void initComponents() {
		int maxWidth = (int) (MainFrame.getContext().currentWindowWidth * 0.427);
		attachmentTitle = new SizeAutoAdjustTextArea(maxWidth);

		timePanel.setBackground(Colors.WINDOW_BACKGROUND);
		messageAvatarPanel.setBackground(Colors.WINDOW_BACKGROUND);

		time.setForeground(Colors.FONT_GRAY);
		time.setFont(FontUtil.getDefaultFont(12));

		attachmentPanel.setOpaque(false);

		progressBar.setMaximum(100);
		progressBar.setMinimum(0);
		progressBar.setValue(100);
		progressBar.setUI(new GradientProgressBarUI());
		progressBar.setVisible(false);

		sizeLabel.setFont(FontUtil.getDefaultFont(12));
		sizeLabel.setForeground(Colors.FONT_GRAY);
	}
}
