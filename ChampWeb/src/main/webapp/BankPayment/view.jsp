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
		Bank Payment Modes
		</div>
			<div class="box-content">
			<input type="hidden" id="message" value="${message}" />
				<div class="row" style="margin-top: 30px">
					<div class="col-lg-12">
						<table
								class="table table-striped table-bordered bootstrap-datatable datatable">
							<thead>
								<tr>
									<th>Bank Name</th>
									<th>Payment Mode</th>
									<th>Extracted String</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="bankPaymentMode" items="${bankPaymentModes}">
									<tr>
										<td>${bankPaymentMode.bank.name}</td>
										<td>${bankPaymentMode.paymentMode.name}</td>
										<td>${bankPaymentMode.extractedString}</td>
											<td><a
												href='<c:url value="/BankPayment/edit/${bankPaymentMode.id}"></c:url>'
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