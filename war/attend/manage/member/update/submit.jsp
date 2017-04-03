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
<title>登録情報変更完了ページ</title>
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
						<h2>登録情報変更完了</h2>
						<p>ID:${f:h(member.id)} さんの更新が完了しました。</p>
						<input type="hidden" name="key" value="${f:h(member.key)}" />
						<table class="disp-table">
							<tr>
								<th>ID</th> 
								<td>${f:h(member.id)}</td>
							</tr>
							<tr>
								<th>名前</th> 
								<td>${f:h(member.lastName)}${f:h(member.firstName)}</td>
							</tr>
							<tr>
								<th>ニックネーム</th> 
								<td>${f:h(member.nickName)}</td>
							</tr>
							<tr>
								<th>生年月日</th> 
								<td><fmt:formatDate value="${member.birthDay}" pattern="yyyy-MM-dd"/></td>
							</tr>
							<tr>
								<th>メールアドレス</th> 
								<td>${f:h(member.mailAddress)}</td>
							</tr>
							<tr>
								<th>パート</th> 
								<td>${f:h(member.part)}</td>
							</tr>
							<tr>
								<th>休団有無</th> 
								<td>
									<c:if test="${member.suspended == true}">休団中</c:if>
									<c:if test="${member.suspended == false}">活動中</c:if>
								</td>
							</tr>
							<tr>
								<th>長期未入力者</th> 
								<td><input type="checkbox" disabled ${f:checkbox("notUsedLongTime")}/></td>
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
