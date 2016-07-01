<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tags:page>
	<jsp:attribute name="title">User Management</jsp:attribute>
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
							if ($("#category_name").hasClass("error")) {
								e.preventDefault();
								noty({
									text : "Please enter a different Name.",
									type : "error",
									layout : "topRight"
								});
							}
						})
						$("#category_name")
								.on(
										"change",
										function() {
											var category_name = $(this).val();
											var request = $
													.ajax({
														type : "POST",
														url : "/Monetor/Category/checkCategory",
														data : {
															name : category_name
														}
													});
											request
													.done(function(msg) {
														if (msg == "failure") {
															noty({
																text : "Category Name already exists. Please Select a different name.",
																type : "error",
																layout : "topRight"
															});
															$("#category_name")
																	.addClass(
																			"error");
														} else {
															$("#category_name")
																	.removeClass(
																			"error");
														}
													});
											request
													.fail(function(jqXHR,
															textStatus) {
														noty({
															text : "Failed to check Category Name.",
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
		Category Management
		</div>
			<div class="box-content">
			<div class="row">
				<input type="hidden" id="message" value="${message}" />
					<form name="category" action='<c:url value="/Category/save" />'
							method="post" id="form">
						<input type="hidden" name="id" value="${category.id}" />
						<div class="col-lg-12" style="margin-top: 20px">
							<div class="col-lg-4">
								<label>Name : </label> <input type="text" name="name"
										class="form-control required" value="${category.name}"
										id="category_name">
							</div>
								<div class="col-lg-4">
									<label>Category Image Url : </label> <input type="text"
											name="categoryImageUrl" class="form-control required"
											value="${category.categoryImageUrl}">
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