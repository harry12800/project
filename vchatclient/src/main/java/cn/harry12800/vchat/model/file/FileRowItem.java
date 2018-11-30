package cn.harry12800.vchat.model.file;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.harry12800.j2se.style.UI;
import cn.harry12800.j2se.utils.Clip;

/**
 * 两种形态。
 * @author Yuexin
 *
 */
public class FileRowItem extends JPanel {
	boolean hover = false;
	boolean isSelect = true;
	boolean isDir = false;
	private int w;
	private int h;
	public Builder builder;

	static class Builder {
		public boolean isDir = false;
		public Image image = null;
		public int borderRadius = 0;
		public String url;
		public JLabel icon = new JLabel();
		public JLabel text = new JLabel();
		public File file;
	}

	public FileRowItem(int w, int h, final Builder builder) {
		this.w = w;
		this.h = h;
		this.builder = builder;
		setPreferredSize(new Dimension(w, h));
		setSize(w, h);
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(null);
		setMinimumSize(new Dimension(w, h));
		setMaximumSize(new Dimension(w, h));
		builder.text.setForeground(Color.WHITE);
		builder.icon.setOpaque(false);
		//		builder.icon.setIcon(builder.image);
		builder.text.setOpaque(false);
		builder.icon.setBounds(3, 3, 14, 14);
		builder.text.setBounds(h, 0, w - 21, h);
		builder.text.setFont(UI.微软雅黑Font);

		add(builder.icon);
		add(builder.text);
		setOpaque(false);
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				Color color = new Color(255, 255, 255);
				setForeground(color);
				hover = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Color color = new Color(186, 186, 186);
				setForeground(color);
				hover = false;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
				}
				super.mouseReleased(e);
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		builder.text.setForeground(Color.WHITE);
		if (isSelect) {
			g2d.setColor(UI.foreColor);
			g2d.fillRoundRect(0, 0, w, h, 0, 0);
		} else if (hover) {
			g2d.setColor(UI.hoverColor);
			g2d.fillRoundRect(0, 0, w, h, 0, 0);
			builder.text.setForeground(Color.BLACK);
		} else {
			if (builder.file.exists())
				g2d.setColor(UI.backColor);
			else {
				g2d.setColor(UI.voidColor);
			}
			//			g2d.fillRoundRect(0, 0, w , h, 0, 0);
		}
		g2d.drawImage(builder.image, 3, 3, h - 6, h - 6, null);
		g2d.dispose();
		builder.text.repaint();
	}

	private static final long serialVersionUID = 1L;

	public static Builder createFileBuilder(File file) {
		Builder builder = new Builder();
		ImageIcon bigIcon = Clip.getBigIcon(file);
		builder.file = file;
		if (bigIcon != null) {
			builder.image = bigIcon.getImage();
			//			BufferedImage sBufferedImage = (BufferedImage) bigIcon.getImage();
			//			try {
			//				FileUtils.createDirectory("c://a");
			//				ImageIO.write(sBufferedImage, "png",
			//						new File("c:\\a\\" + Math.random() * 100 + ".png"));
			//			} catch (IOException e) {
			//				e.printStackTrace();
			//			}
		}
		return builder;
	}
}
