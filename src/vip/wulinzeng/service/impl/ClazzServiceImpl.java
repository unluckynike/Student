package vip.wulinzeng.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vip.wulinzeng.dao.ClazzDao;
import vip.wulinzeng.entity.Clazz;
import vip.wulinzeng.service.ClazzService;


@Service
public class ClazzServiceImpl  implements ClazzService {

	@Autowired
	private ClazzDao ClazzDao;
	@Override
	public int add(Clazz Clazz) {
		// TODO Auto-generated method stub
		return ClazzDao.add(Clazz);
	}

	@Override
	public List<Clazz> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return ClazzDao.findList(queryMap);
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return ClazzDao.getTotal(queryMap);
	}

	@Override
	public int edit(Clazz Clazz) {
		// TODO Auto-generated method stub
		return ClazzDao.edit(Clazz);
	}

	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		return ClazzDao.delete(ids);
	}

	@Override
	public List<Clazz> findAll() {
		// TODO Auto-generated method stub
		return ClazzDao.findAll();
	}

}
