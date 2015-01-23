
$(document).ready(function() {
	// Multiselect - Select2 plug-in
	$("select.multi").each(function(index, el) {
		$(this).val($(this).attr("data-value").replace("[","").replace("]","").replace(" ","").split(",")).select2();
	});

	$("select").change(function(event) {
		var btnId = $(this).closest('tr').attr("id");
		console.log(btnId);
		$("button[id="+btnId+"]").attr("disabled", false);
		$(this).closest('tr').addClass('row_selected');
	});

	
	$("select.roleSetting").each(function(index, el) {
		$(this).val("user").select2({
		    minimumResultsForSearch: 99
		})
	});
	
	
	$("button.updateuser").click(function(event) {
		var id  = $(this).attr("id");
		var token = $(this).next().val();
		var url = $(this).closest("form").attr("action");
		var userGroup = $(this).closest("tr").find("select[name='userGroup']").val();
		var groupRole = $(this).closest("tr").find("select[name='groupRole']").select2("val");
        var json = {"userGroup" : userGroup, "groupRole" : groupRole};

		console.log(JSON.stringify(json));
		$.ajax({
			url: url,
			type: 'POST',
			dataType: 'json',
			data: JSON.stringify(json),
			beforeSend: function(xhr) {
    			xhr.setRequestHeader("Accept", "application/json");
    			xhr.setRequestHeader("Content-Type", "application/json");
    			xhr.setRequestHeader("X-CSRF-TOKEN", token);
    		},
		})
		.done(function() {
			console.log("success");
		})
		.fail(function(data) {
			console.log("error | " + JSON.stringify(data));
		})
		.always(function(data) {
			console.log("complete");
			alert(data);
		});
	});
});
