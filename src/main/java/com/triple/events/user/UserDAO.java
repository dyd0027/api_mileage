package com.triple.events.user;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAO {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	@Transactional
	public void updateCount(User user ) {
		sqlSession.selectOne("User.updateCount",user);
	}
	public int getTotalCount(String userID) {
		return sqlSession.selectOne("User.getTotalCount",userID);
	}
	public int idCheck(String userID) {
		return sqlSession.selectOne("User.idCheck",userID);
	}
		
}
