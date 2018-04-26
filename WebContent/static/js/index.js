//fetchTemplate('project_tile.tpl', function(template){
//	$.getJSON('api/recommend/projects', function(data){
//	    var rendered = Mustache.render(template, {'data':data});
//	    $('#top_project').html(rendered);
//		console.log(data);
//	});
//});
$.getJSON('api/recommend/projects', function(data) {
	var text = $('#proj_tpl').html();
	var template = Handlebars.compile(text);
	var rendered = template({'data': data});
    $('#top_project').html(rendered);
});

$.getJSON('api/recommend/categories', function(data) {
	var text = $('#tab_head_tpl').html();
	var template = Handlebars.compile(text);
	var rendered = template({'data': data});
    $('#tab_head').html(rendered);
    $($('.sidebar_link')[0]).click();
});

function loadTop5(thisR){
	$.getJSON('api/recommend/category/' + thisR.id + '/topfive', function(data) {
		var text = $('#tab_content_tpl').html();
		var template = Handlebars.compile(text);
		var rendered = template(data);
		$('#tab_content').html(rendered);
	});
	return true;
}

Handlebars.registerHelper('pp', function (text) {
	  num = Number.parseInt(text)
	  return num > 999 ? (num/1000).toFixed(1) + 'k' : num
});
