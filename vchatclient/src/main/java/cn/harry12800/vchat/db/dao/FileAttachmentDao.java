package cn.harry12800.vchat.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.harry12800.vchat.db.model.FileAttachment;

/**
 * Created by harry12800 on 09/06/2017.
 */
public class FileAttachmentDao extends BasicDao {
	public FileAttachmentDao(SqlSession session) {
		super(session, FileAttachmentDao.class);
	}

	public List<FileAttachment> search(String key) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", "'%" + key + "%'");
		return session.selectList(FileAttachmentDao.class.getName() + ".search", map);
	}
}
