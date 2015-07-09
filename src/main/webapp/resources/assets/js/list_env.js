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
            console.log("gonna::::"+text + " at "+url);
            event.preventDefault();
            bootbox.confirm("Are you sure to " + text + " test env: " + action.closest("tr").attr("id") + " ?", function(result) {
                if (result == true) {
                    $.ajax({
                	url: url,
                    })
                    .success(function(data){
                	Messenger().post({
                	    message: "Operation: "+ text + " successfully done!",
                	    type: 'success',
                	    showCloseButton: true
                	});
                	if (text == 'Destroy') {
                	    window.location.reload(true);
                	}
                	console.log(data);
                    })
                    .fail(function(data) {
                	console.log(data);
                	Messenger().post({
                	    message: "Something Wrong!!",
                	    type: 'fail',
                	    showCloseButton: true
                	});
                    })
                    .always(function() {
                	console.log("complete");
                	updateStatus();
                    });
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
    
 /*   $('a.envDetail').click(function(event) {
	var envId = $(this).data("envId");
	var url = "/jcatwebapp/testenv/" +envId;
	
	var orihtml = $("#VMModal .modal-dialog .modal-content").html();
	var modalBody = $("#VMModal .modal-dialog .modal-content");
	modalBody.html($("#spinner").html());
	
	var response;
	$.ajax({
		url: url,
	})
	.success(function(data){
	    console.log(data);
	    if (data.status == "failed") {
		modalBody.html('<div class="modal-header">'+
	                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>'+
	                '<br />'+
	                '<h4 id="docker-name" class="semi-bold" >VM Instance not found! Please check!</h4>'+
	                '<br /></div>');
	    } else {
		modalBody.html(orihtml);
		response = data;
		$("#vm-name").text(response.name);
		$("#vm-image").val(response.image.name);
//		$("#vm-flavor").val(response.image.name);
		$("#vm-flavor option").filter(function() {
		    //may want to use $.trim in here
		    return $(this).val() == response.flavor.name; 
		}).prop('selected', true);
		$("#vm-timecreated").val(new Date(response.created).toLocaleString());
		$("#vm-timeupdated").val(new Date(response.updated).toLocaleString());
		$("#vm-status").val(response.status);
		$("#vm-addresses").val(response.addresses.addresses['int-net'][1].addr);
//		$("#docker-volume").val(response.Volumes.binds[0].hostPath);
	    }
	    
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
    });*/
    
    $('a.inspectVM').click(function(event) {
	var vmid = $(this).data("vmid");
	var url = "/jcatwebapp/testenv/vmserver/" +vmid;
	
	var orihtml = $("#VMModal .modal-dialog .modal-content").html();
	var modalBody = $("#VMModal .modal-dialog .modal-content");
	modalBody.html($("#spinner").html());
	
	var response;
	$.ajax({
		url: url,
	})
	.success(function(data){
	    console.log(data);
	    if (data.status == "failed") {
		modalBody.html('<div class="modal-header">'+
	                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>'+
	                '<br />'+
	                '<h4 id="docker-name" class="semi-bold" >VM Instance not found! Please check!</h4>'+
	                '<br /></div>');
	    } else {
		modalBody.html(orihtml);
		response = data;
		$("#vm-name").text(response.name);
		$("#vm-desc").text(response.desc);
		$("#vm-image").val(response.image.name);
//		$("#vm-flavor").val(response.image.name);
		$("#vm-flavor option").filter(function() {
		    //may want to use $.trim in here
		    return $(this).val() == response.flavor.name; 
		}).prop('selected', true);
		$("#vm-timecreated").val(new Date(response.created).toLocaleString());
		$("#vm-timeupdated").val(new Date(response.updated).toLocaleString());
		$("#vm-status").val(response.status);
		$("#vm-addresses").val(response.addresses.addresses['int-net'][1].addr);
//		$("#docker-volume").val(response.Volumes.binds[0].hostPath);
	    }
	    
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
	    console.log(data);
	    if (data.status == "failed") {
		$("#dockerModal .modal-dialog .modal-content").html('<div class="modal-header">'+
	                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>'+
	                '<br />'+
	                '<h4 id="docker-name" class="semi-bold" >Docker Instance not found! Please check!</h4>'+
	                '<br /></div>');
	    } else {
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
//		$("#docker-volume").val(response.Volumes.binds[0].hostPath);
	    }
	    
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
        	if (data.status == 'failed') {
        	    statusGrid.addClass("label-warning");
        	    statusGrid.text('ServerERR');
        	    console.log(data);
		}
        	if (data.status == 'success') {
        	    if (data == 'SUSPENDED' || data == 'STOPPED' || data == 'BUILDING') {
        		statusGrid.addClass("label-important");
        		statusGrid.text(data);
        	    } else {
        		statusGrid.addClass("label-success");
        		statusGrid.text(data);
        	    }
        	}
            }).fail(function() {
        	console.log( "error updating envs... " + (vmId) );
        	statusGrid.addClass("label-warning");
        	statusGrid.text("UNKNOWN");
            });
        });
    }
});