$(function() {

	/*按update前面都變成input 其他按鍵disable*/
	$(".updatebtn").on("click", function() {

		var id = $(this).prev().val();
		console.log(id);

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
		var sup = $('#' + id + 'sup').val();
		var person = $('#' + id + 'person').val();
		var phone = $('#' + id + 'phone').val();


		$.ajax({
			method: 'get',
			url: '/products/supplier/list/update',
			data: { "id": id, "supplier": sup, "person": person, "phone": phone },
			contentType: "application/json;charset=utf-8",
			dataType: 'json',
			success: function(data) {
				$('#' + id + 'sup').prev('span').text(sup);
				$('#' + id + 'person').prev('span').text(person);
				$('#' + id + 'phone').prev('span').text(phone);

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

	/*按取消upload復原(不清除框框中的資料)*/
	$("#uploadcancel").on("click", function() {
		$("#uploadrow").css("display", "none");
		$("#uploadbtn").css("display", "unset");
		$(".updatebtn").prop("disabled", false);
		$(".deletebtn").prop("disabled", false);

	});

	/*supplier框框blur 以ajax確認有無重複*/
	$("#uploadsup").blur(function() {

		var sup = $(this).val();

		console.log(sup)
		$.ajax({
			method: 'get',
			url: '/products/supplier/list/check',
			data: { "supplier": sup },
			contentType: "application/json;charset=utf-8",
			dataType: 'text',
			success: function(data) {
				if (data == 'true') {
					$("#uploadconfirm").prop("disabled", true);
					$("#msg").text('此供應商已存在');
				} else {
					$("#uploadconfirm").prop("disabled", false);
					$("#msg").text('');
				}

			}

		});
	});


	/* 刪除 */
	$(".deletebtn").on("click", function() {

		var sup = $(this).parent().prev().prev().prev().prev().children('span').text();
		var id = $(this).parent().prev().prev().prev().prev().prev().children('span').text();

		var r = confirm("確定要刪除供應商 " + sup + " 嗎？");

		if (r == true) {
			$.ajax({
				method: 'get',
				url: '/products/supplier/list/remove',
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
