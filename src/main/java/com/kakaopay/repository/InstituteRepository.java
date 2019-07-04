package com.kakaopay.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kakaopay.housingfinance.model.TblFund;
import com.kakaopay.housingfinance.model.instituteVO;
import com.kakaopay.housingfinance.model.maxInsVO;
import com.kakaopay.housingfinance.model.minMaxVO;
import com.kakaopay.housingfinance.model.yearlySumVO;


@Repository
public class InstituteRepository {
	private static final String INSTITUTE_MAPPER_NAME = "mapper.instituteMapper.";
	private static final String FUND_MAPPER_NAME ="mapper.fundMapper.";
	
	  @Autowired
	  private SqlSessionTemplate sqlSessionTemplate;

	  /* inslist  */
	  public List<instituteVO> selecInstituteAll(String inscode) {
	    	return sqlSessionTemplate.selectList(INSTITUTE_MAPPER_NAME + "selecInstituteAll", inscode);
	    }
	  
	  /* save */
	  public int insertCVSData(TblFund record) {
		 
		 /* 외부 DB Insert를 위한 쿼리문 생성   
		 String tmpstr = "INSERT INTO TBL_FUND Values('" + record.getHfyear() + "','" 
				 + record.getHfmonth() + "','"   
				 + record.getInstitutecode() + "',"   
				 + record.getHfamount() +");";		    
		 System.out.println(tmpstr);
		 */		 		 
		  return sqlSessionTemplate.insert(FUND_MAPPER_NAME + "insertCVS", record); 
	  }
	  
	  /* Count Check*/
	  public List<Integer> selectCVSData() {
		  return sqlSessionTemplate.selectList(FUND_MAPPER_NAME + "selectCVS"); 
	  }

	  /* Yearly Sum */
	  public List<yearlySumVO> selctYearlySum() {
		  return sqlSessionTemplate.selectList(FUND_MAPPER_NAME + "selectYearlySum");
	  }
	  
	  /* selectMaxIns */
	  public maxInsVO selectMaxIns(TblFund record) {
		  return sqlSessionTemplate.selectOne(FUND_MAPPER_NAME + "selectMaxIns", record);
	  }
	  
	  /* selctMinMax */
	  public List<minMaxVO> selectMinMax() {
		  return sqlSessionTemplate.selectList(FUND_MAPPER_NAME + "selctMinMax");
	  }
	  

}
