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
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Kiosk - Bill</title>
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans|Nunito+Sans:600|700"
	rel="stylesheet">
<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap-material-design.min.css">
<link rel="icon" href="img/favicon.ico" type="image/x-icon">
<link href="css/my-style.css" type="text/css" rel="stylesheet" />

</head>

<body style="background: url(img/inner-bg.png); background-size: cover;">

	<div id="wrapper">
		<div id="mask">
			<div id="payment" class="item">
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
										<h1>Choose Payment Method</h1>
										<div class="feildone">
											<div class="col-md-4 paymentone">
												<input type="hidden" value="${amount}" id="amt"> <input
													type="hidden" value="${Id}" id="trnsid"> <input
													type="hidden" value="${acutalAmt}" id="actualamt11">
												<!-- <input type="button" id="cash" value="cashAcceptor"> -->

												<!-- <a onclick="csd()"> <img src="img/cash.png" />
													<h2>Cash</h2>
												</a> -->
												<c:choose>
													<c:when test="${serviceProviderID == 2575 || serviceProviderID == 2878}">
														<a data-toggle="modal" href="#msgBSNLPayment"> <img
															src="img/cash.png" />
															<h2>Cash</h2>
														</a>
														<div class="modal fade" id="msgBSNLPayment" role="dialog"
															data-backdrop="static" data-keyboard="false">
															<div class="modal-dialog">
																<div class="modal-content">
																	<div class="modal-body">
																		<p>
																			<strong>Cash Mode Not Available In This	Service.</strong>
																		</p>

																	</div>
																	<div class="modal-footer" style="text-align: center">
																		<button type="button" class="btn btn-default getone"
																			data-dismiss="modal">OK</button>
																	</div>
																</div>
															</div>
														</div>

													</c:when>
													<c:otherwise>
														<a onclick="csd()"> <img src="img/cash.png" />
															<h2>Cash</h2>
														</a>
													</c:otherwise>
												</c:choose>
											</div>

											<div class="col-md-4 paymentone">
												<a href="#" onclick="biomatric()" id="fingurePaymewnt">
													<img src="img/thumb.png" />

													<h2>Biometric</h2>
												</a>
											</div>


											<div class="col-md-4 paymentone">
												<a onclick="debit()"> <img src="img/credit.png" />

													<h2>Credit/Debit Card</h2>
												</a>
											</div>
										</div>
									</div>
								</div>

								<div class="modal fade" id="showBillInfoModel" role="dialog"
									data-backdrop="static" data-keyboard="false">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-body">
												<div class="row">
													<div class="col-md-12  text-center">
														<strong> Bill Amount : </strong> <span id="Aamt"> </span>
													</div>

												</div>
												<div class="row">
													<div class="col-md-12 text-center">
														<strong>Amount to be deposited in cash : </strong> <span
															id="Pamt"> </span>
													</div>
												</div>

												<br> <br>
												<p>
													<strong>Note: The difference amount between the
														bill and the amount to be deposited will be adjusted in
														next Payment. </strong>
												</p>
												<p>
													<strong>Do you wish to continue ?</strong>
												</p>

											</div>
											<div class="modal-footer" style="text-align: center">
												<button type="submit" class="btn btn-default getone"
													id="cash" onclick="callBillInfoModel();">Ok</button>
												<a href="index" class="panel">
													<button type="button" class="btn btn-default getone"
														id="cancel">Cancel</button>
												</a>
											</div>

										</div>
									</div>

								</div>
								<div class="modal fade" id="showCardModel" role="dialog"
									data-backdrop="static" data-keyboard="false">
									<div class="modal-dialog">
										<div class="modal-content"
											style="MARGIN-LEFT: -200PX; WIDTH: 1040PX; MARGIN-TOP: 140PX;">
											<div class="modal-body">
												<p class="text-center" style="font-size: 25px">
													<strong>Do you want to make payment through Card ?
													</strong>
												</p>

												<div class="col-md-12  text-center"
													style="padding-bottom: 20px;">

													<h2></h2>

													<table style="border-collapse: collapse; border: none"
														cellspacing="0" cellpadding="0" border="1">
														<tbody>
															<tr style="height: 15.0pt">
																<td rowspan="2"
																	style="border: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 15.0pt"
																	nowrap="nowrap"></td>
																<td colspan="2"
																	style="border: solid windowtext 1.0pt; border-left: none; padding: 0in 5.4pt 0in 5.4pt; height: 15.0pt"
																	nowrap="nowrap">
																	<p class="MsoNormal" style="text-align: center"
																		align="center">
																		<b><span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">Debit
																				Card</span></b><u></u><u></u>
																	</p>
																</td>
																<td rowspan="2"
																	style="border: solid windowtext 1.0pt; border-left: none; padding: 0in 5.4pt 0in 5.4pt; height: 15.0pt"
																	nowrap="nowrap">
																	<p class="MsoNormal" style="text-align: center"
																		align="center">
																		<b><span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">Credit
																				Card</span></b><u></u><u></u>
																	</p>
																</td>
																<td rowspan="2"
																	style="border: solid windowtext 1.0pt; border-left: none; padding: 0in 5.4pt 0in 5.4pt; height: 15.0pt"
																	nowrap="nowrap">
																	<p class="MsoNormal" style="text-align: center"
																		align="center">
																		<b><span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">Net
																				Banking</span></b><u></u><u></u>
																	</p>
																</td>
																<td rowspan="2"
																	style="border: solid windowtext 1.0pt; border-left: none; padding: 0in 5.4pt 0in 5.4pt; height: 15.0pt"
																	nowrap="nowrap">
																	<p class="MsoNormal" style="text-align: center"
																		align="center">
																		<b><span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">Wallets</span></b><u></u><u></u>
																	</p>
																</td>
															</tr>
															<tr style="height: 15.0pt">
																<td
																	style="border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 15.0pt"
																	nowrap="nowrap">
																	<p class="MsoNormal">
																		<b><span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">&lt;=
																				2000</span></b><u></u><u></u>
																	</p>
																</td>
																<td
																	style="border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 15.0pt"
																	nowrap="nowrap">
																	<p class="MsoNormal" style="text-align: center"
																		align="center">
																		<b><span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">More
																				than 2000</span></b><u></u><u></u>
																	</p>
																</td>
															</tr>
															<tr style="height: 60.0pt">
																<td rowspan="2"
																	style="border: solid windowtext 1.0pt; border-top: none; padding: 0in 5.4pt 0in 5.4pt; height: 60.0pt"
																	nowrap="nowrap">
																	<p class="MsoNormal">
																		<b><span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">Bill Desk(Via Online)</span></b><u></u><u></u>
																	</p>
																</td>
																<td
																	style="border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 60.0pt">
																	<p class="MsoNormal">
																		<span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">For
																			transactions up to Rs 1000/-, 0.25% of the
																			transaction amount + applicable taxes (as per RBI
																			Guidelines)&nbsp;<u></u><u></u>
																		</span>
																	</p>

																	<p class="MsoNormal">
																		Currently it is free<u></u><u></u>
																	</p>
																</td>
																<td rowspan="2"
																	style="border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 60.0pt">
																	<p class="MsoNormal">
																		<span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">0.90%
																			of the transaction amount + applicable taxes (as per
																			RBI Guidelines)</span><u></u><u></u>
																	</p>
																</td>
																<td rowspan="2"
																	style="width: 110.0pt; border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 60.0pt"
																	width="147">
																	<p class="MsoNormal">
																		<span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">0.85%
																			of the transaction amount + Taxes (as applicable)</span><u></u><u></u>
																	</p>
																</td>
																<td rowspan="2"
																	style="border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 60.0pt">
																	<p class="MsoNormal">
																		<span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">Rs
																			3.25/- + Taxes</span><u></u><u></u>
																	</p>
																</td>
																<td rowspan="2"
																	style="border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 60.0pt">
																	<p class="MsoNormal">
																		<span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">0.50%
																			of transaction Amount + Taxes (if any)</span><u></u><u></u>
																	</p>
																</td>
															</tr>
															<tr style="height: 60.0pt">
																<td
																	style="border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 60.0pt">
																	<p class="MsoNormal">
																		<span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">For
																			transactions above Rs 1000/- and up to Rs 2000/-,
																			0.5% of the transaction amount + applicable taxes (as
																			per RBI Guidelines)</span><u></u><u></u>
																	</p>
																	<p class="MsoNormal">
																		Currently it is free<u></u><u></u>
																	</p>
																</td>
															</tr>
															<tr style="height: 45.1pt">
																<td
																	style="border: solid windowtext 1.0pt; border-top: none; padding: 0in 5.4pt 0in 5.4pt; height: 45.1pt"
																	nowrap="nowrap">
																	<p class="MsoNormal">
																		<b><span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">Ezetap(Via Swipe Debit/Credit Card)
																		</span></b><u></u><u></u>
																	</p>
																</td>
																<td
																	style="border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 45.1pt">
																	<p class="MsoNormal">
																		<span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">No
																			service fee is applicable for transactions till Rs
																			2000.</span><u></u><u></u>
																	</p>
																</td>
																<td
																	style="border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 45.1pt">
																	<p class="MsoNormal">
																		<span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">For
																			transactions more than Rs 2000, service fee of 0.90%
																			+ Taxes is applicable.</span><u></u><u></u>
																	</p>
																</td>
																<td
																	style="border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 45.1pt">
																	<p class="MsoNormal">
																		<span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">1.99% of Transaction amount + Taxes</span><u></u><u></u>
																	</p>
																</td>
																<td colspan="2"
																	style="border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 45.1pt"
																	nowrap="nowrap">
																	<p class="MsoNormal">
																		<span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">&nbsp;Not Applicable</span><u></u><u></u>
																	</p>
																</td>
															</tr>
														</tbody>
													</table>

												</div>

											</div>
											<div class="modal-footer" style="text-align: center">
												<button type="button" class="btn btn-default getone"
													onclick="yesCallPayementCard();">I Agree (Swipe)</button>
												<button type="button" class="btn btn-default getone"
													onclick="noCallPayementCard();">I Agree (Enter
													Card Details)</button>
												<button type="button" class="btn btn-default getone"
													data-dismiss="modal">Cancel</button>

											</div>
										</div>
									</div>
								</div>



								<div class="modal fade" id="showAdharPayModel" role="dialog"
									data-backdrop="static" data-keyboard="false">
									<div class="modal-dialog">
										<div class="modal-content"
											style="MARGIN-LEFT: -200PX; WIDTH: 1040PX; MARGIN-TOP: 140PX;">
											<div class="modal-body">
												<p class=" text-center" style="font-size: 25px">
													<strong>Do you want to make the payment using
														Fingerprint Scanner(AadharPay)? </strong>

												</p>

												<div class="col-md-12  text-center"
													style="padding-bottom: 20px;">

													<table style="border-collapse: collapse; border: none"
														cellspacing="0" cellpadding="0" border="1">
														<tbody>

															<tr style="height: 15.0pt">
																<td
																	style="border: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 15.0pt"
																	nowrap="nowrap">
																	<p class="MsoNormal">
																		<b></b><u></u><u></u>
																	</p>
																</td>
																<td
																	style="border: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 15.0pt"
																	nowrap="nowrap">
																	<p class="MsoNormal">
																		<b><span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">Amount
																				&lt;= 2000</span></b><u></u><u></u>
																	</p>
																</td>
																<td
																	style="border: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 15.0pt"
																	nowrap="nowrap">
																	<p class="MsoNormal" style="text-align: center"
																		align="center">
																		<b><span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">Amount
																				More than 2000</span></b><u></u><u></u>
																	</p>
																</td>
															</tr>
															<tr style="height: 60.0pt">
																<td rowspan="1"
																	style="border: solid windowtext 1.0pt; border-top: none; padding: 0in 5.4pt 0in 5.4pt; height: 60.0pt"
																	nowrap="nowrap">
																	<p class="MsoNormal">
																		<b><span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">Digit
																				Secure</span></b><u></u><u></u>
																	</p>
																</td>
																<td
																	style="border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 30.4pt 0in 59.4pt; height: 60.0pt">
																	<p class="MsoNormal">
																		<span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">
																			Convenience Fees = Rs 1 + Taxes<br><br> Total Charges =
																			Convenience Fees&nbsp;<u></u><u></u>
																		</span>
																	</p>

																	<p class="MsoNormal">
																		<u></u><u></u>
																	</p>
																</td>
																<td rowspan="1"
																	style="border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; padding: 0in 27.4pt 0in 70.4pt; height: 60.0pt">
																	<p class="MsoNormal">
																		<span
																			style="font-size: 12.0pt; font-family: &amp; amp; amp; quot; Trebuchet MS&amp;amp; amp; quot; , sans-serif; color: black">Convenience
																			Fees = Rs 1 + Taxes <br><br> MDR = 0.25% of transaction amount
																			+ Taxes <br><br>Total Charges = Convenience Fees + MDR</span><u></u><u></u>
																	</p>
																</td>


															</tr>

															<!-- <tr style="height: 45.1pt">
																<td
																	style="border: solid windowtext 1.0pt; border-top: none; padding: 0in 5.4pt 0in 5.4pt; height: 45.1pt"
																	nowrap="nowrap">
																	<p class="MsoNormal">
																		<b><span
																			style="font-size: 12.0pt; font-family: &amp; amp; quot; Trebuchet MS&amp;amp; quot; , sans-serif; color: black">Fing
																				Pay </span></b><u></u><u></u>
																	</p>
																</td>
																<td
																	style="border-top: none; border-left: none; border-right: none; border-bottom: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 14.4pt; height: 45.1pt">
																	<p class="MsoNormal">
																		<span
																			style="font-size: 12.0pt; font-family: &amp; amp; quot; Trebuchet MS&amp;amp; quot; , sans-serif; color: black">No
																			additional charges up to Dec 2018</span><u></u><u></u>
																	</p>
																</td>
																<td
																	style="border-bottom: solid windowtext 1.0pt; border-left: none; border-right: solid windowtext 1.0pt; padding: 0in 5.4pt 0in 5.4pt; height: 45.1pt">
																	<p class="MsoNormal">
																		<u></u><u></u>
																	</p>
																</td>


															</tr> -->
														</tbody>
													</table>

												</div>

											</div>
											<div class="modal-footer" style="text-align: center">
												<button type="button" class="btn btn-default getone"
													onclick="payementAdharPayWithFingPay(3);">I Agree</button>

												<!-- 	<button type="button" class="btn btn-default getone"
													onclick="payementAdharPayWithFingPay(4);">Fingpay</button>  -->
												<button type="button" class="btn btn-default getone"
													data-dismiss="modal">Cancel</button>

											</div>
										</div>
									</div>
								</div>

								<div class="modal fade" id="noteslist" role="dialog"
									data-backdrop="static" data-keyboard="false">
									<div class="modal-dialog">
										<div class="modal-content">

											<div class="modal-header">
												<h5 class="modal-title">Details Of Cash</h5>
											</div>

											<div class="modal-body">
												<div class="row">
													<div class="col-md-12 text-center">
														<h2>
															Timer : <span id="cashTimer"></span>
														</h2>
														<h4>
															Total Amount To Be Deposited : <span id="depositAmount"></span>
														</h4>
														<table class="table table-bordered table-striped">
															<thead>
																<tr>
																	<th>Amount</th>
																	<th colspan="2">Cash</th>
																	<th>Total</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>10</td>
																	<td colspan="2">* <span id="count1">0</span></td>
																	<td><span id="totalcount1">0</span></td>

																</tr>
																<tr>
																	<td>20</td>
																	<td colspan="2">* <span id="count2">0</span></td>
																	<td><span id="totalcount2">0</span></td>

																</tr>
																<tr>
																	<td>50</td>
																	<td colspan="2">* <span id="count3">0</span></td>
																	<td><span id="totalcount3">0</span></td>

																</tr>
																<tr>
																	<td>100</td>
																	<td colspan="2">* <span id="count4">0</span></td>
																	<td><span id="totalcount4">0</span></td>

																</tr>
																<tr>
																	<td>200</td>
																	<td colspan="2">* <span id="count5">0</span></td>
																	<td><span id="totalcount5">0</span></td>

																</tr>
																<tr>
																	<td>500</td>
																	<td colspan="2">* <span id="count6">0</span></td>
																	<td><span id="totalcount6">0</span></td>

																</tr>
																<tr>
																	<td>2000</td>
																	<td colspan="2">* <span id="count7">0</span></td>
																	<td><span id="totalcount7">0</span></td>

																</tr>

																<tr>

																	<td colspan="3">Total Amount</td>
																	<td><span id="totalAmount">0 </span></td>

																</tr>
															</tbody>
														</table>


													</div>
												</div>

											</div>

										</div>
									</div>
								</div>


								<div class="modal fade" id="PaymentErrorBTB" role="dialog"
									data-backdrop="static" data-keyboard="false">
									<div class="modal-dialog">
										<div class="modal-content">

											<div class="modal-header" style="text-align: center">
												<h5 class="modal-title">Payment Error Message</h5>
											</div>

											<div class="modal-body" style="text-align: center">
												<p>
													<span id="showError" style="color: red;"></span>
												</p>

											</div>
											<div class="modal-footer" style="text-align: center"></div>

										</div>
									</div>

								</div>


								<div class="button_div">
									<a href="index" class="panel"> <img src="img/new/back.png"></a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>





		<form id="payment111">
			<input type="hidden" name="transactionId" value="0" id="csd">
			<input type="hidden" name="billAmount" value="0" id="csd1"> <input
				type="hidden" value="0" name="langCode"> <input
				type="hidden" value="${serviceProviderID}" name="serviceProviderID">
		</form>

		<form id="payment112">
			<input type="hidden" name="transactionId" value="${Id}"> <input
				type="hidden" name="billAmount" value="${acutalAmt}"> <input
				type="hidden" name="name" value="${name}"> <input
				type="hidden" name="billEmail" value="${email}"> <input
				type="hidden" name="billMobileNo" value="${mobile}"> <input
				type="hidden" name="consumerKeyValue" value="${consumerKeyValue}">
			<input type="hidden" name="createdDate" value="${date}"> <input
				type="hidden" name="yesorno" value="0" id="yesorno"> <input
				type="hidden" value="${serviceProviderID}" name="serviceProviderID">
			<input type="hidden" value="0" name="langCode">
		</form>
		<input type="hidden" value="0" name="emitraTransactionId"
			id="emitraTransactionId">

	</div>
	<div id="loading" style="display: none;">
		<img id="loading-image" src="img/ajax-loader.gif" alt="loading..." />
	</div>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.scrollTo.js"></script>
	<script type="text/javascript" src="js/app.js"></script>
	<script type="text/javascript" src="js/jquery.flexisel.js"></script>
	<script type="text/javascript" src="js/jquery-ui.js"></script>
	
		
	<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
	
	
	<script type="text/javascript">
		function getNoteDetails() {
			$.ajax({
				url : '/KioskService/NoteDetails/',
				type : "POST",
				dataType : 'json',
				success : function(data) {
					//	alert(data);
					if (data == '') {
						console.log("No Data Found");
						return false;
					} else {
						var json = jQuery.parseJSON(JSON.stringify(data));

						$("#count1").html(json[0].count1);
						$("#count2").html(json[0].count2);
						$("#count3").html(json[0].count3);
						$("#count4").html(json[0].count4);
						$("#count5").html(json[0].count5);
						$("#count6").html(json[0].count6);
						$("#count7").html(json[0].count7);

						$("#totalcount1").html(json[0].totalcount1);
						$("#totalcount2").html(json[0].totalcount2);
						$("#totalcount3").html(json[0].totalcount3);
						$("#totalcount4").html(json[0].totalcount4);
						$("#totalcount5").html(json[0].totalcount5);
						$("#totalcount6").html(json[0].totalcount6);
						$("#totalcount7").html(json[0].totalcount7);
						$("#totalAmount").html(json[0].totalAmount);

						$("#cashTimer").html(json[0].cashTimer);
						$("#depositAmount").html(json[0].depositAmount);

						return true;
					}
				}

			});
		}

		$(function() {
			$("#cash")
					.bind(
							'click',
							function(e) {
								//alert("+++++++++");
								var amt = $('#amt').val();
								var trnsid = $('#trnsid').val();
								document.getElementById('csd').value = trnsid;
								document.getElementById('csd1').value = amt;
								$("#emitraTransactionId").val(0);
								//alert("Please submit your Cash Amount.");
								//alert("amt::"+amt+" trns::"+trnsid);

								// start calling function for showing note details
								// var noteIntervalVar = setInterval(function(){ getNoteDetails(); }, 500);
								// End  calling function for showing note details

								/***
								$("#emitraTransactionId").val('180084338795');
								console.log($("#emitraTransactionId").val());
									
								 $.ajax({
									url : '/KioskService/cancelTranscation/'+$("#emitraTransactionId").val()+'/',
									type : "POST",
									success : function(data) {
										console.log(data);
										alert("data "+data);
									}
								 });  */

								$('#loading').show();

								$
										.ajax({
											url : '/KioskService/backtoback/'
													+ amt + '/' + trnsid + '/'
													+ '${serviceProviderID}'
													+ '/',
											//data:{field1:amt,field2 :trnsid},
											type : "POST",
											success : function(trans) {
												var valArr = trans.split("##");
												if (valArr[0] == 'SUCCESS') {

													$('#noteslist').modal(
															'show');
													$('#loading').hide();
													// start calling function for showing note details
													var noteIntervalVar = setInterval(
															function() {
																getNoteDetails();
															}, 500);
													// End  calling function for showing note details

													$("#emitraTransactionId")
															.val(valArr[1]);
													console
															.log($(
																	"#emitraTransactionId")
																	.val());

													$
															.ajax({
																url : '/KioskService/cashAcceptor/'
																		+ amt
																		+ '/'
																		+ trnsid
																		+ '/',
																type : "POST",
																success : function(
																		data) {
																	//	alert(data);
																	//	console.log(data);
																	if (data == 'NoData') {
																		console
																				.log("No Data Found");
																		window.location.href = "paymentError";

																	} else if (data == 'cancel') {
																		clearInterval(noteIntervalVar);
																		$
																				.ajax({
																					url : '/KioskService/cancelTranscation/'
																							+ $(
																									"#emitraTransactionId")
																									.val()
																							+ '/',
																					type : "POST",
																					success : function(
																							data) {
																						console
																								.log(data);
																						window.location.href = "index";
																					}
																				});
																	} else {
																		setTimeout(
																				function() {
																					$(
																							'#loading')
																							.show();
																					document
																							.getElementById('payment111').action = "paymentSuccess";
																					document
																							.getElementById('payment111').method = "post";
																					document
																							.getElementById(
																									'payment111')
																							.submit();
																				},
																				1000);

																	}

																},
																error : function(
																		xhr,
																		status,
																		error) {
																	window.location.href = "paymentError";
																	console
																			.log("Error "
																					+ error);
																}
															});
												} else {
													var msg = valArr[1];
													document
															.getElementById('showError').innerHTML = msg;
													$('#PaymentErrorBTB')
															.modal('show');
													setTimeout(
															function() {
																$(
																		'#PaymentErrorBTB')
																		.modal(
																				'hide')
																console
																		.log("trans : "
																				+ trans);
																window.location.href = 'index';
															}, 6000);
													//console.log("trans : "+trans);
													//window.location.href="paymentError";
												}
											}
										});
							});
		});

		function csd() {
			var id = document.getElementById('amt').value;
			var id1 = document.getElementById('actualamt11').value;
			document.getElementById('Pamt').innerHTML = id;
			document.getElementById('Aamt').innerHTML = id1;
			$('#showBillInfoModel').modal('show');
		}

		function callBillInfoModel() {
			// start function for showing note value
			$('#showBillInfoModel').modal('hide');

			// end function for showing note value
			document.getElementById('cancel').disabled = true;
			document.getElementById('cash').disabled = true;
		}

		function debit() {
			$('#showCardModel').modal('show');
		}

		function yesCallPayementCard() {
			document.getElementById('yesorno').value = 1;
			document.getElementById('payment112').action = "paymentByCard";
			document.getElementById('payment112').method = "post";
			document.getElementById('payment112').submit();
		}
		function noCallPayementCard() {
			document.getElementById('yesorno').value = 2;
			document.getElementById('payment112').action = "paymentByCard";
			document.getElementById('payment112').method = "post";
			document.getElementById('payment112').submit();
		}

		function biomatric() {
			$('#showAdharPayModel').modal('show');
		}

		function payementAdharPayWithFingPay(flag) {
			var action = "";
			if (flag == 3) {
				action = "paymentByBiomatric";
			} else if (flag == 4) {
				action = "figpayservice";
			}

			if (action != '') {
				document.getElementById('yesorno').value = flag;
				document.getElementById('payment112').action = action;
				document.getElementById('payment112').method = "post";
				document.getElementById('payment112').submit();
			}

		}
	</script>
</body>
</html>