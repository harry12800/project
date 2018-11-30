package cn.harry12800.vchat.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.harry12800.vchat.db.model.ContactsUser;

/**
 * Created by harry12800 on 09/06/2017.
 */
public class ContactsUserDao extends BasicDao {
	public ContactsUserDao(SqlSession session) {
		super(session, ContactsUserDao.class);
	}

	public int deleteByUsername(String username) {
		return session.delete("deleteByUsername", username);
	}

	public List<ContactsUser> searchByUsernameOrName(String username, String name) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("usernameCondition", "'%" + username + "%'");
		map.put("nameCondition", "'%" + name + "%'");
		return session.selectList("searchByUsernameOrName", map);
	}
}
