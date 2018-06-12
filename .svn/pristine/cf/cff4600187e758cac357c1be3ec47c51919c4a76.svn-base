function getSP(pageName) {
	$("#serviceProviderPage").val(pageName);
	document.getElementById("spd").action = "utilityBills";
	document.getElementById("spd").method = "post";
	$("#spd").submit();
}
			
function checkVehicleRegex(){
	var reg = /^([A-Za-z]){2}[ -]([0-9]){2}[ -]([A-Za-z0-9]){2}[ -]([0-9]){4}?$/; // for length 13 EX. AB[ /-]12[ /-][AB/A1/1A][ /-]1234
	var reg1 = /^([A-Za-z]){2}([0-9]){2}[ -]([A-Za-z0-9]{2})[ -]([0-9]){4}?$/; // for length 12 EX. AB12[ /-][AB/A1/1A][ /-]1234
	var reg2 = /^([A-Za-z]){2}([0-9]){2}([A-Za-z0-9]{2})([0-9]){4}?$/; // for length 10 EX. AB12[AB/B3/3A]1234
	var reg3 = /^([A-Za-z]){2}([0-9]){6}?$/; // for length 8 EX. AB123456
	var reg4 = /^([A-Za-z]){2}([0-9]){2}([A-Za-z]{1})([0-9]){4}?$/; // for length 9 EX. AB12A1234
	var reg5 = /^([A-Za-z]){3}([0-9]){4}?$/; // for length 7 EX. ABC1234
	var reg6 = /^([A-Za-z]){2}([0-9]){3}([A-Za-z]{1})([0-9]){4}?$/; // for length 10 EX. AB123A1234
	var reg7 = /^([0-9]){2}([A-Za-z]){1}([0-9]{6})([A-Za-z]){1}?$/; // for length 10 EX. 12A123456A

	var regNo = document.getElementById("vehicleRegNo").value;
	var lengthValidate = regNo.length >= 7 && regNo.length <= 13;
	if(!lengthValidate){
		document.getElementById("errVehicleNoMessage").style = "display:block; color:red; font-size:15px;";
		document.getElementById("errVehicleNoMessage").innerHTML = 'Note * :- Entered Vehicle Registration Number Is Not Valid.';
		return false;
	}else{
		
		var result = reg.test(regNo) || reg1.test(regNo) || reg2.test(regNo) || reg3.test(regNo) || reg4.test(regNo) || reg5.test(regNo) || reg6.test(regNo) || reg7.test(regNo);
		if(result){
			document.getElementById("errVehicleNo").style.display = 'none';
			document.getElementById("vehicleType").disabled = "";
			document.getElementById("fuelType").disabled = "";
			document.getElementById("expDuration").disabled = "";
			document.getElementById("getBillDetails").disabled = "";
			document.getElementById("lblVehicleRegNo").innerHTML = regNo.toUpperCase();
			document.getElementById("vehicleRegNo1").value = regNo.toUpperCase();
		}else{
			document.getElementById("errVehicleNoMessage").style = "display:block; color:red; font-size:15px;";
			document.getElementById("errVehicleNoMessage").innerHTML = 'Note * :- Entered Vehicle Registration Number Is Not Valid.';
			return false;
		}
	}
}
			
function getBill(){
	
	document.getElementById("fetchDetails").style.display = "block";
	
	var vehicleType = document.getElementById("vehicleType").value;
	var fuelType = document.getElementById("fuelType").value;
	var expDuration = document.getElementById("expDuration").value;
	var langCode = document.getElementById("langCode").value;
	var payebleAmt;
	var commCharge = '6.00';
	var othCharge = '0.00';
	
	if(langCode == 1){
		if(vehicleType == '1'){//1 for 2 wheeler
			vehicleType = "दो पहिया";
			document.getElementById("vehicleType1").value = 'TWO VEHICLE';
			if(expDuration == '1'){
				expDuration = "एक महीना";
				payebleAmt = '200.00';
				document.getElementById("expDuration1").value = 'ONE MONTH';
			}
			if(expDuration == '2'){
				expDuration = "एक महीने से ज़्यादा";
				payebleAmt = '500.00';
				document.getElementById("expDuration1").value = 'MORE THAN ONE MONTH';
			}
		}
		if(vehicleType == '2'){//2 for 4 wheeler
			vehicleType = "चार पहिया";
			document.getElementById("vehicleType1").value = 'FOUR VEHICLE';
			if(expDuration == '1'){
				expDuration = "एक महीना";
				payebleAmt = '500.00';
				document.getElementById("expDuration1").value = 'ONE MONTH';
			}
			if(expDuration == '2'){
				expDuration = "एक महीने से ज़्यादा";
				payebleAmt = '1000.00';
				document.getElementById("expDuration1").value = 'MORE THAN ONE MONTH';
			}
		}
		if(fuelType == '1'){
			fuelType = 'पेट्रोल';
			document.getElementById("fuelType1").value = 'PETROL';
		}
		if(fuelType == '2'){
			fuelType = 'डीज़ल';
			document.getElementById("fuelType1").value = 'DIESEL';
		}
		
	}
	if(langCode == 0){
		if(vehicleType == '1'){//1 for 2 wheeler
			vehicleType = "TWO WHEELER";
			if(expDuration == '1'){
				expDuration = "ONE MONTH";
				payebleAmt = '200.00';
			}
			if(expDuration == '2'){
				expDuration = "MORE THAN ONE MONTH";
				payebleAmt = '500.00';
			}
		}
		if(vehicleType == '2'){//2 for 4 wheeler
			vehicleType = "FOUR WHEELER";
			if(expDuration == '1'){
				expDuration = "ONE MONTH";
				payebleAmt = '500.00';
			}
			if(expDuration == '2'){
				expDuration = "MORE THAN ONE MONTH";
				payebleAmt = '1000.00';
			}
		}
		if(fuelType == '1')
			fuelType = 'PETROL';
		if(fuelType == '2')
			fuelType = 'DIESEL';

		document.getElementById("fuelType1").value = fuelType;
		document.getElementById("expDuration1").value = expDuration;
		document.getElementById("vehicleType1").value = vehicleType;
	}
	
	var consumerKey = document.getElementById("vehicleRegNo1").value+'-'+document.getElementById("vehicleType1").value
						+'-'+document.getElementById("fuelType1").value+'-'+document.getElementById("expDuration1").value;
	var totalAmount = parseFloat(payebleAmt) + parseFloat(commCharge);
	
	document.getElementById("lblFuelType").innerHTML = fuelType;
	document.getElementById("lblExpDuration").innerHTML = expDuration;
	document.getElementById("lblVehicletype").innerHTML = vehicleType;
	document.getElementById("lblPayableAmt").innerHTML = payebleAmt;
	document.getElementById("lblCommCharges").innerHTML = commCharge;
	document.getElementById("lblOthCharges").innerHTML = othCharge;
	document.getElementById("lblTotalAmount").innerHTML = totalAmount.toFixed(2);
	document.getElementById("lblConsumerKey").innerHTML = consumerKey;
	
	document.getElementById("payableAmt").value = payebleAmt;
	document.getElementById("commCharges").value = commCharge;
	document.getElementById("othCharges").value = othCharge;
	document.getElementById("totalAmount").value = totalAmount.toFixed(2);
	document.getElementById("consumerKey").value = consumerKey;
	
	document.getElementById("reqMobileMessage").style = 'display:block;color:red;';
	document.getElementById("reqMobileMessage").innerHTML = "Please Enter a Mobile Number.";
	
	setTimeout(function() {
		document.getElementById("reqMobileMessage").style.display = "none";
		document.getElementById("reqMobileMessage").innerHTML = "";
	}, 15000);
}
			
function submitBillDetails(){
	document.getElementById("mobileNo").value = document.getElementById("mobileNumber").value;
	document.getElementById("emailID").value = document.getElementById("email_id").value;
	
	document.getElementById('savePUCDetails').action="savePUCDetails";
	document.getElementById('savePUCDetails').method="post";
	document.getElementById('savePUCDetails').submit();
}

function reset(){
	document.getElementById("fetchDetails").style.display = "none";
	document.getElementById("vehicleRegNo").value = '';
	document.getElementById("vehicleType").value = '0';
	document.getElementById("fuelType").value = '0';
	document.getElementById("expDuration").value = '0';
}

function isNumberKey(event , obj){
	var flag = false;
	var key = event.keyCode;
	if (key === 8 || key === 46) {
    	flag = false;
        
    } else if ( key < 48 || key > 57 ) {
    	flag = false;
    } else {
    	flag = true;
    }
    
    if(flag){
		var length = obj.maxLength;
		var value = obj.value ;
		if(value.length === length){
			document.getElementById('billDetails').disabled = "";
		}else{
			document.getElementById('billDetails').disabled = "disabled";
		}
	}else{
		document.getElementById('billDetails').disabled = "disabled";
	}
}