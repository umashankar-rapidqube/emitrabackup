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
	    <title>KIOSK - Home</title>
	    <link href="https://fonts.googleapis.com/css?family=Noto+Sans|Nunito+Sans:600|700" type="text/css" rel="stylesheet">
	    <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	    <link rel="stylesheet" type="text/css" href="css/bootstrap-material-design.min.css">
	    <link rel="icon" href="img/favicon.ico" type="image/x-icon">
	    <link href="css/my-style.css" type="text/css" rel="stylesheet" />
	        <link rel="stylesheet" type="text/css" href="css/slick-theme.css" />
	    <link rel="stylesheet" type="text/css" href="css/slick.css" />
	      <style>
	    .versionbox{
	        position: fixed;
		    top: 130px;
		    left: 11%;
		    height: 40px;
		    width: 25%;
		    padding-top: 5px;	    
		    font-weight: 700;
		   
	   	}
	    .versionbox h4{
	      color: #ec4556 !important;
	       font-size: 15px;
	       text-transform: uppercase;
	    }
	    </style>
	</head>

	<body>
		<div id="wrapper">
			<div id="mask">
				<div id="item1" class="item home_back">
					<div class="topone">
						<div class="container">
							<div class="col-md-12">
								<div class="col-md-4 logo_div">
									<img src="img/logo.png" class="logo">
								</div>
								<div class="col-md-4"></div>
							</div>
							<div class="col-md-12 lahar">
								<img src="img/home_red1.png" class="lahar_img">
								<div class="lang_div">
									<div>
										<a class="btn btn-default panel" href="javascript:void(0);" onclick="getSP('Service' , '1')" id="hindi">
											<img src="img/hindi.png" class="lang_btn">
										</a>
										<a class="btn btn-default panel" href="javascript:void(0);" onclick="getSP('Service' , '0')" id="english">
											<img src="img/english.png" class="lang_btn">
										</a>
									</div>
								</div>
								<form id="spd">
									<input type="hidden" name="langCode" id="langCode">
									<input type="hidden" name="serviceProviderPage" id="serviceProviderPage" />
								</form>
							</div>
							<div class="col-md-12 versionbox">
								<h4>Version 3.0</h4>
							</div>
							<img src="img/cartooon-new.png" id="myImage"
								onclick="changeImage()" class="a" alt="" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="loading" style="display: none;">
			<img id="loading-image" src="img/ajax-loader.gif"
				alt="लोड हो रहा है..." />
		</div>
	
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/jquery.scrollTo.js"></script>
		<script type="text/javascript" src="js/app.js"></script>
		<script type="text/javascript" src="js/jquery.flexisel.js"></script>
		<script type="text/javascript" src="js/jquery-ui.js"></script>
		<script type="text/javascript" src="js/slick.js"></script>
	
		<script type="text/javascript" src="js/KioskServices/BackButtonDisable.js"></script>
	
		<script type="text/javascript">
		function getSP(pageName , langCode) {
			$("#langCode").val(langCode);
			$("#serviceProviderPage").val(pageName);
			document.getElementById("spd").action = "service";
			document.getElementById("spd").method = "post";
			document.getElementById('spd').submit();
		}
	    </script>
	</body>
</html>