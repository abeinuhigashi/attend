<%@page import="slim3.model.Attendance"%>
<%@page import="slim3.model.Practice"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="atd" uri="http://neeton.org/tags/atd"%>

<%
  response.setHeader("Expires", "-1");
  response.setHeader("Pragma","no-cache");
  response.setHeader("Cache-Control","no-cache");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>出席入力ページ</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="/js/jquery-1.5.min.js"></script>
</head>
<body>
<script type="text/javascript">

	function submitAttendanceForm(allFlg , index){

		document.attendanceForm.allFlg.value = allFlg;
		document.attendanceForm.index.value = index;

		return true;

	}

</script>
<div id="wrapper">
	<jsp:include page="/attend/common/header.jsp" />
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				<jsp:include page="/attend/common/sidebar.jsp" />
				<div id="content">
					<div class="post">
						<h2>${f:h(currentYear)}年${f:h(currentMonth)}月</h2>
						<c:if test="${attendanceList == null}">
							<p>練習日程がまだ登録されていません。</p>
						</c:if>
						<c:if test="${attendanceList != null}">
							<p>出席情報を入力してください</br>過去の日程のものは修正出来ません。マネージャーにお問い合わせ下さい。</p>
							<p><span class="error">${f:h(errors.cannotUpdate)}</span></p>
							<form method="post" action="/attend/inputAttendance/submit" name="attendanceForm">
							<atd:attendForEach var="e" items="${attendanceList}" varStatus="status">
								<c:set value="${e.attendance}" var="attendance" scope="request"/>
								<ul>
									<li>日時：<fmt:formatDate value="${e.practiceRef.model.startDate}" pattern="MM月dd日"/> <fmt:formatDate value="${e.practiceRef.model.startDate}" pattern="HH:mm"/> - <fmt:formatDate value="${e.practiceRef.model.endDate}" pattern="HH:mm"/> ＠${f:h(e.practiceRef.model.practicePlace)}</li>
									<li>集合：<fmt:formatDate value="${e.practiceRef.model.gatheringDate}" pattern="HH:mm"/>＠${f:h(e.practiceRef.model.gatheringPoint)}</li>
									<li>備考：${f:h(e.practiceRef.model.recital)}</li>
									<li>
									<table>
										<tr>
											<atd:checkPast date="${e.practiceRef.model.startDate}" >
												<atd:future>
													<td>
														<select name="attendanceArray">
															<option ${f:select("attendance", "0")}>未入力</option>
															<option ${f:select("attendance", "1")}>未定</option>
															<option ${f:select("attendance", "2")}>出席</option>
															<option ${f:select("attendance", "3")}>欠席</option>
															<option ${f:select("attendance", "4")}>遅刻</option>
															<option ${f:select("attendance", "5")}>早退</option>
														</select>
													</td>
													<td>備考<input type="text" class="text2" name="racitalArray" value="${e.racital}"></input></td>
													<td><input type="submit" class="attendance-update"  onclick="submitAttendanceForm(false, ${future.idx});" value="更新" /></td>
													<input type="hidden" name="keyArray" value="${f:h(e.key)}"/>
													<input type="hidden" name="memberKeyArray" value="${f:h(e.memberKey) }"/>
													<input type="hidden" name="practiceKeyArray" value="${f:h(e.practiceKey) }"/>
												</atd:future>
												<atd:past>
													<td>
														<select name="attendanceArray" disabled>
															<option ${f:select("attendance", "0")}>未入力</option>
															<option ${f:select("attendance", "1")}>未定</option>
															<option ${f:select("attendance", "2")}>出席</option>
															<option ${f:select("attendance", "3")}>欠席</option>
															<option ${f:select("attendance", "4")}>遅刻</option>
															<option ${f:select("attendance", "5")}>早退</option>
														</select>
													</td>
													<td>備考<input type="text" class="text2-disabled" name="racitalArray" value="${e.racital}" disabled></input></td>
													<td></td>
												</atd:past>
											</atd:checkPast>
										</tr>
									</table>
									</li>
								</ul>
							</atd:attendForEach>
							<c:if test="${future.idx > 0}">
								<input type="hidden" name="index" />
								<input type="hidden" name="allFlg" />
								<input type="hidden" name="currentDate" value="${currentDate}" />
								<input type="submit" class="attendance-updateAll" onclick="submitAttendanceForm(true, 99);" value="一括更新" />
							</c:if>
							</form>
						</c:if>
						<p class="links"><a href="/attend/inputAttendance/?date=${f:h(beforeDate)}">前の月へ</a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;<a href="/attend/inputAttendance/?date=${f:h(nextDate)}"">次の月へ</a></p>
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
