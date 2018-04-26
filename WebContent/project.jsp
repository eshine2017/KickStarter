<%@ include file="WEB-INF/templates/header.jsp" %>

<script id='project_content_tpl' type='text/x-handlebars-template'>
	{{#this}}
		
			<div class='card' style='width:100%; margin-bottom:20px'>
				<img class='card-img-top' src="{{photo}}" />
			</div>	
	{{/this}}
</script>

<script id='project_map_tpl' type='text/x-handlebars-template'>
	{{#this}}
		
			<div class='card' style='width:100%'>
						<div class='row m-0 p-0'>
							<iframe class='m-0 p-0' src="/dbms_ks/moneymovement.jsp?pid={{project_id}}" height=400 width="100%" style='border:none;'/>
						</div>
			</div>	
	{{/this}}
</script>


<script id='project_info_tpl' type='text/x-handlebars-template'>
	{{#data}}		
		<div class='col-md-12' style='width:100%'; margin-left:30px;>
			<h3 class='card-title mt-3'>{{name}}</h3>
			<h5> Description </h5>
			<p class='card-sub-title'>{{blurb}}</p>
			<p><b>Launch date: </b>{{launch_date}}</p>
			<p><b>Deadline: </b>{{deadline}}</p>
			<p><b>Category: </b>{{j_subcategory.j_maincategory.name}}</p>
			<p><b>Location: </b>{{j_location.name}},{{j_location.country}}</p>
			<p><b>Status: </b>{{status}}</p>
			<p class ='card-sub-title'><b>Number of backers: </b>{{backer_count}}</p>
			<p><b>Goal: </b>{{goal}}</p>
			<p><b>Money pledge info: </b> {{money_pledged}} </p>
			<p><b>Currency: </b> {{currency}} </p>	
			<div class="progress mt-2" style="height: 20px; background-color: #e0e0e0">
  				<div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="{{percentage}}" aria-valuemin="0" aria-valuemax="100" style="width:{{percentage}}%; background-color: #5ECE65; color: #1a237e">
					<span>{{percentage}}% Funded</span>
				</div>
			</div>
			
			<hr>
		</div>
	{{/data}}
</script>


<script id='owner_info_tpl' type='text/x-handlebars-template'>
{{#this}}
<div class='row' style='width:100%';>
<h4> Owner Info </h4>
</div>
<hr>
<div class='row'>
<a href="user.jsp?u_id={{creator_id}}"><img class='col-md-2 rounded-circle' style='max-width:10vw; height:8vw;' src='{{j_profilepic.url}}'/></a>
	<h5 class='row' style='padding-top:50px; padding-left:20px;'>{{name}}</h5>
	<h6 class='row small'style='padding-left:150px;'>{{j_location.name}},{{j_location.country}} </h6>
</div>
		
{{/this}}

</script>
<script type="text/x-handlebars-template" id='similarProjects_tpl'>
	{{#this}}
			<div class='col-md-3'>
			<div class='card mb-2'>
				
 				<a href="project.jsp?p_id={{project_id}}">
					<img class="card-img-top row-md-6" src="{{photo}}" style="width: 100%;  box-shadow:1px 4px 6px #eceff1;" alt="Card image cap">
					<p class='card-subtitle mt-1 ml-1 text-center' style='height:10vh;'>{{name}}</p>
 				</a>
			</div>
		</div>
	{{/this}}
</script>
<script>
	cacheTemplates();
</script>

<div class="container">
<div class="row">
<div class='col-md-8'>
	<div class="row mt-5" id='project' style='min-height:50vh;'>
		<!-- generated -->
	</div>
	<div class="row mt-5" id='project_map' style='min-height:50vh;'>
		<!-- generated -->
	</div>
	<h4> Similar Projects </h4> 
			<hr>
	<div class="row" id='similarProjects' style='min-height:20vh;'>
		<!-- generated -->
	</div>
	
</div>
<div class='col-md-4'>
		<div class="row mt-5" id='project_info' style='min-height:50vh; margin:20px;'>
		<!-- generated -->
		</div>
	
	<div class="row mt-5" id='owner_info' style='min-height:10vh; margin:20px;'>
		<!-- generated -->
	</div>
</div>
	</div>
</div>

<%@ include file="WEB-INF/templates/footer.jsp" %>
<script src="static/js/project.js" type="text/javascript"> </script>