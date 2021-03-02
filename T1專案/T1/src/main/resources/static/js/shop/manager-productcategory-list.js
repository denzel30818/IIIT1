$(function() {

	/*按update前面都變成input 其他按鍵disable*/
	$(".updatebtn").on("click", function() {

		var id = $(this).prev().val();

		$("." + id + "info").css("display", "none");
		$("." + id + "info").next("input").css("display", "unset");
		$(this).next().css("display", "unset");
		$(this).next().next().css("display", "unset");
		$(this).css("display", "none");
		
		$("#uploadbtn").prop("disabled", true);
		$(".updatebtn").prop("disabled", true);
		$(".deletebtn").prop("disabled", true);
	});

	/*按取消update前面變回來*/
	$(".cancelbtn").on("click", function() {
		var id = $(this).prev().prev().prev().val();
		$("." + id + "info").css("display", "unset");
		$("." + id + "info").next("input").css("display", "none");

		$(this).css("display", "none");
		$(this).prev().css("display", "none");
		$(this).prev().prev().css("display", "unset");

		$(".updatebtn").prop("disabled", false);
		$(".deletebtn").prop("disabled", false);
		$("#uploadbtn").prop("disabled", false);

	});

	/*按確認update ajax更新*/
	$(".confirmbtn").on("click", function() {

		var id = $(this).prev().prev().val();
		var pc = $('#' + id + 'pc').val();


		$.ajax({
			method: 'get',
			url: '/products/productcategory/list/update',
			data: { "id": id, "category": pc},
			contentType: "application/json;charset=utf-8",
			dataType: 'text',
			success: function(){
				$('#' + id + 'pc').prev('span').text(pc);

				$('.updatebtn').css("display", "unset");
				$('.confirmbtn').css("display", "none");
				$('.cancelbtn').css("display", "none");

				$("." + id + "info").css("display", "unset");
				$("." + id + "info").next().css("display", "none");

				$(".updatebtn").prop("disabled", false);
				$(".deletebtn").prop("disabled", false);
				$("#uploadbtn").prop("disabled", false);

			}

		});

	});

	/*按upload出現輸入框*/
	$("#uploadbtn").on("click", function() {
		$("#uploadrow").css("display", "unset");
		$(this).css("display", "none");
		$(".updatebtn").prop("disabled", true);
		$(".deletebtn").prop("disabled", true);

	});

	/*按取消upload復原*/
	$("#uploadcancel").on("click", function() {
		$("#uploadrow").css("display", "none");
		$("#uploadbtn").css("display", "unset");
		$(".updatebtn").prop("disabled", false);
		$(".deletebtn").prop("disabled", false);

	});

	/*gametype框框blur 以ajax確認有無重複*/
	$("#uploadpc").blur(function() {

		var pc = $(this).val();

		$.ajax({
			method: 'get',
			url: '/products/productcategory/list/check',
			data: { "category": pc },
			contentType: "application/json;charset=utf-8",
			dataType: 'text',
			success: function(data) {
				if (data == 'true') {
					$("#uploadconfirm").prop("disabled", true);
					$("#msg").text('此商品型別已存在');
				} else {
					$("#uploadconfirm").prop("disabled", false);
					$("#msg").text('');
				}
			}

		});
	});


	/* 刪除 */
	$(".deletebtn").on("click", function() {

		var id = $(this).parent().prev().prev().prev().children('span').text();
		var pc = $(this).parent().prev().prev().children('span').text();

		var r = confirm("確定要刪除商品型別 " + pc + " 嗎？");

		if (r == true) {
			$.ajax({
				method: 'get',
				url: '/products/productcategory/list/remove',
				data: { "id": id },
				contentType: "application/json;charset=utf-8",
				dataType: 'text',
				success: function() {
					$("#" + id).parent().parent().remove();
				}
			});
		}
	});



});