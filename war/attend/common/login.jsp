<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<li>
	<div id="login" >
		<c:if test="${sessionScope.loginUser != null}">
		<form method="post" action="/attend/login/logout">
			<div>
				<h2>ようこそ <span class="blue">${f:h(loginUser.lastName)}</span> さん</h2>
				<font color="red">${sessionScope.autherror}</font>
				<input type="submit" id="loginsubmit" value="Logout" />
			</div>
		</form>
		</c:if>
		<c:if test="${sessionScope.loginUser == null}">
		<form method="post" action="/attend/login/login">
			<div>
				<h2>ログイン</h2>
				<p><span class="error">${f:h(errors.cannotLogin)}</span></p>
				ID: <input type="text" name="id" id="loginid" value="" /> <BR>
				Pass: <input type="password" name="password" id="loginid" value="" /><BR>
				<input type="submit" id="loginsubmit" value="Login" />
				<input type="hidden" name="fromPath" value="${f:h(fromPath)}" />
			</div>
		</form>
		</c:if>
	</div>
	<div style="clear: both;">&nbsp;</div>
</li>