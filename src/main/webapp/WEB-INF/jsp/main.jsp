<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>トップ‐My Schedule Plan‐</title>
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
		<h2 class="">${user.name}さんのスケジュール一覧</h2>
		<h3 class="">予定を登録する</h3>
		<div class="text-box">
			<p>日付</p>
		</div>
		<input class="" type="date" name="date" value="" required><br>

		<div class="text-box">
			<p>予定(タイトル)</p>
		</div>
		<input class="" type="taxt" name="schedule" value="" required><br>

		<input type="hidden" name="next" value="signup"> <input
			class="Registerbutton" type="submit" value="登録">
		
			<div class="box-container">
			<p class="">2025/10/03</p>
			<p class="">水族館</p>
			<input type="hidden" name="next" value="deleate"> <input
				class="deleatebutton" type="submit" value="削除">
			</div>
		<form action="MainServlet" method="post">
				<input type="hidden" name="next" value="logout">
				<input class="logoutbutton" type="submit" class="logout-button" value="ログアウト">
			</form>	
	</main>

	<%@ include file="inc/fotter.jsp"%>
</body>
</html>