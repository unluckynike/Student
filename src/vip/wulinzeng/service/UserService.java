package vip.wulinzeng.service;

import org.springframework.stereotype.Service;

import vip.wulinzeng.entity.User;

@Service
public interface UserService {

	public User findByUserName(String username);
}
