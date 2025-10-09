<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン‐My Schedule Plan-</title>
</head>
<body>
	<%@ include file="inc/header.jsp"%>
	<main id="container">
		<!--エラー時の表示処理-->
		<c:if test="${not empty errorMsg}">
			<p style="color: red;">
				<c:out value="${errorMsg}" />
			</p>
		</c:if>

		<h1 class="">My Schedule Plan</h1>
		<form action="MainServlet" method="post">
			<div class="text-box">
				<p>ログインID</p>
			</div>
			<input class="" type="text" name="loginid" value="" required><br>

			<div class="text-box">
				<p>パスワード</p>
			</div>
			<input class="" type="password" name="password" value="" required><br>
			<!--		 	<input type="" name="next" value=""><br>-->

			<div class="">
			<input type="hidden" name="next" value="signup">
				<input class="login-button" type="submit" value="ログイン">
		</form>

		<form action="RegisterServlet" method="post">
			<input type="hidden" name="next" value="signup"> <input
				class="signup-button" type="submit" value="新規登録">
		</form>
		</div>
	</main>

	<%@ include file="inc/fotter.jsp"%>
</body>
</html>