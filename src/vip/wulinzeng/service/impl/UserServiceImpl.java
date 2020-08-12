package vip.wulinzeng.service.impl;

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

}
