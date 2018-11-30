package cn.harry12800.vchat.adapter;

import javax.swing.JLabel;

/**
 * Created by harry12800 on 17-5-30.
 */
public class ContactsHeaderViewHolder extends HeaderViewHolder {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String letter;
	public JLabel letterLabel;

	public ContactsHeaderViewHolder(String ch) {
		this.letter = ch;
	}

	public String getLetter() {
		return letter;
	}
}
