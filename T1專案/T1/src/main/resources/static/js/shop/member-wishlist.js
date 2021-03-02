
if(performance.navigation.type == 2){
   		location.reload(true);
}


$(function(){
	
	$(".trash").on("click",function(){
		
		var p_id=$(this).prev().text();
		
		$.ajax({
		method:'get',
		url:'/member/mywishlist/remove',
		data : {"p_id":p_id} ,
		contentType:"application/json;charset=utf-8",
		dataType:'text',
		success:function(){
			$("."+p_id).remove();
			
		}
	
	});
	/*-------------02/05----------------------------*/
		if ( $('#togglew2').first().is( ":hidden" ) ) {
   						 $( '#togglew2' ).slideDown( "fast" );
						setTimeout(function(){ $( '#togglew2' ).hide()}, 1650)
  						} else {
    					$( '#togglew2' ).hide();
  						}
		/*-----------^^^^^^^---------------------------*/
		
		
		
		
		
	});
	
	
	
	
	
	
	
	
	
});