package cn.harry12800.vchat.db.service;

import org.apache.ibatis.session.SqlSession;

import cn.harry12800.vchat.db.dao.CurrentUserDao;
import cn.harry12800.vchat.db.model.CurrentUser;

/**
 * Created by harry12800 on 08/06/2017.
 */
public class CurrentUserService extends BasicService<CurrentUserDao, CurrentUser> {
	public CurrentUserService(SqlSession session) {
		dao = new CurrentUserDao(session);
		super.setDao(dao);
	}

	public int insertOrUpdate(CurrentUser currentUser) {
		if (exist(currentUser)) {
			return update(currentUser);
		} else {
			return insert(currentUser);
		}
	}
	boolean exist(CurrentUser room){
		return dao.exist(room);
	}
	/*
	 * @Override public List<CurrentUser> findAll() { // TODO: 从数据库获取当前登录用户
	 * List<CurrentUser> list = new ArrayList(); list.add(new
	 * CurrentUser("Ni7bJcX3W8yExKSa3", "song", "", "", "", "", "song", "", ""));
	 * return list; }
	 */
}
