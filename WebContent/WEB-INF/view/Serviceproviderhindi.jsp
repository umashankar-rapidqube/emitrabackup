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
	    <title>सेवा प्रदाता</title>
	    <link href="https://fonts.googleapis.com/css?family=Noto+Sans|Nunito+Sans:600|700" rel="stylesheet" />
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
	                                            <img src="img/cartooon-bg-inner.png" alt="" />
	                                            <img src="img/bubble.png" class="bubble" alt="" />
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="col-md-4">
	                                    <div class="logomain midinnerlogomain">
	                                        <div class="logo midinnerlogo">
	                                            <a href="#">
	                                                <img src="img/logo-inner.png" alt="" /></a>
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="col-md-4">
	                                    <a href="index" class="panel pull-right homebtn">
	                                        <img src="img/new/home.png" alt="" /></a>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="fourservices">
	                        <div class="innermid">
	                            <div class="container main_row">
	                                <div class="row sliderow">
	                                    <div class="billmidmain">
	                                    <h1 class="">उपयोगिता बिल और अन्य भुगतान</h1>
		                                    <form id="spd">
		                                    	<input type="hidden" name="langCode" id="langCode" value="1">
												<input type="hidden" name="serviceProviderPage" id="serviceProviderPage" />
											</form>
											<form method="post" id="spd1">
												<input type="hidden" name="serviceProviderID" id="serviceProviderID" />
												<input type="hidden" name="serviceProviderName" id="serviceProviderName" />
												<input type="hidden" name="langCode" id="langCode" value="1">
											</form>
	                                        <ul id="" class="servicepro slider">
												<li>
													<div class="oneservices">
														<a href="javascript:void(0);"
															onclick="getSP('Bill_Electricity')" class="panel"> <img
															src="img/Logos/electricity_bills.png" alt="" />
															<h6 style="padding-top: 20px; color: black;">बिजली</h6>
														</a>
													</div>
												</li>
												<li>
													<div class="oneservices">
														<a href="javascript:void(0);"
															onclick="getSP('Bill_Water')" class="panel"> <img
															src="img/Logos/water_bills.png" alt="" />
															<h6 style="padding-top: 20px; color: black;">पानी</h6>
														</a>
													</div>
												</li>
												<li>
													<div class="oneservices">
														<a href="javascript:void(0);"
															onclick="getSP('Bill_Postpaid')" class="panel"> <img
															src="img/Logos/postpaid_bills.png" alt="" />
															<h6 style="padding-top: 20px; color: black;">पोस्टपेड(लैंडलाइन और मोबाइल)</h6>
														</a>
													</div>
												</li>
												<li>
													<div class="oneservices">
														<!-- <a href="javascript:void(0);"
															onclick="getSP('Bill_Puc')" class="panel"> <img
															src="img/Logos/Transport_Service.png" alt="" />
															<h2>परिवहन</h2>
														</a> -->
														<a href="javascript:void(0);"
															onclick="getSP1('2878','PUC Pollution Penalty Fee')" class="panel">
															<img src="img/Logos/PUC_Service.png" alt="" />
																<h6 style="padding-top: 20px; color: black;">पीयूसी पोल्यूशन पेनल्टी फीस</h6>
														</a>
													</div>
												</li>
	                                        </ul>
	                                    </div>
	                                </div>
	                            </div>
	                            <div class="button_div">
	                                <a href="servicehindi" class="panel">
	                                    <img src="img/new/backhindi.png" alt="" /></a>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	    <script	type="text/javascript" src="js/jquery.min.js"></script>
	    <script type="text/javascript" src="js/bootstrap.min.js"></script>
	    <script type="text/javascript" src="js/jquery.scrollTo.js"></script>
	    <script type="text/javascript" src="js/app-inner.js"></script>
	    <script type="text/javascript" src="js/slick.js"></script>
		<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
	    
		<script type="text/javascript">
			
			function getSP(pageName) {
		    	$("#serviceProviderPage").val(pageName);
				document.getElementById("spd").action = "utilityBills";
				document.getElementById("spd").method = "post";
				$("#spd").submit();
			}
			function getSP1(id,name) {
		    	$("#serviceProviderID").val(id);
		    	$("#serviceProviderName").val(name);
	    		
		    	document.getElementById("spd1").action = "puc";
		    	document.getElementById("spd1").method = "post";
				$("#spd1").submit();
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