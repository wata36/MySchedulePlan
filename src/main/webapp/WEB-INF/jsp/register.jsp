<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Questrial&family=Zen+Maru+Gothic&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
	 <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
	<%@ include file="../../inc/top_header.jsp"%>
	<main id="container">
		<div class="text-element"></div>
		<h2 class="">新規登録</h2>

		<!--エラー時の表示処理-->
		<c:if test="${not empty errorMsg}">
			<p style="color: red;">
				<c:out value="${errorMsg}" />
			</p>
		</c:if>
		<div class="mainbox">
			<form action="RegisterServlet" method="post">
				<input type="hidden" name="action" value="register"> <br>

				<p>登録したいユーザー情報を入力してください</p>
				<div class="text-element"></div>
				<div class="text-box">
					<p>-ユーザーログインID-</p>
				</div>
				<input class="" type="text" name="loginid"
					placeholder="英数字で入力してください" required> <br>
				<div class="text-element"></div>
				<div class="text-box">
					<p>-名前-</p>
				</div>
				<input class="" type="text" name="name" value="" required><br>
				<div class="text-element"></div>
				<div class="text-box">
					<p>-パスワード-</p>
					<p class="helptext">［半角英数字12文字以上で入力してください］</p>
				</div>
				<input class="" type="password" name="password" placeholder=""
					required><br>
				<div class="text-element"></div>
				<div class="text-box">
					<p>-確認用パスワード-</p>
				</div>
				<input class="" type="password" name="password2" value="" required><br>

				<div class="text-element">
					<input class="button_dezain action-button" type="submit"
						value="新規登録する">
				</div>
			</form>
		</div>

	</main>

	<%@ include file="../../inc/fotter.jsp"%>

<script src="path/to/your/external.js"></script>
</body>
</html>