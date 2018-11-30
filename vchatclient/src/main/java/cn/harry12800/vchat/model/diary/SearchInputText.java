package cn.harry12800.vchat.model.diary;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import cn.harry12800.j2se.action.EnterAction;
import cn.harry12800.j2se.component.InputText;
import cn.harry12800.j2se.component.utils.ImageUtils;

public class SearchInputText extends InputText {

	JButton j = new JButton();
	BufferedImage image = ImageUtils.getByName("image/search.png");
	ImageIcon ii = new ImageIcon(image);

	public SearchInputText(int x) {
		super(x);
		setLayout(null);
		j.setIcon(ii);
		j.setOpaque(false);
		j.setBorder(null);
		j.setBorderPainted(false);
		j.setFocusPainted(false);
		j.setContentAreaFilled(false);
		j.setCursor(new Cursor(Cursor.HAND_CURSOR));
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EnterAction enterAction2 = getEnterAction();
				if (enterAction2 != null)
					enterAction2.enter();
			}
		});
		add(j);
		// FocusTraversalPolicy policy = new FocusTraversalPolicy() {
		//
		// @Override
		// public Component getLastComponent(Container aContainer) {
		// // TODO Auto-generated method stub
		// return null;
		// }
		//
		// @Override
		// public Component getFirstComponent(Container aContainer) {
		// // TODO Auto-generated method stub
		// return null;
		// }
		//
		// @Override
		// public Component getDefaultComponent(Container aContainer) {
		// // TODO Auto-generated method stub
		// return null;
		// }
		//
		// @Override
		// public Component getComponentBefore(Container aContainer, Component
		// aComponent) {
		// // TODO Auto-generated method stub
		// return null;
		// }
		//
		// @Override
		// public Component getComponentAfter(Container aContainer, Component
		// aComponent) {
		// // TODO Auto-generated method stub
		// return null;
		// }
		// };
		// setFocusTraversalPolicy(policy);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void paintComponent(Graphics g) {
		int width2 = getWidth();
		j.setBounds(width2 - getHeight() + 2, 2, getHeight() - 4, getHeight() - 4);
		ii.setImage(image.getScaledInstance(getHeight() - 4, getHeight() - 4, Image.SCALE_DEFAULT));
		j.setIcon(ii);
		super.paintComponent(g);

	}
}
