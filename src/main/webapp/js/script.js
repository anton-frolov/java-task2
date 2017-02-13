$(function() {
 

 
	$('#guestEtry').click( function() {
		$("#inputUsername").val("guest");
		$("#inputPassword").val("guest");
		document.loginForm.submit ();
	});
	
	$('#registrationForm').submit(function(event) {

		event.preventDefault();
		var formValid = true;

		$('#contactForm input').each(function() {

			var formGroup = $(this).parents('.form-group');
			var glyphicon = formGroup.find('.form-control-feedback');
			if (this.checkValidity()) {
				formGroup.addClass('has-success').removeClass('has-error');
				glyphicon.addClass('glyphicon-ok').removeClass('glyphicon-remove');
			} else {
				formGroup.addClass('has-error').removeClass('has-success');
				glyphicon.addClass('glyphicon-remove').removeClass('glyphicon-ok');
				formValid = false;  
			}
		});
		
		if(formValid){
			
			var login = $("#inputUsername").val();
			var passwd = $("#inputPassword").val();
			var email = $("#inputEmail").val();
		   
		    $.ajax({
		    	type: "POST",
		        url: "registration",
		        data: {
					userId: login,
					password: passwd,
					userEmail: email
				},
		        cache: false,
		        
		        success : function(data){
					var $data =  data;
					//$('#error').text('');
					if ($data.result == "success") {
						$('#contactForm').hide();
						$('#successRegMessage').removeClass('hidden');
					}
					else{
						$('#errorRegMessage').removeClass('hidden');
						$('#errorMessage').text(data);
					}
		        	
		        },
		        error: function (request){
					$('#errorRegMessage').removeClass('hidden');
					$('#errorMessage').text(request.responseText);
		        }
		    	
		    });
		}
		
	});
 
 
});