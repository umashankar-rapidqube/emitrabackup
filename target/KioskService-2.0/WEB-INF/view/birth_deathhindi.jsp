<%@ page language="java" contentType="text/html; charset=utf-8"
	 pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>प्रमाणपत्र   सेवाएं</title>
     <link href="css/font-awesome.css" type="text/css" rel="stylesheet" />
    <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" href="css/bootstrap-material-design.min.css" />
    <link rel="icon" href="img/favicon.ico" type="image/x-icon" />
    <link href="css/my-style.css" type="text/css" rel="stylesheet" />
</head>


<body style="background:url(img/inner-bg.png); background-size:cover;">

 <div id="wrapper">
        <div id="mask">
  				<div id="fill" class="item allcir">
                <div class="content">
                    <!--<a href="#item1" class="panel">back</a>-->
                    <div class="mainone innermaintop">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="logomain">
                                        <div class="cartoon innercartoon">
                                            <img src="img/cartooon-bg-inner.png" alt="" />
                                            <img src="img/bubble.png" class="bubble" alt="" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="logomain midinnerlogomain">
                                        <div class="logo midinnerlogo">
                                            <a href="#">
                                                <img src="img/logo-inner.png" alt="" /></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <a href="index" class="panel pull-right homebtn">
                                        <img src="img/new/homehindi.png" alt="" /></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <form id="SubSerId">
						<input type="hidden" name="serviceID" id="serviceID" /> <input
							type="hidden" name="subServiceID" id="subServiceID" /> <input
							type="hidden" name="serviceName" id="serviceName" />
							<input type="hidden" name="langCode" id="langCode" value="1" />
					</form>
					<form id="spd1">
						<input type="hidden" name="langCode" id="langCode" value="1" />
						<input type="hidden" name="serviceProviderPage" id="serviceProviderPage" />
					</form>
					<input type="hidden" value="${serviceID}" id="srvID" />
                    
                    <div class="fourservices">
                        <div class="innermid">
							<div class="container main_row">
								<div class="row circle-container">
									<div class="billmidmain">
										<h1 class="">प्रमाणपत्र   सेवाएं</h1>

									
										<div class="col-md-3 deg0">
											<div class="grevanceservices">
												<a href="javascript:void(0);"
													onclick="setSSId('200','Caste Certificate')"
													class="panel"> <img src="img/caste.png" alt="" /> 
													<span>जाति  प्रमाणपत्र </span>
												</a>
											</div>
										</div>
										
										
										<div class="col-md-3 deg20">
											<div class="grevanceservices">
												<a href="javascript:void(0);"
													onclick="setSSId('100','Domicile/Minority/General Certificate')"
													class="panel"> <img src="img/print.png" alt="" /> 
													<span>मूल निवास, अल्पसंख्यक, सामान्य  प्रमाणपत्र  इत्यादि..</span>
												</a>
											</div>
										</div>
										
										<div class="col-md-3 deg40">
											<div class="grevanceservices">
												<a href="javascript:void(0);"
													onclick="setSSId('1003','Birth Certificate')" class="panel">
													<img src="img/birth.png" alt="" /> <span>जन्म
														प्रमाणपत्र</span>
												</a>
											</div>
										</div>
										<div class="col-md-3 deg60">
											<div class="grevanceservices">
												<a href="javascript:void(0);"
													onclick="setSSId('1001','Death Certificate')" class="panel">
													<img src="img/death.png" alt="" /> <span>मृत्यु
														प्रमाणपत्र</span>
												</a>
											</div>
										</div>
										<div class="col-md-3 deg80">
											<div class="grevanceservices">
												<a href="javascript:void(0);"
													onclick="setSSId('1004','Marriage Certificate')"
													class="panel"> <img src="img/marriage.png" alt="" /> <span>विवाह पंजीकरण
														प्रमाण पत्र</span>
												</a>
											</div>
										</div>
										<div class="col-md-3 deg100">
											<div class="grevanceservices">
												<a href="javascript:void(0);"
													onclick="setSSId('1002','Disability Certificate')"
													class="panel"> <img src="img/disability.png" alt="" /> <span>विकलांगता प्रमाण पत्र</span>
												</a>
											</div>
										</div>
										
									</div>
								</div>
							</div>
							<div class="button_div">
                                <a href="javascript:void(0);" onclick="getSP('GovernmentServiceProvider')" class="panel">
									<img src="img/new/backhindi.png" alt="" />
								</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script	type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript"  src="js/bootstrap.min.js"></script>
    <script type="text/javascript"  src="js/jquery.scrollTo.js"></script>
    <script type="text/javascript" src="js/app-inner.js"></script>
	
	<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
    
  	
  	<script>
    
	  	function setSSId(id,name) {
			$("#subServiceID").val(id);
			$("#serviceName").val(name);
			
			switch(id){
				case "1003":
					submitForm("birthservicehi");
					break;
				case "100":
				case "200":
					submitForm("governmentservicesHi12");
					break;
				case "1001":
					submitForm("deathservicehi");
					break;
				case "1004":
					submitForm("marriageservicehi");
					break;
				case "1002":
					submitForm("disabilityCertificate");
					break;
			
			}
			
		}
	    
		function submitForm(action){
			$("#serviceID").val($("#srvID").val());
		   	document.getElementById("SubSerId").action = action;
			document.getElementById("SubSerId").method = "post";
			$("#SubSerId").submit();
	    	
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

