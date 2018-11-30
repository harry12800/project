package cn.harry12800.vchat.db.dao;

import org.apache.ibatis.session.SqlSession;

/**
 * Created by harry12800 on 08/06/2017.
 */
public class CurrentUserDao extends BasicDao {
	public CurrentUserDao(SqlSession session) {
		super(session, CurrentUserDao.class);
	}
}