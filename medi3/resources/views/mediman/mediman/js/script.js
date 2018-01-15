$(document).ready(function(){
	'use strict';

	$(window).on('load resize', function(){

		// Responsive Vimeo Video
		$('.vimeo-video').height( $('.vimeo-video').width() * 9/16 );

	});

	function singleStepHeight(){
		$('.single-step').each(function(){
			$(this).height( $(this).find('.single-step-inner').outerHeight() );
		});
	}

	function formHeight(){
		setTimeout(function(){
			singleStepHeight();
			console.log('jitututututu');
			$('.sign-up-bottom form').height( $('.sign-up-bottom form').children('.active').height() );
		}, 200);
	}

	$('nav ul li button').on('click', function(){
		formHeight();
	});

	$('.sign-in-bottom button').on('click', function(){
		formHeight();
	});

	// Fixing Modal Issue

	var isClickVanish = false;

	function clickButton(){
		if(isClickVanish){
			console.log('I"m clicking');
			$('nav ul button.sign-in').click();
		}
		isClickVanish = false;	
	}

	$('[data-dismiss]').on('click', function(){
		
		clickButton();
		
	});

	$('.modal').on('click', function(event){
		if( event.target != this) return;
		clickButton();
	});

	$('[data-vanish]').on('click', function(){
		isClickVanish = true;
		var vanishItem = $(this).attr('data-vanish');
		$(vanishItem).fadeOut();
		$('.modal-backdrop').fadeOut();
	});

	$('[data-hide]').on('click', function(){
		var hideElement = $(this).attr('data-hide');
		$(hideElement).fadeOut();
		$(hideElement).removeClass('active');
	});

	$('[data-show]').on('click', function(){
		var hideElement = $(this).attr('data-show');
		$(hideElement).fadeIn();
		$(hideElement).addClass('active');
		formHeight();
	});

	// Sign Up Step Status

	var onGoingStep = 2;

	$('[data-go-step]').on('click', function(){
		onGoingStep = $(this).attr('data-go-step');

		$('.single-sign-up-step').eq(onGoingStep-1).addClass('full');
		$('.single-sign-up-step').eq(onGoingStep-2).addClass('stick-full');

	});

	$('[data-back-step]').on('click', function(){
		onGoingStep = $(this).attr('data-back-step');

		$('.single-sign-up-step').eq(onGoingStep).removeClass('full');
		$('.single-sign-up-step').eq(onGoingStep-1).removeClass('stick-full');

	});

	// Sign In Role

	$('[data-role]').on('click', function(){
		var getRole = $(this).attr('data-role');
		$('[data-if]').show();
		$('[data-if]').filter("[data-if='" + getRole + "']").hide();
	});

	// Dropdown menu

	$('.user-settings > button').on('click', function(event){
		event.stopPropagation();
		$('.logged-in-dropdown-menu').fadeToggle();
		$('.logged-in-menu > li > button i').toggleClass('rotate');
	});

	$('.logged-in-dropdown-menu').on('click', function(event){
		event.stopPropagation();
	});

	$(document).on('click', function(){
		$('.logged-in-dropdown-menu').fadeOut();
		$('.logged-in-menu > li > button i').removeClass('rotate');
	});

});