<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<h3 align="center" style="padding-bottom: 0px;">${user.preduzece.naziv}</h3>
	<h4 align="center"><b>Nalog br: ${nalogBroj}</b></h4>
	<h4 align="center"><b>Pregled svih stavki</b></h4>
	<p><a href="api/stavkes/new/${nalogId}" class="btn btn-info">Dodaj stavku</a></p>
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
				<!-- <th class="text-center" scope="col">Akcija</th> -->
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
					<%-- <td align="center">
						<a href="nalogs/add/${nalog.id}" title="Dodaj stavku"><i class="glyphicon glyphicon-plus"></i></a>
						&nbsp;
						<a href="#" data-href="foods/delete/${food.id}" data-toggle="modal" data-target="#confirmDeleteId" title="Delete"><i class="glyphicon glyphicon-remove"></i></a>
					</td> --%>
				</tr>
			</c:forEach>
			<tr><td colspan="9">&nbsp;</td></tr>
			<tr>
				<td class="active">&nbsp;</td>
				<td class="active">&nbsp;</td>
				<td class="active">&nbsp;</td>
				<td class="active">&nbsp;</td>
				<td class="active">&nbsp;</td>
				<td class="active" align="center"><b>Ukupno:</b></td>
				<td class="danger" align="right"><b>${duguje}</b></td>
				<td class="success" align="right"><b>${potrazuje}</b></td>
				<td class="info" align="right"><b>${saldo}</b></td>
				<!-- <td class="active">&nbsp;</td> -->	
			</tr>			
		</tbody>
    </table>
</div>
<div class="modal fade" id="confirmDeleteId" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" align="center"><b>Confirm Delete</b></div>
            <div class="modal-body" align="center">Are you sure you want to delete this item?</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a class="btn btn-danger btn-ok">Delete</a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
	$('#confirmDeleteId').on('show.bs.modal', function(e) {
	    $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
	});
	
	function filterFoodByCategory() {
		var baseUrl = "${baseurl}";
		location.href = baseUrl + "foods?categoryId=" + $("#category").val();
	}
</script>
