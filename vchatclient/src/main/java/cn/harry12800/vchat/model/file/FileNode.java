package cn.harry12800.vchat.model.file;

import java.awt.Component;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.tree.DefaultMutableTreeNode;

import cn.harry12800.vchat.model.file.FileRowItem.Builder;

public class FileNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Icon icon;
	public JLabel picture;
	public JLabel nickName;
	public FileRowItem panelItem = null;
	public Builder builder;
	public OpenFileType file;
	public OpenFilePanel panel;

	public FileNode(OpenFilePanel httpUrlNewPanel, OpenFileType of) {
		file = of;
		panel = httpUrlNewPanel;
		builder = FileRowItem.createFileBuilder(new File(of.getPath()));
		builder.text.setText(of.getName());
		panelItem = new FileRowItem(200, 30, this.builder);
		panelItem.setBounds(0, 0, 200, 30);
	}

	public Component getView() {
		return panelItem;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		panelItem.builder.icon.setIcon(icon);
		this.icon = icon;
	}

	public Component setSelect(boolean selected, boolean mouseEnter) {
		panelItem.isSelect = selected;
		panelItem.hover = mouseEnter;
		return panelItem;
	}

	public Component getView(boolean mouseEnter) {
		panelItem.hover = mouseEnter;
		return panelItem;
	}
}
