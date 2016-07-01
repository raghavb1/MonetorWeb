<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tags:page>
	<jsp:attribute name="title">Search Query Management</jsp:attribute>
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
		})
	});
</script>
</jsp:attribute>
	<jsp:body>
	<div id="content" class="col-lg-10 col-sm-11">
		<div class="box">
		<div class="box-header" style="padding-top: 10px; padding-left: 10px">
		Search Query Management
		</div>
			<div class="box-content">
			<div class="row">
				<input type="hidden" id="message" value="${message}" />
					<form name="searchQuery"
							action='<c:url value="/SearchQuery/save" />' method="post"
							id="form">
						<input type="hidden" name="id" value="${searchQuery.id}" />
						<div class="col-lg-12" style="margin-top: 20px">
							<div class="col-lg-4">
								<label>Search Query : </label> <input type="text"
										name="searchQuery" class="form-control required"
										value="${searchQuery.searchQuery}" id="searchQuery_code">
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