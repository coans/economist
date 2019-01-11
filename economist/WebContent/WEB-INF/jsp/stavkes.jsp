<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<h3 align="center" style="padding-bottom: 0px;">${user.preduzece.naziv}</h3>
	<h4 align="center"><b>Nalog br: ${nalogBroj}</b></h4>
	<h4 align="center"><b>Pregled svih stavki</b></h4>
	<p><a href="api/stavkes/new/${nalogId}" class="btn btn-info">Dodaj stavku</a>&nbsp;<a href="api/nalogs" class="btn btn-primary">Povratak</a></p>
<%-- 	<label>Filter by category</label>
	<select id="category" onchange="filterFoodByCategory()">
	    <c:forEach items="${categories}" var="category">
            <option value="${category.id}" ${category.id == selectedCategoryId ? 'selected' : ''}>${category.name}</option>
	    </c:forEach>
	</select> --%>
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
	setActiveHeader("api/nalogs");
});
</script>
