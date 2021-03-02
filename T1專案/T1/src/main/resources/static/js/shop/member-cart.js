$(document).ready(function() {

	var all = 0;
	$(".totalPrice").each(function() {
		all += parseInt($(this).html().substring(3))
	});
	console.log(all)

	$("#all").text("NT$" + all)

	/*---------------------------------------------------------*/

	$('.qnumber').change(function() {

		var ord = 0
		var id = parseInt($(this).parent().parent().text())
		console.log(id)

		var n = 0;
		n = $(this).val()
		console.log(n)

		var sp = 0;
		sp = parseInt($(this).parent().next('.singlePrice').text().slice(3))
		console.log(sp)

		var total = n * sp
		console.log(total)

		$(this).parent().next().next('.totalPrice').text('NT$' + total)

		$(".totalPrice").each(function() {
			console.log(parseInt($(this).html().substring(3)))
			ord += parseInt($(this).html().substring(3))
		});
		console.log(ord)

		$("#all").text("NT$" + ord)

		$.ajax({
			method: 'get',
			url: '/member/mycart/update',
			data: { "id": id, "quantity": n },
			contentType: "application/json;charset=utf-8",
			dataType: 'text',
			success: function(data) {
				console.log(data);
				if (data == 'true') {
					/*----------------02/05---中----------------------*/	
					if ( $('#change').first().is( ":hidden" ) ) {
   						 $( '#change' ).slideDown( "fast" );
						setTimeout(function(){ $( '#change' ).hide()}, 1650)
  						} else {
    					$( '#change' ).hide();
  						}
				/*----------------＾＾＾＾＾＾＾----------------------*/	

				} else {
					Swal.fire('無庫存')
				}
			}

		});

	})
	
/*------------02/03 家寶 start---------- */
/*------------刪除---------- */		
	
	$(".remove").on("click",function(){
		
		var cid=$(this).next().next().val();
		var pretotal=$("#all").text().substring(3);
		var thisprice=$(this).parent().prev().text().substring(3);
		
		$.ajax({
			method: 'get',
			url: '/member/mycart/delete',
			data: { "id": cid},
			contentType: "application/json;charset=utf-8",
			dataType: 'text',
			success: function() {
				$("." + cid).remove();
				
				var newtotal=pretotal-thisprice;
				
				$("#all").text("NT$"+newtotal);
				
				
				var num = $("#cartnum").text();
				num = parseInt(num);
				$("#cartnum").text(num - 1);
				
				var n = parseInt($("#all").text().substring(3));
				if(n==0){
					$("#order").remove();
				}
				/*----------------02/05---中----------------------*/	
				if ( $('#removec').first().is( ":hidden" ) ) {
   						 $( '#removec' ).slideDown( "fast" );
						setTimeout(function(){ $( '#removec' ).hide()}, 1650)
  						} else {
    					$( '#removec' ).hide();
  						}
				/*----------------^^^^^^^^^----------------------*/	
			}

		});		
		
	});

	/*------------02/03 家寶 end---------- */







	/*----------------------01/28---------------------------- */
	$('.addw').on("click", function() {
		var cId = $(this).prev().val();
		var product_id = $(this).prev().prev().val();
		/*------------02/03 家寶 start---------- */
		
		var pretotal=$("#all").text().substring(3);
		var thisprice=$(this).parent().prev().text().substring(3);

		$.ajax({
			method: 'get',
			url: '/member/CartToWishList',
			data: { "p_id": product_id, "cId": cId },
			contentType: "application/json;charset=utf-8",
			dataType: 'text',
			success: function() {

				$("." + cId).remove();
				
				var newtotal=pretotal-thisprice;
				
				$("#all").text("NT$"+newtotal);
				
				
				var num = $("#cartnum").text();
				num = parseInt(num);
				$("#cartnum").text(num - 1);
				
				var n = parseInt($("#all").text().substring(3));
				if(n==0){
					$("#order").remove();
				}
		/*------------02/03 家寶 end---------- */
			/*----------------02/05---中----------------------*/	
			if ( $('#addw').first().is( ":hidden" ) ) {
   						 $( '#addw' ).slideDown( "fast" );
						setTimeout(function(){ $( '#addw' ).hide()}, 1650)
  						} else {
    					$( '#addw' ).hide();
  						}
			
		/*----------------^^^^^^^^^^----------------------*/	
			
			}

		});

	});

});