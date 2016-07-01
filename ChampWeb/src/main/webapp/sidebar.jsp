<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<div id="sidebar-left" class="col-lg-2 col-sm-1 ">
	<div class="sidebar-nav nav-collapse collapse navbar-collapse">
		<ul class="nav main-menu">
			<li><a href="<c:url value="/home"/>"><i class="fa fa-home"></i><span
					class="hidden-sm text"> Home</span> </a></li>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<li><a class="dropmenu" href="#"><i class="fa fa-user"></i><span
						class="hidden-sm text"> Users</span> <span class="chevron closed"></span>
				</a>
					<ul>
						<li><a class="submenu" href="<c:url value="/User/create" />"><i
								class="fa fa-plus"></i><span class="hidden-sm text">
									Create</span> </a></li>
						<li><a class="submenu" href="<c:url value="/User/view" />"><i
								class="fa fa-edit"></i><span class="hidden-sm text">
									View/Edit</span> </a></li>
					</ul></li>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<li><a class="dropmenu" href="#"><i class="fa fa-inbox"></i><span
						class="hidden-sm text"> Category</span> <span
						class="chevron closed"></span> </a>
					<ul>
						<li><a class="submenu"
							href="<c:url value="/Category/create" />"><i
								class="fa fa-plus"></i><span class="hidden-sm text">
									Create</span> </a></li>
						<li><a class="submenu"
							href="<c:url value="/Category/view" />"><i class="fa fa-edit"></i><span
								class="hidden-sm text"> View/Edit</span> </a></li>
					</ul></li>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<li><a class="dropmenu" href="#"><i class="fa fa-bullhorn"></i><span
						class="hidden-sm text"> Merchant</span> <span
						class="chevron closed"></span> </a>
					<ul>
						<li><a class="submenu"
							href="<c:url value="/Merchant/create" />"><i
								class="fa fa-plus"></i><span class="hidden-sm text">
									Create</span> </a></li>
						<li><a class="submenu"
							href="<c:url value="/Merchant/view" />"><i class="fa fa-edit"></i><span
								class="hidden-sm text"> View/Edit</span> </a></li>
					</ul></li>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<li><a class="dropmenu" href="#"><i class="fa fa-th-list"></i><span
						class="hidden-sm text">Sub Merchant</span> <span
						class="chevron closed"></span> </a>
					<ul>
						<li><a class="submenu"
							href="<c:url value="/SubMerchant/create" />"><i
								class="fa fa-plus"></i><span class="hidden-sm text">
									Create</span> </a></li>
						<li><a class="submenu"
							href="<c:url value="/SubMerchant/view" />"><i
								class="fa fa-edit"></i><span class="hidden-sm text">
									View/Edit</span> </a></li>
					</ul></li>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<li><a class="dropmenu" href="#"><i class="fa fa-book"></i><span
						class="hidden-sm text"> Bank</span> <span class="chevron closed"></span>
				</a>
					<ul>
						<li><a class="submenu" href="<c:url value="/Bank/create" />"><i
								class="fa fa-plus"></i><span class="hidden-sm text">
									Create</span> </a></li>
						<li><a class="submenu" href="<c:url value="/Bank/view" />"><i
								class="fa fa-edit"></i><span class="hidden-sm text">
									View/Edit</span> </a></li>
					</ul></li>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<li><a class="dropmenu" href="#"><i class="fa fa-briefcase"></i><span
						class="hidden-sm text">Payment Modes</span> <span
						class="chevron closed"></span> </a>
					<ul>
						<li><a class="submenu"
							href="<c:url value="/BankPayment/create" />"><i
								class="fa fa-plus"></i><span class="hidden-sm text">
									Create</span> </a></li>
						<li><a class="submenu"
							href="<c:url value="/BankPayment/view" />"><i
								class="fa fa-edit"></i><span class="hidden-sm text">
									View/Edit</span> </a></li>
					</ul></li>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<li><a class="dropmenu" href="#"><i class="fa fa-refresh"></i><span
						class="hidden-sm text"> Search Query</span> <span
						class="chevron closed"></span> </a>
					<ul>
						<li><a class="submenu"
							href="<c:url value="/SearchQuery/create" />"><i
								class="fa fa-plus"></i><span class="hidden-sm text">
									Create</span> </a></li>
						<li><a class="submenu"
							href="<c:url value="/SearchQuery/view" />"><i
								class="fa fa-edit"></i><span class="hidden-sm text">
									View/Edit</span> </a></li>
					</ul></li>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<li><a class="dropmenu" href="#"><i class="fa fa-share"></i><span
						class="hidden-sm text"> Parser</span> <span
						class="chevron closed"></span> </a>
					<ul>
						<li><a class="submenu"
							href="<c:url value="/Parser/create" />"><i class="fa fa-plus"></i><span
								class="hidden-sm text"> Create</span> </a></li>
						<li><a class="submenu" href="<c:url value="/Parser/view" />"><i
								class="fa fa-edit"></i><span class="hidden-sm text">
									View/Edit</span> </a></li>
					</ul></li>
			</security:authorize>

		</ul>
	</div>
	<a href="#" id="main-menu-min" class="full visible-md visible-lg"><i
		class="fa fa-angle-double-left"></i> </a>
</div>
<!-- end: Main Menu -->
