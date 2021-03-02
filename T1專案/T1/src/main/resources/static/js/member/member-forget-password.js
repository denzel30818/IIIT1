$(function() {

	$.validator.addMethod(
		"regex",
		function(value, element, regexp) {
			var re = new RegExp(regexp);
			return this.optional(element) || re.test(value);
		},
		"Please check your input."
	);

	$('#forget-password').validate({
		rules: {
			email: {
				required: true,
				regex: /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/
			}
		},
		messages: {
			email: {
				required: "請輸入email",
				regex: "格式錯誤 ex:abc@xx.com"
			}
		}
	});

	$('#sendEmail').attr('disabled', true);

	// 輸入時清除錯誤訊息、解鎖按鈕
	$('#email').on('input', function() {
		$('#sendEmail').attr('disabled', false);
		$('#emailSendError').text('');
	})

	// 送出後鎖定按鈕，三秒後解除鎖定
	$('#forget-password').submit(function() {
		$('#sendEmail').attr('disabled', true);

		setTimeout(function() {
			$('#sendEmail').attr('disabled', false);
		}, 3000)
	})
});