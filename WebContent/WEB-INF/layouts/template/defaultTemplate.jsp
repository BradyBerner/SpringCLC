<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Spring CLC</title>
	
	<!-- Bootstrap Resources and other CSS files -->
	<spring:url value="/resources/css/main.css" var="mainCss" />
	<spring:url value="/resources/css/bootstrap.min.css" var="bootstrap" />
	<spring:url value="/resources/js/main.js" var="mainJs" />
	<spring:url value="/resources/js/smoothproducts.min.js" var="smoothJs" />
	<spring:url value="/resources/js/theme.js" var="themeJs" />
	
	<link href="${mainCss}" rel="stylesheet" />
	<link href="${bootstrap}" rel="stylesheet" />
	<link href="${springCLC}" rel="stylesheet"/>
	
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
</head>

<body style="background-color: #444444">
	<!-- Header -->
	<tiles:insertAttribute name="header" />

	<!-- Body Page -->
	<div align="center" style="text-align: center; padding-top: 15px;">
		<tiles:insertAttribute name="body" />
	</div>

	<!-- Footer Page -->
	<tiles:insertAttribute name="footer" />

	<script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="   crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
    <script src="${smoothJs}"></script>
	<script src="${themeJs}"></script>
	<script src="${mainJs}"></script>
</body>

</html>