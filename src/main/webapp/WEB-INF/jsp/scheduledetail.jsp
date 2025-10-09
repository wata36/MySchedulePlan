<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>一日のスケジュール‐My Schedule Plan‐</title>
</head>
<body>
	<%@ include file="inc/header.jsp"%>
	<main id="container">

		<h3 class="username">${user.name}さんのスケジュール一覧</h3>
		<h2 class="scheduletitle">水族館${schedule.date}${schedule.title}</h2>

		<!--エラー時の表示処理-->
		<c:if test="${not empty errorMsg}">
			<p style="color: red;">
				<c:out value="${errorMsg}" />
			</p>
		</c:if>

		<div class="text-box">
			<p>時間</p>
		</div>
		<input class="" type="time" name="time" value="" required><br>

		<div class="text-box">
			<p>場所</p>
		</div>
		<input class="" type="taxt" name="place" value="" required><br>

		<div class="text-box">
			<p>予定</p>
		</div>
		<input class="" type="taxt" name="detail" value="" required><br>

		<div class="text-box">
			<p>地図</p>
		</div>
		<input class="" type="taxt" name="map" value=""><br>

		<input type="hidden" name="next" value="signup"> <input
			class="Registerbutton" type="submit" value="登録">

		<div class="border-line"></div>

		<div class="Scheduledetail">
			<div class="time">${scheduledetail.time}7:30</div>
			<div class="place">${scheduledetail.place}〇〇ホテル</div>
			<div class="detail">${scheduledetail.detail}朝食を食べる</div>
			<div class="map">${scheduledetail.map}ちず</div>
			<input type="hidden" name="next" value="signup"> <input
				class="changebutton" type="submit" value="変更"> <input
				type="hidden" name="next" value="signup"> <input
				class="deleatebutton" type="submit" value="削除">
		</div>

	</main>
	<%@ include file="inc/fotter.jsp"%>
</body>
</html>