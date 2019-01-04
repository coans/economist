<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
  
<div class="container">
	<div class="row">
		<div class="col-xs-5">
			<h3>${title}</h3>
			<form:form modelAttribute="nalog" method="POST" action="${action}">
				<form:hidden path="id" />
				<div class="form-group">
					<form:label path="broj" class="required">Broj</form:label>
					<form:input path="broj" type="text" class="form-control"/>
					<div class="has-error">
						<form:errors path="broj" cssClass="help-block" element="label"/>
					</div>
				</div>			
				<div class="form-group">
					<form:label path="opis" class="required">Opis</form:label>
					<form:textarea path="opis" class="form-control" />
					<div class="has-error">
						<form:errors path="opis" cssClass="help-block" element="label"/>
					</div>
				</div>
				<div class="form-group">
					<form:label path="vrstaDokumenta.id" class="required">Vrsta dokumenta</form:label>
					<form:select path="vrstaDokumenta.id" class="form-control" id="vrstaDokumenta" items="${vrstadokumentas}" itemLabel="naziv" itemValue="id"  disabled="${disabled}"/>
					<div class="has-error">
						<form:errors path="vrstaDokumenta.id" cssClass="help-block" element="label"/>
					</div>
				</div>
				<div class="form-group">
					<form:label path="napomena">Napomena</form:label>
					<form:textarea path="napomena" class="form-control" />
					<div class="has-error">
						<form:errors path="napomena" cssClass="help-block" element="label"/>
					</div>
				</div>
				<p>&nbsp;</p>					
				<button type="submit" class="btn btn btn-success"><spring:message code="button.save"/></button>
				<a class="btn btn-primary" href="api/nalogs"><spring:message code="button.cancel"/></a>
			</form:form>
			<span class="form-group"><label class="required"><font size="2"><spring:message code="page.required.fields"/></font></label></span>
		</div>
	</div>
</div>