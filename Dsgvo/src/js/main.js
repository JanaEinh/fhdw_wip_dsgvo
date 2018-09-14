/* Javascript function, that prevents that a form is submitted by pressing the Enter Key */

function preventEnterFromSubmit(){
	$(document).ready(function() {
	    $(window).keydown(function(event){
	        if((event.keyCode == 13) && ($(event.target)[0]!=$("textarea")[0])) {
	            event.preventDefault();
	            return false;
	        }
	    });
	});
}