
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

### 6. 구현 고려사항과 구현 이유
(1) 바코드 발급<br/>
바코드 발급에서의 핵심사항은 다음 발급될 멤버십 바코드가 예측 가능해서는 안되다는 말이였습니다.<br/>
해당 요구사항을 구현하기 위해 생각한 방식은 2가지였습니다.<br/>
첫번째는, 바코드 발급이 올 경우 10가지 랜덤한 숫자를 통해 바코드를 발급하는 방식이였습니다.<br/>
두번째는, 미리 배치 시스템을 통해 일정량의 발급된 바코드를 DB에 저장해놓고 랜덤 Row를 추출하는 방식을 통해 바코드 발급을 하는 방식이였습니다.<br/>
결론적으로는 두번째 방법을 선택했습니다.<br/>
그 이유는, 예측 가능성이 이유였습니다. 랜덤으로 그자리에서 생성하는 방식은 기존에 존재하는 바코드 번호와 같은 값을 만드는 경우의 수도 포함되어 있었습니다.<br/>
그리고 그러한 경우가 반복될수 있는 경우의 수도 있습니다. 이는 비록 적은 시간이지만, 그 서비스가 정확히 얼만큼의 시간이 돌것인가에 대한 정확한 예측을 방해하는 요소였습니다.<br/> 그리고 우연히라도 동시에 같은 바코드 번호를 다른 사람에게 발급하는 경우의 수도  존재하였습니다.<br/>비록 DB에서 가져오는 작업은 느릴수는 있어도 변수가 없었고 어느정도 예측이 가능한 경우였습니다.<br/>또한 바코드 번호에 대해서도 얼마만큼을 발급할지 특정 숫자대에 바코드를 몇개를 발급할지 관리도 가능하였습니다.<br/> 
두번째 방식을 선택하고 중요했던 요소는 한 바코드는 절대 다른 두사람에게 발급되어서는 안된다는 말이였습니다.<br/>
그 방식을 구현하기 위해서 선택한건 ForUpdate를 통한 Row Index Lock이였습니다.<br/> 해당 방식을 사용하면 Mysql은 같은 row에 대해서는 읽기, 수정도 안되게 Lock을 걸었고 하나의 Session이 수행중일 경우 다른 Session은 대기상태에 빠지는 경우였습니다.<br/>
또 Option으로 nowait라는 Option을 주어 Session이 대기 상태에 빠지지 않게 하였습니다. <br/>왜냐면 해당 바코드 번호는 한 사람에게 발급이 가능해야 하기에 대기 상태에 있다가 해당 row에 접근해서 다른 사람에게 발급될 가능성도 있었기 때문입니다.
<br/><br/>
(2) 포인트 적립<br/>
해당 방식을 구현하는데에 고려했던 점은 이력으로만 관리할것인가 아니면 포인트 적립금을 저장해서 관리할까였습니다.<br/>
결론적으로는 두번째 방법을 선택했습니다.<br/>
그 이유로는 이력으로 관리할 경우 10년 더 나아가 20년이상 간다라고 했을때 이사람이 얼마있는지에 대해서는 20년전 데이터를 찾아서 조회해야 한다는 거고 이는 시스템의 속도 감소와 그 외적인 요소에 너무 많은 영향을 받는다고 생각하였습니다. ( 저는 포인트에 기한이 없다고 설정했습니다. 만약 포인트에 대한 기한이 있다고 했을때 이력만으로 관리하는 방식을 선택했을 것입니다. )<br/>
해당 방식을 선택하고 고려했던 점은 2가지였습니다.<br/>
언제 Point 테이블에 대해 데이터를 넣을것인가? 였습니다. (Point 테이블은 업종별로 포인트 금액을 저장하는 테이블입니다.)<br/>
첫번째 방법은, 바코드를 발급하였을때 업종별로 0원이라는 금액을 Point 테이블에 넣는 방식이였습니다.<br/>
두번째 방법은, 해당 업종에서 포인트를 적립하려고 할때 해당 데이터를 넣는 방식이였습니다.<br/>
두번째 방법을 선택했습니다. 그 이유는 업종이 많이 생긴다고 가정하였을때 불필요한 데이터가 들어갈수 있다는 생각을 하였고 이는 큰 자원낭비라고 생각했기 때문입니다.<br/>
해당 방식을 선택했을때 고려사항은 두가지였습니다.<br/>
첫번째는, 데이터가 없을 경우 동시에 동일 업종에서 적립을 하여 동일한 업종, 동일한 바코드 번호를 가진 데이터를 가질수 있다는 것이였습니다.<br/>
해당 방식은 ForUpdate Lock을 통해 풀었습니다. 만약 처음 적립을 하여 Insert를 하여야 한다면 그 전에 BarcodeIssue(바코드 발급) 테이블에 For Update를 통해 Row Lock을 걸고, Point 테이블에 대해 다시 동일 업종, 동일 바코드 번호로 된 데이터가 있는지 확인 후 저장하는 방식이였습니다. <br/>그래서 만약 동일한 트랜잭션이 동시에 들어온다면, 일단 조금이라고 늦게 들어온 Session은 바코드 이슈 테이블에서 Lock에 의한 대기를 하게 되고 이후 첫번째가 실행된 후 두번째 Session은 Point 테이블에 대해 이미 등록되었는지 확인한후 이미 등록되었다면 Exception을 발생시켰습니다.<br/>
두번째는, 포인트 적립금이 있는 경우 동시에 들어온 Transcation들에 의해 값이 동시에 변경될수 있다는 것이였습니다.<br/> 그래서 ForUpdate를 통한 Lock을 통해 순차적으로 늦게 들어온 Session은 대기하도록 하여 순차적으로 진행하도록 구성하였습니다.<br/>

(3) Point 사용<br/>
위의 경우와 마찬가지로 동시에 접근하는 경우를 막아야 하였습니다.<br/> 그래서 ForUpdate를 통한 Lock을 통해 순차적으로 늦게 들어온 Session은 대기하도록 하여 순차적으로 진행하도록 구성하였습니다.<br/>

(4) Point 이력 조회<br/>
Jpa만으로 다루기에는 복잡한 쿼리를 요구하는 문제가 있었습니다. QueryDsl을 도입하여 문제를 해결했습니다<br/>
응답 Dto를 만들었고, Left Join을 통해 여러번 테이블을 조회하는 방식이 아닌 한번의 조회를 통해 요구 데이터를 가져와 응답하는 방식으로 최적화하였습니다.


#### 결론적으로, 저는 대부분 Lock을 통해서 해결했습니다. <br/>이로 인해 다른 트랜잭션은 대기하여야 하기에 전반적인 속도가 느려질수 있습니다.<br/> 하지만, 대부분의 경우 Index Row Lock을 걸어 Row에 대한 Lock을 걸었고, 그마저도 테이블 컬럼을 세분화시켰습니다. <br/>Point는 바코드, 업종별로 세분화시켜서 바코드 번호가 아닌 바코드 번호, 업종이 같은 Row에 대한 Lock을 걸도록 하였습니다. <br/>바코드 발급 같은 경우, 바코드 발급을 위한 테이블을 만들고 그 테이블에 Row에 Lock을 걸어서 부담을 최소화시키는걸 의도하였습니다. 그렇기에 순간적으로 많은 요청이 들어와도, 일정한 속도로 해결할수 있다고 생각하였기에 대규모 트랜잭션 환경에서 버틸수 있다고 생각했습니다.

<br/>
그 외에도 API명을 어떻게 할것인가? 내부적으로 Class는 어떻게 나눌것인가? 바코드를 발급한 사람이 해제하면 어떻게 될것인가? 해제한 후에 다시 발급 받으면 이전의 포인트와 바코드 번호는 어떻게 될것인가? 상당부분 있지만, 가장 중요한 4가지 포인트를 적는것으로 마치겠습니다.

### 7. 이후 추가적으로 고려할 사항
1. 현재의 구조는 DB에 대단히 의존적인 구조입니다. MYsql DB가 잠시 동안 장애가 나거나 할 경우 대처하기에 유연한 구조가 아닙니다.<br/>
   따라서 추가적으로 중간에 Queue나 Redis같은 방충제 역할을 해줄 구성이 필요합니다.
   예를 들어서, DB 서버가 장애가 날 경우, 모든 요청사항들을 Queue나 Redis에 임시적으로 저장하고, 기존 저장 내역들로 요청들을 처리한 후 DB 서버가 장애로부터회복될 경우<br/>
   저장되어 있는 요청사항들을 순차적으로 처리하는걸 생각해볼수 있습니다.
2. point history테이블은 포인트 적립 이력과 사용 이력을 저장하는 테이블입니다. 해당 테이블에 대한 트랜잭션은 왕성하다고 생각됩니다. 따라서 Mysql Master - slave구조를 활용하여, 쓰기는 Master에 읽을시 Slave를 읽음으로써 부하를 감소시킬수 있습니다.
