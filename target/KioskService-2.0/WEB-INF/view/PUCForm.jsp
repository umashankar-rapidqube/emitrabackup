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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<c:if test="${serviceProvider.langCode == 0}">
			<title>Service Providers</title>
		</c:if>
		<c:if test="${serviceProvider.langCode == 1}">
			<title>सेवा प्रदाता</title>
		</c:if>
		<link
			href="https://fonts.googleapis.com/css?family=Noto+Sans|Nunito+Sans:600|700"
			rel="stylesheet" />
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
									<div class="row">
										<div class="billmidmain">
											<c:if test="${serviceProvider.langCode == 0}">
												<h1>PLEASE ENTER DETAILS FOR PUC TRANSPORT SERVICE</h1>
											</c:if>
											<c:if test="${serviceProvider.langCode == 1}">
												<h1>कृपया पीयूसी परिवहन सेवा के लिए विवरण दर्ज करें</h1>
											</c:if>
											<form id="spd">
												<input type="hidden" name="langCode" id="langCode"
													value="${serviceProvider.langCode}"> <input
													type="hidden" name="serviceProviderPage"
													id="serviceProviderPage" />
												<div class="feildone" style="text-align: center" id="insertDetails">
													<br>
													<div class="col-sm-6 form-group required">
														<div class="form-group">
															<c:if test="${serviceProvider.langCode == 0}">
																<label class="control-label col-sm-4" style="text-align: left;">Vehicle Reg. No</label>
															</c:if>
															<c:if test="${serviceProvider.langCode == 1}">
																<label class="control-label col-sm-4" style="text-align: left;">वाहन पंजी. संख्या</label>
															</c:if>
															<div class="col-sm-8">
																<input type="text" class="form-control" id="vehicleRegNo" placeholder="Vehicle Reg No" 
																	onkeyup="checkVehicleRegex();" required="required" style="text-transform: uppercase;">
															</div>
														</div>       
													</div>
													<div class="billidright" id="errVehicleNo">
														<span id="errVehicleNoMessage" style="display:block; color:grey; font-size:15px;">Note * :- Please enter a valid Vehicle Registration Number.</span>
													</div>
													<br>
													<br>
													<div class="col-sm-6 form-group required">
														<c:if test="${serviceProvider.langCode == 0}">
															<label class="control-label col-sm-4" style="text-align: left;">Vehicle Type</label>
														</c:if>
														<c:if test="${serviceProvider.langCode == 1}">
															<label class="control-label col-sm-4" style="text-align: left;">वाहन का प्रकार</label>
														</c:if>
														<div class="col-sm-8">
															<select aria-required="true" id="vehicleType" class="form-control col-sm-12 eformclass required" required="required" disabled="disabled">
																<c:if test="${serviceProvider.langCode == 0}">
																	<option value="0" selected>---Select---</option>
																	<option value="1">Two Wheeler</option>
																	<option value="2">Four Wheeler</option>
																</c:if>
																<c:if test="${serviceProvider.langCode == 1}">
																	<option value="0" selected>---चुनें---</option>
																	<option value="1">दो पहिया</option>
																	<option value="2">चार पहिया</option>
																</c:if>
															</select>
														</div>
													</div>
													<div class="col-sm-6 form-group required">
														<c:if test="${serviceProvider.langCode == 0}">
															<label class="control-label col-sm-4" style="text-align: left;">Fuel Type</label>
														</c:if>
														<c:if test="${serviceProvider.langCode == 1}">
															<label class="control-label col-sm-4" style="text-align: left;">ईंधन का प्रकार</label>
														</c:if>
														<div class="col-sm-8">
															<select aria-required="true" id="fuelType" class="form-control col-sm-12 eformclass required" required="required" disabled="disabled">
																<c:if test="${serviceProvider.langCode == 0}">
																	<option value="0" selected>---Select---</option>
																	<option value="1">PETROL</option>
																	<option value="2">DIESEL</option>
																</c:if>
																<c:if test="${serviceProvider.langCode == 1}">
																	<option value="0" selected>---चुनें---</option>
																	<option value="1">पेट्रोल</option>
																	<option value="2">डीज़ल</option>
																</c:if>
															</select>
														</div>
													</div>
													<div class="col-sm-6 form-group required">
														<c:if test="${serviceProvider.langCode == 0}">
															<label class="control-label col-sm-4" style="text-align: left;">Expiry Duration</label>
														</c:if>
														<c:if test="${serviceProvider.langCode == 1}">
															<label class="control-label col-sm-4" style="text-align: left;">समाप्ति अवधि</label>
														</c:if>
														<div class="col-sm-8">
															<select aria-required="true" id="expDuration" class="form-control col-sm-12 eformclass required" required="required" disabled="disabled">
																<c:if test="${serviceProvider.langCode == 0}">
																	<option value="0" selected>---Select---</option>
																	<option value="1">One Month</option>
																	<option value="2">More Than One Month</option>
																</c:if>
																<c:if test="${serviceProvider.langCode == 1}">
																	<option value="0" selected>---चुनें---</option>
																	<option value="1">एक महीना</option>
																	<option value="2">एक महीने से ज़्यादा</option>
																</c:if>
															</select>
														</div>
													</div>
													<div class="twobtn">
														<div class="twobtn">
															<button class="btn btn-default getone" id="getBillDetails" type="button" onclick="getBill();" disabled="disabled">
																<c:if test="${serviceProvider.langCode == 0}">Get Bill Details</c:if>
																<c:if test="${serviceProvider.langCode == 1}">बिल विवरण प्राप्त करें</c:if>
															</button>
															<button class="btn btn-default btn-raised getone" type="reset" onclick="reset();">
																<c:if test="${serviceProvider.langCode == 0}">RESET</c:if>
																<c:if test="${serviceProvider.langCode == 1}">रीसेट</c:if>
															</button>
														</div>
													</div> 
												</div>
												<br><br>
												<div id="fetchDetails" style="display: none;">
													<table class="table table-striped table-bordered">
														<thead>
															<tr>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Vehicle Reg No</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">वाहन पंजी. संख्या</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Consumer Key</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">उपभोक्ता कुंजी</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Vehicle Type</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">वाहन का प्रकार</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Fuel Type</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">ईंधन का प्रकार</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Expiry Duration</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">समाप्ति अवधि</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Payable Amount</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">भुगतान योग्य राशि</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Commission Charges(Inclusive Of All Taxes)</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">आयोग शुल्क (सभी करों सहित)</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Other Charge</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">अन्य शुल्क</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Total Amount</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">कुल रकम</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Mobile Number</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">मोबाइल नंबर</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Email ID</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">ईमेल आईडी</c:if>
																</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td>
																	<label id="lblVehicleRegNo"></label>
																</td>
																<td>
																	<label id="lblConsumerKey"></label>
																</td>
																<td>
																	<label id="lblVehicletype"></label>
																</td>
																<td>
																	<label id="lblFuelType"></label>
																</td>
																<td>
																	<label id="lblExpDuration"></label>
																</td>
																<td>
																	<label id="lblPayableAmt"></label>
																</td>
																<td>
																	<label id="lblCommCharges"></label>
																</td>
																<td>
																	<label id="lblOthCharges"></label>
																</td>
																<td>
																	<label id="lblTotalAmount"></label>
																</td>
																<td>
																	<input type="text" class="form-control" id="mobileNumber" placeholder="Enter Mobile No" placeholder="Enter Mobile Number"
																	 onkeyup="isNumberKey(event , this);" required="required" autocomplete="off" maxlength="10">
																</td>
																<td>
																	<input type="text" class="form-control" id="email_id" placeholder="Enter Email ID">
																</td>
															</tr>
														</tbody>
														<tfoot>
															<tr>
																<td colspan="11" align="center">
																	<button id="billDetails" class="btn btn-default getone" id="checkForm" type="button" onclick="submitBillDetails();" disabled="disabled">
																		<c:if test="${serviceProvider.langCode == 0}">Pay Bill</c:if>
																		<c:if test="${serviceProvider.langCode == 1}">बिल का भुगतान</c:if>
																	</button>
																	<span id="reqMobileMessage" style="display: none;"></span>
																</td>
															</tr>
														</tfoot>
													</table>
												</div>
											</form>
											<form id="savePUCDetails">
												<input type="hidden" name="langCode" id="langCode1" value="${serviceProvider.langCode}">
												<input type="hidden" name="serviceProviderID" id="serviceProviderID" value="${serviceProvider.serviceProviderID}" />
												<input type="hidden" name="serviceProviderName" id="serviceProviderName" value="${serviceProvider.serviceProviderName}" />
												<input type="hidden" name="fuelType" id="fuelType1" value="">
												<input type="hidden" name="expDuration" id="expDuration1" value="">
												<input type="hidden" name="vehicleType" id="vehicleType1" value="">
												<input type="hidden" name="vehicleRegNo" id="vehicleRegNo1" value="">
												<input type="hidden" name="payableAmt" id="payableAmt" value="">
												<input type="hidden" name="commCharges" id="commCharges" value="">
												<input type="hidden" name="othCharges" id="othCharges" value="">
												<input type="hidden" name="totalAmount" id="totalAmount" value="">
												<input type="hidden" name="consumerName" id="consumerName" value="NA">
												<input type="hidden" name="consumerKey" id="consumerKey" value="">
												<input type="hidden" name="emailID" id="emailID" value="">
												<input type="hidden" name="mobileNo" id="mobileNo" value="">
												<input type="hidden" name="ssoId" id="ssoId" value="${serviceProvider.ssoID}">
											</form>
										</div>
									</div>
								</div>
								<div class="button_div">
									<a href="javascript:void(0);" onclick="getSP('Serviceprovider')" class="panel">
										<c:if test="${serviceProvider.langCode == 0}">
											<img src="img/new/back.png"	alt="" />
										</c:if>
										<c:if test="${serviceProvider.langCode == 1}">
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
		<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
		<script type="text/javascript" src="js/KioskServices/PucService.js"></script>
	</body>
</html>