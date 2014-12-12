$(document).ready(function() {	
	$('.env-name').tooltip();

	$(".modal-body input").prop('disabled', true);
	$(".modal-body select").prop('disabled', true);


	$(".modalFormSubmit").click(function(event) {
		$(this).html('<i class="fa fa-wrench"></i>&nbsp;&nbsp;Save changes');
		$(".modal-body input").prop('disabled', false);
		$(".modal-body select").prop('disabled', false);



		$.ajax({
			url: '/path/to/file',
			type: 'default GET (Other values: POST)',
			dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
			data: {param1: 'value1'},
		})
		.done(function() {
			console.log("success");
		})
		.fail(function() {
			console.log("error");
		})
		.always(function() {
			console.log("complete");
		});

		event.preventDefault();
	});

	$(".modalFormCancel").click(function(event) {
		$(".modal-body input").prop('disabled', true);
		$(".modal-body select").prop('disabled', true);
		$(".modalFormSubmit").html('<i class="fa fa-cogs"></i>&nbsp;&nbsp;Update');
	});
	$("input[name='useRPC']").change(function(event) {
		if ($("input[name='useRPC']:checked").val() == 'true') {
			$("#rpcHwSet").fadeIn('slow', function() {
			});

		} else {
			$("#rpcHwSet").fadeOut('slow', function() {
				
			});
		};
	});

	$('[data-load-remote]').on('click',function(e) {
		e.preventDefault();
		var $this = $(this);
		var remote = $this.data('load-remote');
		if(remote) {
			$($this.data('remote-target')).load(remote);
		}
	});	

});

