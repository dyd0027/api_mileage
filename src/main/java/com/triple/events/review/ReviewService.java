package com.triple.events.review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.triple.events.photo.Photo;
import com.triple.events.user.UserDAO;

@Service
public interface ReviewService {
	
	public String reviewCheck(Review reviewDTO);
	public int insert(Review reviewDTO,Photo[] photo);
	public int delete(Review reviewDTO);
	public int[] modify(Review reviewDTO,Photo[] photo);
	public List<Review> manageUserPlace(String userID,String placeID);
	public List<String> getPlaceIDs(String userID);
}
