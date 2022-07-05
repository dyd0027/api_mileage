package com.triple.events.review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.triple.events.photo.Photo;
import com.triple.events.photo.PhotoDAO;
import com.triple.events.user.User;
import com.triple.events.user.UserDAO;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService{
	@Autowired
	ReviewDAO reviewDao;
	@Autowired
	PhotoDAO photoDao;
	@Autowired
	UserDAO userDao;
	
	@Override
	public String reviewCheck(Review reviewDTO) {
		return reviewDao.reviewCheck(reviewDTO);
	}

	@Override
	public int insert(Review reviewDTO,Photo[] photo) {
		String reviewCnt = reviewDao.reviewCnt(reviewDTO);
		int point = 0;
		if(reviewCnt.equals("0")) {
			point +=1;
		}
		String content = reviewDTO.getContent();
		if(content!=null) {
			point +=1;
		}
		if(photo.length>0) {
			point +=1;
		}
		reviewDTO.setPoint(point);
		reviewDao.reviewInsert(reviewDTO);
		reviewDao.reviewHistoryInsert(reviewDTO);
		if(photo.length>0) {
			for(int i=0;i<photo.length;i++) {
				photoDao.photoInsert(photo[i]);
			}
		}
		User user = new User();
		user.setTotalCount(point);
		user.setUserID(reviewDTO.getUserID());
		userDao.updateCount(user);
		return point;
	}
	@Override
	public int delete(Review reviewDTO) {
		String reviewID = reviewDao.getReviewID(reviewDTO);
		reviewDao.reviewDelete(reviewID);
		int deletePoint = (-1)*reviewDao.getDeletePoint(reviewDTO);
		//TODO 0으로 할지 deletePoint로 할지 ? >>0으로 해야 이력 관리가 쉬어질 듯
		reviewDTO.setPoint(0);
		reviewDao.reviewHistoryInsert(reviewDTO);

		User user = new User();
		user.setTotalCount(deletePoint);
		user.setUserID(reviewDTO.getUserID());
		userDao.updateCount(user);
		
		return deletePoint;
	}
	@Override
	public int[] modify(Review reviewDTO,Photo[] photo) {
		String reviewID = reviewDao.getReviewID(reviewDTO);
		String beforeContent = reviewDao.reviewContent(reviewDTO);
		String modContent = reviewDTO.getContent();
		int beforePoint = reviewDao.getDeletePoint(reviewDTO);
		if(beforePoint ==0 ){
			return null;
		}
		int point = 0;
		if(beforeContent==null && modContent!=null) {
			point ++;
		}else if(beforeContent !=null && modContent==null){
			point --;
		}
		
		int beforePhotoCnt = photoDao.photoCnt(reviewID);
		int modPhotoCnt = photo.length;
		
		if(beforePhotoCnt ==0 && modPhotoCnt >0){
			point++;
		}else if(beforePhotoCnt >0 && modPhotoCnt ==0){
			point--;
		}
		reviewDTO.setPoint(beforePoint+point);
		reviewDao.reviewInsert(reviewDTO);
		reviewDao.reviewHistoryInsert(reviewDTO);
		reviewDao.reviewDelete(reviewID);
		if(photo.length>0) {
			for(int i=0;i<photo.length;i++) {
				photoDao.photoInsert(photo[i]);
			}
		}
		User user = new User();
		user.setTotalCount(point);
		user.setUserID(reviewDTO.getUserID());
		userDao.updateCount(user);
		//기존 포인트 , 수정 될 포인트 증감량
		int[] arr= {beforePoint,point};
		return arr;
	}
	
	@Override
	public List<Review> manageUserPlace(String userID,String placeID){
		List<Review> list = new ArrayList<Review>();
		list = reviewDao.getListByUserPlace(userID, placeID);
		return list;
	};
	
	@Override
	public List<String> getPlaceIDs(String userID){
		List<String> list = new ArrayList<String>();
		list = reviewDao.getPlaceIDs(userID);
		return list;
	};

}
