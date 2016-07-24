<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tags:page>
	<jsp:attribute name="title">Bulk Merchant Upload</jsp:attribute>
	<jsp:attribute name="script">
<script type="text/javascript">
	$(document).ready(function() {
		$("#form").validate();
		if ($("#message").val() != "") {
			noty({
				text : $("#message").val(),
				type : "error",
				layout : "topRight"
			});
		}
	});
</script>
</jsp:attribute>
	<jsp:body>
	<div id="content" class="col-lg-10 col-sm-11">
		<div class="box">
		<div class="box-header" style="padding-top: 10px; padding-left: 10px">
		Bulk Merchant Upload
		</div>
			<div class="box-content">
			<div class="row">
				<input type="hidden" id="message" value="${message}" />
					<form name="fileBean"
							action='<c:url value="/Bulk/Merchant/upload" />' method="post"
							id="form" enctype="multipart/form-data">
						<div class="col-lg-12" style="margin-top: 20px">
							<div class="col-lg-4">
								<label>Select File : </label> <input type="file" name="file"
										class="form-control required">
							</div>
							<div class="col-lg-4"
									style="text-align: center; vertical-align: bottom;">
								<input type="submit" value="Upload File" class="btn btn-primary">
							</div>
						</div>
					</form>
				</div>
			<c:if test="${error}">
				<div class="row" style="margin-top: 30px">
					<div class="col-lg-12">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Merchant Name</th>
									<th>Error Message</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="error" items="${errors}">
									<tr>
										<td>${error.field}</td>
										<td>${error.reason}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</c:if>
		</div>
	</div>
	</div>
	</jsp:body>
</tags:page>