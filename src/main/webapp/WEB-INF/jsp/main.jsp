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
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
	<%@ include file="../../inc/header.jsp"%>
	<main id="container">

		<h2 class="text-element">${loginUser.name}さんのスケジュール一覧</h2>


		<div class="mainbox">
			<h3>予定を登録する📖</h3>
			<p class="text-element">🚶予定(タイトル)と日付を入力してください💭</p>
			<div class="text-box">
				<!--エラー時の表示処理-->
				<c:if test="${not empty errorMsg}">
					<p style="color: red;">
						<c:out value="${errorMsg}" />
					</p>
				</c:if>
			</div>
			<form action="MainServlet" method="post">
				<div class="text-box">
					<p class="text-element">日付</p>
				</div>
				<input class="" type="date" name="date" value="" required><br>


				<div class="text-box">
					<p class="text-element">予定(タイトル)</p>
				</div>
				<input class="" type="text" name="title" value="" required><br>

				<div class="text-element">

					<input type="hidden" name="action" value="regist"> <input
						class="button_dezain Register-button" type="submit" value="登録">
				</div>

			</form>
		</div>


		<div class=""></div>

		<!--		<div class="listbox">-->

		<c:forEach var="schedule" items="${scheduleList}">
    <div class="listbox">
        
        <div class="text-block"> 
            <p>
                日付: <c:out value="${schedule.date}" />
            </p>
            <p>
                タイトル: <c:out value="${schedule.title }" />
            </p>
        </div>

        <div class="button-group"> 
            
            <form action="ScheduleDleateServlet" method="post"> 
                <input type="hidden" name="schedule_id" value="${schedule.schedule_id}"> 
                <input class="button_dezain delete-button" type="submit" value="削除">
            </form>

            <form action="ScheduleRegisterServlet" method="post">
                <input type="hidden" name="schedule_id" value="${schedule.schedule_id}"> 
                <input type="hidden" name="title" value="${schedule.title}"> 
                <input type="hidden" name="date" value="${schedule.date}"> 
                <input class="button_dezain action-button" type="submit" value="詳細">
            </form>
            
        </div>
    </div>
</c:forEach>


	</main>

	<%@ include file="../../inc/fotter.jsp"%>
	<script src="js/script.js"></script>
</body>
</html>