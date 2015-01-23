/* Set the defaults for DataTables initialisation */
/* Table initialisation */
$(document).ready(function() {


    $('#quick-access .btn-cancel').click(function() {
        $("#quick-access").css("bottom", "-115px");
    });
    $('#quick-access .btn-add').click(function() {
        fnClickAddRow();
        $("#quick-access").css("bottom", "-115px");
    });


    /*
     * Initialse DataTables, with no sorting on the 'details' column
     */


    $('#test2').on("click", function() {
        $("#quick-access").css("bottom", "0px");
    });
    
    $('.deleteGroup').click(function(event) {
    	var row = $(this).attr("id");
    	console.log(row);
    	$(this).closest('tr').remove();
    });


    function fnClickAddRow() {
        $('#groupTable').find('tbody').append($('<tr>')
            .append(
                $('<td>').text("4")
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