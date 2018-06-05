<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<div class="row">
		<div class="col-xs-5">
			<h3><spring:message code="posts.page.header"/></h3>
			<p>You can add your post here. It will be shown on the Wall.</p>
			<p>&nbsp;</p>
			<form:form modelAttribute="postmodel" method="POST" action="${action}">
				<div class="form-group">
					<form:label path="title" class="required"><spring:message code="posts.page.title"/></form:label>
					<form:input path="title" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="title" cssClass="help-block" element="label"/>
					</div>
				</div>
				<div class="form-group">
					<form:label path="message" class="required"><spring:message code="posts.page.message"/></form:label>
					<form:textarea path="message" class="form-control" />
					<div class="has-error">
						<form:errors path="message" cssClass="help-block" element="label"/>
					</div>
				</div>
				<button type="submit" class="btn btn btn-primary"><spring:message code="button.save"/></button>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	setActiveHeader("my/posts");
</script>