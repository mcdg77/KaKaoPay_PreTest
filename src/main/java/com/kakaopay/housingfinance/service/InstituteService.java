package com.kakaopay.housingfinance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kakaopay.common.model.YearlySumResult;
import com.kakaopay.housingfinance.model.instituteVO;
import com.kakaopay.housingfinance.model.maxInsVO;
import com.kakaopay.housingfinance.model.minMaxVO;

@Service
public interface InstituteService {
	
	List<instituteVO> getInsList();
	
	int InsertCSVfile();

	List<YearlySumResult> selctYearlySum();

	maxInsVO selectMaxIns(String year);
	
	List<minMaxVO> selctMinMax();
	
}
