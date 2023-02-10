<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String id = request.getParameter("id");
	System.out.println("id:" + id);			// 잘넘어갔는지 확인

	MemberDao dao = MemberDao.getInstance();
	boolean b = dao.getId(id);
	
	if(b == true){ // id가 있음
		out.println("NO");	// id가 있으면 중복
	}else{
		out.println("YES"); // id가 없으니 사용가능의 예스
	}
%>