$(function() {
	$('.ban').on('click', function() {
		console.log($(this).css('color'))
		if ($(this).css('color') == 'rgb(204, 204, 204)') {
			$(this).parent().parent().children().css('background-color', '#fff');
			$(this).css('color', 'red');

			var id = $(this).parent().parent().find('td:eq(0)').text(); // 找尋該行ID值
			$.ajax({
				url: '/unbanUser?id=' + id,
				type: 'POST',
				contentType: false,
				processData: false,
				cache: false
			})
		} else {
			$(this).parent().parent().children().css('background-color', '#cf6363');
			$(this).css('color', '#ccc');

			var id = $(this).parent().parent().find('td:eq(0)').text();
			$.ajax({
				url: '/banUser?id=' + id,
				type: 'POST',
				contentType: false,
				processData: false,
				cache: false
			})
		}
	});
})