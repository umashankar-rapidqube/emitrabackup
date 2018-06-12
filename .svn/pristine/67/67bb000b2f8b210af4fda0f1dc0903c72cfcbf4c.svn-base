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
		<title>GOVERNMENT SERVICES</title>
		<link
			href="https://fonts.googleapis.com/css?family=Noto+Sans|Nunito+Sans:600|700"
			rel="stylesheet" />
		<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
		<link rel="stylesheet" href="css/bootstrap-material-design.min.css" />
		<link rel="icon" href="img/favicon.ico" type="image/x-icon" />
		<link href="css/my-style.css" type="text/css" rel="stylesheet" />
		
		<style>
			.unselectable {
			    -webkit-touch-callout: none;
			    -webkit-user-select: none;
			    -khtml-user-select: none;
			    -moz-user-select: none;
			    -ms-user-select: none;
			    user-select: none;
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
										<c:if test="${serviceProvider.langCode == 0}">
											<a href="index" class="panel pull-right homebtn">
												<img src="img/new/home.png" alt="" /></a>
										</c:if>
										<c:if test="${serviceProvider.langCode == 1}">
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
												<h1>Status of Revenue / Subordinate Court Case</h1>
											</c:if>
											<c:if test="${serviceProvider.langCode == 1}">
												<h1>राजस्व / अधीनस्थ न्यायालय के प्रकरण की सूचना</h1>
											</c:if>
											<form id="spd1">
												<input type="hidden" name="langCode" id="langCode1" value="${serviceProvider.langCode}">
												<input type="hidden" name="serviceProviderPage"	id="serviceProviderPage" value="${serviceProvider.serviceProviderPage}"/>
											</form>
											<form id="spd">
												<input type="hidden" name="langCode" id="langCode" value="${serviceProvider.langCode}">
												<input type="hidden" name="serviceProviderPage"	id="serviceProviderPage" value="value="${serviceProvider.serviceProviderPage}"/>
												<div class="feildone" style="text-align: center" id="insertDetails">
													<br>
													<div class="col-sm-6 form-group required">
														<c:if test="${serviceProvider.langCode == 0}">
															<label class="control-label col-sm-4" style="text-align: left;">District Name</label>
														</c:if>
														<c:if test="${serviceProvider.langCode == 1}">
															<label class="control-label col-sm-4" style="text-align: left;">जिला जा नाम</label>
														</c:if>
														<div class="col-sm-8">
															<select aria-required="true" id="district" class="form-control col-sm-12 eformclass required" required="required" onchange="getCourtName();">
																<c:if test="${serviceProvider.langCode == 0}">
																	<option value="0" selected>---Select---</option>
																	<c:forEach items="${district}" var="districtObj">
																		<option value="${districtObj.valueid}" key-dist="${districtObj.valuenamehi}">${districtObj.valueeng}</option>
																    </c:forEach>
																</c:if>
																<c:if test="${serviceProvider.langCode == 1}">
																	<option value="0" selected>---चुनें---</option>
																	<c:forEach items="${district}" var="districtObj">
															            <option value="${districtObj.valueid}" key-dist="${districtObj.valuenamehi}">${districtObj.valuenamehi}</option>
																    </c:forEach>
																</c:if>
															</select>
														</div>
													</div>
													<div class="col-sm-6 form-group required">
														<c:if test="${serviceProvider.langCode == 0}">
															<label class="control-label col-sm-4" style="text-align: left;">Court Type</label>
														</c:if>
														<c:if test="${serviceProvider.langCode == 1}">
															<label class="control-label col-sm-4" style="text-align: left;">अदालत का प्रकार</label>
														</c:if>
														<div class="col-sm-8">
															<select aria-required="true" id="courtType" class="form-control col-sm-12 eformclass required" required="required" onchange="getCourtName();">
																<c:if test="${serviceProvider.langCode == 0}">
																	<option value="0" selected>---Select---</option>
																	<c:forEach items="${courtType}" var="courtTypeObj">
															            <option value="${courtTypeObj.valueid}" key-ctype="${courtTypeObj.valuenamehi}">${courtTypeObj.valueeng}</option>
																    </c:forEach>
																</c:if>
																<c:if test="${serviceProvider.langCode == 1}">
																	<option value="0" selected>---चुनें---</option>
																	<c:forEach items="${courtType}" var="courtTypeObj">
															            <option value="${courtTypeObj.valueid}" key-ctype="${courtTypeObj.valuenamehi}">${courtTypeObj.valuenamehi}</option>
																    </c:forEach>
																</c:if>
															</select>
														</div>
													</div>
													<div class="col-sm-6 form-group required">
														<c:if test="${serviceProvider.langCode == 0}">
															<label class="control-label col-sm-4" style="text-align: left;">Court Name</label>
														</c:if>
														<c:if test="${serviceProvider.langCode == 1}">
															<label class="control-label col-sm-4" style="text-align: left;">अदालत का नाम</label>
														</c:if>
														<div class="col-sm-8">
															<select aria-required="true" id="courtName" class="form-control col-sm-12 eformclass required" required="required">
																<c:if test="${serviceProvider.langCode == 0}">
																	<option value="0">---Select---</option>
																</c:if>
																<c:if test="${serviceProvider.langCode == 1}">
																	<option value="0">---चुनें---</option>
																</c:if>
															</select>
														</div>
													</div>
													<br>
													<br>
													<br>
													<br>
													<div class="col-sm-6 form-group required">
														<div class="form-group">
															<c:if test="${serviceProvider.langCode == 0}">
																<label class="control-label col-sm-4" style="text-align: left;">Case Number</label>
															</c:if>
															<c:if test="${serviceProvider.langCode == 1}">
																<label class="control-label col-sm-4" style="text-align: left;">वाद संख्या</label>
															</c:if>
															<div class="col-sm-8">
																<c:if test="${serviceProvider.langCode == 0}">
																	<input type="text" class="form-control" id="caseNumber" placeholder="Case Number" maxlength="10"
																	onkeyup="checkCaseNoRegex();" required="required">
<!-- 																	<span id="caseNoMessage" class="unselectable" style="display:block; color:grey; font-size:15px;">YYYY/NNNNN ('NNNNN' Start With '0')</span> -->
																	<span id="caseNoMessage" class="unselectable" style="display:block; color:grey; font-size:15px;"></span>
																</c:if>
																<c:if test="${serviceProvider.langCode == 1}">
																	<input type="text" class="form-control" id="caseNumber" placeholder="वाद संख्या" maxlength="10"
																	onkeyup="checkCaseNoRegex();" required="required">
<!-- 																	<span id="caseNoMessage" class="unselectable" style="display:block; color:grey; font-size:15px;">YYYY/NNNNN ('NNNNN' '0' से शुरू करें)</span> -->
																	<span id="caseNoMessage" class="unselectable" style="display:block; color:grey; font-size:15px;"></span>
																</c:if>
															</div>
														</div>       
													</div>
													<div class="col-sm-6 form-group required" id="mCaseNoDiv" style="display: none;">
														<div class="form-group">
															<c:if test="${serviceProvider.langCode == 0}">
																<label class="control-label col-sm-4" style="text-align: left;">Manual Case Number</label>
															</c:if>
															<c:if test="${serviceProvider.langCode == 1}">
																<label class="control-label col-sm-4" style="text-align: left;">मैनुअल वाद संख्या</label>
															</c:if>
															<div class="col-sm-8">
																<c:if test="${serviceProvider.langCode == 0}">
																	<input type="text" class="form-control" id="mCaseNo" placeholder="Manual Case Number" 
																	onkeyup="checkCaseNoRegex();" required="required">
																</c:if>
																<c:if test="${serviceProvider.langCode == 1}">
																	<input type="text" class="form-control" id="mCaseNo" placeholder="मैनुअल वाद संख्या"
																	onkeyup="checkCaseNoRegex();" required="required">
																</c:if>
<!-- 																<span id="mcaseNoMessage" class="unselectable" style="display:block; color:grey; font-size:15px;">YYYY/NNNN</span> -->
																<span id="mcaseNoMessage" class="unselectable" style="display:block; color:grey; font-size:15px;"></span>
															</div>
														</div>       
													</div>
													<div class="twobtn">
														<div class="twobtn">
															<button class="btn btn-default getone" id="getStatusDetails" type="button" onclick="getCaseStatus();" disabled="disabled">
																<c:if test="${serviceProvider.langCode == 0}">Get Case Status</c:if>
																<c:if test="${serviceProvider.langCode == 1}">केस स्थिति प्राप्त करें</c:if>
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
																	<c:if test="${serviceProvider.langCode == 0}">Manual Case No</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">मैनुअल वाद संख्या</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Appellant</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">प्रार्थी</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Appellant's Advocate Name</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">प्रार्थी के वकील का नाम</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Respondent</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">अप्रार्थी</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Respondent's Advocate Name</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">अप्रार्थी के वकील का नाम</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Act Name</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">अधिनियम का नाम</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Case Type</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">मामले का प्रकार</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Reason Of Hearing</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">सुनवाई का कारण</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Institution Date</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">संस्थान की तारीख</c:if>
																</th>
																<th>
																	<c:if test="${serviceProvider.langCode == 0}">Next Hearing Date</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">अगली सुनवाई दिनांक</c:if>
																</th>
																<th id="thLblfinalBench" style="display: none;">
																	<c:if test="${serviceProvider.langCode == 0}">Final Bench</c:if>
																	<c:if test="${serviceProvider.langCode == 1}">आवंटित बेंच</c:if>
																</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td>
																	<label id="lblmanualCaseNo"></label>
																</td>
																<td>
																	<label id="lblapplicantName"></label>
																</td>
																<td>
																	<label id="lblappAdv"></label>
																</td>
																<td>
																	<label id="lblrespondentName"></label>
																</td>
																<td>
																	<label id="lblrespAdv"></label>
																</td>
																<td>
																	<label id="lblactName"></label>
																</td>
																<td>
																	<label id="lblcaseType"></label>
																</td>
																<td>
																	<label id="lblcaseReason"></label>
																</td>
																<td>
																	<label id="lblcaseStartDate"></label>
																</td>
																<td>
																	<label id="lblnxtHearingDate"></label>
																</td>
																<td id="tdLblfinalBench" style="display: none;">
																	<label id="lblfinalBench">------</label>
																</td>
															</tr>
														</tbody>
														<tfoot>
															<tr>
																<td colspan="11" align="center">
																	<button id="billDetails" class="btn btn-default getone" id="checkForm" type="button" onclick="submitDetails();">
																		<c:if test="${serviceProvider.langCode == 0}">See Decision</c:if>
																		<c:if test="${serviceProvider.langCode == 1}">निर्णय देखें</c:if>
																	</button>
																	<span id="reqMobileMessage" style="display: none;"></span>
																</td>
															</tr>
														</tfoot>
													</table>
												</div>
											</form>
											<form id="saveRevenueCaseDetails">
												<input type="hidden" name="langCode" id="langCode" value="${serviceProvider.langCode}">
												<input type="hidden" name="districtName" id="districtName" value="">
												<input type="hidden" name="cName" id="cName" value="NA">
												<input type="hidden" name="cType" id="cType" value="">
												<input type="hidden" name="manualCaseNo" id="manualCaseNo" value="" />
												<input type="hidden" name="applicantName" id="applicantName" value="" />
												<input type="hidden" name="appAdv" id="appAdv" value="">
												<input type="hidden" name="respondentName" id="respondentName" value="">
												<input type="hidden" name="respAdv" id="respAdv" value="">
												<input type="hidden" name="actName" id="actName" value="">
												<input type="hidden" name="caseType" id="caseType" value="">
												<input type="hidden" name="caseReason" id="caseReason" value="">
												<input type="hidden" name="caseStartDate" id="caseStartDate" value="">
												<input type="hidden" name="nxtHearingDate" id="nxtHearingDate" value="">
												<input type="hidden" name="finalBench" id="finalBench" value="">
												<input type="hidden" name="secCode" id="secCode" value="">
												<input type="hidden" name="caseNo" id="caseNo" value="">
												<input type="hidden" name="pdfUrl" id="pdfUrl" value="">
												<input type="hidden" name="origPdfUrl" id="origPdfUrl" value="">
											</form>
										</div>
									</div>
								</div>
								<div class="button_div">
									<c:if test="${serviceProvider.langCode == 0}">
										<a href="javascript:void(0);" onclick="getSP1('ViewServiceStatus')" class="panel">
											<img src="img/new/back.png"	alt="" />
										</a>
									</c:if>
									<c:if test="${serviceProvider.langCode == 1}">
										<a href="javascript:void(0);" onclick="getSP1('ViewServiceStatus')" class="panel">
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
	
		<div id="loading" style="display: none;">
			<img id="loading-image" src="img/ajax-loader.gif" alt="Loading..." />
		</div>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/jquery.scrollTo.js"></script>
		<script type="text/javascript" src="js/app-inner.js"></script>
		<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
		<script type="text/javascript" src="js/KioskServices/RevenueService.js"></script>
		
		<script>
			function getSP1(pageName) {
		    	$("#serviceProviderPage").val(pageName);
				document.getElementById("spd1").action = "utilityBills";
				document.getElementById("spd1").method = "post";
				$("#spd1").submit();
			}
		</script>
	</body>
</html>