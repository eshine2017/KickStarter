<%@ include file="WEB-INF/templates/header.jsp" %>

<div class="container">

<div class="row">

<div id="show_rank" class="col-md-4" style="width: 20%; font-size: 13px">
</div>

<div class="col-md-8">
     <h3 align="center"># of Creators owned Art projects in different countries</h3>
     <canvas id="show_rank4" width="100" height="80"></canvas>
</div>

<!-- <div id="show_rank4" class="col-md-4" style="width: 20%">
</div> -->

<div id="show_rank1" class="col-md-4" style="width: 20%; font-size: 13px">
</div>

<div class="col-md-8">
    <h3 align="center"># of Backers back Art projects in different countries</h3>
    <canvas id="show_rank3" width="100" height="80"></canvas>
</div>
<!-- <div id="show_rank3" class="col-md-4" style="width: 20%">
</div> -->

<div id="show_rank2" class="col-md-4" style="width: 20%; font-size: 13px">
</div>



<div class="col-md-8">
     <h3 align="center">Money pledged percentage in US of different Category</h3>
     <canvas id="show_rank5" width="100" height="80"></canvas>
</div>

<!-- <div id="show_rank5" class="col-md-4" style="width: 20%">
</div> -->

</div>

</div>



<script id='metric' type="text/x-handlebars-template">
<table class="table table-bordered">
  <thead class="bg-primary table-dark">
    <tr>
       <th colspan="3" scope="col">Top 10 most money pledged</th>
    </tr>
  </thead>
  <thead class="bg-primary table-dark">
    <tr>
      <th scope="col">Rank</th>
      <th scope="col">Name</th>
      <th scope="col">percentage%</th>
    </tr>
  </thead>
  <tbody>
  {{#data}}
    <tr>
      <th scope="row">{{rank}}</th>
      <td>{{name}}</td>
      <td>{{percentage}}</td>
    </tr>
  {{/data}}
  </tbody>
</table>

</script>

<script id='metric1' type="text/x-handlebars-template">
<table class="table table-bordered">
  <thead class="bg-primary table-dark">
    <tr>
       <th colspan="3" scope="col">Top 10 most money pledged in Canada</th>
    </tr>
  </thead>
  <thead class="bg-primary table-dark">
    <tr>
      <th scope="col">Rank</th>
      <th scope="col">Name</th>
      <th scope="col">percentage%</th>
    </tr>
  </thead>
  <tbody>
  {{#data}}
    <tr>
      <th scope="row">{{rank}}</th>
      <td>{{name}}</td>
      <td>{{percentage}}</td>
    </tr>
  {{/data}}
  </tbody>
</table>

</script>

<script id='metric2' type="text/x-handlebars-template">
<table class="table table-bordered">
  <thead class="bg-primary table-dark">
    <tr>
       <th colspan="3" scope="col">Top 10 Art projects have most backers in US</th>
    </tr>
  </thead>
  <thead class="bg-primary table-dark">
    <tr>
      <th scope="col">Rank</th>
      <th scope="col">Name</th>
      <th scope="col">#Backer</th>
    </tr>
  </thead>
  <tbody>
  {{#data}}
    <tr>
      <th scope="row">{{rank}}</th>
      <td>{{name}}</td>
      <td>{{backer_count}}</td>
    </tr>
  {{/data}}
  </tbody>
</table>

</script>

<script id='metric3' type="text/x-handlebars-template">
<table class="table table-bordered">
  <thead class="bg-primary table-dark">
    <tr>
       <th colspan="3" scope="col">backers country art</th>
    </tr>
  </thead>
  <thead class="bg-primary table-dark">
    <tr>
      <th scope="col">Country</th>
      <th scope="col"># of backers</th>
    </tr>
  </thead>
  <tbody>
  {{#data}}
    <tr>
      <td>{{country}}</td>
      <td>{{cc}}</td>
    </tr>
  {{/data}}
  </tbody>
</table>

</script>

<script id='metric4' type="text/x-handlebars-template">
<table class="table table-bordered">
  <thead class="bg-primary table-dark">
    <tr>
       <th colspan="3" scope="col">creator country art</th>
    </tr>
  </thead>
  <thead class="bg-primary table-dark">
    <tr>
      <th scope="col">Country</th>
      <th scope="col"># of creators</th>
    </tr>
  </thead>
  <tbody>
  {{#data}}
    <tr>
      <td>{{country}}</td>
      <td>{{cc}}</td>
    </tr>
  {{/data}}
  </tbody>
</table>

</script>

<script id='metric5' type="text/x-handlebars-template">
<table class="table table-bordered">
  <thead class="bg-primary table-dark">
    <tr>
       <th colspan="3" scope="col">money percentage in us different category</th>
    </tr>
  </thead>
  <thead class="bg-primary table-dark">
    <tr>
      <th scope="col">Category</th>
      <th scope="col">percentage of money</th>
    </tr>
  </thead>
  <tbody>
  {{#data}}
    <tr>
      <td>{{name}}</td>
      <td>{{percentage}}</td>
    </tr>
  {{/data}}
  </tbody>
</table>

</script>


<%@ include file="WEB-INF/templates/footer.jsp" %>
<script src="static/js/metric.js" type="text/javascript"></script>