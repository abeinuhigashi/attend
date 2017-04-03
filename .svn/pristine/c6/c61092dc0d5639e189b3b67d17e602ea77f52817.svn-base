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
<title>団員登録ページ</title>
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
						<h2>団員登録ページ</h2>
						<p>団員情報を入力してください。</p>
						<p><span class="error">${f:h(errors.cannotRegist)}</span></p>
						<form method="post" action="/attend/manage/member/regist/submit">
						<table>
							<tr>
								<td>ID</td> 
								<td><input type="text" id="loginid" ${f:text("id")}/></td>
								<td><span class="error">${f:h(errors.id)}</span></td>
							</tr>
							<tr>
								<td>Password</td> 
								<td><input type="password" name="password" id="loginid" /></td>
								<td><span class="error">${f:h(errors.password)}</span></td>
							</tr>
							<tr>
								<td>苗字</td> 
								<td><input type="text" id="loginid" ${f:text("lastName")}/></td>
								<td><span class="error">${f:h(errors.lastName)}</span></td>
							</tr>
							<tr>
								<td>名前</td> 
								<td><input type="text" id="loginid" ${f:text("firstName")}/></td>
								<td><span class="error">${f:h(errors.firstName)}</span></td>
							</tr>
							<tr>
								<td>ニックネーム</td> 
								<td><input type="text" id="loginid" ${f:text("nickName")}/></td>
								<td><span class="error">${f:h(errors.nickName)}</span></td>
							</tr>
							<tr>
								<td>パート</td>
								<td>
								<select name="part">
									<option ${f:select("part", "")}></option>
									<option ${f:select("part", "Sop")}>Sop</option>
									<option ${f:select("part", "Alto")}>Alto</option>
									<option ${f:select("part", "Tenor")}>Tenor</option>
									<option ${f:select("part", "Bass")}>Bass</option>
								</select>
								</td>
							</tr>							
						</table>
						<table>
							<tr>
								<td></td> 
								<td><input type="submit" id="submit" value="登録" /></td>
							</tr>
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
