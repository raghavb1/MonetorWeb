<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="com.champ.core.entity.AdminUser"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header class="navbar">
	<div class="container">
		<a class="navbar-brand col-md-2 col-sm-1 col-xs-2"
			href='<c:url value="/home" />'><font color="white" face="Verdana">Monetor</font></a>
		<div class="nav-no-collapse header-nav">
			<ul class="nav navbar-nav pull-right">
				<!-- start: User Dropdown -->
				<li class="dropdown"><a class="btn account dropdown-toggle"
					data-toggle="dropdown" href="#">
						<div class="avatar">
							<img src="<c:url value="/img/user.png" />" alt="User">
						</div>
						<div class="user">
							<span class="hello">Welcome!</span> <span class="name"><%=SecurityContextHolder.getContext().getAuthentication().getName()%></span>
						</div>
				</a>
					<ul class="dropdown-menu">
						<li><a href="<c:url value="/j_spring_security_logout" />"><i
								class="fa fa-cog"></i> Logout</a></li>
					</ul></li>
				<!-- end: User Dropdown -->
			</ul>
		</div>
		<!-- end: Header Menu -->
	</div>
</header>