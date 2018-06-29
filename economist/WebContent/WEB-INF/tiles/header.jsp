<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a href="" class="navbar-brand">Economist</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="#home"><spring:message code="button.home"/></a></li>
				<sec:authorize access="hasAnyRole('ROLE_USER')">
					<li><a href="nalogs">Nalog</a></li>
					<li><a href="kontos">Konto</a></li>
					<li><a href="vrstadokumentas">Vrsta dokumenta</a></li>						
					<li>
						<a class="dropdown-toggle"	data-toggle="dropdown" href="#analitika">Analitika<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="analitika">Analitika</a></li>
							<li><a href="bilans">Bilans</a></li>
						</ul>
					</li>
				</sec:authorize>	
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<c:choose>
					<c:when test="${not empty user}">
						<li class="dropdown"><a class="dropdown-toggle"	data-toggle="dropdown" href="#">
							${user.firstName} ${user.lastName}
							<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="my/profile">Edit Profile</a></li>
								<li><a href="<c:url value='j_spring_security_logout' />">Logout</a></li>
							</ul>
						</li>
					</c:when>
					<c:otherwise>
						<li><a href="login"><spring:message code="button.login"/></a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</div>