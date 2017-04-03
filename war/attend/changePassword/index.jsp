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
<title>パスワード変更</title>
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
						<h2>パスワード変更</h2>
						<p><span class="error">${f:h(errors.inputError)}</span></p>
						<form method="post" action="/attend/changePassword/submit">
						<table>
							<tr>
								<td>古いパスワード</td> 
								<td><input type="password" name="oldPassword" id="loginid" value="" /></td>
								<td><span class="error">${f:h(errors.oldPassword)}</span></td>
							</tr>
							<tr>
								<td>新しいパスワード</td> 
								<td><input type="password" name="newPassword" id="loginid" value="" /></td>
								<td><span class="error">${f:h(errors.newPassword)}</span></td>
							</tr>
							<tr>
								<td>新しいパスワード（確認）</td> 
								<td><input type="password" name="newPassword2" id="loginid" value="" /></td>
								<td><span class="error">${f:h(errors.newPassword2)}</span></td>
							</tr>
							<tr>
								<td><input type="hidden" name="key" value="${f:h(key)}" /></td> 
								<td><input type="submit" id="submit" value="決定" /></td>
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
