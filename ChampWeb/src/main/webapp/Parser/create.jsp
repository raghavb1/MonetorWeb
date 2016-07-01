<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tags:page>
	<jsp:attribute name="title">Parser Management</jsp:attribute>
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
		$("form").submit(function(e) {
			$("#form").validate();
			if ($("#bank").val() == null) {
				e.preventDefault();
				noty({
					text : "Please select Bank.",
					type : "error",
					layout : "topRight"
				});
			}
			if ($("#searchQuery").val() == null) {
				e.preventDefault();
				noty({
					text : "Please select SearchQuery.",
					type : "error",
					layout : "topRight"
				});
			}
		})
	});
</script>
</jsp:attribute>
	<jsp:body>
	<div id="content" class="col-lg-10 col-sm-11">
		<div class="box">
		<div class="box-header" style="padding-top: 10px; padding-left: 10px">
		Parser Management
		</div>
			<div class="box-content">
			<div class="row">
				<input type="hidden" id="message" value="${message}" />
					<form name="parser" action='<c:url value="/Parser/save" />'
							method="post" id="form">
						<input type="hidden" name="id" value="${parser.id}" />
						<div class="col-lg-12" style="margin-top: 20px">
							<div class="col-lg-4">
								<label>Template : </label> <input type="text" name="template"
										class="form-control required" value="${parser.template}"
										id="template">
							</div>
							<div class="col-lg-4">
								<label>Bank : </label> <select name="bank.id"
										class="form-control required" data-rel="chosen" id="bank">
									<div class="chosen-container">
										<c:forEach items="${banks}" var="bank">
											<c:choose>
												<c:when test="${bank.id eq searchQuery.bank.id}">
													<option value="${bank.id}" selected="selected">${bank.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${bank.id}">${bank.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</div>
								</select>
							</div>
							<div class="col-lg-4">
								<label>Search Query : </label> <select name="searchQuery.id"
										class="form-control required" data-rel="chosen" id="searchQuery">
									<div class="chosen-container">
										<c:forEach items="${searchQueries}" var="searchQuery">
											<c:choose>
												<c:when test="${searchQuery.id eq parser.searchQuery.id}">
													<option value="${searchQuery.id}" selected="selected">${searchQuery.searchQuery}</option>
												</c:when>
												<c:otherwise>
													<option value="${searchQuery.id}">${searchQuery.searchQuery}</option>
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