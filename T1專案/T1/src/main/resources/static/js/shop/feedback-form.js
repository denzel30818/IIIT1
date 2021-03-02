$(function(){
	
	$.validator.addMethod(
		"regex",
		function(value, element, regexp) {
			var re = new RegExp(regexp);
			return this.optional(element) || re.test(value);
		},
		"Please check your input."
	);

	$('#feedback').validate({
		rules: {
			user_name:{
				required: true,
			},
			email: {
				required: true,
				regex: /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{3,4})$/
			},
			content:{
				required: true,
				regex:/.{5,}/
			}
		},
		messages: {
			user_name:{
				required:"請輸入大名",
			},
			
			email: {
				required: "請輸入信箱",
				regex: "信箱格式錯誤 ex:abc@xx.com"
			},
			content:{
				required: "請輸入問題內容",
				regex:"內容字數過少"
			}
			
		}
	});

});