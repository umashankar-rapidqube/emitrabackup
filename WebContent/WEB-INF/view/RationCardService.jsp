<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<html>

	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	    <c:if test="${bill.langCode == 0}">
			<title>RATION CARD INFORMATION</title>
		</c:if>
		<c:if test="${bill.langCode == 1}">
			<title>राशन कार्ड जानकारी</title>
		</c:if>
	    <link href="https://fonts.googleapis.com/css?family=Noto+Sans|Nunito+Sans:600|700" rel="stylesheet" />
	    <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	    <link rel="stylesheet" href="css/bootstrap-material-design.min.css" />
	    <link rel="icon" href="img/favicon.ico" type="image/x-icon" />
	    <link href="css/my-style.css" type="text/css" rel="stylesheet" />
	</head>




	<body style="background: url(img/inner-bg.png); background-size: cover;">
		<div id="wrapper">
			<div id="mask">
				<div id="bill" class="item">
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
											<c:if test="${bill.langCode == 0}">
												<img src="img/new/home.png" alt="" />
											</c:if>
											<c:if test="${bill.langCode == 1}">
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
									<div class="row">
	
										<div class="billmidmain">
											<c:if test="${bill.langCode == 0}">
												<h1>Get Details Using Ration Card, Aadhaar, Bhamashah Number</h1>
											</c:if>
											<c:if test="${bill.langCode == 1}">
												<h1>राशन कार्ड, आधार, भामाशाह संख्या का उपयोग करके विवरण प्राप्त करें</h1>
											</c:if>
											<form id="spd">
												<input type="hidden" name="langCode" id="langCode" value="${bill.langCode}">
												<input type="hidden" name="serviceProviderPage" id="serviceProviderPage" value="FetchRationDetails"/>
											</form>
											<form id="rationCard">
												<input type="hidden" name="langCode" id="langCode" value="${bill.langCode}">
												<div class="feildone" style="text-align: center">
													<div class="col-md-6 col-md-offset-3">
														<div class="col-md-4">
															<div class="billid">
																<label for="exampleInputEmail1" class="bmd-label-floating">
																	<c:if test="${bill.langCode == 0}">CRITERIA</c:if>
																	<c:if test="${bill.langCode == 1}">मानदंड</c:if>
																</label>
															</div>
														</div>
														<div class="col-md-8">
															<div class="billidright">
																<div class="form-group">
																	<select class="form-control" name="cardType" id="cardType" required>
																		<option value="RATION">
																			<c:if test="${bill.langCode == 0}">RATION</c:if>
																			<c:if test="${bill.langCode == 1}">राशन</c:if>
																		</option>
																		<option value="AADHAR">
																			<c:if test="${bill.langCode == 0}">AADHAR</c:if>
																			<c:if test="${bill.langCode == 1}">आधार</c:if>
																		</option>
																		<option value="BHAMASHAH">
																			<c:if test="${bill.langCode == 0}">BHAMASHAH</c:if>
																			<c:if test="${bill.langCode == 1}">भामाशाह</c:if>
																		</option>
																	</select>
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-6 col-md-offset-3">
														<div class="col-md-4">
															<div class="billid">
																<label for="exampleInputEmail1" class="bmd-label-floating">
																	<c:if test="${bill.langCode == 0}">NUMBER</c:if>
																	<c:if test="${bill.langCode == 1}">संख्या</c:if>
																</label>
															</div>
														</div>
														<div class="col-md-8">
															<div class="billidright">
																<div class="form-group">
																	<input type="text" class="form-control" name="cardNumber"
																		id="cardNumber" required="required" autocomplete="off">
																	<div id="errCardNumber"></div>
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="twobtn">
													<div class="twobtn">
														<button class="btn btn-default getone" id="checkForm"
															type="button">
															<c:if test="${bill.langCode == 0}">Get Details</c:if>
															<c:if test="${bill.langCode == 1}">विवरण प्राप्त करें</c:if>
														</button>
														<button class="btn btn-default getone" type="reset">
															<c:if test="${bill.langCode == 0}">RESET</c:if>
															<c:if test="${bill.langCode == 1}">रीसेट</c:if>
														</button>
													</div>
												</div>
											</form>
										</div>
									</div>
								</div>
								<div class="button_div">
									<a href="javascript:void(0);" onclick="getSP('InformationPanchayatService')">
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
		<div id="loading" style="display: none;">
			<img id="loading-image" src="img/ajax-loader.gif" alt="Loading..." />
		</div>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/jquery.scrollTo.js"></script>
		<script type="text/javascript" src="js/app-inner.js"></script>
	
		<script type="text/javascript"
			src="js/KioskServices/BackButtonDisable.js"></script>
	
		<script type="text/javascript">
			$(function() {
				$("#checkForm").on('click', function() {
					$('#loading').show();
					$("#rationCard").attr("action", "fetchRation");
					$("#rationCard").attr("method", "GET");
					$("#rationCard").submit();
				})
			});
			
			function getSP(pageName) {
				$("#serviceProviderPage").val(pageName);
				document.getElementById("spd").action = "utilityBills";
				document.getElementById("spd").method = "post";
				document.getElementById('spd').submit();
			}
		</script>
	</body>
</html>