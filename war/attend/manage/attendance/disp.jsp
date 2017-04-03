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
						<p>プルダウンを変更することで出欠が更新されます。</br>最新結果が知りたい場合はページを更新してください。</p>
						<p><span class="error">${f:h(errors.selectError)}</span></p>
						<c:if test="${dispTable != null}">
						<table class="disp-table">
							<tr>
								<th><a href="/attend/manage/attendance/disp?key=${f:h(dispTable.columnHeader.keyList[0])}&mode=past"> ＜前へ </a></th>
								<c:forEach var="e" items="${dispTable.columnHeader.dateList}" varStatus="status">
									<th><a href="/attend/manage/attendance/disp?key=${f:h(dispTable.columnHeader.keyList[status.index])}"><fmt:formatDate value="${e}" pattern="MM/dd HH:mm"/></a></th>
								</c:forEach>
							</tr>
							
							<c:forEach var="e" items="${dispTable.rowList}">
							<tr>
								<td>${f:h(e.rowHeader.memberName)}</td>
								<c:forEach var="cell" items="${e.cellList}">
									<td><atd:atdOption attendance="${f:h(cell.attendance)}" memberKey="${f:h(e.rowHeader.memberKey)}" practiceKey="${f:h(cell.practiceKey)}"/></td>
								</c:forEach>
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
