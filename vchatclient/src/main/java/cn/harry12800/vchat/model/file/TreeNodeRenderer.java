package cn.harry12800.vchat.model.file;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class TreeNodeRenderer extends DefaultTreeCellRenderer {
	// 通过mouseEnter判定当前鼠标是否悬停
	private boolean mouseEnter = false;

	public TreeNodeRenderer() {
		super();
	}

	private static final long serialVersionUID = 1L;
	public static int mouseRow = -1;

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		// 执行父类原型操作
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		//通过mouseRow判断鼠标是否悬停在当前行
		if (mouseRow == row) {
			mouseEnter = true;
		} else {
			mouseEnter = false;
		}
		if (value instanceof FileNode) {
			if (selected)
				return ((FileNode) value).setSelect(selected, mouseEnter);
			else {
				return ((FileNode) value).setSelect(selected, mouseEnter);
			}
		}
		return this;
	}

}
