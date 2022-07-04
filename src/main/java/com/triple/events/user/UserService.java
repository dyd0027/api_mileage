package com.triple.events.user;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
	public String idCheck(String userID);
	public void userInsert(String userID);
}
