package cn.harry12800.vchat.components;

import java.awt.Image;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TooManyListenersException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import cn.harry12800.j2se.component.imageview.ImageViewerFrame;
import cn.harry12800.vchat.components.message.FileEditorThumbnail;
import cn.harry12800.vchat.utils.ClipboardUtil;

/**
 * Created by harry12800 on 03/07/2017.
 */
@SuppressWarnings("serial")
public class RCTextEditor extends JTextPane {
	public RCTextEditor() {
		initDragArea();
	}

	@Override
	public void paste() {
		Object data = ClipboardUtil.paste();
		if (data instanceof String) {
			this.replaceSelection((String) data);
		} else if (data instanceof ImageIcon) {
			ImageIcon icon = (ImageIcon) data;
			adjustAndInsertIcon(icon);
		} else if (data instanceof java.util.List) {
			@SuppressWarnings("unchecked")
			java.util.List<Object> list = (java.util.List<Object>) data;
			for (Object obj : list) {
				// 图像
				if (obj instanceof ImageIcon) {
					adjustAndInsertIcon((ImageIcon) obj);
				}
				// 文件
				else if (obj instanceof String) {
					FileEditorThumbnail thumbnail = new FileEditorThumbnail((String) obj);
					this.insertComponent(thumbnail);
				}
			}
		}
	}

	private void initDragArea() {
		DropTarget dt = new DropTarget();
		FileDropTargetAdapter fileDropTargetAdapter = new FileDropTargetAdapter(this);
		dt.setComponent(this);
		dt.setDefaultActions(DnDConstants.ACTION_COPY_OR_MOVE);
		try {
			dt.addDropTargetListener(fileDropTargetAdapter);
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 插入图片到编辑框，并自动调整图片大小
	 *
	 * @param icon
	 */
	private void adjustAndInsertIcon(ImageIcon icon) {
		String path = icon.getDescription();
		int iconWidth = icon.getIconWidth();
		int iconHeight = icon.getIconHeight();
		float scale = iconWidth * 1.0F / iconHeight;
		boolean needToScale = false;
		int max = 100;
		if (iconWidth >= iconHeight && iconWidth > max) {
			iconWidth = max;
			iconHeight = (int) (iconWidth / scale);
			needToScale = true;
		} else if (iconHeight >= iconWidth && iconHeight > max) {
			iconHeight = max;
			iconWidth = (int) (iconHeight * scale);
			needToScale = true;
		}

		JLabel label = new JLabel();
		if (needToScale) {
			ImageIcon scaledIcon = new ImageIcon(
					icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH));
			scaledIcon.setDescription(icon.getDescription());
			// this.insertIcon(scaledIcon);
			label.setIcon(scaledIcon);
		} else {
			// this.insertIcon(icon);
			label.setIcon(icon);
		}

		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 双击预览选中的图片
				if (e.getClickCount() == 2) {
					ImageViewerFrame frame = new ImageViewerFrame(path);
					frame.setVisible(true);
				}
				super.mouseClicked(e);
			}
		});

		insertComponent(label);

	}
}