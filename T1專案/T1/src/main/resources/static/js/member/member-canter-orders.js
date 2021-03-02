$(function() {
	$('.check-details').click(function(e) {
		e.preventDefault();
		var id = $(this).prev().val();
		$.ajax({
			url: '/ordersDetail?id=' + id,
			type: "POST",
			dataType: 'json',
			success: function(res) {
				var s = '';
				var fee = 0;
				var total = 0;
				console.log(res);
				for (let i = 0; i < res.length; i++) {
					s += '<tr><td><a href="/shop/product/' + res[i].productId + '">' + res[i].productName + '</a></td><td>' + res[i].quantity + '</td><td>$ ' + res[i].singlePrice + '</td><td>$ ' + res[i].totalPrice + '</td>';
					total += res[i].totalPrice;
					if (res[i].fee == 120) {
						fee = 120;
					}
				}
				$('#dialog').html('<table class="table table-hover table-borderless text-center"><thead><tr><th>產品名稱</th><th>數量</th><th>購買價格</th><th>小計</th></thead><tbody>' + s + '</tbody></table>' +
					'<hr/><table class="table table-borderless text-center"><tbody>' +
					'<tr><td style="width:65%;"></td><td style="width:15%">運費</td><td style="width:20%">$ ' + fee + '</td></tr>' +
					'<tr><td style="width:65%;"></td><td style="width:15%">總計</td><td style="width:20%">$ ' + (total + fee) + '</td></tbody></table>');
				$("#dialog").dialog('open');
			}
		})
	})

	$("#dialog").dialog({
		minWidth: 680,
		autoOpen: false,
		dialogClass: 'fixed-dialog'
	});
})