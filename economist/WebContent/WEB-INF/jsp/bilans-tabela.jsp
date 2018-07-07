<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<c:out value="${param.myVar}"/>
<table class="table table-striped table-bordered">
	<thead class="thead-light">
		<tr>
			<th class="text-center" scope="col">#</th>
			<th class="text-center" scope="col">Konto</th>
			<th class="text-center" scope="col">Naziv konta</th>
			<th class="text-center" scope="col">Duguje</th>
			<th class="text-center" scope="col">Potrazuje</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${param.myVar}" var="bilans" varStatus="loop">	
			<tr>
				<td align="center">${loop.count}</td>
				<td align="center">${bilans.konto.sifra}</td>
				<td align="center">${bilans.konto.naziv}</td>
				<td align="right">${bilans.duguje}</td>
				<td align="right">${bilans.potrazuje}</td>
			</tr>
		</c:forEach>
		<tr>
			<td class="active">&nbsp;</td>
			<td class="active">&nbsp;</td>
			<td class="active" align="right"><b>Saldo:</b></td>
			<td class="danger" align="right"><b>${dugujeKonto1}</b></td>
			<td class="success" align="right"><b>${potrazujeKonto1}</b></td>
		</tr>
	</tbody>
</table>
