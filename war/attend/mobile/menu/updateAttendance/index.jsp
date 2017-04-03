<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<fmt:setLocale value="ja-JP"/>
<title>出欠更新ページ</title>
</head>
<body>
	出席を入力して、更新ボタンを押してください。
	<p><span class="error">${f:h(errors.inputError)}</span></p>
	<hr>
	<form action="/attend/mobile/menu/updateAttendance/submit;jsessionid=${f:h(jsessionid)}">
	<c:set value="${attend.attendance}" var="attendance" scope="request"/>
	<ul>
		<li>日時：<fmt:formatDate value="${attend.practiceRef.model.startDate}" pattern="MM月dd日"/> <fmt:formatDate value="${attend.practiceRef.model.startDate}" pattern="HH:mm"/> - <fmt:formatDate value="${attend.practiceRef.model.endDate}" pattern="HH:mm"/> </li>
		<li>場所：${f:h(attend.practiceRef.model.practicePlace)}</li>
		<li>集合：<fmt:formatDate value="${attend.practiceRef.model.gatheringDate}" pattern="HH:mm"/></li>
		<li>場所：${f:h(attend.practiceRef.model.gatheringPoint)}
		<li>備考：${f:h(attend.practiceRef.model.recital)}</li>
		<li>出欠：<select name="inputAttendance">
						<option ${f:select("attendance", "0")} disabled>未入力</option>
						<option ${f:select("attendance", "1")}>未定</option>
						<option ${f:select("attendance", "2")}>出席</option>
						<option ${f:select("attendance", "3")}>欠席</option>
						<option ${f:select("attendance", "4")}>遅刻</option>
						<option ${f:select("attendance", "5")}>早退</option>
					</select></li>
		<li>備考：<input type="text" name="inputRacital" value="${attend.racital}"></input></li>
	</ul>
	<input type="hidden" name="attendanceKey" value="${f:h(attend.key)}"/>
	<input type="submit" name="submit" value="更新" />
	<input type="hidden" name="date" value="${f:h(date)}"/>		
	<input type="submit" name="back" value="戻る" />	
	</form>
</body>
</html>
