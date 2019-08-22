# 지자체 협약 지원 API 개발 

## 개발 프레임워크
### 메인 프래임웍  
  - spring boot 2.1.7 (Spring Web Starter + Spring Data jpa)
  - lombok
  - log4j
  
### API 명세 제공 및 테스트
  - Swagger 2.9.2 : http://localhost:8080/swagger-ui.html
  - h2 memoredb : http://localhost:8080/h2-console
```
접속정보
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
```
## 문제해결 방법
- 2%~3% ,5억원 이내 같은 비정형적인 데이터를 어떻게 저장할 것인가 : 컬럼으로 일일이 분리하기에는 타입값과 컬럼수가 늘어난다. 그래서 그대로 저장하여 조회시 처리
- Restful 하게 url은 리소스를 나타내고 행위에 대해서는 HTTP METHOD 로 동작하도록 함
- 파라미터로 조회 결과를 조절할수 있지만 url을 특하화여 제공 (ex /matching-support/max-limit-support,  /matching-support/min-rate-support)

### 설계
- 엔티티는 지자체 지원정보, 지원 지자체로 분리 (BankSupport, LocalCovCode)
- 지원 금액 내림차순 정렬, 보전 비율 min 값 같은비 정형화된 데이터는 조회시 Sort를 하는 컴포넌트에서 parseing 하여 처리하는 것으로 구현

### API 개발
- 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 개발
 ```
 url : /bank-support/upload-support-data
 method : POST
 request 예시 : formData로 csv 파일 전송
 ```
- 지원하는 지자체 목록 검색 API 개발 
 1. 모든 지원정보  : 검색조건이 없는경우
 ```
  url : /bank-support/list
  method : GET
  ```
 2. 지원하는 지자체명 : 검색조건이 지자체만 있는 경우
```
  url : /bank-support/support
  method : GET
  body :
  {
  "region" : "REG0001"
  }
  ```
 3. 지원한도 컬럼에서 지원금액으로 내림차순
정렬(지원금액이 동일하면 이차보전 평균 비 율이 적은 순서)하여 특정 개수만 출력하는 API 개발 
```
  url : /matching-support/max-limit-support
  method : GET
  body :
  {
  "count": 3
  }
```
- 지원하는 지자체 정보 수정 기능 API 개발 
```
  url : /bank-support/support/{regionCode} ex)/bank-support/support/REG0001
  method : PUT
  body :
  {
    "institute": "강릉시",
    "limit": "10억원 이내",
    "mgmt": "강릉지점",
    "rate": "1%~2%",
    "reception": "강릉시 소재 영업점",
    "target": "강릉시 소재 중소기업으로서 강릉시장이 추천한 자",
    "usage": "운전"
  }
```
- 이차보전 컬럼에서 보전 비율이 가장 작은 추천 기관명을 출력하는 API 개발 
```
  url : /matching-support/min-rate-support
  method : GET
```


### 빌드 및 실행 방법 
```
maven package
java -jar target/support-0.0.1-SNAPSHOT.jar
```