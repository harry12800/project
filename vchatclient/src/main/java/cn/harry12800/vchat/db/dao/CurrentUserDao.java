package cn.harry12800.vchat.db.dao;

import org.apache.ibatis.session.SqlSession;

import cn.harry12800.vchat.db.model.CurrentUser;

/**
 * Created by harry12800 on 08/06/2017.
 */
public class CurrentUserDao extends BasicDao {
	
	public CurrentUserDao(SqlSession session) {
		super(session, CurrentUserDao.class);
	}
	public boolean exist(CurrentUser currentUser) {
		return ((int) (session.selectOne(getClass().getName()+".exist", currentUser))) > 0;
		
	}
}