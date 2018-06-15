<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<html>
	<style>
		.div2 {
			width: 500px;
			height: 200px;
			padding: 50px;
			border: 1px solid grey;
			text-align: center;
		}
	</style>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<c:if test="${langCode == 0}">
			<title>RATION CARD INFORMATION</title>
		</c:if>
		<c:if test="${bill.langCode == 1}">
			<title>राशन कार्ड जानकारी</title>
		</c:if>
		<link href="https://fonts.googleapis.com/css?family=Noto+Sans|Nunito+Sans:600|700" rel="stylesheet" />
		<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
		<link rel="stylesheet" href="css/bootstrap-material-design.min.css" />
		<link rel="stylesheet" href="css/tablecustom.css" />
		<link rel="icon" href="img/favicon.ico" type="image/x-icon" />
		<link href="css/my-style.css" type="text/css" rel="stylesheet" />
	</head>

	<body style="background: url(img/inner-bg.png); background-size: cover;">
		<div id="wrapper">
			<div id="mask">
				<div id="payment_succ" class="item">
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
											<c:if test="${langCode == 0}"><img src="img/new/home.png" alt="" /></c:if>
											<c:if test="${langCode == 1}"><img src="img/new/homehindi.png" alt="" /></c:if>
										</a>
									</div>
								</div>
							</div>
						</div>
						<div class="fourservices">
							<div class="innermid">
								<div class="container main_row" style="min-height: 1000px">
									<div class="row">
										<div class="billmidmain ">
											<c:if test="${langCode == 0}"><h1 class="">Details of Ration Card Holder</h1></c:if>
											<c:if test="${langCode == 1}"><h1 class="">राशन कार्ड धारक का विवरण</h1></c:if>
											<form id="spd">
												<input type="hidden" name="langCode" id="langCode" value="${langCode}">
												<input type="hidden" name="serviceProviderPage" id="serviceProviderPage" value="FetchRationDetails"/>
											</form>
											<c:choose>
												<c:when test="${flag=='false'}">
													<span>${MSG}</span>
												</c:when>
												<c:otherwise>
													<c:if test="${langCode == 0}"><h1 class="">FPS Details</h1></c:if>
													<c:if test="${langCode == 1}"><h1 class="">एफपीएस विवरण</h1></c:if>
													<table class="table" border=1>
														<thead>
															<tr>
																<c:if test="${langCode == 0}">
																	<th>FPS Code</th>
																	<th>FPS Owner Name</th>
																	<th>FPS Address</th>
																</c:if>
																<c:if test="${langCode == 1}">
																	<th>एफपीएस कोड</th>
																	<th>एफपीएस मालिक का नाम</th>
																	<th>एफपीएस पता</th>
																</c:if>
															</tr>
														</thead>
														<tbody>
															<c:forEach varStatus="i" items="${requestScope.list}" var="user">
																<tr>
																	<td align="center">${user.fpsCode}</td>
																	<td align="center">${user.fpsOwnerName}</td>
																	<td align="center">${user.fpsAddress}, ${user.fpsBlock}, ${user.fpsDistrict}</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
													<br>
													<c:if test="${langCode == 0}"><h1 class="">Ration Card Details</h1></c:if>
													<c:if test="${langCode == 1}"><h1 class="">राशन कार्ड विवरण</h1></c:if>
													<table class="table" border=1>
														<thead>
															<tr>
																<c:if test="${langCode == 0}">
																	<th style="width: 8%;">CATEGORY NAME</th>
																	<th style="width: 12%;">NFSA (Y/N)</th>
																	<th style="width: 40%;">ADDRESS</th>
																	<th style="width: 15%;">RATION CARD NUMBER</th>
																	<th style="width: 25%;">RATION CARD HOLDER NAME</th>
																</c:if>
																<c:if test="${langCode == 1}">
																	<th style="width: 8%;">श्रेणी नाम</th>
																	<th style="width: 12%;">एनएफएसए (Y/N)</th>
																	<th style="width: 40%;">पता</th>
																	<th style="width: 15%;">राशन कार्ड नंबर</th>
																	<th style="width: 25%;">राशन कार्ड धारक का नाम</th>
																</c:if>
															</tr>
														</thead>
														<tbody>
															<c:forEach varStatus="i" items="${requestScope.list}"
																var="user">
																<tr style="height: 40px;">
																	<td align="center">${user.categoryName}</td>
																	<td align="center">${user.nfsa}</td>
																	<td align="center">${user.houseNameNo}, ${user.colonyStreet}, ${user.tehsil},${user.district},${user.state},PIN - ${user.pin}</td>
																	<td align="center">${user.rationCardNumber}</td>
																	<td align="center">${user.rationCardName}</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
													<br>
													<c:if test="${langCode == 0}">	<h1 class="">Family Details</h1></c:if>
													<c:if test="${langCode == 1}"><h1 class="">पारिवारिक विवरण</h1></c:if>
													<table class="scroll">
														<thead>
															<tr>
																<c:if test="${langCode == 0}">
																	<th style="text-align: center;">Sr No.</th>
																	<th style="text-align: center;">MEMBER NAME(ENGLISH/HINDI)</th>
																	<th style="text-align: center;">MEMBER RELATION(ENGLISH/HINDI)</th>
																	<th style="text-align: center;">AGE</th>
																</c:if>
																<c:if test="${langCode == 1}">
																	<th style="text-align: center;">अनु क्रमांक</th>
																	<th style="text-align: center;">सदस्य का नाम (इंग्लिश / हिंदी)</th>
																	<th style="text-align: center;">सदस्य संबंध (अंग्रेजी / हिंदी)</th>
																	<th style="text-align: center;">उम्र</th>
																</c:if>
															</tr>
														</thead>
														<tbody>
															<c:forEach varStatus="j" items="${requestScope.list}"
																var="user">
																<c:forEach varStatus="j" items="${user.familyDetail}" var="familydetail">
																	<tr>
																		<td style="text-align: center;">${familydetail.runningNumber}</td>
																		<td style="text-align: center;">${familydetail.memberNameEN}</td>
																		<td style="text-align: center;">${familydetail.memberRelationEN}</td>
																		<td style="text-align: center;">${familydetail.age}</td>
																	</tr>
																</c:forEach>
															</c:forEach>
														</tbody>
													</table>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</div>
								<div class="button_div">
									<a href="javascript:void(0);" onclick="getSP('RationCardService')">
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
		<script type="text/javascript" src="js/tablejs.js"></script>
	
		<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
		
		<script>
			function getSP(pageName) {
				$("#serviceProviderPage").val(pageName);
				document.getElementById("spd").action = "utilityBills";
				document.getElementById("spd").method = "post";
				document.getElementById('spd').submit();
			}
		</script>
	</body>
</html>