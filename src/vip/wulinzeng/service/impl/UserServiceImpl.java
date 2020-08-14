package vip.wulinzeng.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vip.wulinzeng.dao.UserDao;
import vip.wulinzeng.entity.User;
import vip.wulinzeng.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao UserDao;
	@Override
	public User findByUserName(String username) {
		// TODO Auto-generated method stub
		return UserDao.findByUserName(username);
	}
	@Override
	public int add(User user) {
		// TODO Auto-generated method stub
		return UserDao.add(user);
	}
	@Override
	public List<User> findList(Map<String,Object> queryMap) {
		// TODO Auto-generated method stub
		return UserDao.findList(queryMap);
	}
	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return UserDao.getTotal(queryMap);
	}
	@Override
	public int edit(User user) {
		// TODO Auto-generated method stub
		return UserDao.edit(user);
	}
	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		return UserDao.delete(ids);
	}

}
