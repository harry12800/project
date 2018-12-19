package cn.harry12800.vchat.adapter.message;

import javax.swing.JLabel;

import cn.harry12800.j2se.component.rc.adapter.ViewHolder;

/**
 * Created by harry12800 on 13/06/2017.
 */
@SuppressWarnings("serial")
public class BaseMessageViewHolder extends ViewHolder {
	public JLabel avatar = new JLabel();
	public JLabel time = new JLabel();
}
