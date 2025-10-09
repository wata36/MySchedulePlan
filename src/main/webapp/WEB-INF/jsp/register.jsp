<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<%@ include file="inc/header.jsp"%>
	<main id="container">

		<h1 class="">新規ユーザー登録</h1>

		<!--エラー時の表示処理-->
		<c:if test="${not empty errorMsg}">
			<p style="color: red;">
				<c:out value="${errorMsg}" />
			</p>
		</c:if>
		<form action="SignUpServlet" method="post" name="next" value="check">
			<input type="hidden" name="next" value="check"> <br>
			<div class="text-box">
				<p>ユーザーログインID</p>
			</div>
			<input class="" type="text" name="loginid" placeholder="英数字で入力してください"
				required> <br>

			<div class="text-box">
				<p>名前</p>
			</div>
			<input class="" type="text" name="name"
				value="${form.email}" required><br>

			<div class="text-box">
				<p>パスワード</p>
				<p class="helptext">［半角英数字12文字以上で入力してください］</p>
			</div>
			<input class="" type="password" name="passward" placeholder=""
				required><br>

			<div class="text-box">
				<p>確認用パスワード</p>
			</div>
			<input class="" type="password" name="passward2" value=""
				required><br>
			<div class="">
				<input class="action-button" type="submit" value="新規登録する">
		</form>

		<form action="loginServlet" method="post" name="next" value="back">
			<input type="hidden" name="next" value="back"> <input
				class="returnbutton" type="submit" value="戻る">
		</form>
		</div>
	</main>

	<%@ include file="inc/fotter.jsp"%>
</body>
</html>