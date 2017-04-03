<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>団員権限情報変更完了ページ</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<div id="wrapper">
	<jsp:include page="/attend/common/header.jsp" />
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				<jsp:include page="/attend/manage/member/sidebar.jsp" />
				<div id="content">
					<div class="post">
						<h2>団員権限情報変更完了</h2>
						<p>${f:h(memberInfo.lastName)}&nbsp;${f:h(memberInfo.firstName)} さんの更新が完了しました。</p>
						<table>
							<tr>
								<td>出席情報</td>
								<td>
									<c:if test="${f:h(auth.attendance) == 0}">なし</c:if>
									<c:if test="${f:h(auth.attendance) == 1}">参照可</c:if>
									<c:if test="${f:h(auth.attendance) == 2}">更新可</c:if>
								</td>
							</tr>
							<tr>
								<td>団員情報</td>
								<td>
									<c:if test="${f:h(auth.member) == 0}">なし</c:if>
									<c:if test="${f:h(auth.member) == 1}">参照可</c:if>
									<c:if test="${f:h(auth.member) == 2}">更新可</c:if>
								</td>
							</tr>
							<tr>
								<td>練習日情報</td>
								<td>
									<c:if test="${f:h(auth.practice) == 0}">なし</c:if>
									<c:if test="${f:h(auth.practice) == 1}">参照可</c:if>
									<c:if test="${f:h(auth.practice) == 2}">更新可</c:if>
								</td>
							</tr>
							<tr>
								<td>団員権限情報</td>
								<td>
									<c:if test="${f:h(auth.memberAuth) == 0}">なし</c:if>
									<c:if test="${f:h(auth.memberAuth) == 2}">更新可</c:if>
								</td>
							</tr>
							<tr>
								<td>メール情報</td>
								<td>
									<c:if test="${f:h(auth.mail) == 0}">なし</c:if>
									<c:if test="${f:h(auth.mail) == 1}">参照可</c:if>
									<c:if test="${f:h(auth.mail) == 2}">更新可</c:if>
								</td>
							</tr>
						</table>
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
