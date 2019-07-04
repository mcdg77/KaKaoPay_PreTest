package com.kakaopay.housingfinance.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.kakaopay.common.model.YearlySumResult;
import com.kakaopay.housingfinance.model.TblFund;
import com.kakaopay.housingfinance.model.instituteVO;
import com.kakaopay.housingfinance.model.maxInsVO;
import com.kakaopay.housingfinance.model.minMaxVO;
import com.kakaopay.housingfinance.model.yearlySumVO;
import com.kakaopay.repository.InstituteRepository;

@Service("instituteServicde")
public class InstituteServiceImpl implements InstituteService {	
	
	private static final String CVS_FILE_NAME = "HFCG_F2005_T2017.csv";	
	
	@Autowired
	private InstituteRepository	 instituteRepository;
	
	@Override
	public List<instituteVO> getInsList() {
		return instituteRepository.selecInstituteAll("076");
	}
	
	private instituteVO[] getInsListArrya() {
		 List<instituteVO> list = instituteRepository.selecInstituteAll("");		 
		 instituteVO[] arrdata = new instituteVO[list.size()];
		 
		 for(int i=0; i<list.size(); i++) {	
			 arrdata[i] = list.get(i);
		 }
		 return arrdata;
	}

	@Override
	public int InsertCSVfile() {			
		return procCVSInsert(CVS_FILE_NAME);
	}
	
	/* CVS File Insert 처리 
	 * 2016년 부터 데이터 Pasrsing 구분이 달라 분기 처리 되도록 수정 */ 
	private int procCVSInsert(String fname) {
		int result =0;
		BufferedReader br;
		String lineStr="";
				
		try {			
			String appPath =  System.getProperty("user.dir")+ "/";

			instituteVO[] insArray =  getInsListArrya();
			String[] strarr;

            br = new BufferedReader(new InputStreamReader(new FileInputStream(appPath + fname),"utf-8"));            
            br.readLine();
            
            while(( lineStr = br.readLine()) !=null ) {            	
            	strarr = lineStr.split(",");
            	
            	String year = strarr[0];
            	String month = strarr[1];
            	int amout =0;

            	if (Integer.parseInt(year) < 2016) {
                	for(int i=0; i< insArray.length; i++ ) {            		

                		amout = Integer.parseInt(strarr[i+2]);
                		
                		TblFund tblfund = new TblFund(year, month, insArray[i].getIntitute_code(), amout );                		
                		instituteRepository.insertCVSData(tblfund);                		
                	}
            	}else {
            		/* 2016년 이후 부터 데이터 파싱 문자 상이 */ 
            		strarr = lineStr.split("\",");
            		
            		int i=0, j=0;            		
            		while(i < insArray.length) {            			
                		if (strarr[j].indexOf(",\"") != -1) { 
                			String[] tmparr = strarr[j].split(",\"");

                			if(i>1) {                 				
                				if (tmparr[0].indexOf(",") != -1) { 
                					String[] tmparr2 = tmparr[0].split(",");
                     				
	                				for(int s=0; s< tmparr2.length; s++) {
		                				amout = Integer.parseInt(tmparr2[s].replace("\"", "").replace(",", ""));
		                				   
			                    		TblFund tblfund = new TblFund(year, month, insArray[i].getIntitute_code(), amout);                		
			                    		instituteRepository.insertCVSData(tblfund);                  					                   		                					               					
	                					i++;
	                				}                				
                				}else {
	                				amout = Integer.parseInt(tmparr[0].replace("\"", "").replace(",", ""));
	                				
		                    		TblFund tblfund = new TblFund(year, month, insArray[i].getIntitute_code(), amout);                		
		                    		instituteRepository.insertCVSData(tblfund);                      		
		                    		
		                    		i++;
                				}	                    		
                			}
                			amout = Integer.parseInt(tmparr[1].replace("\"", "").replace(",", ""));
                			
                    		TblFund tblfund = new TblFund(year, month, insArray[i].getIntitute_code(), amout);                		
                    		instituteRepository.insertCVSData(tblfund);  
                    		
                		}else {
                			amout = Integer.parseInt(strarr[j].replace("\"", "").replace(",", ""));               			
                			
                    		TblFund tblfund = new TblFund(year, month, insArray[i].getIntitute_code(), amout);                		
                    		instituteRepository.insertCVSData(tblfund);  
                		}
                		i++;
                		j++;
                	}
            	}
            }            
    		List<Integer> li =  instituteRepository.selectCVSData();   		
            result=li.get(0);
            
		}catch (IOException e) {
            e.printStackTrace();
        }		
		return result;	
	}	
	
	@Override
	public List<YearlySumResult> selctYearlySum() {	
		return ChangeHashmap(instituteRepository.selctYearlySum()); 	
	}
	
	/* 출력 형태를 맞추기 위한 추가 코딩 */
	private List<YearlySumResult> ChangeHashmap(List<yearlySumVO> slist){
		
		List<YearlySumResult> rlist = new  ArrayList<YearlySumResult>(); 
	
		for(int i=0; i< slist.size(); i++) {
			yearlySumVO tmp = slist.get(i);
			rlist.add(new YearlySumResult(tmp.getYear(), tmp.getTotal_amount(), pasingData(tmp.getDetail_amount()))); 						
		}				
		return rlist;
	}
	
	/* detail_amount를 따로 배열로 만들기 위해 HashMa사용 */
	private HashMap<String, Integer> pasingData(String data){
		
		HashMap<String,Integer> result = new HashMap<String,Integer>();		
		
		String[] tmparr = data.split(",");					
		for(String tmps : tmparr ) {
				String[]  tmparr2 = tmps.split(":");
				result.put(tmparr2[0],  Integer.parseInt(tmparr2[1]));					
		}
		return result;
	}

	@Override
	public maxInsVO selectMaxIns(String year) {		
		TblFund record = new TblFund();
		record.setHfyear(year);		
		return instituteRepository.selectMaxIns(record);	
	}
	
	@Override
	public List<minMaxVO> selctMinMax(){
		return instituteRepository.selectMinMax();
	}
	
	
	
	
}
