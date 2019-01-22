<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<form:form modelAttribute="search" method="POST" action="${action}">
		<div class="row">
			<h3 align="center"><spring:message code="kif"/></h3>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="komitent.id"><spring:message code="komitent"/></form:label>
						<form:select path="komitent.id" class="form-control" items="${komitents}" itemLabel="naziv" itemValue="id"/>
						<div class="has-error">
							<form:errors path="komitent.id" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="datumOd" class="required"><spring:message code="datum.od"/></form:label>
						<form:input path="datumOd" type="text" class="form-control datepicker"/>
						<div class="has-error">
							<form:errors path="datumOd" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="datumDo" class="required"><spring:message code="datum.do"/></form:label>
						<form:input path="datumDo" type="text" class="form-control datepicker"/>
						<div class="has-error">
							<form:errors path="datumDo" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>				
			</div>		
			<div class="row" align="right">
				<button type="submit" class="btn btn btn-success"><spring:message code="pretraga"/></button>
				<a class="btn btn-primary" href="api/nalogs"><spring:message code="povratak"/></a>
			</div>
		</form:form>
	<p>&nbsp;</p>					
	<table class="table table-striped table-bordered">
		<thead class="thead-light">
			<tr>
				<th class="text-center" scope="col">#</th>
				<th class="text-center" scope="col"><spring:message code="broj"/></th>
				<th class="text-center" scope="col"><spring:message code="datum"/></th>
				<%-- <th class="text-center" scope="col"><spring:message code="opis"/></th> --%>
				<%-- <th class="text-center" scope="col"><spring:message code="konto"/></th> --%>
				<th class="text-center" scope="col"><spring:message code="komitent"/></th>
				<th class="text-center" scope="col"><spring:message code="iznos"/></th>
				<th class="text-center" scope="col"><spring:message code="kif.osnovica"/></th>
				<th class="text-center" scope="col"><spring:message code="kif.pdv"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${stavkes}" var="stavka" varStatus="loop">	
				<tr>
					<td align="center">${loop.count}</td>
					<td align="center"><%-- ${stavka.nalog.broj} --%></td>
					<td align="center"><fmt:formatDate pattern = "${datumPattern}" value = "${stavka.datum}" /></td>
					<%-- <td align="center">${stavka.opis}</td> --%>
					<%-- <td align="center">${stavka.kontoA.sifra} - ${stavka.kontoA.naziv}</td> --%>
					<td align="center">${stavka.komitent}</td>
					<td align="right">${stavka.ukupno}</td>
					<td align="right">${stavka.iznos}</td>
					<td align="right">${stavka.pdv}</td>
				</tr>
			</c:forEach>
			<tr><td colspan="7">&nbsp;</td></tr>
			<tr>
				<td colspan="3" class="active">&nbsp;</td>
				<td class="active" align="center"><b><spring:message code="ukupno"/></b></td>
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
		setActiveHeader("api/kif-kuf/kif");
	});
</script>
