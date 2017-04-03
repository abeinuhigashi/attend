<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>トップページ</title>
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
						<h2>つかいかた</h2>
						<p>こんにちは！僕はMEKAZAWAっていうんだ！</br>僕の使い方の説明をするね！</p>
						<hr></hr>
						<p><b>ログイン</b>：ここから僕にログインをするんだ！</p>
						<p><b>出席入力</b>：僕と契約をして出席を入力してよ！</p>
						<p><b>登録情報変更</b>：もしメールアドレスとか変わったからここから変更するんだよ！！</p>
						<p><b>パスワード変更</b>：パスワードを変えたいときはここだよ！</p>
						<hr></hr>
						<p>まだ色々と途中だけどこれからパワーアップしていくよ！</p>
						<p>※Webアプリに詳しい人へ。いたずらしないでね！団員に悪い子はいないはずだからね！</p>
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
