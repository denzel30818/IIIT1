$(function() {
	
	$("#autofill").on("click",function(){
   $("#receiver").val('JohnCena');
   $("#receiverPhone").val('0911333222');
   $("#address").val('新北市三重區');
   
  });

	/*--------02/05-----Radio改無圈圈---*/
	$(".pay").checkboxradio({
		icon: false
	});
	/*---------- */

	var availableTags = [
		"基隆市",
		"台北市",
		"新北市",
		"桃園縣",
		"新竹市",
		"新竹縣",
		"苗栗縣",
		"台中市",
		"彰化縣",
		"南投縣",
		"雲林縣",
		"嘉義市",
		"嘉義縣",
		"台南市",
		"高雄市",
		"屏東縣",
		"台東縣",
		"花蓮縣",
		"宜蘭縣",
		"澎湖縣",
		"金門縣",
		"連江縣"
	];
	$("#address").autocomplete({
		source: availableTags
	});

	/*----0203 家寶-----*/
	/*驗證*/
	$.validator.addMethod(
		"regex",
		function(value, element, regexp) {
			var re = new RegExp(regexp);
			return this.optional(element) || re.test(value);
		},
		"Please check your input."
	);

	$('#confirmationForm').validate({
		rules: {
			receiver: {
				required: true,
				regex: /^[\u4e00-\u9fa5a-zA-Z]{2,22}$/
			},
			receiverPhone: {
				required: true,
				regex: /^[0-9]{9,12}$/
			},
			address: {
				required: true,
				regex: /^[\u4e00-\u9fa5a-zA-Z0-9]{3,40}$/
			}
		},
		messages: {
			receiver: {
				required: "請輸入收件人姓名",
				regex: "請輸入正確姓名"
			},
			receiverPhone: {
				required: "請輸入電話號碼",
				regex: "請輸入正確電話格式"
			},
			address: {
				required: "請輸入地址",
				regex: "請輸入正確地址"
			}
		}
	});

	$('#confirmationForm input').attr('autocomplete', 'off');
	/*----0203 家寶-----*/

});