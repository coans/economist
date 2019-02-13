<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<div class="row">
		<div class="col-xs-5">
			<h3><spring:message code="profile.page.header"/></h3>
			<form:form modelAttribute="usermodel" method="POST" action="${action}">
				<div class="form-group">
					<form:label path="email" class="required"><spring:message code="signup.page.email"/></form:label>
					<form:input type="text" path="email" class="form-control" readonly="true" />
					<div class="has-error">
						<form:errors path="email" cssClass="help-block" element="label"/>
					</div>
				</div>
				<div class="form-group">
					<form:label path="password" class="required"><spring:message code="signup.page.password"/></form:label>
					<form:input type="password" path="password" class="form-control"/>
					<div class="text-muted"><spring:message code="signup.page.password.info"/></div>
					<div class="has-error">
						<form:errors path="password" cssClass="help-block" element="label"/>
					</div>
				</div>
				<div class="form-group">
					<form:label path="firstName" class="required"><spring:message code="signup.page.firstname"/></form:label>
					<form:input type="text" path="firstName" class="form-control"/>
					<div class="has-error">
						<form:errors path="firstName" cssClass="help-block" element="label"/>
					</div>
				</div>
				<div class="form-group">
					<form:label path="lastName" class="required"><spring:message code="signup.page.lastname"/></form:label>
					<form:input type="text" path="lastName" class="form-control"/>
					<div class="has-error">
						<form:errors path="lastName" cssClass="help-block" element="label"/>
					</div>
				</div>
				<button type="submit" class="btn btn-primary"><spring:message code="button.save"/></button>
			</form:form>
		</div>
	</div>
</div>