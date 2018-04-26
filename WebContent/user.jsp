<%@ include file="WEB-INF/templates/header.jsp" %>
<script id='userCreator_content_tpl' type='text/x-handlebars-template'>
	{{#this}}
			<div class='col-md-6'>
				<div class='row'>
					<img class='col-md-6' style='max-width:100%; height:16vw;' src='{{j_profilepic.url}}'/>
					<div class='col-md-4'>
						<h5 class='row' style="padding-top:60px">{{name}}</h5>
						<h6 class='row small'>{{j_location.name}},{{j_location.country}} </h6>
					</div>
				</div>	
			</div>
			
	{{/this}}
</script>
<script id='userCreatorProject_tpl' type="text/x-handlebars-template">
	{{#this}}
		<div class='col-md-4'>
			<div class='card' style="width: 100%; height:25rem; margin: 1rem; z-index:100;">
 				<a href="project.jsp?p_id={{project_id}}">
					<img class="card-img-top row-md-6" src="{{photo}}" style="width: 100%;  box-shadow:1px 4px 6px #eceff1;" alt="Card image cap">
					<p class='card-subtitle mt-1 ml-1 text-center' style='height:5vh;'>{{name}}</p>
					<div class="card-body row-md-4">
						<div class="progress" style="height: 20px; background-color: #e0e0e0">
  							<div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="{{percentage}}" aria-valuemin="0" aria-valuemax="100" style="width:{{percentage}}%; background-color: #5ECE65; color: #1a237e">
    							<span>{{percentage}}% Funded</span>
  							</div>
						</div>
						<div style='width:100%;'>
							<div class='row text-center'>
								<span class='glyph glyph-user col-md-4'></span>
								<span class='glyph glyph-target col-md-4'></span>
								<span class='glyph glyph-usd col-md-4'></span>
							</div>
							<div class='row text-center'>
								<span class='glyph col-md-4 mt-0'>{{pp backer_count}}</span>
								<span class='glyph col-md-4 mt-0'>&#36;{{pp goal}}</span>
								<span class='glyph col-md-4 mt-0'>&#36;{{pp money_pledged}}</span>						
							</div>
						</div>
					</div>
 				</a>
			</div>
		</div>
	{{/this}}
</script>
<script id='similarUsers_tpl' type="text/x-handlebars-template">
		{{#data}}
		<div class='card mb-3 mr-0' style="width: 300px; margin:40px">
		<a href="user.jsp?u_id={{creator_id}}">
		<div class="row mr-1 pr-2">
		<div class="col-md-5">
		<img class='card-img-left' src="{{j_profilepic.url}}" style="height: 80px;">
		</div>
		<div class="col-md-7 p-2">
			<div class='card-sub-title'>{{name}}</div>
		</div>
		</div>
		</a>
		</div>
	
	{{/data}}
</script>

<div class="container">
<div class="row">
	<div class='col-md-9'>
		<div class="row mt-5" id='userCreator' style='min-height:10vh;'>
			<!-- generated -->
		</div>
	<hr>
	<H3> Projects of the same user </h3>
		<div class="row mt-5" id='userCreatorProjects' style='min-height:100vh;'>
			<!-- generated -->
		</div>
	</div>
	<div class='col-md-3'>
	<H3 style='padding:35px; margin-left:30px'> Similar users </h3>
			<div class="row mt-5" id='similarUsers' style='height:30vh;'>
			<!-- generated -->
		</div>
	</div>
</div>
</div>



<%@ include file="WEB-INF/templates/footer.jsp" %>
<script src="static/js/user.js" type="text/javascript"> </script>
