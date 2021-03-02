/*------------02/03 家寶---------- */


if (performance.navigation.type == 2) {
	location.reload(true);
}


/*------------02/03 家寶 ---------- */


$(function() {


	jQuery('.btn-reduce-number').on("click", function() {
		var _num = parseInt(jQuery('.show-products-number').text(), 10) - 1;
		if (_num > 0) {
			jQuery('.show-products-number').text(_num);
		}
	});

	jQuery('.btn-add-number').on("click", function() {
		var _num = parseInt(jQuery('.show-products-number').text(), 10) + 1;
		if (_num <= 5) {
			jQuery('.show-products-number').text(_num);
		}
	});

	var p_id = $("#p_id").val();

	$('#wish').on("click", function() {
		$.ajax({
			method: 'get',
			url: '/shop/checkWishList',
			data: { "p_id": p_id },
			contentType: "application/json;charset=utf-8",
			dataType: 'text',
			success: function(data) {
				console.log(data);
				if (data == 'true') {
					$("#wish").attr('src', '/images/shop/img/like.png');

						if ($('#togglew').first().is(":hidden")) {
							$('#togglew').slideDown("fast");
							setTimeout(function() { $('#togglew').hide() }, 1650)
						} else {
							$('#togglew').hide();
							}
					} else {
						$("#wish").attr('src', '/images/shop/img/like1.png');
				
						if ( $('#togglew2').first().is( ":hidden" ) ) {
   							$( '#togglew2' ).slideDown( "fast" );
							setTimeout(function(){ $( '#togglew2' ).hide()}, 1650)
  						} else {
    						$( '#togglew2' ).hide();
					}
				}
			}

		});


	});

	var p_id = parseInt($("#p_id").val());

	$('#add_btn').on("click", function() {
		var add_num = parseInt($('#ord_num').text());
		$.ajax({
			method: 'get',
			url: '/shop/add',
			data: { "p_id": p_id, "add_num": add_num },
			contentType: "application/json;charset=utf-8",
			dataType: 'text',
			success: function(data) {
				if (data == 'true') {
					if ( $('#toggle').first().is( ":hidden" ) ) {
   						 $( '#toggle' ).slideDown( "fast" );
						setTimeout(function(){ $( '#toggle' ).hide()}, 1650)
  						} else {
    					$( '#toggle' ).hide();
  						}
					/*------------02/03 家寶 ---------- */
					var num = parseInt($("#cartnum").text());
					$("#cartnum").text(num + 1);

					/*------------02/03 家寶 ---------- */

				} else {
					if ( $('#toggle2').first().is( ":hidden" ) ) {
   						 $( '#toggle2' ).slideDown( "fast" );
						setTimeout(function(){ $( '#toggle2' ).hide()}, 1650)
  					} else {
    					$( '#toggle2' ).hide();
  						}

				}
			}

		});

	});


});
