<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@page import="java.util.*" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
	<head>
		<title>AlfaEkonomist</title>
		<meta name="description" content="<spring:message code="description"/>" />

		<base href="${baseurl}">
		<!-- CSS -->
		<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans">
		<link rel="stylesheet" href="css/custom.css?v=30012015" />
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<link rel="stylesheet" href="//cdn.datatables.net/1.10.5/css/jquery.dataTables.min.css"/>
		<!-- JS -->
		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<script type="text/javascript" src="js/nav/jquery.nav.js"></script>
		<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/custom.js?v=29112014"></script>
		<script src="//cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>
	</head>
	<body>
		<tiles:insertAttribute name="header" ignore="true" />
		<tiles:insertAttribute name="body" ignore="true" />
		<tiles:insertAttribute name="footer" ignore="true" />
	</body>
</html>