

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
 
 > **Note:** The <i class="icon-refresh"></i> 서비스 기동시 실행되는 DDL경로 :  ./src/main/resources/schema-hsqldb.sql 

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
      




#### <i class="icon-refresh"></i> 빌드

    $ ./gradlew bootjar

#### <i class="icon-refresh"></i> 실행 방법

    $ cd ./build/libs
    $ java -jar KaKaoPay_Test-1.0.1.jar    

> **Note:** The <i class="icon-refresh"></i> 서비스 기동시 HFCG_F2005_T2017.csv 파일이 동일 경로에 있어야 합니다. 
    

