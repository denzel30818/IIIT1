$(function() {
	$('#index-carousel').slick({
		dots: true,
		arrows: false,
		autoplay: true,
		centerMode: true,
		variableWidth: true,
		adaptiveHeight: true,
		slidesToShow: 1,
		slidesToScroll: 3,
		autoplaySpeed: 2000
	});
	$('#index-news-tab a').hover(function() { // 新聞分頁按鈕 hover 特效
		$(this).addClass('enter');
	}, function() {
		$(this).removeClass('enter');
	})
	var len = 50; // 超過50個字以"..."取代
	$(".news-text").each(function() {
		if ($(this).text().length > len) {
			$(this).attr("title", $(this).text());
			var text = $(this).text().substring(0, len - 1) + "...";
			$(this).text(text);
		}
	});
});