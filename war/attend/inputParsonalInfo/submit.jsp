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
				<jsp:include page="/attend/common/sidebar.jsp" />
				<div id="content">
					<div class="post">
						<h2>登録情報変更完了</h2>
						<p>更新が完了しました。</p>
						<input type="hidden" name="key" value="${f:h(member.key)}" />
						<table class="disp-table">
							<tr>
								<td>ID</td> 
								<td>${f:h(member.id)}</td>
							</tr>
							<tr>
								<td>名前</td> 
								<td>${f:h(member.lastName)}${f:h(member.firstName)}</td>
							</tr>
							<tr>
								<td>ニックネーム</td> 
								<td>${f:h(member.nickName)}</td>
							</tr>
							<tr>
								<td>生年月日</td> 
								<td><fmt:formatDate value="${member.birthDay}" pattern="yyyy年MM月dd日"/></td>
							</tr>
							<tr>
								<td>メールアドレス</td> 
								<td>${f:h(member.mailAddress)}</td>
							</tr>
							<tr>
								<td>パート</td> 
								<td>${f:h(member.part)}</td>
							</tr>
							<tr>
								<td>お知らせメール</td> 
								<td>
									<c:if test="${e.mailUse == true}">使用する</c:if>	
									<c:if test="${e.mailUse != true}">使用しない</c:if>	
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
