package cn.harry12800.vchat.model.diary;

import java.awt.Component;
import java.io.File;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

import cn.harry12800.j2se.component.utils.ImageUtils;
import cn.harry12800.j2se.style.UI;
import cn.harry12800.j2se.utils.JsonUtils;
import cn.harry12800.vchat.entity.Diary;
import cn.harry12800.vchat.model.diary.AricleItemPanel.Builder;
import cn.harry12800.vchat.panels.DiaryPanel;

public class AricleNode extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Icon icon;
	public AricleItemPanel plainButton = null;
	private File file;
	public Builder builder;
	public Date date;
	public Diary aritcle;
	public DiaryPanel diaryPanel;

	public AricleNode(File file, Diary aritcle) {
		setFile(file, aritcle);
		builder = AricleItemPanel.createBuilder(this, UI.backColor);
		builder.image = ImageUtils.getByName("image/arrow_left.png");
		plainButton = new AricleItemPanel(250, 30, builder);
		this.date = this.aritcle.getUpdateTime();
		if (this.date == null) {
			date = new Date();
		}
		plainButton.updatedateL.setText(RelativeDateFormat.format(date));
		plainButton.setBounds(0, 0, 230, 30);
	}

	public Component getView() {
		plainButton.updatedateL.setText(RelativeDateFormat.format(date));
		return plainButton;
	}

	public Icon getIcon() {
		return icon;
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
	 * @param aritcle2
	 */
	public void setFile(File file, Diary aritcle2) {
		this.file = file;
		// System.out.println(file);
		try {
			this.aritcle = JsonUtils.string2Json(file, Diary.class);
			if (this.aritcle == null)
				this.aritcle = new Diary();
			aritcle.setSort(aritcle2.getSort());
			// System.out.println(aritcle);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return file.toString();
	}

	public Component setSelect(boolean selected, boolean mouseEnter) {
		plainButton.updatedateL.setText(RelativeDateFormat.format(date));
		plainButton.isSelect = selected;
		plainButton.hover = mouseEnter;
		return plainButton;
	}

}
