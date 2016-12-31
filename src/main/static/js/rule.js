$(function() {
	$("#btn_create_rule").click(function() {
		
		var ctnrid = $(this).data("ctnr-id");
		$("#new_rule").load("/rules-new?containerid=" + ctnrid, function() {
			  attachFormListener();
		});
	});
})

function attachFormListener()
{
	$('form').submit(function(event) {
		// Prevent the form from submitting via the browser
		event.preventDefault();
		var form = $(this);
		$.ajax({
			type : form.attr('method'),
			url : form.attr('action'),
			data : form.serialize()
		}).done(function(data) {
			alert('suxes');
			alert(data);
		}).fail(function(data) {
			alert('sux');
			alert(data);
		});
	});
}