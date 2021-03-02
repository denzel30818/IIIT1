$(function() {
	$('.index-carousel').slick({
		dots: true, // 顯示圓點按鈕
		// arrows: false, // 顯示箭頭
		autoplay: true, // 自動播放
		centerMode: true, // 置中
		variableWidth: true, // 寬度可變動
		adaptiveHeight: true, // 高度可變動
		slidesToShow: 1, // 一次播出的件數
		slidesToScroll: 3, // 一次滾動的件數
		autoplaySpeed: 2000 // 自動播放延遲
	});
	$('#index-top-tab a').hover(function() {
		$(this).addClass('enter');
	}, function() {
		$(this).removeClass('enter');
	})
});