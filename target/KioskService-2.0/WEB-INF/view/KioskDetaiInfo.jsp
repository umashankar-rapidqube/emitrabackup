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
<title>Kiosk Details</title>
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans|Nunito+Sans:600|700"
	rel="stylesheet" />
<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap-material-design.min.css" />
<link rel="icon" href="img/favicon.ico" type="image/x-icon" />
<link href="css/my-style.css" type="text/css" rel="stylesheet" />
<style>
.gridview  th {
	font-weight: bold;
	display: table-cell;
	vertical-align: inherit;
	color: white;
	font: bold 12px roboto, arial, sans-serif;
	padding: 3px 5px 3px 5px;
	/*background-color: #F5A320;*/
	background-color: #6a67ce;
	border: 1px solid #dedede;
	border-bottom: 1px solid #dedede;
	text-align: center;
}

.gridview  th a {
	color: white;
	text-decoration: none;
	padding: 3px 5px 3px 5px;
	font-size: 12px;
}

.gridview  td {
	padding: 13px 5px 2px 3px;
	font: normal 11px roboto, arial, sans-serif;
	color: black;
	line-height: 17PX;
	background-color: white;
	border: 1px solid #eee;
	vertical-align: top;
	text-align: center;
}

.gridview  td a {
	color: black;
	text-decoration: none;
	font-size: 11px;
	/*  padding: 2px 5px 2px 5px;*/
}

.gridviewhead {
	font-weight: bold;
	display: table-cell;
	vertical-align: inherit;
	color: white;
	font: bold 12px roboto, arial, sans-serif;
	padding: 3px 10px 3px 10px;
	background-color: #d43f3f;
	border: 1px solid #a22424;
	border-bottom: 2px solid #a22424;
}

.gridview tbody>tr:nth-child(odd)>td, .table-striped tbody>tr:nth-child(odd)>th
	{
	background-color: #f9f9f9;
}

.gridview tbody tr:hover td, .table tbody tr:hover th {
	background-color: #fdfdfd;
}

.gridview tfoot td {
	font: bold 12px roboto, arial, sans-serif;
	color: #333;
}

.pagination {
	padding: 3px;
	margin: 3px;
	text-align: center;
}

.pagination  td table {
	border-collapse: collapse;
	text-align: center;
}

.pagination   td table td {
	padding: 2px 2px 2px 2px;
	border-collapse: collapse;
	border: 0px solid #eee;
}
</style>
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
									<a href="index" class="panel pull-right homebtn"> <img
										src="img/new/home.png" alt="" /></a>
								</div>
							</div>
						</div>
					</div>
					<div class="fourservices">
						<div class="innermid">
							<div class="container main_row">
								<div class="row">

									<div class="billmidmain">
										<h1>Emitra Kiosk Details</h1>
<div>
									
																<table class="gridview" cellspacing="0" cellpadding="0"
																	style="width: 100%; border-collapse: collapse;overflow: auto;">
																	<tr>
																		<th style="width: 5%;">Urban / Rural</th>
																		<th style="width: 15%;">District Name</th>
																		<th style="width: 5%;">Muncipality</th>
																		<!-- <th style="width: 10%;">Ward Name</th> -->
																		
																		<th style="width: 10%;">PanchayatSamiti</th>
																	<!-- 	<th style="width: 15%;">GramPanchayat</th> -->

																	</tr>


																	<tr style="height: 40px;">
																		<td><c:choose>
																						<c:when test="${ISRURAL=='N'}">
                                                                                       Urban
                                                                                     <br />
																						</c:when>
																						<c:otherwise>
                                                                                       Rural 
                                                                                       <br />
																						</c:otherwise>
																					</c:choose></td>
																		<td align="center">${DISTRICT}</td>
																		<td align="center">${MUNCIPALITY}</td>
																	<%-- 	<td align="center">${WARD}</td> --%>
																		
																		<td align="center">${PANCHAYATSAMITI}</td>
																	<%-- 	<td align="center">${GRAMPANCHAYAT}</td> --%>
																	</tr>
																	</table>
															
															</div>
														
															<div id="Content1_pnlgrv"
																style="width: 100%; height: 480px;overflow: auto;">

																<table class="gridview" cellspacing="0" cellpadding="0"
																	style="width: 100%; border-collapse: collapse;">
																		<thead>
																			<tr>
																				<th style="width: 5%;">Sr. No.</th>
																				<th style="width: 15%;">Kiosk Holder Name</th>
																				<th style="width: 20%;">Address</th>
																				<th style="width: 10%;">PhoneNumber</th>
																			</tr>
																		</thead>
																		<tbody>
																		<c:forEach varStatus="i" items="${requestScope.list}"
																			var="user">
																			<tr style="height: 40px;">
																				<td>${i.index+1}</td>
																				<td align="center">${user.vendorName}</td>
																				<td align="center">${user.address}</td>
																				<td align="center">${user.phoneNumber1}</td>
																			</tr>
																		</c:forEach>
																		</tbody>
																	</table>
																	</div>
									</div>
								</div>
							</div>
							<div class="button_div">
								<a href="InformationPanchayatService" class="panel"> <img
									src="img/new/back.png" alt=""></a>
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



</body>

</html>

