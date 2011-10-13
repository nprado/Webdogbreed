<%@ page language="java" import = "mc536.dogbreed.dao.ResearcherDao, mc536.dogbreed.entities.Researcher" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    ResearcherDao resDao = new ResearcherDao();
	Researcher res = new Researcher(-1 , request.getParameter("username"), request.getParameter("password"), 0);
	
	resDao.save(res);
		
		
		out.println("<h1>Register Success</h1> <a href=\"../admin/login.jsp\">Entrar</a>");		
    %>
    	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registrar - DogHouse</title>
</head>
<body>

</body>
</html>