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
<title>सरकारी सेवा</title>
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans|Nunito+Sans:600|700"
	rel="stylesheet" />
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
							<div class="container main_row" style="min-height: 1000px">
								<div class="row">
									<div class="billmidmain ">
										<h1 class="">Details of Ration Card Holder</h1>

										<center>
											<h1 class="">FPS Details</h1>

											<table class="table" border=1 cellspacing="0" cellpadding="0">
												<thead>
													<c:choose>
														<c:when test="${MSG=='Invalid Data'}">
															<tr>
																<th>Message</th>
																<td>${MSG}</td>
															</tr>
														</c:when>
														<c:otherwise>
															<tr>
																<th>FPS Code</th>
																<th>FPS Owner Name</th>
																<th>FPS Address</th>
															</tr>
												</thead>
												<tbody>
													<tr>
														<c:forEach varStatus="i" items="${requestScope.list}"
															var="user">
															<td align="center">${user.fpsCode}</td>
															<td align="center">${user.fpsOwnerName}</td>
															<td align="center">${user.fpsAddress},
																${user.fpsBlock}, ${user.fpsDistrict}</td>
													</tr>
													</c:forEach>
													</c:otherwise>
													</c:choose>
												</tbody>
											</table>
										</center>


										<center>
											<h1 class="">Ration Card Details</h1>
											<table class="table" border=1 cellspacing="0" cellpadding="0">
												<c:choose>
													<c:when test="${MSG=='Invalid Data'}">
														<tr>
															<th>Message</th>
															<td>${MSG}</td>
														</tr>
													</c:when>
													<c:otherwise>
														<thead>
															<tr>
																<th>CATEGORY NAME</th>
																<th>NFSA (Y/N)</th>
																<th>ADDRESS</th>
																<th>RATION CARD NUMBER</th>
																<th>RATION CARD HOLDER NAME</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach varStatus="i" items="${requestScope.list}"
																var="user">
																<tr style="height: 40px;">
																	<td align="center">${user.categoryName}</td>
																	<td align="center">${user.nfsa}</td>
																	<td align="center">${user.houseNameNo},
																		${user.colonyStreet},
																		${user.tehsil},${user.district},${user.state},PIN -
																		${user.pin}</td>
																	<td align="center">${user.rationCardNumber}</td>
																	<td align="center">${user.rationCardName}</td>
																</tr>
															</c:forEach>
														</tbody>
													</c:otherwise>
												</c:choose>
											</table>
										</center>

										<center>
											<h1 class="">Family Details</h1>
											<table class="scroll">
												<c:choose>
													<c:when test="${MSG=='Invalid Data'}">
														<tr>
															<th>Message</th>
															<td>${MSG}</td>
														</tr>
													</c:when>
													<c:otherwise>
														<thead>
															<tr>
																<th>SN</th>
																<th>MEMBER NAME(ENGLISH/HINDI)</th>
																<th>MEMBER RELATION(ENGLISH/HINDI)</th>
																<th>AGE</th>
															</tr>
														</thead>
														<tbody height="90px">
															<c:forEach varStatus="j" items="${requestScope.list}"
																var="user">
																<c:forEach varStatus="j" items="${user.familyDetail}"
																	var="familydetail">
																	<tr>
																		<td align="center">${familydetail.runningNumber}</td>
																		<td align="center">${familydetail.memberNameEN}</td>
																		<td align="center">${familydetail.memberRelationEN}</td>
																		<td align="center">${familydetail.age}</td>
																	</tr>
																</c:forEach>
															</c:forEach>
														</tbody>
													</c:otherwise>
												</c:choose>
											</table>
										</center>
									</div>


								</div>
							</div>
						</div>
					</div>
				</div>
				
								<div class="button_div">
									<a href="InformationPanchayatService" class="panel"> <img
										src="img/new/back.png"></a>
								</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/jquery.scrollTo.js"></script>
	<script type="text/javascript" src="js/app-inner.js"></script>
	<script type="text/javascript" src="js/tablejs.js"></script>

	<script type="text/javascript"
		src="js/KioskServices/BackButtonDisable.js"></script>
</body>
<script>
	/* 

	 var fpsCode = (${s}.detail.fpsDetail.fpsCode)
	 var category = ${s}.detail.rcDetail.familyDetail[1].memberNameEN;

	 console.log("category",category)
	 //var fpsOwnerName = (${s}.detail.fpsDetail.fpsOwnerName)
	 console.log("fpsCode",fpsCode)
	 document.getElementById("fpsCode").innerHTML = fpsCode;
	 document.getElementById("fpsOwnerName").innerHTML = ${s}.detail.fpsDetail.fpsOwnerName ;
	 document.getElementById("fpsAddress").innerHTML = ${s}.detail.fpsDetail.fpsAddress;
	 document.getElementById("fpsBlock").innerHTML = ${s}.detail.fpsDetail.fpsBlock;
	 document.getElementById("fpsDistrict").innerHTML = ${s}.detail.fpsDetail.fpsDistrict;
	 document.getElementById("categoryName").innerHTML = ${s}.detail.rcDetail.categoryName;
	 document.getElementById("nfsa").innerHTML = ${s}.detail.rcDetail.nfsa;
	 document.getElementById("houseNameNo").innerHTML = ${s}.detail.rcDetail.houseNameNo;
	 document.getElementById("rationCardNumber").innerHTML = ${s}.detail.rcDetail.rationCardNumber;
	 document.getElementById("rationCardName").innerHTML = ${s}.detail.rcDetail.rationCardName;
	 document.getElementById("runningNumber").innerHTML = ${s}.detail.rcDetail.nfsa;
	 document.getElementById("houseNameNo").innerHTML = ${s}.detail.rcDetail.houseNameNo;
	 document.getElementById("memberNameEN").innerHTML = ${s}.detail.rcDetail.familyDetail[1].memberNameEN;
	 document.getElementById("memberRelationEN").innerHTML = category;
	 document.getElementById("age").innerHTML = ${s}.detail.familyDetail[0].age;
	 */
</script>
</html>



