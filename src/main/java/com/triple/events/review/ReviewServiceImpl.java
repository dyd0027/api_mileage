package com.triple.events.review;

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
		reviewDao.reviewHistoryInsert(reviewDTO);
		reviewDao.reviewInsert(reviewDTO);
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
		reviewDao.reviewDelete(reviewDTO.getReviewID());
		photoDao.photoDelete(reviewDTO.getReviewID());
		int deletePoint = (-1)*reviewDao.getDeletePoint(reviewDTO.getReviewID());
		reviewDTO.setPoint(deletePoint);
		reviewDao.reviewHistoryInsert(reviewDTO);

		User user = new User();
		user.setTotalCount(deletePoint);
		user.setUserID(reviewDTO.getUserID());
		userDao.updateCount(user);
		
		return deletePoint;
	}

}
