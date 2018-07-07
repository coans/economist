<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<form:form modelAttribute="search" method="POST" action="${action}">
		<div class="row">
			<h3>Total po kontima i komitentima</h3>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="kontoOd">Konto</form:label>
						<form:select path="kontoOd" class="form-control" items="${konta}" itemLabel="naziv" itemValue="id"/>
						<div class="has-error">
							<form:errors path="kontoOd" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>
<%-- 				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="kontoDo">Konto do</form:label>
						<form:select path="kontoDo" class="form-control" items="${konta}" itemLabel="naziv" itemValue="id"/>
						<div class="has-error">
							<form:errors path="kontoDo" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div> --%>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="komitent">Komitent</form:label>
						<form:select path="komitent" class="form-control" items="${komitents}" itemLabel="naziv" itemValue="id"/>
						<div class="has-error">
							<form:errors path="komitent" cssClass="help-block" element="label"/>
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
			</div>		
			<div class="row" align="right">
				<button type="submit" class="btn btn btn-success">Pretraga</button>
				<a class="btn btn-primary" href="nalogs"><spring:message code="button.cancel"/></a>
			</div>
		</form:form>
	<p>&nbsp;</p>					
	<table class="table table-striped table-bordered">
		<thead class="thead-light">
			<tr>
				<th class="text-center" scope="col">#</th>
				<th class="text-center" scope="col">Konto</th>
				<th class="text-center" scope="col">Naziv komitenta</th>
				<th class="text-center" scope="col">Duguje</th>
				<th class="text-center" scope="col">Potrazuje</th>
				<th class="text-center" scope="col">Saldo</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${beans}" var="bean" varStatus="loop">	
				<tr>
					<td align="center">${loop.count}</td>
					<td align="center">${bean.konto.sifra}</td>
					<td align="center">${bean.komitent.naziv}</td>
					<td align="right">${bean.duguje}</td>
					<td align="right">${bean.potrazuje}</td>
					<td align="right">${bean.saldo}</td>
				</tr>
			</c:forEach>
			<!-- <tr><td colspan="7">&nbsp;</td></tr> -->
			<tr>
				<td class="active">&nbsp;</td>
				<td class="active">&nbsp;</td>
				<td class="active" align="right"><b>Ukupno:</b></td>
				<td class="danger" align="right"><b>${dugujeBean}</b></td>
				<td class="success" align="right"><b>${potrazujeBean}</b></td>
				<td class="success" align="right"><b>${saldoBean}</b></td>
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
