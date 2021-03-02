$(document).ready(function() {
	var rPath = "/images/shop/img/preload.png"
	$('#thumbnail').attr('src', rPath)

	$('#image').change(function() {
		showImageThumbnail(this);
	});


	//-------------------------上傳圖片預覽---------------------------------
	function showImageThumbnail(fileInput) {
		file = fileInput.files[0];
		reader = new FileReader();

		reader.onload = function(e) {
			$('#thumbnail').attr('src', e.target.result);
		};
		reader.readAsDataURL(file);
	}
	//------------------------datepicker樣式與作用-------------------------

	$("#datepicker").datepicker({
		dateFormat: "yyyy-mm-dd",
		changeMonth: true,
		changeYear: true
	});

	var dt = new Date();
	var time = dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
	console.log(time);


})











