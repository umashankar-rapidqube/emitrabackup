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
<title>GOVERNMENT SERVICES</title>
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans|Nunito+Sans:600|700"
	rel="stylesheet" />
<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap-material-design.min.css" />
<link rel="icon" href="img/favicon.ico" type="image/x-icon" />
<link href="css/my-style.css" type="text/css" rel="stylesheet" />
</head>

<body style="background: url(img/inner-bg.png); background-size: cover;" onload="FetchData();">
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
										<h1>Updation in Mobile Number in Bhamashah Card</h1>
										<form>
											<div class="feildone" style="text-align: center">
												<div class="col-md-6 col-md-offset-3">
													<div class="col-md-6">
														<div class="billid">
															<label for="exampleInputEmail1"
																class="bmd-label-floating">Aadhar Card Number <span style="color:red;">*</span></label>
														</div>
													</div>
													<div class="col-md-6">
														<div class="billidright">
															<div class="form-group">
																<input type="text" class="form-control"
																	required="required" autocomplete="off"
																	name="aadharNo"	id="aadharNo" data-lang-code="ENG"
																	onkeypress="return isNumberKey(event, this , 'uid')"
																	maxlength="12" placeholder="Enter Aadhar Card No"
																	value="${aadharNo}" />
															</div>
														</div>
													</div>
													<div class="billidright" id="errAadharNo">
														<span id="errAadharNoMessage" style="display:block; color:grey; font-size:15px;">Note * :- An OTP will be sent on the mobile number linked with aadhar ID.</span>
													</div>
												</div>
											</div>
											<div class="twobtn">
												<button class="btn btn-default getone" id="uidSubmitButton"
													onclick="getAadharAuth();" type="button" disabled="disabled">SUBMIT</button>
												<button class="btn btn-default getone" id="uidResetButton"
													onclick="reset();" type="reset">RESET</button>
											</div>
											<br>
											<hr>
											<br>
										</form>
										
										<input value="${submitButtonAction}" type="hidden" id="submitButtonAction">
										
										<form id="updateToBhamashah">
											<input value="" type="hidden" id="updateCount" name="updateCount">
											<input value="" type="hidden" id="transId" name="transId">
											<input type="hidden" name="langCode" value="0">
										</form>
										
										<div id="updateToBhamashahBack" align="center"
											style="display: none;">
											<input value="${updateCount}" type="hidden" id="updateCountFromServer">
											<input value="${transId}" type="hidden" id="transIdFromServer">
											<div class="twobtn">
												<button class="btn btn-default getone" id="updateToBhamashahButton"
													 onclick="updateToBhamashahBack();" type="button">TRY AGAIN</button>
											</div>
										</div>
										<div id="dataUpdateMobileDiv" align="center"
											style="display: none;">
											<form>
												<div class="feildone" style="text-align: center">
													<div class="col-md-6 col-md-offset-3">
														<div class="col-md-6">
															<div class="billid">
																<label for="exampleInputEmail1"
																	class="bmd-label-floating">Mobile Number <span style="color:red;">*</span></label>
															</div>
														</div>
														<div class="col-md-6">
															<div class="billidright">
																<div class="form-group">
																	<input type="text" class="form-control"
																		required="required" autocomplete="off"
																		name="mobileNumber" id="mobileNumber"
																		placeholder="Enter Mobile Number"
																		onkeypress="return isNumberKey(event, this , 'mob')"
																		maxlength="10">
																</div>
															</div>
														</div>
														<div class="billidright" id="errMobileNo">
															<span id="errMobileNoMessage" style="display:block; color:grey; font-size:15px;">Note * :- An OTP will be sent to the mobile number entered.</span>
														</div>
													</div>
												</div>
												<div class="twobtn">
													<button class="btn btn-default getone" id="verifyMobileNo"
														type="button" disabled="disabled">UPDATE MOBILE NUMBER</button>
												</div>
											</form>
										</div>
										<div id="dataUpdateBnkAccDiv" style="display: none;">
											<form>
												<label for="exampleInputEmail1" class="bmd-label-floating">Update
													Bank Account Details</label>
												<div class="feildone">
													<div class="col-md-6 col-md-offset-3">
														<div class="col-md-6">
															<div class="billid">
																<input type="text" class="form-control"
																	required="required" autocomplete="off" name="ifscCode"
																	id="ifscCode" placeholder="Enter IFSC Code">
															</div>
														</div>
														<div class="col-md-6">
															<div class="billidright">
																<div class="form-group">

																	<button class="btn btn-default getone"
																		id="getIFSCDetails" type="button">Check IFSC
																		Code</button>
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="feildone" style="display: none;"
													id="bankDetails">
													<div class="col-md-6 col-md-offset-3">
														<div class="col-md-6">
															<div class="billid" style="text-align: left;">
																<label for="exampleInputEmail1"
																	class="bmd-label-floating">Bank Name</label>
															</div>
														</div>
														<div class="col-md-6">
															<div class="billid" style="text-align: left;">
																<label for="exampleInputEmail1"
																	class="bmd-label-floating">Branch Name</label>
															</div>
														</div>
													</div>
													<div class="col-md-6 col-md-offset-3">
														<div class="col-md-6">
															<div class="billid">
																<input type="text" class="form-control"
																	required="required" disabled="disabled" name="bnkName"
																	id="bnkName" value="">
															</div>
														</div>
														<div class="col-md-6">
															<div class="billid">
																<input type="text" class="form-control"
																	required="required" disabled="disabled"
																	name="branchName" id="branchName" value="">
															</div>
														</div>
													</div>
													<div class="col-md-6 col-md-offset-3">
														<div class="col-md-6">
															<div class="billid" style="text-align: left;">
																<label for="exampleInputEmail1"
																	class="bmd-label-floating">Account Number</label>
															</div>
														</div>
														<div class="col-md-6">
															<div class="billid" style="text-align: left;">
																<label for="exampleInputEmail1"
																	class="bmd-label-floating">Confirm Account
																	Number</label>
															</div>
														</div>
													</div>
													<div class="col-md-6 col-md-offset-3">
														<div class="col-md-6">
															<div class="billid">
																<input type="password" class="form-control"
																	required="required"
																	onfocusout="setLengthOfCnfrmAccNo()" name="accNumber"
																	id="accNumber" value="">
															</div>
														</div>
														<div class="col-md-6">
															<div class="billid">
																<input type="text" class="form-control"
																	required="required" onkeyup="matchAccountNo('ENG')"
																	name="cnfrmAccNumber" id="cnfrmAccNumber" value="">
																<span id="accNoMatched" style="display: none;"></span>
															</div>
														</div>
													</div>
													<div class="twobtn">
														<button class="btn btn-default getone"
															id="updateAccNumber" type="button">UPDATE
															ACCOUNT NUMBER</button>
													</div>
												</div>
											</form>
										</div>
										<span id="updateMessage" style="display: block">${message}</span>
									</div>
								</div>
							</div>
							<form id="govtPayment">
								<input type="hidden" name="langCode" id="langCode" value="0">
								<input type="hidden" name="token" id="token" value="">
								<input type="hidden" name="amount" id="amount" value="10"> 
								<input type="hidden" name="serviceProviderName" id="serviceProviderName" value="${serviceProviderName}" />
								<input type="hidden" name="serviceProviderID" id="serviceProviderID" value="${serviceProviderID}" />
								<input	type="hidden" name="subServiceID" id="subServiceID" value="">
								<input type="hidden" name="transactionID" id="transactionID" value=""> 
								<input type="hidden" name="memberDetails" id="memberDetails" value=""> 
							</form>
							<form id="spd1">
								<input type="hidden" name="langCode" id="langCode" value="0" />
								<input type="hidden" name="serviceProviderPage" id="serviceProviderPage" />
							</form>
							<div class="button_div">
								<a href="javascript:void(0);" onclick="getSP('GovernmentServiceProvider')" class="panel">
									<img src="img/new/back.png" alt="" />
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
	<script type="text/javascript" src="js/jquery.mCustomScrollbar.concat.min.js"></script>
	<script type="text/javascript" src="js/KioskServices/bhamashahCardServices.js"></script>
	
	<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
	

	<!-- MODAL DIALOGUE FOR BHAMASHAH CLIENT NAMES -->
	<div class="modal fade" id="modalForBhamashahDetails" role="dialog">
		<div class="modal-dialog modal-lg">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Bhamashah Family Details</h4>
				</div>
				<div class="modal-body">
					<div id="content-1" class="content mCustomScrollbar" style="height: 369px;">
						<table class="table" id="familyMemberTable">
							<thead>
								<tr>
									<th>Select</th>
									<th>Name</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>
										<div class="radio">
											<label><input type="radio" name="radioName"
												onclick="myFunction(this.value)" value="Gopal"></label>
										</div>
									</td>
									<td>Gopal (Male)</td>
								</tr>
								<tr>
									<td>
										<div class="radio">
											<label><input type="radio" name="radioName"
												onclick="myFunction(this.value)" value="Gita"></label>
										</div>
									</td>
									<td>Gita (Female)</td>
								</tr>
								<tr>
									<td>
										<div class="radio">
											<label><input type="radio" name="radioName"
												onclick="myFunction(this.value)" value="Harphool Verma"></label>
										</div>
									</td>
									<td>Harphool Verma (Male)</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default getone"
						data-dismiss="modal" id="updateMobileNo" value="mobile"
						disabled="disabled">Update Mobile</button>
					<!-- <button type="button" class="btn btn-default getone"
						data-dismiss="modal" id="updateBankAccNo" value="bankAcc"
						disabled="disabled">Update Bank A/C Number</button> -->
				</div>
			</div>
		</div>
	</div>

	<!-- Modal Dialogue for OTP Insertion -->
	<div class="modal fade" id="modalForOtpUid" role="dialog"
		data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Please Enter OTP For Verification</h4>
				</div>
				<div class="modal-body">
					<input type="text" class="form-control" required="required"
						placeholder="Enter OTP" autocomplete="off" name="aadharOtp"
						id="aadharOtp">
				</div>
				<span id="wrongOtp" style="display: none;"></span>
				<div class="modal-footer" style="text-align: center">
					<button type="button" class="btn btn-default getone" id="submitOtp">Submit
						OTP</button>
					<button type="button" class="btn btn-default getone"
						data-dismiss="modal" onclick="cancelWork();">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal Dialogue for Payment Assurance -->
	<div class="modal fade" id="cnfrmPaymentModal" role="dialog"
		data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-body" style="text-align: center">
					<p id="paymentMessage">Are you sure you want to make payment
						for update mobile number ?</p>
					<p id="paymentChargeMessage">
						The charges would be <i class="fa fa-inr" aria-hidden="true"></i>10.
					</p>
					<!-- <div>
						<label for="exampleInputEmail1" class="bmd-label-floating">Please enter your details for BILL</label>
						<div class="feildone">
							<div class="col-md-6 col-md-offset-3">
								<div class="col-md-6">
									<div class="billid">
										<input type="text" class="form-control"
											autocomplete="off" name="emailIdBill"
											id="emailIdBill" placeholder="Enter Email Id">
									</div>
								</div>
								<div class="col-md-6">
									<div class="billid">
										<input type="text" class="form-control"
											required="required" autocomplete="off" name="mobileNumberBill"
											id="mobileNumberBill" placeholder="Enter Mobile Number">
									</div>
								</div>
							</div>
						</div>
					</div> -->
				</div>
				<div class="modal-footer" style="text-align: center">
					<button type="button" class="btn btn-default getone"
						id="paymentYes">Yes</button>
					<button type="button" class="btn btn-default getone"
						data-dismiss="modal">No</button>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		function FetchData() {
			
			var updateCountFromServer = document.getElementById("updateCountFromServer").value;
			
			if(updateCountFromServer == "1"){
				document.getElementById('updateToBhamashahBack').style.display = "block";
				document.getElementById("updateCount").value = 2;
				document.getElementById("transId").value = document.getElementById("transIdFromServer").value;
				
			}else {
				setInterval(function(){
					var submitButtonAction = document.getElementById("submitButtonAction").value;
					if(submitButtonAction == "Y"){
						document.getElementById("uidSubmitButton").disabled = "";
					}
				}, 2000);
			}
		}
		
		function getSP(pageName) {
			$("#serviceProviderPage").val(pageName);
			document.getElementById("spd1").action = "utilityBills";
			document.getElementById("spd1").method = "post";
			document.getElementById('spd1').submit();
		}
	</script>
</body>
</html>