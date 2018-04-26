<%@page import="org.dbms.ks.models.Project"%>
<%@page import="java.util.Set"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.dbms.ks.util.DBUtil.DBConnection"%>
<%@page import="org.dbms.ks.util.QueryUtil"%>
<%@page import="org.dbms.ks.util.DBUtil"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<html>
<body>
<%=Project.fetch(1039079644).getBlurb() %>
<h2>Hello World!</h2>
<iframe src="/dbms_ks/moneymovement.jsp?pid=1277667619&pname=sadas" height=400 width=800/>

</body>
</html>
