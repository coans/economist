<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="java.util.*" %>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  
<div class="container">
	<div class="row">
		<div class="col-xs-5">
			<h3>${title}</h3>
			<form:form modelAttribute="vrstadokumenta" method="POST" action="${action}">
				<form:hidden path="id" />
				<form:hidden path="sifra" />
				<div class="form-group">
					<form:label path="naziv" class="required">Naziv</form:label>
					<form:input path="naziv" type="text" class="form-control" />
					<div class="has-error">
						<form:errors path="naziv" cssClass="help-block" element="label"/>
					</div>
				</div>
				<div class="form-group">
					<form:label path="prikaziukif" style="padding-right: 20px;">Prika&#382;i u KIF</form:label>
					<form:checkbox path="prikaziukif"/>
					<div class="has-error">
						<form:errors path="prikaziukif" cssClass="help-block" element="label"/>
					</div>
				</div>
				<div class="form-group">
					<form:label path="prikaziukuf" style="padding-right: 20px;">Prika&#382;i u KUF</form:label>
					<form:checkbox path="prikaziukuf"/>
					<div class="has-error">
						<form:errors path="prikaziukuf" cssClass="help-block" element="label"/>
					</div>
				</div>								
				<p>&nbsp;</p>					
				<button type="submit" class="btn btn btn-success"><spring:message code="button.save"/></button>
				<a class="btn btn-primary" href="nalogs"><spring:message code="button.cancel"/></a>
			</form:form>
			<span class="form-group"><label class="required"><font size="2"><spring:message code="page.required.fields"/></font></label></span>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		setActiveHeader("#sifarnici");
	});
</script>