<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tags:page>
	<jsp:attribute name="title">Search Query Management</jsp:attribute>
	<jsp:attribute name="script">
	<script type="text/javascript">
		$(document).ready(function() {
			if ($("#message").val() != "") {
				noty({
					text : $("#message").val(),
					type : "success",
					layout : "topRight"
				});
			}
		});
	</script>
	</jsp:attribute>
	<jsp:body>
	<div id="content" class="col-sm-11 col-lg-10">
		<div class="box">
		<div class="box-header" style="padding-top: 10px; padding-left: 10px">
		Search Queries
		</div>
			<div class="box-content">
			<input type="hidden" id="message" value="${message}" />
				<div class="row" style="margin-top: 30px">
					<div class="col-lg-12">
						<table
								class="table table-striped table-bordered bootstrap-datatable datatable">
							<thead>
								<tr>
									<th>Search Query</th>
									<th>Bank</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="searchQuery" items="${searchQueries}">
									<tr id="${searchQuery.id}">
										<td>${searchQuery.searchQuery}</td>
										<td>${searchQuery.bank.name}</td>
									<td><a
												href='<c:url value="/SearchQuery/edit/${searchQuery.id}"></c:url>'
												class="btn btn-primary">Edit</a>
											</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</jsp:body>
</tags:page>