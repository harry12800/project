package cn.harry12800.vchat.model.diary;

import java.awt.Component;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.tree.DefaultMutableTreeNode;

import cn.harry12800.j2se.component.utils.ImageUtils;
import cn.harry12800.j2se.style.UI;
import cn.harry12800.vchat.model.diary.CatalogItemPanel.Builder;

public class CategoryNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Icon icon;
	public JLabel picture;
	public JLabel nickName;
	public CatalogItemPanel panelItem = null;
	private File file;

	public CategoryNode(File file) {
		this.file = file;
		icon = ImageUtils.getIcon("image/arrow_left.png");
		Builder createBgColorBuilder = CatalogItemPanel.createBuilder(this, UI.backColor);
		createBgColorBuilder.image = ImageUtils.getByName("image/arrow_left.png");
		panelItem = new CatalogItemPanel(250, 30, createBgColorBuilder);
		panelItem.setBounds(0, 0, 250, 30);
	}

	public Component getView() {
		return panelItem;
	}

	/**
	 * 获取file
	 * 
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * 设置file
	 * 
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
		panelItem.text.setText(file.getName());
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		panelItem.picture.setIcon(icon);
		this.icon = icon;
	}

	@Override
	public String toString() {
		return file.getName();
	}

	public Component setSelect(boolean selected, boolean mouseEnter) {
		panelItem.hover = mouseEnter;
		return panelItem;
	}

	public Component getView(boolean mouseEnter) {
		panelItem.hover = mouseEnter;
		return panelItem;
	}
}
