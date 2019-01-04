package cn.harry12800.common.core.packet;

import io.protostuff.Tag;

public class ContactEntity {

	@Tag(1)
	public String id;
	@Tag(2)
	public String name;
	@Tag(3)
	public String nickName;
	@Tag(4)
	public String avatarUrl;
	@Tag(5)
	public String title;
	@Tag(6)
	// todo eric, change it to address
	public String position; // 地址
	@Tag(7)
	public int roleStatus; // 用户在职状态 0:在职 1:离职
	@Tag(8)
	public int sex; // 0:女 1:男
	@Tag(9)
	public String departmentId;
	@Tag(10)
	public int jobNum; // 工号
	@Tag(11)
	public String telephone;
	@Tag(12)
	public String email;

//    @Tag(13)
	//not protocol
//	public PinYinElement pinyinElement = new PinYinElement();
//    @Tag(14)
//	public SearchElement searchElement = new SearchElement();
	
	@Override
	public String toString() {
		return String.format(
				"id:%s, name:%s, nickName:%s, avatarUrl:%s, title:%s, position:%s, "
						+ "roleStatus:%d, sex:%d, departmentId:%s, jobNum:%d,"
						+ " telephone:%s, email:%s, pinyinElement:%s", id, name, nickName,
				avatarUrl, title, position, roleStatus, sex, departmentId,
				jobNum, telephone, email,"");
	}
	
}