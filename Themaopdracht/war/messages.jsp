<% 
	Object error =  request.getAttribute("error");
	if(error != null){
		%>
			<div class = "bericht">
				<h3><span>Error!</span></h3>
				<p name="error" class="error" > <%=error %>  </p>
			</div>
		<%
	}
	else{
		Object msg = request.getAttribute("msg");
		if(msg != null){
			%>
				<div class = "bericht">
					<h3 name="msg" class="msg" > <%=msg %> </h3>
				</div>
			<%
		}
	}
%>