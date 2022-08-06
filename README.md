
## -목차-
&nbsp;&nbsp;1.  사용한 기술스택 목록
<br>
&nbsp;&nbsp;2.  ERD
<br>
&nbsp;&nbsp;3.  BACKEND ARCHITECTURE
<br>
&nbsp;&nbsp;4.   구현 항목 및 API
<br>
&nbsp;&nbsp;5.   폴더 구조
<br>
&nbsp;&nbsp;6.   구현 고려사항과 구현 이유
<br>
&nbsp;&nbsp;7.   이후 추가적으로 고려할 사항
<br>

### 1. 사용한 기술스택 목록
BACK END<br/>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=Java&logoColor=white">
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-007396?style=for-the-badge&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/AWS-007396?style=for-the-badge&logo=amazonaws&logoColor=white">
<img src="https://img.shields.io/badge/Spring data Jpa-231F20?style=for-the-badge&logo=Spring data Jpa&logoColor=white">  
<img src="https://img.shields.io/badge/Spring Validation-6DB33F?style=for-the-badge&logo=Spring Validation&logoColor=white">
<img src="https://img.shields.io/badge/Spring hateoas-231F20?style=for-the-badge&logo=Spring hateoas&logoColor=white">  
<img src="https://img.shields.io/badge/Swagger-231F20?style=for-the-badge&logo=Swagger&logoColor=white">  
<img src="https://img.shields.io/badge/querydsl-4479A1?style=for-the-badge&logo=querydsl&logoColor=white">

### 2. 테이블 ERD
![image](https://user-images.githubusercontent.com/31757314/180645966-59c0225e-98a3-49f6-830a-e1b04fbcf54d.png)

### 3. BACKEND ARCHITECTURE 구축 예상도
![image](https://user-images.githubusercontent.com/31757314/180651252-7a739d6b-69e4-419c-afb3-3fae12f87d46.png)

### 4. 구현 항목 및 API
(1) Post /api/barcode/issue : 바코드를 발급한다.<br/>
<br/>Request : <br/>
![image](https://user-images.githubusercontent.com/31757314/180654915-04add0ec-c3bb-454f-b2e6-539edaf6bdfc.png)
<br/>Response : <br/>
![image](https://user-images.githubusercontent.com/31757314/180654982-6653cbfa-76d0-4998-91a1-a3dcafecd7bf.png)
<br/>
(2) Post /api/point/earn : 포인트를 적립합니다.<br/>
<br/>Request : <br/>
![image](https://user-images.githubusercontent.com/31757314/180655163-1a184486-06a4-4429-8923-b6f64ef97492.png)
<br/>Response : <br/>
![image](https://user-images.githubusercontent.com/31757314/180655187-a96ac22c-66ac-4321-bc08-cf0b69901af4.png)
<br/>
(3) Post /api/point/use : 포인트를 사용합니다.<br/>
<br/>Request : <br/>
![image](https://user-images.githubusercontent.com/31757314/180655203-ac5ddefa-acee-4036-89be-f331fe86d7ce.png)
<br/>Response : <br/>
![image](https://user-images.githubusercontent.com/31757314/180655220-7c86cc9f-97bd-422a-a6f7-258d657f94ab.png)
<br/>
(4) Post /api/point/history : 포인트 이력을 조회합니다.<br/>
<br/>Request : <br/>
![image](https://user-images.githubusercontent.com/31757314/180655254-67b9e6ad-8952-4bbd-bd48-e072d74703c9.png)
<br/>Response : <br/>
![image](https://user-images.githubusercontent.com/31757314/180655274-cc088c93-b4f4-419c-a4a4-c5869677a7cf.png)
<br/> 

/swagger-ui.html#/<br/>
![image](https://user-images.githubusercontent.com/31757314/180652161-ec7c5c11-4980-428f-9084-e9043ed2a48b.png)

### 5. 폴더 구조
![image](https://user-images.githubusercontent.com/31757314/180652094-c07a789e-4868-4856-889d-de32e63c9b11.png)<br/>
application : Service 및 응답 Response 위치 <br/>
Domain Model : Domain Entity 및 중요 비즈니스 로직 위치.<br/>
Infrastructure : 공통 클래스 및 설정 파일 위치<br/>
presentation : Controller 및 Request 요청 클래스 위치.<br/>
Domain 역할과 비즈니스 로직과 외부 계층과의 의존성과 결합도를 최소화하고 싶었습니다.<br/>
그래서 <br/>
Presentation layer : 외부 요청을 처리하는 영역<br/>
Infrastructure layer : 설정과 전반적인 layer에게 영향을 끼치는 영역<br/>
Domain Layer : 핵심적인 비즈니스 로직을 담당하는 영역<br/>
Application layer : 외부영역과 Domain 영역 사이에서 추상화 역할을 담당하여 연결을 담당하는 영역<br/>
으로 나누었습니다.<br/>

### 6. 구현 흐름, 고려사항
(1) 바코드 발급<br/>
![image](https://user-images.githubusercontent.com/31757314/183233769-af038746-1928-4a0e-9337-a7330ddd2494.png)
![image](https://user-images.githubusercontent.com/31757314/183233779-b40891a1-7e07-40f0-8ffc-f9d9fa3072fe.png)

1. 고려사항 - 바코드 발급시 다음 발급 바코든느 예측이 가능해서는 안된다.<br/>
   해결법   : 미리 barcodes 테이블에 바코드 번호를 일일 배치를 통해 저장해논 뒤 Random Row를 꺼낸 Rand()함수를 통해 해당 문제를 해결.<br/>

(2) 포인트 적립<br/>
![image](https://user-images.githubusercontent.com/31757314/183233876-bf5bf100-771a-45e3-bf29-245cebf27837.png)
![image](https://user-images.githubusercontent.com/31757314/183233905-dede7402-5537-4c1d-a1a1-8173d32210f9.png)

1. 포인트 이력으로만 관리 ? 포인트 적립금을 통해 관리 ?<br/>
1) 해결법 : 결론적으로는 두번째 방법을 선택했습니다.<br/>
그 이유로는 이력으로 관리할 경우 10년 더 나아가 20년이상 간다라고 했을때 이사람이 얼마있는지에 대해서는 20년전 데이터를 찾아서 조회해야 한다는 거고 이는 시스템의 속도 감소와 그 외적인 요소에 너무 많은 영향을 받는다고 생각하였습니다. ( 저는 포인트에 기한이 없다고 설정했습니다. 만약 포인트에 대한 기한이 있다고 했을때 이력만으로 관리하는 방식을 선택했을 것입니다. )<br/>
2. 언제 업종별 초기적립금(0원)을 테이블에 넣을것인가 ? 멤버십 바코드를 발급할때 ? 해당 업종에서 사용할 때?<br/>
1) 해결법 : 두번째 방법을 선택했습니다. 그 이유는 업종이 많이 생긴다고 가정하였을때 불필요한 데이터가 들어갈수 있다는 생각을 하였고 이는 큰 자원낭비라고 생각했기 때문입니다.<br/>
해당 방식을 선택했을때 고려사항은 두가지였습니다.<br/>
첫번째는, 데이터가 없을 경우 동시에 동일 업종에서 적립을 하여 동일한 업종, 동일한 바코드 번호를 가진 데이터를 가질수 있다는 것이였습니다.<br/>
해당 방식은 ForUpdate Lock을 통해 풀었습니다. 만약 처음 적립을 하여 Insert를 하여야 한다면 그 전에 BarcodeIssue(바코드 발급) 테이블에 For Update를 통해 Row Lock을 걸고, Point 테이블에 대해 다시 동일 업종, 동일 바코드 번호로 된 데이터가 있는지 확인 후 저장하는 방식이였습니다. <br/>그래서 만약 동일한 트랜잭션이 동시에 들어온다면, 일단 조금이라고 늦게 들어온 Session은 바코드 이슈 테이블에서 Lock에 의한 대기를 하게 되고 이후 첫번째가 실행된 후 두번째 Session은 Point 테이블에 대해 이미 등록되었는지 확인한후 이미 등록되었다면 Exception을 발생시켰습니다.<br/>
두번째는, 포인트 적립금이 있는 경우 동시에 들어온 Transcation들에 의해 값이 동시에 변경될수 있다는 것이였습니다.<br/> 그래서 ForUpdate를 통한 Lock을 통해 순차적으로 늦게 들어온 Session은 대기하도록 하여 순차적으로 진행하도록 구성하였습니다.<br/>

(3) Point 사용<br/>
![image](https://user-images.githubusercontent.com/31757314/183234091-6b2d3210-9a12-466c-b6f5-4d700217114c.png)

1. 동시에 포인트 적립과 사용이 올경우 어떻게 처리할 것인가 ?
위의 경우와 마찬가지로 동시에 접근하는 경우를 막아야 하였습니다.<br/> 
그래서 ForUpdate를 통한 Lock을 통해 순차적으로 늦게 들어온 Session은 대기하도록 하여 순차적으로 진행하도록 구성하였습니다.<br/>

(4) Point 이력 조회<br/>
![image](https://user-images.githubusercontent.com/31757314/183234118-f29c3a79-40e1-40ca-909c-54c138b3e96e.png)

1. Jspl로만 다루기에는 복잡하고 난해한 쿼리 어떻게 할것인가 ?
1) 해결법 : Jpa만으로 다루기에는 복잡한 쿼리를 요구하는 문제가 있었습니다. QueryDsl을 도입하여 문제를 해결했습니다<br/>
![image](https://user-images.githubusercontent.com/31757314/183234164-6684138c-4264-4074-a882-010489a722cc.png)<br/>
응답 Dto를 만들었고, Left Join을 통해 여러번 테이블을 조회하는 방식이 아닌 한번의 조회를 통해 요구 데이터를 가져와 응답하는 방식으로 최적화하였습니다.<br/>


#### 결론적으로, 저는 대부분 Lock을 통해서 해결했습니다. <br/>이로 인해 다른 트랜잭션은 대기하여야 하기에 전반적인 속도가 느려질수 있습니다.<br/> 하지만, 대부분의 경우 Index Row Lock을 걸어 Row에 대한 Lock을 걸었고, 그마저도 테이블 컬럼을 세분화시켰습니다. <br/>Point는 바코드, 업종별로 세분화시켜서 바코드 번호가 아닌 바코드 번호, 업종이 같은 Row에 대한 Lock을 걸도록 하였습니다. <br/>바코드 발급 같은 경우, 바코드 발급을 위한 테이블을 만들고 그 테이블에 Row에 Lock을 걸어서 부담을 최소화시키는걸 의도하였습니다. 그렇기에 순간적으로 많은 요청이 들어와도, 일정한 속도로 해결할수 있다고 생각하였기에 대규모 트랜잭션 환경에서 버틸수 있다고 생각했습니다.
<br/>

### 7. 이후 추가적으로 고려할 사항
1. 현재의 구조는 DB에 대단히 의존적인 구조입니다. MYsql DB가 잠시 동안 장애가 나거나 할 경우 대처하기에 유연한 구조가 아닙니다.<br/>
   따라서 추가적으로 중간에 Queue나 Redis같은 방충제 역할을 해줄 구성이 필요합니다.
   예를 들어서, DB 서버가 장애가 날 경우, 모든 요청사항들을 Queue나 Redis에 임시적으로 저장하고, 기존 저장 내역들로 요청들을 처리한 후 DB 서버가 장애로부터회복될 경우<br/>
   저장되어 있는 요청사항들을 순차적으로 처리하는걸 생각해볼수 있습니다.
2. point history테이블은 포인트 적립 이력과 사용 이력을 저장하는 테이블입니다. 해당 테이블에 대한 트랜잭션은 왕성하다고 생각됩니다. 따라서 Mysql Master - slave구조를 활용하여, 쓰기는 Master에 읽을시 Slave를 읽음으로써 부하를 감소시킬수 있습니다.
