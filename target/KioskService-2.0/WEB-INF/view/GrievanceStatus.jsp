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
<title>GovernmentService</title>
<link href="css/font-awesome.css" type="text/css" rel="stylesheet" />
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

			<div id="fill" class="item gr">
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
								<div class="row circle-container">
									<div class="billmidmain">
										<h1 class="">Sampark Grievance Status</h1>


										<form id="Grievance_details" name="myForm">
										<div class="col-md-6 col-md-offset-3">
										<div class="col-md-7">
											<label><input type="radio" class="form-switch"
												name="Grievance" value="red" data-id="a" id="checked"
												checked> Grievance ID</label>
												</div> <div class="col-md-3"><label><input
												type="radio" class="form-switch" name="Grievance"
												value="green" data-id="b" id="notchecked"> Mobile
												No.</label>
												</div>
												</div>
											<div class="feildone" style="text-align: center"
												id="Grievance">
												<input type="hidden" name="langCode" value="0">
												<div class="col-md-6 col-md-offset-3">
													<div class="col-md-6">
														<div class="billid">
															<label for="exampleInputEmail1"
																class="bmd-label-floating">Enter Grievance ID <span
																style="color: red">*</span></label>
														</div>
													</div>
													<div class="col-md-6">
														<div class="billidright">
															<div class="form-group">
																<input type="text" class="form-control"
																	autocomplete="off" name="GrievanceId" id="gid"
																	placeholder="Enter Grievance ID" VALUE="" required
																	aria-required=”true”>

															</div>
														</div>
													</div>
													<div class="billidright" id="errTokenId1"
														style="margin-left: 153px;"></div>
												</div>

											</div>

											<div class="feildone" style="text-align: center" id="mobile">
												<div class="col-md-8 col-md-offset-3">
													<div class="col-md-3">
														<div class="billid">
															<label for="exampleInputEmail1"
																class="bmd-label-floating">Enter Mobile No. <span
																style="color: red">*</span></label>
														</div>
													</div>
													<div class="col-md-4">
														<div class="billidright">
															<div class="form-group">

																<input type="text" class="form-control"
																	placeholder="Enter Mobile No." maxlength="10"
																	autocomplete="off" name="mobileNo" id="MobileNo1"
																	value=""
																	onkeypress="return isNumberKey(event, this)"
																	required>



															</div>
														</div>
													</div>

													<div class="col-md-4">
														<div class="billid">
															<img id="Content1_imgOTPVeri" title="Not Verified"
																src="img/new/NotVerified.png" style="width: 15px;">

															<input type="submit" name="ctl00$Content1$btnSendOTP"
																value="Send OTP to Verify" id="Content1_btnSendOTP"
																tabindex="5" class="btnOTP">

														</div>
													</div>
													<div class="billidright" id="errTokenId"
														style="margin-left: 153px;"></div>
												</div>

											</div>

											<div class="twobtn">
												<button class="btn btn-default getone" id="checkForm"
													type="button">Submit</button>

												<button class="btn btn-default btn-raised getone"
													id="ResetButton" type="reset">Reset</button>
											</div>
											<span id="updateMessage" style="display: none"></span>

										</form>
										<form id="spd1">
											<input type="hidden" name="langCode" id="langCode" value="0">
											<input type="hidden" name="serviceProviderPage" id="serviceProviderPage" />
										</form>
									</div>
								</div>


								<div id="Content1_divmain" style="display: none">
									<table class="gridview" align="center" width="100%"
										cellspacing="0" cellpadding="0">
										<thead>
											<tr>
												<th colspan="4"><span id="Content1_lblheading">Basic
														Details Of Your Grievance</span></th>

											</tr>
										</thead>
										<tbody style="width: 90%">

											<tr>

												<td style="width: 20%; text-align: left;"><span
													class="lblblack">Grievance ID</span></td>
												<td style="width: 30%; text-align: left;"><span
													id="Content1_lblgrid">${GrievanceId}</span> <!-- 02180433231565 -->
												</td>
												<td style="width: 20%; text-align: left;"><span
													class="lblblack">Grievance Date</span></td>
												<td style="width: 30%; text-align: left;"><span
													id="Content1_lblgrdt">${GDate}</span></td>
											</tr>

											<tr>
												<td style="width: 20%; text-align: left;"><span
													class="lblblack">Full Name</span></td>
												<td style="width: 30%; text-align: left;"><span
													id="Content1_lblname">${ComplainantName}</span></td>
												<td style="width: 20%; text-align: left;"><span
													class="lblblack">Address</span></td>
												<td style="width: 30%; text-align: left;"><span
													id="Content1_lblAddress" style="word-break: break-all;">${GrievanceArea}</span>
												</td>
											</tr>
											<tr>
												<td style="width: 20%; text-align: left;"><span
													class="lblblack">Email Id</span></td>
												<td style="width: 30%; text-align: left;"><span
													id="Content1_lblemailid">NA</span></td>
												<td style="width: 20%; text-align: left;"><span
													class="lblblack">Owner Department</span></td>
												<td style="width: 30%; text-align: left;"><span
													id="Content1_lbldept">${OwnerDepartment}</span></td>
											</tr>
											<tr>
												<td style="width: 20%; text-align: left;"><span
													class="lblblack">Concern Department</span></td>
												<td style="width: 30%; text-align: left;" colspan="3">
													<span id="Content1_lblConcernDept"></span>
												</td>

											</tr>
											<tr>
												<td style="width: 20%; text-align: left;"><span
													class="lblblack">Grievance Description</span></td>
												<td colspan="3" align="left"><span
													id="Content1_lbldesc" title="">${Description}</span></td>
											</tr>
											<tr>
												<td style="width: 20%; text-align: left;"><span
													class="lblblack">Grievance Status</span></td>
												<td style="width: 30%" align="left"><span
													id="Content1_lblstatus">${ComplainStatusHindi}</span></td>
												<td style="width: 20%" align="left"></td>
												<td style="width: 30%" align="left"></td>
											</tr>
											<tr>
												<td style="width: 20%; text-align: left;"><span
													class="lblblack">Document</span></td>
												<td style="width: 30%" align="left"><span
													id="Content1_lblDocument">-</span></td>
												<td colspan="2" style="Padding-top: 7px" align="left">

												</td>
											</tr>
											<tr>
												<td colspan="4" align="center"><b><span
														id="Content1_spheadsrch" class="lblblack">Service /
															Scheme Details</span></b></td>
											</tr>
											<tr>
												<td colspan="4">
													<div id="Content1_pnlrpt">

														<table class="gridview" style="width: 100%;">
															<tbody>
																<tr>
																	<th style="width: 2%;">Sr. No.</th>
																	<th style="width: 15%;">Service / Scheme Name</th>
																	<th style="width: 18%;">Currently Available At</th>
																	<th style="width: 18%;">Remarks</th>
																	<th style="width: 18%;">Last Action Taken</th>
																	<th style="width: 9%;">Last Action Taken On</th>
																	<th style="width: 9%;">File Name</th>
																</tr>
																<tr>
																	<td style="width: 2%;">1</td>
																	<td style="width: 15%;">${Subject}</td>
																	<td style="width: 18%;">Information Technology
																		&amp; Communication (DOIT) - Technical Director -
																		DoIT&amp;C Head Office</td>
																	<td
																		title="परिवादी विभाग द्वारा की गयी कार्यवाही से असंतुष्ट है |परिवादी के अनुसार कार्यवाई नहीं किया गया है कार्यवाई करते समय परिवादी से संपर्क करे |"
																		style="width: 18%; word-break: break-all;">परिवादी
																		विभाग द्वारा की गयी कार्यवाही से असंतुष्ट है |परिवादी
																		के अनुसार कार्यवाई नहीं किया गया है कार्यवाई करते समय
																		परिवादी से संपर्क करे |</td>
																	<td style="width: 18%;" align="left">${ComplainStatus}</td>
																	<td style="width: 9%;" align="center">21-Mar-2018</td>
																	<td style="width: 9%;" align="center"></td>
																</tr>

															</tbody>
														</table>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>


								<!-- //Mobile -->


								<div id="Content1_up" style="padding: 30px; display: none">

									<table cellpadding="0" cellspacing="0" width="100%"
										style="margin-bottom: 20px;">
										<tr>
											<td align="center">
												<table style="width: 100%; margin-bottom: 15px;">
													<tr>
														<td align="left" colspan="2"><br /></td>
													</tr>
													<tr>

														<td align="left" colspan="2">
															<div id="Content1_pnlgrv"
																style="width: 100%; height: 281px; overflow: auto;">

																<table class="gridview" cellspacing="0" cellpadding="0"
																	style="width: 100%; border-collapse: collapse;">

																	<tr>
																		<th style="width: 5%;">Sr No.</th>
																		<th style="width: 15%;">Grievance Id</th>
																		<th style="width: 10%;">Mobile No</th>
																		<th style="width: 5%;">View</th>
																		<th style="width: 10%;">Feedback</th>
																		<th style="width: 15%;">Suggestion</th>
																		<th style="width: 18%;">Send Reminder</th>
																		<th style="width: 20%;">Upload Document</th>
																	</tr>
																	<c:forEach varStatus="i" items="${requestScope.list}"
																		var="user">
																		<tr style="height: 40px;">
																			<td>${i.index+1}</td>
																			<td align="center">${user.grievanceId}</td>
																			<td align="center">${user.mobileNo}</td>
																			<td align="center" valign="middle"
																				style="Padding-top: 7px;"><input type="hidden"
																				value="${user.grievanceId}" id="viewData"> <submit
																					class="btn btn-flat btn-primary viewUser"
																					target="_blank">View</submit></td>

																			<td align="center" style="Padding-top: 7px;"><submit
																					class="btn btn-flat btn-primary" target="_blank">-</submit></td>
																			<td align="center" style="Padding-top: 7px;"><submit
																				class="btn btn-flat btn-primary"
																				
																				target="_blank">-</submit></td>
																			<td align="center" style="Padding-top: 7px;"><span>-</span></td>
																			<td align="center" style="Padding-top: 7px;"><span>-</span></td>
																		</tr>
																	</c:forEach>

																</table>
															</div>
														</td>
													</tr>
													<tr>
														<td align="left" colspan="2"><input type="hidden"
															name="ctl00$Content1$hidLang" id="Content1_hidLang" /></td>
													</tr>
												</table>

											</td>
										</tr>
									</table>
								</div>
							</div>
							<div class="button_div">
								<a href="javascript:void(0);" onclick="getSP1('ViewServiceStatus')" class="panel">
									<img src="img/new/back.png" alt="">
								</a>
							</div>
						</div>
					</div>
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

				<span id="updateMessage1" style="display: none"></span>
				<div class="modal-footer" style="text-align: center">
					<button type="button" class="btn btn-default getone" id="submitOtp">Submit
						OTP</button>
					<button type="button" class="btn btn-default getone"
						data-dismiss="modal">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal Dialogue for Error message -->
	<div class="modal fade" id="ValidationModel" role="dialog"
		data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header"></div>
				<div class="modal-body">Field must be filled out</div>
				<div class="modal-footer" style="text-align: center">

					<button type="button" class="btn btn-default getone"
						data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>

	<form id="grivence2">

		<input type="hidden" name="grv_id" value="" id="grv_id">

	</form>
	<div id="loading" style="display: none;">
		<img id="loading-image" src="img/ajax-loader.gif" alt="Loading..." />
	</div>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/jquery.scrollTo.js"></script>
	<script type="text/javascript" src="js/app-inner.js"></script>
	
	<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
	
	<script type="text/javascript">
		/*   function myFunction(grivence_id) { */
		$('.viewUser').on('click', function() {
			$('#loading').show();
		
			var grivence_id = document.getElementById('viewData').value;
			document.getElementById('gid').value = grivence_id;
			$("#Grievance_details").attr("action", "GrievanceStatusDetails");
			$("#Grievance_details").attr("method", "GET");
			$("#Grievance_details").submit();
			document.getElementById('Content1_up').style = "display :block;";
		});

		$(function() {
			$("#mobile").hide();
			$("#Grievance").show();

		});

		var VarifyDetailMSG = "${VarifyDetailMSG}";
		var statusGri = "${statusGrivence}";
		var statusMo = "${statusMobile}";
		if (statusGri == "grvYes") {
			document.getElementById('Content1_up').style = "display :none;";
			document.getElementById('Content1_divmain').style = "display :block;";
		}
		if (statusMo == "mobileYes") {
			document.getElementById('Content1_up').style = "display :block;";
			document.getElementById('Content1_divmain').style = "display :none;";
		}
		if (VarifyDetailMSG == "empty") {
			setTimeout(function() {
		
			document.getElementById('updateMessage').style = "display:block; color:red; font-size:15px;";
			document.getElementById('updateMessage').innerHTML = "Data Not Found!Please input valid Details";
			}, 3000);
		}
		var otpMobile = "${OTP}";
		var mobileNo = "${mobileNo}";
		if (otpMobile != null && otpMobile != undefined && otpMobile != "") {
			$("#modalForOtpUid").modal();
		} else {

		}

		$("#submitOtp")
				.click(
						function() {
							var mobileOtp = document
									.getElementById('aadharOtp').value;
							if (mobileOtp === otpMobile) {
								document.getElementById("MobileNo1")
										.setAttribute("value", mobileNo);

								document.getElementById("MobileNo1").readOnly = true;
								$('#modalForOtpUid').modal('hide');
								document.getElementById("notchecked").click();
								document.getElementById("checkForm").disabled = false;
								document.getElementById('Content1_btnSendOTP').style = "display :none;";
								document.getElementById('Content1_imgOTPVeri').src = "img/new/verified.png";
								document.getElementById('ResetButton').style = "display :none;";
								document.getElementById('Content1_imgOTPVeri').style = "width:auto";

							} else {
								document.getElementById("checkForm").disabled = true;
								setTimeout(function() {
								document.getElementById('updateMessage1').style = "display:block; color:red; font-size:15px;  margin-left: 25px;";
								document.getElementById('updateMessage1').innerHTML = "OTP Authentication failed";
							}, 3000);
							}
						});
		$("#checkForm")
				.click(
						function() {
							
							var gid = $("#gid").val();
							$("#updateMessage").html('');
							  var isChecked = jQuery("input[name=Grievance]:checked").val();
							if(isChecked=="red"){
							
							if ($("#gid").val().trim() == '') {
							
								document.getElementById('updateMessage').style = "display:block; color:red; font-size:15px;";
								$("#updateMessage")
										.html(
												'<p style="">Please Enter Your Grievance ID</p>');
								$("#gid").focus();
								return false;
								
							
						
							}

							else {
								$('#loading').show();
								$("#Grievance_details").attr("action",
										"GrievanceStatusDetails");
								$("#Grievance_details").attr("method", "GET");
								$("#Grievance_details").submit();
							}
							}
							else{
								$('#loading').show();
								$("#Grievance_details").attr("action",
										"GrievanceStatusDetails");
								$("#Grievance_details").attr("method", "GET");
								$("#Grievance_details").submit();
							}
							
						});
		$('#checked').click(function() {
			$("#mobile").hide();
			$("#Grievance").show();
			document.getElementById("checkForm").disabled = false;

		})
		$('#notchecked').click(function() {
			$("#Grievance").hide();
			$("#mobile").show();
			document.getElementById('checkForm').disabled = true;

		})
		$("#Content1_btnSendOTP")
				.on(
						'click',
						function() {
							$("#updateMessage").html('');
							if ($("#MobileNo1").val().trim() == '') {
							
								document.getElementById('updateMessage').style = "display:block; color:red; font-size:15px;";
								$("#updateMessage")
										.html(
												'<p style="">* Please Enter Grivence Mobile No</p>');
								$("#MobileNo1").focus();
								return false;
							
							}

							else {
								$('#loading').show();
							
								$("#Grievance_details").attr("action",
										"getOtpForMobileVerify1");
								$("#Grievance_details").attr("method", "GET");
								$("#Grievance_details").submit();

							}

						});
		
		 function isNumberKey(evt, obj) {

             var charCode = (evt.which) ? evt.which : event.keyCode
             var value = obj.value;
             var dotcontains = value.indexOf(".") != -1;
             if (dotcontains)
                 if (charCode == 46)
                     return false;
             if (charCode == 46)
                 return true;
             if (charCode > 31 && (charCode < 48 || charCode > 57))
                 return false;
             return true;
         }
	
		 function getSP1(pageName) {
	    	$("#serviceProviderPage").val(pageName);
			document.getElementById("spd1").action = "utilityBills";
			document.getElementById("spd1").method = "post";
			$("#spd1").submit();
		}
		
		
	</script>

</body>

</html>
