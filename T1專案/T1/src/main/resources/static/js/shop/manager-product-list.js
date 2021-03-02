$(function() {

	var count = 0;
	if (count > 0) {
		if (document.referrer != "http://localhost:8080/products/list") {
			window.location.reload();
			count += 1;
		}
	}

});