$(function() {
	var len = 130; // 超過50個字以"..."取代
	$(".news-article-body-div").each(function() {
		if ($(this).text().length > len) {
			$(this).attr("title", $(this).text());
			var text = $(this).text().substring(0, len - 1) + "...";
			$(this).text(text);
		}
	});
	$('#submit').click(function(e) {
		e.preventDefault();
		if (date.month.value == "") {
			alert("您未選擇月份");
		} else if (date.year.value == "") {
			alert("您未選擇年份");
		}
		if (date.month.value != "" & date.year.value != "") {
			$('#date').submit();
		}
	})
});