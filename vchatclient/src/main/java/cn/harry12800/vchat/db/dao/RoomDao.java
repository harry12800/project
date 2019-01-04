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

	public Room findRelativeRoomIdByUserId(long userId, long creatorId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", "" + userId + "");
		map.put("creatorId", "" + creatorId + "");
		return (Room) session.selectOne("findRelativeRoomIdByUserId", map);
	}
	public Room findByRoomId(long roomId) {
		return (Room) session.selectOne(getClass().getName()+".findByRoomId", roomId);
	}
	
	public List<Room> searchByName(String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", "'%" + name + "%'");
		return session.selectList("searchByName", map);
	}

	public List<Room> findRelativeRoomIdByCreatorId(long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", "'" + userId + "'");
		return session.selectList("findSelfRoomIdByCreatorId", map);
	}

	public boolean exist(Room room) {
		return ((int) (session.selectOne(getClass().getName() + ".exist", room))) > 0;

	}
}
