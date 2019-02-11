<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
  
<div class="container">
	<div class="row">
		<h3 align="center">${title}</h3>
		<form:form modelAttribute="stavka" method="POST" action="${action}">
			<div class="col-xs-4">
				<form:hidden path="idStavka" />
				<form:hidden path="idProtivStavka" />
				<form:hidden path="idPDV" />
				<form:hidden path="nalog.id" />
				<h4 align="center"><spring:message code="stavka"/></h4>
				<div class="form-group">
					<form:label path="datum" class="required"><spring:message code="datum"/></form:label>
					<form:input path="datum" type="text" class="form-control datepicker" id="datum"/>
					<div class="has-error">
						<form:errors path="datum" cssClass="help-block" element="label"/>
					</div>
				</div>				
				<div class="form-group">
					<form:label path="kontoStavka.id" class="required"><spring:message code="konto"/></form:label>
					<form:select path="kontoStavka.id" class="form-control" id="konto" items="${konta}" itemLabel="sifraNaziv" itemValue="id"/>
					<div class="has-error">
						<form:errors path="kontoStavka.id" cssClass="help-block" element="label"/>
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
					<form:select path="komitent.id" id="komitent" class="form-control" items="${komitents}" itemLabel="naziv" itemValue="id" onchange="selectKomitent(this.value)"/>
					<div class="has-error">
						<form:errors path="komitent.id" cssClass="help-block" element="label"/>
					</div>
				</div>
				<div class="form-group">
					<form:label path="brojFakture"><spring:message code="broj.fakture"/></form:label>
					<form:input path="brojFakture" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="brojFakture" cssClass="help-block" element="label"/>
					</div>
				</div>				
				<div class="form-group">
					<form:label path="dugujeStavka" class="required"><spring:message code="duguje"/></form:label>
					<form:input path="dugujeStavka" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="dugujeStavka" cssClass="help-block" element="label"/>
					</div>
				</div>				
				<div class="form-group">
					<form:label path="potrazujeStavka" class="required"><spring:message code="potrazuje"/></form:label>
					<form:input path="potrazujeStavka" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="potrazujeStavka" cssClass="help-block" element="label"/>
					</div>
				</div>	
				<p>&nbsp;</p>		
				<button type="submit" class="btn btn btn-success"><spring:message code="button.save"/></button>
				<a class="btn btn-primary" href="api/stavkas/details/${stavka.nalog.id}"><spring:message code="button.cancel"/></a><br>
				<span class="form-group"><label class="required"><font size="2"><spring:message code="page.required.fields"/></font></label></span>			
			</div>
			<div class="col-xs-4">
				<h4 align="center"><spring:message code="protiv.stavka"/></h4>		
				<div class="form-group">
					<form:label path="kontoProtivStavka.id" class="required"><spring:message code="konto"/></form:label>
					<form:select path="kontoProtivStavka.id" class="form-control" id="konto" items="${konta}" itemLabel="sifraNaziv" itemValue="id"/>
					<div class="has-error">
						<form:errors path="kontoProtivStavka.id" cssClass="help-block" element="label"/>
					</div>
				</div>			
				<div class="form-group">
					<form:label path="dugujeProtivStavka" class="required"><spring:message code="duguje"/></form:label>
					<form:input path="dugujeProtivStavka" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="dugujeProtivStavka" cssClass="help-block" element="label"/>
					</div>
				</div>				
				<div class="form-group">
					<form:label path="potrazujeProtivStavka" class="required"><spring:message code="potrazuje"/></form:label>
					<form:input path="potrazujeProtivStavka" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="potrazujeProtivStavka" cssClass="help-block" element="label"/>
					</div>
				</div>				
			</div>
			<div class="col-xs-4" id="pdv" hidden="true">
				<h4 align="center"><spring:message code="pdv"/></h4>		
				<div class="form-group">
					<form:label path="kontoPDV.id" class="required"><spring:message code="konto"/></form:label>
					<form:select path="kontoPDV.id" class="form-control" id="konto" items="${konta}" itemLabel="sifraNaziv" itemValue="id"/>
					<div class="has-error">
						<form:errors path="kontoPDV.id" cssClass="help-block" element="label"/>
					</div>
				</div>			
				<div class="form-group">
					<form:label path="dugujePDV" class="required"><spring:message code="duguje"/></form:label>
					<form:input path="dugujePDV" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="dugujePDV" cssClass="help-block" element="label"/>
					</div>
				</div>				
				<div class="form-group">
					<form:label path="potrazujePDV" class="required"><spring:message code="potrazuje"/></form:label>
					<form:input path="potrazujePDV" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="potrazujePDV" cssClass="help-block" element="label"/>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
<script>
	$(document).ready(function() {
		makeInputDate();
		if (document.getElementById("komitent").value != -1) {
			selectKomitent(document.getElementById("komitent").value);
		}
	});
	function selectKomitent(id) {
		var baseUrl = "${baseurl}";
		var url = baseUrl + "api/komitents/usistemupdv/" + id;
		$.get(url, function(data, status){
			if (data) {//komitent je u sistemu pdv, prikazi formu za unos pdv-a
				$("#pdv").show();				
			} else {
				$("#pdv").hide();
			}
	  	});
	} 
</script>