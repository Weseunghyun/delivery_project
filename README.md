
## 와이어 프레임

![배달앱 와이어프레임](https://github.com/user-attachments/assets/4ba82b28-5c1b-4d76-856a-77ebd6b38277)

---

## 아웃소싱 프로젝트 API 명세서

### **1. 메뉴**

### **1-1. 가게 메뉴 조회 (GET /api/stores/{storeId}/menus?page=0)**

- 상세 내용 펼쳐보기
    - **요청**
        - **Query Parameters**:
            - `page` (default: 0): 조회할 페이지 번호
        - **Path Variables**:
            - `{storeId}`: 수정할 메뉴의 가게 ID
    - **응답**
        - **Status Code**: 200 OK
        - **Body**:
            
            ```json
            {
                "content": [
                    {
                        "storeId": 1,
                        "menuId": 2,
                        "name": "돈까스",
                        "price": 10000,
                        "menuDelete": "ACTIVE"
                    },
                    {
                        "storeId": 1,
                        "menuId": 1,
                        "name": "치킨",
                        "price": 20000,
                        "menuDelete": "ACTIVE"
                    }
                ]
            }
            ```
            

---

### **1-2 가게 메뉴 추가 (Post /api/owners/stores/{storeId}/menus)**

- 상세 내용 펼쳐보기
    - **요청**
        - **Headers**:
            - `Content-Type: application/json`
            - `Cookie: JSESSIONID={sessionId}`
        
        - **Path Variables**:
            - `{storeId}`: 추가할 메뉴의 가게 ID
        - **Body**:
            
            ```json
            {
                "name": "치킨",
                "price": "20000"
            }
            
            ```
            
        - 설명:
            
            
            | # | 이름 | 타입 | 설명 | Nullable |
            | --- | --- | --- | --- | --- |
            | 1 | user | String | 유저 아이디 | X |
            | 2 | menu | String | 메뉴 이름 | X |
    - **응답**
        - **Status Code**: `201 Created`
        - **Body**:
            
            ```json
            {
                "storeId": 1,
                "menuId": 1,
                "name": "치킨",
                "price": 20000,
                "menuDelete": "ACTIVE"
            }
            
            ```
            
    - **예외**
        - `400 Bad Request`: 메뉴 이름이 누락된 경우
        - `403 Forbidden`: 가게 사장이 아닌 사용자가 등록 하려는 경우

---

### **1-3. 가게 메뉴 수정 (PATCH /api/owners/stores/{storeId}/menus/{menuId})**

- 상세 내용 펼쳐보기
    - **요청**
        - **Headers**:
            - `Content-Type: application/json`
            - `Cookie: JSESSIONID={sessionId}`
        - **Path Variables**:
            - `{storeId}`: 수정할 메뉴의 가게 ID
            - `{menuId}`:  수정할 메뉴 ID
        - **Body**:
            
            ```json
            {
                "name": "육회덮밥",
                "price": "9000",
                "menuDelete":"ACTIVE"
            }
            
            ```
            
        - 설명:
            
            
            | # | 이름 | 타입 | 설명 | Nullable |
            | --- | --- | --- | --- | --- |
            | 1 | name | String | 유저 아이디 | X |
            | 2 | price | int | 메뉴 가격 | x |
            | 3 | menuDelete | String | 메뉴 상태 | x |
    - **응답**
        - **Status Code**: `200 OK`
        - **Body**:
            
            ```json
            {
                "storeId":1,
                "menuId":1,
                "name": "육회덮밥",
                "price": "9000",
                "menuDelete":"ACTIVE"
            }
            
            ```
            
    - **예**
        - `403 Forbidden`: 작성자가 아닌 사용자가 수정 하려는 경우
        - `404 Not Found`: 해당 메뉴가 없는 경우

---

### **1-4. 가게 메뉴 삭제 (PUT /api/owners/stores/{storeId}/menus/{menuId})**

- 상세 내용 펼쳐보기
    - 요청
        - **Headers**:
            - `Cookie: JSESSIONID={sessionId}`
            - `Content-Type: application/json`
        - **Path Variables**:
            - `{storeId}`: 삭제할 메뉴의 가게 ID
            - `{menuId}`: 삭제할 메뉴 ID
    - **응답**
    - **Status Code**: `200 OK`
    - **Body**:
        
        ```json
        {
            "storeId":1,
            "menuId":1,
            "name": "육회덮밥",
            "price": "9000",
            "menuDelete":"DEACTIVATE"
        }
        
        ```
        
    - **예**
        - `403 *UNAUTHORIZED*`: 작성자가 아닌 사용자가 삭제 하려는 경우
        - `404 Not Found`: 해당 메뉴가 없는 경우

---

### 2. 주문

### 2-1. 주문 기능 **(POST /api/orders)**

- 상세 내용 펼쳐보기
    - **요청**
        - **Headers**:
            - `Content-Type: application/json`
            - `Cookie: JSESSIONID={sessionId}`
        - **Body**:
            
            ```json
            {
            		"menuId": 1
            }
            ```
            
        - 설명:
            - `404 Not Found`: 해당 메뉴가 없는 경우
            
            | # | 이름 | 타입 | 설명 | Nullable |
            | --- | --- | --- | --- | --- |
            | 1 | menuId | Long | 메뉴 아이디 | X |
        - **Status Code**: `200 OK`
        - 예외
            - `404 Not Found`: 해당 메뉴, 가게가 없는 경우
            - `403 Forbidden` : 최소 주문 금액이 넘지 않은 경우

---

### 2-2. 주문 상태 변경 기능 **(PATCH /api/orders/{orderId})**

- 상세 내용 펼쳐보기
    - **요청**
        - **Headers**:
            - `Content-Type: application/json`
            - `Cookie: JSESSIONID={sessionId}`
        - **Body**:
        
        ```json
        {
        		**"orderStatus": "DELIVERY_FINISHED"
        }
        ```
        
        - 설명:
            - 주문 완료 → 주문 수락 → 조리 중 → 조리 완료 → 배달 중 → 배달 완료 를 표현 해야함.
    - **응답**
        - **Status Code**: `200 OK`
            - `403 Forbidden`: 주문이 들어온 해당 가게의 OWNER가 아닌 경우
            - `404 Not Found`: 해당 메뉴가 없는 경우

---

### 3. 리뷰

### 3-1. 리뷰 생성 (POST **/api/stores/{orderId}/review**)

- 상세 내용 펼쳐보기
    - **요청**
        - **Headers**:
            - `Cookie: JSESSIONID={sessionId}`
            - `Content-Type: application/json`
        
        - **Body**:
            
            ```json
            {
            	"starPoint": 5,
            	"content": "맛있어요"
            }
            ```
            
    - **응답**
        - **Status Code**: 201 Created
        
        - **Body**:
            
            ```json
            {
              "id": 1,
            	"orderId": 1,
            	"starPoint": 5
            	"content": "맛있어요",
              "createdAt": "2024-11-13T14:12:27.223",
            	"modifiedAt": "2024-11-13T14:12:27.223"
            }
            ```
            
        
    - **예외**
        - `400 Bad Request`: 내용이 누락된 경우
        - `403 Forbidden` : ‘배달 완료’되지 않은 주문을 리뷰하려는 경우

---

### 3-2. 가게 리뷰 조회 (GET **/api/stores/{storeId}/review?page=0**)

- 상세 내용 펼쳐보기
    - **요청**
    
    - **Headers**:
        - `Content-Type: application/json`
    - **Path Variables**:
        - `{storeId}`: 조회할 메뉴의 가게 ID
        - **Headers**:
            - `Content-Type: application/json`
        - **Headers**:
            - `Content-Type: application/json`
    - **RequestParam**:
        - `{page}`: 페이징 숫자
    - **응답**
        - **Status Code**: 200 OK
        - **Body**:
            
            ```json
            [
                {
            	    "id": 1,
                  "orderId": 1,
                  "storeId": 1,
                  "userId": 1,
                  "starPoint": 5,
                  "content": "맛있어요",
                  "createdAt": "2024-12-06T09:58:47.437667"
                }
                .....
            ]
            ```
            

---

### 3-3. 가게 리뷰 포인트 범위 조회 (GET **/api/stores/{storeId}/review/point?minStar=2&maxStar=5**)

- 상세 내용 펼쳐보기
    - **요청**
    
    - **Headers**:
        - `Content-Type: application/json`
    - **Path Variables**:
        - `{storeId}`: 조회할 메뉴의 가게 ID
    - **RequestParam**:
        - `{minStar}`: 최소 별점
        - `{maxStar}`: 최대 별점
        - `{page}`: 페이징 숫자
    - **응답**
        - **Status Code**: 200 OK
        - **Body**:
            
            ```json
            [
                {
            	    "id": 1,
                  "orderId": 1,
                  "storeId": 1,
                  "userId": 1,
                  "starPoint": 5,
                  "content": "맛있어요",
                  "createdAt": "2024-12-06T09:58:47.437667"
                }
                .....
            ]
            ```
            

### **4. 유저**

### **4-1. 유저 회원가입 (POST /api/users/signup)**

- 상세 내용 펼쳐보기
    - **요청**
        - **Headers**:
            - Content-Type: application/json
        - **Body**:
        
        ```json
        {
            "userName": "예시명",
            "userEmail": "example@example.com",
            "password": "Aaaa123!",
            "authority": "USER"
        }
        ```
        
        - 설명:
        
        | # | 이름 | 타입 | 설명 | Nullable |
        | --- | --- | --- | --- | --- |
        | 1 | username | String | 유저명 | X |
        | 2 | userEmail | String | 유저 이메일 | X |
        | 4 | password | String | 유저 비밀번호 | X |
        | 5 | authority | String | 권한 USER or OWNER | X |
    - **응답**
        - **Status Code**: `201 Created`
        - **Body**:
        
        ```json
        {
        	"userId": 1,
        	"userName": "예시명",
        	"userEmail": "[example@example.com](mailto:example@example.com)",
        	"profileImageUrl": "http://example.com/profile.jpg",
        	"authority": 0,
        	"createdAt": "2024-11-13T14:12:27.223",
        	"modifiedAt": "2024-11-13T14:12:27.223"
        	
        }
        ```
        
        - 설명:
        
        | # | 이름 | 타입 | 설명 | Nullable |
        | --- | --- | --- | --- | --- |
        | 1 | userId | Long | 유저 고유 식별자 | X |
        | 2 | username | String | 유저명 | X |
        | 3 | userEmail | String | 유저 이메일 | X |
        | 4 | profileImageUrl | String | 유저 프로필 이미지 링크 | O |
        | 5 | authority | TinyInt | 권한(0:유저/1:사장) | X |
        | 6 | createdAt | LocalDateTime | 생성일 | X |
        | 7 | modifiedAt | LocalDateTime | 수정일 | X |
        - 예외
            - `400 Bad Request`: 중복된 사용자 아이디로 가입하는 경우
            - `403 Forbidden`: 사용자 아이디 이메일과 비밀번호 형식이 올바르지 않은 경우

---

### **4-2. 유저 로그인 (POST /api/users/login)**

- 상세 내용 펼쳐보기
    - **요청**
        - **Headers**:
            - `Content-Type: application/json`
        - **Body**:
            
            ```json
            {
                "userEmail": "example@example.com",
                "password": "Aaaa123!"
            }
            ```
            
        - 설명:
            
            
            | # | 이름 | 타입 | 설명 | Nullable |
            | --- | --- | --- | --- | --- |
            | 1 | userEmail | String | 유저 이메일 | X |
            | 2 | password | String | 유저 비밀번호 | X |
    - **응답**
        - **Status Code**: `200 OK`
        - **Body : X**
    - **동작**
        - 성공 시, `HttpServletRequest`를 사용하여 세션 생성 및  `userId` 저장.
        - 예:
        
        ```java
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);
        ```
        
        - 예외
            - `404 Not Found`: 해당 사용자가 존재하지 않는 경우
            - `401 Unauthorized`: 세션이 유효하지 않거나 만료된 경우
            

---

### **4-3. 유저 로그아웃 (POST /api/users/logout)**

- 상세 내용 펼쳐보기
    - **요청**
        - **Headers**: 없음
    - **동작**
        - 세션 제거
            
            ```java
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            
            ```
            
    - **응답**
        - **Status Code**: `200 OK`
        - **Body**: X
        - 예외
            - `401 Unauthorized`: 로그인 상태 아닐 때 로그아웃 하는 경우

---

### **4-4. 유저 회원탈퇴 (PATCH /api/users)**

회원 탈퇴 기능을 구현하기 위해서 User 테이블에 boolean 타입의 칼럼이 하나 있어야함.

- 상세 내용 펼쳐보기
    - **요청**
        - 세션 값에 현재 로그인한 userId가 포함될 것이기 때문에 userId는 필요가 없음.
        - 세션에서 꺼내서 사용하면 됨.
        - **Body:**
        
        ```json
        {
            "password": "Aaaa123!"
        }
        ```
        
    - **응답**
        - **Status Code**: `200 OK`
        - **Body**: X
        
        - 예외
            - `404 Not Found`: 해당 사용자가 존재하지 않는 경우, 이미 탈퇴처리된 사용자인 경우
            - `403 Forbidden`: 비밀번호가 일치하지 않는 경우
            - `401 Unauthorized`: 회원가입 상태 아닐 때 회원탈퇴 하는 경우
            
             
            

---

### **5. 가게**

### **5-1. 가게 생성 (POST /api/owners/stores)**

- 상세 내용 펼쳐보기
    - **요청**
        - **Headers**:
            - `Content-Type: application/json`
            - `Cookie: JSESSIONID={sessionId}`
        
        - **Body**:
            
            ```json
            {
                "name": "가게 이름1",
                "openTime": "09:00:00",
                "closeTime": "22:00:00",
                "minimumOrderPrice": 13000
            }
            
            ```
            
        - 설명:
            
            
            | # | 이름 | 타입 | 설명 | Nullable |
            | --- | --- | --- | --- | --- |
            | 1 | name | String | 가게 이름 | X |
            | 3 | openTime | LocalTime | 오픈 시간 | X |
            | 4 | closeTime | LocalTime | 마감 시간 | X |
            | 5 | minimumOrderPrice | Integer | 최소 주문 금액 | X |
    - **응답**
        - **Status Code**: `201 Created`
        - **Body**:
            
            ```json
            {
                "storeId": 1,
                "userId": 1,
                "name": "가게 이름1",
                "storeStatus": "OPEN",
                "openTime": "09:00:00",
                "closeTime": "22:00:00",
                "minimumOrderPrice": 13000,
                "createdAt": "2024-12-05T14:21:00.934721",
                "modifiedAt": "2024-12-05T14:21:00.934721"
            }
            
            ```
            
        - 설명
            
            
            | # | 이름 | 타입 | 설명 | Nullable |
            | --- | --- | --- | --- | --- |
            | 1 | storeId | Long | 가게 고유 식별자 | X |
            | 2 | userId | Long | 유저 고유 식별자 | X |
            | 3 | name | String | 가게 이름 | X |
            | 4 | storeStatus | StoreStatus | 가게 상태 | X |
            | 5 | openTime | LocalTime | 가게 오픈 시간 | X |
            | 6 | closeTime | LocalTime | 가게 마감 시간 | X |
            | 7 | minimumOrderPrice | Integer | 최소 주문 금액 | X |
            | 8 | createdAt | LocalDateTime | 생성일 | X |
            | 9 | modifiedAt | LocalDateTime | 수정일 | O |
    - **예외**
        - `403 Forbidden`: 가게 이름이 이미 존재하는 경우
        - `403 Forbidden`: 폐업 상태가 아닌 가게가 3개 초과일 경우

---

### **5-2. 가게 다건 조회 (GET /api/stores?page=0&size=10)**

기본 정렬은 생성일자 ****기준으로 내림차순 정렬.

- 상세 내용 펼쳐보기
    - **요청**
        - **Query Parameters**:
            - `page` (default: 1): 조회할 페이지 번호
            - `size` (default: 10): 페이지당 게시물 개수
            - `storeName` (required = false): 가게 이름 (* 검색하고자 할 때 사용한다.)
    - **응답**
        - **Status Code**: 200 OK
        - **Body**:
            
            ```json
            {
                "content": [
                    {
                        "storeId": 2,
                        "name": "가게 이름2",
                        "storeStatus": "OPEN",
                        "openTime": "09:00:00",
                        "closeTime": "22:00:00",
                        "minimumOrderPrice": 13000,
                        "createdAt": "2024-12-04T14:36:34.126298",
                        "modifiedAt": "2024-12-04T14:36:34.126298"
                    },
                    {
                        "storeId": 1,
                        "name": "가게 이름1",
                        "storeStatus": "OPEN",
                        "openTime": "09:00:00",
                        "closeTime": "22:00:00",
                        "minimumOrderPrice": 13000,
                        "createdAt": "2024-12-04T14:36:01.278024",
                        "modifiedAt": "2024-12-04T14:36:01.278024"
                    }
                ],
                "pageable": {
                    "pageNumber": 0,
                    "pageSize": 10,
                    "sort": {
                        "empty": false,
                        "unsorted": false,
                        "sorted": true
                    },
                    "offset": 0,
                    "unpaged": false,
                    "paged": true
                },
                "last": true,
                "totalElements": 2,
                "totalPages": 1,
                "first": true,
                "size": 10,
                "number": 0,
                "sort": {
                    "empty": false,
                    "unsorted": false,
                    "sorted": true
                },
                "numberOfElements": 2,
                "empty": false
            }
            
            ```
            
    

---

### **5-3. 가게 단건 조회 (GET /api/stores/{storeId})**

- 상세 내용 펼쳐보기
    - **요청**
        - **Headers**:
            - `Cookie: JSESSIONID={sessionId}` 로그인하면 자동으로 요청됨.
        - **Path Variables**:
            - `{storeId}` : 가게 고유 식별자
    - **응답**
        - **Status Code**: 200 OK
        - **Body**:
            
            ```json
            {
                "storeId": 1,
                "name": "가게 이름1",
                "storeStatus": "OPEN",
                "openTime": "09:00:00",
                "closeTime": "22:00:00",
                "minimumOrderPrice": 13000,
                "menus": [
                    {
                        "storeId": 1,
                        "menuId": 1,
                        "name": "치킨1",
                        "price": 20000,
                        "menuDelete": "ACTIVE"
                    },
                    {
                        "storeId": 1,
                        "menuId": 2,
                        "name": "치킨2",
                        "price": 20000,
                        "menuDelete": "ACTIVE"
                    }
                ],
                "createdAt": "2024-12-05T14:21:00.934721",
                "modifiedAt": "2024-12-05T14:21:00.934721"
            }
            
            ```
            
            | # | 이름 | 타입 | 설명 | Nullable |
            | --- | --- | --- | --- | --- |
            | 1 | storeId | Long | 가게 고유 식별자 | X |
            | 2 | name | String | 가게 이름 | X |
            | 3 | openTime | LocalTime | 가게 오픈 시간 | X |
            | 4 | closeTime | LocalTime | 가게 마감 시간 | X |
            | 5 | minimumOrderPrice | Integer | 최소 주문 금액 | X |
            | 6 | menus | Menu[] | 가게 메뉴 | X |
            | 7 | storeStatus | StoreStatus | 가게 상태 | X |
            | 8 | createdAt | LocalDateTime | 생성일 | X |
            | 9 | modifiedAt | LocalDateTime | 수정일 | O |

---

### **5-4. 가게 수정 (PATCH /api/owners/stores/{storeId})**

- 상세 내용 펼쳐보기
    - **요청**
        - **Headers**:
            - `Cookie: JSESSIONID={sessionId}`
            - `Content-Type: application/json`
        - **Path Variables**:
            - `{postId}`: 수정할 가게 ID
        - **Body**:
            
            ```json
            {
            		"name": "수정된 가게 이름",
                "openTime": "09:30:00",
                "closeTime": "22:00:00",
                "minimumOrderPrice": 15000
            }
            
            ```
            
    - **응답**
        - **Status Code**: 200 OK
        - **Body**:
            
            ```json
            {
                "storeId": 1,
                "userId": 1,
                "name": "수정된 가게 이름",
                "storeStatus": "OPEN",
                "openTime": "09:30:00",
                "closeTime": "22:00:00",
                "minimumOrderPrice": 15000,
                "createdAt": "2024-11-13T14:12:27.223",
                "modifiedAt": "2024-11-19T15:00:00.123456"
            }
            
            ```
            
    - **예**
        - `403 Forbidden`: 작성자가 아닌 사용자가 수정하려는 경우
        - `404 Not Found`: 해당 가게가 없는 경우

---

### **5-5. 가게 폐업 ( PATCH /api/owners/stores/status/{storeId})**

- 상세 내용 펼쳐보기
    - **요청**
        - **Headers**:
            - `Cookie: JSESSIONID={sessionId}`
            - `Content-Type: application/json`
        - **Path Variables**:
            - `{`**storeId**`}`: 폐업할 **가게** ID
        - Body: X
    - **응답**
        - **Status Code**: 200 OK
        - **Body**:
            
            ```json
            {
                "storeId": 1,
                "name": "가게 이름",
                "storeStatus": "CLOSE"
            }
            ```
            
            | # | 이름 | 타입 | 설명 | Nullable |
            | --- | --- | --- | --- | --- |
            | 1 | storeId | Long | 가게 고유 식별자 | X |
            | 2 | name | String | 가게 이름 | X |
            | 6 | storeStatus | StoreStatus | 가게 상태 | X |
        - **예외**
            - `401 Unauthorized`: 사장님 권한을 가진 유저만 가게를 폐업할 수 있습니다.
            - `404 Not Found`: 해당하는 가게가 존재하지 않습니다.

---

## ERD

![ERD](https://github.com/user-attachments/assets/a1505520-d966-4bbf-8107-ebe6836cf94e)

