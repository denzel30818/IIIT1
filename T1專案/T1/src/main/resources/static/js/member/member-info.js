$(function() {
	$('#upload_img').on('change', function() {
		oldImgUrl = $('#avatar').attr('src');
		newImg = this.files[0];
		if (newImg.type.indexOf('image') == 0) { // 如果上傳的是圖片檔才讀取
			reader = new FileReader();

			reader.onload = function(e) { // 載入完成後，顯示 check 和 cancel
				$('#avatar').attr('src', e.target.result);
				$('#check-upload_img').show();
				$('#cancel-upload_img').show();
			};
			reader.readAsDataURL(newImg); // 更換圖片
		} else {
			if ($('#wrongimg').first().is(":hidden")) {
				$('#wrongimg').slideDown("fast");
				setTimeout(function() { $('#wrongimg').hide() }, 3000)
			} else {
				$('#wrongimg').hide();
			}
		}
	})
	$('#check-upload_img').on('click', function(e) {
		e.preventDefault();

		var file = $('#upload_img').prop('files')[0];   // 取得上傳檔案屬性
		var formData = new FormData();
		formData.append('upload_img', file);
		$.ajax({
			url: 'http://localhost:8080/member/info',
			type: 'POST',
			contentType: false,
			processData: false,
			data: formData,
			success: function() {
				if ($('#changesuccess').first().is(":hidden")) {
					$('#changesuccess').slideDown("fast");
					setTimeout(function() { $('#changesuccess').hide() }, 3000)
				} else {
					$('#changesuccess').hide();
				}
			}
		})
		$('#check-upload_img').hide(); // 隱藏 check 和 cancel
		$('#cancel-upload_img').hide();
	})
	$('#cancel-upload_img').on('click', function() {
		$('#check-upload_img').hide(); // 隱藏 check 和 cancel
		$('#cancel-upload_img').hide();
		$('#avatar').attr('src', oldImgUrl); // 換回原本的圖片
	})
})