$(document).ready(function() {
    fnUpdateUserTable();


    $("button.updateuser").click(function(event) {
        var id = $(this).attr("id");
        var token = $(this).next().val();
        var url = $(this).closest("form").attr("action");
        var userGroup = $(this).closest("tr").find("select[name='userGroup']").val();
        var groupRole = $(this).closest("tr").find("select[name='groupRole']").select2("val");
        var json = {
            "userGroup": userGroup,
            "groupRole": groupRole
        };

        console.log(JSON.stringify(json));

        bootbox.confirm("Are you sure to update User " + $(this).closest("tr").find("td#user-name").text() + " ?", function(result) {
            console.log("Confirm result: " + result);
            if (result == true) {
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
            } else {

            }
        });


    });

    $("select").change(function(event) {
        var btnId = $(this).closest('tr').attr("id");
        console.log(btnId);
        $("button[id=" + btnId + "]").attr("disabled", false);
        $(this).closest('tr').addClass('row_selected');
    });

    function fnUpdateUserTable() {
        // Multiselect - Select2 plug-in
        $("select.multi").each(function(index, el) {
            $(this).prev("div").remove();
            $(this).val($(this).attr("data-value").replace("[", "").replace("]", "").replace(/ /g, "").split(",")).select2();
        });




        $("select.roleSetting").each(function(index, el) {
            $(this).prev("div").remove();
            $(this).val($(this).attr("data-value")).select2({
                minimumResultsForSearch: 99
            })
        });
    }
});