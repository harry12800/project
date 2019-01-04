package cn.harry12800.common.core.packet;

import java.util.ArrayList;
import java.util.List;

import io.protostuff.Tag;

public class GroupEntity {
	@Tag(1)
	public String id;
	@Tag(2)
	public String name;
	@Tag(3)
	public String avatarUrl;
	@Tag(4)
	public String creatorId;
	@Tag(5)
	public int type; // 1--normal group, 2--temporary group
	@Tag(6)
	public int updated;
	@Tag(7)
	public List<String> memberIdList = new ArrayList<String>();

//	@Tag(8)
//	public PinYinElement pinyinElement = new PinYinElement();
//	@Tag(9)
//	public SearchElement searchElement = new SearchElement();

	@Override
	public String toString() {
		String ret = "GroupEntity [id=" + id + ", name=" + name + ", avatar="
				+ avatarUrl + ", creatorId=" + creatorId + ", type=" + type
				+ ", updated=" + updated + "memberCnt=" + memberIdList.size()
				+ "]";

		StringBuilder memberString = new StringBuilder("member ids:");
		for (String id : memberIdList) {
			memberString.append(id + ",");
		}

		return ret + memberString;
	}

}
