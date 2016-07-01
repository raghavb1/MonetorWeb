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
						$("form").submit(function(e) {
							$("#form").validate();
							if ($("#submerchant_code").hasClass("error")) {
								e.preventDefault();
								noty({
									text : "Please enter a different Code.",
									type : "error",
									layout : "topRight"
								});
							}
							if ($("#merchant").val() == null) {
								e.preventDefault();
								noty({
									text : "Please select Merchant.",
									type : "error",
									layout : "topRight"
								});
							}
						})
						$("#submerchant_code")
								.on(
										"change",
										function() {
											var submerchant_code = $(this)
													.val();
											var request = $
													.ajax({
														type : "POST",
														url : "/Monetor/SubMerchant/checkSubMerchant",
														data : {
															code : submerchant_code
														}
													});
											request
													.done(function(msg) {
														if (msg == "failure") {
															noty({
																text : "Sub Merchant already exists. Please Select a different code.",
																type : "error",
																layout : "topRight"
															});
															$(
																	"#submerchant_code")
																	.addClass(
																			"error");
														} else {
															$(
																	"#submerchant_code")
																	.removeClass(
																			"error");
														}
													});
											request
													.fail(function(jqXHR,
															textStatus) {
														noty({
															text : "Failed to check Sub Merchant Name.",
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
		Sub Merchant Management
		</div>
			<div class="box-content">
			<div class="row">
				<input type="hidden" id="message" value="${message}" />
					<form name="subMerchant"
							action='<c:url value="/SubMerchant/save" />' method="post"
							id="form">
						<input type="hidden" name="id" value="${subMerchant.id}" />
						<div class="col-lg-12" style="margin-top: 20px">
							<div class="col-lg-4">
								<label>Code : </label> <input type="text" name="code"
										class="form-control required" value="${subMerchant.code}"
										id="subMerchant_code">
							</div>
							<div class="col-lg-4">
								<label>Merchant : </label> <select name="merchant.id"
										class="form-control required" data-rel="chosen" id="merchant">
									<div class="chosen-container">
										<c:forEach items="${merchants}" var="merchant">
											<c:choose>
												<c:when test="${merchant.id eq subMerchant.merchant.id}">
													<option value="${merchant.id}" selected="selected">${merchant.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${merchant.id}">${merchant.name}</option>
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