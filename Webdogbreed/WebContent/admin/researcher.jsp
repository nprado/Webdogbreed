<%@ page language="java" import = "mc536.dogbreed.dao.ResearcherDao, mc536.dogbreed.entities.Researcher, java.util.List" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DogHouse - Pesquisadores</title>
</head>
<body>
 <!-- Esse eh o cabecalho usado em todas as paginas do CRDU  -->
<div><a href="logoffAction.jsp">Logoff</a> | <a href="../alluser/faq.html">FAQ</a><br>Logado com sucesso!</div>
<div>
	<a href="researcher.jsp">Pesquisadores</a> | <a href="breed.jsp">Raças</a> | <a href="picture.jsp">Fotos</a>
	| <a href="physical.jsp">Caracteristicas Físicas</a> | <a href="behavioral.jsp">Caracteristicas Comportamentais</a>
	| <a href="../alluser/fag.html">Doenças</a> | <a href="../alluser/fag.html">Sintomas</a>
	| <a href="../alluser/fag.html">Profilaxia</a>
</div>
<!-- fim do cabecalho usado em todas as paginas do CRDU  -->

<form method="POST" action="findResarcherAction.jsp" >
<h1>Filtrar</h1>
	
	<p>Nome:<br><input type="text" tabindex="1" name="search_username" id="search_username" size="25" value=""></p>	
	<p>Raça Pesquisada:<br><input  tabindex="2" id="search_breed" name="search_breed" size="25"></p>
	
	<p> <input type="submit" name="filter" tabindex="6" value="Filtrar"> <p>
</form>	

<form method="POST" action="saveResearcherAction.jsp" >
<h1>Novo</h1>
	
	<p>Nome:<br><input type="text" tabindex="1" name="search_username" id="search_username" size="25" value=""></p>	
	<p>Raça Pesquisada:<br><input  tabindex="2" id="search_breed" name="search_breed" size="25"></p>
	
	<p> <input type="submit" name="save" tabindex="6" value="Criar"> <p>
</form>	

	<table border = "1">
	<tr bgcolor = "#cccccc">
	<td> Id </td>
	<td> Pesquisador </td>	 
	<TD> Senha </TD>
	<td> idBreed </td>
	<TD> Ação  </TD>
	<%
	ResearcherDao resDao = new ResearcherDao();
	List<Researcher> resList = resDao.list();
	for (Researcher r: resList){
	%>	
	<TR>	
		<TD> <%= r.getidResearcher() %> </TD>
		<TD> <%= r.getName() %> </TD>
		<TD> <%= r.getPassword()%> </TD>
		<TD> <%= r.getidBreed() %> </TD>
		<TD> <a href = "excluir.jsp" > </a>
	<%
	}
	%>
	</table>


</body>
</html>