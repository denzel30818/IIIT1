$(function() {

	$.validator.addMethod(
		"regex",
		function(value, element, regexp) {
			var re = new RegExp(regexp);
			return this.optional(element) || re.test(value);
		},
		"Please check your input."
	);

	$('#updatePassword').validate({
		rules: {
			userPassword: {
				required: true,
				regex: /^[a-zA-z0-9]{6,12}$/
			},
			newPassword: {
				required: true,
				regex: /^[a-zA-z0-9]{6,12}$/
			},
			checkNewPassword: {
				equalTo: '#newPassword'
			}
		},
		messages: {
			userPassword: {
				required: "請輸入舊密碼",
				regex: "請輸入6-12個英數字"
			},
			newPassword: {
				required: "請輸入新密碼",
				regex: "請輸入6-12個英數字"
			},
			checkNewPassword: {
				equalTo: '兩次輸入的密碼不相符'
			}
		}
	});

	// ajax 驗證舊密碼不相同，新密碼等於舊密碼時警告
	$('#userPassword').on('change', function() {
		$.ajax({
			url: '/updatePassword',
			type: 'POST',
			cache: false,
			data: {userPassword:$('#userPassword').val()},
			success: function(response) {	
				if (response) {
					$('#oldPwd-error').text('')
				} else {
					$('#oldPwd-error').text('舊密碼輸入錯誤!')
				}
			}
		})
	})

	// 新舊錯誤訊息彈出框
	$('#update-Pwd').on('click', function(e) {
		e.preventDefault();
		if ($('#updatePassword div[class="pwd-error"]').text() == '') {
			$('#updatePassword').submit();
		} else {
			let errorArray = $('#updatePassword div[class="pwd-error"]').text().split('!');
			let errorMsg = '';
			for (let i = 0; i < errorArray.length - 1; i++) {
				errorMsg += errorArray[i] + '!<br/>'
			}
			swal('', errorMsg, 'error');
		}
	});
});