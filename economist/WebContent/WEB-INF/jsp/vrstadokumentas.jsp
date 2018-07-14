<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<p><a href="vrstadokumentas/new" class="btn btn-info">Nova vrsta dokumenta</a></p>
	<table class="table table-striped">
		<thead>
			<tr>
				<th class="text-center" scope="col">#</th>
				<th class="text-center" scope="col">&#352;ifra</th>
				<th class="text-center" scope="col">Naziv</th>
				<th class="text-center" scope="col">Prikazi u KIF</th>
				<th class="text-center" scope="col">Prikazi u KUF</th>
				<th class="text-center" scope="col">Akcija</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${vrstadokumentas}" var="vrstadokumenta" varStatus="loop">	
				<tr>
					<td align="center">${loop.count}</td>
					<td align="center">${vrstadokumenta.sifra}</td>
					<td align="center">${vrstadokumenta.naziv}</td>
					<c:if test="${vrstadokumenta.prikaziukif}">
						<td align="center">Da</td>
					</c:if>
					<c:if test="${ not vrstadokumenta.prikaziukif}">
						<td align="center">Ne</td>
					</c:if>

					<c:if test="${vrstadokumenta.prikaziukuf}">
						<td align="center">Da</td>
					</c:if>
					<c:if test="${ not vrstadokumenta.prikaziukuf}">
						<td align="center">Ne</td>
					</c:if>
															
					<td align="center">
						<a href="vrstadokumentas/edit/${vrstadokumenta.id}" title="Izmijeni"><i class="glyphicon glyphicon-pencil"></i></a>
						&nbsp;
						<a href="#" data-href="vrstadokumentas/delete/${vrstadokumenta.id}" data-toggle="modal" data-target="#confirmDeleteId" title="Obri&#353;i"><i class="glyphicon glyphicon-remove"></i></a>
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
