<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
	<div class="row">
		<div class="col-xs-5">
			<h3><spring:message code="recovery.page.header"/></h3>
			<form method="POST" action="${action}">
				<div class="form-group">
					<label for="email" class="required"><spring:message code="recovery.page.email"/></label>
					<input type="text" id="email" name="email" class="form-control"/>
				</div>
				<div class="form-group">
					<div class="text-muted"><spring:message code="recovery.page.info"/></div>
				</div>
				<button type="submit" class="btn btn-primary"><spring:message code="button.send"/></button>
			</form>
		</div>
	</div>
</div>