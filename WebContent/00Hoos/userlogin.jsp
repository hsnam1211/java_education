<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
#login_view
{
	width:520px;
	border:1px solid black;
	padding:20px 40px;
	margin:0 auto;
}
#userid, #userpass {
	width: 200px;
	height: 25px;	
}
form, table{
	margin: 0 auto;
	text-align: center;
}
form > input {
	width: 208px;
	height: 30px;
}
#error {
	color: red;
}
</style>
</head>
<%
	/* String errMsg = (String)request.getAttribute("errMsg"); */
	String errMsg = (String)session.getAttribute("errMsg");
	
	if(errMsg == null) {
		errMsg = "";
	}
%>
<body>
	<header>
		<img src="/webProject(hoos)/00Hoos/images/스케치.png" width="100px" >
	</header>
	
	<main>
		<section>
			<fieldset id="login_view">
				<legend><h1>로그인</h1></legend>
					<form action="login.jsp" method="post">
						<table>
							<tr>
								<td><input type="text" id="userid" name="userid" autofocus placeholder="ID"><br></td>						
							</tr>
							<tr>
								<td><input type="text" id="userpass" name="userpass" placeholder="PASSWORD"><br></td>
							</tr>
							<tr>
								<td id="error"><%=errMsg %></td>
							</tr>
						</table>	
						<input type="submit" value="로그인">
					</form>
			
			</fieldset>
			
		</section>
	</main>
	
	<footer>
	
	</footer>
</body>
</html>