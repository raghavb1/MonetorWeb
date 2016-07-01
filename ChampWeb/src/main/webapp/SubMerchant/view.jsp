<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tags:page>
	<jsp:attribute name="title">Merchant Management</jsp:attribute>
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
		function approve(subMerchantId) {
				var merchantId = $("#"+subMerchantId.toString()+"_merchantId").val();
				var request = $
						.ajax({
							type : "POST",
							url : "/Monetor/SubMerchant/approve/"+subMerchantId,
							data : {
								merchantId : merchantId
							}
						});
				request
						.done(function(msg) {
							if (msg == "failure") {
								noty({
									text : "Failed to approve Sub Merchant",
									type : "error",
									layout : "topRight"
								});
							} else {
								$("#"+subMerchantId.toString()).remove();
							}
						});
				request
						.fail(function(jqXHR,
								textStatus) {
							noty({
								text : "Failed to connect to server to approve Sub Merchant.",
								type : "error",
								layout : "topRight"
							});
						});
		}
	</script>
	</jsp:attribute>
	<jsp:body>
	<div id="content" class="col-sm-11 col-lg-10">
		<div class="box">
		<div class="box-header" style="padding-top: 10px; padding-left: 10px">
		Sub Merchants Pending for Mapping And Approval
		</div>
			<div class="box-content">
			<input type="hidden" id="message" value="${message}" />
				<div class="row" style="margin-top: 30px">
					<div class="col-lg-12">
						<table
								class="table table-striped table-bordered bootstrap-datatable datatable"
								id="submerchantTable">
							<thead>
								<tr>
									<th>SubMerchant Code</th>
									<th>Merchant</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="subMerchant" items="${submerchants}">
									<tr id="${subMerchant.id}">
										<td>${subMerchant.code}</td>
										<td><select name="merchantId"
												class="form-control required" data-rel="chosen"
												id="${subMerchant.id}_merchantId">
									<div class="chosen-container">
										<c:forEach items="${merchants}" var="merchant">
											<option value="${merchant.id}">${merchant.name}</option>
										</c:forEach>
									</div>
								</select></td>
										<td><button onclick="approve(${subMerchant.id})"
													class="btn btn-success">Approve</button>
													<a
												href='<c:url value="/SubMerchant/edit/${subMerchant.id}"></c:url>'
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