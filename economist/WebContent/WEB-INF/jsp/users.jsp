<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<h3 align="center"><spring:message code="lista.user"/></h3>
	<p><a href="admin/users/new" class="btn btn-info"><spring:message code="novi.user"/></a></p>
	<table class="${tableClass}">
		<thead>
			<tr>
				<th class="text-center" scope="col">#</th>
				<th class="text-center" scope="col"><spring:message code="user.email"/></th>
				<th class="text-center" scope="col"><spring:message code="user.ime"/></th>
				<th class="text-center" scope="col"><spring:message code="user.prezime"/></th>
				<th class="text-center" scope="col"><spring:message code="user.preduzece"/></th>
				<th class="text-center" scope="col"><spring:message code="akcije"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="user" varStatus="loop">	
				<tr>
					<td align="center">${loop.count}</td>
					<td align="center">${user.email}</td>
					<td align="center">${user.firstName}</td>
					<td align="center">${user.lastName}</td>
					<td align="center">${user.preduzece.naziv}</td>
					<td align="center"><a href="admin/users/edit/${user.id}" title="<spring:message code='izmijeni'/>"><i class="glyphicon glyphicon-pencil"></i></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		setActiveHeader("#administration");
		setActiveHeader("admin/users");
	});
</script>
