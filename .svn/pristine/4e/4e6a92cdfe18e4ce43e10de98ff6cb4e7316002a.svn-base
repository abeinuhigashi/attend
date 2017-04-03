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
<title>出欠参照ページ</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" media="screen" />

<script type="text/javascript" src="/js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.8.9.custom.min.js"></script>

<script> 
$(document).ready(function(){
	$('select').change(function(){
		var keys = "&memberKey=" + $(this).attr("name").replace(",", "&practiceKey=");
		var msg = "inputAttendance=" + $(this).val() + keys;
		$.ajax({
		   type: "POST",
		   url: "/attend/async/attendance/update",
		   data: msg,
		});
	});
	
	$(".selectedAttendanceArray").click(function(){
		var str = ".attendance" + $(this).attr("value");
		if($(this).attr("checked") == true){
			$(str).show();
			if($(this).attr("value") == 2){
				$(".attendance4").show();
				$(".attendance5").show();
			}
		} else {
			$(str).hide();
			if($(this).attr("value") == 2){
				$(".attendance4").hide();
				$(".attendance5").hide();
			}
		}
	});
});
</script> 

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
						<h2>出欠参照ページ</h2>
						<p>プルダウンを変更することで出欠が更新されます。<br />最新結果が知りたい場合はページを更新してください。</p>
						<p><span class="error">${f:h(errors.inputError)}</span></p>
						<c:if test="${dispPractice != null}">
							<p>選択中の練習<br />
							日時：<fmt:formatDate value="${dispPractice.startDate}" pattern="M月dd日"/> <fmt:formatDate value="${dispPractice.startDate}" pattern="HH:mm"/> - <fmt:formatDate value="${dispPractice.endDate}" pattern="HH:mm"/> ＠${f:h(dispPractice.practicePlace)}<br />
							集合：<fmt:formatDate value="${dispPractice.gatheringDate}" pattern="HH:mm"/>＠${f:h(dispPractice.gatheringPoint)}
							</p>
							<form action="/attend/manage/attendance/date/disp">
							<input type="hidden" name="key" value="${f:h(dispPractice.key) }"/>
							<c:if test="${prevButtonDisp != false}">
							<input type="submit" name="prev" value="前へ" />
							</c:if>
							<c:if test="${nextButtonDisp != false}">
							<input type="submit" name="next" value="次へ" />
							</c:if>
							</form>
							
							<br />
							<hr />
							<p>チェックしてある出欠のみ表示します。</p>
							<form action="/attend/manage/attendance/date/disp">
							<table>
							<tr>
							<td>
							<input type="checkbox" ${f:multibox("selectedAttendanceArray", "0")} class="selectedAttendanceArray" checked/>未入力
							<input type="checkbox" ${f:multibox("selectedAttendanceArray", "1")} class="selectedAttendanceArray" checked/>未定
							<input type="checkbox" ${f:multibox("selectedAttendanceArray", "2")} class="selectedAttendanceArray" checked/>出席
							<input type="checkbox" ${f:multibox("selectedAttendanceArray", "3")} class="selectedAttendanceArray" checked/>欠席
							</td>
							<!-- <td>
							<input type="submit" value="選択" />
							<input type="hidden" name="key" value="${f:h(dispPractice.key) }"/>
							</td> -->
							</tr>
							</table>
							</form>
	
							<c:if test="${dispMemberList == null}">
							<p><span class="error">表示対象の出欠が存在しません。</span></p>
							</c:if>
							<c:if test="${dispMemberList != null}">
							<p>Total:${f:h(totalAttendance)} (S:${f:h(sopAttendance)} A:${f:h(altoAttendance)} T:${f:h(tenAttendance)} B:${f:h(bassAttendance)})</p>
							<table class="disp-table">
								<tr>
									<th>団員</th>
									<th>出欠</th>
									<th>備考</th>
								</tr>
								
								<c:forEach var="member" items="${dispMemberList}" varStatus="status">
								<tr class="attendance${f:h(dispAttendanceList[status.index].attendance)}">
									<td>${f:h(member.lastName)} ${f:h(member.firstName)}</td>
									<td><atd:atdOption attendance="${f:h(dispAttendanceList[status.index].attendance)}" memberKey="${f:h(member.key)}" practiceKey="${f:h(dispPractice.key)}" disable="${f:h(updateDisabled)}"/></td>
									<td>${f:h(dispAttendanceList[status.index].racital) }</td>
								</tr>
								</c:forEach>
							</table>
							</c:if>	
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
