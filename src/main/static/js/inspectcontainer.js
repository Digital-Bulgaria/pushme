function loadRequest(containerid, requestid)
{
	$( "#req-id-" + requestid ).
		load( "/requests-details?containerid=" + containerid + "&requestid=" + requestid);
}

$(function() {
	
	$( ".req-container-preload" ).each(function() {
		var containerid = $( this ).data( "ctnr-id" );
		var requestid = $( this ).data( "request-id" );
		
		loadRequest(containerid, requestid);
	});
})