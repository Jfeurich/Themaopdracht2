<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="https://jquery-ui.googlecode.com/svn-history/r3982/trunk/ui/i18n/jquery.ui.datepicker-nl.js"></script>
<script>
	$(function() {
		$.datepicker.setDefaults($.datepicker.regional["nl"]);
		$( ".datepicker" ).datepicker( {
			dateFormat: "dd-mm-yy", 
			minDate: "01-01-2000",
			maxDate: "+5y", 
			changeMonth: true,
			changeYear: true,
			constrainInput: true, 
			monthNamesShort: $.datepicker.regional['nl'].monthNames
		} );
	});
</script>
<style>
	.ui-datepicker{
		font-size: .7em;
		text-align: center;
	}
	.ui-datepicker select.ui-datepicker-month, .ui-datepicker select.ui-datepicker-year{
		background: #FFFFFF;
		color: #000033;
		font-size: .8em;
		text-align: center;
		font-weight: bold;
	}
	.ui-datepicker select.ui-datepicker-month{
		width: 60%;
	}
	.ui-datepicker select.ui-datepicker-year{
		width: 40%;
	}
	.ui-datepicker table{  
        width: 100%;  
    }  
	.ui-datepicker th{
		background: #8080B2;
		color: #000000;
		font-weight: bold;
	}  
	.ui-datepicker td span, .ui-datepicker td a{  
	    display: inline-block; 
	    width: 75%;
	    height: 100%; 
	    text-align: center;  
	    background: #9999C2;   
	    color: #FFFFFF;
		font-weight: bold;
	} 
	.ui-datepicker-current-day .ui-state-active{
		background: #000033;
		color: #FFFFFF;
	}
</style>