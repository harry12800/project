package cn.harry12800.db.entity;

/**
 * 玩家实体对象
 * @author -harry12800-
 *
 */
public class User {

	/**
	 * 玩家id
	 */
	private long id;
	/**
	 * 玩家名
	 */
	private String userId;
	/**
	 * 玩家名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String passward;

	/**
	 * 等级
	 */
	private int level;

	/**
	 * 经验
	 */
	private int exp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassward() {
		return passward;
	}

	public void setPassward(String passward) {
		this.passward = passward;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", userName=" + userName + ", passward=" + passward + ", level=" + level + ", exp=" + exp + "]";
	}

	 

}
