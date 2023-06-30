# API 예제
    REST-API
        * GET(조회) V
        * POST(생성) v
        * DELETE(삭제) v
        * PUT (PATCH)(수정) v

    TEST
        * JUNIT v
        * POSTMAN v
        * CURL

    DB
        * 회원
            - 아이디
            - 비밀번호
            - 이름
            - 생성일자 (yyyy-MM-dd hh:MM:ss)
            - 수정일자 (yyyy-MM-dd hh:MM:ss)
            - 성별 (ENUM)
            - 연애 유무 (Y/N)
            - 자기소개
        * INDEX - 필요할 것 같은 부분에 추가해주세요. v
        * Table, Column에 comment 달아주세요. v
        * 변경 이력을 관리하는 테이블을 추가로 관리해주세요. v
            - 뭘 수정했는지
            - 언제 수정했는지 (회원 테이블의 수정일자와 동일해야함)
            - 아이디는 수정이 불가능함 v
            - 변경 발생 시,
                as-is -> to-be
                변경 이력 - insert(entity -> before, dto -> after)
                회원 테이블 - update

    RETURN
        * ResponseEntity로 정의
        * 응답은 무조건 성공 (Http Status : 200)
        * 쉽게 응답할 수 있는 방법을 생각해보세요.
        * 에러가 날 경우, 쉽게 응답할 수 있는 방법도 생각해보세요.
        
* 저희 MVC 패턴에 맞게 만들어주세요.
* 우리 서비스에 다 있는 부분들이니 알아서 잘 찾아서 해보세요.
* 열심히 하세요.

#### 순서
1. DB 테이블 생성
2. DB 인덱스 생성
3. 서비스 로직 구현
4. 서비스 로직 테스트 (junit)
5. 컨트롤러 구현
6. curl 테스트

#### 회원 변경 기록 조회
    특정 회원의 변경 기록을 변경일자 기준으로 내림차순으로 정렬하고 페이지 당 10건씩 보여주세요.
    1. 특정 회원의 변경 기록을 변경일자 기준으로 내림차순 정렬해주세요. v
    2. 이때, '변경'버튼을 기준으로 어떤 게 변경이 됐는지 묶어서 보여주세요.
    3. 보여줄 때, 한 페이지에 10건씩 보여주시고 현재 페이지 번호랑 총 몇 페이지가 있는지 보여주세요.
    
    param 값
    * cmn

    100 -> 10건 10페이지
    123 -> 10건 13페이지

    return 값
    {
    currentPageNum : 1 (현재 페이지 번호) // 값 파라미터로 받기
    totalPageNum : 20 (총 페이지 번호) // db 조회해서 maxPage % 10 == 0 : maxPage / 10 : maxPage / 10 + 1
    nextPageNum : 2 (다음 페이지 번호) // currentPageNum == totalPageNum -> null
    prevPageNum : null (이전 페이지 번호) // currentPageNum - 1 == 0 ? null : currentPageNum - 1
    listSize : 10 (이력 갯수)
    list : [
        historyList : [ 
            {MemberModHistoryEntity 값},
            {},
            {}
        ],
        historyList : [ 
            {},
            {},
            {}
        ],
        historyList : [
            {},
            {},
            {}
        ],
        historyList : [
            {},
            {},
            {}
        ],
    ]
    }
