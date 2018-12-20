package cn.harry12800.common.core.packet;

import io.protostuff.Tag;

public class DepartmentEntity {
	@Tag(1)
	public String id;
	@Tag(2)
	public String title;
	@Tag(3)
	public String description;
	@Tag(4)
	public String parentId;
	@Tag(5)
	public String leaderId;
	@Tag(6)
	public int status;
//	@Tag(7)
//	public PinYinElement pinyinElement = new PinYinElement();
//	@Tag(8)
//	public SearchElement searchElement = new SearchElement();

	@Override
	public String toString() {
		return "id:" + id + ", title:" + title + ", description:" + description
				+ ", parentId:" + parentId + ", leaderId:" + leaderId
				+ ", status:" + String.valueOf(status);
	}

}
