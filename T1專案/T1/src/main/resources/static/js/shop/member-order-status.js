$(document).ready(function() {
	
	$('.changebtn').click(function(){
	
		$(this).parent().prev().find(".ostat").removeAttr("disabled");
	
	})	


	$('.ostat').change(function(){
		
		var id = parseInt($(this).parent().prev().prev().text());
		console.log(id);
		
		var status = $(this).val();
		console.log(status);
	
		$.ajax({
			method: 'get',
			url: '/member/order/statchange',
			data: { "id": id, "status": status },
			contentType: "application/json;charset=utf-8",
			dataType: 'text',
			success: function(data) {
				console.log(data);
				if (data == 'ok') {
				console.log("OKKKKKKKKKKKK");
				
				} 
						
			}

		});
	$(this).attr("disabled", true);
	})	



});