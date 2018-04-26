<!DOCTYPE html>
<%@page import="org.dbms.ks.models.Project"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page pageEncoding="UTF-8" %>

<html>
<head>
	<link rel="stylesheet" href="static/css/bootstrap.min.css">
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content='text/html; charset=utf-8'>
    <style type="text/css">
        body {
  			background-color: white;
            color: #5d5d5d;
            font-family: Helvetica, Arial, sans-serif;
			overflow: hidden;
        }

        h5 {
            font-size: 20px;
            margin: 0px;
            width: 100vw;
            text-align: center;
            margin-top: 0px;
            color: #6f42c1;
        }

        .container {
        	/*width: 100vw;*/
        	height: 90vh;
            margin: 0px;
        	padding: 0px;
        	max-width: 800px;
        }

        .mapael .map {
            position: relative;
        }
        
        .swappable .active {
        	disabled: true;
        }
        
        svg{
            min-height: 80vh;
        	max-height: 80vh;
        	max-width: 100vw;
        	align: center;
        	display: block;
  			background-color: white;
  			margin-left: auto;
  			margin-right: auto;
        }
        
        text{
        	transform:"rotate(10 0,0)"
        }
    </style>
 	
 	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js" charset="utf-8"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.13/jquery.mousewheel.min.js" charset="utf-8"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.2.7/raphael.min.js" charset="utf-8"></script>
    <script src="static/js/jquery.mapael.min.js" charset="utf-8"></script>
    <script src="static/js/maps/world_countries.js" charset="utf-8"></script>
    <script src="static/js/maps/usa_states.js" charset="utf-8"></script>

    <script type="text/javascript">
       	mapType = 'usa_states';
       	mapData = null;
       	
       	function swapType(thisR){
       		mapType = mapType == 'world_countries' ? 'usa_states' : 'world_countries';
       		$('.swappable').attr('disabled', false);
       		$(thisR).attr('disabled', true);
       		loadMap();
       	}
       	
       	function loadMap(){
           	$(".mapcontainer").mapael({
                   map: {
                       name: mapType,
                       afterInit: function(){
                           $('.zoomButton').remove();
                           if(mapType=='usa_states'){
	                           	$('svg').css('max-width', '70vw');
                           } else {
	                           	$('svg').css('max-width', '100vw');
                           }
                       },
                       zoom: {
                       		enabled: true,
                       		maxLevel: 40
                       },
                       defaultArea: {
                           attrs: {
                               fill: "#f4f4e8",
                               stroke: "#6f42c1"
                           },
                           attrsHover:{
                        	   fill: '#f4f4e8'
                           }
                       },
                       defaultLink: {
                           factor: -0.4,
                           attrs:{
                              "stroke-width": 1,
                              stroke: "#32cb00"
                           }
                       },
                       defaultPlot: {
                           size: 4,
                           text: {
                        	   "position": 'inner',
                               attrs: {
                            	   "font-size": 2,
                            	    fill: "black",
                             	   "font-weight": "normal",
                            	    transform: "r-5"
                               },
                               attrsHover: {
                            	   "font-size": 8,
                            	   "font-weight": "bolder",
                                    fill: "#1a237e",
                               	    transform: "r10"
                               }
                           }
                       }
                   },
                  "links" : mapData['links'],
                  "plots" : mapData['plots']
              });
       	}
       	
    	$(function () {
    		$.getJSON('api/project/<%=request.getParameter("pid")%>/moneymovement', function(data){
    			mapData = data;
                loadMap();
    		});
        });
    </script>

	</head>
	<body>
		<div class="container">
<!--      		<h5>Money Movement for <%=Project.fetch(Integer.valueOf(request.getParameter("pid"))).getName()%></h5> -->
    		<div class="mapcontainer">
        		<div class="map">
            		<span>Loading Map!</span>
        		</div>
        		<div class='btn-group  text-center' style='margin-left:40vw;'> 
        			<button class='swappable btn btn-primary' onclick='swapType(this);'disabled='disabled' >USA</button>
        			<button class='swappable btn btn-primary' onclick='swapType(this);'>World</button> 
        		</div>
    		</div>
		</div>
	</body>

</html>