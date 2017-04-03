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

<script>
$(document).ready(function(){
	$(".submitButton").click(function(){
		var tr = $(this).parents(".practice");
		var inputAttendance = tr.find("select[name=inputAttendance]");
		var inputRacital = tr.find("input[name=inputRacital]");
		var memberKey = tr.find("input[name=memberKey]");
		var practiceKey = tr.find("input[name=practiceKey]");

		var msg = "inputAttendance=" + inputAttendance.val() + "&inputRacital=" + inputRacital.val() + "&memberKey=" + memberKey.val() + "&practiceKey=" + practiceKey.val();
		$.ajax({
		   type: "POST",
		   url: "/attend/async/attendance/update",
		   data: msg,
		   context: $(this),
		   success: function(){
			   $(this).parents(".practice").animate({opacity: "hide" }, "slow");
		   }
		});
		
	});	
	
	$('select[name=inputAttendance]').change(function(){
		var tr = $(this).parents(".practice");
		if($(this).val() == 1 || $(this).val() == 4 ||$(this).val() == 5){
			var inputRacital = tr.find("input[name=inputRacital]");
			if(inputRacital.val() == ''){
				cannnotSubmit(tr);
			} else {
				canSubmit(tr);
			}
		} else {
			canSubmit(tr);
		}
	});
	
	$("input[name=inputRacital]").change(function(){
		var tr = $(this).parents(".practice");
		var inputAttendance = tr.find("select[name=inputAttendance]");
		if(inputAttendance.val() != 0){
			if($(this).val() == ''){
				if(inputAttendance.val() == 1 || inputAttendance.val() == 4 || inputAttendance.val() == 5){
					cannnotSubmit(tr);
				}
			} else {
				canSubmit(tr);
			}
		}
	});
	
	function cannnotSubmit(parent){
		parent.find(".ajaxWarnMessage").text("時間・理由を入れてちょ。");
		parent.find(".submitButton").attr("disabled", "disabled");					
		parent.find(".submitButton").attr("id", "submitdisabled");		
	};
	
	function canSubmit(parent){
		parent.find(".ajaxWarnMessage").text(" ");
		parent.find(".submitButton").removeAttr("disabled");
		parent.find(".submitButton").attr("id", "submit");		
	};
	
});
</script> 

</head>
<body>
<div id="wrapper">
	<jsp:include page="/attend/common/header.jsp" />
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				<jsp:include page="/attend/common/sidebar.jsp" />
				<div id="content">
					<div class="post">
						<h2>未入力出欠一覧</h2>
						<p>未入力の練習を表示してます。<br/>出欠を入力して登録ボタンを押して下さい。</p>
						<hr/>
						<c:if test="${attendanceList == null}">
							<p>と思ったけど、もう未入力の練習はありません！ステキ！</p>
						</c:if>
						<c:if test="${attendanceList != null}">
							<p><span class="error">${f:h(errors.message)}</span></p>
<!--							<form action="/attend/inputAttendance/blank/submit">  -->
							<form>
							<c:forEach var="e" items="${attendanceList}" varStatus="status">
								<c:set value="${e.attendance}" var="attendance" scope="request"/>
								<ul class="practice">
									<li>日時：<fmt:formatDate value="${e.practiceRef.model.startDate}" pattern="MM月dd日"/> <fmt:formatDate value="${e.practiceRef.model.startDate}" pattern="HH:mm"/> - <fmt:formatDate value="${e.practiceRef.model.endDate}" pattern="HH:mm"/> ＠${f:h(e.practiceRef.model.practicePlace)}</li>
									<li>集合：<fmt:formatDate value="${e.practiceRef.model.gatheringDate}" pattern="HH:mm"/>＠${f:h(e.practiceRef.model.gatheringPoint)}</li>
									<li>備考：${f:h(e.practiceRef.model.recital)}</li>
									<li>
									<table>
										<tr>
											<td>
												<select name="inputAttendance">
													<option ${f:select("attendance", "0")} disabled>未入力</option>
													<option ${f:select("attendance", "1")}>未定</option>
													<option ${f:select("attendance", "2")}>出席</option>
													<option ${f:select("attendance", "3")}>欠席</option>
													<option ${f:select("attendance", "4")}>遅刻</option>
													<option ${f:select("attendance", "5")}>早退</option>
												</select>
											</td>
											<td>備考：<input type="text" value="${e.racital}" name="inputRacital"></input></td>
											<td><input type="hidden" name="attendanceKey" value="${f:h(e.key)}"/></td>
											<td><input type="hidden" name="practiceKey" value="${f:h(e.practiceRef.model.key)}"/></td>
											<td><input type="hidden" name="memberKey" value="${f:h(e.memberRef.model.key)}"/></td>
											<td><input type="button" value="登録" class="submitButton" disabled="disabled" id="submitdisabled"/></td>
											<td class="ajaxWarnMessage"></td>
										</tr>
									</table>
									</li>
								</ul>
							</c:forEach>
							</form>
						</c:if>
					</div>				
					<div style="clear: both;">&nbsp;</div>
				</div>
				<!-- end #content -->
			<div style="clear: both;">&nbsp;</div>
			</div>
		</div>
	<!-- end #page -->
	</div>
</div>
<jsp:include page="/attend/common/footer.jsp" />
</body>
</html>
