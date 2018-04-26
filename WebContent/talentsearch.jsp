<%@page import="java.sql.ResultSet"%>
<%@page import="org.dbms.ks.util.DBUtil.DBConnection"%>
<%@page import="org.dbms.ks.util.DBUtil"%>
<%@ include file="WEB-INF/templates/header.jsp" %>

<div class="container">
    <div class="row">
        
        <div class="col-md-3">
	        <div style="width: 80%; margin-top: 100px;">
	            <form action="/login" method="POST">
	                <div class="form-group">
	                    <input class="form-control" type="text" name="category" placeholder="category">
	                </div>
	                <div class="form-group">
	                    <input class="form-control" type="text" name="subcategory" placeholder="subcategory">
	                </div>
	                <div class="form-group">
	                    <input class="form-control" type="text" name="location" placeholder="location">
	                </div>
	                <div class="form-group">
	                    <button class="btn btn-primary btn-md btn-block">search</button>
	                </div> 
	            </form>
	        </div>
        </div>
        
        <div class="col-md-9">
            <h1 style="text-align: center;">Here  is the data you found</h1>
        </div>
    </div>
</div>

<%@ include file="WEB-INF/templates/footer.jsp" %>
