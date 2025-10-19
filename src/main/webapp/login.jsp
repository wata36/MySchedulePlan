<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン‐My Schedule Plan-</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Questrial&family=Zen+Maru+Gothic&display=swap"
	rel="stylesheet">
</head>
<body>
	<%@ include file="inc/top_header.jsp"%>
	<main id="container">
		<!--エラー時の表示処理-->
		<c:if test="${not empty errorMsg}">
			<p style="color: red;">
				<c:out value="${errorMsg}" />
			</p>
		</c:if>
		<div class="mainbox">
			<h1 class="questrial-regular">My Schedule Plan</h1>
			<form action="MainServlet" method="post">
				<div class="text-box">
					<p class="text-element">ログインID</p>
				</div>
				<input class="" type="text" name="loginid" value="" required><br>

				<div class="text-box">
					<p class="text-element">パスワード</p>
				</div>
				<input class="" type="password" name="pass" value="" required><br>

				<div class="button-group text-element">

					<form action="RegisterServlet" method="post">
						<input type="hidden" name="next" value="signup"> <input
							class="button_dezain action-button" type="submit" value="新規登録">
					</form>

					<input class="button_dezain login-button" type="submit"
						value="ログイン">

				</div>
	</main>

	<%@ include file="inc/fotter.jsp"%>
</body>
</html>