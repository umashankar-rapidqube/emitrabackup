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
			<title>GOVERNMENT SERVICES</title>
		</c:if>
		<c:if test="${bill.langCode == 1}">
			<title>सरकारी सेवाएं</title>
		</c:if>
		<link href="css/font-awesome.css" type="text/css" rel="stylesheet" />
		<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
		<link rel="stylesheet" href="css/bootstrap-material-design.min.css" />
		<link rel="icon" href="img/favicon.ico" type="image/x-icon" />
		<link href="css/my-style.css" type="text/css" rel="stylesheet" />
		<style>
		.main_row img {
			height: 80px;
			width: 80px;
		}
		</style>
	</head>

	<body style="background: url(img/inner-bg.png); background-size: cover;">
		<div id="wrapper">
			<div id="mask">
				<div id="fill" class="item gr gs">
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
										<a href="index" class="panel pull-right homebtn">
											<c:if test="${bill.langCode == 0}">
												<img src="img/new/home.png" alt="" />
											</c:if>
											<c:if test="${bill.langCode == 1}">
												<img src="img/new/homehindi.png" alt="" />
											</c:if>
										</a>
									</div>
								</div>
							</div>
						</div>
						<form id="spd">
							<input type="hidden" name="langCode" id="langCode" value="${bill.langCode}">
							<input type="hidden" name="serviceProviderPage" id="serviceProviderPage" />
						</form>
	
						<div class="fourservices" style="margin-top: 20px;">
							<div class="innermid">
								<div class="container main_row" style="min-height: 790px;">
									<div class="row circle-container">
										<div class="billmidmain">
											<c:if test="${bill.langCode == 0}">
												<h1>INFORMATION PANCHAYAT SAMITI SERVICES</h1>
											</c:if>
											<c:if test="${bill.langCode == 1}">
												<h1>सूचना पंचायत समिति सेवाएं</h1>
											</c:if>
											<div class="row circle-container">
												<div class="col-md-4">
													<div class="oneservices lasrservices"></div>
												</div>
												<div class="col-md-4">
													<div class="oneservices lasrservices">
														<a href="javascript:void(0);" onclick="getKioskDetails('KioskDetaiInfo')">
															<img src="img/info_img/eMitra.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<span>E-Mitra Kiosks Information</span>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<span>ई-मित्र कियोस्‍कों की जानकारी</span>
															</c:if>
														</a>
													</div>
												</div>
												<div class="col-md-4">
													<div class="oneservices lasrservices"></div>
												</div>
											</div>
											<div class="row circle-container">
												<div class="col-md-4">
													<div class="oneservices">
														<a href="#" onclick="UnderConstraction()" class="panel">
															<img src="img/info_img/ration_ shops.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<span>Fair Price Shops Information (RATION Shops)</span>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<span>उचित मूल्‍य की दुकानों की जानकारी (राशन की दुकानें)</span>
															</c:if>
														</a>
													</div>
												</div>
												<div class="col-md-4">
													<div class="oneservices lasrservices">
														<a href="#" onclick="UnderConstraction()">
															<img src="img/info_img/Bhamasa_card.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<span>Bhamashah Card Holder Information</span>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<span>भामाशाह कार्ड धारकों की जानकारी</span>
															</c:if>
														</a>
													</div>
												</div>
												<div class="col-md-4">
													<div class="oneservices lasrservices">
														<a href="#" onclick="UnderConstraction()"> 
															<img src="img/info_img/Employee.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<span>Government Officers / Employees Information</span>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<span>सरकारी अधिकारियों / कर्मचारियों की जानकारी</span>
															</c:if>
														</a>
													</div>
												</div>
											</div>
											<div class="row circle-container">
												<div class="col-md-4">
													<div class="oneservices lasrservices">
														<a href="#" onclick="UnderConstraction()">
															<img src="img/info_img/Disable.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<span>Disable persons Information</span>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<span>विशेष योग्‍यजनों की जानकारी</span>
															</c:if>
														</a>
													</div>
												</div>
												<div class="col-md-4">
													<div class="oneservices">
														<a href="#" onclick="UnderConstraction()" class="panel">
															<img src="img/info_img/NAREGA.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<span>NAREGA Workers Information</span>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<span>नरेगा मजदूरों की जानकारी</span>
															</c:if>
														</a>
													</div>
												</div>
												<div class="col-md-4">
													<div class="oneservices lasrservices">
														<a href="javascript:void(0);" onclick="getSP('RationCardService' , 'utilityBills')">
															<img src="img/info_img/ration_card.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<span>Ration Card Holders Information</span>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<span>राशन कार्ड धारकों की जानकारी</span>
															</c:if>
														</a>
													</div>
												</div>
											</div>
											<div class="row circle-container">
												<div class="col-md-4">
													<div class="oneservices lasrservices">
														<a href="#" onclick="UnderConstraction()"> 
															<img src="img/info_img/Social_Security.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<span>Social Security pension Beneficiary Information</span>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<span>सामाजिक सुरक्षा पेंशन लाभार्थी जानकारी</span>
															</c:if>
														</a>
													</div>
												</div>
												<div class="col-md-4">
													<div class="oneservices">
														<a href="#" onclick="UnderConstraction()" class="panel">
															<img src="img/info_img/Swasthya_bima.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<span>Bhamashah Swasthya Bima beneficiaries information</span>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<span>भामाशाह स्‍वास्‍थ बीमा योजना के लाभार्थीयों की जानकारी</span>
															</c:if>
														</a>
													</div>
												</div>
												<div class="col-md-4">
													<div class="oneservices lasrservices">
														<a href="#" onclick="UnderConstraction()">
															<img src="img/info_img/Food_Security.png" alt="" />
															<c:if test="${bill.langCode == 0}">
																<span>National Food Security Act (NFSA) beneficiaries Information</span>
															</c:if>
															<c:if test="${bill.langCode == 1}">
																<span>खाद्य सुरक्षा योजना (NFSA) के लाभार्थीयों की जानकारी</span>
															</c:if>
														</a>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="button_div">
									<a href="javascript:void(0);" onclick="getSP('Service' , 'service')" class="panel">
										<c:if test="${bill.langCode == 0}">
											<img src="img/new/back.png" alt="" />
										</c:if>
										<c:if test="${bill.langCode == 1}">
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
	
		<div class="modal fade" id="showModel" role="dialog"
			data-backdrop="static" data-keyboard="false">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body " style="text-align: center;">
						<c:if test="${bill.langCode == 0}">
							<h6>Information will be provided shortly. Please cooperate with us.</h6>
						</c:if>
						<c:if test="${bill.langCode == 1}">
							<h6>जानकारी जल्द ही प्रदान की जाएगी। कृपया सहयोग करें।</h6>
						</c:if>
					</div>
					<div class="modal-footer" style="text-align: center">
						<button type="button" class="btn btn-default getone"
							data-dismiss="modal">
							<c:if test="${bill.langCode == 0}">OK</c:if>
							<c:if test="${bill.langCode == 1}">ठीक</c:if>
						</button>
					</div>
				</div>
			</div>
		</div>
		<div id="loading" style="display: none;">
			<img id="loading-image" src="img/ajax-loader.gif" alt="loading..." />
		</div>
	
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/jquery.scrollTo.js"></script>
		<script type="text/javascript" src="js/app-inner.js"></script>
		<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
	
		<script type="text/javascript">
			function getKioskDetails(pageName) {
				$('#loading').show();
				$("#serviceProviderPage").val(pageName);
				document.getElementById('spd').action = "kioskDetaiInfo";
				document.getElementById('spd').submit();
			}
			
			function getSP(pageName , actionName) {
				$('#loading').show();
				$("#serviceProviderPage").val(pageName);
				document.getElementById("spd").action = actionName;
				document.getElementById("spd").method = "post";
				document.getElementById('spd').submit();
			}
			
			function UnderConstraction() {
				$('#showModel').modal('show');
			}
		</script>
	</body>
</html>