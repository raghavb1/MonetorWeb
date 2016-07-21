<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tags:page>
	<jsp:attribute name="title">User Management</jsp:attribute>
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
		Payment Modes
		</div>
			<div class="box-content">
			<input type="hidden" id="message" value="${message}" />
				<div class="row" style="margin-top: 30px">
					<div class="col-lg-12">
						<table
								class="table table-striped table-bordered bootstrap-datatable datatable">
							<thead>
								<tr>
									<th>Payment Mode</th>
									<th>Icon Url</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="paymentMode" items="${paymentModes}">
									<tr>
										<td>${paymentMode.name}</td>
										<td>${paymentMode.iconUrl}</td>
											<td><a
												href='<c:url value="/PaymentMode/edit/${paymentMode.id}"></c:url>'
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