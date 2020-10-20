# Develop Smell (커뮤니티성 개발블로그)

## Member

##### Team 4Men
* Front-End + Back-End
  * 전병규 (팀장)
  * 이석기
  * 윤영현
  * 장진우

## Project introduction

* 유저들이 만드는 트렌디한 블로그

## Function & Description

<details>
<summary>유저관리</summary>

* 유저에 대한 전반적인 기능입니다.
  * 회원가입 기능
  * 로그인 기능
  * 정보 수정 기능
</details>

<details>
<summary>포스트 작성</summary>

* 자신의 블로그에 포스트를 작성하는 기능입니다.
  * Toast UI Editor를 사용하여 Markdown과 WYSIWYG 편집기를 사용.
</details>

<details>
<summary>포스트 조회</summary>

* 어떤 사람이 쓴 글이던지 조회가 가능합니다.
  * 포스트의 리스트는 인피니트 스크롤을 적용하여 페이징 처리.
</details>

<details>
<summary>랭킹</summary>

* 게시글과 유저에 랭킹을 부여하는 기능입니다.
  * 게시글에는 보이지않는 점수가 있고, 이 점수로 유저들이 보다 질 좋은 포스트를 볼 수 있도록 합니다.
  * 유저에게 포스팅을 하는 동기부여가 될 수 있도록 랭킹시스템을 도입하여 포스팅의 재미를 더해줍니다.
</details>

# Backend

## User API

<details>
<summary>회원 정보 수정</summary>

### 회원 정보 수정
---
- path : .../account
- method : PUT
- input data : userno, uid, password, introduce
</details>

<details>
<summary>회원 정보 조회</summary>

### 회원 정보 조회
---
- path : .../account/{userno}
- method : GET
- input data : userno
</details>

<details>
<summary>회원 정보 삭제</summary>

### 회원 정보 삭제
---
- path : .../account/{userno}
- method : DELETE
- input data : userno
</details>

<details>
<summary>메일 인증회</summary>

### 메일 인증
---
- path : .../account/certification
- method : POST
- input data : email
- output data : code
</details>

<details>
<summary>로그인</summary>

### 로그인
---
- path : .../account/login
- method : POST
- input data : email, password
- output data : userno, uid, email
</details>

<details>
<summary>로그인 여부 확인</summary>

### 로그인 여부 확인 
---
- path : .../account/user/{userno}
- method : GET
- input data : userno
- ouput data : uid, email
</details>

## Comment API

<details>
<summary>댓글 조회</summary>

###  댓글 조회
---
- path : .../comment/{postno}/{userno}
- method : GET
- input data : postno, userno
- output data : commentno, userno, postno, uid, reply, reply_date, likecount, dislikecount, kind, profilename 
</details>

<details>
<summary>댓글 작성</summary>

### 댓글 작성
---
- path : .../comment
- method : POST
- input data : userno, reply
</details>

<details>
<summary>좋아요, 싫어요 추가</summary>

### 좋아요, 싫어요 추가
---
- path : .../comment/like
- method : POST
- input data : userno, targetno, kind
</details>

<details>
<summary>좋아요, 싫어요 삭제</summary>

### 좋아요, 싫어요 삭제
---
- path : .../comment/like/{userno}/{commentno}
- method : DELETE
- input data : userno, commentno
</details>

<details>
<summary>댓글 삭제</summary>

### 댓글 삭제
---
- path : .../comment/{commentno}
- method : DELETE
- input data : commentno
</details>

<details>
<summary>좋아요, 싫어요 수정</summary>

### 좋아요, 싫어요 수정
---
- path : .../comment/like
- method : PUT
- input data : userno, targetno, kind
</details>

## Post API

<details>
<summary>게시글 작성</summary>

### 게시글 작성
---
- path : .../post
- method : POST
- input data : userno, postno, title, subtitle, content, save
</details>

<details>
<summary>게시글 수정</summary>

### 게시글 수정
---
- path : .../post
- method : PUT
- input data : postno, title, content
</details>

<details>
<summary>게시글 삭제</summary>

### 게시글 삭제
---
- path : .../post/{postno}
- method : DELETE
- input data : postno
</details>

<details>
<summary>게시글 조회</summary>

### 게시글 조회
---
- path : /post/total/{limit}/{userno}
- method : GET
- input data : limit, userno
- output data : postno, userno, profilename, tags, title, subtitle, content, create_date, likecount, dislikecount, kind
</details>

<details>
<summary>게시글 좋아요, 싫어요</summary>

### 게시글 좋아요, 싫어요
---
- path : .../post/like
- method : POST
- input data : userno, targetno, kind
</details>

<details>
<summary>게시글 좋아요, 싫어요 수정</summary>

### 게시글 좋아요, 싫어요 수정
---
- path : .../post/like
- method : PUT
- input data : userno, targetno, kind
</details>

<details>
<summary>게시글 좋아요, 싫어요 삭제</summary>

### 게시글 좋아요, 싫어요 삭제
---
- path : .../post/like/{userno}/{postno}
- method : DELETE
- input data : userno, postno
</details>
