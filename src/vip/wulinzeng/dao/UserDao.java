package vip.wulinzeng.dao;

import org.springframework.stereotype.Repository;

import vip.wulinzeng.entity.User;

@Repository
public interface UserDao {
	public User findByUserName(String username);
	
}
