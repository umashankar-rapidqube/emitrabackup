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
		<c:if test="${bill.langCode == 0}"><title>GOVERNMENT SERVICES</title></c:if>
		<c:if test="${bill.langCode == 1}"><title>सरकारी सेवाएं</title></c:if>
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
										<a href="index" class="panel pull-right homebtn">
											<c:if test="${bill.langCode == 0}"><img src="img/new/home.png" alt="" /></c:if>
											<c:if test="${bill.langCode == 1}"><img src="img/new/homehindi.png" alt="" /></c:if>
										</a>
									</div>
								</div>
							</div>
						</div>
						 <form method="post" id="spd">
							<input type="hidden" name="serviceID" id="serviceID" />
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
											<c:if test="${bill.langCode == 0}"><h1 class="">GOVERNMENT SERVICES</h1></c:if>
											<c:if test="${bill.langCode == 1}"><h1 class="">सरकारी सेवाएं</h1></c:if>
											<div class="col-md-3 deg30">
												<div class="grevanceservices">
													<c:if test="${bill.langCode == 0}">
														<a href="javascript:void(0);" onclick="getSP('2289' , 'governmentservice11')" class="panel">
															<img src="img/print-certificate.png" alt="" />
															<span>View/Print Certificate</span>
														</a>
													</c:if>
													<c:if test="${bill.langCode == 1}">
														<a href="javascript:void(0);" onclick="getSP('2289' , 'governmentservicehindi11')" class="panel">
															<img src="img/print-certificate.png" alt="" />
															<span>प्रमाणपत्र देखें/छापें</span>
														</a>
													</c:if>
												</div>
											</div>
											<div class="clearfix"></div> 
											<div class="col-md-3 deg0">
												<!-- <div class="grevanceservices">
													<a href="transactionservice1" class="panel"> <img
														src="img/Get-status.png" alt="" /> <span>Get Status of E-mitra Transactions</span>
													</a>
												</div> -->
											</div>
											<div class="col-md-3 deg60">
												<div class="grevanceservices">
													<c:if test="${bill.langCode == 0}">
														<a href="#" onclick="getSP1('bhamashahCard' , 'bhamashahCard')" class="panel">
															<img src="img/bhamashah_logo.png" alt="" />
															<span>Updation of mobile number in Bhamashah Card</span>
														</a>
													</c:if>
													<c:if test="${bill.langCode == 1}">
														<a href="#" onclick="getSP1('bhamashahCardHi' , 'bhamashahCardHi')" class="panel">
															<img src="img/bhamashah_logo.png" alt="" />
															<span>भामाशाह कार्ड में मोबाइल नंबर का अपडेशन</span>
														</a>
													</c:if>
												</div>
											</div>
											<div class="col-md-3 deg90">
												<div class="grevanceservices">
													<a  href="javascript:void(0);" onclick="getSP1('ViewServiceStatus' , 'utilityBills')" class="panel">
														<img src="img/Various_Service.png" alt="" />
														<c:if test="${bill.langCode == 0}"><span>View Status Of Various Services</span></c:if>
														<c:if test="${bill.langCode == 1}"><span>विभिन्न सेवाओं की स्थिति देखें</span></c:if>
													</a>
												</div>
											</div>
											<div class="col-md-3 deg180">
												<!-- <div class="grevanceservices">
													<a href="jamabandiRecord" class="panel">  
														<img src="img/ror.png" alt="" /> 												
														<span>View Land Records</span>
													</a>
												</div> -->
											</div>
											<div class="col-md-3 deg30" style="margin: -255px 0px 0px 0px;">
												<!-- <div class="grevanceservices">
													<a href="GrievanceStatus" class="panel">
														<img src="img/Grievance.png" alt="" /> <span>View Grievance Status</span>
													</a>
												</div> -->
											</div>
										</div>
									</div>
								</div>
								<div class="button_div">
									<a href="javascript:void(0);" onclick="getSP1('Service' , 'service')" class="panel">
										<c:if test="${bill.langCode == 0}">
											<img src="img/new/back.png" alt="" />
										</c:if>
										<c:if test="${bill.langCode == 1}">
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
			function getSP(id , actionName) {
		    	$("#serviceID").val(id);
				document.getElementById("spd").action = actionName;
				document.getElementById("spd").method = "post";
				document.getElementById('spd').submit();
			}
			
			function getSP1(pageName , actionName) {
				$("#serviceProviderPage").val(pageName);
				document.getElementById("spd1").action = actionName;
				document.getElementById("spd1").method = "post";
				document.getElementById('spd1').submit();
			}
		</script>
	</body>
</html>