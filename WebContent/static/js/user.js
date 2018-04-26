var urlParams = new URLSearchParams(window.location.search);
creatorId = urlParams.get('u_id');


$.getJSON('api/user/creator/' + creatorId +'/projects?autojoin=true'  , function(data) {
	var text = $('#userCreatorProject_tpl').html();
	var template = Handlebars.compile(text);
	var rendered = template(data);
    $('#userCreatorProjects').html(rendered);
});

$.getJSON('api/user/creator/' + creatorId +'?autojoin=true'  , function(data) {
	var text = $('#userCreator_content_tpl').html();
	var template = Handlebars.compile(text);
	var rendered = template(data);
    $('#userCreator').html(rendered);
});


Handlebars.registerHelper('pp', function (text) {
	  num = Number.parseInt(text)
	  return num > 999 ? (num/1000).toFixed(1) + 'k' : num
});

Handlebars.registerHelper('percentage', function () {
	return Number.parseInt((Number.parseInt(this.money_pledged)/ Number.parseFloat(this.goal)*100));
	});
	
$.getJSON('api/user/creator/' + creatorId +'/similar'+'?autojoin=true', function(data) {
		var text = $('#similarUsers_tpl').html();
		var template = Handlebars.compile(text);
		var rendered = template({'data': data});
		$('#similarUsers').html(rendered);
	}); 