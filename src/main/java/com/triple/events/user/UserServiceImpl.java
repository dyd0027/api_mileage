package com.triple.events.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	UserDAO userDao;

	@Override
	public int getTotalCount(String userID) {
		return userDao.getTotalCount(userID);
	}
	
	@Override
	public int idCheck(String userID) {
		return userDao.idCheck(userID);
	}
	
}
