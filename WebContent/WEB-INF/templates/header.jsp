<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>KickStarter Analysis</title>

	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="static/css/bootstrap.min.css">
	
	<!-- Glyphicon CSS -->
	<link rel="stylesheet" href="static/css/glyph.css">
	
	<!-- suboptimal loading of js here to make loading cleaner - remove after async loading is done -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script> 
	<script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.3.0/mustache.min.js" type="text/javascript"></script>
	<script src="static/js/util.js" type="text/javascript"></script>
	<script src="static/js/handlebars-v4.0.11.js" type="text/javascript"></script>
</head>
<body style='background-color:#fafafa;'>

<nav class="navbar navbar-expand-md navbar-light" style='border-bottom:1px solid #e1e1e1;' >
    <a class="navbar-brand" href="/dbms_ks/index.jsp" style='font-weight:bold; color:#6e4cbb;'>Kickstarter</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    	<span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
     	<ul class="navbar-nav mr-auto">

    	</ul>

        <div id="search_block" class="dropdown" style="width: 35%;">
            <div class="input-group">
                <input id="key_words" class="form-control" type="text" placeholder="Search" style="width: 45%;">
                <select id="search_cat" class="custom-select">
                    <option>Projects</option>
                    <option>Users</option>
                </select>
                <div class="input-group-append">
                    <button id="search_button" class="btn btn-primary">Search</button>
                </div>
            </div>

            <div class="dropdown-menu" role="menu" id="search_res" style="width: 100%;">
                <!-- will be replaced after search -->
                <h6 class="dropdown-header">Search for projects or users...</h6>
            </div>
        </div>
    </div>
</nav>

<script id='proj_res' type="text/x-handlebars-template">
    <h6 class="dropdown-header">Search Results</h6>
    <div class="dropdown-divider"></div>
    {{#data}}
        <a class="dropdown-item" href="project.jsp?p_id={{project_id}}">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-4">
                        <img src="{{photo}}" style="width: 100%; height: auto">
                    </div>
                    <div class="col-sm-8">
                        <p style="white-space: initial;">{{name}}</p>
                    </div>
                </div>
            </div>
        </a>
    {{/data}}
</script>

<script id='user_res' type="text/x-handlebars-template">
    <h6 class="dropdown-header">Search Results</h6>
    <div class="dropdown-divider"></div>
    {{#data}}
        <a class="dropdown-item" href="user.jsp?u_id={{creator_id}}">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-3">
                        <img src="{{j_profilepic.url}}" style="width: 100%; height: auto;">
                    </div>
                    <div class="col-sm-9">
                        <p style="white-space: initial;">{{name}}</p>
                    </div>
                </div>
            </div>
        </a>
    {{/data}}
</script>

<script src="static/js/header.js" type="text/javascript"> </script>
