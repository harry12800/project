package cn.harry12800.vchat.db.model;

/**
 * Created by harry12800 on 09/03/2017.
 */

public class ContactsUser extends BasicModel {
	
	private long userId;
	private long friendId;
	private String username;

	private String name;

	public ContactsUser() {
	}

	public ContactsUser(long userId, String username, String name, long friendId) {
		this.userId = userId;
		this.username = username;
		this.name = name;
		this.friendId = friendId;
	}


	public long getFriendId() {
		return friendId;
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ContactsUser{" + "userId='" + userId + '\'' + ", username='" + username + '\'' + ", name='" + name
				+ '\'' + '}';
	}
}
