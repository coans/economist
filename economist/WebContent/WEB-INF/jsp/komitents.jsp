<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<h3 align="center">Lista komitenata</h3>
	<p><a href="komitents/new" class="btn btn-info">Novi komitent</a></p>
	<table class="${tableClass}">
		<thead>
			<tr>
				<th class="text-center" scope="col">#</th>
				<th class="text-center" scope="col">Naziv</th>
				<th class="text-center" scope="col">Mjesto</th>
				<th class="text-center" scope="col">Adresa</th>
				<th class="text-center" scope="col">U sistemu PDV-a</th>
				<th class="text-center" scope="col">Lokacija</th>
				<th class="text-center" scope="col">&#381;iro-ra&#269;un</th>
				<th class="text-center" scope="col">Telefon</th>
				<th class="text-center" scope="col">Napomena</th>
				<th class="text-center" scope="col">Akcije</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${komitents}" var="komitent" varStatus="loop">	
				<tr>
					<td align="center">${loop.count}</td>
					<td align="center">${komitent.naziv}</td>
					<td align="center">${komitent.mesto}</td>
					<td align="center">${komitent.adresa}</td>
					<c:if test="${komitent.usistemupdv}"><td align="center">Da</td></c:if>
					<c:if test="${not komitent.usistemupdv}"><td align="center">Ne</td></c:if>
					<td align="center">${komitent.lokacija}</td>
					<td align="center">${komitent.ziroracun}</td>
					<td align="center">${komitent.telefon}</td>
					<td align="center">${komitent.napomena}</td>
					<td align="center">
						<a href="komitents/edit/${komitent.id}" title="Izmijeni"><i class="glyphicon glyphicon-pencil"></i></a>
						&nbsp;
						<%-- <a href="#" data-href="komitents/delete/${komitent.id}" data-toggle="modal" data-target="#confirmDeleteId" title="Obri&#353;i"><i class="glyphicon glyphicon-remove"></i></a> --%>
					</td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
</div>
<div class="modal fade" id="confirmDeleteId" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" align="center"><b>Potvrda brisanja</b></div>
            <div class="modal-body" align="center">Da li ste sigurni da &#382;elite obrisati ovaj red?</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Odustani</button>
                <a class="btn btn-danger btn-ok">Obri&#353;i</a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
	$('#confirmDeleteId').on('show.bs.modal', function(e) {
	    $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
	});
</script>
