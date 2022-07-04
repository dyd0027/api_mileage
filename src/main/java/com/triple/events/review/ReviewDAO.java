package com.triple.events.review;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDAO {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public String reviewCheck(Review reviewDTO) {
		String result = sqlSession.selectOne("Review.reviewCheck",reviewDTO);
		return result;
	}
	
	public String reviewCnt(Review reviewDTO) {
		String result = sqlSession.selectOne("Review.reviewCnt",reviewDTO);
		return result;
	}
	
	public void reviewHistoryInsert(Review reviewDTO) {
		sqlSession.selectOne("Review.reviewHistoryInsert",reviewDTO);
	}

	public void reviewInsert(Review reviewDTO) {
		sqlSession.selectOne("Review.reviewInsert",reviewDTO);
	}
	
	public void reviewDelete(String reviewID) {
		sqlSession.selectOne("Review.reviewDelete",reviewID);
	}
	public int getDeletePoint(String reviewID) {
		int result = sqlSession.selectOne("Review.getDeletePoint",reviewID);
		return result;
	}
	
	public String reviewContent(String reviewID) {
		String result = sqlSession.selectOne("Review.reviewContent",reviewID);
		return result;
	}
	
	public List<Review> getListByUserPlace(String userID, String placeID){
		Review review = new Review();
		review.setUserID(userID);
		review.setPlaceID(placeID);
		List<Review> list = new ArrayList<Review>();
		list= sqlSession.selectList("Review.getListByUserPlace", review);
		return list;
	}
}
