$(function() {
	$('#login').validate({
		rules: {
			account: {
				required: true,
			},
			userPassword: {
				required: true,
			}
		},
		messages: {
			account: {
				required: "請輸入帳號",
			},

			userPassword: {
				required: "請輸入密碼",
			}
		}
	})

	var tags = [ // AutoComplete參數陣列
		"Ann123",
		"iiione"
	];

	$("#account").autocomplete({
		source: tags,
		minLength: 0,
	});
});