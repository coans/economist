<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<form:form modelAttribute="search" method="POST" action="${action}">
		<div class="row">
			<h3>Sintetika od konta do konta</h3>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="kontoOd">Konto od</form:label>
						<form:select path="kontoOd" class="form-control" items="${kontos}" itemLabel="sifraNaziv" itemValue="id"/>
						<div class="has-error">
							<form:errors path="kontoOd" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="kontoDo">Konto do</form:label>
						<form:select path="kontoDo" class="form-control" items="${kontos}" itemLabel="sifraNaziv" itemValue="id"/>
						<div class="has-error">
							<form:errors path="kontoDo" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="datumOd" class="required">Datum od</form:label>
						<form:input path="datumOd" type="text" class="form-control datepicker"/>
						<div class="has-error">
							<form:errors path="datumOd" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="datumDo" class="required">Datum do</form:label>
						<form:input path="datumDo" type="text" class="form-control datepicker"/>
						<div class="has-error">
							<form:errors path="datumDo" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>	
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="komitent">Komitent</form:label>
						<form:select path="komitent" class="form-control" items="${komitents}" itemLabel="naziv" itemValue="id"/>
						<div class="has-error">
							<form:errors path="komitent" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>							
			</div>		
			<div class="row" align="right">
				<button type="submit" class="btn btn btn-success">Pretraga</button>
				<a class="btn btn-primary" href="nalogs"><spring:message code="button.cancel"/></a>
			</div>
		</form:form>
	<p>&nbsp;</p>					
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
	$(document).ready(function() {
		makeInputDate();
		setActiveHeader("#analitika");
	});
	$('#confirmDeleteId').on('show.bs.modal', function(e) {
	    $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
	});
	
	function filterFoodByCategory() {
		var baseUrl = "${baseurl}";
		location.href = baseUrl + "foods?categoryId=" + $("#category").val();
	}
</script>
