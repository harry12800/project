package cn.harry12800.vchat.components.message;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.plaf.basic.BasicMenuItemUI;

import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.FontUtil;

/**
 * Created by harry12800 on 21/06/2017.
 */
public class RCRemindUserMenuItemUI extends BasicMenuItemUI {
	private int width;
	private int height;

	public RCRemindUserMenuItemUI() {
		this(100, 30);
	}

	public RCRemindUserMenuItemUI(int width, int height) {

		this.width = width;
		this.height = height;
	}

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		c.setPreferredSize(new Dimension(width, height));
		c.setBackground(Colors.FONT_WHITE);
		c.setFont(FontUtil.getDefaultFont(12));
		selectionForeground = Colors.FONT_BLACK;
		selectionBackground = Colors.SCROLL_BAR_TRACK_LIGHT;
		c.setBorder(null);
	}

	@Override
	protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text) {
		g.setColor(Colors.FONT_BLACK);
		Rectangle newRect = new Rectangle(28, textRect.y, textRect.width, textRect.height);
		super.paintText(g, menuItem, newRect, text);
	}
}
