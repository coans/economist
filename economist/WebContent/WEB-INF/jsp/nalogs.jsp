<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<h3 align="center" style="padding-bottom: 0px;"><b><spring:message code="pregled.svih.naloga"/></b></h3>
	<h4 align="center" style="padding-bottom: 0px;">${user.preduzece.naziv}</h4>
	<div class="row">
		<div class="col-xs-7">
			<p><a href="api/nalogs/new" class="btn btn-info"><spring:message code="novi.nalog"/></a></p>
		</div>
		<div class="col-xs-2" style="padding-left: 6%">
			<label><spring:message code="nalog.filter"/></label>
		</div>
		<div class="col-xs-3" align="right">
			<select class="form-control" id="status" onchange="filterNalog()">
	    		<c:forEach items="${statuses}" var="s">
	    	        <option value="${s}" ${s == statusSelected ? 'selected' : ''}><c:if test="${s == 0}"><spring:message code="aktivan"/></c:if> <c:if test="${s == 1}"><spring:message code="zakljucan"/></c:if></option>
			    </c:forEach>
			</select>
		</div>
	</div>
	<table class="${tableClass}" id="nalogTable">
		<thead>
			<tr>
				<th class="text-center" scope="col">#</th>
				<th class="text-center" scope="col"><spring:message code="broj"/></th>
				<th class="text-center" scope="col"><spring:message code="nalog.datum"/></th>
				<th class="text-center" scope="col"><spring:message code="vrsta.dokumenta"/></th>
				<th class="text-center" scope="col"><spring:message code="opis"/></th>
				<th class="text-center" scope="col"><spring:message code="napomena"/></th>
				<th class="text-center" scope="col"><spring:message code="status"/></th>
				<th class="text-center" scope="col"><spring:message code="duguje"/></th>
				<th class="text-center" scope="col"><spring:message code="potrazuje"/></th>
				<th class="text-center" scope="col"><spring:message code="saldo"/></th>
				<th class="text-center" scope="col"><spring:message code="akcije"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${nalogs}" var="nalog" varStatus="loop">	
				<tr>
					<td align="center">${loop.count}</td>
					<c:if test="${nalog.zakljucan == 0}">
						<td align="center"><a href="api/stavkas/details/${nalog.id}" title="<spring:message code='detalji'/>">${nalog.broj}</a></td>
					</c:if>
					<c:if test="${nalog.zakljucan == 1}">
						<td align="center">${nalog.broj}</td>
					</c:if>
					<td align="center"><fmt:formatDate pattern = "${datumPattern}" value = "${nalog.created}" /> / <fmt:formatDate pattern = "${datumPattern}" value = "${nalog.modified}" /></td>
					<td align="center">${nalog.vrstaDokumenta.naziv}</td>
					<td align="center">${nalog.opis}</td>
					<td align="center">${nalog.napomena}</td>
					<c:if test="${nalog.zakljucan == 1}"><td align="center"><spring:message code="zakljucan"/></td></c:if>
					<c:if test="${nalog.zakljucan == 0}"><td align="center"><spring:message code="aktivan"/></td></c:if>
					<td align="right">${nalog.duguje}</td>
					<td align="right">${nalog.potrazuje}</td>
					<td align="right">${nalog.saldo}</td>
					<td align="center">
						<c:if test="${nalog.zakljucan == 0}">
							<a href="api/stavkas/new/${nalog.id}" title="<spring:message code='dodaj.stavku'/>"><i class="glyphicon glyphicon-plus"></i></a>
							 &nbsp;
							<%-- <a href="#" data-href="api/nalogs/zakljucaj/${nalog.id}" data-toggle="modal" data-target="#confirmDeleteId" title="Delete"><i class="glyphicon glyphicon-remove"></i></a> --%>
							<a href="api/nalogs/zakljucaj/${nalog.id}" title="<spring:message code='zakljucaj.nalog'/>"><i class="glyphicon glyphicon-ban-circle"></i></a>
						</c:if>
						<c:if test="${nalog.zakljucan == 1}">	
							&nbsp;
							<a href="api/nalogs/otkljucaj/${nalog.id}" title="<spring:message code='otkljucaj.nalog'/>"><i class="glyphicon glyphicon-ok"></i></a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td class="active" colspan="6">&nbsp;</td>
				<td class="active" align="center"><b><spring:message code="ukupno"/></b></td>
				<td class="ukupno" align="right"><b>${duguje}</b></td>
				<td class="ukupno" align="right"><b>${potrazuje}</b></td>
				<td class="ukupno" align="right"><b>${saldo}</b></td>
				<td class="ukupno">&nbsp;</td>
			</tr>
		</tfoot>
    </table>
</div>
<!-- <div class="modal fade" id="confirmDeleteId" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
</div> -->
<script type="text/javascript">
	$(document).ready(function() {
		setActiveHeader("api/nalogs");
		setPaginationTableLabels('#nalogTable');
	});
/* 	$('#confirmDeleteId').on('show.bs.modal', function(e) {
	    $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
	}); */
	
	function filterNalog() {
		var baseUrl = "${baseurl}";
		location.href = baseUrl + "api/nalogs?status=" + $("#status").val();
	}
</script>
