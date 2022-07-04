package com.triple.events.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	UserDAO dao;
	
	@Override
	public String idCheck(String userID) {
		return dao.idCheck(userID);
	}
	@Override
	public void userInsert(String userID) {
		dao.userInsert(userID);
	}

	
}
