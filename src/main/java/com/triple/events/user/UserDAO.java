package com.triple.events.user;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public String idCheck(String userID) {
		String result = sqlSession.selectOne("User.idCheck",userID);
		return result;
	}
	public void userInsert(String userID) {
		sqlSession.selectOne("User.userInsert",userID);
	}
	public void updateCount(User user ) {
		sqlSession.selectOne("User.updateCount",user);
	}
		
}
