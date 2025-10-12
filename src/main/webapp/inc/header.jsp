<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Questrial&display=swap" rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<!--静的インクルード-->

<header class = "header">
<div class="header-container">
    <h3 class="questrial-regular">My Schedule Plan</h3>
    <form action="MainServlet" method="post">
	<input class="button_dezain logout-button" type="submit" value="ログアウト">
</form>
</div>
</header>