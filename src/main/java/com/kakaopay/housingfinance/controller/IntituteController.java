package com.kakaopay.housingfinance.controller;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.common.model.Result;
import com.kakaopay.common.model.minMaxResult;
import com.kakaopay.housingfinance.model.maxInsVO;
import com.kakaopay.housingfinance.service.InstituteService;


@RestController
@RequestMapping(value= "/housefund")
public class IntituteController {
	
	@Autowired
	InstituteService instituteService;
	
	/* 금융기관(은행) 목록을 출력 */
	@GetMapping(value="/inslist")
	public ResponseEntity<Result> getiInsList(){
		System.out.println( "GetMapping URL -[housefund/inslist] ");	
		Result result = new Result( "금융기관(은행) 목록", instituteService.getInsList());
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
	/* CVS 데이터베이스에 저장*/
	@GetMapping(value="/save")
	public ResponseEntity<HashMap<String,String>> insertCSVfile(){
		System.out.println( "GetMapping URL -[housefund/save] ");	
		HashMap<String,String> message = new HashMap<String,String>();		
		int result = instituteService.InsertCSVfile();		
		message.put("Message", "정상처리되었습니다.");
		message.put("Count", String.valueOf(result) + "건" );
		return new ResponseEntity<HashMap<String,String>>(message, HttpStatus.OK);
	}
	
	/* 년도별 각 금융기관의 지원금액 합계 */
	@GetMapping(value="/yearlysum")
	public ResponseEntity<Result> selctYearlySum(){
		System.out.println( "GetMapping URL -[housefund/yearlysum] ");
		Result result = new Result( "주택금융 공급현황", instituteService.selctYearlySum());
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
	/* 년도별 각 기관의 전체 지원금액 중 큰 금액의 기관명을 출력*/
	@PostMapping(value="/maxInst")
	public ResponseEntity<maxInsVO> selectMaxIns(@RequestBody String year ){
		try {
			System.out.println( "PostMapping URL -[housefund/maxInst] ");
			JSONObject json = new JSONObject(year);	
			return new ResponseEntity<maxInsVO>(instituteService.selectMaxIns(json.getString("year")), HttpStatus.OK);
		} catch (JSONException e) {
			e.printStackTrace();			
			return new ResponseEntity<maxInsVO>(HttpStatus.EXPECTATION_FAILED);
		}		
	}
	
	/* 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력*/
	@GetMapping(value="/minmax")
	public ResponseEntity<minMaxResult> selctMinMax(){
		System.out.println( "GetMapping URL -[housefund/minmax] ");
		minMaxResult mmResult = new minMaxResult ("외환은행", instituteService.selctMinMax());	
		return new ResponseEntity<minMaxResult>(mmResult, HttpStatus.OK);
	}
	
}
