<% 
Object error =  request.getAttribute("error");
if(error != null){
	%>
		<div id = "bericht">
			<h3 name="error" class="error">Error! <br />
			<%=error %>  </h3>
		</div>
	<%
}
else{
	Object msg = request.getAttribute("msg");
	if(msg != null){
		%>
			<div id = "bericht">
				<h3 name="msg" class="msg"><span><%=msg%></span></h3>
			</div>
		<%
	}
}
%>