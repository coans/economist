<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html>
	<head>
		<title>TheraProject</title>
		<meta name="description" content="TheraProject is Java web tutorial :)" />

		<base href="${baseurl}">
		<!-- CSS -->
		<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans">
		<link rel="stylesheet" href="css/custom.css?v=30012015" />
		<!-- JS -->
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
		<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/nav/jquery.nav.js"></script>
		<script type="text/javascript" src="js/custom.js?v=29112014"></script>
	</head>
	<body>
		<tiles:insertAttribute name="header" ignore="true" />
		<tiles:insertAttribute name="body" ignore="true" />
		<tiles:insertAttribute name="footer" ignore="true" />
	</body>
</html>