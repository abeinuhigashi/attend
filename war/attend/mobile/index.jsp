<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>ログインページ</title>
</head>
<body>
<form method="post" action="/attend/mobile/login">
	<div>
		<h2>ログインしてください。</h2>
		<p><span class="error">${f:h(errors.loginInputError)}</span></p>
		<p><span class="error">${f:h(errors.notLoginError)}</span></p>
		ID: <input type="text" name="id" id="loginid" value="" /> <BR>
		Pass: <input type="password" name="password" id="loginid" value="" /><BR>
		<input type="submit" id="loginsubmit" value="Login" />
		<input type="hidden" name="fromPath" value="${f:h(fromPath)}" />
	</div>
</form>
</body>
</html>
