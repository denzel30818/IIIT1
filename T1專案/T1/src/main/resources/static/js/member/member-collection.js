$(function() {
	$('.delTrack').on('click', function(e) {
		e.preventDefault();
		$(this).parent().parent().remove();
		var userid = $(this).next().val();
		var artid = $(this).next().next().val();
		$.ajax({
			url: '/article/removetrack',
			type: 'GET',
			contentType: "application/json;charset=utf-8",
			dataType: 'text',
			data: { "userid": userid, "artid": artid },
			success: function() {
				if ($('.delTrack').length == 0) {
					window.location.href = 'http://localhost:8080/member/collectionList?page=' + ($('#pageNum').val() - 1);
				}
			}
		});
	});
});
	// Sweet版本
//	$(function() {
//		$('.delArticle').on('click', function(e) {
//			e.preventDefault();
//			var $delArticle = $(this);
//			swal({
//				title: "確定要刪除嗎?",
//				type: "warning",
//				cancelButtonColor: '#d33',
//				showCancelButton: true
//			}).then(function(result) {
//				if (result.value) {
//					$delArticle.parent().parent().remove();
//					var formData = new FormData();
//					formData.append('delID', $delArticle.next().val());
//					$.ajax({
//						url: '/member/deleteArticle',
//						type: 'POST',
//						contentType: false,
//						processData: false,
//						cache: false,
//						data: formData,
//						success: function() {
//							swal('', '刪除成功', 'success');
//							if ($('.delArticle').length == 0) {
//								window.location.href = 'http://localhost:8080/member/article?page=' + ($('#pageNum').val() - 1);
//							}
//						}
//					});
//				}
//			});
//		});
//	});