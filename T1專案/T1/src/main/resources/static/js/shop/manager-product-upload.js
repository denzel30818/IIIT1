$(function() {

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
			p_name: {
				required: true
			},
			publisher: {
				required: true
			},
			supplier: {
				required: true
			},
			release_date: {
				required: true,
				regex: /^([0-9]{4}-[0-9]{2}-[0-9]{2})$/
			},
			sales_volume: {
				required: true,
				regex: /^([0-9]*)$/
			},
			price: {
				required: true,
				regex: /^([1-9]{1}[0-9]*)$/
			},
			quantity: {
				required: true,
				regex: /^([0-9]*)$/
			}
		},
		messages: {
			p_name: {
				required: "請輸入商品名稱"
			},
			publisher: {
				required: "請輸入開發商名稱"
			},
			supplier: {
				required: "請輸入代理商名稱"
			},
			release_date: {
				required: "請輸入發行日期",
				regex: "日期格式：yyyy-mm-dd"
			},
			sales_volume: {
				required: "請輸入銷量",
				regex: "請輸入數字"
			},
			price: {
				required: "請輸入商品價格",
				regex: "請輸入正確價格"
			},
			quantity: {
				required: "請輸入庫存量",
				regex: "請輸入數字"
			}
		}
	});

	$("#callsup").on("click", function(event) {
		event.preventDefault();
		$("#suppliertable").css("display", "unset");
	});

	$("#close").on("click", function() {
		$("#suppliertable").css("display", "none");
	});

	$(".selectsup").on("click", function() {
		var sup = $(this).parent().next().next().children().text();
		$("#sup").val(sup);
		$("#suppliertable").css("display", "none")
	});

	console.log($(".thisProductType").text());
	
	$(".gt").each(function() {
		var gt = $(this).text();
		$(".thisProductType").each(function() {
			var pt = $(this).text();
			if (gt == pt) {
				$("#" + gt).prop("checked", true);
			}
		});


	});


});