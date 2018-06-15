package controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import commonServices.ReceiptPrint;
import model.NoteType;
import service.BhamashahCardService;
import service.BillService;
import webServicesRepository.utility.EncrptDesryptDataService;

@EnableWebMvc
@Controller
public class BhamashahCardController {

	static final Logger logger = Logger.getLogger(BhamashahCardController.class);
	private static final String SERVICEPROVIDERNAME = "Editing In Bhamashah Card";
	private static final String SERVICEPROVIDERID = "3025";
	@Autowired
	BillService billService;
	
	@Autowired
	BhamashahCardService bhamashahCardService;
	
	@RequestMapping(value = "/bhamashahCardHi" ,method = RequestMethod.POST)
	public ModelAndView bonafideHi(HttpServletRequest request) {
		request.setAttribute("serviceProviderName", SERVICEPROVIDERNAME);
		request.setAttribute("serviceProviderID", SERVICEPROVIDERID);
		return new ModelAndView("bhamashahCardHi");
	}
	
	@RequestMapping(value = "/bhamashahCard" ,method = RequestMethod.POST)
	public ModelAndView bonafide(HttpServletRequest request) {
		request.setAttribute("serviceProviderName", SERVICEPROVIDERNAME);
		request.setAttribute("serviceProviderID", SERVICEPROVIDERID);
		return new ModelAndView("bhamashahCard");
	}
	
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/uidOtpGeneration" ,method = RequestMethod.POST)
	public ResponseEntity<JSONObject> uidOtpGeneration(@RequestParam String memberUniqueId , @RequestParam String modeOfPage) {
			
		JSONObject responseObject = bhamashahCardService.uidOtpGeneration(memberUniqueId, modeOfPage);
		if(responseObject != null && !responseObject.isEmpty()) {
			return new ResponseEntity<JSONObject>(responseObject, HttpStatus.OK);
		}else {
			String message = null;
			if(modeOfPage.equalsIgnoreCase("ENG")) {
				message = "There's an error occurred in server, please try again after some time.";
			}else if(modeOfPage.equalsIgnoreCase("HND")) {
				message = "सर्वर में  त्रुटि हुई है, कृपया कुछ समय बाद पुनः प्रयास करें।";
			}
			responseObject.put("message", message);
			responseObject.put("status", "n");
			
			return new ResponseEntity<JSONObject>(responseObject, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/uidOtpAuth" ,method = RequestMethod.POST)
	public ResponseEntity<JSONObject> uidOtpAuth(HttpServletRequest request , @RequestParam ("serviceData") String serviceData) {

		JSONParser parser = new JSONParser();
		JSONObject serviceDataJson = new JSONObject();
        try {
        	serviceDataJson = (JSONObject) parser.parse(serviceData);

        } catch (org.json.simple.parser.ParseException e) {
        	logger.error("BhamashahCardController, Exception in uidOtpAuth : " +e.getMessage());
			/*e.printStackTrace();*/
		}
        
        JSONObject data = bhamashahCardService.uidOtpAuth(request , serviceDataJson);
		
		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOtpForMobileVerify" ,method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getOtpForMobileVerify(@RequestParam ("mobileNo") String mobileNo , @RequestParam ("txnId") String txnId , @RequestParam ("modeOfPage") String modeOfPage) {
		JSONObject resObj = new JSONObject();
		JSONObject responseObj = bhamashahCardService.getOtpForMobileVerify(mobileNo , txnId);
		if(responseObj != null && !responseObj.isEmpty()) {
			String OTP = String.valueOf(responseObj.get("OTP"));
			if(OTP != null && !OTP.isEmpty()) {
				resObj.put("OTP", OTP);
			}else {
				resObj.put("OTP", "");
			}
		}else {
			resObj.put("OTP", "");
		}
		return new ResponseEntity<JSONObject>(resObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getIFSCDetails" ,method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getIFSCDetails(@RequestParam ("ifscCode") String ifscCode , @RequestParam ("modeOfPage") String modeOfPage) {
		
		JSONObject resObj = bhamashahCardService.getIFSCDetails(ifscCode);
		if(resObj != null && !resObj.isEmpty()) 
			return new ResponseEntity<JSONObject>(resObj, HttpStatus.OK);
		else {
			return new ResponseEntity<JSONObject>(new JSONObject(), HttpStatus.OK);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statusForUpdateNumber" ,method = RequestMethod.GET)
	public ResponseEntity<JSONObject> statusForUpdateNumber(@RequestParam ("memberData") String memberData , @RequestParam ("updateFlag") String updateFlag) {
		
		JSONParser parser = new JSONParser();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson = (JSONObject) parser.parse(memberData);
		} catch (ParseException e) {
			logger.error("BhamashahCardController, Exception in statusForUpdateNumber : " + e.getMessage());
			/*e.printStackTrace();*/
		}
		
		JSONObject resObj = bhamashahCardService.statusForUpdateNumber(dataJson , updateFlag);
		
		if(resObj != null && !resObj.isEmpty()) 
			return new ResponseEntity<JSONObject>(resObj, HttpStatus.OK);
		else {
			resObj.put("status", "N");
			return new ResponseEntity<JSONObject>(new JSONObject(), HttpStatus.OK);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateMobileAccountNumber" ,method = RequestMethod.POST)
	public ModelAndView updateMobileAccountNumber(HttpServletRequest request) {
		String langCode = null;
		try {
			JSONObject resObj = new JSONObject();
			String transId = request.getParameter("transId");
			String languageCode = request.getParameter("langCode");
			String updateCount = request.getParameter("updateCount");
			String message = null;
			
			if(languageCode.equals("0")) {
				langCode = "ENG";
			}else {
				langCode = "HND";
			}
			
			if(updateCount.equalsIgnoreCase("1")) {
				String eMitratransId = request.getParameter("eMitratransId");
				String cancelStatus = request.getParameter("cancelStatus");
				if(cancelStatus.equalsIgnoreCase("Y")){
					String returnCancelStatus = cancelTranscation(eMitratransId);
					logger.info("cancelStatus of emitraTransactionId");
					logger.debug("Transacton Cancel Status:" + returnCancelStatus);
					if(langCode.equalsIgnoreCase("ENG")) {
						message = "Transaction has been cancelled.";
					}else {
						message = "लेनदेन रद्द कर दिया गया है।";
					}
					request.setAttribute("aadharNo", "");
					
				}else if(cancelStatus.equalsIgnoreCase("N")){
					
					bhamashahCardService.updateBhamashahStatus(transId , "PENDING");
					
					resObj = bhamashahCardService.updateMobileAccountNumber(transId , updateCount , languageCode);
					
					String updateStatus = String.valueOf(resObj.get("VERIFY_STATUS"));
					
					if(!updateStatus.equalsIgnoreCase("V")) {
						resObj.put("updateCount", updateCount);
					}else {
						bhamashahCardService.updateBhamashahStatus(transId , "SUCCESS");
						
						org.json.JSONObject receiptData = bhamashahCardService.getPrintReceiptData("sp_bhamashah_paymentdetails" , "getPrintReceiptData" , transId);
						
						resObj.put("updateCount", 0);
//						org.json.JSONObject receiptData = (org.json.JSONObject)resObj.get("statusOfTransaction");
						String consumerKey = getConsumerKey(receiptData);
						receiptData.put("consumerKey" , consumerKey);	
						ReceiptPrint.printReciept(request , receiptData , "certificate");
					}
					
					request.setAttribute("aadharNo", resObj.get("aadharNo"));
					message = String.valueOf(resObj.get("message"));
				}
			}else if(updateCount.equalsIgnoreCase("2")){
				resObj = bhamashahCardService.updateMobileAccountNumber(transId , updateCount , languageCode);
				
				String updateStatus = String.valueOf(resObj.get("VERIFY_STATUS"));
				
				if(!updateStatus.equalsIgnoreCase("V")) {
					resObj.put("updateCount", updateCount);
				}else {
					bhamashahCardService.updateBhamashahStatus(transId , "SUCCESS");
					
					org.json.JSONObject receiptData = bhamashahCardService.getPrintReceiptData("sp_bhamashah_paymentdetails" , "getPrintReceiptData" , transId);
					
					resObj.put("updateCount", 0);
//					org.json.JSONObject receiptData = (org.json.JSONObject)resObj.get("statusOfTransaction");
					String consumerKey = getConsumerKey(receiptData);
					receiptData.put("consumerKey" , consumerKey);
					ReceiptPrint.printReciept(request , receiptData , "certificate");
				}
				
				request.setAttribute("aadharNo", resObj.get("aadharNo"));
				message = String.valueOf(resObj.get("message"));
			}
			
			request.setAttribute("transId", transId);
			request.setAttribute("updateCount", resObj.get("updateCount"));
			request.setAttribute("serviceProviderName", SERVICEPROVIDERNAME);
			request.setAttribute("serviceProviderID", SERVICEPROVIDERID);
			request.setAttribute("languageCode", languageCode);
			
			request.setAttribute("message", message);
			request.setAttribute("submitButtonAction", "Y");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			logger.error("BhamashahCardController, Exception in updateMobileAccountNumber : "+e.getMessage());
			/*e.printStackTrace();*/
		} catch(Exception e) {
			// TODO Auto-generated catch block
			logger.error("BhamashahCardController, Exception in updateMobileAccountNumber : "+e.getMessage());
			/*e.printStackTrace();*/
		}
		
		if(langCode.equalsIgnoreCase("HND")) {
			return new ModelAndView("bhamashahCardHi");
		}else {
			return new ModelAndView("bhamashahCard");
		}
	}
	
	private String getConsumerKey(org.json.JSONObject receiptData) {
		// TODO Auto-generated method stub
		String consumerKey = null;
		try {
			String Update_Flag = String.valueOf(receiptData.get("Update_Flag"));
			String UID = String.valueOf(receiptData.get("UID"));
			
			if (Update_Flag.equalsIgnoreCase("M")) {
				consumerKey = UID + "-" + receiptData.get("Mobile_No");
			} else if (Update_Flag.equalsIgnoreCase("A")) {
				consumerKey = UID + "-" + receiptData.get("Bank_Account_No");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			logger.error("BhamashahCardController, Exception in getConsumerKey : " + e.getMessage());
			/*e.printStackTrace();*/
		}
		
		return consumerKey;
	}

	@RequestMapping(value = "/paymentModeBhamashahCard" ,method = RequestMethod.POST)
	public ModelAndView paymentModeBhamashahCard(Model model, HttpServletRequest request) {
		
		String data = String.valueOf(request.getParameter("memberDetails"));
		JSONParser parser = new JSONParser();
		JSONObject memberData = new JSONObject();
		try {
			memberData = (JSONObject) parser.parse(data);
		} catch (ParseException e) {
			logger.error("BhamashahCardController, Exception in paymentModeBhamashahCard : "+e.getMessage());
			/*e.printStackTrace();*/
		}
		String language = String.valueOf(memberData.get("LANGUAGE_CODE"));
		int langCode = 0;
		if(language.equalsIgnoreCase("ENG")) {
			langCode = 0;
		}else if(language.equalsIgnoreCase("HND")) {
			langCode = 1;
		}

		String checkSSO = EncrptDesryptDataService.checkSSOID();
		if (checkSSO.equalsIgnoreCase("yes")) {

			logger.info("paymentModeBhamashahCard :: Before flushing note value");
			logger.info("paymentModeBhamashahCard :: Note Value, total10note :");
			logger.debug(NoteType.total10Note + " total20note :"
					+ NoteType.total20Note + " total50note:" + NoteType.total50Note + " total100note :"
					+ NoteType.total100Note + " total200note :" + NoteType.total200Note + " total500note :"
					+ NoteType.total500Note + " total2000note :" + NoteType.total2000Note);
			NoteType.total10Note = 0;
			NoteType.total20Note = 0;
			NoteType.total50Note = 0;
			NoteType.total100Note = 0;
			NoteType.total200Note = 0;
			NoteType.total500Note = 0;
			NoteType.total2000Note = 0;
			NoteType.cashTimer = 0;
			NoteType.depositAmount = 0;

			logger.info("paymentModeBhamashahCard ::After regreshing Note Value, total10note :");
			logger.debug( NoteType.total10Note
					+ " total20note :" + NoteType.total20Note + " total50note:" + NoteType.total50Note
					+ " total100note :" + NoteType.total100Note + " total200note :" + NoteType.total200Note
					+ " total500note :" + NoteType.total500Note + " total2000note :" + NoteType.total2000Note);

			String token = request.getParameter("token");
			String Amount = request.getParameter("amount");
			String certiTransID = request.getParameter("transactionID");
			String serviceID = request.getParameter("serviceProviderID");
			String serviceName = request.getParameter("serviceProviderName");

			logger.debug("paymentModeBhamashahCard ::Amount :" + Amount + " CertiTransID :" + certiTransID + " ServiceID:"
					+ serviceID);

			request.setAttribute("token", token);
			request.setAttribute("serviceID", serviceID);
			request.setAttribute("serviceName", serviceName);

			String transId = bhamashahCardService.updateBhamashahPaymentDetails(request, memberData);
			request.setAttribute("certiTransID", transId);
			if (langCode == 0)
				return new ModelAndView("ServicePaymentmode");
			else
				return new ModelAndView("ServicePaymentmodehi");

		} else {
			return new ModelAndView("Login");
		}
	}
	
	public String cancelTranscation(String emitraTransactionId) {
		
		logger.info("cancelTranscation, emitraTransactionId");
		logger.debug("cancelTranscation, emitraTransactionId :"+emitraTransactionId);
		String cancelStatus = null;
		if (emitraTransactionId != null) {

			String cancelTranscationurl = billService.getCancelTranscationurl(emitraTransactionId);
			logger.info("cancelTranscation");
			logger.debug("cancelTranscation, cancelTranscationurl :"+cancelTranscationurl);

			EncrptDesryptDataService eds = new EncrptDesryptDataService();
			String encriptCancelTranscationUrl = null;
			try {
				encriptCancelTranscationUrl = eds.sendPostForEncryptData(cancelTranscationurl);
			} catch (Exception e1) {
				logger.error("BhamashahCardController, Exception in cancelTranscation for sendPostForEncryptData: " +e1.getMessage());
				/*e1.printStackTrace();*/
			}
			logger.info("cancelTranscation, encriptBackTOBackUrl ");
			logger.debug("cancelTranscation, encriptBackTOBackUrl :"+encriptCancelTranscationUrl);
			String cancelTranscationResponse = null;
			try {
				cancelTranscationResponse = eds.sendPostForCancelTranscation(encriptCancelTranscationUrl);
			} catch (Exception e1) {
				logger.error("BhamashahCardController, Exception in cancelTranscation for sendPostForEncryptData : " +e1.getMessage());
				/*e1.printStackTrace();*/
			}
			
			logger.info("cancelTranscation, cancelTranscationResponse");
			logger.debug("cancelTranscation, cancelTranscationResponse :"+cancelTranscationResponse);
			String decriptCancelTranscationUrl = null;
			try {
				decriptCancelTranscationUrl = eds.sendPostForDecryptData(cancelTranscationResponse);
				logger.debug("cancelTranscation, decriptCancelTranscationUrl :"+decriptCancelTranscationUrl);
				
				if(decriptCancelTranscationUrl!=null && !decriptCancelTranscationUrl.equals("")) {
			        billService.saveCancelTranscationurl(decriptCancelTranscationUrl);
			        JSONParser parser = new JSONParser();
			        JSONObject json;
			        json = (JSONObject) parser.parse(decriptCancelTranscationUrl);
			        cancelStatus = (String)json.get("CANCELSTATUS");
			        if(cancelStatus.equals("SUCCESS"))
			        	billService.updateDeleteTransactionFlag("Yes", emitraTransactionId);
				}
		        
			} catch (Exception e1) {
				logger.error("cancelTranscation exception::"+e1.getMessage());
				/*e1.printStackTrace();*/
			}
			logger.info("cancelTranscation");
			logger.debug("cancelTranscation, decriptCancelTranscationUrl :"+decriptCancelTranscationUrl);
		}
		return cancelStatus;

	}
}