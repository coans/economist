<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<h3 align="center" style="padding-bottom: 0px;"><b><spring:message code="pregled.svih.stavki"/></b></h3>
	<h4 align="center"><b><spring:message code="nalog.broj"/> ${nalogBroj}</b></h4>
	<h4 align="center" style="padding-bottom: 0px;">${user.preduzece.naziv}</h4>
	<p><a href="api/stavkas/new/${nalogId}" class="btn btn-info"><spring:message code="dodaj.stavku"/></a>&nbsp;<a href="api/nalogs" class="btn btn-primary"><spring:message code="povratak"/></a></p>
	<table class="${tableClass}" id="stavkeTable">
		<thead>
			<tr>
				<th class="text-center" style="width:5%" scope="col">#</th>
				<th class="text-center" style="width:10%" scope="col"><spring:message code="broj"/></th>
				<th class="text-center" style="width:10%" scope="col"><spring:message code="datum"/></th>
				<th class="text-center" style="width:15%" scope="col"><spring:message code="opis"/></th>
				<th class="text-center" style="width:10%" scope="col"><spring:message code="konto"/></th>
				<th class="text-center" style="width:10%" scope="col"><spring:message code="komitent"/></th>
				<th class="text-center" style="width:10%" scope="col"><spring:message code="duguje"/></th>
				<th class="text-center" style="width:10%" scope="col"><spring:message code="potrazuje"/></th>
				<th class="text-center" style="width:10%" scope="col"><spring:message code="saldo"/></th>
				<th class="text-center" style="width:10%" scope="col"><spring:message code="akcije"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${stavkes}" var="stavka" varStatus="loop">	
				<tr>
					<td align="center">${loop.count}</td>
					<td align="center">${stavka.nalog.broj}</td>
					<td align="center"><fmt:formatDate pattern = "${datumPattern}" value = "${stavka.datum}" /></td>
					<td align="center">${stavka.opis}</td>
					<td align="center">${stavka.kontoStavka.sifra} - ${stavka.kontoStavka.naziv}</td>
					<c:if test="${ not empty stavka.komitent.naziv}">
						<td align="center">${stavka.komitent.naziv}(${stavka.komitent.lokacija})</td>
					</c:if>
					<c:if test="${empty stavka.komitent.naziv}">
						<td align="center">${stavka.komitent.naziv}</td>
					</c:if>
					<td align="right">${stavka.dugujeStavka}</td>
					<td align="right">${stavka.potrazujeStavka}</td>
					<td align="right">${stavka.saldoStavka}</td>
					<td align="center"><a href="api/stavkas/edit/${stavka.idStavka}" title="<spring:message code='izmijeni'/>"><i class="glyphicon glyphicon-pencil"></i></a>
				</tr>
			</c:forEach>
			<!-- <tr><td colspan="10">&nbsp;</td></tr> -->
						
		</tbody>
		<tfoot>
			<tr>
				<td class="active" colspan="5">&nbsp;</td>
				<td class="active" align="center"><b><spring:message code="ukupno"/></b></td>
				<td class="ukupno" align="right"><b>${duguje}</b></td>
				<td class="ukupno" align="right"><b>${potrazuje}</b></td>
				<td class="ukupno" align="right"><b>${saldo}</b></td>
				<td class="ukupno">&nbsp;</td>
			</tr>
		</tfoot>
    </table>
</div>
<script type="text/javascript">
$(document).ready(function() {
	setActiveHeader("api/nalogs");
	setPaginationTableLabels('#stavkeTable');
});
</script>
