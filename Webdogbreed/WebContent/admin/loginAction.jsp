<%@ page language="java" import = "mc536.dogbreed.dao.ResearcherDao, mc536.dogbreed.entities.Researcher , java.sql.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
		ResearcherDao resDao = new ResearcherDao();
		Researcher res = new Researcher(-1 , request.getParameter("username"), request.getParameter("password"), 0);
		System.out.println(res.getName());
		int login = resDao.authenticate(res.getName(), res.getPassword());
		if ( login != -1){
			session.setAttribute("user", request.getParameter("username"));
			%>
			<jsp:forward page="loginSuccess.jsp" />
			<% 
		}else{
			out.println("Login Failed");	
		}%>
		
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
</body>
</html>
