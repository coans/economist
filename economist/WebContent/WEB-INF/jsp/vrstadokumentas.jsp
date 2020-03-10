<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<h3 align="center"><spring:message code="lista.vrsta.dokumenata"/></h3>
	<p><a href="api/vrstadokumentas/new" class="btn btn-info"><spring:message code="nova.vrsta.dokumenta"/></a>&nbsp;<a href="api/nalogs" class="btn btn-primary"><spring:message code="povratak"/></a></p>
	<table class="${tableClass}" id="vrstadokumentaTable">
		<thead>
			<tr>
				<th class="text-center" style="width:100%" scope="col"><spring:message code="naziv"/></th>
				<th class="text-center" scope="col"><spring:message code="sifra"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${vrstadokumentas}" var="vrstadokumenta" varStatus="loop">	
				<tr>
					<td align="center">${vrstadokumenta.naziv}</td>
					<td align="center">${vrstadokumenta.sifra}</td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		setActiveHeader("#sifarnici");
		setActiveHeader("api/vrstadokumentas");
		setPaginationTableLabels('#vrstadokumentaTable');
	});
</script>
