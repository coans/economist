<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<div class="row">
		<div class="col-xs-5">
			<h3><spring:message code="reset.page.header"/></h3>
			<c:choose>
				<c:when test="${empty resetmodel}">
					<p><spring:message code="reset.page.expired"/></p>
				</c:when>
				<c:otherwise>
					<form:form modelAttribute="resetmodel" method="POST" action="${action}">
						<div class="form-group">
							<form:label path="password" class="required"><spring:message code="signup.page.password"/></form:label>
							<form:input type="password" path="password" class="form-control"/>
							<div class="text-muted"><spring:message code="signup.page.password.info"/></div>
							<div class="has-error">
								<form:errors path="password" cssClass="help-block" element="label"/>
							</div>
						</div>
						<div class="form-group">
							<form:label path="repassword" class="required"><spring:message code="reset.page.repassword"/></form:label>
							<form:input type="password" path="repassword" class="form-control"/>
							<div class="has-error">
								<form:errors path="repassword" cssClass="help-block" element="label"/>
							</div>
						</div>
						<form:hidden path="email"/>
						<form:hidden path="id"/>
						<button type="submit" class="btn btn-primary"><spring:message code="button.save"/></button>
					</form:form>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>