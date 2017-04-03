<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<fmt:setLocale value="ja-JP"/>
<title>未入力一覧ページ</title>
</head>
<body>
<div id="content">
	<div class="post">
		<p>未入力の練習を表示します。</p>
		<c:if test="${attendanceList == null}">
			<p>と思ったけど、もう未入力の練習はありません。</p>
		</c:if>
		<c:if test="${attendanceList != null}">
			<p>出欠を入力して登録ボタンを押して下さい。</p>
			<p><span class="error">${f:h(errors.message)}</span></p>
			<hr>
			<c:forEach var="e" items="${attendanceList}" varStatus="status">
				<form action="/attend/mobile/menu/inputAttendance/submit;jsessionid=${f:h(jsessionid)}">
				<c:set value="${e.attendance}" var="attendance" scope="request"/>
				<ul>
					<li>日時：<fmt:formatDate value="${e.practiceRef.model.startDate}" pattern="MM月dd日"/> <fmt:formatDate value="${e.practiceRef.model.startDate}" pattern="HH:mm"/> - <fmt:formatDate value="${e.practiceRef.model.endDate}" pattern="HH:mm"/> </li>
					<li>場所：${f:h(e.practiceRef.model.practicePlace)}</li>
					<li>集合：<fmt:formatDate value="${e.practiceRef.model.gatheringDate}" pattern="HH:mm"/></li>
					<li>場所：${f:h(e.practiceRef.model.gatheringPoint)}
					<li>備考：${f:h(e.practiceRef.model.recital)}</li>
					<li>出欠：<select name="inputAttendance">
									<option ${f:select("attendance", "0")} disabled>未入力</option>
									<option ${f:select("attendance", "1")}>未定</option>
									<option ${f:select("attendance", "2")}>出席</option>
									<option ${f:select("attendance", "3")}>欠席</option>
									<option ${f:select("attendance", "4")}>遅刻</option>
									<option ${f:select("attendance", "5")}>早退</option>
								</select></li>
					<li>備考：<input type="text" value="${e.racital}" name="inputRacital"></input></li>
				</ul>
				<input type="hidden" name="attendanceKey" value="${f:h(e.key)}"/>
				<input type="submit" value="登録" />
				</form>
				<hr>
			</c:forEach>
		</c:if>
		<p class="links"><a href="/attend/mobile/menu/;jsessionid=${f:h(jsessionid)}">メニューへ</a></p>		
	</div>
	<div style="clear: both;">&nbsp;</div>
</div>
</body>
</html>
