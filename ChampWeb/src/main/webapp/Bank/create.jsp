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
							if ($("#bank_name").hasClass("error")) {
								e.preventDefault();
								noty({
									text : "Please enter a different Name.",
									type : "error",
									layout : "topRight"
								});
							}
						})
						$("#bank_name")
								.on(
										"change",
										function() {
											var bank_name = $(this).val();
											var request = $
													.ajax({
														type : "POST",
														url : "/Monetor/Bank/checkBank",
														data : {
															name : bank_name
														}
													});
											request
													.done(function(msg) {
														if (msg == "failure") {
															noty({
																text : "Bank already exists. Please Select a different name.",
																type : "error",
																layout : "topRight"
															});
															$("#bank_name")
																	.addClass(
																			"error");
														} else {
															$("#bank_name")
																	.removeClass(
																			"error");
														}
													});
											request
													.fail(function(jqXHR,
															textStatus) {
														noty({
															text : "Failed to check Bank Name.",
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
		Bank Management
		</div>
			<div class="box-content">
			<div class="row">
				<input type="hidden" id="message" value="${message}" />
					<form name="bank" action='<c:url value="/Bank/save" />'
							method="post" id="form">
						<input type="hidden" name="id" value="${bank.id}" />
						<div class="col-lg-12" style="margin-top: 20px">
							<div class="col-lg-4">
								<label>Name : </label> <input type="text" name="name"
										class="form-control required" value="${bank.name}"
										id="bank_name">
							</div>
								<div class="col-lg-4">
									<label>Icon Url : </label> <input type="text" name="iconUrl"
										value="${bank.iconUrl}" class="form-control required">
								</div>
							<div class="col-lg-4">
								<label>Website Link : </label> <input type="text" name="websiteLink"
										value="${bank.websiteLink}" class="form-control required">
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