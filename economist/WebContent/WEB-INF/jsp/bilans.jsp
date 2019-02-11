<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<form:form modelAttribute="search" method="POST" action="${action}">
		<div class="row">
			<h3 align="center" style="padding-bottom: 0px;"><spring:message code="bilans.header"/></h3>
			<h4 align="center"><spring:message code="datum"/>: <fmt:formatDate pattern = "${datumPattern}" value = "${search.datumOd}" /> - <fmt:formatDate pattern = "${datumPattern}" value = "${search.datumDo}" /></h4>
			<div class="col-xs-3">
				<div class="form-group">
					<form:label path="kontoOd.id"><spring:message code="konto.od"/></form:label>
					<form:select path="kontoOd.id" class="form-control" items="${kontos}" itemLabel="sifraNaziv" itemValue="id"/>
					<div class="has-error">
						<form:errors path="kontoOd.id" cssClass="help-block" element="label"/>
					</div>
				</div>
			</div>
			<div class="col-xs-3">
				<div class="form-group">
					<form:label path="kontoDo.id"><spring:message code="konto.do"/></form:label>
					<form:select path="kontoDo.id" class="form-control" items="${kontos}" itemLabel="sifraNaziv" itemValue="id"/>
					<div class="has-error">
						<form:errors path="kontoDo.id" cssClass="help-block" element="label"/>
					</div>
				</div>
			</div>			
			<div class="col-xs-3">
				<div class="form-group">
					<form:label path="datumOd" class="required"><spring:message code="datum.od"/></form:label>
					<form:input path="datumOd" type="text" class="form-control datepicker"/>
					<div class="has-error">
						<form:errors path="datumOd" cssClass="help-block" element="label"/>
					</div>
				</div>
			</div>
			<div class="col-xs-3">
				<div class="form-group">
					<form:label path="datumDo" class="required"><spring:message code="datum.do"/></form:label>
					<form:input path="datumDo" type="text" class="form-control datepicker"/>
					<div class="has-error">
						<form:errors path="datumDo" cssClass="help-block" element="label"/>
					</div>
				</div>
			</div>				
		</div>		
		<div class="row" align="right">
			<button type="submit" class="btn btn btn-success" name="pretraga"><spring:message code="pretraga"/></button>
			<button type="submit" class="btn btn btn-success" name="pdf"><spring:message code="pdf"/></button>
			<a class="btn btn-primary" href="api/nalogs"><spring:message code="povratak"/></a>
		</div>
	</form:form>
	<p>&nbsp;</p>

	<c:if test="${not empty konto0}">
		<label><spring:message code="klasa"/> 0</label>
		<table class="${tableClass}">
			<thead>
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col"><spring:message code="konto"/></th>
					<th class="text-center" scope="col"><spring:message code="duguje"/></th>
					<th class="text-center" scope="col"><spring:message code="potrazuje"/></th>
					<th class="text-center" scope="col"><spring:message code="saldo"/></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="duguje" value="${0}"/>
				<c:set var="potrazuje" value="${0}"/>
				<c:set var="saldo" value="${0}"/>
				<c:forEach items="${konto0}" var="bilans" varStatus="loop">
					<c:set var="duguje" value="${duguje + bilans.duguje}" />
					<c:set var="potrazuje" value="${potrazuje + bilans.potrazuje}" />
					<c:set var="saldo" value="${saldo + bilans.saldo}" />
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifraNaziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="2" align="center"><b><spring:message code="ukupno.klasa"/> 0</b></td>
					<td class="ukupno" align="right"><b>${duguje}</b></td>
					<td class="ukupno" align="right"><b>${potrazuje}</b></td>
					<td class="ukupno" align="right"><b>${saldo}</b></td>
				</tr>
			</tbody>
		</table>				
	</c:if>

	<c:if test="${not empty konto1}">
		<label><spring:message code="klasa"/> 1</label>
		<table class="${tableClass}">
			<thead>
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col"><spring:message code="konto"/></th>
					<th class="text-center" scope="col"><spring:message code="duguje"/></th>
					<th class="text-center" scope="col"><spring:message code="potrazuje"/></th>
					<th class="text-center" scope="col"><spring:message code="saldo"/></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="duguje" value="${0}"/>
				<c:set var="potrazuje" value="${0}"/>
				<c:set var="saldo" value="${0}"/>
				<c:forEach items="${konto1}" var="bilans" varStatus="loop">
					<c:set var="duguje" value="${duguje + bilans.duguje}" />
					<c:set var="potrazuje" value="${potrazuje + bilans.potrazuje}" />
					<c:set var="saldo" value="${saldo + bilans.saldo}" />
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifraNaziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="2" align="center"><b><spring:message code="ukupno.klasa"/> 1</b></td>
					<td class="ukupno" align="right"><b>${duguje}</b></td>
					<td class="ukupno" align="right"><b>${potrazuje}</b></td>
					<td class="ukupno" align="right"><b>${saldo}</b></td>
				</tr>
			</tbody>
		</table>				
	</c:if>
	
	<c:if test="${not empty konto2}">
		<label><spring:message code="klasa"/> 2</label>
		<table class="${tableClass}">
			<thead>
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col"><spring:message code="konto"/></th>
					<th class="text-center" scope="col"><spring:message code="duguje"/></th>
					<th class="text-center" scope="col"><spring:message code="potrazuje"/></th>
					<th class="text-center" scope="col"><spring:message code="saldo"/></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="duguje" value="${0}"/>
				<c:set var="potrazuje" value="${0}"/>
				<c:set var="saldo" value="${0}"/>
				<c:forEach items="${konto2}" var="bilans" varStatus="loop">
					<c:set var="duguje" value="${duguje + bilans.duguje}" />
					<c:set var="potrazuje" value="${potrazuje + bilans.potrazuje}" />
					<c:set var="saldo" value="${saldo + bilans.saldo}" />
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifraNaziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="2" align="center"><b><spring:message code="ukupno.klasa"/> 2</b></td>
					<td class="ukupno" align="right"><b>${duguje}</b></td>
					<td class="ukupno" align="right"><b>${potrazuje}</b></td>
					<td class="ukupno" align="right"><b>${saldo}</b></td>
				</tr>
			</tbody>
		</table>				
	</c:if>	

	<c:if test="${not empty konto3}">
		<label><spring:message code="klasa"/> 3</label>
		<table class="${tableClass}">
			<thead>
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col"><spring:message code="konto"/></th>
					<th class="text-center" scope="col"><spring:message code="duguje"/></th>
					<th class="text-center" scope="col"><spring:message code="potrazuje"/></th>
					<th class="text-center" scope="col"><spring:message code="saldo"/></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="duguje" value="${0}"/>
				<c:set var="potrazuje" value="${0}"/>
				<c:set var="saldo" value="${0}"/>
				<c:forEach items="${konto3}" var="bilans" varStatus="loop">
					<c:set var="duguje" value="${duguje + bilans.duguje}" />
					<c:set var="potrazuje" value="${potrazuje + bilans.potrazuje}" />
					<c:set var="saldo" value="${saldo + bilans.saldo}" />
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifraNaziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="2" align="center"><b><spring:message code="ukupno.klasa"/> 3</b></td>
					<td class="ukupno" align="right"><b>${duguje}</b></td>
					<td class="ukupno" align="right"><b>${potrazuje}</b></td>
					<td class="ukupno" align="right"><b>${saldo}</b></td>
				</tr>
			</tbody>
		</table>				
	</c:if>

	<c:if test="${not empty konto4}">
		<label><spring:message code="klasa"/> 4</label>
		<table class="${tableClass}">
			<thead>
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col"><spring:message code="konto"/></th>
					<th class="text-center" scope="col"><spring:message code="duguje"/></th>
					<th class="text-center" scope="col"><spring:message code="potrazuje"/></th>
					<th class="text-center" scope="col"><spring:message code="saldo"/></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="duguje" value="${0}"/>
				<c:set var="potrazuje" value="${0}"/>
				<c:set var="saldo" value="${0}"/>
				<c:forEach items="${konto4}" var="bilans" varStatus="loop">
					<c:set var="duguje" value="${duguje + bilans.duguje}" />
					<c:set var="potrazuje" value="${potrazuje + bilans.potrazuje}" />
					<c:set var="saldo" value="${saldo + bilans.saldo}" />
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifraNaziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="2" align="center"><b><spring:message code="ukupno.klasa"/> 4</b></td>
					<td class="ukupno" align="right"><b>${duguje}</b></td>
					<td class="ukupno" align="right"><b>${potrazuje}</b></td>
					<td class="ukupno" align="right"><b>${saldo}</b></td>
				</tr>
			</tbody>
		</table>				
	</c:if>

	<c:if test="${not empty konto5}">
		<label><spring:message code="klasa"/> 5</label>
		<table class="${tableClass}">
			<thead>
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col"><spring:message code="konto"/></th>
					<th class="text-center" scope="col"><spring:message code="duguje"/></th>
					<th class="text-center" scope="col"><spring:message code="potrazuje"/></th>
					<th class="text-center" scope="col"><spring:message code="saldo"/></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="duguje" value="${0}"/>
				<c:set var="potrazuje" value="${0}"/>
				<c:set var="saldo" value="${0}"/>
				<c:forEach items="${konto5}" var="bilans" varStatus="loop">
					<c:set var="duguje" value="${duguje + bilans.duguje}" />
					<c:set var="potrazuje" value="${potrazuje + bilans.potrazuje}" />
					<c:set var="saldo" value="${saldo + bilans.saldo}" />
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifraNaziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="2" align="center"><b><spring:message code="ukupno.klasa"/> 5</b></td>
					<td class="ukupno" align="right"><b>${duguje}</b></td>
					<td class="ukupno" align="right"><b>${potrazuje}</b></td>
					<td class="ukupno" align="right"><b>${saldo}</b></td>
				</tr>
			</tbody>
		</table>				
	</c:if>

	<c:if test="${not empty konto6}">
		<label><spring:message code="klasa"/> 6</label>
		<table class="${tableClass}">
			<thead>
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col"><spring:message code="konto"/></th>
					<th class="text-center" scope="col"><spring:message code="duguje"/></th>
					<th class="text-center" scope="col"><spring:message code="potrazuje"/></th>
					<th class="text-center" scope="col"><spring:message code="saldo"/></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="duguje" value="${0}"/>
				<c:set var="potrazuje" value="${0}"/>
				<c:set var="saldo" value="${0}"/>
				<c:forEach items="${konto6}" var="bilans" varStatus="loop">
					<c:set var="duguje" value="${duguje + bilans.duguje}" />
					<c:set var="potrazuje" value="${potrazuje + bilans.potrazuje}" />
					<c:set var="saldo" value="${saldo + bilans.saldo}" />
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifraNaziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="2" align="center"><b><spring:message code="ukupno.klasa"/> 6</b></td>
					<td class="ukupno" align="right"><b>${duguje}</b></td>
					<td class="ukupno" align="right"><b>${potrazuje}</b></td>
					<td class="ukupno" align="right"><b>${saldo}</b></td>
				</tr>
			</tbody>
		</table>				
	</c:if>

	<c:if test="${not empty konto7}">
		<label><spring:message code="klasa"/> 7</label>
		<table class="${tableClass}">
			<thead>
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col"><spring:message code="konto"/></th>
					<th class="text-center" scope="col"><spring:message code="duguje"/></th>
					<th class="text-center" scope="col"><spring:message code="potrazuje"/></th>
					<th class="text-center" scope="col"><spring:message code="saldo"/></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="duguje" value="${0}"/>
				<c:set var="potrazuje" value="${0}"/>
				<c:set var="saldo" value="${0}"/>
				<c:forEach items="${konto7}" var="bilans" varStatus="loop">
					<c:set var="duguje" value="${duguje + bilans.duguje}" />
					<c:set var="potrazuje" value="${potrazuje + bilans.potrazuje}" />
					<c:set var="saldo" value="${saldo + bilans.saldo}" />
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifraNaziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="2" align="center"><b><spring:message code="ukupno.klasa"/> 7</b></td>
					<td class="ukupno" align="right"><b>${duguje}</b></td>
					<td class="ukupno" align="right"><b>${potrazuje}</b></td>
					<td class="ukupno" align="right"><b>${saldo}</b></td>
				</tr>
			</tbody>
		</table>				
	</c:if>

	<c:if test="${not empty konto8}">
		<label><spring:message code="klasa"/> 8</label>
		<table class="${tableClass}">
			<thead>
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col"><spring:message code="konto"/></th>
					<th class="text-center" scope="col"><spring:message code="duguje"/></th>
					<th class="text-center" scope="col"><spring:message code="potrazuje"/></th>
					<th class="text-center" scope="col"><spring:message code="saldo"/></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="duguje" value="${0}"/>
				<c:set var="potrazuje" value="${0}"/>
				<c:set var="saldo" value="${0}"/>
				<c:forEach items="${konto8}" var="bilans" varStatus="loop">
					<c:set var="duguje" value="${duguje + bilans.duguje}" />
					<c:set var="potrazuje" value="${potrazuje + bilans.potrazuje}" />
					<c:set var="saldo" value="${saldo + bilans.saldo}" />
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifraNaziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="2" align="center"><b><spring:message code="ukupno.klasa"/> 8</b></td>
					<td class="ukupno" align="right"><b>${duguje}</b></td>
					<td class="ukupno" align="right"><b>${potrazuje}</b></td>
					<td class="ukupno" align="right"><b>${saldo}</b></td>
				</tr>
			</tbody>
		</table>				
	</c:if>

	<c:if test="${not empty konto9}">
		<label><spring:message code="klasa"/> 9</label>
		<table class="${tableClass}">
			<thead>
				<tr>
					<th class="text-center" scope="col">#</th>
					<th class="text-center" scope="col"><spring:message code="konto"/></th>
					<th class="text-center" scope="col"><spring:message code="duguje"/></th>
					<th class="text-center" scope="col"><spring:message code="potrazuje"/></th>
					<th class="text-center" scope="col"><spring:message code="saldo"/></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="duguje" value="${0}"/>
				<c:set var="potrazuje" value="${0}"/>
				<c:set var="saldo" value="${0}"/>
				<c:forEach items="${konto9}" var="bilans" varStatus="loop">
					<c:set var="duguje" value="${duguje + bilans.duguje}" />
					<c:set var="potrazuje" value="${potrazuje + bilans.potrazuje}" />
					<c:set var="saldo" value="${saldo + bilans.saldo}" />
					<tr>
						<td align="center">${loop.count}</td>
						<td align="center">${bilans.konto.sifraNaziv}</td>
						<td align="right">${bilans.duguje}</td>
						<td align="right">${bilans.potrazuje}</td>
						<td align="right">${bilans.saldo}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="active" colspan="2" align="center"><b><spring:message code="ukupno.klasa"/> 9</b></td>
					<td class="ukupno" align="right"><b>${duguje}</b></td>
					<td class="ukupno" align="right"><b>${potrazuje}</b></td>
					<td class="ukupno" align="right"><b>${saldo}</b></td>
				</tr>
			</tbody>
		</table>				
	</c:if>		
</div>
<script type="text/javascript">
	$(document).ready(function() {
		makeInputDate();
		setActiveHeader("#izvjestaji");
		setActiveHeader("api/bilans");
	});
</script>
