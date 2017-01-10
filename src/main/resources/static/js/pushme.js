$(function() {

	//submits csrf enabled logout form.
	$('.log-out-link').click(function() {
		$('.log-out-form').submit();
	});
})