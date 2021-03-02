$(function(){
	
	$(".replybtn").on("click",function(){
		
		var id = $(this).prev().val();
		var content= $(this).prev().prev().val();
		
		$("#reply").css("display","unset");		
		
		$(".replybtn").css("display","none");
		
		$(this).parent().append('<div class="txt">回覆中<div>');
		
		$("#feedbackid").val(id);
		$("#thisquestion").text('問題內文：'+content);
		
	});
	
	$("#replycancel").on("click",function(){
		$("#reply").css("display","none");		
		$(".replybtn").css("display","unset");
		$(".txt").remove();
		
	});
	
	
	
});