<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<h3 align="center"><spring:message code="lista.komitenata"/></h3>
	<p><a href="api/komitents/new" class="btn btn-info"><spring:message code="novi.komitent"/></a>&nbsp;<a href="api/nalogs" class="btn btn-primary"><spring:message code="povratak"/></a></p>
	<table class="${tableClass}">
		<thead>
			<tr>
				<th class="text-center" scope="col">#</th>
				<th class="text-center" scope="col"><spring:message code="naziv"/></th>
				<th class="text-center" scope="col"><spring:message code="mjesto"/></th>
				<th class="text-center" scope="col"><spring:message code="adresa"/></th>
				<th class="text-center" scope="col"><spring:message code="u.sistemu.pdv"/></th>
				<th class="text-center" scope="col"><spring:message code="lokacija"/></th>
				<th class="text-center" scope="col"><spring:message code="ziroracun"/></th>
				<th class="text-center" scope="col"><spring:message code="telefon"/></th>
				<th class="text-center" scope="col"><spring:message code="napomena"/></th>
				<th class="text-center" scope="col"><spring:message code="akcije"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${komitents}" var="komitent" varStatus="loop">	
				<tr>
					<td align="center">${loop.count}</td>
					<td align="center">${komitent.naziv}</td>
					<td align="center">${komitent.mesto}</td>
					<td align="center">${komitent.adresa}</td>
					<c:if test="${komitent.usistemupdv}"><td align="center"><spring:message code="da"/></td></c:if>
					<c:if test="${not komitent.usistemupdv}"><td align="center"><spring:message code="ne"/></td></c:if>
					<td align="center">${komitent.lokacija}</td>
					<td align="center">${komitent.ziroracun}</td>
					<td align="center">${komitent.telefon}</td>
					<td align="center">${komitent.napomena}</td>
					<td align="center"><a href="api/komitents/edit/${komitent.id}" title="<spring:message code='izmijeni'/>"><i class="glyphicon glyphicon-pencil"></i></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		setActiveHeader("#sifarnici");
		setActiveHeader("komitents");
	});
</script>
