<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<div id="sidebar">
	<ul>
		<jsp:include page="/attend/common/login.jsp" />
		<li>
			<h2>メニュー</h2>
			<ul>
			<c:if test="${loginUserAuth.practice == 2}">
				<li><a href="/attend/manage/practice/regist/">練習日登録</a></li>
			</c:if>
				<li><a href="/attend/manage/practice/search/">練習日検索</a></li>
			</ul>
		</li>
	</ul>
</div>
<!-- end #sidebar -->
