function fetchTemplate(tplName, callback){
	$.get('js/templates/'+tplName, callback);
}

function cacheTemplates(){
	$('script[type="x-handlebars-template"]').each(function(_, template){
		Handlebars.compile($(template).html());
	});
}

$(document).ready(function() {
    $("body").css("display", "none");
    $("body").fadeIn(300);
});