$(function() {
	//sets the id of the container to be deleted
	$('.delete-ctnr').click(function() {
		var ctnrId = $(this).data('ctnr-id');
		$('#delete-ctnr-id').val(ctnrId);
	});
})