<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<div class="container">
	<div class="row">
		<div class="col-xs-5">
			<h3><spring:message code="login.page.login"/> <a href="signup"><spring:message code="login.page.signup"/></a></h3>
			<form id="login_form" method="post" action="j_spring_security_check" novalidate="novalidate">
				<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
					<div class="alert alert-danger">
						Login failed: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
					</div>
				</c:if>
				<div class="form-group">
					<label for="email"><spring:message code="login.page.email"/></label>
					<input type="text" class="form-control" name="email" id="email" autofocus="autofocus" tabindex=1>
				</div>
				<div class="form-group">
					<a class="pull-right" href="recovery"><spring:message code="login.page.forgotpass"/></a>
					<label for="password"><spring:message code="login.page.password"/></label>
					<input type="password" class="form-control" name="password" id="password" tabindex=2>
				</div>
				<div class="checkbox pull-right">
					<label> <input type="checkbox" checked="checked" name="_spring_security_remember_me" /> <spring:message code="login.page.rememberme"/>
					</label>
				</div>
				<button type="submit" class="btn btn btn-primary"><spring:message code="button.login"/></button>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
	setActiveHeader("login");
	setTimeZoneCookie(false);
</script>