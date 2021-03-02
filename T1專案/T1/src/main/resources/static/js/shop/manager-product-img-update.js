$(document).ready(function(){
	
//----------頁面載入就show舊的封面	
	var rPath = $("#root").val();
	console.log(rPath)
	$('#thumbnail').attr('src', rPath)
	$('#dpath').val(rPath)
	
	$('#pimage').change(function(){
		showImageThumbnail(this); //方法在下面
		$("#submit").css("display","unset");
	});
	 

//-------------------------上傳圖片預覽---------------------------------
	function showImageThumbnail(fileInput){
		file = fileInput.files[0];
		reader = new FileReader();
		
		reader.onload = function(e){
			$('#thumbnail').attr('src', e.target.result);
		};
		reader.readAsDataURL(file);
	}

})











	