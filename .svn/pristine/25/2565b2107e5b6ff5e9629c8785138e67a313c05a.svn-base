<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>GOVERNMENT SERVICES</title>
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans|Nunito+Sans:600|700"
	rel="stylesheet" />
<link href="css/font-awesome.css" type="text/css" rel="stylesheet" />
<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap-material-design.min.css" />
<link rel="icon" href="img/favicon.ico" type="image/x-icon" />
<link href="css/my-style.css" type="text/css" rel="stylesheet" />
<script src="https://printjs-4de6.kxcdn.com/print.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://printjs-4de6.kxcdn.com/print.min.css">
</head>




<body style="background: url(img/inner-bg.png); background-size: cover;">

	<div id="wrapper">
		<div id="mask">


			<div id="bill" class="item">
				<div class="content">
					<!--<a href="#item1" class="panel">back</a>-->
					<div class="mainone innermaintop">
						<div class="container">
							<div class="row">
								<div class="col-md-4">
									<div class="logomain">
										<div class="cartoon innercartoon">
											<img src="img/cartooon-bg-inner.png" alt="" /> <img
												src="img/bubble.png" class="bubble" alt="" />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="logomain midinnerlogomain">
										<div class="logo midinnerlogo">
											<a href="#"> <img src="img/logo-inner.png" alt="" /></a>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<c:if test="${langCode == 0}">
										<a href="index" class="panel pull-right homebtn">
											<img src="img/new/home.png" alt="" /></a>
									</c:if>
									<c:if test="${langCode == 1}">
										<a href="index" class="panel pull-right homebtn">
											<img src="img/new/homehindi.png" alt="">
										</a>
									</c:if>
								</div>
							</div>
						</div>
					</div>
					<div class="fourservices">
						<div class="innermid">
							<div class="container main_row">
								<div class="row">
									<div class="billmidmain">
										<div class="col-md-12">
											<iframe  id='iframeid' src="${pdfUrl}" width="100%" height="480"
											 type="application/pdf"></iframe>
										</div>
									</div>
								</div>
							</div>
							<div class="button_div">
								<c:if test="${langCode == 0}">
									<a href="javascript:void(0);" onclick="getSP1('RevenueService' , 'revenueCaseService')" class="panel">
										<img src="img/new/back.png"	alt="" />
									</a>
								</c:if>
								<c:if test="${langCode == 1}">
									<a href="javascript:void(0);" onclick="getSP1('RevenueService' , 'revenueCaseService')" class="panel">
										<img src="img/new/backhindi.png" alt="">
									</a>
								</c:if>
							</div>
							<form id="spd1">
								<input type="hidden" name="langCode" id="langCode" value="${langCode}">
								<input type="hidden" name="transId" id="transId" value="${transId}">
								<input type="hidden" name="serviceProviderPage"	id="serviceProviderPage" value=""/>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>


	</div>
	<div id="loading" style="display: none;">
		<img id="loading-image" src="img/ajax-loader.gif" alt="Loading..." />
	</div>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/jquery.scrollTo.js"></script>
	<script type="text/javascript" src="js/app-inner.js"></script>
	
	<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
	
	<script>
		function getSP1(pageName , actionName) {
			$("#serviceProviderPage").val(pageName);
			document.getElementById("spd1").action = actionName;
			document.getElementById("spd1").method = "post";
			document.getElementById('spd1').submit();
		}
	</script>
</body>
</html>