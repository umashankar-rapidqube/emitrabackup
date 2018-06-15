function getCourtName(){
	document.getElementById("mCaseNo").value = '';
	document.getElementById("caseNumber").value = '';
	document.getElementById("courtName").value = '0';
	var distElement = document.getElementById('district');
	var ctypeElement = document.getElementById('courtType');
	
	var distSelected = $(distElement).find(":selected");
	var ctypeSelected = $(ctypeElement).find(":selected");
	
	var districtKey = $(distSelected).attr('key-dist');
	var ctypeKey = $(ctypeSelected).attr('key-ctype');
	
	if(districtKey != null && districtKey != undefined && districtKey != 0){
		if(ctypeKey != null && ctypeKey != undefined && ctypeKey != 0){
			$('#loading').show();
			document.getElementById('cType').value = ctypeKey;
			document.getElementById('districtName').value = districtKey;
			var ctypeValue = $(ctypeSelected).attr('value');
			if(ctypeValue === "10206000000012"){
				document.getElementById('mCaseNoDiv').style.display = "none";
			}else{
				document.getElementById('mCaseNoDiv').style.display = "block";
			}
			var xhttp = new XMLHttpRequest();
	  		xhttp.onreadystatechange = function() {
	  			if (this.readyState == 4 && this.status == 200) {
		  			var responseJson = JSON.parse(this.responseText);
		  			var langCode = document.getElementById('langCode1').value;
		  			var courtNameSelect = document.getElementById('courtName');
		  				courtNameSelect.innerHTML = '';
		  			var optionSelect = document.createElement('option');
		  				optionSelect.setAttribute("value" , "0");
		  				if(langCode == 0){
		  					var text = document.createTextNode('---Select---');
		  					optionSelect.appendChild(text);
		  				}else if(langCode == 1){
		  					var text = document.createTextNode('---चुनें---');
		  					optionSelect.appendChild(text);
		  				}
		  			courtNameSelect.appendChild(optionSelect);
		  			
		  			for (var i = 0; i < responseJson.length; i++){
		  				var obj = responseJson[i];
		  				if(obj['court_name'] != null && obj['court_name'] != undefined && obj['court_name'] != ""){
			  				var option = document.createElement('option');
				  				option.setAttribute("value" , obj['rowno']);
				  				option.setAttribute("key-cname" , obj['court_name']);
				  					var innerText = document.createTextNode(obj['court_name']);
				  				option.appendChild(innerText);
			  				courtNameSelect.appendChild(option);
		  				}
		  			}
		  			$('#loading').hide();
	  			}
	  		};
	  		xhttp.open("POST", "revenueCaseCourtName?districtKey="+districtKey+"&ctypeKey="+ctypeKey, false);
	  		xhttp.send();
		}
	}
}

function getCaseStatus(){
	$('#loading').show();
	document.getElementById('tdLblfinalBench').style.display = "none";
	document.getElementById('thLblfinalBench').style.display = "none";
	
	var caseNo = document.getElementById('caseNo').value;
	var cnameElement = document.getElementById('courtName');
	
	var cnameSelected = $(cnameElement).find(":selected");
	
	var cnameKey = $(cnameSelected).attr('key-cname');
	
	if(cnameKey != null && cnameKey != undefined && cnameKey != 0){
		if(caseNo != null && caseNo != undefined){
			
			document.getElementById('cName').value = cnameKey;
			var langCode = document.getElementById('langCode1').value;
			document.getElementById('langCode').value = langCode;
			
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					var responseJson = JSON.parse(this.responseText);
					
					var data = responseJson['data'];
					var keys = responseJson['keys'];
					
					for (var i = 0; i < keys.length; i++){
						if(keys[i] == 'finalBench'){
							if(data[keys[i]] == null || data[keys[i]] == undefined || data[keys[i]] == "" || data[keys[i]] == "null"){
								document.getElementById('lbl'+keys[i]).innerHTML = "----";
							}else{
								document.getElementById('lbl'+keys[i]).innerHTML = data[keys[i]];
							}
							document.getElementById(keys[i]).value = data[keys[i]];
						} else if(keys[i] == 'fileSize'){
							var fileSize = data[keys[i]];
							if(fileSize == "0")
								document.getElementById('billDetails').style.display = "none";
							else
								document.getElementById('billDetails').style.display = "block";
							
						} else if(keys[i] == 'caseNo' || keys[i] == 'secCode' || keys[i] == 'pdfUrl' || keys[i] == 'origPdfUrl') {
							document.getElementById(keys[i]).value = data[keys[i]];
						} else {
							document.getElementById(keys[i]).value = data[keys[i]];
							document.getElementById('lbl'+keys[i]).innerHTML = data[keys[i]];
						}
					}
					var ctypeElement = document.getElementById('courtType');
					var ctypeSelected = $(ctypeElement).find(":selected");
					var ctypeKey = $(ctypeSelected).attr('value');
					
					if(ctypeKey === "10206000000012"){
						document.getElementById('tdLblfinalBench').style.display = "block";
						document.getElementById('thLblfinalBench').style.display = "block";
					}
					$('#loading').hide();
					document.getElementById("fetchDetails").style.display = "block";
				}
			};
			xhttp.open("POST", "revenueCaseStatus?cnameKey="+cnameKey+"&caseNo="+caseNo, false);
			xhttp.send();
		}
	}
}

function getSP1(pageName) {
	$("#serviceProviderPage").val(pageName);
	document.getElementById("spd1").action = "utilityBills";
	document.getElementById("spd1").method = "post";
	$("#spd1").submit();
}

function getBoardType(){
	var ctypeElement = document.getElementById('courtType');
	var ctypeSelected = $(ctypeElement).find(":selected");
	var ctypeKey = $(ctypeSelected).attr('value');
	return ctypeKey;
}

function checkCaseNoRegex(){
	var caseNo = document.getElementById('caseNumber').value;
	var mCaseNo = document.getElementById('mCaseNo').value;
	var checkCaseNo = "";
	var example = "";
	var idValue = "";
	var successMsg = "";
	var regex;
	var lengthValidate = "";
	var flagNumber = "C";
	
	if(caseNo != null && caseNo != undefined && caseNo.length > 0) {
		checkCaseNo = caseNo;
		
		var boardTypeValue = getBoardType();
		
		if(boardTypeValue === "10206000000012"){
			regex = /^([0-9]){4}[/]([0-9]){1,5}?$/; // for length 10 EX. 2010/12345
			successMsg = "YYYY/NNNNN";
			example = "YYYY/12345";
		}else{
			regex = /^([0-9]){4}[/][0]([0-9]){4}?$/; // for length 10 EX. 2010/00001
			successMsg = "YYYY/NNNNN (Case No Start With '0')";
			example = "YYYY/00001";
		}
		
		lengthValidate = caseNo.length >= 6 || caseNo.length <= 10;
		idValue = "caseNoMessage";
		flagNumber = "C";
	}
	if(checkCaseNo == ""){
		if(mCaseNo != null && mCaseNo != undefined && mCaseNo.length > 0) {
			checkCaseNo = mCaseNo;
			regex = /^([0-9]){4}[/]([0-9A-Za-z]){1,10}?$/; // for length 10 EX. 2010/1234
			lengthValidate = mCaseNo.length  >= 6 || mCaseNo.length <= 9;
			example = "YYYY/1234";
			idValue = "mcaseNoMessage";
			successMsg = "";
			flagNumber = "M";
		}
	}
	
	if(!lengthValidate){
		document.getElementById('getStatusDetails').disabled = "disabled";
		document.getElementById(idValue).style = "display:block; color:red; font-size:15px;";
		if(flagNumber === "C")
			document.getElementById(idValue).innerHTML = "Please Enter In (" + example + ") Format.";
		return false;
	}else{
		var result = regex.test(checkCaseNo);
		if(result){
			document.getElementById('caseNo').value = checkCaseNo;
			document.getElementById('getStatusDetails').disabled = "";
			document.getElementById(idValue).style = "display:block; color:grey; font-size:15px;";
			document.getElementById(idValue).innerHTML = successMsg;
		}else{
			document.getElementById('getStatusDetails').disabled = "disabled";
			document.getElementById(idValue).style = "display:block; color:red; font-size:15px;";
			
			if(flagNumber === "C")
				document.getElementById(idValue).innerHTML = "Please Enter In (" + example + ") Format.";
			return false;
		}
	}
	console.log(caseNo.value);
}

function reset(){
	document.getElementById("mCaseNo").value = '';
	document.getElementById("caseNumber").value = '';
	document.getElementById("districtName").value = '0';
	document.getElementById("courtType").value = '0';
	document.getElementById("courtName").value = '0';
	document.getElementById("fetchDetails").style.display = "none";
}

function submitDetails(){
	$('#loading').show();
	document.getElementById("saveRevenueCaseDetails").action = "revenueCaseSaveDetails";
	document.getElementById("saveRevenueCaseDetails").method="post";
	document.getElementById("saveRevenueCaseDetails").submit();
}