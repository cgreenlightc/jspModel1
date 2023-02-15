<%@page import="dto.MemberDto"%>
<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");

	MemberDao dao = MemberDao.getInstance();  // dao 클래스를 불러온다.
	
	MemberDto mem = dao.login(id,pwd);   // dto로 멤버를 리턴을 받는다, id,pwd 넘겨 준다.
	
	if(mem != null) {		// null이 아니라는거는 id,pwd가 매칭이 됐다는 의미
		// 로그인 회원정보 session에 저장된다. 얻는 방법은 2가지
		// request.getSession().setAttribute("login", mem);
		session.setAttribute("login", mem);  // login한 정보(mem)를 저장을 해라는 의미  // mem을 object로 저장한다.
	//	session.setMaxInactiveInterval(60 *60 * 2); // session의 유효기간 (안해도된다.)
		%>
		<script type="text/javascript">
		alert("환영합니다. <%=mem.getId() %>님");
		location.href = "./bbslist.jsp";
		</script>
		<%
	}else{
		%>
		<script type="text/javascript">
		alert("아이디나 패스워드를 확인하십시오.");
		location.href = "login.jsp";
		</script>
		<% 
	}
	%>
		
		
		
		
		
		