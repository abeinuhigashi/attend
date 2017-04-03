<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="#">MEKAZAWA</a>
				<div class="nav-collapse">
					<ul class="nav">
						<li class="">
							<a href="/attend/sp/member/practice/monthly/">練習一覧</a>
						</li>
						<li class="">
							<a href="/attend/sp/member/practice/blank/">未入力一覧</a>
						</li>
						<li class="">
							<a href="/attend/sp/member/practice/next/">次回練習</a>
						</li>
						<li class="">
							<a href="/attend/sp/login/logout">ログアウト</a>
						</li>
					</ul>
					<ul class="nav pull-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">練習管理<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="/attend/sp/manager/attendance/date/">出欠表示：次回分</a></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div><!-- /navbar-fixed-top -->
