<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スケジュール‐My Schedule Plan‐</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Questrial&family=Zen+Maru+Gothic&display=swap"
	rel="stylesheet">
</head>
<body>
	<%@ include file="../../inc/header.jsp"%>
	<main id="container">

			<h3 class="username text-element">${loginUser.name}さんの１日のスケジュール</h3>
			<div class="text-element"></div>
			<h2 class="scheduletitle">${schedule.date}<br> ${schedule.title}</h2>
				
			<div class="mainbox">
			<!--エラー時の表示処理-->
			<c:if test="${not empty errorMsg}">
				<p style="color: red;">
					<c:out value="${errorMsg}" />
				</p>
			</c:if>
			<p>🧎‍♂️‍➡️${loginUser.name}さんのスケジュールをたてよう🧎‍♂️</p>
			<form action="ScheduleRegisterServlet" method="post">
			<input type="hidden" name="schedule_id" value="${schedulId}">
			<div class="text-element"></div>
				<div class="text-box">
					<p>-時間(必須)-</p>
				</div>
				<input class="text" type="time" name="time" value="" required><br>

				<div class="text-box">
					<p>-場所(必須)-</p>
				</div>
				<input class="text" type="text" name="place" value="" required><br>

				<div class="text-box">
					<p>-予定-</p>
				</div>
				<input class="textarea" type="textarea" name="detail" value="" required><br>

				<div class="text-box">
					<p>-地図-</p>
				</div>
				<input class="text" type="text" name="map" value=""><br> 
				
				<div class="text-element">
				<input type="hidden" name="action" value="regist"> <input
					class="button_dezain Register-button" type="submit" value="登録">
				</div>
			</form>
	
				</div>
		

		<div class="Scheduledetail">
			<c:forEach var="scheduledetail" items="${scheduledetailList}">
 <div class="planbox">
  <div class="Textbeside">

    <div class="time">${scheduledetail.time}</div>
    <div class="place">${scheduledetail.place}</div>
    <div class="detail">${scheduledetail.detail}</div>
    <div class="map">${scheduledetail.map}</div>
    </div>
       
    <div class="ButtonGroup">
    <form action="" method="post" >
      <input type="hidden" name="detail_id" value="${scheduledetail.detail_id}">
      <input class="button_dezain action-button" type="submit" value="変更">
    </form>
    <form action="ScheduleDleateServlet" method="post">
      <input type="hidden" name="detail_id" value="${scheduledetail.detail_id}">
      <input class="button_dezain delete-button" type="submit" value="削除">
    </form>
     </div>
    </div>

</c:forEach>

		</div>

	</main>
	<%@ include file="../../inc/fotter.jsp"%>
</body>
</html>