<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<h3 align="center"><spring:message code="lista.preduzeca"/></h3>
	<p><a href="admin/preduzeces/new" class="btn btn-info"><spring:message code="novo.preduzece"/></a></p>
	<table class="${tableClass}">
		<thead>
			<tr>
				<th class="text-center" scope="col">#</th>
				<th class="text-center" scope="col"><spring:message code="naziv"/></th>
				<th class="text-center" scope="col"><spring:message code="adresa"/></th>
				<th class="text-center" scope="col"><spring:message code="telefon"/></th>
				<th class="text-center" scope="col"><spring:message code="mobilni"/></th>
				<th class="text-center" scope="col"><spring:message code="ziroracun"/></th>
				<th class="text-center" scope="col"><spring:message code="datum"/></th>
				<th class="text-center" scope="col"><spring:message code="agencija"/></th>
				<th class="text-center" scope="col"><spring:message code="akcije"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${preduzeces}" var="preduzece" varStatus="loop">	
				<tr>
					<td align="center">${loop.count}</td>
					<td align="center">${preduzece.naziv}</td>
					<td align="center">${preduzece.adresa}</td>
					<td align="center">${preduzece.telefon}</td>
					<td align="center">${preduzece.mobilni}</td>
					<td align="center">${preduzece.ziroracun}</td>
					<td align="center"><fmt:formatDate pattern = "${datumPattern}" value = "${preduzece.created}" /></td>
					<td align="center">${preduzece.agencija.naziv}</td>
					<td align="center"><a href="admin/preduzeces/edit/${preduzece.id}" title="<spring:message code='izmijeni'/>"><i class="glyphicon glyphicon-pencil"></i></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		setActiveHeader("#administration");
		setActiveHeader("admin/preduzeces");
	});
</script>
