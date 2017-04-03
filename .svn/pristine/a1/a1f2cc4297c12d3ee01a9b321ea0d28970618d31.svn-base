<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>団員登録結果ページ</title>
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
						<h2>団員登録結果ページ</h2>
						<p>ID:${f:h(member.id)} さんの登録が完了しました。</p>
						<table>
							<tr>
								<td>ID</td> 
								<td>${f:h(member.id)}</td>
							</tr>
							<tr>
								<td>Password</td> 
								<td>${f:h(member.password)}</td>
							</tr>
							<tr>
								<td>苗字</td> 
								<td>${f:h(member.lastName)}</td>
							</tr>
							<tr>
								<td>名前</td> 
								<td>${f:h(member.firstName)}</td>
							</tr>
							<tr>
								<td>ニックネーム</td> 
								<td>${f:h(member.nickName)}</td>
							</tr>
							<tr>
								<td>パート</td> 
								<td>${f:h(member.part)}</td>
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
