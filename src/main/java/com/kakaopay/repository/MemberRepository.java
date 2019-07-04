package com.kakaopay.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kakaopay.auth.model.TblJwtAuthVO;
import com.kakaopay.auth.model.TblUserVO;

@Repository
public class MemberRepository {
	private static final String TBLUSER_MAPPER_NAME = "mapper.memberMapper.";
	
	  @Autowired
	  private SqlSessionTemplate sqlSessionTemplate;

	  /* insertTb_User */
	  public int insertTblUser(TblUserVO record) {
		  return sqlSessionTemplate.insert(TBLUSER_MAPPER_NAME + "insertTblUser", record);
	  }
	  
	  /* select Tb_User */ 
	  public List<TblUserVO> selectTblUser(TblUserVO record){
		  return sqlSessionTemplate.selectList(TBLUSER_MAPPER_NAME  + "selectTblUser", record);
	  }
	  
	  /* insertTb_JwtAuth */
	  public int insertTblJwtAuth(TblJwtAuthVO record) {
		  return sqlSessionTemplate.insert(TBLUSER_MAPPER_NAME + "insertTblJwtAuth", record);		  
	  }
	  
	  /* select Tb_JwtAuth */
	  public List<TblJwtAuthVO> selectTblJwtAuth(String usrid){
		  return sqlSessionTemplate.selectList(TBLUSER_MAPPER_NAME  + "selectTblJwtAuth", usrid);
	  }
	
	  /* select selectTblJwtAuthByJtw */
	  public List<TblJwtAuthVO> selectTblJwtAuthByJtw(String jwtval){
		  return sqlSessionTemplate.selectList(TBLUSER_MAPPER_NAME  + "selectTblJwtAuthByJtw", jwtval);
	  }	  
	  
	  /* update Tb_JwtAuth */
	  public int updateTblJwtAuth(TblJwtAuthVO record) {
		  return sqlSessionTemplate.update(TBLUSER_MAPPER_NAME + "updateTblJwtAuth", record);
	  }
	  
	  
}
