$(function() {
	
	$('#form1').on('submit', function(e) {
		e.preventDefault()
		$.ajax({
			type: 'POST',
			url: '/article/comment/save',
			cache: false,
			dataType: "json",
			data: { content: $('#content').val(), artID: $('#art_id').val() },
			success: function(data) {
				var content = `<div class="newsCommentDiv" style="box-shadow: 0 4px 8px 0 rgb(228, 228, 228);margin: 15px 0;min-height: 150px;">
                     					<img src=" ${data.memberPhotoUri}" class="rounded-circle mx-2" width="40" height="40">
                     					<span> ${data.nickName} </span><br>
                     					<span style="font-size: 14px;color: gray;margin: 15px;"> ${data.posttime}</span><br>
                     					<div style="padding: 20px;"> ${data.content}</div>
                     					</div>`;
				$('#Msg').append(content);
				$('#content').val('');
			},
			error: function() {
				window.location.href='http://localhost:8080/login';
			}
		});
	});


	/*0208家寶*/
	$("#trackimg").on("click", function() {

		var userid = $("#userid").val();
		var artid = $("#artid").val();
		var title = $("#title").val();
		var category = $("#category").val();
		var forumid = $("#forumid").val();
		var forumname = $("#forumname").val();

		if ($("#trackimg").attr('src') == '/images/shop/img/star.png') {

			$.ajax({
				method: 'get',
				url: '/article/addtotrack',
				data: { "userid": userid, "artid": artid, "title": title, "category": category, "forumid": forumid, "forumname": forumname },
				contentType: "application/json;charset=utf-8",
				dataType: 'text',
				success: function() {
					$("#trackimg").attr('src', '/images/shop/img/star1.png');
					
					if ( $('#toggleadd').first().is( ":hidden" ) ) {
   						 $( '#toggleadd' ).slideDown( "fast" );
						setTimeout(function(){ $( '#toggleadd' ).hide()}, 1650)
  						} else {
    					$( '#toggleadd' ).hide();
  						}
					
				}

			});

		} else {

			$.ajax({
				method: 'get',
				url: '/article/removetrack',
				data: { "userid": userid, "artid": artid },
				contentType: "application/json;charset=utf-8",
				dataType: 'text',
				success: function() {
					$("#trackimg").attr('src', '/images/shop/img/star.png');
					
					
					if ( $('#toggleremove').first().is( ":hidden" ) ) {
   						 $( '#toggleremove' ).slideDown( "fast" );
						setTimeout(function(){ $( '#toggleremove' ).hide()}, 1650)
  						} else {
    					$( '#toggleremove' ).hide();
  						}
				}

			});



		}

	});


	$("#likeimg").on("click", function() {
		
		var userid = $("#userid").val();
		var artid = $("#artid").val();
		var likenum = parseInt($(this).next().text());
		if ($("#likeimg").attr('src') == '/images/shop/img/thumb.png') {

			$.ajax({
				method: 'get',
				url: '/article/like',
				data: { "userid": userid, "artid": artid },
				contentType: "application/json;charset=utf-8",
				dataType: 'text',
				success: function() {
					$("#likeimg").next().text(likenum + 1);
					$("#likeimg").attr('src','/images/shop/img/thumb1.png');
				}

			});


		} else {

			$.ajax({
				method: 'get',
				url: '/article/removelike',
				data: { "userid": userid, "artid": artid },
				contentType: "application/json;charset=utf-8",
				dataType: 'text',
				success: function() {
					$("#likeimg").next().text(likenum - 1);
					$("#likeimg").attr('src', '/images/shop/img/thumb.png');
				}

			});
			
			}

			});
	

	/*0208家寶*/
});