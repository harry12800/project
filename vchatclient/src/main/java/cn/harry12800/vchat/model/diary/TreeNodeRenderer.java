package cn.harry12800.vchat.model.diary;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import cn.harry12800.j2se.component.utils.ImageUtils;

public class TreeNodeRenderer extends DefaultTreeCellRenderer {
	// 通过mouseEnter判定当前鼠标是否悬停
	private boolean mouseEnter = false;

	public TreeNodeRenderer() {
		super();
		setOpaque(false);
	}

	private static final long serialVersionUID = 1L;
	public static int mouseRow = -1;

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		// 执行父类原型操作
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		// 通过mouseRow判断鼠标是否悬停在当前行
		if (mouseRow == row) {
			mouseEnter = true;
		} else {
			mouseEnter = false;
		}
		if (value instanceof AricleNode) {
			if (selected)
				return ((AricleNode) value).setSelect(selected, mouseEnter);
			else {
				return ((AricleNode) value).setSelect(selected, mouseEnter);
			}
		}
		if (value instanceof CategoryNode) {
			if (expanded) {
				((CategoryNode) value).setIcon(ImageUtils.getIcon("image/arrow_down.png"));
			} else {
				((CategoryNode) value).setIcon(ImageUtils.getIcon("image/arrow_left.png"));
			}
			return ((CategoryNode) value).getView(mouseEnter);
		}

		return this;
	}

}
