<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<title>GOVERNMENT SERVICES</title>
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
									<a href="index" class="panel pull-right homebtn"> <img
										src="img/new/home.png" alt="" /></a>
								</div>
							</div>
						</div>
					</div>
					<form method="post" id="spd">
						<input type="hidden" name="serviceID" id="serviceID" />
					</form>

					<form method="post" id="spdIN"></form>

					<div class="fourservices" style="margin-top: 20px;">
						<div class="innermid">
							<div class="container main_row" style="min-height: 790px;">
								<div class="row circle-container">
									<div class="billmidmain">
										<h1 class="">INFORMATION PANCHAYAT SAMITI SERVICES</h1>
										<!-- 	<div class="col-md-3 deg30">
											<div class="grevanceservices">
												<a href="onlineverification" class="panel"> <img
													src="img/online-verification.png" alt="" /> <span>Online
														Verification</span>
												</a>
											</div>
										</div>
										
										-->
										<div class="row circle-container">
											<div class="col-md-4">
												<div class="oneservices lasrservices"></div>
											</div>
											<div class="col-md-4">
												<div class="oneservices lasrservices">
													<a href="#" onclick="KioskDetaiInfo()"> <img
														src="img/info_img/eMitra.png" alt="" /> <span>eMitra
															Kiosk Information</span>
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
													<a href="#" onclick="UnderConstraction()" class="panel"> <img
														src="img/info_img/ration_ shops.png" alt="" /> <span>Fair
															Price Shops Information (RATION Shops)</span>

													</a>
												</div>
											</div>



											<div class="col-md-4">
												<div class="oneservices lasrservices">
													<a href="#" onclick="UnderConstraction()"> <img
														src="img/info_img/Bhamasa_card.png" alt="" /> <span>Bhamashah
															Card Holder Information</span>
													</a>
												</div>
											</div>
											<div class="col-md-4">
												<div class="oneservices lasrservices">
													<a href="#" onclick="UnderConstraction()"> <img
														src="img/info_img/Employee.png" alt="" /> <span>Government
															Officers / Employees Information</span>
													</a>
												</div>
											</div>
										</div>






										<div class="row circle-container">

											<div class="col-md-4">
												<div class="oneservices lasrservices">
													<a href="#" onclick="UnderConstraction()"> <img
														src="img/info_img/Disable.png" alt="" /> <span>Disable
															persons Information</span>
													</a>
												</div>
											</div>
											<div class="col-md-4">
												<div class="oneservices">
													<a href="#" onclick="UnderConstraction()" class="panel"> <img
														src="img/info_img/NAREGA.png" alt="" /> <span>NAREGA
															Workers Information</span>

													</a>
												</div>
											</div>


											<div class="col-md-4">
												<div class="oneservices lasrservices">
													<a href="rationcardservice"> <img
														src="img/info_img/ration_card.png" alt="" /> <span>Ration
															Card Holders Information</span>
													</a>
												</div>
											</div>






										</div>
										<div class="row circle-container">



											<div class="col-md-4">
												<div class="oneservices lasrservices">
													<a href="#" onclick="UnderConstraction()"> <img
														src="img/info_img/Social_Security.png" alt="" /> <span>Social
															Security pension Beneficiary Information</span>
													</a>
												</div>
											</div>
											<div class="col-md-4">
												<div class="oneservices">
													<a href="#" onclick="UnderConstraction()" class="panel"> <img
														src="img/info_img/Swasthya_bima.png" alt="" /> <span>Bhamashah
															Swasthya Bima beneficiaries information</span>

													</a>
												</div>
											</div>


											<div class="col-md-4">
												<div class="oneservices lasrservices">
													<a href="#" onclick="UnderConstraction()"> <img
														src="img/info_img/Food_Security.png" alt="" /> <span>National
															Food Security Act (NFSA) beneficiaries Information</span>
													</a>
												</div>
											</div>






										</div>

										<!-- <div class="col-md-3 deg30">
											<div class="grevanceservices">
												<a href="javascript:void(0);" onclick="getSP('2289')" class="panel"> <img
													src="img/print-certificate.png" alt="" /> <span>Fair Price Shops Information (RATION Shops)</span>
												</a>
											</div>
										</div>
										<div class="clearfix"></div> 
										

										<div class="col-md-3 deg0">
											<div class="grevanceservices">
												<a href="transactionservice1" class="panel"> <img
													src="img/Get-status.png" alt="" /> <span>Ration Card Holders Information</span>
												</a>
											</div>
										</div>
										<div class="col-md-3 deg60">
											<div class="grevanceservices">
												<a href="examservice" class="panel">
												 <img src="img/sexam.png" alt="" /> <span>
eMitra Kiosk Information</span>
												</a>
											</div>
										</div>
										<div class="col-md-3 deg90">
											<div class="grevanceservices">
												<a href="jamabandiRecord" class="panel">  
												<img src="img/ror.png" alt="" /> 												
												<span>Social Security pension Beneficiary Information</span>
												</a>
											</div>
										</div>
										
										<div class="col-md-3 deg180" style="    margin: -204px 0px 0px 801px;">
										<div class="grevanceservices">
											<a href="#" onclick="getSPIN()" class="panel">
											 <img src="img/bhamashah_logo.png" alt="" /> <span>NAREGA Workers Information</span>
											</a>
										</div>
									</div>
									
									
										
									</div>
									

									   <div class="col-md-3 deg180">
                                                <div class="grevanceservices">
                                                <a href="agricultureDeptHindi" class="panel">
                                                    <img src="img/Application-Service.png" alt="" />

                                                    <span>Application Services</span>
                                                </a>
                                                    </div>
                                            </div>
								</div>
								 -->

									</div>

 
									<form id="payment112">
										<input type="hidden" value="0" name="langCode">
									</form>
								</div>

							</div>
							<div class="button_div">
								<a href="service" class="panel"> <img src="img/new/back.png"
									alt=""></a>

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

							<h6>Information will be provided shortly. Please cooperate with us.</h6>
						</div>
						<div class="modal-footer" style="text-align: center">
							<button type="button" class="btn btn-default getone" data-dismiss="modal">OK</button>


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

			<script type="text/javascript"
				src="js/KioskServices/BackButtonDisable.js"></script>


			<script type="text/javascript">
			
				function KioskDetaiInfo() {
					$('#loading').show();
					document.getElementById('payment112').action = "KioskDetaiInfo";

					document.getElementById('payment112').submit();
				}
				function UnderConstraction() {
					$('#showModel').modal('show');
				}
			</script>
</body>

</html>
