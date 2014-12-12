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
$(document).ready(function(){
	  //Dropdown menu - select2 plug-in
	  $("#source").select2();
	  
	  //Multiselect - Select2 plug-in
	  $("#multi").val(["Jim","Lucy"]).select2();
	  
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
    });
	
	$("input[name='remotePC']").change(function(event) {
		var rpc = $("input[name='remotePC']:checked").val();

		if (rpc == 'has-remotePC') {
			console.log(rpc);
			$("#rpc-custom").show('fast', function() {
				
			});
			$(".env-hw").show('fast', function() {
				
			});

		} else {
			$("#rpc-custom").hide('fast', function() {
				
			});
			$(".env-hw").hide('fast', function() {
				
			});
		};
	});
 	
 	$("input").change(function(event) {
 		var inputbox = $(this).attr("name");
 		if (inputbox == "name") {
 			$("#env-name").text($(this).val());
 		}
 		if (inputbox == "description") {
 			$("#env-description").text($(this).val());
 		}
 		if (inputbox == "remotePC") {
 			$("#env-rpc").text($(this).val());
 		}
 		if (inputbox == "envTrafficGenerator") {
 			
 			var str = "";
 			$("input[name='envTrafficGenerator']:checked").each(function(index, value) {
 				// console.log($(this).legnth());
 				// console.log($(this).val());
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

 		if (inputbox == "") {
 			// $("#env-tg").text($(this).val());
 		}


 		// var name = $(this).val();
 		// $("#env-name").text(name);
 	});

	// select change
	$("select[id='group']").change(function(event) {
		$("#env-group").text($(this).val());
	});

	$("select[id='hw-set']").change(function(event) {
		$("#env-hw").text($(this).val());
	});
	

	cosole.console.log("${page}");
	
});