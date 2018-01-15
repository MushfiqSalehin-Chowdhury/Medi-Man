


function setImage(){

	var image;

	$('.cover-image').each(function(i){
		image = new Image();
		image.src = $(this).children('img').attr("src");

		thisContainer = $(this);

		$(image).on('load', function(){

			var coverWidth = $('.cover-image').eq(i).width();
			var coverHeight = $('.cover-image').eq(i).height();
			
			containerRatio = coverWidth/coverHeight;
			imageRatio = this.width/this.height;

			if(imageRatio > containerRatio){

				$('.cover-image').eq(i).children('img').css({
					'height': '100%',
					'width': 'auto'
				});
				$('.cover-image').eq(i).children('img').css({
					'left': -($('.cover-image').eq(i).children('img').width() - $('.cover-image').eq(i).width())/2 + 'px',
					'top': 0
				});
			}else{
				$('.cover-image').eq(i).children('img').css({
					'height': 'auto',
					'width': '100%'
				});
				$('.cover-image').eq(i).children('img').css({
					'left': 0,
					'top': -($('.cover-image').eq(i).children('img').height() - $('.cover-image').eq(i).height())/2 + 'px'
				});
			}

			// Additional Blur effect
			$('.reports').removeClass('blur');
		});
	});
}

$(window).on('load resize', function(){
	setImage();
});

