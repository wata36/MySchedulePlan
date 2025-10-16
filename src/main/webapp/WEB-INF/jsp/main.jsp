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
		<div class="mainbox">
			<h3 class="">予定を登録する</h3>
			<p>🚶予定(タイトル)と日付を入力してください💭</p>
			<div class="text-box">
				<!--エラー時の表示処理-->
				<c:if test="${not empty errorMsg}">
					<p style="color: red;">
						<c:out value="${errorMsg}" />
					</p>
				</c:if>
				<form action="MainServlet" method="post">
					<div class="text-box">
						<p>日付</p>
					</div>
					<input class="" type="date" name="date" value="" required><br>

					<div class="text-box">
						<p>予定(タイトル)</p>
					</div>
					<input class="" type="text" name="title" value="" required><br>

					<input type="hidden" name="action" value="regist"> <input
						class="button_dezain Register-button" type="submit" value="登録">
				</form>
			</div>
		</div>


		<div class=""></div>

		<div class="">

			<c:forEach var="schedule" items="${scheduleList}">
				<p>
					<c:out value="${schedule.date}" />
					：
					<c:out value="${schedule.title }" />
				</p>
				<form action="MainServlet" method="post">
					<input type="hidden" name="actionType" value="delete">
					
					<!-- 削除する scheduleId も必要になる -->
					<input type="hidden" name="schedule_Id"
						value="${schedule.schedule_id}"> <input
						class="button_dezain deleatebutton" type="submit" value="削除">
				</form>
				<form action="ScheduleRegisterServlet" method="post">
					<input type="hidden" name="schedule_id" value="${schedule.schedule_id}"> <input
						class="button_dezain" type="submit" value="詳細">
				</form>
			</c:forEach>

		</div>
	</main>

	<%@ include file="../../inc/fotter.jsp"%>
</body>
</html>