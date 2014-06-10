<%
if(session == null || session.getAttribute("gebruiker") == null){
	response.sendRedirect(response.encodeRedirectURL("http://localhost:8080/Themaopdracht/loginpage.jsp"));
}
%>