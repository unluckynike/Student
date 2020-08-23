package vip.wulinzeng.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vip.wulinzeng.dao.StudentDao;
import vip.wulinzeng.entity.Student;
import vip.wulinzeng.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentDao StudentDao;
	
	@Override
	public Student findByUserName(String username) {
		// TODO Auto-generated method stub
		return StudentDao.findByUserName(username);
	}

	@Override
	public int add(Student student) {
		// TODO Auto-generated method stub
		return StudentDao.add(student);
	}

	@Override
	public int edit(Student student) {
		// TODO Auto-generated method stub
		return StudentDao.edit(student);
	}

	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		return StudentDao.delete(ids);
	}

	@Override
	public List<Student> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return StudentDao.findList(queryMap);
	}

	@Override
	public List<Student> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return 0;
	}

}
