package com.triple.events.photo;

import java.beans.Transient;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PhotoDAO {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	@Transactional
	public void photoInsert(Photo photo) {
		sqlSession.selectOne("Photo.photoInsert",photo);
	}
	@Transactional
	public void photoDelete(String reviewID) {
		sqlSession.selectOne("Photo.photoDelete",reviewID);
	}
	public int photoCnt(String reviewID){
		int result = sqlSession.selectOne("Photo.photoCnt",reviewID);
		return result;
	}
}


