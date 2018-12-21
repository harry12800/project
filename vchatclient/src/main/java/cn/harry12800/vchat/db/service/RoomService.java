package cn.harry12800.vchat.db.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.harry12800.vchat.db.dao.RoomDao;
import cn.harry12800.vchat.db.model.Room;

/**
 * Created by harry12800 on 08/06/2017.
 */
public class RoomService extends BasicService<RoomDao, Room> {
	public RoomService(SqlSession session) {
		dao = new RoomDao(session);
		super.setDao(dao);
	}

	public int insertOrUpdate(Room room) {
		if (exist(room)) {
			return update(room);
		} else {
			return insert(room);
		}
	}
	boolean exist(Room room){
		return dao.exist(room);
	}
	
	// 查找自己和好友的房间
	public Room findRelativeRoomIdByUserId(long userId,long creatorId) {
		return dao.findRelativeRoomIdByUserId(userId,creatorId);
	}
	// 查找自己的所有房间
	public List<Room> findRelativeRoomIdByCreatorId(long userId) {
		return dao.findRelativeRoomIdByCreatorId(userId);
	}
	public Room findByName(String name) {
		List list = dao.find("name", name);
		if (list.size() > 0) {
			return (Room) list.get(0);
		}
		return null;
	}
	public Room findById(long id) {
		List list = dao.find("roomId", id);
		if (list.size() > 0) {
			return (Room) list.get(0);
		}
		return null;
	}

	public List<Room> searchByName(String name) {
		return dao.searchByName(name);
	}
}
