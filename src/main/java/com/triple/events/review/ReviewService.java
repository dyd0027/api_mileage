package com.triple.events.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.triple.events.photo.Photo;
import com.triple.events.user.UserDAO;

@Service
public interface ReviewService {
	
	public String reviewCheck(Review reviewDTO);
	public int insert(Review reviewDTO,Photo[] photo);
	public int delete(Review reviewDTO);
}
