<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<form:form modelAttribute="search" method="POST" action="${action}">
		<div class="row">
			<h3>Bruto bilans (po datumu i kontu)</h3>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="kontoOdBilans">Konto od</form:label>
						<form:select path="kontoOdBilans" class="form-control" items="${kontaKratko}"/>
						<div class="has-error">
							<form:errors path="kontoOdBilans" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="kontoDoBilans">Konto do</form:label>
						<form:select path="kontoDoBilans" class="form-control" items="${kontaKratko}"/>
						<div class="has-error">
							<form:errors path="kontoDoBilans" cssClass="help-block" element="label"/>
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
				<th class="text-center" scope="col">Naziv konta</th>
				<th class="text-center" scope="col">Duguje</th>
				<th class="text-center" scope="col">Potrazuje</th>
			</tr>
		</thead>
		<tbody>
  					<c:forEach items="${konto1}" var="bilans" varStatus="loop">	
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
