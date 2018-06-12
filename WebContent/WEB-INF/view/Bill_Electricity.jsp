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
										<a href="index" class="panel pull-right homebtn"> <img
											src="img/new/home.png" alt="" /></a>
									</div>
								</div>
							</div>
						</div>
						<div class="fourservices">
							<div class="innermid">
								<div class="container main_row">
									<form method="post" id="spd">
										<input type="hidden" name="serviceProviderID" id="serviceProviderID" />
										<input type="hidden" name="serviceProviderName" id="serviceProviderName" />
										<input type="hidden" name="langCode" id="langCode" value="${bill.langCode}"/>
								</form>
									<div class="row sliderow">
										<div class="billmidmain">
											<c:if test="${bill.langCode == 0}">
												<h1 class="">ELECTRICITY BILL</h1>
											</c:if>
											<c:if test="${bill.langCode == 1}">
												<h1 class="">बिजली बिल</h1>
											</c:if>
											<ul id="" class="servicepro slider">
												<li>
													<div class="oneservices">
														<a href="javascript:void(0);"
															onclick="getSP('3018','AJMER')" class="panel">
															<img src="img/Logos/AJMER.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<h6 style="padding-top: 20px; color: black;">Ajmer K Number bill deposition</h6>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<h6 style="padding-top: 20px; color: black;">अजमेर के नंबर बिल</h6>
															</c:if>
														</a>
													</div>
												</li>
												<li>
													<div class="oneservices">
														<a href="javascript:void(0);"
															onclick="getSP('2582','BCITS')" class="panel">
															<img src="img/Logos/BCITS.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<h6 style="padding-top: 20px; color: black;">BCITS K Number Payment</h6>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<h6 style="padding-top: 20px; color: black;">बीसीआईटीएस के नंबर भुगतान</h6>
															</c:if>
														</a>
													</div>
												</li>
												<li>
													<div class="oneservices">
														<a href="javascript:void(0);"
															onclick="getSP('2615','BESL')" class="panel">
															<img src="img/Logos/BESL.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<h6 style="padding-top: 20px; color: black;">BESL Bharatpur K Number</h6>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<h6 style="padding-top: 20px; color: black;">बीईएसएल भरतपुर के नंबर</h6>
															</c:if>
														</a>
													</div>
												</li>
												<li>
													<div class="oneservices">
														<a href="javascript:void(0);"
															onclick="getSP('2616','BKESL')" class="panel">
															<img src="img/Logos/BKESL.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<h6 style="padding-top: 20px; color: black;">BKESL Bikaner K Number</h6>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<h6 style="padding-top: 20px; color: black;">बीकेईएस बीकानेर के नंबर</h6>
															</c:if>
														</a>
													</div>
												</li>
												<li>
													<div class="oneservices">
														<a href="javascript:void(0);"
															onclick="getSP('1223','DISCOM')" class="panel">
															<img src="img/Logos/DISCOM.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<h6 style="padding-top: 20px; color: black;">DISCOM K No Electricity Bill</h6>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<h6 style="padding-top: 20px; color: black;">डिस्कॉम के नंबर बिजली बिल</h6>
															</c:if>
														</a>
													</div>
												</li>
												<li>
													<div class="oneservices">
														<a href="javascript:void(0);"
															onclick="getSP('2531','KEDL')" class="panel">
															<img src="img/Logos/KEDL.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<h6 style="padding-top: 20px; color: black;">KOTA - KEDL K NUMBER</h6>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<h6 style="padding-top: 20px; color: black;">कोटा - केडएल के नंबर</h6>
															</c:if>
														</a>
													</div>
												</li>
											</ul>
										</div>
									</div>
								</div>
								<div class="button_div">
									<c:if test="${bill.langCode == 0}">
										<a href="serviceprovider" class="panel">
											<img src="img/new/back.png"	alt="" />
										</a>
									</c:if>
									<c:if test="${bill.langCode == 1}">
										<a href="serviceproviderHi" class="panel"> 
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
		<script type="text/javascript" src="js/slick.js"></script>
		<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
	
		<script type="text/javascript">
		    function getSP(id,name) {
		    	$("#serviceProviderID").val(id);
		    	$("#serviceProviderName").val(name);
		    	var langCode = $("#langCode").val();
		    	if(langCode == 0)
					document.getElementById("spd").action = "discom";
		    	if(langCode == 1)
		    		document.getElementById("spd").action = "discomHi";
		    	
				document.getElementById("spd").method = "post";
				$("#spd").submit();
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