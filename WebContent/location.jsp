<%@ include file="WEB-INF/templates/header.jsp" %>
<link rel="stylesheet" href="static/css/flag-icon.min.css">

<script id='location_content_tpl' type='text/x-handlebars-template'>
	{{#this}}
		<div class='col-md-9'>
			<div class='card'>
				<div class='card-body'>
					<div class='row' style='min-height:10vh'>
						<span class='card-img-top col-md-3 flag-icon flag-icon-{{lower country}}'></span>
						<div class='col-md-9'>
							<h4 class='card-title lead'>{{short_name}}</h4>
							<h6 class='card-sub-title small'>{{type}}</h6>
						</div>
					</div>
					<div class='container p-0 m-0 mt-5'>
					
						<div class='row'>
							<iframe id='money3d' src='' style="width:100%; min-height:60vh; border:none; overflow: hidden; margin-left: 5vw;" />
						</div>
						
						<div class='row' id='nearby-creators'>
							<!-- creators -->
						</div>
	
						<div class='row' id='nearby-backers'>
							<!-- backers -->
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class='col-md-3'>
			<h5>Nearby Projects</h5>
			<div class='row' id='nearby-projects'>
				<!-- generated -->
			</div>
		</div>
	{{/this}}
</script>

<script type="text/x-handlebars-template" id='nearby-projects-tpl'>
	{{#this}}
		<div>
			<div class='card m-2'>
				<a href="project.jsp?p_id={{project_id}}">
					<img class='card-img-top' src="{{photo}}" style="width: 100%;">
					<div class="card-body">
  						<div class='card-title'>
							{{name}}
   						</div>
					</div>
				</a>
			</div>
		</div>
	{{/this}}
</script>

<script type="text/x-handlebars-template" id='nearby-creators-tpl'>
	{{#if this}}
		<h4 class='col-md-12'>Nearby Creators</h4>
	{{/if}}

	{{#this}}
		<div>
			<div class='card m-2 ml-4 mb-4 p-2' style='max-height: 15rem; max-width: 15rem; height:15rem; width:15rem'>
				<a href="project.jsp?p_id={{project_id}}">
					<div class="card-body">
						<img class='card-img-top m-0 p-0' src="{{url}}" style="width: 100%;">
  						<div class='card-sub-title text-center'>
							{{name}}
   						</div>
					</div>
				</a>
			</div>
		</div>
	{{/this}}
</script>

<script type="text/x-handlebars-template" id='nearby-backers-tpl'>
	{{#if this}}
		<h4 class='col-md-12'>Nearby Backers</h4>
	{{/if}}
	{{#this}}
		<div>
			<div class='card m-2 ml-4 mb-4 p-2' style='max-height: 15rem; max-width: 15rem; height:15rem; width:15rem'>
				<a href="project.jsp?p_id={{project_id}}">
					<div class="card-body">
						<img class='card-img-top m-0 p-0' src="{{url}}" style="width: 100%;">
  						<div class='card-sub-title text-center'>
							{{name}}
   						</div>
					</div>
				</a>
			</div>
		</div>
	{{/this}}
</script>


<script>
	cacheTemplates();
</script>

<div class="container">
	<div class="row mt-5" id='location' style='min-height:100vh;'>
		<!-- generated -->
	</div>
</div>

<%@ include file="WEB-INF/templates/footer.jsp" %>
<script src="static/js/location.js" type="text/javascript"> </script>