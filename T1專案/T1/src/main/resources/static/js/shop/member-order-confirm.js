$(function(){
	
	var total = 0;
	$(".smtotal").each(function() {
		total += parseInt($(this).html().substring(3))
	});
	
	$('#proTotal').text("NT$" + total);
	
	
	
});