function loadRequest(dataCtnr)
{
	var containerid = $( dataCtnr ).data( "ctnr-id" );
	var requestid = $( dataCtnr ).data( "request-id" );
	
	$( "#req-id-" + requestid ).
		load( "/requests-details?containerid=" + containerid + "&requestid=" + requestid);
}

$(function() {
	
	//preload the latest request
	$( ".req-container-preload" ).each(function() {
		loadRequest(this);
	});
	
	//load a specific request
	$( ".req-container > a ").click(function(event) {
		
		event.preventDefault();
		var dataCtnr = $(this).parent();
		
		$(this).hide();
		$(dataCtnr).children('.req-container-spinner').show();
		
		loadRequest(dataCtnr);
	});
})