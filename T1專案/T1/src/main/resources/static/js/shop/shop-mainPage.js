/*------------02/03 家寶---------- */



if (performance.navigation.type == 2) {
	location.reload(true);
}



/*------------02/03 家寶 ---------- */



$(function() {

	$('.tab a').on("click", function() {
		$('.tab').removeClass("active");
		$(this).parent().addClass("active");
	});


});