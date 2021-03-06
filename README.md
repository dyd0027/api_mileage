# api_mileage
#### 포인트를 부여하고, 포인트 부여 히스토리와 개별 누적 포인트를 관리하고자 하는 API 개발

## 목차
1. [개발 환경](#1_개발-환경)
2. [스카마 설계](#2_스키마-설계)
3. [실행 방법](#3_실행-방법)
4. [실행 결과](#4_실행-결과)
5. [예외사항](#5_예외사항)


---
## 1_개발 환경
- Java8
- MySQL(v8.0)
- STS3
- mybatis
- Tomcat(v8.5)

## 2_스키마 설계
- user
  - userID : 계정 식별 값
  - totalCount : 현재 포인트 점수
- place 
  - placeID : 장소 식별 값
- review
  - reviewID : 리뷰 식별 값
  - placeID : 장소 식별 값
  - userID : 유저 식별 값
- review_history
  - reviewID : 리뷰 식별 값
  - placeID : 장소 식별 값
  - userID : 유저 식별 값
  - date : 리뷰를 올린 날짜
  - action : event 값
  - point : 해당 action에서 얻은 point 
- photo
  - reviewID : 리뷰 식별 값
  - photoID : 사진 ID
---
## 3_실행 방법
- 스키마
  - admin.sql을 실행 시켜 database 및 계정을 생성해 준다. (db:triple, id:triple, pw:triple)
  - user.sql을 실행 시켜 [스키마 설계](#2_스키마-설계)에 정의 된 테이블을 생성한다.
  - data.sql을 실행 시켜 테이블에 user와 place 데이터를 넣어준다.
- Spring
  - STS3에 해당 프로젝트를 import 시킨다.
  - 개발환경에 맞게 셋팅 후 프로젝트 실행시킨다.
- event
  - 조건에 맞게 add, mod, delete를 시켜준다(reviewID는 이벤트마다 UUID포멧으로 유니크한 값을 넣어주세요.)
  - 예외사항은 [예외사항](#5_예외사항) 참조
- 포인트 조회
  - GET 방식 url : /inquiry 
  - userID 만 입력하여 해당 유저의 모든 포인트 히스토리를 장소별로 확인.
  - userID && placeID를 입력하여 해당 유저의 특정 장소 포인트 히스토리를 확인. 
---
## 4_실행 결과
|alias|ID|
|:--:|:--:|
|user A|0ffaa5ef-faaf-11ec-9047-b4a9fceece61|
|user B|10e6a68f-faaf-11ec-9047-b4a9fceece61|
|place a|15d4e96a-faaf-11ec-9047-b4a9fceece61|
|place b|162f52d6-faaf-11ec-9047-b4a9fceece61|
|place c|803a7c57-fab7-11ec-9047-b4a9fceece61|
#### 4.1 유저 A로 장소 a 첫 리뷰 등록(content, attactedPhotosIds,최초 등록 --> point : 3점)
![2  첫 add](https://user-images.githubusercontent.com/82923905/177497944-0b10f1cd-348d-46e0-bc15-608d63e8f3bd.JPG)
#### 4.2 유저 A로 장소 a 수정(content --> point : 2점)
![3  mod](https://user-images.githubusercontent.com/82923905/177498216-71a1a389-ebaa-4316-b776-1502055431c1.JPG)
#### 4.3 유저 A로 장소 a 삭제(deletePoint : -2점)
![4  delete](https://user-images.githubusercontent.com/82923905/177498358-4f1608d0-e0d0-481f-b682-0dad868f2986.JPG)
#### 4.4 유저 B로 장소 a 등록(content,최초 등록 --> point : 2점)
![5  다른계정 첫 add](https://user-images.githubusercontent.com/82923905/177498736-ff0171f9-2d1c-4cf0-b70c-2bd401c30989.JPG)
#### 4.5 유저 A로 장소 a 등록(content, 최초 등록x --> point : 1점)
![6  기존 계정 add](https://user-images.githubusercontent.com/82923905/177499023-6afc98fd-8cc0-4ba9-9a3f-b6333c033278.JPG)
#### 4.6 계정별 이력 조회(변수 / userID : 유저 A)
![7  계정별 이력 조회](https://user-images.githubusercontent.com/82923905/177499185-3a86cf6d-bacc-48a9-89f1-47c6e34a454a.JPG)
#### 4.7 계정+장소 이력 조회(변수 / userUD : 유저 A, placeID : 장소 a)
![8 계정+장소별 이력 조회](https://user-images.githubusercontent.com/82923905/177499439-5138a3b3-e7e1-4607-9e0d-bf49e7902b98.JPG)

---


## 5_예외사항
  - result : false // msg : 각 에러 사항
#### 5.1 없는 계정 입력
![9  없는 계정](https://user-images.githubusercontent.com/82923905/177537120-e76ede03-62b9-4d26-a853-cea569aee1eb.JPG)
#### 5.2 없는 장소 입력
![10 없는장소](https://user-images.githubusercontent.com/82923905/177537138-cf90576a-03e4-42a2-9f65-4421929cc3b7.JPG)
#### 5.3 add하기전에 mod or delete 실행
![11  노 수정](https://user-images.githubusercontent.com/82923905/177537176-47efb1ed-ff3a-40a5-83ef-c1a949b042b2.JPG)
![12  노 삭제](https://user-images.githubusercontent.com/82923905/177537199-dc248519-b50f-4710-a85a-3da037ca72bb.JPG)
#### 5.4 add or mod 후에 add 실행
![14  add두번](https://user-images.githubusercontent.com/82923905/177537254-9efcc778-8b39-4af6-8776-fe178eeb8c16.JPG)
#### 5.5 event가 add,mod,delete가 아닌 경우
![15  없음](https://user-images.githubusercontent.com/82923905/177537318-b6ed5189-c403-41c1-a4ac-f1eb40ee2c19.JPG)

