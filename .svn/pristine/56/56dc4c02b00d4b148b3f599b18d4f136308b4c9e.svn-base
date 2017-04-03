<%@page import="slim3.model.Attendance"%>
<%@page import="slim3.model.Practice"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="jp">
<head>
	<title>次回練習</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	
	<!-- Le styles -->
	<link href="/assets/css/bootstrap.css" rel="stylesheet">
	<style>
	  body {
	    padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
	  }
	</style>
	<link href="/assets/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="/assets/js/google-code-prettify/prettify.css" rel="stylesheet">
	
	<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	  <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
	
	<!-- Le fav and touch icons -->
	<link rel="shortcut icon" href="images/favicon.ico">
	<link rel="apple-touch-icon" href="images/apple-touch-icon.png">
	<link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png">
	<link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png">
</head>
<body>
	<jsp:include page="/attend/sp/common/header.jsp" />
	<div class="container">
		<div class="page-header">
			<h1>次回練習</h1>
		</div>
		
		<div class="row">
			<div class="span10 offset1">

			<c:if test="${success != null}">
			<div class="alert alert-success">
				<a class="close" data-dismiss="alert">×</a>
				<p>更新しました！</p>
			</div>
			</c:if>
			<c:if test="${errors.cannotUpdate != null}">
			<div class="alert alert-error">
				<a class="close" data-dismiss="alert">×</a>
				<p>${f:h(errors.cannotUpdate)}</p>
			</div>
			</c:if>
			<c:if test="${dispAttendance == null}">
			<div class="alert alert-error">
				<a class="close" data-dismiss="alert">×</a>
				<p>次回の練習は設定されていません。</p>
			</div>
			</c:if>
			<ul class="pager">
			<c:if test="${prevButtonDisp != false}">
			<li class="previous">
				<a href="/attend/sp/member/practice/next/?practiceKey=${f:h(dispAttendance.practiceKey) }&prev">&larr; 前へ</a>
			</li>
			</c:if>
			<c:if test="${nextButtonDisp != false}">
			<li class="next">
				<a href="/attend/sp/member/practice/next/?practiceKey=${f:h(dispAttendance.practiceKey) }&next">次へ &rarr;</a>
			</li>
			</c:if>
			</ul>
			
			<c:if test="${dispAttendance != null}">
				<c:set value="${dispAttendance}" var="e" scope="request"/>
				<c:set value="${e.attendance}" var="attendance" scope="request"/>
				
				<form class="well"  method="post" action="/attend/sp/member/practice/next/update" >
					<label><fmt:formatDate value="${e.practiceRef.model.startDate}" pattern="MM月dd日"/> <fmt:formatDate value="${e.practiceRef.model.startDate}" pattern="HH:mm"/> - <fmt:formatDate value="${e.practiceRef.model.endDate}" pattern="HH:mm"/> ＠${f:h(e.practiceRef.model.practicePlace)}</label>
					<label><fmt:formatDate value="${e.practiceRef.model.gatheringDate}" pattern="HH:mm"/>${f:h(e.practiceRef.model.gatheringPoint)}集合</label>
					<label>備考：${f:h(e.practiceRef.model.recital)}</label>
					<select class="input-small" name="attendance">
						<option ${f:select("attendance", "0")} disabled>未入力</option>
						<option ${f:select("attendance", "1")}>未定</option>
						<option ${f:select("attendance", "2")}>出席</option>
						<option ${f:select("attendance", "3")}>欠席</option>
						<option ${f:select("attendance", "4")}>遅刻</option>
						<option ${f:select("attendance", "5")}>早退</option>					
					</select>
					<input type="text" class="span3" name="racital" value="${e.racital}" placeholder="遅刻早退の場合は理由を">
					<button type="submit" class="btn btn-primary">登録</button>
					<input type="hidden" name="attendanceKey" value="${f:h(e.key)}"/>
					<input type="hidden" name="memberKey" value="${f:h(e.memberKey) }"/>
					<input type="hidden" name="practiceKey" value="${f:h(e.practiceKey) }"/>
					<input type="hidden" name="currentDate" value="${currentDate}" />
				</form>
				
			</c:if>
			</div>
		</div>
		<jsp:include page="/attend/sp/common/footer.jsp" />
	</div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="/assets/js/jquery.js"></script>
    <script src="/assets/js/bootstrap-transition.js"></script>
    <script src="/assets/js/bootstrap-alert.js"></script>
    <script src="/assets/js/bootstrap-modal.js"></script>
    <script src="/assets/js/bootstrap-dropdown.js"></script>
    <script src="/assets/js/bootstrap-scrollspy.js"></script>
    <script src="/assets/js/bootstrap-tab.js"></script>
    <script src="/assets/js/bootstrap-tooltip.js"></script>
    <script src="/assets/js/bootstrap-popover.js"></script>
    <script src="/assets/js/bootstrap-button.js"></script>
    <script src="/assets/js/bootstrap-collapse.js"></script>
    <script src="/assets/js/bootstrap-carousel.js"></script>
    <script src="/assets/js/bootstrap-typeahead.js"></script>
    
</body>
</html>
