<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a href="" class="navbar-brand">Olgica-Restaurant</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="#home"><spring:message code="button.home"/></a></li>
				<li><a href="#about"><spring:message code="button.about"/></a></li>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
				<li><a href="policies/my-orders">My orders</a></li>
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