<%@ page language="java" import = "mc536.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>DogHouse</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
<script type="text/javascript">

	function validateForm(theForm){

		if(theForm.username.value==""){

		//Please enter username
		alert("Please enter User Name.");
		theForm.userid.focus();

		return false;

	}
	if(theForm.password.value==""){
		//please enter passward
		alert("Please enter Password.");
		theForm.password.focus();
	return false;
	}

	return true;
	} 
</script>
</head>
<body bgcolor="#FFFFFF">
<form method="POST" action="loginAction.jsp" onsubmit="return validateForm(this);">

<div><a href="admin/login.jsp">Entrar</a> | <a href="alluser/register.jsp">Registrar</a> | <a href="alluser/fag.html">FAQ</a><br></div>
	
	<h1>Entrar</h1>
	
	<p>Nome de Usuário:<br><input type="text" tabindex="1" name="username" id="username" size="25" value=""></p>	
	<p>Senha:<br><input type="password" tabindex="2" id="password" name="password" size="25"></p>
	<p> <input type="submit" name="login" tabindex="6" value="Entrar"> <p>
	</form>
	<%
		
		%>
</body>			
</html>
