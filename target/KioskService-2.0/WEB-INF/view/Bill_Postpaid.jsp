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
			<title>Service Providers</title>
		</c:if>
		<c:if test="${bill.langCode == 1}">
			<title>सेवा प्रदाता</title>
		</c:if>
		<link
			href="https://fonts.googleapis.com/css?family=Noto+Sans|Nunito+Sans:600|700"
			rel="stylesheet" />
		<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
		<link rel="stylesheet" href="css/bootstrap-material-design.min.css" />
		<link rel="icon" href="img/favicon.ico" type="image/x-icon" />
		<link href="css/my-style.css" type="text/css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="css/slick-theme.css" />
		<link rel="stylesheet" type="text/css" href="css/slick.css" />
	</head>

	<body style="background: url(img/inner-bg.png); background-size: cover;">
		<div id="wrapper">
			<div id="mask">
				<div id="service" class="item utiltyservice">
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
						<div class="fourservices">
							<div class="innermid">
								<div class="container main_row">
									<form id="spd1">
										<input type="hidden" name="langCode" id="langCode" value="${bill.langCode}">
										<input type="hidden" name="serviceProviderPage" id="serviceProviderPage" />
									</form>
									<form method="post" id="spd">
										<input type="hidden" name="serviceProviderID" id="serviceProviderID" />
										<input type="hidden" name="serviceProviderName" id="serviceProviderName" />
										<input type="hidden" name="langCode" id="langCode" value="${bill.langCode}"/>
									</form>
									<div class="row sliderow">
										<div class="billmidmain">
											<c:if test="${bill.langCode == 0}">
												<h1 class="">POSTPAID BILL</h1>
											</c:if>
											<c:if test="${bill.langCode == 1}">
												<h1 class="">पोस्टपेड बिल</h1>
											</c:if>
											<ul id="" class="servicepro slider">
												<li>
													<div class="oneservices">
														<a href="javascript:void(0);"
															onclick="getSP('2575','BSNL')" class="panel"> <img
															src="img/Logos/BSNL.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<h6 style="padding-top: 20px; color: black;">BSNL</h6>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<h6 style="padding-top: 20px; color: black;">बीएसएनएल</h6>
															</c:if>
														</a>
													</div>
												</li>
												<li>
													<div class="oneservices">
														<a href="javascript:void(0);"
															onclick="getSP('1214','AIRTEL')" class="panel"> <img
															src="img/Logos/Airtel.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<h6 style="padding-top: 20px; color: black;">Airtel</h6>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<h6 style="padding-top: 20px; color: black;">एयरटेल</h6>
															</c:if>
														</a>
													</div>
												</li>
												<li>
													<div class="oneservices">
														<a href="javascript:void(0);"
															onclick="getSP('1220','IDEA')" class="panel"> <img
															src="img/Logos/Idea.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<h6 style="padding-top: 20px; color: black;">Idea</h6>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<h6 style="padding-top: 20px; color: black;">आइडिया</h6>
															</c:if>
														</a>
													</div>
												</li>
												<li>
													<div class="oneservices">
														<a href="javascript:void(0);"
															onclick="getSP('1219','Vodafone')" class="panel"> <img
															src="img/Logos/vodafone.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<h6 style="padding-top: 20px; color: black;">Vodafone</h6>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<h6 style="padding-top: 20px; color: black;">वोडाफोन</h6>
															</c:if>
														</a>
													</div>
												</li>
											</ul>
										</div>
									</div>
								</div>
								<div class="button_div">
									<a href="javascript:void(0);" onclick="getSP1('Serviceprovider')" class="panel">
										<c:if test="${bill.langCode == 0}">
											<img src="img/new/back.png"	alt="" />
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
		<script type="text/javascript" src="js/slick.js"></script>
		<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
	
		<script type="text/javascript">
		    function getSP(id,name) {
		    	$("#serviceProviderID").val(id);
		    	$("#serviceProviderName").val(name);
		    	var langCode = $("#langCode").val();
		    	if(langCode == 0)
					document.getElementById("spd").action = "serviceProviderInfo";
		    	if(langCode == 1)
					document.getElementById("spd").action = "serviceProviderInfoHi";
		    	
				document.getElementById("spd").method = "post";
				$("#spd").submit();
			}
		    
		    function getSP1(pageName) {
				$("#serviceProviderPage").val(pageName);
				document.getElementById("spd1").action = "utilityBills";
				document.getElementById("spd1").method = "post";
				document.getElementById('spd1').submit();
			}
		    
		    $(document).ready(function () {
		
		        $(".servicepro").slick({
		            dots: false,
		            infinite: true,
		            slidesToShow: 6,
		            slidesToScroll: 6
		        });
		    });
	    </script>
	</body>
</html>