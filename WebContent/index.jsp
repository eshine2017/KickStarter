<%@ include file="WEB-INF/templates/header.jsp" %>

<script id='proj_tpl' type="text/x-handlebars-template">
	{{#data}}
		<div class='col-md-3'>
			<div class='card' style="width: 16rem; height:20rem; margin: 1rem; z-index:100;">
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
	{{/data}}
</script>

<script id='tab_head_tpl' type="text/x-handlebars-template">
	{{#data}}
		<li class='nav-item'><a class='nav-link sidebar_link' id='{{category_id}}' href="#main_category_{{category_id}}" onclick='loadTop5(this);' data-toggle="tab">{{name}}</a></li>
	{{/data}}
</script>

<script id='tab_content_tpl' type='text/x-handlebars-template'>
	<div class="tab-pane active" id="main_category_{{id}}">
   			<div class="row" style="padding:1%;">
   				<div class="col-md-5">
					<div class='card pb-2'>
   						<img class='card-img-top' src="{{photo}}" style="width: 100%">
						<div class='card-body'>
	   						<h5 class='card-sub-title'>{{name}}</h5>
							<p>{{blurb}}</p>
							<a class="btn btn-outline-primary" href='project.jsp?p_id={{project_id}}'>Learn more</a>
						</div>
					</div>
   				</div>

   				<div class="col-md-7">
					{{#topfive}}
						<div class='card mb-3 mr-0'>
							<a href="project.jsp?p_id={{project_id}}">
							<div class="row mr-1 pr-2">
   								<div class="col-md-5">
   									<img class='card-img-left' src="{{photo}}" style="width: 100%; height: 100%;">
 	  							</div>
   								<div class="col-md-7 p-2">
									<div class='card-sub-title'>{{name}}</div>
									<div class="progress mt-2" style="height: 20px; background-color: #e0e0e0">
  										<div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="{{percentage}}" aria-valuemin="0" aria-valuemax="100" style="width:{{percentage}}%; background-color: #5ECE65; color: #1a237e">
											<span>{{percentage}}% Funded</span>
										</div>
									</div>
   								</div>
							</div>
							</a>
						</div>
					{{/topfive}}
   				</div>
   			</div>
	   </div>
</script>

<script>
	cacheTemplates();
</script>

<!-- Top projects, launched most recently but have a good probability to success -->
<div class="container">
	<h4 class='glyph glyph-fire'>Top Projects</h4>
	<hr/>
	<div class="row" id='top_project' style='min-height:100vh;'>
		<!-- generated -->
	</div>
</div>

<hr>

<!-- View projects by main category, display top projects within this category -->
<div class="container" style='min-height:100vh;'>
	<h4 class='glyph glyph-filter'>Popular Categories</h4>
	<hr/>
	<div class='row'>
		<ul class="col-md-3 nav nav-pills nav-stacked" id='tab_head' style='height:30vh;'>
			<!-- generated -->
		</ul>
		
		<div class="col-md-9 tab-content" id='tab_content'>	
		</div>
	</div>
</div>

<%@ include file="WEB-INF/templates/footer.jsp" %>
<script src="static/js/index.js" type="text/javascript"> </script>