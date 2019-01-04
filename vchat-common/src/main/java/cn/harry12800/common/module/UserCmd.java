package cn.harry12800.common.module;

/**
 * 用户模块
 * @author harry12800
 *
 */
public interface UserCmd {

	/**
	 * 创建并登录帐号
	 */
	short REGISTER_AND_LOGIN = 1;

	/**
	 * 登录帐号
	 */
	short LOGIN = 2;
	short SHOW_ALL_USER = 3;
	short PULL_MSG = 4;
}
