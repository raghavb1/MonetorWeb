<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tags:page>
	<jsp:attribute name="title">Parser Management</jsp:attribute>
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
		Parsers
		</div>
			<div class="box-content">
			<input type="hidden" id="message" value="${message}" />
				<div class="row" style="margin-top: 30px">
					<div class="col-lg-12">
						<table
								class="table table-striped table-bordered bootstrap-datatable datatable">
							<thead>
								<tr>
									<th>Template</th>
									<th>Search Query</th>
									<th>Bank</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="parser" items="${parsers}">
									<tr id="${parser.id}">
									<td>${parser.template}</td>
										<td>${parser.searchQuery.searchQuery}</td>
										<td>${parser.bank.name}</td>
									<td><a
												href='<c:url value="/Parser/edit/${parser.id}"></c:url>'
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