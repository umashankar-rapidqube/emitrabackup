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
		<c:if test="${bill.langCode == 0}">
			<title>GOVERNMENT SERVICES</title>
		</c:if>
		<c:if test="${bill.langCode == 1}">
			<title>सरकारी सेवाएं</title>
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
				<div id="fill" class="item gr gs">
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
										<c:if test="${bill.langCode == 0}">
											<a href="index" class="panel pull-right homebtn">
												<img src="img/new/home.png" alt="" /></a>
										</c:if>
										<c:if test="${bill.langCode == 1}">
											<a href="index" class="panel pull-right homebtn">
												<img src="img/new/homehindi.png" alt="">
											</a>
										</c:if>
									</div>
								</div>
							</div>
						</div>
						
						 <form method="post" id="spd">
							<input type="hidden" name="serviceID" id="serviceID" />
					     </form>
					    <form method="post" id="spdIN">
						</form>
						<form id="spd1">
							<input type="hidden" name="langCode" id="langCode" value="${bill.langCode}">
							<input type="hidden" name="serviceProviderPage" id="serviceProviderPage" />
						</form>
						
						<div class="fourservices">
							<div class="innermid">
								<div class="container main_row">
									<div class="row circle-container">
										<div class="billmidmain">
											<h1 class="">
												<c:if test="${bill.langCode == 0}">GOVERNMENT SERVICES</c:if>
												<c:if test="${bill.langCode == 1}">सरकारी सेवाएं</c:if>
											</h1>
											<div class="col-md-3 deg30">
												<div class="grevanceservices">
													<a href="transactionservice1" class="panel">
														<img src="img/Get-status.png" alt="" />
														<span>
															<c:if test="${bill.langCode == 0}">Get Status of E-mitra Transactions</c:if>
															<c:if test="${bill.langCode == 1}">ई-मित्र ट्रांन्जेकशन की स्थिति प्राप्त करें</c:if>
														</span>
													</a>
												</div>
											</div>
											<div class="clearfix"></div> 
											
	
											<div class="col-md-3 deg0">
												<div class="grevanceservices">
													<a href="javascript:void(0);" onclick="getSP1('RevenueService' , 'revenueCaseService')"class="panel">
														<img src="img/RCMS.png" alt="" />
														<span>
															<c:if test="${bill.langCode == 0}">Court Case Status</c:if>
															<c:if test="${bill.langCode == 1}">न्यायालय प्रकरण की सूचना</c:if>
														</span>
													</a>
												</div>
											</div>
											<div class="col-md-3 deg60">
												<div class="grevanceservices">
													<a href="examservice" class="panel">
														<img src="img/sexam.png" alt="" />
														<span>
															<c:if test="${bill.langCode == 0}">View Secondary Exam Detail B.S.E.R., Ajmer</c:if>
															<c:if test="${bill.langCode == 1}">माध्यमिक परीक्षा का  विवरण देखें बी.एस.ई.आर., अजमेर</c:if>
														</span>
													</a>
												</div>
											</div>
											<div class="col-md-3 deg90">
												<div class="grevanceservices">
													<a href="javascript:void(0);" onclick="getSP1('Jamabandi' , 'jamabandiRecord')"class="panel">
														<img src="img/ror.png" alt="" /> 												
														<span>
															<c:if test="${bill.langCode == 0}">View Land Records</c:if>
															<c:if test="${bill.langCode == 1}">भूमि   रिकॉर्ड   देखें</c:if>
														</span>
													</a>
												</div>
											</div>
											<div class="col-md-3 deg180">
												<div class="grevanceservices">
													<a href="GrievanceStatus" class="panel">
														<img src="img/Grievance.png" alt="" />
														<span>
															<c:if test="${bill.langCode == 0}">View Grievance Status</c:if>
															<c:if test="${bill.langCode == 1}">शिकायत की स्थिति देखें</c:if>
														</span>
													</a>
												</div>
											</div>
											<div class="col-md-3 deg30" style="margin: -255px 0px 0px 0px;">
											</div>
										</div>
									</div>
								</div>
								<div class="button_div">
									<c:if test="${bill.langCode == 0}">
										<a href="governmentServiceProvider" class="panel">
										<img src="img/new/back.png" alt="">
										</a>
									</c:if>
									<c:if test="${bill.langCode == 1}">
										<a href="governmentServiceProviderhindi" class="panel">
											<img src="img/new/backhindi.png" alt="">
										</a>
									</c:if>
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
			function getSP(id) {
		    	$("#serviceID").val(id);
				document.getElementById("spd").action = "governmentservice11";
				document.getElementById("spd").method = "post";
				document.getElementById('spd').submit();
			}
			
			function getSP1(pageName , actionName) {
				$("#serviceProviderPage").val(pageName);
				document.getElementById("spd1").action = actionName;
				document.getElementById("spd1").method = "post";
				document.getElementById('spd1').submit();
			}
			
			function getSPIN(id,name) {
				document.getElementById("spdIN").action = "bhamashahCard";
				document.getElementById("spdIN").method = "post";
				document.getElementById('spdIN').submit();
			}
		</script>
	</body>
</html>