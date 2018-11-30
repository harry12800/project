package cn.harry12800.scan.redis;

import java.util.Objects;

public final class RedisKeyBuilder {
	private static final String KEY_FORMAT_User = "fg:user:%s"; // kv
	private static final String HKEY_FORMAT_Roster = "fg:roster:%s"; // hash
	private static final String SKEY_FORMAT_UserMuc = "fg:muc:user:%s"; // set
	private static final String HKEY_FORMAT_MucMember = "fg:muc:member:%s"; // hash
	private static final String KEY_FORMAT_Employee2 = "fg:employee:%s"; // kv

	private static final String SKEY_FORMAT_OfUserLine = "fg:oul:%s"; //set
	private static final String KEY_FORMAT_UserRouter = "fg:ur:%s"; //hash

	public static final String HKEY_MucRooms = "fg:muc:muc:config"; // hash

	public static class UserKeyBuilder {
		private String userid = null;

		public UserKeyBuilder userid(String userid) {
			this.userid = userid.toLowerCase();
			return this;
		}

		public String build() {
			return String.format(KEY_FORMAT_User, this.userid);
		}
	}

	public static class RosterKeyBuilder {
		private String userid = null;

		public RosterKeyBuilder userid(String userid) {
			this.userid = userid.toLowerCase();
			return this;
		}

		public String build() {
			return String.format(HKEY_FORMAT_Roster, this.userid);
		}
	}

	public static class UserMucKeyBuilder {
		private String userid = null;

		public UserMucKeyBuilder userid(String userid) {
			this.userid = userid.toLowerCase();
			return this;
		}

		public String build() {
			return String.format(SKEY_FORMAT_UserMuc, this.userid);
		}
	}

	public static class MucMemberKeyBuilder {
		private String muc_id = null;

		public MucMemberKeyBuilder mucId(String muc_id) {
			this.muc_id = muc_id.toLowerCase();
			return this;
		}

		public String build() {
			return String.format(HKEY_FORMAT_MucMember, this.muc_id);
		}
	}

	public static class EmployeeKeyBuilder {
		private String empNo = null;

		public EmployeeKeyBuilder employeeNo(String empNo) {
			Objects.requireNonNull(empNo);
			this.empNo = empNo.toLowerCase();

			return this;
		}

		public String build() {
			return String.format(KEY_FORMAT_Employee2, this.empNo);
		}

	}

	public static class OfUserLineKeyBuilder {
		private String server_ip = null;

		public OfUserLineKeyBuilder serverIp(String server_ip) {
			this.server_ip = server_ip.toLowerCase();
			return this;
		}

		public String build() {
			return String.format(SKEY_FORMAT_OfUserLine, this.server_ip);
		}
	}

	public static class UserRouterKeyBuilder {
		private String username = null;

		public UserRouterKeyBuilder username(String username) {
			this.username = username.toLowerCase();
			return this;
		}

		public String build() {
			return String.format(KEY_FORMAT_UserRouter, this.username);
		}
	}

}
