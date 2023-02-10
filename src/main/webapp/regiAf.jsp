<%@page import="dto.MemberDto"%>
<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	request.setCharacterEncoding("utf-8");    // 한글 깨지는 것을 방지해준다.
	//데이터 받아오기 refi.jsp에서 input 에서 name이 등록되어있따
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	String name = request.getParameter("name");
	String email = request.getParameter("email");

	// back-end 필요 (회원가입을 시켜줘야 하기 떄무에 db에서 넣어줘야 된다. 그래서 다시 dao로 간다.)
	MemberDao dao = MemberDao.getInstance();

	MemberDto dto = new MemberDto(id, pwd, name, email, 0);
	boolean isS = dao.addMember(dto);
	if(isS){
	   %>
			<script type="text/javascript">
			alert("성공적으로 가입되었습니다");
			location.href = "login.jsp";
			</script>
		<%
	}else{
		%>
			<script type="text/javascript">
			alert("다시 가입해 주십시오");
			location.href = "regi.jsp";
			</script>
		<%
	}
%>
