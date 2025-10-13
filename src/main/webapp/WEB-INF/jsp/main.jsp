<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>トップ‐My Schedule Plan‐</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Questrial&family=Zen+Maru+Gothic&display=swap"
	rel="stylesheet">
</head>
<body>
	<%@ include file="../../inc/header.jsp"%>
	<main id="container">
		
		<h2 class="">${loginUser.name}さんのスケジュール一覧</h2>
		<h3 class="">予定を登録する💭</h3>
		<div class="mainbox">
		
		<p>📖予定の日付、タイトルを入力してください🖍️</p>
		<!--エラー時の表示処理-->
		<c:if test="${not empty errorMsg}">
			<p style="color: red;">
				<c:out value="${errorMsg}" />
			</p>
		</c:if>
		<div class="text-box">
			<p>日付</p>
		</div>
		<input class="" type="date" name="date" value="" required><br>

		<div class="text-box">
			<p>予定(タイトル)</p>
		</div>
		<input class="" type="taxt" name="schedule" value="" required><br>

		<input type="hidden" name="next" value="signup"> <input
			class="button_dezain Registerbutton" type="submit" value="登録">
		</div>
			<div class="box-container">
			<p class="">2025/10/03</p>
			<p class="">水族館</p>
			<input type="hidden" name="next" value="deleate"> <input
				class="button_dezain deleatebutton" type="submit" value="削除">
			</div>
			</form>	
	</main>

	<%@ include file="../../inc/fotter.jsp"%>
</body>
</html>