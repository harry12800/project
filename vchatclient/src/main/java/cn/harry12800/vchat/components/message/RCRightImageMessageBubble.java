package cn.harry12800.vchat.components.message;

import java.awt.Insets;

/**
 * 右侧图片聊天气泡
 */
@SuppressWarnings("serial")
public class RCRightImageMessageBubble extends RCAttachmentMessageBubble {
	public RCRightImageMessageBubble() {
		NinePatchImageIcon backgroundNormal = new NinePatchImageIcon(this.getClass().getResource("/image/right.9.png"));
		NinePatchImageIcon backgroundActive = new NinePatchImageIcon(
				this.getClass().getResource("/image/right_active.9.png"));
		setBackgroundNormalIcon(backgroundNormal);
		setBackgroundActiveIcon(backgroundActive);
		setBackgroundIcon(backgroundNormal);
	}

	@Override
	public Insets getInsets() {
		return new Insets(2, 2, 5, 8);
	}
}
