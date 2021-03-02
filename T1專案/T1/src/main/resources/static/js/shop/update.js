$(function(){
	
	
	$(".gt").each(function(){
		
		var gt=$(this).text();
		
		$(".pt").each(function(){
			
			var pt=$(this).text();
			
			if(gt==pt){
				
				$("#"+gt).prop("checked",true);
				
			}
			
		});
		
		
	});
	
	
	
	
	
	
	
});