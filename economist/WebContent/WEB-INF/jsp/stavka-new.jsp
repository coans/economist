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
					<form:label path="konto.id" class="required"><spring:message code="konto"/></form:label>
					<form:select path="konto.id" class="form-control" id="konto" items="${konta}" itemLabel="sifraNaziv" itemValue="id"/>
					<div class="has-error">
						<form:errors path="konto.id" cssClass="help-block" element="label"/>
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
					<form:label path="komitent.id"><spring:message code="komitent"/></form:label>
					<form:select path="komitent.id" class="form-control" items="${komitents}" itemLabel="naziv" itemValue="id" onchange="selectKomitent(this.value)"/>
					<div class="has-error">
						<form:errors path="komitent.id" cssClass="help-block" element="label"/>
					</div>
				</div>				
				<div class="form-group">
					<form:label path="duguje" class="required" id="labelDuguje"><spring:message code="duguje"/></form:label>
					<form:input path="duguje" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="duguje" cssClass="help-block" element="label"/>
					</div>
				</div>				
				<div class="form-group">
					<form:label path="potrazuje" class="required" id="labelPotrazuje"><spring:message code="potrazuje"/></form:label>
					<form:input path="potrazuje" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="potrazuje" cssClass="help-block" element="label"/>
					</div>
				</div>				
				<p>&nbsp;</p>					
				<button type="submit" class="btn btn btn-success"><spring:message code="button.save"/></button>
				<a class="btn btn-primary" href="api/stavkas/details/${stavka.nalog.id}"><spring:message code="button.cancel"/></a>
			</form:form>
			<span class="form-group"><label class="required"><font size="2"><spring:message code="page.required.fields"/></font></label></span>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		makeInputDate();
	});
	function selectKomitent(id) {
		var baseUrl = "${baseurl}";
		var url = baseUrl + "api/komitents/usistemupdv/" + id;
		$.get(url, function(data, status){
			if (data) {//komitent je u sistemu pdv, promijeni labele
				$("#labelDuguje").text('<spring:message code="duguje"/> bez PDV-a');
				$("#labelPotrazuje").text('<spring:message code="potrazuje"/> bez PDV-a');
			} else {
				$("#labelDuguje").text('<spring:message code="duguje"/>');
				$("#labelPotrazuje").text('<spring:message code="potrazuje"/>');
			}
	  	});
	} 
</script>