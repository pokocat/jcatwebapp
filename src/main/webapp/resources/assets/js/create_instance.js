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
    $("#env-group").text($("select[id='group']").val());
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


    $(".stp-custom").attr("disabled", "disabled");
    $("select[name='stpName']").change(function(event) {
        if ($("select[name='stpName']").val() == 'custom') {
            $(".stp-custom").removeAttr("disabled", "");
        } else {
            $(".stp-custom").attr("disabled", "disabled");
        }
    });


    $("input").change(function(event) {
        var inputbox = $(this).attr("name");
        if (inputbox == "name") {
            if (!$("input[name='name']").val()) {
                $("input[name='name']").addClass("error");
                $("#name-span").show('fast', function() {});
            } else {
                $("input[name='name']").removeClass("error");
                $("#name-span").hide('fast', function() {});
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
                var hwset = "." + $("select[name='hwSet']").val().replace('.', '');
                $(hwset).show();
                // $(".env-hw").show('fast', function() {});
            } else {
                $("#pc-custom").hide('fast', function() {});
                $(".env-hw").hide('fast', function() {});
            };
            $("#env-rpc").text($(this).val());
        }
        if (inputbox == "envTG") {
            var str = "";
            $("input[name='envTG']:checked").each(function(index, value) {
                str += $(this).val() + ", ";
            });
            $("#env-tg").text(str.substr(0, str.length - 2));
//            if (!$("input[value='MgwSim']:checked").val()) {
//                $("#mgwsim_stp").hide();
//            } else {
//                $("#mgwsim_stp").show();
//            }
        }
        if (inputbox == "envTT") {
            var str = "";
            $("input[name='envTT']:checked").each(function(index, value) {
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
    $("select[name='hwSet']").change(function(event) {
        var hwset = "." + $("select[name='hwSet']").val().replace('.', '');
        $(".env-hw").hide();
        $(hwset).show();
    });


    $("#form-createTestEnv").submit(function(event) {
        var name = $("#name").val();
        var desc = $("#description").val();
        var userGroup = $("select[name='userGroup']").val();
        var pcSet = $("input[name='pcSet']:checked").val();
        var imageSet = $("select[name='imageSet']").val();
        var hwSet = $("select[name='hwSet']").val();
        var envTG = new Array();
        $("input[name='envTG']:checked").each(function(index, el) {
            envTG.push($(el).val());
        });
        var envTT = new Array();
        $("input[name='envTT']:checked").each(function(index, el) {
            envTT.push($(el).val());
        });
        var stpName = $("select[name=stpName]").val();
        var stpIp = $("input[name='stpIp']").val();
        var expertUser = $("input[name='expertUser']").val();
        var expertPass = $("input[name='expertPass']").val();
        var customerUser = $("input[name='customerUser']").val();
        var customerPass = $("input[name='customerPass']").val();

        var json = {
            "name": name,
            "description": desc,
            "userGroup": userGroup,
            "pcSet": pcSet,
            "imageSet": imageSet,
            "hwSet": hwSet,
            "envTG": envTG,
            "envTT": envTT,
            "stpName": stpName,
            "stpIp": stpIp,
            "expertUser": expertUser,
            "expertPass": expertPass,
            "customerUser": customerUser,
            "customerPass": customerPass
        };


        // for spring csrf verify
        // var token = $("meta[name='_csrf']").attr("content");
        var token = $("input[name='_csrf']").attr("value");

        var el = $(this);
//        if(!checkFormValid()) {
//            return false;
//        } else {
//            blockUI(el);
//        };
        
        if (!$("input[name='name']").val()) {
            $("input[name='name']").addClass("error");
            $("#name-span").show('fast', function() {});
            event.preventDefault();
            return false;
        } else {
            $("input[name='name']").removeClass("error");
            $("#name-span").hide('fast', function() {});
            $("#env-name").text($(this).val());
            blockUI(el);
        };
        
        console.log($(this).attr("action"));
        console.log(" json data: " + JSON.stringify(json));

        $.ajax({
            url: $(this).attr("action"),
            type: 'post',
            data: JSON.stringify(json),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            beforeSend: function(xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", token);
            },
            success: function(data) {
                console.log(JSON.stringify(data));
                if (data.status == "success") {
                    unblockUI(el, "Successfully created new Test Environment!<h3>Submitting...</h3>");
                    console.log(data.status);
                    $("#resultBox").removeClass('hide');
                    $("#resultBox > div").addClass("alert-success");
                    $("#resultBox > div > h4").text("Success!");
                    $("#resultBox > div > p").text("New test environment successfully created!");
                    window.location.href = "/jcatwebapp/testenv/";
                } else {
                    unblockUI(el, "Created new Test Environment failed!");
                    $("#resultBox").removeClass('hide');
                    $("#resultBox > div").addClass("alert-error");
                    $("#resultBox > div > h4").text("Error!");
                    $("#resultBox > div > p").text("New test environment creating failed! Reason: "+ data.reason);
//                    alert("errror");
                };
            },
            error: function(data) {
                alert(JSON.stringify(data));
            }
        });
        event.preventDefault();
    });

    function checkFormValid() {
        if (!$("input[name='name']").val()) {
            $("input[name='name']").addClass("error");
            $("#name-span").show('fast', function() {});
            return false;
        } else {
            $("input[name='name']").removeClass("error");
            $("#name-span").hide('fast', function() {});
            $("#env-name").text($(this).val());
            return true;
        };
    }



    //***********************************BEGIN Function calls ***************************** 
    function blockUI(el) {
        $(el).block({
            message: '<div class="loading-animator"></div>',
            css: {
                border: 'none',
                padding: '2px',
                backgroundColor: 'none'
            },
            overlayCSS: {
                backgroundColor: '#fff',
                opacity: 0.5,
                cursor: 'wait'
            }
        });
    }

    // wrapper function to  un-block element(finish loading)
    function unblockUI(el, msg) {
        $(".blockMsg > div").removeClass("loading-animator").html("<h1>" + msg + "</h1>");
        $(el).unblock();
    }
});