<%@ page language="java" contentType="text/html; charset=utf-8"
	 pageEncoding="utf-8"%>
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
		<c:if test="${langCode == 0}">
			<title>Service</title>
		</c:if>
		<c:if test="${langCode == 1}">
			<title>सेवाएं</title>
		</c:if>
		<link href="css/font-awesome.css" type="text/css" rel="stylesheet" />
		<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
		<link rel="stylesheet" href="css/bootstrap-material-design.min.css" />
		<link rel="icon" href="img/favicon.ico" type="image/x-icon" />
		<link href="css/my-style.css" type="text/css" rel="stylesheet" />
	</head>

	<body style="background: url(img/inner-bg.png); background-size: cover;">
		<div id="wrapper">
			<div id="mask">
				<div id="fill" class="item allservice">
					<div class="content">
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
										<a href="index" class="panel pull-right homebtn">
											<c:if test="${langCode == 0}">
												<img src="img/new/home.png" alt="" />
											</c:if>
											<c:if test="${langCode == 1}">
												<img src="img/new/homehindi.png" alt="" />
											</c:if>
										</a>
									</div>
								</div>
							</div>
						</div>
						<div class="fourservices">
							<div class="innermid">
								<div class="container main_row">
									<div class="row circle-container">
										<form id="spd">
											<input type="hidden" name="langCode" id="langCode" value="${langCode}">
											<input type="hidden" name="serviceProviderPage" id="serviceProviderPage" />
										</form>
										<div class="col-md-4">
											<div class="oneservices lasrservices">
												<a href="javascript:void(0);" onclick="getSP('GovernmentServiceProvider')" class="panel">
													<img src="img/gov-ser.png" alt="" />
													<c:if test="${langCode == 0}">
														<span>GOVERNMENT SERVICES</span>
													</c:if>
													<c:if test="${langCode == 1}">
														<span>सरकारी सेवाएं</span>
													</c:if>
												</a>
											</div>
										</div>
										<div class="col-md-4">
											<div class="oneservices">
												<a href="javascript:void(0);" onclick="getSP('Serviceprovider')" class="panel">
													<img src="img/bils.png" alt="" />
													<c:if test="${langCode == 0}">
														<span>UTILITY BILLS</span>
													</c:if>
													<c:if test="${langCode == 1}">
														<span>बिल भुगतान</span>
													</c:if>
	
												</a>
											</div>
										</div>
										<div class="col-md-4">
											<div class="oneservices lasrservices"
												style="margin-left: 55px;">
												<a href="videoconferncing">
													<img src="img/videoconf.png" alt="" />
													<c:if test="${langCode == 0}">
														<span>VIDEO CONFERENCING</span>
													</c:if>
													<c:if test="${langCode == 1}">
														<span>वीडियो कॉन्फ्रेंसिंग</span>
													</c:if>
												</a>
											</div>
										</div>
									</div>
									<div class="row circle-container">
										<div class="col-md-4">
											<img src="img/k11.png" alt="" style="height: 360px;" />
										</div>
										<div class="col-md-4">
											<div class="oneservices lasrservices">
												<a href="javascript:void(0);" onclick="getSP('InformationPanchayatService')" class="panel">
													<img src="img/infoPanchayat.png" alt="" /> 
													<c:if test="${langCode == 0}">
														<span>INFORMATION RELATED TO PANCHAYAT SAMITI / GRAM PANCHAYAT</span>
													</c:if>
													<c:if test="${langCode == 1}">
														<span>पंचायत समिति / ग्राम पंचायत से संबंधित जानकारी</span>
													</c:if>
												</a>
											</div>
										</div>
										<div class="col-md-4	">
											<img src="img/k11.png" alt="" style="height: 360px;" />
										</div>
									</div>
								</div>
								<div class="button_div">
									<a href="index" class="panel">
										<c:if test="${langCode == 0}">
											<img src="img/new/back.png" alt="" />
										</c:if>
										<c:if test="${langCode == 1}">
											<img src="img/new/backhindi.png" alt="">
										</c:if>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/jquery.scrollTo.js"></script>
		<script type="text/javascript" src="js/app-inner.js"></script>
		<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
		<script type="text/javascript">
			function getSP(pageName) {
				$("#serviceProviderPage").val(pageName);
				document.getElementById("spd").action = "utilityBills";
				document.getElementById("spd").method = "post";
				document.getElementById('spd').submit();
			}
		</script>
	</body>
</html>