<%
if(session == null || session.getAttribute("gebruiker") == null){
	response.addCookie(new Cookie("vorigepagina", request.getRequestURI()));
	response.sendRedirect("http://localhost:8080/Themaopdracht/loginpage.jsp");
	return;
}
%>