<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jquery 링크 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<!-- cookie 링크 -->
<script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript" ></script>

<style type="text/css">
.center{
	margin: auto;
	width: 60%;
	border: 3px solid #ff0000;
	padding: 10px;	
}
</style>

</head>
<body>

<h2>login page</h2>

<div class="center">

<form action="loginAf.jsp" method="post">  <!-- login 한 id,pwd 가 loginAf로 가라!! -->

<table border="1">
<tr>
	<th>아이디</th>
	<td>
		<input type="text" id="id" name="id" size="20"><br>
		<input type="checkbox" id="chk_save_id">id 저장		
	</td>
</tr>
<tr>
	<th>패스워드</th>
	<td>
		<input type="password" name="pwd" size="20">	
	</td>
</tr>
<tr>
	<td colspan="2">
		<input type="submit" value="log-in">
		<a href="regi.jsp">회원가입</a>
	</td>
</tr>
</table>

</form>
</div>

<script type="text/javascript">
/*
 	cookie : id저장, pw저장 == String  --> 저장공간 client
 	session : login한 정보 == Object --> 저장공간 server
*/

let user_id = $.cookie("user_id");
 	
if(user_id != null) {  // 'null 이 아니면' 이라고 하니 그의미는 저장한 id가 있음
	$("#id").val(user_id);
	$("#chk_save_id").prop("checked", true);
}

$("#chk_save_id").click(function () {
	
	if( $("#chk_save_id").is(":checked") == true ) { // == true 생략 가능 디폴트값
 		// alert('true');  // 체크박스에 체크하면 true 창이 뜸
 		if( $("#id").val().trim() == "" ) { // id를 입력했을때 그 값이 빈칸이면...
 			alert('id를 입력해 주십시오');
 			$("#chk_save_id").prop("checked", false); // 사실은 체크가 되는데 꺼준거라고 보면 된다.
 		}else{
 			// cookie를 저장하는 부분
 			$.cookie("user_id", $("#id").val().trim(), { expires:7, path:'./'}); // { expires:7일동안 path:'모든경로'}기한을 정해줌.
 		}
 		
	}else{
		// alert('false'); // 체크박스에 체크를 풀면 false 창이 뜸
		$.removeCookie("user_id", { path:'./'}); // cookie 
	}	
	
});


</script>

</body>
</html>