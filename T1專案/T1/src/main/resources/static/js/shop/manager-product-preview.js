$(document).ready(function(){
	var rPath = "/images/shop/img/preload.png"  //02/09
	$('#thumbnail').attr('src', rPath)
	
	$('#image').change(function(){
		showImageThumbnail(this);
	});
//-----------------

$('#load').hide();
	
//-------------------------上傳圖片預覽---------------------------------
	function showImageThumbnail(fileInput){
		file = fileInput.files[0];
		reader = new FileReader();
		
		reader.onload = function(e){
			$('#thumbnail').attr('src', e.target.result);
		//----02/08------------
			$('#submit-btn').removeAttr("disabled");
		//---------------------	
		};
		reader.readAsDataURL(file);
	}

var dt = new Date();
var time = dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
console.log(time);
//-----------------------------------------

//----------02/12-----中-------------------
$('#submit-btn').click(function(){
		$('#load').show();
	    var formData = new FormData();
        formData.append("image", $("#image")[0].files[0]);
        $.ajax({
            url: '/products/save',
			cache: false,
            type: 'post',
            data: formData,
			dataType: "text",
            processData: false,
            contentType: false,
            success: function () {
			console.log("OKKKK")
					setTimeout(function(){
						
						window.location.href="/products/list"}, 4500)
            }
        });
	
})
//------^^^^^-------------------------------

})











	