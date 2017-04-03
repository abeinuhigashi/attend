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
<title>団員権限情報更新ページ</title>
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
						<h2>団員権限情報変更</h2>
						<p>更新情報を入力してください。</p>
						<p><span class="error">${f:h(errors.cannotRegist)}</span></p>
						<form method="post" action="/attend/manage/member/updateAuth/submit">
						<input type="hidden" name="memberKey" value="${f:h(memberInfo.key)}" />
						<input type="hidden" name="memberAuthKey" value="${f:h(memberAuthKey)}" />
						<table>
							<tr>
								<td>名前</td>
								<td>${f:h(memberInfo.lastName)}&nbsp;${f:h(memberInfo.firstName)}</td>
							</tr>
							<tr>
								<td>出席情報</td>
								<td>
									<input type="radio" ${f:radio("attendance", "0")}/>なし
									<input type="radio" ${f:radio("attendance", "1")}/>参照可
									<input type="radio" ${f:radio("attendance", "2")}/>更新可
								</td>
							</tr>
							<tr>
								<td>団員情報</td>
								<td>
									<input type="radio" ${f:radio("member", "0")}/>なし
									<input type="radio" ${f:radio("member", "1")}/>参照可
									<input type="radio" ${f:radio("member", "2")}/>更新可
								</td>
							</tr>
							<tr>
								<td>練習日情報</td>
								<td>
									<input type="radio" ${f:radio("practice", "0")}/>なし
									<input type="radio" ${f:radio("practice", "1")}/>参照可
									<input type="radio" ${f:radio("practice", "2")}/>更新可
								</td>
							</tr>
							<tr>
								<td>団員権限情報</td>
								<td>
									<input type="radio" ${f:radio("memberAuth", "0")}/>なし
									<input type="radio" ${f:radio("memberAuth", "2")}/>更新可
								</td>
							</tr>
							<tr>
								<td>メール情報</td>
								<td>
									<input type="radio" ${f:radio("mail", "0")}/>なし
									<input type="radio" ${f:radio("mail", "1")}/>参照可
									<input type="radio" ${f:radio("mail", "2")}/>更新可
								</td>
							</tr>
							<c:if test="${loginUserAuth.member == 2}">
							<tr>
								<td></td>
								<td><input type="submit" class="memberAuth-update" value="更新" /></td>
								<td></td>
							</tr>
							</c:if>
						</table>
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
