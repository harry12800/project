package cn.harry12800.vchat.db.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.harry12800.vchat.db.dao.FileAttachmentDao;
import cn.harry12800.vchat.db.model.FileAttachment;

/**
 * Created by harry12800 on 08/06/2017.
 */
public class FileAttachmentService extends BasicService<FileAttachmentDao, FileAttachment> {
	public FileAttachmentService(SqlSession session) {
		dao = new FileAttachmentDao(session);
		super.setDao(dao);
	}

	public int insertOrUpdate(FileAttachment attachment) {
		if (exist(attachment.getId())) {
			return update(attachment);
		} else {
			return insert(attachment);
		}
	}

	public List<FileAttachment> search(String key) {
		return dao.search(key);
	}
}
