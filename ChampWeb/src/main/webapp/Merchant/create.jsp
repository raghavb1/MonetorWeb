<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tags:page>
	<jsp:attribute name="title">Merchant Management</jsp:attribute>
	<jsp:attribute name="script">
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#form").validate();
						if ($("#message").val() != "") {
							noty({
								text : $("#message").val(),
								type : "error",
								layout : "topRight"
							});
						}
						$("form")
								.submit(
										function(e) {
											$("#form").validate();
											if ($("#merchant_name").hasClass(
													"error")) {
												e.preventDefault();
												noty({
													text : "Please enter a different Name.",
													type : "error",
													layout : "topRight"
												});
											}
											if ($("#category").val() == null) {
												e.preventDefault();
												noty({
													text : "Please select category for the merchant.",
													type : "error",
													layout : "topRight"
												});
											}
										})
						$("#merchant_name")
								.on(
										"change",
										function() {
											var merchant_name = $(this).val();
											var request = $
													.ajax({
														type : "POST",
														url : "/Monetor/Merchant/checkMerchant",
														data : {
															name : merchant_name
														}
													});
											request
													.done(function(msg) {
														if (msg == "failure") {
															noty({
																text : "Merchant already exists. Please Select a different name.",
																type : "error",
																layout : "topRight"
															});
															$("#merchant_name")
																	.addClass(
																			"error");
														} else {
															$("#merchant_name")
																	.removeClass(
																			"error");
														}
													});
											request
													.fail(function(jqXHR,
															textStatus) {
														noty({
															text : "Failed to check Merchant Name.",
															type : "error",
															layout : "topRight"
														});
													});
										});
					});
</script>
</jsp:attribute>
	<jsp:body>
	<div id="content" class="col-lg-10 col-sm-11">
		<div class="box">
		<div class="box-header" style="padding-top: 10px; padding-left: 10px">
		Merchant Management
		</div>
			<div class="box-content">
			<div class="row">
				<input type="hidden" id="message" value="${message}" />
					<form name="merchant" action='<c:url value="/Merchant/save" />'
							method="post" id="form">
						<input type="hidden" name="id" value="${merchant.id}" />
						<div class="col-lg-12" style="margin-top: 20px">
							<div class="col-lg-4">
								<label>Name : </label> <input type="text" name="name"
										class="form-control required" value="${merchant.name}"
										id="merchant_name">
							</div>
								<div class="col-lg-4">
									<label>Image Url : </label> <input type="text" name="imageUrl"
										value="${merchant.imageUrl}" class="form-control required">
								</div>
							<div class="col-lg-4">
								<label>Category : </label> <select name="category.id"
										class="form-control required" data-rel="chosen" id="category">
									<div class="chosen-container">
										<c:forEach items="${categories}" var="category">
											<c:choose>
												<c:when test="${category.id eq merchant.category.id}">
													<option value="${category.id}" selected="selected">${category.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${category.id}">${category.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</div>
								</select>
							</div>
						</div>
						<div class="col-lg-12"
								style="text-align: center; margin-top: 20px">
							<input type="submit" value="Save" class="btn btn-primary">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	</jsp:body>
</tags:page>