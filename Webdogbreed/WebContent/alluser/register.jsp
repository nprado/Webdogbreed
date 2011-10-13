<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="POST" action="registerAction.jsp" >
<h1>Registrar</h1>
	
	<p>Nome de Usuário:<br><input type="text" tabindex="1" name="username" id="username" size="25" value=""></p>	
	<p>Senha:<br><input type="password" tabindex="2" id="password" name="password" size="25"></p>
	<p>Confirmação de Senha:<br><input type="password" tabindex="3" id="repassword" name="repassword" size="25"></p>
	<p> <input type="submit" name="login" tabindex="6" value="Registrar"> <p>
</form>	
</body>
</html>