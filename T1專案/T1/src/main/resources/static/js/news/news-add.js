$(function() {
	$('#submit').attr('disabled', true);
	$('#news_image').on('change', function() {
		oldImgUrl = $('#fileImage').attr('src');
		newImg = this.files[0];
		if (newImg.type.indexOf('image') == 0) { //如果上傳的是圖片檔才讀取
			reader = new FileReader();

			reader.onload = function(e) { //載入完成後，顯示 check 和 cancel
				$('#fileImage').attr('src', e.target.result);
				$('#cancel-upload_img').show();
			};
			reader.readAsDataURL(newImg); //更換圖片
			$('#submit').attr('disabled', false);
		} else {
			swal('格式錯誤', '您上傳的不是圖檔!', 'error');
		}
	})
//	$('#cancel-upload_img').on('click', function() {
//		$('#cancel-upload_img').hide();//隱藏 cancel
//		$('#fileImage').attr('src', oldImgUrl); //換回原本的圖片
//	})
})