<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width">
<title>スケジュール‐My Schedule Plan‐</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Questrial&family=Zen+Maru+Gothic&display=swap"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
	<%@ include file="../../inc/header.jsp"%>
	<main id="container">

		<h3 class="username text-element font-size">${loginUser.name}さんの１日のスケジュール</h3>
		<div class="text-element"></div>
		<h2 class="scheduletitle"><span class="schedule-date">${schedule.date}</span><span class="weekday"></span><br>
			${schedule.title}
		</h2>

		<div class="mainbox">
			<!--エラー時の表示処理-->
			<c:if test="${not empty errorMsg}">
				<p style="color: red;">
					<c:out value="${errorMsg}" />
				</p>
			</c:if>
			<p>🧎‍♂️‍➡️${loginUser.name}さんのスケジュールをたてよう🧎‍♂️</p>
			<form id="detail-form" action="ScheduleRegisterServlet" method="post">
				<input type="hidden" name="schedule_id" value="${scheduleId}">
				<div class="text-element"></div>

				<p>-時間(必須)-</p>

				<input class="text" type="time" name="time" value="" required><br>


				<p>-場所(必須)-</p>

				<input class="text" type="text" name="place" value="" required><br>


				<p>-予定-</p>

				<input class="textarea" type="textarea" name="detail" value=""
					required><br>


				<p>-地図-</p>
				<p class="helptext">地図はgoogleの"地図埋め込み"を張り付けてください</p>
				<p class="helptext">⚠地図は変更で反映されません😭</p>
				<input class="text" type="text" name="map" value=""><br>

				<!-- 地図表示用 -->
				<div id="map-container"></div>

				<div class="text-element">
					<input type="hidden" name="action" value="regist"> <input
						type="hidden" name="detail_id" value=""> <input
						class="button_dezain Register-button" type="submit" value="登録">
				</div>
			</form>
			<!--name="detail_id" value=""←リストから取得したID-->
		</div>


		<div class="Scheduledetail scheduledetail-list">
			<c:forEach var="scheduledetail" items="${scheduledetailList}">
				<div class="planbox">
					<div class="Textbeside">

						<div class="time">${scheduledetail.time}</div>
						<div class="place font-size">${scheduledetail.place}</div>
						<div class="text-element"></div>
						<div class="detail">${scheduledetail.detail}</div>
						<div class="text-element"></div>
						<div class="map">${scheduledetail.map}</div>
					</div>

					<div class="ButtonGroup">
						<form action="SchedulemodifyServlet" method="post">
							<input type="hidden" name="detail_id"
								value="${scheduledetail.detail_id}"> <input
								class="button_dezain action-button" type="submit" value="変更">
						</form>
						<form action="ScheduleDleateServlet" method="post">
							<input type="hidden" name="detail_id"
								value="${scheduledetail.detail_id}"> <input
								class="button_dezain delete-button" type="submit" value="削除">
						</form>
					</div>
				</div>
			</c:forEach>

		</div>

	</main>
	<%@ include file="../../inc/fotter.jsp"%>
	<script src="js/script.js"></script>
</body>
</html>