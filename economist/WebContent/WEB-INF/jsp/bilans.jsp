<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<form:form modelAttribute="search" method="POST" action="${action}">
		<div class="row">
			<h3 align="center" style="padding-bottom: 0px;">BRUTO BILANS - SINTETIKA</h3>
			<h4 align="center">PO DATUMU DOKUMENTA</h4>
			<h4 align="center">Konto od: ${search.kontoOdBilans}     do: ${search.kontoDoBilans}</h4>
			<h4 align="center">Datum: <fmt:formatDate pattern = "${datumPattern}" value = "${search.datumOd}" /> - <fmt:formatDate pattern = "${datumPattern}" value = "${search.datumDo}" /></h4>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="kontoOdBilans">Konto od</form:label>
						<form:select path="kontoOdBilans" class="form-control" items="${klaseKonta}"/>
						<div class="has-error">
							<form:errors path="kontoOdBilans" cssClass="help-block" element="label"/>
						</div>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="form-group">
						<form:label path="kontoDoBilans">Konto do</form:label>
						<form:select path="kontoDoBilans" class="form-control" items="${klaseKonta}"/>
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
 <!-- <jsp:include page="bilans-tabela.jsp">
        <jsp:param name="myVar" value="konto1"/>
    </jsp:include> -->
	<c:if test="${not empty konto0}">
		<table class="table table-striped table-bordered">
			<thead class="thead-light">
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col">Konto</th>
					<th class="text-center" scope="col">Naziv konta</th>
					<th class="text-center" scope="col">Duguje</th>
					<th class="text-center" scope="col">Potrazuje</th>
					<th class="text-center" scope="col">Saldo</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${konto0}" var="bilans" varStatus="loop">	
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifra}</td>
						<td align="center">${bilans.konto.naziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="3" align="center"><b>Ukupno za klasu	0</b></td>
					<!-- <td class="active" align="right"><b>Saldo:</b></td> -->
					<td class="danger" align="right"><b>${dugujeKonto0}</b></td>
					<td class="success" align="right"><b>${potrazujeKonto0}</b></td>
					<td class="success" align="right"><b>${saldoKonto0}</b></td>
				</tr>
			</tbody>
		</table>				
	</c:if>
	<c:if test="${not empty konto1}">
		<table class="table table-striped table-bordered">
			<thead class="thead-light">
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col">Konto</th>
					<th class="text-center" scope="col">Naziv konta</th>
					<th class="text-center" scope="col">Duguje</th>
					<th class="text-center" scope="col">Potrazuje</th>
					<th class="text-center" scope="col">Saldo</th>
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
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="3" align="center"><b>Ukupno za klasu	1</b></td>
					<td class="danger" align="right"><b>${dugujeKonto1}</b></td>
					<td class="success" align="right"><b>${potrazujeKonto1}</b></td>
					<td class="success" align="right"><b>${saldoKonto1}</b></td>
				</tr>
			</tbody>
		</table>				
	</c:if>
	<c:if test="${not empty konto2}">
		<table class="table table-striped table-bordered">
			<thead class="thead-light">
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col">Konto</th>
					<th class="text-center" scope="col">Naziv konta</th>
					<th class="text-center" scope="col">Duguje</th>
					<th class="text-center" scope="col">Potrazuje</th>
					<th class="text-center" scope="col">Saldo</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${konto2}" var="bilans" varStatus="loop">	
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifra}</td>
						<td align="center">${bilans.konto.naziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="3" align="center"><b>Ukupno za klasu	2</b></td>
					<td class="danger" align="right"><b>${dugujeKonto2}</b></td>
					<td class="success" align="right"><b>${potrazujeKonto2}</b></td>
					<td class="success" align="right"><b>${saldoKonto2}</b></td>
				</tr>
			</tbody>
		</table> 				
	</c:if>	
	<c:if test="${not empty konto3}">
		<table class="table table-striped table-bordered">
			<thead class="thead-light">
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col">Konto</th>
					<th class="text-center" scope="col">Naziv konta</th>
					<th class="text-center" scope="col">Duguje</th>
					<th class="text-center" scope="col">Potrazuje</th>
					<th class="text-center" scope="col">Saldo</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${konto3}" var="bilans" varStatus="loop">	
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifra}</td>
						<td align="center">${bilans.konto.naziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="3" align="center"><b>Ukupno za klasu	3</b></td>
					<td class="danger" align="right"><b>${dugujeKonto3}</b></td>
					<td class="success" align="right"><b>${potrazujeKonto3}</b></td>
					<td class="success" align="right"><b>${saldoKonto3}</b></td>
				</tr>
			</tbody>
		</table> 				
	</c:if>	
	<c:if test="${not empty konto4}">
		<table class="table table-striped table-bordered">
			<thead class="thead-light">
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col">Konto</th>
					<th class="text-center" scope="col">Naziv konta</th>
					<th class="text-center" scope="col">Duguje</th>
					<th class="text-center" scope="col">Potrazuje</th>
					<th class="text-center" scope="col">Saldo</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${konto4}" var="bilans" varStatus="loop">	
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifra}</td>
						<td align="center">${bilans.konto.naziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="3" align="center"><b>Ukupno za klasu	4</b></td>
					<td class="danger" align="right"><b>${dugujeKonto4}</b></td>
					<td class="success" align="right"><b>${potrazujeKonto4}</b></td>
					<td class="success" align="right"><b>${saldoKonto4}</b></td>
				</tr>
			</tbody>
		</table> 				
	</c:if>	
	<c:if test="${not empty konto5}">
		<table class="table table-striped table-bordered">
			<thead class="thead-light">
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col">Konto</th>
					<th class="text-center" scope="col">Naziv konta</th>
					<th class="text-center" scope="col">Duguje</th>
					<th class="text-center" scope="col">Potrazuje</th>
					<th class="text-center" scope="col">Saldo</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${konto5}" var="bilans" varStatus="loop">	
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifra}</td>
						<td align="center">${bilans.konto.naziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="3" align="center"><b>Ukupno za klasu	5</b></td>
					<td class="danger" align="right"><b>${dugujeKonto5}</b></td>
					<td class="success" align="right"><b>${potrazujeKonto5}</b></td>
					<td class="success" align="right"><b>${saldoKonto5}</b></td>
				</tr>
			</tbody>
		</table> 				
	</c:if>	
	<c:if test="${not empty konto6}">
		<table class="table table-striped table-bordered">
			<thead class="thead-light">
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col">Konto</th>
					<th class="text-center" scope="col">Naziv konta</th>
					<th class="text-center" scope="col">Duguje</th>
					<th class="text-center" scope="col">Potrazuje</th>
					<th class="text-center" scope="col">Saldo</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${konto6}" var="bilans" varStatus="loop">	
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifra}</td>
						<td align="center">${bilans.konto.naziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="3" align="center"><b>Ukupno za klasu	6</b></td>
					<td class="danger" align="right"><b>${dugujeKonto6}</b></td>
					<td class="success" align="right"><b>${potrazujeKonto6}</b></td>
					<td class="success" align="right"><b>${saldoKonto6}</b></td>
				</tr>
			</tbody>
		</table> 				
	</c:if>	
	<c:if test="${not empty konto7}">
		<table class="table table-striped table-bordered">
			<thead class="thead-light">
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col">Konto</th>
					<th class="text-center" scope="col">Naziv konta</th>
					<th class="text-center" scope="col">Duguje</th>
					<th class="text-center" scope="col">Potrazuje</th>
					<th class="text-center" scope="col">Saldo</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${konto7}" var="bilans" varStatus="loop">	
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifra}</td>
						<td align="center">${bilans.konto.naziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="3" align="center"><b>Ukupno za klasu	7</b></td>
					<td class="danger" align="right"><b>${dugujeKonto7}</b></td>
					<td class="success" align="right"><b>${potrazujeKonto7}</b></td>
					<td class="success" align="right"><b>${saldoKonto7}</b></td>
				</tr>
			</tbody>
		</table> 				
	</c:if>	
	<c:if test="${not empty konto8}">
		<table class="table table-striped table-bordered">
			<thead class="thead-light">
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col">Konto</th>
					<th class="text-center" scope="col">Naziv konta</th>
					<th class="text-center" scope="col">Duguje</th>
					<th class="text-center" scope="col">Potrazuje</th>
					<th class="text-center" scope="col">Saldo</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${konto8}" var="bilans" varStatus="loop">	
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifra}</td>
						<td align="center">${bilans.konto.naziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="3" align="center"><b>Ukupno za klasu	8</b></td>
					<td class="danger" align="right"><b>${dugujeKonto8}</b></td>
					<td class="success" align="right"><b>${potrazujeKonto8}</b></td>
					<td class="success" align="right"><b>${saldoKonto8}</b></td>
				</tr>
			</tbody>
		</table> 				
	</c:if>	
	<c:if test="${not empty konto9}">
		<table class="table table-striped table-bordered">
			<thead class="thead-light">
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col">Konto</th>
					<th class="text-center" scope="col">Naziv konta</th>
					<th class="text-center" scope="col">Duguje</th>
					<th class="text-center" scope="col">Potrazuje</th>
					<th class="text-center" scope="col">Saldo</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${konto9}" var="bilans" varStatus="loop">	
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifra}</td>
						<td align="center">${bilans.konto.naziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="3" align="center"><b>Ukupno za klasu	9</b></td>
					<td class="danger" align="right"><b>${dugujeKonto9}</b></td>
					<td class="success" align="right"><b>${potrazujeKonto9}</b></td>
					<td class="success" align="right"><b>${saldoKonto9}</b></td>
				</tr>
			</tbody>
		</table> 				
	</c:if>
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
