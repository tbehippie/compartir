✔프로젝트 명 : Compartir(컴파티르)<br>
✔프로젝트 목적 : 소통기능에 집중한 SNS를 제공<br>
✔작업 기간 : ‘23. 03. 29 ~ ‘23. 04. 13<br>
✔작업 인원 : 3명<br>
✔개발 환경 : Window 10 Pro x64bit, Eclipse 4.24.0, Spring Boot2.7.10, Gradle 7.6.1 Groovy<br>
✔사용 언어 : <br>
- Front-End : HTML, CSS, JavaScript, Thymeleaf, Bootstrap<br>
- Back-End : Java11, Dependencies(Spring Web, Spring Data JDBC, H2, Lombok, Thymeleaf)<br>
<br>
💎구현한 부분 소개💎<br>
⦁ 관리자 공지사항 기능 구현(게시판 조회/등록/수정/삭제) <br>
  : MVC(Domain, Repository, Service, Controller), Thymeleaf 작성<br>
⦁ Test코드 작성(@SpringBootTest 유닛테스트 사용)<br>
⦁ 게시글 프론트 페이지(post.html) 구현<br>
⦁ 트러블슈팅<br>
<br>

▪ 코드
<a href = "https://github.com/tbehippie/compartir/wiki/Domain"> ⚡Domain</a>
<a href = "https://github.com/tbehippie/compartir/wiki/Repository"> ⚡Repository</a>
<a href = "https://github.com/tbehippie/compartir/wiki/Service"> ⚡Service</a>
<a href = "https://github.com/tbehippie/compartir/wiki/Controller"> ⚡Controller</a>

<h1> ✔ 웹페이지 </h1>

<h2> 1) 관리자(아이디admin/비밀번호1234) 로그인</h2>

   ![로그인](https://github.com/tbehippie/compartir/assets/122521832/f5fc9165-1578-4a75-a746-95c35be2ebd1)
   
<h2> 2) 공지사항 목록페이지 </h2>

   ![공지사항페이지](https://github.com/tbehippie/compartir/assets/122521832/2a57456e-ff66-4e43-b47f-186a8b87dc15)
   
<h2> 공지사항 작성 </h2>

   ![공지작성](https://github.com/tbehippie/compartir/assets/122521832/931490f7-e690-40a4-8ef4-61e28f9bdf02)
   
<h2> 공지사항 수정폼</h2>
   
   ![공지수정1-2](https://github.com/tbehippie/compartir/assets/122521832/a1166ca2-0e1d-4786-a9a4-3be19703b2b4)
   
<h2> 공지사항 수정 및 삭제</h2>

   ![공지수정1-1 + 삭제](https://github.com/tbehippie/compartir/assets/122521832/2621f102-6ad5-4b2a-894c-cc3ecf684ba1)
