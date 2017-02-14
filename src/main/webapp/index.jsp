<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Форма обратной связи</title>
	  	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<link rel="stylesheet" href="css/bootstrap.min.css"> 
		<!-- <link rel="stylesheet" href="data:text/css;charset=utf-8," data-href="css/bootstrap-theme.min.css" id="bs-theme-stylesheet">  -->
		<link rel="stylesheet" href="css/bootstrap-multiselect.css" type="text/css"/>
		<link rel="stylesheet" href="css/style.css">
		<!-- <script type="text/javascript" language="javascript" src="js/jquery.min.js"></script> -->
		<script type="text/javascript" language="javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
		<script type="text/javascript" language="javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" language="javascript" src="js/bootstrap-multiselect.js"></script>
		<script type="text/javascript" language="javascript" src="js/script.js"></script>
		
		
	</head>
	<body>
		<noscript>
		    <div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
		        Your web browser must have JavaScript enabled
		        in order for this application to display correctly.
		    </div>
		</noscript>
		  
		<nav role="navigation" class="navbar navbar-default navbar-fixed-top">
		  <div class="container-fluid">
			<div class="navbar-header">
			  <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			  </button>
			  <a href="#" class="navbar-brand">Задание 2</a>
			</div>
			<!-- Collection of nav links and other content for toggling -->
			<div id="navbarCollapse" class="collapse navbar-collapse">
			  <ul class="nav navbar-nav">
				<li class="active"><a href="tickets.jsp" id="refListTickets">Список заявок</a></li>
			  </ul>
			  <ul class="nav navbar-nav navbar-right">
				<li><a href="logout">Выйти</a></li>
			  </ul>
			</div>
		  </div>
		</nav>
	
	<h3 class="h3 page-header text-center"></h3> 
		  <div class="container">
			<div class="row">
			  <div class="col-lg-8 col-lg-offset-2">
				<div class="panel panel-info">
				  <!-- Заголовок контейнера -->
				  <div class="panel-heading">
					<h3 class="panel-title">Форма обратной связи</h3>
				  </div>
				  <div class="panel-body">
					<div class="alert alert-success hidden" role="alert" id="successMessage">
					  <strong>Внимание!</strong> Ваше сообщение успешно отправлено.
					</div>
	
					<form id="taskForm">
					  <div class="row">
						<div class="col-md-4">
						  <div class="form-group has-feedback">
							<label for="firstname" class="control-label">Ваша фамилия:</label>
							<input type="text" id="firstname" name="firstname" class="form-control" required="required" value="" placeholder="Например, Иванов" minlength="2" maxlength="50">
							<span class="glyphicon form-control-feedback"></span>
						  </div>
						</div>
						<div class="col-md-4">
							<div class="form-group has-feedback">
								<label for="middlename" class="control-label">имя:</label>
								<input type="text" id="middlename" name="middlename" class="form-control" required="required"  value="" placeholder="Иван" maxlength="50">
								<span class="glyphicon form-control-feedback"></span>
							</div>
						</div>
						
						<div class="col-md-4">
							<div class="form-group has-feedback">
								<label for="lastname" class="control-label">отчество:</label>
								<input type="text" id="lastname" name="lastname" class="form-control" value="" placeholder="Иванович" maxlength="50">
								<span class="glyphicon form-control-feedback"></span>
							</div>
						</div>
					  </div>
					<div class="row">  
						<div class="col-md-3">
						  <div class="has-feedback">
							<label for="recipients-select" class="control-label">Получатели:</label>
							<select id="recipients-select" multiple="multiple" required="required">
								
							</select>
						  </div>
						</div>
					</div>
					<div class="form-group">  
					<div  class="has-feedback">
						<label for="theme" class="control-label">Тема:</label>
						<textarea id="theme" class="form-control" rows="1" placeholder="Введите тему сообщения" maxlength="254" required="required"></textarea>
					</div>

					<div  class="has-feedback">
						<label for="message" class="control-label">Введите сообщение:</label>
						<textarea id="message" class="form-control" rows="5" placeholder="Введите сообщение до 2000 символов" maxlength="2000" required="required"></textarea>
					</div>
					</div>
					<div class="form-group">  
						<button id="sendTaskBtn" type="button" class="btn btn-primary pull-right">Отправить сообщение</button>
					</div>
					</form>
				  </div>
				</div>
		 
			  </div>
			</div>
		  </div>
		  
		<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
				  <div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 id="modalHeader" class="modal-title"></h4>
				  </div>
				  <div class="modal-body">
					<div id="error" class="col-sm-12" style="color: #ff0000; margin-top: 5px; margin-bottom: 5px;"></div>
				  </div>
				  <div class="modal-footer">
					<button id="closeModal" type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
				  </div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#recipients-select').multiselect({
				nonSelectedText: 'Выберите получателей',
		        includeSelectAllOption: true,
		        enableFiltering: true
			});
			$.ajax({
		    	type: "POST",
		        url: "persons",
		        data: {
		
				},
		        cache: false,
		        
		        success : function(data){
					var $data = data;
					if ($data.result == "success") {
						var options = '';
						for(var i=0; i<$data.persons.length; i++) {
							var sender = $data.persons[i].firstName+' '+$data.persons[i].middleName
							if(!$data.persons[i].lastName==undefined){
								sender +=' '+$data.persons[i].lastName;
							};
							if(!$data.persons[i].email==undefined){	
								sender +=' '+$data.persons[i].email;
							};
							options += '<option value="'+$data.persons[i].id+'">\''+sender+'\'</option>'; 
						}
						$('#recipients-select').append(options);
						$('#recipients-select').multiselect('rebuild');
					}
					else{
						$('#errorLoadMessage').removeClass('hidden');
						$('#errorMessage').text(request.responseText);
					}
		        	
		        },
		        error: function (request){
					$('#errorLoadMessage').removeClass('hidden');
					$('#errorMessage').text(request.responseText);
		        }
		    	
		    });
		});
		
		$('#sendTaskBtn').click( function() {
			
			var formValid = true;
			
			$('#taskForm input,select,textarea').each(function() {

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
				
				var firstname = $("#firstname").val();
				var middlename = $("#middlename").val();
				var lastname = $("#lastname").val();
				var recipients = '';
				var theme = $('#theme').val();
				var message = $('#message').val();
				
				for(var i=0; i<$('#recipients-select')[0].selectedOptions.length; i++) {
					recipients += $('#recipients-select')[0].selectedOptions[i].value+';'
				}
			    $.ajax({
			    	type: "POST",
			        url: "tickets",
			        data: {
			        	type: "add",
						firstname: firstname,
						middlename: middlename,
						lastname: lastname,
						recipients: recipients,
						theme: theme,
						message: message
					},
			        cache: false,
			        
			        success : function(data){
						var $data =  data;
						if ($data.result == "success") {
							$('#contactForm').hide();
							$('#successMessage').removeClass('hidden');
						}
						else{
							$('#successMessage').removeClass('hidden');
							$('#errorMessage').text(request.responseText);
						}
			        	
			        },
			        error: function (request){
						$('#successMessage').removeClass('hidden');
						$('#errorMessage').text(request.responseText);
			        }
			    	
			    });
			}
		});
	</script>
</html>
