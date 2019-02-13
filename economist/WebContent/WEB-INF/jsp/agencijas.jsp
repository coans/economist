<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<h3 align="center"><spring:message code="lista.agencija"/></h3>
	<p><a href="admin/agencijas/new" class="btn btn-info"><spring:message code="nova.agencija"/></a></p>
	<table class="${tableClass}">
		<thead>
			<tr>
				<th class="text-center" scope="col">#</th>
				<th class="text-center" scope="col"><spring:message code="naziv"/></th>
				<th class="text-center" scope="col"><spring:message code="datum"/></th>
				<th class="text-center" scope="col"><spring:message code="akcije"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${agencijas}" var="agencija" varStatus="loop">	
				<tr>
					<td align="center">${loop.count}</td>
					<td align="center">${agencija.naziv}</td>
					<td align="center"><fmt:formatDate pattern = "${datumPattern}" value = "${agencija.created}" /></td>
					<td align="center"><a href="admin/agencijas/edit/${agencija.id}" title="<spring:message code='izmijeni'/>"><i class="glyphicon glyphicon-pencil"></i></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		setActiveHeader("#administration");
		setActiveHeader("admin/agencijas");
	});
</script>
