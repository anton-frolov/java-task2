<%@ page language="java" contentType="text/html; charset=UTF-8" 
                                              pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
         "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Страница регистрации</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/bootstrap.css"> 
	<link rel="stylesheet" href="css/style.css"> 
	<script type="text/javascript" language="javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" language="javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" language="javascript" src="js/script.js"></script>
	
  </head>
	<body>
		<h1 class="h2 page-header text-center">Форма регистрации</h1>
		<div class="container">
			<div class="row">
				<div class="col-sm-6 col-sm-offset-3">
					<div class="panel panel-info">
					  <div class="panel-heading">
						<h3 class="panel-title">Данные для регистрации пользователя</h3>
					  </div>
					  <div class="panel-body">
						<div class="alert alert-success hidden" role="alert" id="successRegMessage">
							<strong>Внимание!</strong> 
							<p>Ваша регистрация прошла успешно.</p>
							<p><a href="index.jsp">Перейти на сайт</a></p>	
						</div>
						<div class="alert alert-danger hidden" role="alert" id="errorRegMessage">
							<span class="close">&times;</span>
							<strong>Внимание!</strong> 
							<p>Ошибка при регистрации</p>
							<p id="errorMessage"></p>
						</div>

						
						<form id="registrationForm" class="form-horizontal">
							<div class="mReg" class="form-group" >
								<div class="form-group">
									<label for="inputUsername" class="col-sm-2 control-label">Логин</label>
									<div class="col-sm-10">
									  <input type="text" class="form-control" id="inputUsername" required="required"placeholder="Введите логин под которым будете входить" name="userId" value="" maxlength="254">
									  <span class="glyphicon form-control-feedback"></span>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-2 control-label">Пароль</label>
									<div class="col-sm-10">
									  <input type="password" class="form-control" id="inputPassword" required="required" placeholder="Введите пароль" name="password" value="" maxlength="254">
									  <span class="glyphicon form-control-feedback"></span>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail" class="col-sm-2 control-label">email</label>
									<div class="col-sm-10">
										<input type="email" class="form-control" id="inputEmail" required="required" placeholder="Введите email для обратной связи" name="userEmail" value="" maxlength="30">
										<span class="glyphicon form-control-feedback"></span>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-default">Сохранить</button>
								<a href="./index.jsp" class="btn btn-default">Закрыть</a>
							 </div>
						</form>	
					  </div>
					  
					</div>
				</div>
			</div>
		</div>	
		<script type="text/javascript">
			$(function(){
				$(".close").click(function(){
					$('#errorMessage').text('');
					$('#errorRegMessage').addClass('hidden');
				});
			});
		</script>
	</body>	
</html>