<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="java.util.*" %>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  
<div class="container">
	<div class="row">
		<div class="col-xs-5">
			<h3>${title}</h3>
			<form:form modelAttribute="nalog" method="POST" action="${action}">
				<form:hidden path="id" />
				<div class="form-group">
					<form:label path="broj" class="required">Broj</form:label>
					<form:input path="broj" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="broj" cssClass="help-block" element="label"/>
					</div>
				</div>
				<div class="form-group">
					<form:label path="datum" class="required">Datum</form:label>
					<form:input path="datum" type="text" class="form-control datepicker" id="datum"/>
					<div class="has-error">
						<form:errors path="datum" cssClass="help-block" element="label"/>
					</div>
				</div>				
				<div class="form-group">
					<form:label path="konto" class="required">Konto</form:label>
					<form:select path="konto" class="form-control" id="konto" items="${konta}" itemLabel="naziv" itemValue="id"/>
					<div class="has-error">
						<form:errors path="konto" cssClass="help-block" element="label"/>
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
					<form:label path="vrstaDokumenta" class="required">Vrsta dokumenta</form:label>
					<form:select path="vrstaDokumenta" class="form-control" id="vrstaDokumenta" items="${vrstaDokumenta}"/>
					<div class="has-error">
						<form:errors path="vrstaDokumenta" cssClass="help-block" element="label"/>
					</div>
				</div>
				<div class="form-group">
					<form:label path="duguje" class="required">Duguje</form:label>
					<form:input path="duguje" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="duguje" cssClass="help-block" element="label"/>
					</div>
				</div>					
				<div class="form-group">
					<form:label path="potrazuje" class="required">Potrazuje</form:label>
					<form:input path="potrazuje" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="potrazuje" cssClass="help-block" element="label"/>
					</div>
				</div>
				<p>&nbsp;</p>					
				<button type="submit" class="btn btn btn-success"><spring:message code="button.save"/></button>
				<a class="btn btn-primary" href="nalogs"><spring:message code="button.cancel"/></a>
			</form:form>
			<span class="form-group"><label class="required"><font size="2"><spring:message code="page.required.fields"/></font></label></span>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		$( function() {
			$("#datum").datepicker({ dateFormat: 'dd.mm.yy.', firstDay: 1,dayNamesMin: [ "Ne", "Po", "Ut", "Sr", "&#268;e", "Pe", "Su" ], monthNames: [ "Januar", "Februar", "Mart", "April", "Maj", "Juni", "Juli", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar" ] }).datepicker("setDate", new Date());
		} );
	});
</script>