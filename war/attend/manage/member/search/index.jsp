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
<title>団員検索ページ</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="/js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="/js/stupidtable.min.js"></script>
<script type="text/javascript">
  $(function(){
      $(".disp-table").stupidtable();
  });
</script>
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
						<h2>団員検索ページ</h2>
						<p>一覧表のタイトルをクリックしたら並び順を変更出来ます。</p>
						<hr/>
						<form method="post" action="/attend/manage/member/update/">
						<c:if test="${memberList != null}">
						<table class="disp-table">
							<thead>
							<tr>
								<th></th>
								<th class="type-string">ID</th>
								<th class="type-string">名前</th>
								<th class="type-string">パート</th>
								<th class="type-string">誕生日</th>
								<th class="type-string">ステータス</th>
								<th class="type-string">最終ログイン</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="e" items="${memberList}">
							<tr>
								<td><input type="radio" name="id" value="${f:h(e.id)}"/></td>
								<td>${f:h(e.id)}</td>
								<td>${f:h(e.lastName)}&nbsp;${f:h(e.firstName)}</td>
								<td>${f:h(e.part)}</td>
								<td><fmt:formatDate value="${e.birthDay}" pattern="yyyy/MM/dd"/></td>
								<td>
									<c:if test="${e.suspended == true}">休団中</c:if>
									<c:if test="${e.suspended != true}">
										<c:if test="${e.notUsedLongTime == true}">長期未入力</c:if>
									</c:if>
								</td>
								<td><fmt:formatDate value="${e.lastLoginDateTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
							</tr>
							</c:forEach>
							</tbody>
						</table>
						</c:if>
						<p></p>
						<c:if test="${memberList != null}">
						<table>
							<tr>
								<c:if test="${loginUserAuth.member == 2}">
								<td><input type="submit" class="searchMember-bottombuttons" name="update" value="更新" /></td>
								<td><input type="submit" class="searchMember-bottombuttons" name="delete" value="削除" /></td>
								</c:if>
								<c:if test="${loginUserAuth.memberAuth == 2}">
								<td><input type="submit" class="searchMember-bottombuttons" name="updateAuth" value="権限変更" /></td>
								</c:if>
								<td><span class="error">${f:h(errors.selectError)}</span></td>
							</tr>
						</table>
						</c:if>
						</form>
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
