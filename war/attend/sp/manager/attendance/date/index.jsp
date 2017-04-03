<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="atd" uri="http://neeton.org/tags/atd"%>

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
		
		$(".btn").click(function(){
			var str = ".attendance" + $(this).attr("name");
			if($(this).hasClass("active")){
				// 該当の出席者をONにする。見栄えの問題から値とは逆の動作。activeを持たない→表示する。
				$(str).show();
				if($(this).attr("name") == 2){
					$(".attendance4").show();
					$(".attendance5").show();
				}
			}else {
				$(str).hide();
				if($(this).attr("name") == 2){
					$(".attendance4").hide();
					$(".attendance5").hide();
				}
			}
		});
	});
	</script> 
</head>
<body>
	<jsp:include page="/attend/sp/common/header.jsp" />
	<div class="container">
		<div class="page-header">
			<h1>出欠参照ページ</h1>
		</div>
		<ul class="pager">
			<c:if test="${prevButtonDisp != false}">
			<li class="previous">
				<a href="/attend/sp/manager/attendance/date/?key=${f:h(dispPractice.key) }&prev">&larr; 前へ</a>
			</li>
			</c:if>
			<c:if test="${nextButtonDisp != false}">
			<li class="next">
				<a href="/attend/sp/manager/attendance/date/?key=${f:h(dispPractice.key) }&next">次へ &rarr;</a>
			</li>
			</c:if>
		</ul>
		<div class="well">
			<label><fmt:formatDate value="${dispPractice.startDate}" pattern="M月dd日"/> <fmt:formatDate value="${dispPractice.startDate}" pattern="HH:mm"/> - <fmt:formatDate value="${dispPractice.endDate}" pattern="HH:mm"/> ＠${f:h(dispPractice.practicePlace)}</label>
			<label><fmt:formatDate value="${dispPractice.gatheringDate}" pattern="HH:mm"/>${f:h(dispPractice.gatheringPoint)}集合</label>
			<hr>
			<label>出席者数：Total${f:h(totalAttendance)} (S:${f:h(sopAttendance)} A:${f:h(altoAttendance)} T:${f:h(tenAttendance)} B:${f:h(bassAttendance)})</label>
		</div>
		<div class="row">
			<div class="span8">
				<div class="btn-group" data-toggle="buttons-checkbox">
					<button class="btn btn-primary" name="0">未入力</button>
					<button class="btn btn-primary" name="1">未定</button>
					<button class="btn btn-primary" name="2">出席</button>
					<button class="btn btn-primary" name="3">欠席</button>
				</div>
			</div>
		</div>
		<hr>
		
		<table class="table table-striped table-condensed">
		<thead>
			<tr>
				<th>名前</th>
				<th>出欠</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="member" items="${dispMemberList}" varStatus="status">
			<tr class="attendance${f:h(dispAttendanceList[status.index].attendance)}">
				<td>${f:h(member.lastName)} ${f:h(member.firstName)}</td>
				<td><atd:atdOption attendance="${f:h(dispAttendanceList[status.index].attendance)}" memberKey="${f:h(member.key)}" practiceKey="${f:h(dispPractice.key)}" disable="${f:h(updateDisabled)}" classAttr="span2"/></td>
			</tr>
			</c:forEach>
		</tbody>        
		</table>
		
		<ul class="pager">
			<c:if test="${prevButtonDisp != false}">
			<li class="previous">
				<a href="/attend/sp/manager/attendance/date/?key=${f:h(dispPractice.key) }&prev">&larr; 前へ</a>
			</li>
			</c:if>
			<c:if test="${nextButtonDisp != false}">
			<li class="next">
				<a href="/attend/sp/manager/attendance/date/?key=${f:h(dispPractice.key) }&next">次へ &rarr;</a>
			</li>
			</c:if>
		</ul>

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
