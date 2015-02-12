/* Set the defaults for DataTables initialisation */
/* Table initialisation */
$(document).ready(function() {

    $('#test2').on("click", function() {
        $("#quick-access").css("bottom", "0px");
    });
    
    $('.deleteGroup').click(function(event) {
    	var thistr = $(this).closest('tr');
    	var row = $(this).attr("id");
    	var url = $(this).closest("form").attr("action");
    	console.log(row);
    	bootbox.confirm("Are you sure to delete user group "+$(this).closest("tr").find("td#usergroup-name").text()+"?", function(result) {
    		if(result == true){
    			$.ajax({
					url: url,
					type: 'GET',
				})
				.done(function() {
					console.log("success");
	    			thistr.remove();
				})
				.fail(function(data) {
					console.log("error | " + JSON.stringify(data));
				})
				.always(function(data) {
					console.log("complete");
					alert(JSON.stringify(data));
					window.location.reload(true);
				});
    		}
    	}); 
    	
    });

    

    $('#quick-access .btn-cancel').click(function() {
        $("#quick-access").css("bottom", "-115px");
    });
    
    $('#quick-access .btn-add').click(function() {
    	bootbox.confirm("Are you sure to add a new User Group ?", function(result) {
    		if (result ==true) {
    			var token = $("form[id=addUserGroup]").find("input[name=_csrf]").val();
    			var name = $("input[name=groupName]").val();
    			var desc = $("input[name=groupDesc]").val()
    			var json = {name : name, desc : desc};
    			var url = $("form[id=addUserGroup]").attr("action");
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
					alert(JSON.stringify(data));
					window.location.reload(true);
				});
    			
    			fnClickAddRow();
    			$("#quick-access").css("bottom", "-115px");
			} else {

			}
    	}); 
        
        
    });


    /*
     * Initialse DataTables, with no sorting on the 'details' column
     */



    function fnClickAddRow() {
    	var lastId = $("td#usergroup-id:last").text();
        $('#groupTable').find('tbody').append($('<tr>')
            .append(
                $('<td>').text(lastId+1)
            )
            .append(
                $('<td>').text($("input[name='groupName']").val())
            )
            .append(
                $('<td>').text($("input[name='groupDesc']").val())
            )
            .append(
            	$('<td>').html('<button type="button" class="btn btn-danger btn-xs btn-mini deleteGroup" style="" id="updateBtn3">Delete</button>')
            )

        );
    }
});