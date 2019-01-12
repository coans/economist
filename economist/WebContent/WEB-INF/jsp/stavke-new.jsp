<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
  
<div class="container">
	<div class="row">
		<div class="col-xs-5">
			<h3>${title}</h3>
			<form:form modelAttribute="stavka" method="POST" action="${action}">
				<form:hidden path="id" />
				<form:hidden path="nalog.id" />
				<div class="form-group">
					<form:label path="datum" class="required"><spring:message code="datum"/></form:label>
					<form:input path="datum" type="text" class="form-control datepicker" id="datum"/>
					<div class="has-error">
						<form:errors path="datum" cssClass="help-block" element="label"/>
					</div>
				</div>				
				<div class="form-group">
					<form:label path="konto" class="required"><spring:message code="konto"/></form:label>
					<form:select path="konto" class="form-control" id="konto" items="${konta}" itemLabel="sifraNaziv" itemValue="id"/>
					<div class="has-error">
						<form:errors path="konto" cssClass="help-block" element="label"/>
					</div>
				</div>				
				<div class="form-group">
					<form:label path="opis" class="required"><spring:message code="opis"/></form:label>
					<form:textarea path="opis" class="form-control" />
					<div class="has-error">
						<form:errors path="opis" cssClass="help-block" element="label"/>
					</div>
				</div>
				<div class="form-group">
					<form:label path="komitent"><spring:message code="komitent"/></form:label>
					<form:select path="komitent" class="form-control" id="komitent" items="${komitents}" itemLabel="naziv" itemValue="id"/>
					<div class="has-error">
						<form:errors path="komitent" cssClass="help-block" element="label"/>
					</div>
				</div>				
				<div class="form-group">
					<form:label path="duguje" class="required"><spring:message code="duguje"/></form:label>
					<form:input path="duguje" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="duguje" cssClass="help-block" element="label"/>
					</div>
				</div>					
				<div class="form-group">
					<form:label path="potrazuje" class="required"><spring:message code="potrazuje"/></form:label>
					<form:input path="potrazuje" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="potrazuje" cssClass="help-block" element="label"/>
					</div>
				</div>
				<p>&nbsp;</p>					
				<button type="submit" class="btn btn btn-success"><spring:message code="button.save"/></button>
				<a class="btn btn-primary" href="api/stavkes/details/${stavka.nalog.id}"><spring:message code="button.cancel"/></a>
			</form:form>
			<span class="form-group"><label class="required"><font size="2"><spring:message code="page.required.fields"/></font></label></span>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		makeInputDate();
	});
</script>