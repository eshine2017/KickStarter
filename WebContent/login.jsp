<%@page import="org.dbms.ks.util.DBUtil.DBConnection"%>
<%@page import="org.dbms.ks.util.DBUtil"%>
<%@ page import="org.dbms.ks.util.SecurityUtil"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Connection"%>

<%
	if (SecurityUtil.isAuthenticated(request)) {
		// already logged in redirecting to home page
		response.sendRedirect("index.jsp");
		return;
	}
%>

<%@ include file="WEB-INF/templates/header.jsp" %>
<h2 style="text-align: center;">Login to KickStarter</h2>
<div style="width: 40%; margin: 25px auto;">
	<form action="api/login" method="POST">
		<div class="form-group">
			<input class="form-control" type="text" name="username"
				placeholder="username">
		</div>
		<div class="form-group">
			<input class="form-control" type="password" name="password"
				placeholder="password">
		</div>
		<div class="form-group">
			<button class="btn btn-primary btn-lg btn-block">submit</button>
		</div>
	</form>
</div>

<%@ include file="WEB-INF/templates/footer.jsp" %>
