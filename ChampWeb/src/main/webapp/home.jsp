<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:page>
	<jsp:attribute name="title">Home Page</jsp:attribute>
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
			<div id="content" class="col-lg-10 col-sm-11">
			<input type="hidden" id="message" value="${message}" />
				<div class="box">
					<div class="box-content">
						<div class="row">
							<div class="col-lg-12">			
							<h1>Welcome to Home !!!	</h1>
							</div>
						</div>
					</div>
				</div>
			</div>
		</jsp:body>
</tags:page>
