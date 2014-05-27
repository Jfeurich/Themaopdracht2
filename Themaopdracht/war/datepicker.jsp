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
			constrainInput: true
		} );
	});
</script>