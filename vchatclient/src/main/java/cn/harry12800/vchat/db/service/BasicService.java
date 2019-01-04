package cn.harry12800.vchat.db.service;

import java.util.List;

import cn.harry12800.vchat.db.dao.BasicDao;
import cn.harry12800.vchat.db.model.BasicModel;

/**
 * Created by harry12800 on 09/06/2017.
 */
public class BasicService<T extends BasicDao, E extends BasicModel> {
	T dao;

	public void setDao(T dao) {
		this.dao = dao;
	}

	public int insert(BasicModel model) {
		return dao.insert(model);
	}

	public List<E> findAll() {
		return dao.findAll();
	}

	public E findById(String id) {
		return (E) dao.findById(id);
	}

	public List<E> find(String field, Object val) {
		return dao.find(field, val);
	}

	public int delete(String id) {
		return dao.delete(id);
	}

	public int deleteAll() {
		return dao.deleteAll();
	}

	public int update(BasicModel model) {
		return dao.update(model);
	}

	public int updateIgnoreNull(BasicModel model) {
		return dao.updateIgnoreNull(model);
	}

	public int count() {
		return dao.count();
	}

	public boolean exist(String id) {
		return dao.exist(id);
	}
}
