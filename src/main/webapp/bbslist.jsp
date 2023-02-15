<%@page import="dto.BbsDto"%>
<%@page import="java.util.List"%>
<%@page import="dao.BbsDao"%>
<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	MemberDto login = (MemberDto)session.getAttribute("login");  //  (MemberDto) 앞에 이걸 적은 것은 object를 MemberDto로 형 변환을 해주기위함.
	if(login == null) {
		%>
		<script>
		alert('로그인 해 주십시오');
		location.href = "login.jsp";
		</script>
		<%
	}

%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
String choice = request.getParameter("choice");
String search = request.getParameter("search");

if(choice == null){
	choice = "";
}
if(search == null){
	search = "";
}

BbsDao dao = BbsDao.getInstance();

// 페이지 넘버(현재)
int pageNumber = 0; 

// List<BbsDto> list = dao.getBbsList();
List<BbsDto> list = dao.getBbsSearchList(choice, search);   //원래는 위인데 29~37을 작성하면서 이렇게 바꼈다.

// 글의 총수
int count = dao.getAllBbs(choice, search);

// 페이지의 총수
int pageBbs = count / 10;		// 10개씩 총글의 수를 나눔
if((count % 10) > 0){
	pageBbs = pageBbs + 1;		
}

%>

<h1>게시판</h1>

<div align="center">

<table border="1">
<col width="70"><col width="600"><col width="100"><col width="150">
<thead>
<tr>
	<th>번호</th><th>제목</th><th>조회수</th><th>작성자</th>
</tr>
</thead>
<tbody>

<%
if(list == null || list.size() ==0){
	%>
	<tr>
		<td colspan="4">작성된 글이 없습니다</td>
	</tr>
	<%	
}else{
	
	for(int i = 0; i < list.size(); i++)
	{	
		BbsDto dto = list.get(i);
		%>
		<tr>
			<th><%=i + 1 %></th>
			<td><%=dto.getTitle() %></td>
			<td><%=dto.getReadcount() %></td>
			<td><%=dto.getId() %></td>
		</tr>
		<%
	}
}	
%>

</tbody>
</table>

<br>

<%
	for(int i= 0;i < pageBbs; i++){
		if(pageNumber == i){	// 현재 페이지
			%>
			<span style="font-size: 15pt; color: #0000ff; font-weight: bold;">
				<%=i+1 %>
			</span>	
			<% 	
		}else{					// 그밖의 페이지
			%>
			<a href="#none" title="<%=i+1 %>페이지" onclick="goPage(<%=i %>)"
				style="font-size: 15pt; color: #000; font-weight:bold; text-decoration: none;">
				[<%=i+1 %>]
			</a>
			<% 
		}
	}
%>

<br><br>

<select id="choice">
	<option value="">검색</option>
	<option value="title">제목</option>
	<option value="content">내용</option>
	<option value="writer">작성자</option>
</select>

<input type="text" id="search" value="">

<button type="button" onclick="searchBtn()">검색</button>

<br><br>
<a href="bbswrite.jsp">글쓰기</a>

</div>

<script type="text/javascript">
function searchBtn() {
	let choice = document.getElementById('choice').value;
	let search = document.getElementById('search').value;
	// 107~114 까지 주석을 달고 실행하게 되면 전체글보기로 돌아가게 된다.	
	/* if(choice == "") {
		alert("카테고리를 선택해 주십시오");
		return;
	} */
	/* if(search.trim() == ""){
		alert("검색어를 선택해 주십시오");
		return;
	} */
	
	location.href = "bbslist.jsp?choice=" + choice + "&search=" + search;  // choice와 search를 넘겨줬따 bbslist로~!
}

function goPage( pageNumber ) {
	let choice = document.getElementById('choice').value;
	let search = document.getElementById('search').value;
	
	location.href = "bbslist.jsp?choice=" + choice + "&search=" + search + "&pageNumber=" + pageNumber;
	
}
</script>

</body>
</html>