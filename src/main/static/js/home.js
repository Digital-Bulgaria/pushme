$(function() {
	
	//toggles learn more/less
	$( "#btn_learn_more" ).click(function() {
		$( "#learn_more_content" ).slideToggle( "fast", function() {			
			$("#btn_learn_more_visible, #btn_learn_less_visible").toggle();
		});
	});
	
	//create a container code
	$( "#btn_create_container" ).click(function() {
		$(this).prop('disabled', true);
		$("#btn_create_container_new, #btn_create_container_working").toggle();
		
		$.ajax({
	        type: 'POST',
	        contentType: 'application/json',
	        url: '/api/containers/container',
	        dataType: "json",
	        success: function(data, textStatus, jqXHR){
	        	//toggle the working to done
	        	$("#btn_create_container_working, #btn_create_container_done").toggle();
	        	//create necessary URL-s, based on the received data
	        	var urlSelectors = $("#url_container, #url_container_setup, #url_container_inspect");
	        	$(urlSelectors).each(function(){this.href = this.href.replace('container_id', data.id);});
	        	//copy the URL-s to the inputs
	        	$(urlSelectors).
	        		each(function()
	        				{
	        					$('#' + this.id + '_input').val(this.href);
	        					$('#' + this.id + '_link').attr('href', this.href);
	        				}
	        		);
	        	
	        	$( "#success_message" ).slideToggle( "slow");
	        	
	        },
	        error: function(jqXHR, textStatus, errorThrown){
	            alert('error: ' + textStatus);
	        }
	    });
		
	});
})