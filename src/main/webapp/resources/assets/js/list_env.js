$(document).ready(function() {
    // Init Messenger
    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'future'
    }

    $(function() {
        updateStatus();
    });

    $('.env-name').tooltip();

    $(".modal-body input.modalInputControl").prop('disabled', true);
    $(".modal-body select.modalInputControl").prop('disabled', true);

    $("#action-btn-group>ul>li>a").each(function(index, el) {
        var action = $(this);
        var url = action.attr("data-href");
        var text = action.text();
        action.click(function(event) {
            event.preventDefault();
            bootbox.confirm("Are you sure to " + text + " test env: " + action.closest("tr").attr("id") + " ?", function(result) {
                if (result == true) {
                    $.get(url, function(data) {
                            console.log(data);
                            Messenger().post({
                                message: data,
                                type: 'success',
                                showCloseButton: true
                            });
                        })
                        .fail(function(data) {
                            Messenger().post({
                                message: data,
                                type: 'fail',
                                showCloseButton: true
                            });
                        })
                        .always(function(data) {
                            console.log(data);
//                            window.location.reload(true);
                            updateStatus();
                        });;
                }
            });
        });
    });


    $(".modalFormSubmit").click(function(event) {
        $(this).html('<i class="fa fa-wrench"></i>&nbsp;&nbsp;Save changes');
        $(".modal-body input.modalInputControl").prop('disabled', false);
        $(".modal-body select.modalInputControl").prop('disabled', false);

        $.ajax({
                url: '',
                type: 'POST',
                dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
                data: {
                    param1: 'value1'
                },
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
        $(".modal-body input.modalInputControl").prop('disabled', true);
        $(".modal-body select.modalInputControl").prop('disabled', true);
        $(".modalFormSubmit").html('<i class="fa fa-cogs"></i>&nbsp;&nbsp;Update');
    });

    $('[data-load-remote]').on('click', function(e) {
        e.preventDefault();
        var $this = $(this);
        var remote = $this.data('load-remote');
        if (remote) {
            $($this.data('remote-target')).load(remote);
        }
    });

    $('.age').age();

    $('#myModal').on('hidden.bs.modal', function(e) {
        $(this).html($("#modalLoadingTemp").html());
    });



    function updateStatus() {
        $("#envstatus span").each(function(index, el) {
            var statusGrid = $(this);
            $.get('status/' + (index + 1), function(data) {
                console.log("env" + index + ' is ' + data);
                statusGrid.text(data);
            });
        });
    }
});