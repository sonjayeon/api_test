# BOARD

### DB Column v
cmn, seq, id, registDateTime, modifyDateTime, title, payload 

### 게시판 리스트(select)
페이징 객체(현재 페이지 번호)  
cmn, seq, id, registDateTime, title

1. 화면에서 페이징 객체를 받음.
2. page max값 db에서 받아오기.
3. 페이징 객체의 현재 페이지 번호로 db에서 게시판 리스트 조회.
4. page 객체에 현재 페이지, 전체 페이지, 위의 list 넣어서 리턴.
5. dto를 클라이언트에게 응답값으로 보내줌.

### 등록화면(insert)
cmn, seq, registDateTime, title(v), payload(v)
1. 화면에서 title, payload 값 받음.
2. title, payload 값 DB에 등록함.
3. board table 조회해서 entity를 dto로 바꿔줌.
4. dto를 클라이언트에게 응답값으로 보내줌.

### 게시판 글 목록 중 상세보기 (1건 조회)
cmn, seq, id, registDateTime, title, payload
특정 글 시퀀스 값 파라미터로 넘어감

1. 화면에서 시퀀스 값 받음.
2. 시퀀스 값으로 DB를 조회함.
3. entity를 dto로 바꿔줌.
4. dto를 클라이언트에게 응답값으로 보내줌.