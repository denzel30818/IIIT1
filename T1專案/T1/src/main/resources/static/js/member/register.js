$(function() {

	$.validator.addMethod(
		"regex",
		function(value, element, regexp) {
			var re = new RegExp(regexp);
			return this.optional(element) || re.test(value);
		},
		"Please check your input."
	);

	$('#register').validate({
		rules: {
			account: {
				required: true,
				regex: /^[\w]{6,12}$/
			},
			userPassword: {
				required: true,
				regex: /^[a-zA-z0-9]{6,12}$/
			},
			checkUserPassword: {
				equalTo: '#userPassword'
			},
			nickName: {
				required: true,
				regex: /[\u4e00-\u9fa5\w]{1,15}/
			},
			birthDate: {
				required: true,
			},
			email: {
				required: true,
				regex: /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{3,4})$/
			}
		},
		messages: {
			account: {
				required: "請輸入帳號",
				regex: "請輸入6-12個英數字"
			},
			userPassword: {
				required: "請輸入密碼",
				regex: "請輸入6-12個英數字"
			},
			checkUserPassword: {
				equalTo: '&nbsp;密碼不相符'
			},
			nickName: {
				required: "請輸入暱稱",
				regex: "請輸入1-15個字元"
			},
			birthDate: {
				required: '請選擇日期'
			},
			email: {
				required: "請輸入信箱",
				regex: "格式錯誤 ex:abc@xx.com"
			}
		}
	});

	$('#register :input').attr('autocomplete', 'off'); // 關閉自動完成功能

	// 重名錯誤訊息彈出框
	$('#add-member').on('click', function(e) {
		e.preventDefault();
		if ($('#register div[class="duplicate-error"]').text() == '') {
			$('#register').submit();
		} 
	});
	
//	$('#add-member').on('click', function(e) {
//		e.preventDefault();
//		if ($('#register div[class="duplicate-error"]').text() == '') {
//			$('#register').submit();
//		} else {
//			let errorArray = $('#register div[class="duplicate-error"]').text().split('!');
//			let errorMsg = '';
//			for (let i = 0; i < errorArray.length - 1; i++) {
//				errorMsg += errorArray[i] + '!<br/>'
//			}
//			swal('', errorMsg, 'error');
//		}
//	});

	// ajax 驗證重複名稱
	$('#account').on('input', function() {
		$.ajax({
			url: 'http://localhost:8080/users',
			type: 'GET',
			success: function(data) {
				for (user of data._embedded.users) {
					if ($('#account').val() == user.account) {
						$('#account-duplicate').text('帳號已被註冊!')
						break;
					} else {
						$('#account-duplicate').text('')
					}
				}
			}
		})
	})
	$('#nickName').on('input', function() {
		$.ajax({
			url: 'http://localhost:8080/users',
			type: 'GET',
			success: function(data) {
				for (user of data._embedded.users) {
					if ($('#nickName').val() == user.nickName) {
						$('#nickName-duplicate').text('暱稱已被使用!')
						break;
					} else {
						$('#nickName-duplicate').text('')
					}
				}
			}
		})
	})
	$('#email').on('input', function() {
		$.ajax({
			url: 'http://localhost:8080/users',
			type: 'GET',
			success: function(data) {
				for (user of data._embedded.users) {
					if ($('#email').val() == user.email) {
						$('#email-duplicate').text('信箱已被使用!')
						break;
					} else {
						$('#email-duplicate').text('')
					}
				}
			}
		})
	})
});