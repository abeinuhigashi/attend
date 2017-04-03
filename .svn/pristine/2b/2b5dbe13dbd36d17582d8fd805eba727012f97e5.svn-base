<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="atd" uri="http://neeton.org/tags/atd"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>出席率確認</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<div id="wrapper">
	<jsp:include page="/attend/common/header.jsp" />
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				<jsp:include page="/attend/manage/attendance/sidebar.jsp" />
				<div id="content">
					<div class="post">
						<h2>出席率の確認</h2>
						<p>表示したい人と期間を選んで下さい。</p>						
						<form method="get" action="/attend/manage/attendance/member/disp">
						<table>
						<tr>
						<td>
							<select name="id">
								<c:forEach var="member" items="${memberList}">
								<c:set var="memberId" value="${member.id}"/>
								<option ${f:select("id", memberId)}>${f:h(member.lastName)} ${f:h(member.firstName)}</option>
								</c:forEach>
							</select>
							<select name="term">
								<c:forEach var="date" items="${dateList}" varStatus="status">
								<c:set var="index" value="${status.index}"/>
								<c:if test="${!status.first}">
								<option ${f:select("term", index-1	)}><fmt:formatDate value="${dateList[index-1]}" pattern="yyyy年MM月dd日"/> - <fmt:formatDate value="${date}" pattern="yyyy年MM月dd日"/></option>
								</c:if>
								</c:forEach>
							</select>
						</td>
						<td>
							<input type="submit" id="submit" value="検索" />
						</td>
						</tr>
						</table>
						</form>
						<hr/>
							<c:if test="${fromDate != null}">
						<p>出席率は以下の通りです。遅刻早退は0.5としてカウントしています。</p>
						<table class="disp-table">
							<tr>
								<th>集計期間</th> 
								<th>練習回数</th> 
								<th>出席回数</th> 
								<th>遅刻早退</th> 
								<th>出席率</th> 
							</tr>
							<tr>
								<td><fmt:formatDate value="${fromDate}" pattern="yyyy年MM月dd日"/> - <fmt:formatDate value="${toDate}" pattern="MM月dd日"/></td>
								<td>${f:h(numPractice)}</td>
								<td>${f:h(numFullAttend)}</td>
								<td>${f:h(numPartAttend)}</td>
								<td><fmt:formatNumber value="${f:h(ratio)}" pattern="##.#%" /></td>
							</tr>
						</table>
						</c:if>
						<c:if test="${attendanceList != null}">
						<hr/>
						<p>具体的な出欠は以下の通りです。（未入力の場合表示されない事があります。）</p>
						<table class="disp-table">
							<tr>
								<th>練習日</th> 
								<th>場所</th> 
								<th>出欠</th>
							</tr>
							<c:forEach var="attendance" items="${attendanceList}" varStatus="status">
							<tr class="attendance${f:h(attendance.attendance)}">
								<td><fmt:formatDate value="${attendance.practiceRef.model.startDate}" pattern="yyyy/MM/dd HH:mm"/> - <fmt:formatDate value="${attendance.practiceRef.model.endDate}" pattern="HH:mm"/></td>
								<td>${f:h(attendance.practiceRef.model.practicePlace)}</td>
								<td><atd:atdOption attendance="${f:h(attendance.attendance)}" memberKey="" practiceKey="" disable="true"/></td>
							</tr>
							</c:forEach>
						</table>
						</c:if>
					</div>
					<div style="clear: both;">&nbsp;</div>
				</div>
				<!-- end #content -->
				<div style="clear: both;">&nbsp;</div>
			</div>
		</div>
	</div>
	<!-- end #page -->
</div>
<jsp:include page="/attend/common/footer.jsp" />
</body>
</html>

