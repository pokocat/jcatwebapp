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
            console.log("gonna::::"+url);
            event.preventDefault();
            bootbox.confirm("Are you sure to " + text + " test env: " + action.closest("tr").attr("id") + " ?", function(result) {
                if (result == true) {
                    $.get(url, function() {
                            Messenger().post({
                                message: "Operation: "+ text + " successfully done!",
                                type: 'success',
                                showCloseButton: true
                            });
                            if (text == 'Destroy') {
                                window.location.reload(true);
			    }
                        })
                        .fail(function(data) {
                            Messenger().post({
                                message: "Something Wrong!!",
                                type: 'fail',
                                showCloseButton: true
                            });
                        })
                        .always(function(data) {
//                            console.log(data);
//                            window.location.reload(true);
                            updateStatus();
                        });;
                }
            });
        });
        
    });
    
    $(".stp").click(function(event) {
	event.preventDefault();
	var data = $(this).data("stpinfo");
	console.log(data);
	bootbox.dialog({
	    title: "target STP info [" +data.stpName+ "]" ,
	    message: 
		'<table class="table no-more-tables table-bordered table-hover"">'+
    		   '<tbody>'+
            		'<tr>'+
            			'<td>STP IP</td>'+
            			'<td>'+data.stpIp+'</td>'+
            		'</tr>'+
            		'<tr>'+
            			'<td>ExpertUser : Pass</td>'+
            			'<td>'+data.expertUser +' : '+ data.expertPass +'</td>'+
            		'</tr>'+
            		'<tr>'+
            			'<td>CustomerUser&nbsp;: Pass</td>'+
            			'<td>'+data.customerUser +' : '+ data.customerPass +'</td>'+
            		'</tr>'+
                    '</tbody>'+
                '</table>'+
                
                '<p>&nbsp;</p>'
		
	});
	
    });
    
    $("a.inspect_docker").click(function(event) {
	var dockerid = $(this).data("dockerid");
	var url = "/jcatwebapp/testenv/docker/" +dockerid;
	
	var orihtml = $("#dockerModal .modal-dialog .modal-content").html();
	
	$("#dockerModal .modal-dialog .modal-content").html($("#spinner").html());
	
	var response;
	$.ajax({
		url: url,
	})
	.success(function(data){
	    $("#dockerModal .modal-dialog .modal-content").html(orihtml);
	    response = data;
	    $("#docker-name").text(response.Name);
	    $("#docker-time").val(response.Created);
	    $("#docker-image").val(response.Config.Image);
	    if (response.State.Running) {
		$("#docker-state").val("Running");
	    }
	    $("#docker-hostName").val(response.Config.Hostname);
	    $("#docker-ip").val(response.NetworkSettings.Ports['22/tcp'][0].HostIp);
	    $("#docker-HostPort").val(response.NetworkSettings.Ports['22/tcp'][0].HostPort);
	    $("#docker-volume").val(response.Volumes.binds[0].hostPath);
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
            var vmId = statusGrid.closest("tr").attr("id");
            $.get('status/' + vmId, function(data) {
//                console.log("env" + index + ' is ' + data);
        	if (data == 'SUSPENDED' || data == 'STOPPED' || data == 'BUILDING') {
        	    statusGrid.addClass("label-important");
                    statusGrid.text(data);
		} else {
		    statusGrid.addClass("label-success");
	            statusGrid.text(data);
		}
            }).fail(function() {
        	console.log( "error updating envs... " + (vmId) );
        	statusGrid.addClass("label-warning");
        	statusGrid.text("UNKNOWN");
            });
        });
    }
});