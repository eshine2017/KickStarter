$('#search_button').on('click', function() {
	var key_words = $('#key_words').val();
	var search_cat = $('#search_cat').find("option:selected").val();
	if (search_cat == "Projects") {
		$.getJSON('api/search/projects/' + key_words, function(data) {
			var text = $('#proj_res').html();
			var template = Handlebars.compile(text);
			var rendered = template({'data': data});
		    $('#search_res').html(rendered);
		});
		
	} else {
		$.getJSON('api/search/users/' + key_words + '?autojoin=true', function(data) {
			var text = $('#user_res').html();
			var template = Handlebars.compile(text);
			var rendered = template({'data': data});
		    $('#search_res').html(rendered);
		});
	}
});

$(document).click(function() {
	$('#search_res').css("display","none");
});

$('#search_block').click(function(e) {
	e.stopPropagation();
	$('#search_res').css("display","block");
});
