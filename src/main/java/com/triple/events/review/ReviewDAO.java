package com.triple.events.review;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public void reviewHistoryInsert(Review reviewDTO) {
		sqlSession.selectOne("Review.reviewHistoryInsert",reviewDTO);
	}
	@Transactional
	public void reviewInsert(Review reviewDTO) {
		sqlSession.selectOne("Review.reviewInsert",reviewDTO);
	}
	@Transactional
	public void reviewDelete(String reviewID) {
		sqlSession.selectOne("Review.reviewDelete",reviewID);
	}
	public int getDeletePoint(Review reviewDTO) {
		int result = sqlSession.selectOne("Review.getDeletePoint",reviewDTO);
		return result;
	}
	public String getReviewID(Review reviewDTO) {
		String result = sqlSession.selectOne("Review.getReviewID",reviewDTO);
		return result;
	}
	
	public String reviewContent(Review reviewDTO) {
		String result = sqlSession.selectOne("Review.reviewContent",reviewDTO);
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
	public List<Review> getListByUser(String userID){
		List<Review> list = new ArrayList<Review>();
		list= sqlSession.selectList("Review.getListByUser", userID);
		return list;
	}
	public List<String> getPlaceIDs(String userID){
		List<String> list = new ArrayList<String>();
		list= sqlSession.selectList("Review.getPlaceIDs", userID);
		return list;
	}
}
