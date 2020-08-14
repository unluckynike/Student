package vip.wulinzeng.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vip.wulinzeng.dao.GradeDao;
import vip.wulinzeng.entity.Grade;
import vip.wulinzeng.service.GradeService;

@Service
public class GradeServiceImpl  implements GradeService {

	@Autowired
	private GradeDao GradeDao;
	@Override
	public int add(Grade grade) {
		// TODO Auto-generated method stub
		return GradeDao.add(grade);
	}

	@Override
	public List<Grade> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return GradeDao.findList(queryMap);
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return GradeDao.getTotal(queryMap);
	}

	@Override
	public int edit(Grade grade) {
		// TODO Auto-generated method stub
		return GradeDao.edit(grade);
	}

	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		return GradeDao.delete(ids);
	}

}
