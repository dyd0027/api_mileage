package com.triple.events.user;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
	public int getTotalCount(String userID);
	public int idCheck(String userID);
}
