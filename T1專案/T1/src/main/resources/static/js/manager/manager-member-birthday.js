$(function() {
	$('.birth').hover(function() { // hover 圖片變換
		if ($(this).attr('src') != '/images/manager/BirthdayCard.png') { // 確認有沒有送過賀卡
			$(this).attr('src', '/images/manager/BirthdayCardBlack.png');
		}
	}, function() {
		if ($(this).attr('src') != '/images/manager/BirthdayCard.png') {
			$(this).attr('src', '/images/manager/BirthdayCardColored.png');
		}
	});
	$('.birth').on('click', function() {
		if ($(this).attr('src') != '/images/manager/BirthdayCard.png') {
			$(this).attr('src', '/images/manager/BirthdayCard.png'); // 切換已送過的圖片

			var id = $(this).parent().parent().find('td:eq(0)').text(); // 取得送禮ID
			$.ajax({
				url: '/congratulate?id=' + id,
				type: 'POST',
				contentType: false,
				processData: false,
				cache: false
			});
		}
	});
	$('.sendAll').on('click', function() { // 一鍵發送
		swal('', '生日賀卡已排程發送', 'success');

		$.ajax({
			url: '/congratulateAll',
			type: 'POST',
			contentType: false,
			processData: false,
			cache: false,
		});
	});
});