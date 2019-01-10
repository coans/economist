<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<form:form modelAttribute="search" method="POST" action="${action}">
		<div class="row">
			<h3 align="center">Sintetika od konta do konta</h3>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="kontoOd.id">Konto od</form:label>
						<form:select path="kontoOd.id" class="form-control" items="${kontos}" itemLabel="sifraNaziv" itemValue="id"/>
						<div class="has-error">
							<form:errors path="kontoOd.id" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="kontoDo.id">Konto do</form:label>
						<form:select path="kontoDo.id" class="form-control" items="${kontos}" itemLabel="sifraNaziv" itemValue="id"/>
						<div class="has-error">
							<form:errors path="kontoDo.id" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="datumOd" class="required">Datum od</form:label>
						<form:input path="datumOd" type="text" class="form-control datepicker"/>
						<div class="has-error">
							<form:errors path="datumOd" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="datumDo" class="required">Datum do</form:label>
						<form:input path="datumDo" type="text" class="form-control datepicker"/>
						<div class="has-error">
							<form:errors path="datumDo" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>	
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="komitent.id">Komitent</form:label>
						<form:select path="komitent.id" class="form-control" items="${komitents}" itemLabel="naziv" itemValue="id"/>
						<div class="has-error">
							<form:errors path="komitent.id" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>							
			</div>		
			<div class="row" align="right">
				<button type="submit" class="btn btn btn-success">Pretraga</button>
				<a class="btn btn-primary" href="api/nalogs"><spring:message code="button.cancel"/></a>
			</div>
		</form:form>
	<p>&nbsp;</p>					
	<table class="${tableClass}">
		<thead>
			<tr>
				<th class="text-center" scope="col">#</th>
				<th class="text-center" scope="col">Broj</th>
				<th class="text-center" scope="col">Datum</th>
				<th class="text-center" scope="col">Opis</th>
				<th class="text-center" scope="col">Konto</th>
				<th class="text-center" scope="col">Komitent</th>
				<th class="text-center" scope="col">Duguje</th>
				<th class="text-center" scope="col">Potrazuje</th>
				<th class="text-center" scope="col">Saldo</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${stavkes}" var="stavka" varStatus="loop">	
				<tr>
					<td align="center">${loop.count}</td>
					<td align="center">${stavka.nalog.broj}</a></td>
					<td align="center"><fmt:formatDate pattern = "${datumPattern}" value = "${stavka.datum}" /></td>
					<td align="center">${stavka.opis}</td>
					<td align="center">${stavka.konto.sifra} - ${stavka.konto.naziv}</td>
					<td align="center">${stavka.komitent.naziv}</td>
					<td align="right">${stavka.duguje}</td>
					<td align="right">${stavka.potrazuje}</td>
					<td align="right">${stavka.saldo}</td>
				</tr>
			</c:forEach>
			<tr><td colspan="9">&nbsp;</td></tr>
			<tr>
				<td colspan="5" class="active">&nbsp;</td>
				<td class="active" align="center"><b>Ukupno:</b></td>
				<td class="danger" align="right"><b>${duguje}</b></td>
				<td class="success" align="right"><b>${potrazuje}</b></td>
				<td class="info" align="right"><b>${saldo}</b></td>
			</tr>			
		</tbody>
    </table>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		makeInputDate();
		setActiveHeader("#izvjestaji");
		setActiveHeader("api/sintetika");
	});
</script>
