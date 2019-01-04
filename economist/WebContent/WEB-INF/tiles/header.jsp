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
				<%-- <li><a href="#home"><spring:message code="button.home"/></a></li> --%>
				<sec:authorize access="hasAnyRole('ROLE_USER')">
					<li><a href="api/nalogs">Glavna knjiga</a></li>
					<li>
						<a class="dropdown-toggle"	data-toggle="dropdown" href="#sifarnici">&#352;ifarnici<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="kontos">Kontni plan</a></li>
							<li><a href="vrstadokumentas">Vrsta dokumenta</a></li>
							<li><a href="komitents">Komitent</a></li>
						</ul>	
					</li>						
					<li>
						<a class="dropdown-toggle"	data-toggle="dropdown" href="#analitika">Analitika<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="analitika">Analitika</a></li>
							<li><a href="analitika-konta-komitenti">Analitika konto komitent</a></li>
							<li><a href="bilans">Bilans</a></li>
							<li><a href="kif-kuf/kif">KIF</a></li>
							<li><a href="kif-kuf/kuf">KUF</a></li>
						</ul>
					</li>
				</sec:authorize>	
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
					<li>
						<a class="dropdown-toggle"	data-toggle="dropdown" href="#administration">Administracija<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="agencijas">Agencije</a></li>
							<li><a href="preduzeces">Preduzeca</a></li>
							<li><a href="users">Korisnici</a></li>
						</ul>
					</li>
				</sec:authorize>
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