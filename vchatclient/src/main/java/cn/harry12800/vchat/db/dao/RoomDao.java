package cn.harry12800.vchat.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.harry12800.vchat.db.model.Room;

/**
 * Created by harry12800 on 09/06/2017.
 */
public class RoomDao extends BasicDao {
	public RoomDao(SqlSession session) {
		super(session, RoomDao.class);
	}

	public Room findRelativeRoomIdByUserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", "'%" + userId + "%'");
		return (Room) session.selectOne("findRelativeRoomIdByUserId", map);
	}

	public List<Room> searchByName(String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", "'%" + name + "%'");
		return session.selectList("searchByName", map);
	}
}
