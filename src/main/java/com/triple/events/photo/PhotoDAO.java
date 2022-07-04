package com.triple.events.photo;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PhotoDAO {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public void photoInsert(Photo photo) {
		sqlSession.selectOne("Photo.photoInsert",photo);
	}
	
	public void photoDelete(String reviewID) {
		sqlSession.selectOne("Photo.photoDelete",reviewID);
	}
}


