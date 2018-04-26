var urlParams = new URLSearchParams(window.location.search);
locId = urlParams.get('l_id');

Handlebars.registerHelper('pp', function (text) {
	  num = Number.parseInt(text)
	  return num > 999 ? (num/1000).toFixed(1) + 'k' : num
});

Handlebars.registerHelper('percentage', function () {
	return Number.parseInt((Number.parseInt(this.money_pledged)/ Number.parseFloat(this.goal)));
});

Handlebars.registerHelper('lower', function(str) {
	return str.toLowerCase();
});


$.getJSON('api/location/' + locId , function(data) {
	var text = $('#location_content_tpl').html();
	var template = Handlebars.compile(text);
	var rendered = template(data);
    $('#location').html(rendered);
    
    $.getJSON('api/location/' + locId + '/backers', function(data) {
    	var text = $('#nearby-backers-tpl').html();
    	var template = Handlebars.compile(text);
    	var rendered = template(data);
        $('#nearby-backers').html(rendered);
    });
    
    $.getJSON('api/location/' + locId + '/creators', function(data) {
    	var text = $('#nearby-creators-tpl').html();
    	var template = Handlebars.compile(text);
    	var rendered = template(data);
        $('#nearby-creators').html(rendered);
    });
    
    $.getJSON('api/location/' + locId + '/projects', function(data) {
    	var text = $('#nearby-projects-tpl').html();
    	var template = Handlebars.compile(text);
    	var rendered = template(data);
        $('#nearby-projects').html(rendered);
    });      
    
    $('#money3d').attr('src', '/dbms_ks/moneymovement3d.jsp?l_id=' + data['location_id']);
});