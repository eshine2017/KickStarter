var rankBys = ['money_pledged_percentage'];
var tableIds = ['show_rank'];

$.getJSON('api/metric/rank/money_pledged_percentage', function(data) {
	var text = $('#metric').html();
	var template = Handlebars.compile(text);
	var rendered = template({'data': data});
    $('#show_rank').html(rendered);
});

$.getJSON('api/metric/rank/money_pledged_ca', function(data) {
	var text = $('#metric1').html();
	var template = Handlebars.compile(text);
	var rendered = template({'data': data});
    $('#show_rank1').html(rendered);
});

$.getJSON('api/metric/rank/backer_us_art', function(data) {
	var text = $('#metric2').html();
	var template = Handlebars.compile(text);
	var rendered = template({'data': data});
    $('#show_rank2').html(rendered);
});

$.getJSON('api/metric/rank/backer_country_art', function(data) {
//	var text = $('#metric3').html();
//	var template = Handlebars.compile(text);
//	var rendered = template({'data': data});
//    $('#show_rank3').html(rendered);
	var labelsArr = [];
	var dataArr = [];
	data.forEach(function(item) {
		labelsArr.push(item.country);
		dataArr.push(item.cc);
	});
	
	var dataSource = {
			labels: labelsArr,
			data: dataArr
	};
	console.log(labelsArr);
	console.log(dataArr);
	barChart("show_rank3", dataSource);
});

$.getJSON('api/metric/rank/creator_country_art', function(data) {
//	var text = $('#metric4').html();
//	var template = Handlebars.compile(text);
//	var rendered = template({'data': data});
//    $('#show_rank4').html(rendered);
	var labelsArr = [];
	var dataArr = [];
	data.forEach(function(item) {
		labelsArr.push(item.country);
		dataArr.push(item.cc);
	});
	
	var dataSource = {
			labels: labelsArr,
			data: dataArr
	};
	console.log(labelsArr);
	console.log(dataArr);
	barChart("show_rank4", dataSource);
});

$.getJSON('api/metric/rank/sum_money_us_category', function(data) {
//	var text = $('#metric5').html();
//	var template = Handlebars.compile(text);
//	var rendered = template({'data': data});
//    $('#show_rank5').html(rendered);
	var labelsArr = [];
	var dataArr = [];
	data.forEach(function(item) {
		labelsArr.push(item.name);
		dataArr.push(item.percentage);
	});
	
	var dataSource = {
			labels: labelsArr,
			data: dataArr
	};
	console.log(labelsArr);
	console.log(dataArr);
	pieChart("show_rank5", dataSource);
});

//input: canvasId(where to generate the graph), dataSource({labels:[],data:[]})
function pieChart(canvasId, dataSource) {

    // colors templates
    var colorSets = ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850","#BE6E46","#CDE7B0","#A3BFA8","#7286A0","#59594A"];
    var data = {
        labels: dataSource.labels,
        datasets: [{
            data: dataSource.data,
            backgroundColor: colorSets
        }]
    };

    var ctx = document.getElementById(canvasId).getContext('2d');
    var myPieChart = new Chart(ctx, {
        type: 'pie',
        data: data,
        options: {}
    });
}

//input: canvasId(where to generate the graph), dataSource({labels:[],data:[]})
function barChart(canvasId, dataSource) {
    
    // colors templates
    var colorSets = ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850","#BE6E46","#CDE7B0","#A3BFA8","#7286A0","#59594A"];
    var data = {
    	labels: dataSource.labels,
        datasets: [{
            data: dataSource.data,
            backgroundColor: colorSets
        }]
    };

    var ctx = document.getElementById(canvasId).getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: data,
        options: {}
    });
}