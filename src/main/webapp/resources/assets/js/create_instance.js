//Cool ios7 switch - Beta version
//Done using pure Javascript
/*var Switch = require('ios7-switch')
        , checkbox = document.querySelector('.ios')
        , mySwitch = new Switch(checkbox);
 mySwitch.toggle();
      mySwitch.el.addEventListener('click', function(e){
        e.preventDefault();
        mySwitch.toggle();
      }, false);
*/
$(document).ready(function() {
	/*
    //Dropdown menu - select2 plug-in
    $("#source").select2();

    //Multiselect - Select2 plug-in
    $("#multi").val(["Jim", "Lucy"]).select2();

    //Date Pickers
    $('.input-append.date').datepicker({
        autoclose: true,
        todayHighlight: true
    });

    $('#dp5').datepicker();

    $('#sandbox-advance').datepicker({
        format: "dd/mm/yyyy",
        startView: 1,
        daysOfWeekDisabled: "3,4",
        autoclose: true,
        todayHighlight: true
    });*/


	$("input").change(function(event) {
		var inputbox = $(this).attr("name");
		if (inputbox == "name") {
    		if (!$("input[name='name']").val()) {
    			$("input[name='name']").addClass("error");
    			$("#name-span").show('fast', function(){});
    		} else{
    			$("input[name='name']").removeClass("error");
    			$("#name-span").hide('fast', function(){});
    			$("#env-name").text($(this).val());
    		};
    	};
		if (inputbox == "description") {
            $("#env-description").text($(this).val());
        }
        if (inputbox == "pcSet") {
        	var pc = $("input[name='pcSet']:checked").val();
        	if (pc == 'true') {
        		$("#pc-custom").show('fast', function() {});
        		$(".env-hw").show('fast', function() {});
        	} else {
        		$("#pc-custom").hide('fast', function() {});
        		$(".env-hw").hide('fast', function() {});
        	};
            $("#env-pc").text($(this).val());
        }
        if (inputbox == "envTrafficGenerator") {

            var str = "";
            $("input[name='envTrafficGenerator']:checked").each(function(index, value) {
                str += $(this).val() + ", ";
            });
            $("#env-tg").text(str.substr(0, str.length - 2));
        }
        if (inputbox == "envTestingTool") {

            var str = "";
            $("input[name='envTestingTool']:checked").each(function(index, value) {
                // console.log($(this).legnth());
                // console.log($(this).val());
                str += $(this).val() + ", ";
            });
            $("#env-tt").text(str.substr(0, str.length - 2));
        }
        if (inputbox == "envSingleProcess") {

            var str = "";
            $("input[name='envSingleProcess']:checked").each(function(index, value) {
                // console.log($(this).legnth());
                // console.log($(this).val());
                str += $(this).val() + ", ";
            });
            $("#env-sp").text(str.substr(0, str.length - 2));
        }

        if (inputbox == "customerUserSetting") {
        	if ($("input[name='customerUserSetting']:checked").val() === "on") {
        		$("input[name='customerUser']").prop('disabled', true);
        		$("input[name='customerPass']").prop('disabled', true);
        		$("input[name='customerUser']").val($("input[name='expertUser']").val());
        		$("input[name='customerPass']").val($("input[name='expertPass']").val());
        	} else {
        		$("input[name='customerUser']").prop('disabled', false);
        		$("input[name='customerPass']").prop('disabled', false);
        	};
            
        }

	});


    // select change
    $("select[id='group']").change(function(event) {
        $("#env-group").text($(this).val());
    });

    $("select[id='hw-set']").change(function(event) {
        $("#env-hw").text($(this).val());
    });


    $("#form-createTestEnv").submit(function(event) {
        var name = $("#name").val();
        var desc = $("#description").val();
        var json = {"name" : name, "description" : desc};

        // for spring csrf verify
        // var token = $("meta[name='_csrf']").attr("content");
        var token = $("input[name='_csrf']").attr("value");
  		
        alert(JSON.stringify(json));
        $.ajax({
        		url: $(this).attr("action"),
        		type: 'post',
        		data: JSON.stringify(json),
        		beforeSend: function(xhr) {
        			xhr.setRequestHeader("Accept", "application/json");
        			xhr.setRequestHeader("Content-Type", "application/json");
        			xhr.setRequestHeader("X-CSRF-TOKEN", token);
        		},
        		complete: function (data) {
        			alert(JSON.stringify(data));
        		},
        		success: function (data) {
        			alert(JSON.stringify(data));
        		},
        		error: function (data) {
        			alert(JSON.stringify(data));
        		},
        		always: function (data) {
        			alert(JSON.stringify(data));
        		}
        	});
        event.preventDefault();
    });

});
