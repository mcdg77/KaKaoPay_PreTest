

KaKaoPay 서버개발 사전과제 
======================
> 주택 금융 서비스 API개발

###  프레임웍 및 개발환경 
국내 주택금융 신용보증 기관으로부터 년도별 각 금융기관(은행)에서 신용보증한 금액에 대한 데이터가 주어집니다. 이를 기반으로 아래 기능명세에 대한 API 를 개발하고 각 기능별 UnitTest 코드를 개발하세요.

### <i class="icon-file"></i> 프레임웍 및 개발환경 
 - JAVA 1.8
 - Gradle
 - Spring  Boot 2.1.6.RELEASE
 - Hsqldb
 - Mybatis
 - Mac OS
 - SpringToolSuite4


### <i class="icon-pencil"></i> 문제해결 전략
#### [ 개발 준비]

#### 1. 단독으로 서비스 실행을 위한 환경 준비
 
> - Sprnig Boot + Hsqldb 로 구성 Local 환경에서 단독구동 
> - 서버 기동시 DB  Table Drop 후 새로 Create Table  :초기화된 상태에서 실행
> - CVS파일이 Jar안에 포함되면서 Jar로 단독실행시 파일로드가 안되는 현상 발생 -> 실행되는 Jar 절대경로로 CVS파일을 로드

#### 2. DataBase 설계 
 
> -  TBL_USER(사용자정보) 1: N TBL_JWTAUTH(사용자별 Token관리)
> -  TBL_INSTITUTE(기간정보) 1:N TBL_FUND(금융기관 신용보즘 기금)
 
 > **Note:** <i class="icon-refresh"></i> 서비스 기동시 실행되는 DDL경로 :  ./src/main/resources/schema-hsqldb.sql 

#### [ API 개발]
  1. 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 개발
  
         2016년부터 [,] 문자로만으로는 구분자로 splite를 할수 없어  [,"]  이것으로 구분하고, 파싱된 ""가 없는 숫자부분은 앞에 
         다시 [,] 으로 구분해서 " " 유무와 상관없이 필요한 데이터를 추출해서 처리되도록 했다. 
   
  2. 주택금융 공급 금융기관(은행) 목록을 출력하는 API 를 개발하세요.
  
         금웅기관(은행) 표기 해서 TBL_INSTITUTE  NOT IN (주택도시기금) 해서 출력했다.
      
  3. 년도별 각 금융기관의 지원금액 합계를 출력하는 API 를 개발하세요.
  
         최대한 DB Query에서 처리될수 있게 SQL CASE WHEN을 써서 데이터 를 출력값에 가까운 형태로 데이터 출력 후
         후 작업이 최소로 해서 출력되도록 했다.

  4. 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발
   
         기관명이  [주택도시기금] 포함 유무의 정의가 애매하다 그래서 보기처럼 은행중에서 (1~12)월 MAX값으로 출력

  5. 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰금액을 출력하는 API 개발
     
         외환은행 코드(005) 기준 평균값의 MIN 과 MAX을 이용해서 출력    


#### [추가 제약사항]


1. JWT Token API 처리

       ● [KaKaoPay] secret으로 서명을 하고 TBL_JWTAUTH 테이블을 통해 이력 관리및 지난 토큰 사용불가 처리
          서명확인과 + 토큰 비교 통해 API 인증 기능 구현
       ● (signup / signin / refresh /  error) 를제회한 모든 URL은 인증 제약을  Interceptor를 통해 구현 

2. ID , PASSWD

       ● 패스워드를 SHA256방식으로 SALT 이용하여 단방향 암호화 하여 DB에  encrypt 된 데이터 와  salt를 저장
       ● 암호화된 값을 비교하여 동일한 경우 인증 처리, 사용자 이외에는 암로를 알수가 없음.

### <i class="icon-refresh"></i> API 명세서
#### 1. 회원가입(SignUp)
- 입력 URL :[POST] http://localhost:8080/member/signup

      {
         "userid" :"mcdg77",
         "passwd" :"1014"
      }

- 출력

      {
          "Message": "Success!!",
          "Result": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzdGlybXBhdGgiLCJzdWIiOiJLYUthb1BheSBQcmVUZXN0IiwiVXNlcklkIjoibWNkZzc3IiwiaWF0IjoxNTYyMjU0OTk2fQ.wyjKQf5nITWkniaGApNcYCCmRHkgZqLfb1pHOsbv0Jk"
      }
            
#### 2. 회원조회(SignIn) 
- 입력 URL :[POST] http://localhost:8080/member/signin

      {
         "userid" :"mcdg77",
         "passwd" :"1014"
      }

- 출력
      
      {
          "Message": "Success!!",
          "Result": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzdGlybXBhdGgiLCJzdWIiOiJLYUthb1BheSBQcmVUZXN0IiwiVXNlcklkIjoibWNkZzc3IiwiaWF0IjoxNTYyMjU0OTk2fQ.wyjKQf5nITWkniaGApNcYCCmRHkgZqLfb1pHOsbv0Jk"
      }
      
#### 3. Token 재발급(Refresh) 
- 입력 URL :[GET] http://localhost:8080/member/refresh
- 출력
      
      {
          "Message": "Success!!",
          "Result": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzdGlybXBhdGgiLCJzdWIiOiJLYUthb1BheSBQcmVUZXN0IiwiVXNlcklkIjoibWNkZzc3IiwiaWF0IjoxNTYyMjU1ODU3fQ.DNK-I14g5iVEPfN7jm-ATW2jPCN0eL_MrW9EdzwAcEI"
      }     
      
#### 4. CSV DB 저장 (save) 
- 입력 URL :[GET] http://localhost:8080/housefund/save
- 출력

      {
          "Message": "정상처리되었습니다.",
          "Count": "1386건"
      }

#### 5.주택금융 공급 금융기관(은행) 목록을 출력 (inslist) 
- 입력 URL :[GET] http://localhost:8080/housefund/inslist
- 출력

      {
          "name": "금융기관(은행) 목록",
          "result": [
              {
                  "intitute_code": "004",
                  "intitute_name": "국민은행"
              },
              {
                  "intitute_code": "020",
                  "intitute_name": "우리은행"
              },
              {
                  "intitute_code": "088",
                  "intitute_name": "신한은행"
              },
              {
                  "intitute_code": "036",
                  "intitute_name": "한국시티은행"
              },
              {
                  "intitute_code": "033",
                  "intitute_name": "하나은행"
              },
              {
                  "intitute_code": "010",
                  "intitute_name": "농협은행/수협은행"
              },
              {
                  "intitute_code": "005",
                  "intitute_name": "외환은행"
              },
              {
                  "intitute_code": "999",
                  "intitute_name": "기타은행"
              }
          ]
      }


#### 6. 년도별 각 금융기관의 지원금액 합계를 출력 (yearlysum) 
- 입력 URL :[GET] http://localhost:8080/housefund/yearlysum
- 출력

      {
          "name": "주택금융 공급현황",
          "result": [
              {
                  "year": "2005년",
                  "total_amount": 48016,
                  "detail_amount": {
                      "하나은행": 3122,
                      "농협은행/수협은행": 1486,
                      "우리은행": 2303,
                      "국민은행": 13231,
                      "신한은행": 1815,
                      "주택도시기금": 22247,
                      "외환은행": 1732,
                      "한국시티은행": 704,
                      "기타은행": 1376
                  }
              },
              {
                  "year": "2006년",
                  "total_amount": 41210,
                  "detail_amount": {
                      "하나은행": 3443,
                      "농협은행/수협은행": 2299,
                      "우리은행": 4134,
                      "국민은행": 5811,
                      "신한은행": 1198,
                      "주택도시기금": 20789,
                      "외환은행": 2187,
                      "한국시티은행": 288,
                      "기타은행": 1061
                  }
              },
              {
                  "year": "2007년",
                  "total_amount": 50893,
                  "detail_amount": {
                      "하나은행": 2279,
                      "농협은행/수협은행": 3515,
                      "우리은행": 3545,
                      "국민은행": 8260,
                      "신한은행": 2497,
                      "주택도시기금": 27745,
                      "외환은행": 2059,
                      "한국시티은행": 139,
                      "기타은행": 854
                  }
              },
              {
                  "year": "2008년",
                  "total_amount": 67603,
                  "detail_amount": {
                      "하나은행": 1706,
                      "농협은행/수협은행": 9630,
                      "우리은행": 4290,
                      "국민은행": 12786,
                      "신한은행": 1701,
                      "주택도시기금": 35721,
                      "외환은행": 941,
                      "한국시티은행": 69,
                      "기타은행": 759
                  }
              },
              {
                  "year": "2009년",
                  "total_amount": 96545,
                  "detail_amount": {
                      "하나은행": 1226,
                      "농협은행/수협은행": 8775,
                      "우리은행": 13105,
                      "국민은행": 8682,
                      "신한은행": 3023,
                      "주택도시기금": 44735,
                      "외환은행": 6908,
                      "한국시티은행": 40,
                      "기타은행": 10051
                  }
              },
              {
                  "year": "2010년",
                  "total_amount": 114903,
                  "detail_amount": {
                      "하나은행": 1872,
                      "농협은행/수협은행": 10984,
                      "우리은행": 15846,
                      "국민은행": 16017,
                      "신한은행": 2724,
                      "주택도시기금": 50554,
                      "외환은행": 11158,
                      "한국시티은행": 22,
                      "기타은행": 5726
                  }
              },
              {
                  "year": "2011년",
                  "total_amount": 206693,
                  "detail_amount": {
                      "하나은행": 9283,
                      "농협은행/수협은행": 19847,
                      "우리은행": 29572,
                      "국민은행": 29118,
                      "신한은행": 11106,
                      "주택도시기금": 69236,
                      "외환은행": 8192,
                      "한국시티은행": 13,
                      "기타은행": 30326
                  }
              },
              {
                  "year": "2012년",
                  "total_amount": 275591,
                  "detail_amount": {
                      "하나은행": 12534,
                      "농협은행/수협은행": 27253,
                      "우리은행": 38278,
                      "국민은행": 37597,
                      "신한은행": 21742,
                      "주택도시기금": 84227,
                      "외환은행": 19975,
                      "한국시티은행": 4,
                      "기타은행": 33981
                  }
              },
              {
                  "year": "2013년",
                  "total_amount": 265805,
                  "detail_amount": {
                      "하나은행": 15167,
                      "농협은행/수협은행": 17908,
                      "우리은행": 37661,
                      "국민은행": 33063,
                      "신한은행": 21330,
                      "주택도시기금": 89823,
                      "외환은행": 10619,
                      "한국시티은행": 50,
                      "기타은행": 40184
                  }
              },
              {
                  "year": "2014년",
                  "total_amount": 318771,
                  "detail_amount": {
                      "하나은행": 20714,
                      "농협은행/수협은행": 20861,
                      "우리은행": 52085,
                      "국민은행": 48338,
                      "신한은행": 28526,
                      "주택도시기금": 96184,
                      "외환은행": 11183,
                      "한국시티은행": 183,
                      "기타은행": 40697
                  }
              },
              {
                  "year": "2015년",
                  "total_amount": 374773,
                  "detail_amount": {
                      "하나은행": 37263,
                      "농협은행/수협은행": 18541,
                      "우리은행": 67999,
                      "국민은행": 57749,
                      "신한은행": 39239,
                      "주택도시기금": 82478,
                      "외환은행": 20421,
                      "한국시티은행": 37,
                      "기타은행": 51046
                  }
              },
              {
                  "year": "2016년",
                  "total_amount": 400971,
                  "detail_amount": {
                      "하나은행": 45485,
                      "농협은행/수협은행": 23913,
                      "우리은행": 45461,
                      "국민은행": 61380,
                      "신한은행": 36767,
                      "주택도시기금": 91017,
                      "외환은행": 5977,
                      "한국시티은행": 46,
                      "기타은행": 90925
                  }
              },
              {
                  "year": "2017년",
                  "total_amount": 295126,
                  "detail_amount": {
                      "하나은행": 35629,
                      "농협은행/수협은행": 26969,
                      "우리은행": 38846,
                      "국민은행": 31480,
                      "신한은행": 40729,
                      "주택도시기금": 85409,
                      "외환은행": 0,
                      "한국시티은행": 7,
                      "기타은행": 36057
                  }
              }
          ]
      }


#### 7. 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력 (maxInst) 
- 입력 URL :[POST] http://localhost:8080/housefund/maxInst

      {
        "year" : "2010"
      }

- 출력

      {
          "year": 2010,
          "bank": "국민은행"
      }
      
#### 8.전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력 (minmax) 
- 입력 URL :[GET] http://localhost:8080/housefund/minmax
- 출력

      {
          "bank": "외환은행",
          "support_amount": [
              {
                  "year": 2017,
                  "amount": 0
              },
              {
                  "year": 2015,
                  "amount": 1701
              }
          ]
      }    
      
      

### <i class="icon-refresh"></i> 빌드

    $ ./gradlew bootjar

### <i class="icon-refresh"></i> 실행 방법

    $ cd ./build/libs
    $ java -jar KaKaoPay_Test-1.0.1.jar    

> **Note:** <i class="icon-refresh"></i> 서비스 기동시 HFCG_F2005_T2017.csv 파일이 동일 경로에 있어야 합니다. 
    
### 테스트 화면
- KaKaoPay_ApiTest.Jar 이용한 테스트 
  <br> 소스경로 https://github.com/mcdg77/ApiTest
<img width="960" src="https://www.giro36524.com:8090/ubill/images/Test_20190704.png">

