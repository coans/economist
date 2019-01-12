<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<h3 align="center"><spring:message code="lista.vrsta.dokumenata"/></h3>
	<p><a href="api/vrstadokumentas/new" class="btn btn-info"><spring:message code="nova.vrsta.dokumenta"/></a>&nbsp;<a href="api/nalogs" class="btn btn-primary"><spring:message code="povratak"/></a></p>
	<table class="${tableClass}">
		<thead>
			<tr>
				<th class="text-center" scope="col"><spring:message code="sifra"/></th>
				<th class="text-center" scope="col"><spring:message code="naziv"/></th>
				<th class="text-center" scope="col"><spring:message code="prikazi.u.kif"/></th>
				<th class="text-center" scope="col"><spring:message code="prikazi.u.kuf"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${vrstadokumentas}" var="vrstadokumenta" varStatus="loop">	
				<tr>
					<td align="center">${vrstadokumenta.sifra}</td>
					<td align="center">${vrstadokumenta.naziv}</td>
					<c:if test="${vrstadokumenta.prikaziukif}">
						<td align="center"><spring:message code="da"/></td>
					</c:if>
					<c:if test="${ not vrstadokumenta.prikaziukif}">
						<td align="center"><spring:message code="ne"/></td>
					</c:if>

					<c:if test="${vrstadokumenta.prikaziukuf}">
						<td align="center"><spring:message code="da"/></td>
					</c:if>
					<c:if test="${ not vrstadokumenta.prikaziukuf}">
						<td align="center"><spring:message code="ne"/></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
    </table>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		setActiveHeader("#sifarnici");
		setActiveHeader("vrstadokumentas");
	});
</script>
