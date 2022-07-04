package com.triple.events.review;

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
}
