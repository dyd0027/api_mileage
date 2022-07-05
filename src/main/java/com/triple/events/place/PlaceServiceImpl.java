package com.triple.events.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.triple.events.review.ReviewDAO;
@Service("placeService")
public class PlaceServiceImpl implements PlaceService {
	@Autowired
	PlaceDAO placeDao;
	@Override
	public int placeCheck(String placeID) {
		return placeDao.placeCheck(placeID);
	}

}
