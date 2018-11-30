package cn.harry12800.vchat.db.service;

import org.apache.ibatis.session.SqlSession;

import cn.harry12800.vchat.db.dao.ImageAttachmentDao;
import cn.harry12800.vchat.db.model.ImageAttachment;

/**
 * Created by harry12800 on 08/06/2017.
 */
public class ImageAttachmentService extends BasicService<ImageAttachmentDao, ImageAttachment> {
	public ImageAttachmentService(SqlSession session) {
		dao = new ImageAttachmentDao(session);
		super.setDao(dao);
	}

	public int insertOrUpdate(ImageAttachment attachment) {
		if (exist(attachment.getId())) {
			return update(attachment);
		} else {
			return insert(attachment);
		}
	}

}
