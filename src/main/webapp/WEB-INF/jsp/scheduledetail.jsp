<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>一日のスケジュール‐My Schedule Plan‐</title>
</head>
<body>
	<%@ include file="../../inc/header.jsp"%>
	<main id="container">

				<h3 class="username">${loginUser.name}さんの１日のスケジュール</h3>
				<h2 class="scheduletitle">${schedule.date}${schedule.title}</h2>
				
			<div class="mainbox">
			<!--エラー時の表示処理-->
			<c:if test="${not empty errorMsg}">
				<p style="color: red;">
					<c:out value="${errorMsg}" />
				</p>
			</c:if>
			<p>🧎‍♂️‍➡️${loginUser.name}さんの1日のスケジュールをたてよう🧎‍♂️</p>
			<form action="ScheduleRegisterServlet" method="post">
			<input type="hidden" name="schedule_id" value="${schedulId}">
				<div class="text-box">
					<p>時間(必須)</p>
				</div>
				<input class="" type="time" name="time" value="" required><br>

				<div class="text-box">
					<p>場所(必須)</p>
				</div>
				<input class="" type="text" name="place" value="" required><br>

				<div class="text-box">
					<p>予定</p>
				</div>
				<input class="" type="text" name="detail" value="" required><br>

				<div class="text-box">
					<p>地図</p>
				</div>
				<input class="" type="text" name="map" value=""><br> <input
					type="hidden" name="action" value="regist"> <input
					class="button_dezain Register-button" type="submit" value="登録">
			</form>
		</div>
		<div class="border-line"></div>

		<div class="Scheduledetail">
			<c:forEach var="scheduledetail" items="${scheduledetailList}">
  <div class="Scheduledetail">
    <div class="time">${scheduledetail.time}</div>
    <div class="place">${scheduledetail.place}</div>
    <div class="detail">${scheduledetail.detail}</div>
    <div class="map">${scheduledetail.map}</div>
    
    
    <form action="" method="post" >
      <input type="hidden" name="detail_id" value="${scheduledetail.detail_id}">
      <input class="changebutton" type="submit" value="変更">
    </form>
    <form action="ScheduleDeleteServlet" method="post">
      <input type="hidden" name="detail_id" value="${scheduledetail.detail_id}">
      <input class="deletebutton" type="submit" value="削除">
    </form>
  </div>
</c:forEach>

		</div>

	</main>
	<%@ include file="../../inc/fotter.jsp"%>
</body>
</html>