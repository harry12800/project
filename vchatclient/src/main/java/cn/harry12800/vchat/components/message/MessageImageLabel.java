package cn.harry12800.vchat.components.message;

import javax.swing.JLabel;

/**
 * Created by harry12800 on 27/06/2017.
 */
@SuppressWarnings("serial")
public class MessageImageLabel extends JLabel {
	private Object tag;

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}
}
