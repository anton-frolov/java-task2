<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Login</title>
	  	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="css/bootstrap.css"> 
		<link rel="stylesheet" href="css/style.css"> 
		<script type="text/javascript" language="javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" language="javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" language="javascript" src="js/script.js"></script>
	</head>

<body>
	<div class="container">
		<div class="row">
			<div class="modal fade in" id="loginModalBox" tabindex="-1" role="dialog" style="display: block;" aria-hidden="false">
				<div class="modal-dialog">
					<div class="modal-content">
					  <div class="modal-header">
						<h4 id="modalHeader" class="modal-title">Форма авторизации</h4>
					  </div>
					  <div class="modal-body">
							<form name="loginForm" id="login" action="login" method="post" class="form-horizontal">
								<div class="mLogin" class="form-group" >
									<div class="form-group">
										<label for="inputUsername" class="col-sm-2 control-label">Логин</label>
										<div class="col-sm-10">
										  <input type="text" class="form-control" id="inputUsername" placeholder="Логин" name="userId" value="">
										</div>
									</div>
									<div class="form-group">
										<label for="inputPassword" class="col-sm-2 control-label">Пароль</label>
										<div class="col-sm-10">
										  <input type="password" class="form-control" id="inputPassword" placeholder="Пароль" name="password" value="">
										</div>
									</div>
									<div class="form-group">
										<label for="rememberMe" class="col-sm-2 control-label"></label>
										<div class="col-sm-10">
											<input type="checkbox" value="" id="rememberMe" value="" name="rememberMe"> Запомнить меня
										</div>
									</div>
								</div>
							</form>	
				
					  </div>
					  <div class="modal-footer">
					  	<a href="./registration.jsp" class="btn btn-default">Регистрация</a>
					  	<button id="guestEtry" type="button" class="btn btn-default" data-dismiss="modal">Гостевой вход</button>
						<button id="closeModal" type="button" class="btn btn-primary" data-dismiss="modal" OnClick="document.loginForm.submit()">Войти</button>
					  </div>
					</div>
				</div>
			</div>
		</div>
	</div>	
	
	<script>
	  $(document).ready(function() {
		$("#loginModalBox").modal('show');
	  });
	</script>
			
</body>
</html>