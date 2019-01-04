package cn.harry12800.common.module.user.dto;

import java.util.ArrayList;
import java.util.List;

import cn.harry12800.common.core.serial.Serializer;

public class ShowAllUserResponse extends Serializer {
	List<UserResponse> users = new ArrayList<UserResponse>();

	@Override
	protected void read() {
		users = readList(UserResponse.class);
	}

	@Override
	protected void write() {
		writeList(users);
	}

	public List<UserResponse> getUsers() {
		return users;
	}

	public void setUsers(List<UserResponse> users) {
		this.users = users;
	}

}
