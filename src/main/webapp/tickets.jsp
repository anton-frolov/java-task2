<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Список заявок</title>
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
				<li class="active"><a href="index.jsp" id="refFormTicket">Форма обратной связи</a></li>
			  </ul>
			  <ul class="nav navbar-nav navbar-right">
				<li><a href="logout">Выйти</a></li>
			  </ul>
			</div>
		  </div>
		</nav>
		<h3 class="h3 page-header text-center"></h3> 
		
		<div class="container">
			<div class="panel panel-info">
				  <!-- Заголовок контейнера -->
				  <div class="panel-heading">
					<h3 class="panel-title">Список заявок</h3>
				  </div>
				  <div class="panel-body">
					<div class="alert alert-danger hidden" role="alert" id="errorLoadMessage">
						<span class="close">&times;</span>
						<strong>Внимание!</strong> 
						<p>Ошибка при загрузке данных</p>
						<p id="errorMessage"></p>
					</div>
					<div>
						<div class="hidden">
						  <ul class="pagination">
							<li><a href="#">Prev</a></li>
							<li><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">Next</a></li>
						  </ul>
						</div>
						<table class="table table-striped table-hover" id="tickets">
						  <thead>
							<tr>
							  <th>№ п/п</th>
							  <th>Пользователь</th>
							  <th>Получатели</th>
							  <th>Тема</th>
							  <th>Заявка</th>
							</tr>
						  </thead>
						  <tbody>
							
						  </tbody>
						</table>
					</div>
				</div>	
			</div>
		</div>
		
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
			$.ajax({
		    	type: "POST",
		        url: "tickets",
		        data: {
		
				},
		        cache: false,
		        
		        success : function(data){
					var $data = data;
					if ($data.result == "success") {
						for(var i=0; i<$data.tickets.length; i++) {
							var sender = $data.tickets[i].sender.firstName+' '+$data.tickets[i].sender.middleName+' '+$data.tickets[i].sender.lastName;
							if(sender.length>200){
								sender = sender.substr(0,198)+'...'; 
							}
							var theme = $data.tickets[i].theme;
							if(theme.length>100){
								theme = theme.substr(0,98)+'...';
							}
							var body =$data.tickets[i].body;
							if(body.length>200){
								body = body.substr(0,198)+'...'; 
							}
							var recipients = '';
							for(var j=0; j<$data.tickets[i].recipients.length; j++){
								recipients += $data.tickets[i].recipients[j].firstName+' '+$data.tickets[i].recipients[j].middleName+' '+$data.tickets[i].recipients[j].lastName+'; '; 
							}
							if(recipients.length>200){
								recipients = recipients.substr(0,198)+'...'; 
							}
							$('#tickets').find('tbody').append('<tr><td>'
									+$data.tickets[i].id+'</td><td>'
									+sender+'</td><td>'
									+recipients+'</td><td>'
									+theme+'</td><td>'
									+body+'</td></tr>');
						}
					}
					else{
						$('#errorLoadMessage').removeClass('hidden');
						$('#errorMessage').text(data);
					}
		        	
		        },
		        error: function (request){
					$('#errorLoadMessage').removeClass('hidden');
					$('#errorMessage').text(request.responseText);
		        }
		    	
		    });
		});
	</script>
</html>
