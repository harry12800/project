package cn.harry12800.vchat.db.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.harry12800.vchat.db.dao.MessageDao;
import cn.harry12800.vchat.db.model.Message;

/**
 * Created by harry12800 on 08/06/2017.
 */
public class MessageService extends BasicService<MessageDao, Message> {
	public MessageService(SqlSession session) {
		dao = new MessageDao(session);
		super.setDao(dao);
	}

	public int insertOrUpdate(Message message) {
		if (exist(message.getId())) {
			return update(message);
		} else {
			return insert(message);
		}
	}

	public Message findLastMessage(Long roomId) {
		return dao.findLastMessage(roomId);
	}

	public int deleteByRoomId(Long roomId) {
		return dao.deleteByRoomId(roomId);
	}

	public int countByRoom(Long roomId) {
		return dao.countByRoom(roomId);
	}

	public List<Message> findByPage(Long roomId, int page, int pageLength) {
		return dao.findByPage(roomId, page, pageLength);
	}

	public List<Message> findOffset(long roomId, int offset, int pageLength) {
		return dao.findOffset(roomId, offset, pageLength);
	}

	public long findLastMessageTime(Long roomId) {
		return dao.findLastMessageTime(roomId);
	}

	public int insertAll(List<Message> list) {
		return dao.insertAll(list);
	}

	public List<Message> findBetween(long roomId, long start, long end) {
		return dao.findBetween(roomId, start, end);
	}

	public long findFirstMessageTime(String roomId) {
		return dao.findFirstMessageTime(roomId);
	}

	public int updateNeedToResend(String id, boolean status) {
		return dao.updateNeedToResend(id, status);
	}

	public int updateProgress(String id, int progress) {
		return dao.updateProgress(id, progress);
	}

	public List<Message> search(String key) {
		return dao.search(key);
	}

	public int markDeleted(String id) {
		return dao.markDeleted(id);
	}
}
