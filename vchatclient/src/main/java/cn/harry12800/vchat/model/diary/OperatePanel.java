package cn.harry12800.vchat.model.diary;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import cn.harry12800.j2se.component.ClickAction;
import cn.harry12800.j2se.component.MButton;
import cn.harry12800.j2se.style.UI;

@SuppressWarnings("serial")
public class OperatePanel extends JPanel {
	MButton ok = new MButton("ok", 80, 30);

	public OperatePanel(final Window diaryScanDialog) {
		add(ok);
		ok.addMouseListener(new ClickAction(ok) {
			@Override
			public void leftClick(MouseEvent e) {
				diaryScanDialog.dispose();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(UI.backColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		Graphics2D g2d = (Graphics2D) g.create();
		GradientPaint p2 = new GradientPaint(0, 0, new Color(255, 255, 255, 255), getWidth(), 0,
				new Color(255, 255, 255, 0));
		g2d.setPaint(p2);
		g2d.drawLine(2, 2, getWidth() - 2, 2);
	}
}
