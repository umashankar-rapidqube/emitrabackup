package controller;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import commonServices.MobileOTP;
import locallogs.LocalLogSyncer;
import model.Bill;
import model.CardPaymentDetails;
import model.CertificateInfo;
import model.Discom;
import model.Jamabandi;
import model.LatestVersionDetails;
import model.Login;
import model.MachineAuth;
import model.NoteType;
import model.Phed;
import model.grievenceDetails;
import service.BillService;
import service.CertificateService;
import service.EncryptDecryptService;
import sun.misc.BASE64Decoder;
import webServicesRepository.utility.EncrptDesryptDataService;
import webServicesRepository.utility.VedioConference;

@SuppressWarnings("restriction")
@EnableWebMvc
@Controller
public class BillController {
	static final Logger logger = Logger.getLogger(BillController.class);
	private final String USER_AGENT = "Mozilla/5.0";
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String AES = "AES";
	private static final String SHA256 = "SHA-256";
	private static final String yesCardEncryptionKey = "A93A58AA4ED6B323ACFD7735C56EA";
	private static final String noCardEncryptionKey = "7D118B59FE7691D56DB47C84D271C";

	private static final String yesCardCheckSum = "Y-EQ!H&SIV.lFzh";
	private static final String noCardCheckSum = "Pw<1!%l81bHY/YZ";

	private static final String yesCardMerchantCode = "EMITRAPLUSEZETAP";
	private static final String noCardMerchantCode = "EMITRAPLUSBILLDESK";

	// digit secure
	private static final String digitSecureEncryptionKey = "1AEED1A2FD4FE22A527A5AFA6EDEA";// Production
	// private static final String digitSecureEncryptionKey =
	// "E2579841F97C9B6D48C79B4367EFC";//Testing
	private static final String digitSecureMerchantCode = "EMITRAPLUSDIGITSECURE";// Production
	// private static final String digitSecureMerchantCode = "EMITRAPLUS";//Testing
	private static final String digitSecureCheckSum = "z[$#vdPb~:2h@8V";// Production
	// private static final String digitSecureCheckSum = "@Vr/.]-2Qa2ManE";//Testing

	BufferedReader br;
	PrintWriter pw;

	@Autowired
	BillService billService;

	@Autowired
	EncryptDecryptService encryptDecryptService;

	@Autowired
	CertificateService certificateService;

	@Autowired
	LocalLogSyncer localLogSyncer;

	@RequestMapping(value = "/serviceProviderInfo", method = RequestMethod.GET)
	public String getServiceProvider() {
		return "index";
	}

	@RequestMapping(value = "/retruntoindex", method = RequestMethod.POST)
	public String getServiceP() {
		return "index";
	}

	@RequestMapping(value = "/serviceProviderInfo", method = RequestMethod.POST)
	public String postServiceProvider(Model model, Bill bill) {
		model.addAttribute("serviceProvider", bill);
		return "serviceProviderInfo";
	}

	@RequestMapping(value = "/serviceProviderInfoHi", method = RequestMethod.POST)
	public String postserviceProviderInfoHi(Model model, Bill bill) {
		model.addAttribute("serviceProvider", bill);
		return "serviceProviderInfoHi";
	}

	@RequestMapping(value = "/billinfo", method = RequestMethod.GET)
	public String getHomePage() {
		return "index";
	}

	@RequestMapping(value = "/noDetailsFoundHi")
	public ModelAndView noDetailsFoundHi() {
		return new ModelAndView("noDetailsFoundHi");
	}

	@RequestMapping(value = "/noDetailsFound")
	public ModelAndView noDetailsFound() {
		return new ModelAndView("noDetailsFound");
	}

	@RequestMapping(value = "/billinfo", method = RequestMethod.POST)
	public String showBillInfoPage(Model model, Bill bill, HttpServletRequest req) throws SQLException {
		int flag = 0;
		String strToEncrypt = "";
		String bsnlType = "";
		if (bill.getServiceProviderID() == 2575) {
			bsnlType = req.getParameter("bsnlType");
		}

		logger.debug("Billinfo, ServiceProviderID :: " + bill.getServiceProviderID() + " serviceProviderName :: "
				+ bill.getServiceProviderName() + " MobileNo : " + bill.getBillMobileNo() + " email id : "
				+ bill.getBillEmail());

		if (bill.getBillEmail() == null || bill.getBillEmail().equals(""))
			bill.setBillEmail("helpdesk.emitra@rajasthan.gov.in");

		bill.setSsoID(Login.SSOID);
		int langCode = bill.getLangCode();
		EncrptDesryptDataService encryptService = new EncrptDesryptDataService();
		String mobileNo = bill.getBillMobileNo();

		if (bill.getServiceProviderID() == 2575) {
			if (bsnlType.equalsIgnoreCase("mobile")) {
				strToEncrypt = "{'SSOID':'" + Login.SSOID + "','SRVID':'" + bill.getServiceProviderID()
						+ "','searchKey':'" + mobileNo + "-" + mobileNo + "-GSM'}";
			} else if (bsnlType.equalsIgnoreCase("landline")) {
				strToEncrypt = "{'SSOID':'" + Login.SSOID + "','SRVID':'" + bill.getServiceProviderID()
						+ "','searchKey':'" + mobileNo + "-" + mobileNo + "-CDR'}";
			}
		}

		else
			strToEncrypt = "{'SRVID':'" + bill.getServiceProviderID() + "','searchKey':'" + mobileNo + "','SSOID':'"
					+ Login.SSOID + "'}";

		String encryptData = null;
		String encryptDetails = null;
		List<Bill> billInformation = null;
		try {
			encryptData = encryptService.sendPostForEncryptData(strToEncrypt);
		} catch (Exception e) {
			logger.error("Billinfo, Exception in sendPostForEncryptData :" + e.getMessage());
			encryptData = null;
			/* e.printStackTrace(); */
		}

		logger.debug("Billinfo, encryptData  :" + encryptData);
		if (!encryptData.equals("") && encryptData != null) {
			try {
				encryptDetails = encryptService.getBillInformationData(encryptData);
			} catch (Exception e) {
				logger.error("Exception in getBillInformationData encryptDetails: " + e.getMessage());
				encryptDetails = null;
				/* e.printStackTrace(); */
			}
		}
		logger.debug("Billinfo, encryptDetails : " + encryptDetails);

		try {
			billInformation = encryptDecryptService.sendPostForDecryptDetail(encryptDetails, bill);
		} catch (Exception e) {
			logger.error("Billinfo, Exception in decryptdetails :" + e.getMessage());
			/* e.printStackTrace(); */
		}
		String transactionId = "";
		double d = 0.0;
		int billamount = 0;
		if (billInformation.size() > 0) {
			transactionId = billInformation.get(0).getTransactionId();

			d = Double.parseDouble(billInformation.get(0).getBillAmount().trim().equalsIgnoreCase("NA") ? "0"
					: billInformation.get(0).getBillAmount());

			int x = new EncrptDesryptDataService().getCashMachineAmount(d);
			billamount = x;
			flag = 1;
		}

		logger.debug("Billinfo, billamount : " + billamount + " transactionId : " + transactionId + " ActualAmountt :: "
				+ d + " serviceProverId : " + bill.getServiceProviderID());

		Bill infoBill = new Bill();
		try {

			infoBill.setBillAmount(Integer.toString(billamount));
			infoBill.setTransactionId(transactionId);
			infoBill.setBillActualAmount(Double.toString(d));
			infoBill.setServiceProviderID(bill.getServiceProviderID());
			billService.updateBillInformation(infoBill);
		} catch (Exception e) {
			logger.error("Exception in updateBillInformation : " + e.getMessage());
			/* e.printStackTrace(); */
		}

		if (flag == 1) {
			req.setAttribute("details", billInformation);
			req.setAttribute("infobill", infoBill);
			if (langCode == 0)
				return "BillDetails";
			else
				return "BillDetailsHi";
		} else {
			if (langCode == 0)
				return "redirect:noDetailsFound";
			else
				return "redirect:noDetailsFoundHi";
		}
	}

	@RequestMapping(value = "/clickToPay", method = RequestMethod.POST)
	public ModelAndView clickToPay(Model model, Bill bill, HttpServletRequest req) {
		logger.debug("Actual Amount  :" + bill.getBillActualAmount() + " TransactionId : " + bill.getTransactionId()
				+ " Billamount : " + bill.getBillAmount() + " ServiceProvider ID : " + bill.getServiceProviderID());

		String checkSSO = EncrptDesryptDataService.checkSSOID();
		if (checkSSO.equalsIgnoreCase("yes")) {
			req.setAttribute("amount", bill.getBillAmount());
			req.setAttribute("Id", bill.getTransactionId());
			req.setAttribute("acutalAmt", bill.getBillActualAmount());
			req.setAttribute("serviceProviderID", bill.getServiceProviderID());
			req.setAttribute("name", bill.getName());
			req.setAttribute("email", bill.getBillEmail());
			req.setAttribute("mobile", bill.getBillMobileNo());
			req.setAttribute("consumerKeyValue", bill.getConsumerKeyValue());
			req.setAttribute("date", bill.getCreatedDate());

			logger.debug("clickToPay :: Note Value, total10note :" + NoteType.total10Note + " total20note :"
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

			logger.debug("clickToPay ::After regreshing Note Value, total10note :" + NoteType.total10Note
					+ " total20note :" + NoteType.total20Note + " total50note:" + NoteType.total50Note
					+ " total100note :" + NoteType.total100Note + " total200note :" + NoteType.total200Note
					+ " total500note :" + NoteType.total500Note + " total2000note :" + NoteType.total2000Note);
			if (bill.getLangCode() == 1)
				return new ModelAndView("PaymentmodeHi");
			else
				return new ModelAndView("Paymentmode");
		} else {
			return new ModelAndView("Login");
		}
	}

	@RequestMapping(value = "/service", method = RequestMethod.POST)
	public String service(Model model, HttpServletRequest request, Bill bill) {
		request.setAttribute("langCode", bill.getLangCode());
		return bill.getServiceProviderPage();
	}

	@RequestMapping(value = "/utilityBills", method = RequestMethod.POST)
	public String getUtilityBills(Model model, Bill bill, HttpServletRequest req) {
		model.addAttribute("bill", bill);
		return bill.getServiceProviderPage();
	}

	@RequestMapping(value = "/designationOfDepartment")
	public ModelAndView designationOfDepartment(Model model, HttpServletRequest req) {
		return new ModelAndView("DesignationOfDepartment");
	}

	@RequestMapping(value = "/governmentservice")
	public ModelAndView govtservice(Model model, HttpServletRequest req, CertificateInfo certificateInfo)
			throws IOException, JSONException, org.json.simple.parser.ParseException {
		String tokenid = req.getParameter("tokenId");
		String certificateType = req.getParameter("certificateDetails");
		int langCode = Integer.parseInt(req.getParameter("langCode"));

		certificateInfo.setRegistrationNo(tokenid);
		String serviceName = certificateInfo.getServiceName();
		String serviceID = certificateInfo.getServiceID();
		String subServiceID = certificateInfo.getSubServiceID();

		logger.debug("Government Service Token Id : " + tokenid);
		String encryptURL = "https://api.sewadwaar.rajasthan.gov.in/app/live/eMitra/Prod/PrintCert/Service/getCertDetails?tokenNo="
				+ tokenid + "&printType=A4&client_id=96f2ea05-8742-401d-aa42-ec269f8a71c0";

		String response1 = "";
		String result = "";
		response1 = encryptDecryptService.getGovtServiceDetail(encryptURL);

		JSONParser parser = new JSONParser();
		JSONObject json;
		try {
			json = (JSONObject) parser.parse(response1);
			String url = (String) json.get("URL");
			logger.debug("Government Service :: how Certificate URL with urlOfPrinting :" + url);

			if (url.equals("")) {
				if (langCode == 0)
					result = "noDetailsFound";
				else
					result = "noDetailsFoundHi";
			} else {
				json = billService.downloadfile(json, tokenid, req);
				String downloadstatus = (String) json.get("STATUS_CODE");
				if (downloadstatus.equalsIgnoreCase("ApiException")) {
					if (langCode == 0)
						result = "noDetailsFound";
					else
						result = "noDetailsFoundHi";
					logger.debug("CertificateService Service :: Error in downloading::"
							+ (String) json.get("ERROR_MESSAGE"));
					return new ModelAndView(result);
				} else {
					String arr[] = ((String) json.get("URL")).split("urlOfPrinting=");
					req.setAttribute("url", (String) json.get("URL"));
					logger.debug("CertificateService Service :: urlOfPrinting::" + arr[1]);
					req.setAttribute("url1", arr[1]);
				}

				String certificateTransID = certificateService.insertCertificateDetails(certificateInfo);
				req.setAttribute("certificateType", certificateType);
				req.setAttribute("token", tokenid);
				req.setAttribute("serviceID", serviceID);
				req.setAttribute("subServiceID", subServiceID);
				req.setAttribute("serviceName", serviceName);
				req.setAttribute("transactionID", certificateTransID);
				Thread.sleep(8000);
				if (langCode == 0)
					result = "governmentPDF";
				else
					result = "governmentPDFHi";
			}

		} catch (Exception e) {
			if (langCode == 0)
				result = "noDetailsFound";
			else
				result = "noDetailsFoundHi";
			logger.error("BillController, Exception in govtservice for insertCertificateDetails :" + e.getMessage());
			/* e.printStackTrace(); */
		}
		logger.info("Government Service :: ModelAndView Name :" + result);
		return new ModelAndView(result);
	}

	@RequestMapping(value = "/ServicePaymentmode")
	public ModelAndView govern(Model model, HttpServletRequest req) {
		req.setAttribute("url", req.getParameter("govtpdf"));
		int langCode = Integer.parseInt(req.getParameter("langCode"));

		String checkSSO = EncrptDesryptDataService.checkSSOID();
		if (checkSSO.equalsIgnoreCase("yes")) {
			logger.info("ServicePaymentmode :: Before flushing note value");
			logger.debug("ServicePaymentmode :: Note Value, total10note :" + NoteType.total10Note + " total20note :"
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

			logger.debug("ServicePaymentmode ::After regreshing Note Value, total10note :" + NoteType.total10Note
					+ " total20note :" + NoteType.total20Note + " total50note:" + NoteType.total50Note
					+ " total100note :" + NoteType.total100Note + " total200note :" + NoteType.total200Note
					+ " total500note :" + NoteType.total500Note + " total2000note :" + NoteType.total2000Note);

			String token = req.getParameter("token");
			String certificateType = req.getParameter("certificateType");
			int certificareYear = Integer
					.parseInt(req.getParameter("certificateYear") == null ? "0" : req.getParameter("certificateYear"));

			String Amount = req.getParameter("amount");
			String CertiTransID = req.getParameter("transactionID");
			String ServiceID = req.getParameter("serviceID");
			String serviceName = req.getParameter("serviceName");
			logger.debug("ServicePaymentmode ::Amount :" + Amount + " CertiTransID :" + CertiTransID + " ServiceID:"
					+ ServiceID);

			req.setAttribute("token", token);
			req.setAttribute("certiTransID", CertiTransID);
			req.setAttribute("serviceID", ServiceID);
			req.setAttribute("serviceName", serviceName);
			billService.savePaymentStatus(CertiTransID, certificateType, certificareYear);

			if (langCode == 0)
				return new ModelAndView("ServicePaymentmode");
			else
				return new ModelAndView("ServicePaymentmodehi");
		} else {
			return new ModelAndView("Login");
		}
	}

	@RequestMapping(value = "/governmentServiceProvider")
	public ModelAndView governmentServiceProvider(Model model, HttpServletRequest req) {
		return new ModelAndView("GovernmentServiceProvider");
	}

	@RequestMapping(value = "/governmentServiceProviderhindi")
	public ModelAndView governmentServiceProviderhindi(Model model, HttpServletRequest req) {
		return new ModelAndView("GovernmentServiceProviderhindi");
	}

	@RequestMapping(value = "/onlineverification")
	public ModelAndView applicationservice(Model model, HttpServletRequest req) throws Exception {
		return new ModelAndView("OnlineVerification");
	}

	@RequestMapping(value = "/onlineverificationhi")
	public ModelAndView applicationservicehi(Model model, HttpServletRequest req) throws Exception {
		return new ModelAndView("OnlineVerificationhi");
	}

	@RequestMapping(value = "/transactionservice")
	public ModelAndView tokenVerification(Model model, HttpServletRequest req) throws Exception {
		String transId = req.getParameter("transId");
		int langCode = Integer.parseInt(req.getParameter("langCode"));
		logger.debug("tokenVerification, TransactionID :" + transId);

		String URL = "https://emitraapp.rajasthan.gov.in/webServicesRepository/getTokenVerify";
		try {
			Bill tr = encryptDecryptService.tokenVerficationDetails(URL, transId);
			req.setAttribute("transactionDate", tr.getTransactionDate());
			req.setAttribute("RECEIPTNO", tr.getrECEIPTNO());
			req.setAttribute("TRANSACTIONID", tr.getTransactionId());
			req.setAttribute("AMT", tr.getaMT());
			req.setAttribute("TRANSACTIONSTATUS", tr.gettRANSACTIONSTATUS());
			req.setAttribute("MSG", tr.getmSG());
			req.setAttribute("DEPTNAME", tr.getdEPTNAME());
			req.setAttribute("SRVNAME", tr.getsRVNAME());

		} catch (Exception e) {
			logger.error("BillController, Exception in tokenVerification ::" + e.getMessage());
			/* e.printStackTrace(); */
		}
		if (langCode == 0)
			return new ModelAndView("transactionDetails");
		else
			return new ModelAndView("transactionDetailsHi");
	}

	@RequestMapping(value = "/onlinever")
	public ModelAndView onlineverify(Model model, HttpServletRequest req) throws Exception {
		logger.info("onlineverify");

		String methodName = "onlineverify";

		String serviceId = req.getParameter("serviceId");
		String tokenNo = req.getParameter("token");
		logger.debug("Online verification, ServiceID:::" + serviceId + " tokenNo :::" + tokenNo);
		String URL = "https://emitraapp.rajasthan.gov.in/webServicesRepository/getMetaDataDetailsForCertificateEmitra";

		try {
			EncrptDesryptDataService service = new EncrptDesryptDataService();

			String param = "SRV_ID=" + serviceId + "&TOKEN_NO=" + tokenNo;
			String requestMethod = "POST";
			String response = service.getPOSTResponse(requestMethod, methodName, param, "", URL);

			logger.debug("Online verification, response json :" + response);
			JSONParser parser = new JSONParser();

			JSONObject json = (JSONObject) parser.parse(response);
			String Approved_Date = (String) json.get("Approved_Date");
			String Status = (String) json.get("Status");
			String Permanent_Address = (String) json.get("Permanent_Address");
			String Digitally_Signed = (String) json.get("Digitally_Signed");
			String Father_Name = (String) json.get("Father_Name");
			String Date_Of_Birth = (String) json.get("Date_Of_Birth");
			String Applicant_Name = (String) json.get("Applicant_Name");
			String Approved_And_Signed_By = (String) json.get("Approved_And_Signed_By");
			String Mother_Name = (String) json.get("Mother_Name");
			String ISAVAILABLE = (String) json.get("ISAVAILABLE");
			String TOKEN_NO = (String) json.get("TOKEN_NO");
			String Current_Address = (String) json.get("Current_Address");

			req.setAttribute("Approved_Date", Approved_Date);
			req.setAttribute("Status", Status);
			req.setAttribute("Permanent_Address", Permanent_Address);
			req.setAttribute("Digitally_Signed", Digitally_Signed);
			req.setAttribute("Father_Name", Father_Name);
			req.setAttribute("Date_Of_Birth", Date_Of_Birth);
			req.setAttribute("Applicant_Name", Applicant_Name);
			req.setAttribute("Approved_And_Signed_By", Approved_And_Signed_By);
			req.setAttribute("Mother_Name", Mother_Name);
			req.setAttribute("ISAVAILABLE", ISAVAILABLE);
			req.setAttribute("TOKEN_NO", TOKEN_NO);
			req.setAttribute("Current_Address", Current_Address);
		} catch (Exception e) {
			logger.error("BillController, Exception in onlineverify: " + e.getMessage());
			/* e.printStackTrace(); */
		}
		return new ModelAndView("OnlineVerify");
	}

	@RequestMapping(value = "/discomservice", method = RequestMethod.POST)
	public String discomservice(Model model, Bill bill, Discom dis1, HttpServletRequest req) throws SQLException {
		bill.setSsoID(Login.SSOID);
		int flag = 0;
		int langCode = Integer.parseInt(req.getParameter("langCode"));
		EncrptDesryptDataService encryptService = new EncrptDesryptDataService();

		String strToEncrypt = "{'SRVID':'" + bill.getServiceProviderID() + "','searchKey':'" + dis1.getK_Number()
				+ "','SSOID':'" + Login.SSOID + "'}";

		if (bill.getBillEmail() == null || bill.getBillEmail().equals(""))
			bill.setBillEmail("helpdesk.emitra@rajasthan.gov.in");

		String encryptData = null;
		String encryptDetails = null;
		List<Discom> discomInformation = null;
		try {
			encryptData = encryptService.sendPostForEncryptData(strToEncrypt);
		} catch (Exception e) {
			logger.error("BillController,Exception in discomservice for getting encrypt data :" + e.getMessage());
			encryptData = null;
			/* e.printStackTrace(); */
		}

		logger.debug("encryptData  :" + encryptData);
		if (encryptData != null) {
			try {
				encryptDetails = encryptService.getDiscomInformationData(encryptData);
			} catch (Exception e) {
				encryptDetails = null;
				logger.error("BillController, Exception in getting discom information :" + e.getMessage());
				/* e.printStackTrace(); */
			}
		}
		logger.debug("discom information : " + encryptDetails);

		try {
			discomInformation = encryptDecryptService.sendPostForDiscomDecryptData(encryptDetails, bill, dis1);
		} catch (Exception e) {
			logger.error("BillController, Exception  sendPostForDiscomDecrptData:" + e.getMessage());
			/* e.printStackTrace(); */
		}
		logger.debug("discomInformation list size :: " + discomInformation.size());

		/****************************************************************************************************************************/

		String transactionId = "";
		double d = 0.0;
		int billamount = 0;
		if (discomInformation.size() > 0) {
			d = Double.parseDouble(discomInformation.get(0).getTotalAmount().trim().equalsIgnoreCase("NA") ? "0"
					: discomInformation.get(0).getTotalAmount());

			transactionId = discomInformation.get(0).getTransactionId();
			int x = (int) d;
			int y = x % 10;
			double z = d - x;
			if (y == 0) {
				if (z > 0)
					x = x + 1;
			} else {

				switch (y) {

				case 1:
					x = x + 9;
					break;
				case 2:
					x = x + 8;
					break;
				case 3:
					x = x + 7;
					break;
				case 4:
					x = x + 6;
					break;
				case 5:
					x = x + 5;
					break;
				case 6:
					x = x + 4;
					break;
				case 7:
					x = x + 3;
					break;
				case 8:
					x = x + 2;
					break;
				case 9:
					x = x + 1;
					break;
				}
			}
			billamount = x;
			flag = 1;
		}

		logger.debug("Billinfo, billamount : " + billamount + " transactionId : " + transactionId + " ActualAmountt :: "
				+ d + " serviceProverId : " + bill.getServiceProviderID());
		Bill infoBill = new Bill();
		if (transactionId != null && !transactionId.equals("")) {
			try {

				infoBill.setBillAmount(Integer.toString(billamount));
				infoBill.setTransactionId(transactionId);
				infoBill.setBillActualAmount(Double.toString(d));
				infoBill.setServiceProviderID(bill.getServiceProviderID());
				infoBill.setServiceProviderName(bill.getServiceProviderName());
				billService.updateBillInformation(infoBill);
			} catch (Exception e) {
				logger.error("BillController, Exception in Discom details updateBillInformation : " + e.getMessage());
				/* e.printStackTrace(); */
			}
		} else {
			logger.error("No " + bill.getServiceProviderName() + " details found for updateBillInformation.");
		}

		if (flag == 1) {
			req.setAttribute("details", discomInformation);
			req.setAttribute("infobill", infoBill);
			if (langCode == 0)
				return "Discomdetails";
			else
				return "DiscomDetailsHi";
		} else {
			if (langCode == 0)
				return "redirect:noDetailsFound";
			else
				return "redirect:noDetailsFoundHi";
		}
		/*******************************************************************************************************************************/
	}

	@RequestMapping(value = "/discom", method = RequestMethod.POST)
	public String discom(Model model, Bill bill) {
		model.addAttribute("serviceProvider", bill);
		return "DiscomSearchKey";
	}

	@RequestMapping(value = "/discomHi", method = RequestMethod.POST)
	public String discomHi(Model model, Bill bill) {
		model.addAttribute("serviceProvider", bill);
		return "DiscomSearchKeyHi";
	}

	@RequestMapping(value = "/returnindex")
	public String returnindex(HttpServletRequest req) {

		return "redirect:serviceProviderInfo";
	}

	@RequestMapping(value = "/governmentservice11")
	public ModelAndView govtservice1(Model model, HttpServletRequest req) {
		req.setAttribute("serviceID", req.getParameter("serviceID"));
		return new ModelAndView("birth_death");
	}

	@RequestMapping(value = "/governmentservicehindi11")
	public ModelAndView govtservice1hindi(Model model, HttpServletRequest req) {
		req.setAttribute("serviceID", req.getParameter("serviceID"));
		return new ModelAndView("birth_deathhindi");
	}

	@RequestMapping(value = "/governmentservices12")
	public ModelAndView govtservice2(Model model, HttpServletRequest req) {
		String sID = req.getParameter("serviceID");
		String ssID = req.getParameter("subServiceID");
		String serviceName = req.getParameter("serviceName");
		req.setAttribute("serviceName", serviceName);
		req.setAttribute("serviceID", sID);
		req.setAttribute("subServiceID", ssID);
		req.setAttribute("certificateDetails", "Domicile");
		return new ModelAndView("GovernmentService");
	}

	@RequestMapping(value = "/governmentservicesHi12")
	public ModelAndView govtservice2Hindi(Model model, HttpServletRequest req) {
		String sID = req.getParameter("serviceID");
		String ssID = req.getParameter("subServiceID");
		String serviceName = req.getParameter("serviceName");
		req.setAttribute("serviceName", serviceName);
		req.setAttribute("serviceID", sID);
		req.setAttribute("subServiceID", ssID);
		req.setAttribute("certificateDetails", "Domicile");
		return new ModelAndView("GovernmentServiceHi");
	}

	@RequestMapping(value = "/phed", method = RequestMethod.POST)
	public String phed(Model model, Bill bill) {
		model.addAttribute("serviceProvider", bill);
		return "PhedSearchKey";
	}

	@RequestMapping(value = "/phedHi", method = RequestMethod.POST)
	public String phedhi(Model model, Bill bill) {
		model.addAttribute("serviceProvider", bill);
		return "PhedSearchKeyHi";
	}

	@RequestMapping(value = "/phedservice", method = RequestMethod.POST)
	public String phedservice(Model model, Bill bill, Phed ph, HttpServletRequest req) throws SQLException {
		bill.setSsoID(Login.SSOID);
		int flag = 0;
		int langCode = Integer.parseInt(req.getParameter("langCode"));
		EncrptDesryptDataService encryptService = new EncrptDesryptDataService();

		String strToEncrypt = "{'SRVID':'" + bill.getServiceProviderID() + "','searchKey':'" + ph.getWbill_Number()
				+ "','SSOID':'" + Login.SSOID + "'}";

		if (null == bill.getBillEmail() || bill.getBillEmail().equals(""))
			bill.setBillEmail("helpdesk.emitra@rajasthan.gov.in");

		String encryptData = null;
		String encryptDetails = null;
		List<Phed> phedInformationlist = null;
		try {
			encryptData = encryptService.sendPostForEncryptData(strToEncrypt);
		} catch (Exception e) {
			logger.error("BillController, Exception in phedservice for getting encrypt data :" + e.getMessage());
			encryptData = null;
			/* e.printStackTrace(); */
		}

		logger.debug("phedservice, encryptData  :" + encryptData);
		if (encryptData != null) {
			try {
				encryptDetails = encryptService.getPhedInformationData(encryptData);
			} catch (Exception e) {
				logger.error("BillController, Exception in getPhedInformationData: " + e.getMessage());
				encryptDetails = null;
				/* e.printStackTrace(); */
			}
		}
		logger.debug("phedservice, encryptDetails : " + encryptDetails);

		try {
			phedInformationlist = encryptDecryptService.sendPostForPhedDecryptData(encryptDetails, bill, ph);
		} catch (Exception e) {
			logger.error("BillController, Exception phedservice  :" + e.getMessage());
			/* e.printStackTrace(); */
		}
		logger.debug("phedservice, phedInformationlist " + phedInformationlist.size());

		/****************************************************************************************************************************/

		String transactionId = "";
		double d = 0.0;
		int billamount = 0;
		if (phedInformationlist.size() > 0) {
			d = Double.parseDouble(phedInformationlist.get(0).getBillAmount().trim().equalsIgnoreCase("NA") ? "0"
					: phedInformationlist.get(0).getBillAmount());

			transactionId = phedInformationlist.get(0).getTranBillId();
			int x = (int) d;
			int y = x % 10;
			double z = d - x;
			if (y == 0) {
				if (z > 0)
					x = x + 1;
			} else {

				switch (y) {

				case 1:
					x = x + 9;
					break;
				case 2:
					x = x + 8;
					break;
				case 3:
					x = x + 7;
					break;
				case 4:
					x = x + 6;
					break;
				case 5:
					x = x + 5;
					break;
				case 6:
					x = x + 4;
					break;
				case 7:
					x = x + 3;
					break;
				case 8:
					x = x + 2;
					break;
				case 9:
					x = x + 1;
					break;
				}
			}
			billamount = x;
			flag = 1;
		}
		logger.debug("Billinfo, billamount : " + billamount + " transactionId : " + transactionId + " ActualAmountt :: "
				+ d + " serviceProverId : " + bill.getServiceProviderID());

		Bill infoBill = new Bill();
		try {
			infoBill.setBillAmount(Integer.toString(billamount));
			infoBill.setTransactionId(transactionId);
			infoBill.setBillActualAmount(Double.toString(d));
			infoBill.setServiceProviderID(bill.getServiceProviderID());
			billService.updateBillInformation(infoBill);
		} catch (Exception e) {
			logger.error("BillController, Exception in Phed details updateBillInformation : " + e.getMessage());
			/* e.printStackTrace(); */
		}

		if (flag == 1) {
			req.setAttribute("details", phedInformationlist);
			req.setAttribute("infobill", infoBill);
			if (langCode == 0)
				return "Pheddetails";
			else
				return "PheddetailsHi";
		} else {
			if (langCode == 0)
				return "redirect:noDetailsFound";
			else
				return "redirect:noDetailsFoundHi";
		}
		/*******************************************************************************************************************************/
	}

	@RequestMapping(value = "/CertificateService", method = RequestMethod.POST)
	public ModelAndView birthCertificateService(Model model, HttpServletRequest req, CertificateInfo certificateInfo)
			throws IOException, JSONException, org.json.simple.parser.ParseException, InterruptedException {

		String serviceName = certificateInfo.getServiceName();
		String rno = certificateInfo.getRegistrationNo();
		String certificateDetails = certificateInfo.getCertificateDetails();
		int langCode = certificateInfo.getLangCode();
		int flag = 0;
		int differ = certificateInfo.getCertificateValue();
		int year = certificateInfo.getYear();
		String serviceID = certificateInfo.getServiceID();
		String subServiceID = certificateInfo.getSubServiceID();
		String requestUrl = "";
		logger.debug("CertificateService, registrationNo :" + rno + " CertificateValue :" + differ + " year :" + year
				+ " certificateDetails :" + certificateDetails);

		if (differ == 0)
			requestUrl = "https://emitraapp.rajasthan.gov.in/webServicesRepository/getPehchanCertificateURLWithEmitra?"
					+ "registrationNumber=" + rno.trim() + "&year=" + year + "&event=1";
		else if (differ == 1)
			requestUrl = "https://emitraapp.rajasthan.gov.in/webServicesRepository/getPehchanCertificateURLWithEmitra?"
					+ "registrationNumber=" + rno.trim() + "&year=" + year + "&event=2";
		else
			requestUrl = "https://emitraapp.rajasthan.gov.in/webServicesRepository/getPehchanCertificateURLWithEmitra?"
					+ "registrationNumber=" + rno.trim() + "&year=" + year + "&event=3";

		logger.debug("CertificateService, requestUrl :" + requestUrl);

		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// optional default is GET
			connection.setRequestMethod("GET");

			// add request header
			connection.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				logger.debug("CertificateService, response code : " + responseCode + " due to API server error");
				flag = 1;
			} else {
				logger.debug("CertificateService, response code : " + responseCode);

				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuffer jsonString = new StringBuffer();
				/*
				 * String line; while ((line = br.readLine()) != null) {
				 * jsonString.append(line); }
				 */

				for (String inputLine = br.readLine(); inputLine != null; inputLine = br.readLine()) {
					jsonString.append(inputLine);
				}

				String s = new String(jsonString);
				String content = "";

				JSONParser parser = new JSONParser();
				JSONObject json;
				json = (JSONObject) parser.parse(s);

				String certiStatusCode = (String) json.get("STATUS_CODE");
				logger.debug("CertificateService, certiStatusCode :" + certiStatusCode + " Message "
						+ (String) json.get("MESSAGE"));

				if (!certiStatusCode.equals("200"))
					flag = 1;
				else {
					content = (String) json.get("URL");
					req.setAttribute("url", content);
					logger.debug("CertificateService, Show Certificate URL with urlOfPrinting :" + content);

					if (null == content || content.equals(""))
						flag = 1;

					if (flag == 0) {
						json = billService.downloadfile(json, rno, req);
						String downloadstatus = (String) json.get("STATUS_CODE");
						if (downloadstatus.equalsIgnoreCase("ApiException")) {
							flag = 1;
							logger.debug("CertificateService Service :: Error in downloading::"
									+ (String) json.get("ERROR_MESSAGE"));
						} else {
							String arr[] = ((String) json.get("URL")).split("urlOfPrinting=");
							req.setAttribute("url", (String) json.get("URL"));
							logger.debug("CertificateService Service :: urlOfPrinting::" + arr[1]);
							req.setAttribute("url1", arr[1]);
						}
					}
				}
			}
		} catch (Exception e) {
			flag = 1;
			logger.error("BillController, CertificateService, Exception msg :: " + e.getMessage());
			/* e.printStackTrace(); */
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("IO Exception");
			}
		}

		if (flag == 0) {

			String certificateTransID = certificateService.insertCertificateDetails(certificateInfo);
			req.setAttribute("transactionID", certificateTransID);
			req.setAttribute("serviceID", serviceID);
			req.setAttribute("subServiceID", subServiceID);
			req.setAttribute("serviceName", serviceName);

			req.setAttribute("certificateType", certificateDetails);
			req.setAttribute("certificateYear", year);
			req.setAttribute("token", rno);
			Thread.sleep(10000);
			if (langCode == 0)
				return new ModelAndView("birthCertificatePdf");
			else
				return new ModelAndView("birthCertificatePdfHi");
		} else {
			if (langCode == 0)
				return new ModelAndView("noDetailsFound");
			else
				return new ModelAndView("noDetailsFoundHi");
		}
	}

	@RequestMapping(value = "/deathservice")
	public ModelAndView deathservice(Model model, HttpServletRequest req) {
		String sID = req.getParameter("serviceID");
		String ssID = req.getParameter("subServiceID");
		String serviceName = req.getParameter("serviceName");
		req.setAttribute("serviceName", serviceName);
		req.setAttribute("serviceID", sID);
		req.setAttribute("subServiceID", ssID);
		req.setAttribute("differ", 1);
		req.setAttribute("certificateDetails", "Death");
		return new ModelAndView("birthCertificate");
	}

	@RequestMapping(value = "/deathservicehi")
	public ModelAndView deathservicehi(Model model, HttpServletRequest req) {
		String sID = req.getParameter("serviceID");
		String ssID = req.getParameter("subServiceID");
		String serviceName = req.getParameter("serviceName");
		req.setAttribute("serviceName", serviceName);
		req.setAttribute("serviceID", sID);
		req.setAttribute("subServiceID", ssID);
		req.setAttribute("differ", 1);
		req.setAttribute("certificateDetails", "Death");
		return new ModelAndView("birthCertificateHi");
	}

	@RequestMapping(value = "/marriageservice")
	public ModelAndView marriageservice(Model model, HttpServletRequest req) {
		String sID = req.getParameter("serviceID");
		String ssID = req.getParameter("subServiceID");
		String serviceName = req.getParameter("serviceName");
		req.setAttribute("serviceName", serviceName);
		req.setAttribute("serviceID", sID);
		req.setAttribute("subServiceID", ssID);
		req.setAttribute("differ", 2);
		req.setAttribute("certificateDetails", "Marriage");
		return new ModelAndView("birthCertificate");
	}

	@RequestMapping(value = "/marriageservicehi")
	public ModelAndView marriageservicehi(Model model, HttpServletRequest req) {
		String sID = req.getParameter("serviceID");
		String ssID = req.getParameter("subServiceID");
		String serviceName = req.getParameter("serviceName");
		req.setAttribute("serviceName", serviceName);
		req.setAttribute("serviceID", sID);
		req.setAttribute("subServiceID", ssID);
		req.setAttribute("differ", 2);
		req.setAttribute("certificateDetails", "Marriage");
		return new ModelAndView("birthCertificateHi");
	}

	@RequestMapping(value = "/birthservice", method = RequestMethod.POST)
	public ModelAndView birthservice(Model model, HttpServletRequest req) {
		String sID = req.getParameter("serviceID");
		String ssID = req.getParameter("subServiceID");
		String serviceName = req.getParameter("serviceName");
		req.setAttribute("serviceID", sID);
		req.setAttribute("subServiceID", ssID);
		req.setAttribute("serviceName", serviceName);
		req.setAttribute("differ", 0);
		req.setAttribute("certificateDetails", "Birth");
		return new ModelAndView("birthCertificate");
	}

	@RequestMapping(value = "/birthservicehi")
	public ModelAndView birthservicehi(Model model, HttpServletRequest req) {
		String sID = req.getParameter("serviceID");
		String ssID = req.getParameter("subServiceID");
		String serviceName = req.getParameter("serviceName");
		req.setAttribute("serviceID", sID);
		req.setAttribute("subServiceID", ssID);
		req.setAttribute("serviceName", serviceName);
		req.setAttribute("differ", 0);
		req.setAttribute("certificateDetails", "Birth");
		return new ModelAndView("birthCertificateHi");
	}

	@RequestMapping(value = "/videoconferncing")
	public ModelAndView videoconferncing() {
		new VedioConference().getVedioConference();
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/paymentByCard")
	public ModelAndView CardpaymentChecksum(Bill bill, HttpServletRequest req) throws Exception {

		if (StringUtils.isBlank(bill.getTransactionId())) {
			logger.error("TransactionId is empty.");
			return null;
		}
		if (bill.getYesorno() == 1)
			billService.updatePaymentMode(bill.getTransactionId(), yesCardMerchantCode);
		else
			billService.updatePaymentMode(bill.getTransactionId(), noCardMerchantCode);

		String seString = "";
		if (bill.getYesorno() == 1)
			seString = yesCardMerchantCode + "|" + bill.getTransactionId() + "|" + bill.getBillAmount() + "|"
					+ yesCardCheckSum;
		else
			seString = noCardMerchantCode + "|" + bill.getTransactionId() + "|" + bill.getBillAmount() + "|"
					+ noCardCheckSum;
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(seString.getBytes());

		byte byteData[] = md.digest();

		Formatter formatter = new Formatter();
		try {
			int j = byteData.length;
			for (int i = 0; i < j; i++) {
				byte b = byteData[i];

				formatter.format("%02x", new Object[] { Byte.valueOf(b) });
			}
			String encData = cardPaymentEncDetails(bill, formatter.toString());
			req.setAttribute("enc", encData);

			if (bill.getYesorno() == 1)
				req.setAttribute("merchant", yesCardMerchantCode);
			else
				req.setAttribute("merchant", noCardMerchantCode);

			req.setAttribute("flag", bill.getYesorno());

		} catch (Exception e) {
			logger.error("BillController, Exception in CardpaymentChecksum: " + e.getMessage());
			/* e.printStackTrace(); */
		} finally {
			formatter.close();
		}
		return new ModelAndView("CardPayment");
	}

	public String cardPaymentEncDetails(Bill bill, String checkSum) throws Exception {
		String encryptedString = "";
		String decryptedString = "";
		String merchantCode = "";
		String username = bill.getName() == null ? "" : bill.getName().replaceAll("[^a-zA-Z\\s]", "");

		if (!username.equals("") && username.length() > 50) {
			username = username.substring(0, 49);
		}
		if (bill.getYesorno() == 1)
			merchantCode = yesCardMerchantCode;
		else
			merchantCode = noCardMerchantCode;

		String toBeEncryptedString = "{\"MERCHANTCODE\": \"" + merchantCode + "\"," + "\"PRN\": \""
				+ bill.getTransactionId() + "\"," + "\"REQTIMESTAMP\": \"" + bill.getCreatedDate() + "\","
				+ "\"PURPOSE\": \"Online purchase\"," + "\"AMOUNT\": \"" + bill.getBillAmount() + "\","
				+ "\"SUCCESSURL\": \"http://localhost:1000/KioskService/index\","
				+ "\"FAILUREURL\": \"http://localhost:1000/KioskService/index\","
				+ "\"CANCELURL\": \"http://localhost:1000/KioskService/index\"," + "\"USERNAME\": \"" + username + "\","
				+ "\"USERMOBILE\": \"" + bill.getBillMobileNo() + "\"," + "\"USEREMAIL\": \"" + bill.getBillEmail()
				+ "\"," + "\"UDF1\": \"" + bill.getServiceProviderID() + "\"," + "\"UDF2\": \"" + bill.getLangCode()
				+ "\"," + "\"UDF3\": \"sample 3\"," + "\"OFFICECODE\": \"HQ\","
				+ "\"REVENUEHEAD\": \"ProductFee-100.00|MerchantComm-26.00\"," + "\"CHECKSUM\": \"" + checkSum + "\"}";

		logger.debug("cardPaymentEncDetails, Original String = " + toBeEncryptedString);
		if (bill.getYesorno() == 1) {
			encryptedString = encryptCardDetails(yesCardEncryptionKey, toBeEncryptedString); // Encrypting the string
			decryptedString = decryptCardDetails(yesCardEncryptionKey, encryptedString); // Decrypting the string
		} else {
			encryptedString = encryptCardDetails(noCardEncryptionKey, toBeEncryptedString); // Encrypting the string
			decryptedString = decryptCardDetails(noCardEncryptionKey, encryptedString); // Decrypting the string
		}
		logger.debug("cardPaymentEncDetails, Encrypted String = " + encryptedString);
		logger.debug("cardPaymentEncDetails, Decrypted String = " + decryptedString);

		return encryptedString;
	}

	public String encryptCardDetails(String key, String toBeEncryptString) {
		if (toBeEncryptString == null) {
			throw new IllegalArgumentException("To be encrypt string must not be null");
		}
		try {
			MessageDigest md = MessageDigest.getInstance(SHA256);
			byte[] byteData = Arrays.copyOf(md.digest(key.getBytes("UTF-8")), 16);

			SecretKeySpec secretKey = new SecretKeySpec(byteData, AES);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(byteData);

			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(ENCRYPT_MODE, secretKey, ivParameterSpec);
			return Base64.encodeBase64String(cipher.doFinal(toBeEncryptString.getBytes("UTF-8")));
		} catch (Exception ex) {
			// Handle exception here
			logger.error("BillController, Exception in encryptCardDetails : " + ex.getMessage());
			return null;
		}
	}

	public String decryptCardDetails(String key, String toBeDecryptString) {
		if (toBeDecryptString == null) {
			throw new IllegalArgumentException("To be decrypt string must not be null");
		}
		try {
			MessageDigest md = MessageDigest.getInstance(SHA256);
			byte[] byteData = Arrays.copyOf(md.digest(key.getBytes("UTF-8")), 16);

			SecretKeySpec secretKey = new SecretKeySpec(byteData, AES);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(byteData);

			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(DECRYPT_MODE, secretKey, ivParameterSpec);
			return new String(cipher.doFinal(Base64.decodeBase64(toBeDecryptString)));
		} catch (Exception ex) {
			// Handle exception here
			logger.error("BillController, Exception in decryptCardDetails : " + ex.getMessage());
			return null;
		}
	}

	public String backToBackCardTrans(String amt, String trnsid, String serviceId, String merchantCode,
			String transcationId, String PAYMENTMODE) {

		logger.debug("backToBackTrans, trnsid :" + trnsid + "amount :" + amt + "service id::" + serviceId);
		String backToBackTransStatus = null;
		String backToBackTransactionID = null;

		if (StringUtils.isBlank(trnsid)) {
			logger.error("TransactionId is empty.");
			return null;
		}

		String EMITRATIMESTAMP = null;

		try {
			String backToBackurl = billService.getbackToBackurl(amt, trnsid, serviceId,
					"card:" + merchantCode + ":" + transcationId);
			logger.debug("backToBackTrans, BackToBackurl: " + backToBackurl);

			EncrptDesryptDataService eds = new EncrptDesryptDataService();

			String encryptedB2BUrl = eds.sendPostForEncryptData(backToBackurl);
			logger.debug("backToBackTrans, BackTOBackResponse: " + encryptedB2BUrl);

			String backtoBackResponse = eds.sendPostForBacktoBack(encryptedB2BUrl);
			logger.debug("backToBackTrans, backtoBackUrl :" + backtoBackResponse);

			String decryptB2BResponse = eds.sendPostForDecryptData(backtoBackResponse);
			logger.debug("backToBackTrans, decriptBackTOBackResponse :" + decryptB2BResponse);

			org.json.JSONObject json = new org.json.JSONObject(decryptB2BResponse);
			backToBackTransStatus = json.optString("TRANSACTIONSTATUS");
			backToBackTransactionID = json.optString("TRANSACTIONID");

			if (StringUtils.isBlank(PAYMENTMODE)) {
				PAYMENTMODE = "CARD";
			}

			try {
				billService.saveBackToBackTransaction(decryptB2BResponse, PAYMENTMODE);
				org.json.JSONObject json1 = new org.json.JSONObject(decryptB2BResponse);
				EMITRATIMESTAMP = json1.optString("EMITRATIMESTAMP");

				String emitraTransactionFlag = null;
				Bill transactionDetails = new Bill();
				transactionDetails.setBackToBackTransactionStatus(backToBackTransStatus);
				transactionDetails.setBackToBackTransactionID(backToBackTransactionID);

				if (backToBackTransStatus.equalsIgnoreCase("SUCCESS"))
					emitraTransactionFlag = "1";
				else
					emitraTransactionFlag = "0";

				billService.updateTransactionDetails(emitraTransactionFlag, transactionDetails, trnsid);

			} catch (Exception ex) {
				logger.error(
						"Caught an exception while inserting backToBackCardtransaction details in the DB for transactionId: "
								+ trnsid,
						ex);

				if (backToBackTransStatus.equalsIgnoreCase("SUCCESS")) {
					localLogSyncer.writeToLocalLogs(backToBackTransactionID, trnsid, serviceId, amt);
				}
				backToBackTransStatus = null;
			}

		} catch (Exception ex) {
			logger.error("Caught an exception while processing emitra backToBackCardTransaction for transactionId: "
					+ trnsid, ex);
		}
		backToBackTransStatus += "##" + EMITRATIMESTAMP;
		return backToBackTransStatus;
	}

	@RequestMapping(value = "/autologin", method = RequestMethod.GET)
	public ResponseEntity<Boolean> getLoginStatus(HttpServletRequest req, HttpServletResponse res) {
		String webPath = System.getProperty("catalina.base") + "\\webapps" + File.separator;
		webPath = webPath.replace("\\", File.separator);
		File file = new File(webPath + "AutoLoginKiosk.txt");
		Boolean flagExist = false;
		if (file.exists()) {
			flagExist = true;
		}
		if (flagExist) {
			// BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
				String MACHINEID;
				while ((MACHINEID = br.readLine()) != null)

					if (MACHINEID != null && MACHINEID != "") {
						String SSOID = billService.getSsoId(MACHINEID);
						if (SSOID != null && SSOID != "") {
							String response = getMachineAuthStatus(MACHINEID, SSOID);
							if (response.equals("TRUE")) {
								String SESSIONID = req.getSession().getId();
								String CHECKSUM = getMd5HashDetails(SSOID + "|" + SESSIONID);
								boolean ISVALID = true;
								Login login = new Login();
								login.setCHECKSUM(CHECKSUM);
								login.setISVALID(ISVALID);
								login.setSESSIONID(SESSIONID);
								login.setSSOID(SSOID);
							} else {
								flagExist = false;
							}
						} else {
							flagExist = false;
						}
					}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				logger.error("BillController, Exception in getLoginStatus : " + e.getMessage());
				/* e.printStackTrace(); */
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("BillController, Exception in getLoginStatus : " + e.getMessage());
				/* e.printStackTrace(); */
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("BillController, Exception in getLoginStatus : " + e.getMessage());
					/* e.printStackTrace(); */
				}
			}
		}
		return new ResponseEntity<Boolean>(flagExist, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getSSOLoginResult(HttpServletRequest req, HttpServletResponse response) {
		return new ModelAndView("Login");
	}

	// SSO ID login Response method
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String getSSOLoginResponseInfornation(HttpServletRequest req, HttpServletResponse response) {

		String SSOID = null;
		String SESSIONID = null;
		String CHECKSUM = null;
		boolean ISVALID = false;

		String SSOAUTH_RESPONSE = req.getParameter("SSOAUTH_RESPONSE");
		logger.debug("getSSOLoginResponseInfornation, SSOAUTH_RESPONSE :" + SSOAUTH_RESPONSE);
		String resp = new String(Base64.decodeBase64(SSOAUTH_RESPONSE.getBytes()));
		logger.debug("getSSOLoginResponseInfornation, SSOAUTH_RESPONSE JSON " + resp);
		JSONParser parser = new JSONParser();
		JSONObject json;
		try {

			json = (JSONObject) parser.parse(resp);
			SSOID = (String) json.get("SSOID");
			SESSIONID = (String) json.get("SESSIONID");
			ISVALID = (boolean) json.get("ISVALID");
			CHECKSUM = (String) json.get("CHECKSUM");

			// ISVALID is false, let's keep the logs for further verification of data
			if (ISVALID == false) {

				logger.info(
						"getSSOLoginResponseInfornation, We received ISVALID parameter as a false so process will not go further :");
				logger.debug("getSSOLoginResponseInfornation, SSOID = " + SSOID + " , SESSIONID = " + SESSIONID
						+ " , CHECKSUM= " + CHECKSUM);

				return "redirect:login";
			}

			String generatedMD5CheckSum = getMd5HashDetails(SSOID + "|" + SESSIONID);

			logger.debug("getSSOLoginResponseInfornation, generatedMD5CheckSum :" + generatedMD5CheckSum);

			if (CHECKSUM.equals(generatedMD5CheckSum) == true) {
				InetAddress ipAddr = InetAddress.getLocalHost();

				// Storing response data to login property for further use
				Login login = new Login();
				login.setCHECKSUM(CHECKSUM);
				login.setISVALID(ISVALID);
				login.setSESSIONID(SESSIONID);
				login.setSSOID(SSOID);
				login.setMACHINEIPADDRESS(ipAddr.getHostAddress());

				// All validations are seems correct so redirecting to machine SSO
				// Authentication page
				return "redirect:machinessoauth";
			}
		} catch (Exception e) {

			logger.error("getSSOLoginResponseInfornation , exception in paring json info ," + e.getMessage());
			logger.debug("getSSOLoginResponseInfornation, SSOI= " + SSOID + " , SESSIONID= " + SESSIONID
					+ " , CHECKSUM= " + CHECKSUM + " , ISVALID= " + ISVALID);

			/* e.printStackTrace(); */
		}
		return "redirect:login";
	}

	public String getMd5HashDetails(String plainText) {
		Formatter formatter = new Formatter();
		String mD5CheckSum = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte byteData[] = md.digest();
			int j = byteData.length;
			for (int i = 0; i < j; i++) {
				byte b = byteData[i];
				formatter.format("%02x", new Object[] { Byte.valueOf(b) });
			}
			mD5CheckSum = formatter.toString();
		} catch (Exception e) {
			formatter.close();
			logger.error("BillController, Exception in getting GetMd5Hash : " + e.getMessage());
			/* e.printStackTrace(); */
		} finally {
			formatter.close();
		}

		return mD5CheckSum;
	}

	@RequestMapping(value = "/jamabandiRecord")
	public ModelAndView jamabandiRecord(HttpServletRequest req, Bill bill) {

		String pageName = bill.getServiceProviderPage();

		String requestUrl = "https://api.sewadwaar.rajasthan.gov.in/app/live/eSeva/Prod/Service/District/Master/getDetails?State_Code=08&User_Name=ws_eseva&Password=ws_eseva%23789&client_id=96f2ea05-8742-401d-aa42-ec269f8a71c0";
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				logger.debug(pageName + ", response code : " + responseCode + " due to API server error");
			} else {
				logger.debug(pageName + ", response code : " + responseCode);

				/*
				 * BufferedReader br = new BufferedReader(new
				 * InputStreamReader(connection.getInputStream())); StringBuffer jsonString =
				 * new StringBuffer(); String line; while ((line = br.readLine()) != null) {
				 * jsonString.append(line); }
				 */

				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuffer jsonString = new StringBuffer();
				for (String inputLine = br.readLine(); inputLine != null; inputLine = br.readLine()) {
					jsonString.append(inputLine);
				}

				String s = new String(jsonString);
				logger.debug(pageName + ", getting JSON details ::" + s);
				JSONParser parser = new JSONParser();
				JSONObject json;
				String s1[] = s.split("\"DocumentElement\":");
				String s2[] = s1[1].split("\"DistrictMaster\":");
				List<Jamabandi> list = new ArrayList<Jamabandi>();

				for (int i = 1; i < s2.length; i++) {
					String s4, s5 = null;
					Jamabandi jama = new Jamabandi();
					if (i == (s2.length - 1)) {
						json = (JSONObject) parser.parse(s2[i].substring(0, s2[i].length() - 5));
						if (json.containsKey("distno_census")) {
							s4 = new String(((String) json.get("distname")).getBytes(), "UTF-8");
							s5 = new String(((String) json.get("distno_census")));
							jama.setDistname(s4);
							jama.setDistrict_code(s5);
							list.add(jama);
						}
					} else {
						json = (JSONObject) parser.parse(s2[i].substring(0, s2[i].length() - 1));
						if (json.containsKey("distno_census")) {
							s4 = new String(((String) json.get("distname")).getBytes(), "UTF-8");
							s5 = new String(((String) json.get("distno_census")));
							jama.setDistname(s4);
							jama.setDistrict_code(s5);
							list.add(jama);
						}
					}
				}
				req.setAttribute("list", list);
			}
		} catch (Exception e) {
			logger.error(pageName + ", Exception msg :: " + e.getMessage());
			/* e.printStackTrace(); */
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("IO Exception");
			}
		}
		return new ModelAndView(pageName);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTehsilByDistrict", method = RequestMethod.GET)
	public void getTehsilByDistrict(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String distcode = req.getParameter("distcode").trim();
		JSONArray array = new JSONArray();
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		String requestUrl = "https://api.sewadwaar.rajasthan.gov.in/app/live/eSeva/Prod/Service/Tehsil/Master/getDetails?District_Code="
				+ distcode
				+ "&User_Name=ws_eseva&Password=ws_eseva%23789&client_id=96f2ea05-8742-401d-aa42-ec269f8a71c0";
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				logger.debug("getTehsilByDistrict, response code : " + responseCode + " due to API server error");
			} else {
				logger.debug("getTehsilByDistrict, response code : " + responseCode);
				/*
				 * BufferedReader br = new BufferedReader(new
				 * InputStreamReader(connection.getInputStream())); StringBuffer jsonString =
				 * new StringBuffer(); String line; while ((line = br.readLine()) != null) {
				 * jsonString.append(line); }
				 */
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuffer jsonString = new StringBuffer();
				for (String inputLine = br.readLine(); inputLine != null; inputLine = br.readLine()) {
					jsonString.append(inputLine);
				}

				String s = new String(jsonString);
				JSONParser parser = new JSONParser();
				JSONObject json;
				String s1[] = s.split("\"DocumentElement\":");
				String s2[] = s1[1].split("\"tehsilmaster\":");
				for (int i = 1; i < s2.length; i++) {
					String s4, s5 = null;

					if (i == (s2.length - 1)) {
						json = (JSONObject) parser.parse(s2[i].substring(0, s2[i].length() - 5));
						if (json.containsKey("tehsilno_census")) {
							s4 = new String(((String) json.get("tehsilname")).getBytes(), "UTF-8");
							s5 = new String(((String) json.get("tehsilno_census")));
							org.json.JSONObject jobj = new org.json.JSONObject();
							jobj.put("tehsilname", s4);
							jobj.put("tehsilno_census", s5);
							array.add(jobj);
						}
					} else {
						json = (JSONObject) parser.parse(s2[i].substring(0, s2[i].length() - 1));
						if (json.containsKey("tehsilno_census")) {
							s4 = new String(((String) json.get("tehsilname")).getBytes(), "UTF-8");
							s5 = new String(((String) json.get("tehsilno_census")));
							org.json.JSONObject jobj = new org.json.JSONObject();
							jobj.put("tehsilname", s4);
							jobj.put("tehsilno_census", s5);
							array.add(jobj);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("BillController,Exception in getTehsilByDistrict : " + e.getMessage());
			/* e.printStackTrace(); */
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("IO Exception");
			}
		}
		try {
			pw = res.getWriter();
		} finally {
			pw.print(array);
			pw.flush();
			pw.close();
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getVillageByTehsil", method = RequestMethod.GET)
	public void getVillageByTehsil(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String distcode = req.getParameter("distcode").trim();
		String tehsilcode = req.getParameter("tehsilcode").trim();
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		JSONArray array = new JSONArray();
		String requestUrl = "https://api.sewadwaar.rajasthan.gov.in/app/live/eSeva/Prod/Service/Village/Master/getDetails?District_Code="
				+ distcode + "&Tehsil_Code=" + tehsilcode
				+ "&User_Name=ws_eseva&Password=ws_eseva%23789&client_id=96f2ea05-8742-401d-aa42-ec269f8a71c0";
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				logger.debug("getVillageByTehsil, response code : " + responseCode + " due to API server error");
			} else {
				logger.debug("getVillageByTehsil, response code : " + responseCode);

				/*
				 * BufferedReader br = new BufferedReader(new
				 * InputStreamReader(connection.getInputStream())); StringBuffer jsonString =
				 * new StringBuffer(); String line; while ((line = br.readLine()) != null) {
				 * jsonString.append(line); }
				 */
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuffer jsonString = new StringBuffer();
				for (String inputLine = br.readLine(); inputLine != null; inputLine = br.readLine()) {
					jsonString.append(inputLine);
				}

				String s = new String(jsonString);
				JSONParser parser = new JSONParser();
				JSONObject json;
				String s1[] = s.split("\"DocumentElement\":");
				String s2[] = s1[1].split("\"VillageMaster\":");
				for (int i = 1; i < s2.length; i++) {
					if (i == (s2.length - 1)) {
						json = (JSONObject) parser.parse(s2[i].substring(0, s2[i].length() - 5));
						if (json.containsKey("villageid_census")) {
							org.json.JSONObject jobj = new org.json.JSONObject();

							jobj.put("VILLAGENAME", new String(((String) json.get("VILLAGENAME")).getBytes(), "UTF-8"));
							jobj.put("VILLAGEID", new String(((String) json.get("villageid_census"))));
							array.add(jobj);
						}
					} else {
						json = (JSONObject) parser.parse(s2[i].substring(0, s2[i].length() - 1));
						if (json.containsKey("villageid_census")) {
							org.json.JSONObject jobj = new org.json.JSONObject();
							jobj.put("VILLAGENAME", new String(((String) json.get("VILLAGENAME")).getBytes(), "UTF-8"));
							jobj.put("VILLAGEID", new String(((String) json.get("villageid_census"))));
							array.add(jobj);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("BillController, Exception in getVillageByTehsilException, getVillageByTehsilException : "
					+ e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("IO Exception");
			}
		}
		try {
			pw = res.getWriter();
		} finally {
			pw.print(array);
			pw.flush();
			pw.close();
		}
	}

	@RequestMapping(value = "/jamabandiservice", method = RequestMethod.POST)
	public ModelAndView jamabandiservice(Model model, Bill bill, Jamabandi jam, HttpServletRequest req)
			throws SQLException {

		bill.setServiceProviderID(2354);
		bill.setSsoID(Login.SSOID);
		int langCode = Integer.parseInt(req.getParameter("langCode"));
		EncrptDesryptDataService encryptService = new EncrptDesryptDataService();
		logger.debug("district" + jam.getDistrict_code() + "tehsil" + jam.getTehsil_code() + "village code"
				+ jam.getVillage_code() + "khasra" + jam.getKhasra());
		String uname = "ws_eseva";
		String pwd = "ws_eseva%23789";
		String clientid = "96f2ea05-8742-401d-aa42-ec269f8a71c0";
		String strToEncrypt = "https://api.sewadwaar.rajasthan.gov.in/app/live/eSeva/Prod/Service/ROR/getDetails?District_Code="
				+ jam.getDistrict_code().trim() + "&Tehsil_Code=" + jam.getTehsil_code().trim() + "&Village_Code="
				+ jam.getVillage_code().trim() + "&Khasra=" + jam.getKhasra().trim() + "&User_Name=" + uname.trim()
				+ "&Password=" + pwd.trim() + "&client_id=" + clientid.trim() + "";

		String encryptData = null;

		try {
			encryptData = encryptService.sendPostForJamabandiData(strToEncrypt);
			logger.debug("jamabandiservice details ::" + encryptData);
			req.setAttribute("details", encryptData);
			logger.debug("jamabandiservice, encryptData  :" + encryptData);
			String s[] = encryptData.split("\"ROR\":");

			JSONParser parser = new JSONParser();
			JSONObject json;

			List<Jamabandi> list = new ArrayList<Jamabandi>();
			for (int i = 1; i < s.length; i++) {
				json = (JSONObject) parser.parse(s[i].substring(0, s[i].length() - 1));
				Jamabandi jama = new Jamabandi();
				if (json.get("id") instanceof String)
					jama.setId((String) json.get("id"));
				else
					jama.setId("");
				if (json.get("rowOrder") instanceof String)
					jama.setRowOrder((String) json.get("rowOrder"));
				else
					jama.setRowOrder("");
				if (json.get("khata") instanceof String)
					jama.setKhata((String) json.get("khata"));
				else
					jama.setKhata("");
				if (json.get("oldkhata") instanceof String)
					jama.setOldkhata((String) json.get("oldkhata"));
				else
					jama.setOldkhata("");
				if (json.get("OwnerName") instanceof String) {
					String s4 = new String(((String) json.get("OwnerName")).getBytes(), "UTF-8");
					jama.setOwnerName(s4);
				} else
					jama.setOwnerName("");
				if (json.get("khasra") instanceof String)
					jama.setKhasra((String) json.get("khasra"));
				else
					jama.setKhasra("");
				if (json.get("khasraarea") instanceof String)
					jama.setKhasraarea((String) json.get("khasraarea"));
				else
					jama.setKhasraarea("");
				if (json.get("SoilName") instanceof String) {
					String s5 = new String(((String) json.get("SoilName")).getBytes(), "UTF-8");
					jama.setSoilName(s5);
				} else
					jama.setSoilName("");
				if (json.get("SoilArea") instanceof String)
					jama.setSoilArea((String) json.get("SoilArea"));
				else
					jama.setSoilArea("");
				if (json.get("SoilRev") instanceof String)
					jama.setSoilRev((String) json.get("SoilRev"));
				else
					jama.setSoilRev("");
				if (json.get("SoilRate") instanceof String)
					jama.setSoilRate((String) json.get("SoilRate"));
				else
					jama.setSoilRate("");
				if (json.get("areaunit") instanceof String)
					jama.setAreaunit((String) json.get("areaunit"));
				else
					jama.setAreaunit("");
				if (json.get("KhasraIrrigation") instanceof String)
					jama.setKhasraIrrigation((String) json.get("KhasraIrrigation"));
				else
					jama.setKhasraIrrigation("");
				if (json.get("KhasraPlot") instanceof String)
					jama.setKhasraPlot((String) json.get("KhasraPlot"));
				else
					jama.setKhasraPlot("");
				list.add(jama);
			}
			req.setAttribute("details", list);
		} catch (Exception e) {

			logger.error("BillController, Exception in jamabandiservice for getting encrypt data :" + e.getMessage());
			/* e.printStackTrace(); */
		}

		if (langCode == 0)
			return new ModelAndView("JamabandiDetails");
		else
			return new ModelAndView("JamabandiDetailsHi");
	}

	@RequestMapping(value = "/examserviceDetails", method = RequestMethod.POST)
	public ModelAndView examServiceDetails(HttpServletRequest req, HttpServletResponse response, Jamabandi jama,
			Bill bill) {

		String requestUrl = "https://api.sewadwaar.rajasthan.gov.in/app/live/eMitra/Prod/bserws/api/Certificate?ExamYear="
				+ jama.getExamYear() + "&ExamName=SEC&ExamType=MAIN&RollNo=" + jama.getRollNo()
				+ "&client_id=96f2ea05-8742-401d-aa42-ec269f8a71c0";
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				logger.debug("examServiceDetails, response code : " + responseCode + " due to API server error");
			} else {
				logger.debug("examServiceDetails, response code : " + responseCode);

				/*
				 * BufferedReader br = new BufferedReader(new
				 * InputStreamReader(connection.getInputStream())); StringBuffer jsonString =
				 * new StringBuffer(); String line; while ((line = br.readLine()) != null) {
				 * jsonString.append(line); }
				 */
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuffer jsonString = new StringBuffer();
				for (String inputLine = br.readLine(); inputLine != null; inputLine = br.readLine()) {
					jsonString.append(inputLine);
				}

				String s = new String(jsonString);
				logger.debug("examServiceDetails, getting JSON details ::" + s);
				JSONParser parser = new JSONParser();
				JSONObject json;
				json = (JSONObject) parser.parse(s);
				if (((String) json.get("Description")).equals("DATA NOT FOUND") || ((String) json.get("Description"))
						.equals("ERROR: Invalid object name 'BSER-2018.dbo.M2018_SEC_MAIN'."))
					req.setAttribute("MSG", "Invalid roll no,exam year");
				else {
					req.setAttribute("examyear", (Long) json.get("ExamYear"));
					req.setAttribute("examname", (String) json.get("ExamName"));
					req.setAttribute("ExamType", (String) json.get("ExamType"));
					req.setAttribute("RollNo", (String) json.get("RollNo"));
					req.setAttribute("StudentName", (String) json.get("StudentName"));
					req.setAttribute("Gender", (String) json.get("Gender"));
					req.setAttribute("FatherName", (String) json.get("FatherName"));
					req.setAttribute("MotherName", (String) json.get("MotherName"));
					String dob = (String) json.get("DateofBirth");
					if (dob != null && !dob.equals("")) {
						if (dob.length() == 6) {
							dob = dob.substring(0, 2) + "/" + dob.substring(2, 4) + "/" + dob.substring(4, 6);
						} else if (dob.length() == 8) {
							dob = dob.substring(0, 2) + "/" + dob.substring(2, 4) + "/" + dob.substring(4, 8);
						} else {
							logger.error("examServiceDetails, Json Error Message :: " + dob.length()
									+ " is Not in proper Format.");
						}
					}
					req.setAttribute("DateofBirth", dob);
					String doi = (String) json.get("DateofIssue");
					if (doi != null && !doi.equals("")) {
						if (doi.length() == 6) {
							doi = doi.substring(0, 2) + "/" + doi.substring(2, 4) + "/" + doi.substring(4, 6);
						} else if (doi.length() == 8) {
							doi = doi.substring(0, 2) + "/" + doi.substring(2, 4) + "/" + doi.substring(4, 8);
						} else {
							logger.error("examServiceDetails, Json Error Message :: " + doi.length()
									+ " is Not in proper Format.");
						}
					}
					req.setAttribute("DateofIssue", doi);
					req.setAttribute("Status", (String) json.get("Status"));
				}
			}
		} catch (Exception e) {
			logger.error("BillController, examServiceDetails, Exception msg :: " + e.getMessage());
			/* e.printStackTrace(); */
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("IO Exception");
			}
		}

		if (bill.getLangCode() == 0)
			return new ModelAndView("ExamDetails");
		else
			return new ModelAndView("ExamDetailsHi");
	}

	@RequestMapping(value = "/checkVersion", method = RequestMethod.POST)
	@ResponseBody
	public String checkVersionStatus(HttpServletRequest request, HttpServletResponse response) {

		InputStream input = null;
		String flag = "false";

		try {
			Properties properties = new Properties();
			input = this.getClass().getResourceAsStream("../versionDetail.properties");
			properties.load(input);
			String versionNo = properties.getProperty("version_no");
			String versionDate = properties.getProperty("version_date");
			logger.debug("Version Properties , Version No : " + versionNo + " Version Date : " + versionDate);
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				versionNo = new String(decoder.decodeBuffer(versionNo));
				versionDate = new String(decoder.decodeBuffer(versionDate));
			} catch (IOException e) {

				logger.error("BillController, Exception in checkVersionStatus : " + e.getMessage());
				/* e.printStackTrace(); */
			}

			double version_number = Double.parseDouble(versionNo);
			logger.debug("Version Properties , Version No : " + version_number + " Version Date : " + versionDate);
			LatestVersionDetails latestVersion = billService.versionCheck();

			if (version_number == latestVersion.getVersionNo() && (versionDate.equals(latestVersion.getCreatedDate())))
				flag = "true";
			else
				flag = flag + "##" + latestVersion.getVersionNo();

		} catch (Exception e) {
			logger.error("BillController, Exception in checkVersion for getting Connection " + e.getMessage());
			/* e.printStackTrace(); */
		}
		return flag;
	}

	@RequestMapping(value = "/machinessoauth")
	public ModelAndView machineIntegration(Model model, HttpServletRequest req) {
		try {
			MachineAuth machi = billService.getMachineAuthenticationDetails(Login.SSOID);
			String machineId = machi.getMachineId();
			logger.debug("MachineId : " + machineId);
			req.setAttribute("MachineId", machineId);

		} catch (Exception e) {
			logger.error("BillController, Exception in machineIntegration: " + e.getMessage());
			/* e.printStackTrace(); */
		}
		return new ModelAndView("MachineSSOAuth");
	}

	@RequestMapping(value = "/validateEmitraPlusMachine", method = RequestMethod.GET)
	public String getMachineAuth(HttpServletRequest req, HttpServletResponse response1) {
		return "redirect:login";

	}

	@RequestMapping(value = "/validateEmitraPlusMachine", method = RequestMethod.POST)
	public @ResponseBody ModelAndView getMachineAuth(HttpServletRequest req, MachineAuth mach1,
			HttpServletResponse response1) throws IOException {

		String SERIALNO = mach1.getMachineId();
		String SSOID = Login.SSOID;

		logger.debug("getMachineAuth, MachineId :" + SERIALNO + " SSOID :" + SSOID);

		String result = "";

		try {

			Properties properties = new Properties();
			InputStream input = this.getClass().getResourceAsStream("../versionDetail.properties");
			properties.load(input);
			String versionNo = properties.getProperty("version_no");
			logger.debug("Version Properties , Version No : " + versionNo);
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				versionNo = new String(decoder.decodeBuffer(versionNo));
			} catch (IOException e) {
				logger.error("BillController, Exception in getMachineAuth : " + e.getMessage());
				/* e.printStackTrace(); */
			}

			String response = getMachineAuthStatus(SERIALNO, SSOID);

			// System.out.println("getMachineAuth, response :" + trans+" for SSOID :
			// "+SSOID+" and machine id : "+mach1.getMachineId());
			logger.debug(
					"response :" + response + " for SSOID : " + SSOID + " and machine id : " + mach1.getMachineId());

			if (response.equals("TRUE")) {
				try {
					result = "index";
					billService.saveMachineAuth(mach1, SSOID, versionNo);
					try {
						String content = mach1.getMachineId();
						String webPath = System.getProperty("catalina.base") + "\\webapps" + File.separator;
						webPath = webPath.replace("\\", File.separator);
						File file = new File(webPath + "AutoLoginKiosk.txt");

						if (!file.exists()) {
							file.createNewFile();
						}

						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);

						bw.write(content);
						//bw.close();
					} catch (Exception e) {
						logger.debug("BillController, Error : " + e.getMessage());
						// System.out.println(e);
					}
					finally {
						try {

							br.close();
						} catch (IOException e) {
							logger.error("IO Exception");
						}
					}
				} catch (Exception e1) {
					result = "MachineSSOAuth";
					req.setAttribute("BillController, error", "2" + e1.getMessage());
					/* e.printStackTrace(); */
				}
			} else {
				result = "MachineSSOAuth";
				req.setAttribute("error", "1");
			}

		} catch (Exception e2) {
			result = "MachineSSOAuth";
			req.setAttribute("error", "2");
			logger.error("BillController, getMachineAuth, Exception :" + e2.getMessage());
			/* e.printStackTrace(); */
		}

		return new ModelAndView(result);

	}

	public String getMachineAuthStatus(String SERIALNO, String SSOID) {

		EncrptDesryptDataService service = new EncrptDesryptDataService();
		String URL = "https://emitraapp.rajasthan.gov.in/webServicesRepository/validateEmitraPlusMachine";
		String methodName = "getMachineAuth";
		String urlParameters = "SERIALNO=" + SERIALNO + "&SSOID=" + SSOID;
		String requestMethod = "POST";
		String response = service.getPOSTResponse(requestMethod, methodName, urlParameters, "", URL);

		return response;
	}

	// digitSecure
	@RequestMapping(value = "/paymentByBiomatric")
	public ModelAndView biomatricPaymentChecksum(Bill bill, HttpServletRequest req) throws Exception {

		if (StringUtils.isBlank(bill.getTransactionId())) {
			logger.error("TransactionId is empty.");
			return null;
		}

		billService.updatePaymentMode(bill.getTransactionId(), digitSecureMerchantCode);

		String seString = "";
		if (bill.getYesorno() == 3)
			seString = digitSecureMerchantCode + "|" + bill.getTransactionId() + "|" + bill.getBillAmount() + "|"
					+ digitSecureCheckSum;

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(seString.getBytes());

		byte byteData[] = md.digest();

		Formatter formatter = new Formatter();
		try {
			int j = byteData.length;
			for (int i = 0; i < j; i++) {
				byte b = byteData[i];

				formatter.format("%02x", new Object[] { Byte.valueOf(b) });
			}

			String encData = digitSecurePaymentEncDetails(bill, formatter.toString());
			req.setAttribute("enc", encData);

			if (bill.getYesorno() == 3)
				req.setAttribute("merchant", digitSecureMerchantCode);

			req.setAttribute("flag", bill.getYesorno());

		} catch (Exception e) {
			logger.error("BillController, Exception in DigitSecurepaymentChecksum: " + e.getMessage());
			/* e.printStackTrace(); */
		} finally {
			formatter.close();
		}
		return new ModelAndView("DigitSecurePayment");
	}

	public String digitSecurePaymentEncDetails(Bill bill, String checkSum) throws Exception {

		String encryptedString = "";
		String decryptedString = "";
		String merchantCode = "";
		String username = bill.getName() == null ? "" : bill.getName().replaceAll("[^a-zA-Z\\s]", "");

		if (!username.equals("") && username.length() > 50) {
			username = username.substring(0, 49);
		}
		if (bill.getYesorno() == 3)
			merchantCode = digitSecureMerchantCode;

		String toBeEncryptedString = "{\"MERCHANTCODE\": \"" + merchantCode + "\"," + "\"PRN\": \""
				+ bill.getTransactionId() + "\"," + "\"REQTIMESTAMP\": \"" + bill.getCreatedDate() + "\","
				+ "\"PURPOSE\": \"Online purchase\"," + "\"AMOUNT\": \"" + bill.getBillAmount() + "\","
				+ "\"SUCCESSURL\": \"http://localhost:1000/KioskService/index\","
				+ "\"FAILUREURL\": \"http://localhost:1000/KioskService/index\","
				+ "\"CANCELURL\": \"http://localhost:1000/KioskService/index\"," + "\"USERNAME\": \"" + username + "\","
				+ "\"USERMOBILE\": \"" + bill.getBillMobileNo() + "\"," + "\"USEREMAIL\": \"" + bill.getBillEmail()
				+ "\"," + "\"UDF1\": \"" + bill.getServiceProviderID() + "\"," + "\"UDF2\": \"" + bill.getLangCode()
				+ "\"," + "\"UDF3\": \"sample 3\"," + "\"OFFICECODE\": \"HQ\","
				+ "\"REVENUEHEAD\": \"ProductFee-100.00|MerchantComm-26.00\"," + "\"CHECKSUM\": \"" + checkSum + "\"}";

		logger.debug("digitSecurePaymentEncDetails, Original String = " + toBeEncryptedString);
		if (bill.getYesorno() == 3) {
			encryptedString = encryptCardDetails(digitSecureEncryptionKey, toBeEncryptedString); // Encrypting the
																									// string
			decryptedString = decryptCardDetails(digitSecureEncryptionKey, encryptedString); // Decrypting the string
		}

		logger.debug("digitSecurePaymentEncDetails, Encrypted String = " + encryptedString);
		logger.debug("digitSecurePaymentEncDetails, Decrypted String = " + decryptedString);
		return encryptedString;
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public ModelAndView getCardDetailsAfterPayment(HttpServletRequest req) {
		String cardDecryptedDetails = "";
		String seString = "";
		String merchantcode = req.getParameter("MERCHANTCODE");
		String encdata = req.getParameter("ENCDATA");
		String status = req.getParameter("STATUS");
		logger.debug("getCardDetailsAfterPayment, merchantcode : " + merchantcode + " status : " + status);

		if (merchantcode.equalsIgnoreCase(yesCardMerchantCode))
			cardDecryptedDetails = decryptCardDetails(yesCardEncryptionKey, encdata);

		else if (merchantcode.equalsIgnoreCase(noCardMerchantCode))
			cardDecryptedDetails = decryptCardDetails(noCardEncryptionKey, encdata);

		else if (merchantcode.equalsIgnoreCase(digitSecureMerchantCode))
			cardDecryptedDetails = decryptCardDetails(digitSecureEncryptionKey, encdata);

		logger.debug("getCardDetailsAfterPayment, cardDecryptedDetails : " + cardDecryptedDetails);
		boolean isCheckSumFlag = false;
		String UDF2 = "";
		JSONParser parser = new JSONParser();
		JSONObject json;
		String backToBackStatus = "";
		try {
			json = (JSONObject) parser.parse(cardDecryptedDetails);
			String MERCHANTCODE = (String) json.get("MERCHANTCODE");
			String REQTIMESTAMP = (String) json.get("REQTIMESTAMP");
			String PRN = (String) json.get("PRN");
			String RPPTXNID = (String) json.get("RPPTXNID");
			String AMOUNT = (String) json.get("AMOUNT");
			String RPPTIMESTAMP = (String) json.get("RPPTIMESTAMP");
			String STATUS = (String) json.get("STATUS");
			String RESPONSECODE = (String) json.get("RESPONSECODE");
			String RESPONSEMESSAGE = (String) json.get("RESPONSEMESSAGE");
			String PAYMENTMODE = (String) json.get("PAYMENTMODE");
			String PAYMENTMODEBID = (String) json.get("PAYMENTMODEBID");
			String PAYMENTMODETIMESTAMP = (String) json.get("PAYMENTMODETIMESTAMP");
			String PAYMENTAMOUNT = (String) json.get("PAYMENTAMOUNT");
			String UDF1 = (String) json.get("UDF1");
			UDF2 = (String) json.get("UDF2");
			String UDF3 = (String) json.get("UDF3");
			String CHECKSUM = (String) json.get("CHECKSUM");

			CardPaymentDetails cpd = new CardPaymentDetails();
			cpd.setMERCHANTCODE(MERCHANTCODE);
			cpd.setREQTIMESTAMP(REQTIMESTAMP);
			cpd.setPRN(PRN);
			cpd.setRPPTXNID(RPPTXNID);
			cpd.setAMOUNT(AMOUNT);
			cpd.setRPPTIMESTAMP(RPPTIMESTAMP);
			cpd.setSTATUS(STATUS);
			cpd.setRESPONSECODE(RESPONSECODE);
			cpd.setRESPONSEMESSAGE(RESPONSEMESSAGE);
			cpd.setPAYMENTMODE(PAYMENTMODE);
			cpd.setPAYMENTMODEBID(PAYMENTMODEBID);
			cpd.setPAYMENTMODETIMESTAMP(PAYMENTMODETIMESTAMP);
			cpd.setPAYMENTAMOUNT(PAYMENTAMOUNT);
			cpd.setUDF1(UDF1);
			cpd.setUDF2(UDF2);
			cpd.setUDF3(UDF3);
			cpd.setCHECKSUM(CHECKSUM);

			billService.saveCardPaymentDetails(cpd);
			List<Bill> billdetails = billService.getPaymentInfo(PRN);
			String amt = billdetails.get(0).getBillAmount();
			String serviceid = String.valueOf(billdetails.get(0).getServiceProviderID());
			String transactionid = billdetails.get(0).getTransactionId();

			logger.debug("getCardDetailsAfterPayment, amt :" + amt + " serviceid :" + serviceid + " transactionid :"
					+ transactionid);
			if (!STATUS.equalsIgnoreCase("SUCCESS") && !RESPONSECODE.equals("00") && PAYMENTMODE.equals("NA")) {
				logger.info("getCardDetailsAfterPayment, Card Payment Error Occured");
				req.setAttribute("RPPFLAG", "1");
				req.setAttribute("RPPMSG", RESPONSEMESSAGE);
				req.setAttribute("RPPSERVICEID", serviceid);
				req.setAttribute("RPPTXNID", RPPTXNID);
				req.setAttribute("RPPPRN", PRN);
				backToBackStatus = "";
			} else {

				if (merchantcode.equalsIgnoreCase(yesCardMerchantCode))
					seString = MERCHANTCODE + "|" + PRN + "|" + RPPTXNID + "|" + AMOUNT + "|" + yesCardCheckSum;

				else if (merchantcode.equalsIgnoreCase(noCardMerchantCode))
					seString = MERCHANTCODE + "|" + PRN + "|" + RPPTXNID + "|" + AMOUNT + "|" + noCardCheckSum;

				else if (merchantcode.equalsIgnoreCase(digitSecureMerchantCode))
					seString = MERCHANTCODE + "|" + PRN + "|" + RPPTXNID + "|" + AMOUNT + "|" + digitSecureCheckSum;

				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(seString.getBytes());

				byte byteData[] = md.digest();
				String generatedCheckSum = "";
				Formatter formatter = new Formatter();
				try {
					int j = byteData.length;
					for (int i = 0; i < j; i++) {
						byte b = byteData[i];

						formatter.format("%02x", new Object[] { Byte.valueOf(b) });
					}
					generatedCheckSum = formatter.toString();

				} catch (Exception e) {
					generatedCheckSum = "";
					/* e.printStackTrace(); */
					//formatter.close();
					logger.error("getCardDetailsAfterPayment Exception in generating checksum, generatedCheckSum :"
							+ generatedCheckSum + " exception :" + e.getMessage());
				} finally {
					formatter.close();
				}

				logger.debug("getCardDetailsAfterPayment, generatedCheckSum : " + generatedCheckSum);

				if (generatedCheckSum.equals(CHECKSUM)) {
					isCheckSumFlag = true;
				}

				logger.debug("getCardDetailsAfterPayment, isCheckSumFlag : " + isCheckSumFlag + " RPPTXNID: " + RPPTXNID
						+ " STATUS: " + STATUS + " RESPONSECODE: " + RESPONSECODE + ": PAYMENTMODE : " + PAYMENTMODE);
				if (STATUS.equalsIgnoreCase("SUCCESS") && RESPONSECODE.equals("00") && !PAYMENTMODE.equals("NA")
						&& isCheckSumFlag) {
					logger.debug("getCardDetailsAfterPayment, payment status :" + isCheckSumFlag);
					billService.updateTransactionDetails(PRN);

					String result = backToBackCardTrans(amt, transactionid, serviceid, MERCHANTCODE, RPPTXNID,
							PAYMENTMODE);

					String backToBackStatusArr[] = result.split("##");

					backToBackStatus = backToBackStatusArr[0];

					req.setAttribute("details", billdetails);
					req.setAttribute("transID", transactionid);
				}
			}
		} catch (Exception e) {
			backToBackStatus = "";
			logger.error("BillController, Exception in getCardDetailsAfterPayment: " + e.getMessage());
			/* e.printStackTrace(); */
		}
		if (backToBackStatus.equalsIgnoreCase("success")) {
			if (UDF2.equals("0"))
				return new ModelAndView("paymentSuccessful");
			else
				return new ModelAndView("paymentSuccessfulHi");
		} else {
			if (UDF2.equals("0"))
				return new ModelAndView("paymentError");
			else
				return new ModelAndView("paymentErrorHi");
		}
	}

	@RequestMapping(value = "/GrievanceStatusDetails", method = RequestMethod.GET)
	public ModelAndView GrievanceStatusDetails(HttpServletRequest req, @RequestParam("GrievanceId") String gId)
			throws IOException, ServletException {
		String mobileNo = req.getParameter("mobileNo");
		String langCode = req.getParameter("langCode");
		JSONObject json = new JSONObject();
		String requestUrl = "";
		List<grievenceDetails> list = new ArrayList<>();

		try {
			if (gId != "") {
				requestUrl = "http://sampark.rajasthan.gov.in/RESTServices/Grievance.svc/griv/" + gId + "";
			} else {
				requestUrl = "http://sampark.rajasthan.gov.in/RESTServices/Grievance.svc/mob/" + mobileNo + "";
			}
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			connection.setRequestProperty("Content-Type", "application/json;");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			int responseCode = connection.getResponseCode();
			StringBuffer jsonString = new StringBuffer();
			if (responseCode != 200) {
				logger.debug("GrievanceStatusDetails, response code : " + responseCode + " due to API server error");
			} else {
				logger.debug("GrievanceStatusDetails, response code : " + responseCode);

				/*
				 * BufferedReader br = new BufferedReader( new
				 * InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
				 * 
				 * String line; while ((line = br.readLine()) != null) {
				 * jsonString.append(line); }
				 */
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				for (String inputLine = br.readLine(); inputLine != null; inputLine = br.readLine()) {
					jsonString.append(inputLine);
				}

				String hindiStr = StringEscapeUtils.unescapeJava(jsonString.toString());

				if (hindiStr.equals("")) {
					req.setAttribute("VarifyDetailMSG", "empty");
				}
			}
			JSONParser parser = new JSONParser();
			org.json.simple.JSONArray obj = (org.json.simple.JSONArray) parser.parse(jsonString.toString());
			if (mobileNo != "") {
				int length1 = obj.size();
				for (int i = 0; i < length1; i++) {
					grievenceDetails tr = new grievenceDetails();
					json = (JSONObject) obj.get(i);

					// 02180433231565
					if (((String) json.get("Description")).equals("DATA NOT FOUND")
							|| ((String) json.get("Description"))
									.equals("ERROR: Invalid object name 'BSER-2018.dbo.M2018_SEC_MAIN'."))
						req.setAttribute("MSG", "Invalid Mobile no");
					else {
						req.setAttribute("statusMobile", "mobileYes");
						String GrievanceId = (String) json.get("GrievanceId");
						String ComplainStatus = (String) json.get("ComplainStatus");
						String ComplainStatusHindi = (String) json.get("ComplainStatusHindi");
						String ComplainantName = (String) json.get("ComplainantName");
						String Description = (String) json.get("Description");
						String GDate = (String) json.get("GDate");
						String GrievanceArea = (String) json.get("GrievanceArea");
						String OwnerDepartment = (String) json.get("OwnerDepartment");
						String Grievance_status = (String) json.get("Grievance Status");
						String Subject = (String) json.get("Subject");
						String Mobileno = (String) json.get("MobileNo");

						tr.setGrievanceId(GrievanceId);
						tr.setComplainStatus(ComplainStatus);
						tr.setComplainStatusHindi(ComplainStatusHindi);
						tr.setComplainantName(ComplainantName);
						tr.setDescription(Description);
						tr.setgDate(GDate);
						tr.setGrievanceArea(GrievanceArea);
						tr.setOwnerDepartment(OwnerDepartment);
						tr.setGrievanceStatus(Grievance_status);
						tr.setSubject(Subject);
						tr.setMobileNo(Mobileno);
						list.add(tr);
					}
				}
			} else {
				JSONObject json1 = (JSONObject) obj.get(0);
				req.setAttribute("statusGrivence", "grvYes");
				req.setAttribute("GrievanceId", new String(((String) json1.get("GrievanceId")).getBytes(), "UTF-8"));
				req.setAttribute("ComplainStatus",
						new String(((String) json1.get("ComplainStatus")).getBytes(), "UTF-8"));
				req.setAttribute("ComplainStatusHindi",
						new String(((String) json1.get("ComplainStatusHindi")).getBytes(), "UTF-8"));
				req.setAttribute("ComplainantName",
						new String(((String) json1.get("ComplainantName")).getBytes(), "UTF-8"));
				req.setAttribute("Description", new String(((String) json1.get("Description")).getBytes(), "UTF-8"));
				req.setAttribute("GDate", new String(((String) json1.get("GDate")).getBytes(), "UTF-8"));
				req.setAttribute("GrievanceArea",
						new String(((String) json1.get("GrievanceArea")).getBytes(), "UTF-8"));
				req.setAttribute("OwnerDepartment",
						new String(((String) json1.get("OwnerDepartment")).getBytes(), "UTF-8"));
				req.setAttribute("Subject", new String(((String) json1.get("Subject")).getBytes(), "UTF-8"));
			}
		} catch (Exception e) {
			logger.error(
					"BillController, GrievanceStatusDetails, Exception msg :: Data Not Found!Please input valid Details "
							+ e.getMessage());
			/* e.printStackTrace(); */
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("IO Exception");
			}
		}

		req.setAttribute("list", list);

		if (langCode.equals("0")) {
			return new ModelAndView("GrievanceStatus");
		} else {
			return new ModelAndView("GrievanceStatusHi");
		}
	}

	@RequestMapping(value = "/getOtpForMobileVerify1", method = RequestMethod.GET)
	public ModelAndView getOtpForMobileVerify(HttpServletRequest req, @RequestParam("mobileNo") String mobileNo,
			@RequestParam("langCode") String langCode) {

		MobileOTP mobileOTP = new MobileOTP();

		JSONObject responseObj = mobileOTP.sendMessageOnMobile(mobileNo);

		if (responseObj != null && !responseObj.isEmpty()) {
			String OTP = String.valueOf(responseObj.get("OTP"));
			if (OTP != null && !OTP.isEmpty()) {
				req.setAttribute("OTP", OTP);
				req.setAttribute("mobileNo", String.valueOf(responseObj.get("MOBILE_NO")));
				req.setAttribute("MSG", String.valueOf(responseObj.get("MSG")));

			} else {
				req.setAttribute("OTP", "");

			}
		} else {
			req.setAttribute("OTP", "");

		}
		if (responseObj != null && !responseObj.isEmpty()) {
			// localLogSyncer.insertEntryIntoLog(responseObj);
		}

		if (langCode.equals("0")) {
			return new ModelAndView("GrievanceStatus");
		} else {
			return new ModelAndView("GrievanceStatusHi");
		}
	}

	@RequestMapping(value = "/information")
	public ModelAndView information(Model model, HttpServletRequest req) {

		return new ModelAndView("Information");
	}

	@RequestMapping(value = "/rationshopservice")
	public ModelAndView rationShopServiceProvider(Model model, HttpServletRequest req) {
		return new ModelAndView("rationshopservice");
	}

	@RequestMapping(value = "/rationshopserviceHi")
	public ModelAndView rationShopServiceProviderHi(Model model, HttpServletRequest req) {
		return new ModelAndView("rationshopserviceHi");
	}

	@RequestMapping(value = "/kioskinformationservice")
	public ModelAndView kioskInformationServiceProvider(Model model, HttpServletRequest req) {
		return new ModelAndView("kioskinformationservice");
	}

	@RequestMapping(value = "/kioskinformationserviceHi")
	public ModelAndView kioskInformationServiceProviderHi(Model model, HttpServletRequest req) {
		return new ModelAndView("kioskinformationserviceHi");
	}

	@RequestMapping(value = "/socialsecurityservice")
	public ModelAndView socialSecurityServiceProvider(Model model, HttpServletRequest req) {
		return new ModelAndView("socialsecurityservice");
	}

	@RequestMapping(value = "/socialsecurityserviceHi")
	public ModelAndView socialSecurityServiceProviderHi(Model model, HttpServletRequest req) {
		return new ModelAndView("socialsecurityserviceHi");
	}

	@RequestMapping(value = "/naregaservice")
	public ModelAndView naregaServiceProvider(Model model, HttpServletRequest req) {
		return new ModelAndView("naregaservice");
	}

	@RequestMapping(value = "/naregaserviceHi")
	public ModelAndView naregaServiceProviderHi(Model model, HttpServletRequest req) {
		return new ModelAndView("naregaserviceHi");
	}

	@RequestMapping(value = "/employeeservice")
	public ModelAndView employeeServiceProvider(Model model, HttpServletRequest req) {
		return new ModelAndView("employeeservice");
	}

	@RequestMapping(value = "/employeeserviceHi")
	public ModelAndView employeeServiceProviderHi(Model model, HttpServletRequest req) {
		return new ModelAndView("employeeserviceHi");
	}

	@RequestMapping(value = "/bhamasacardservice")
	public ModelAndView bhamasaCardServiceProvider(Model model, HttpServletRequest req) {
		return new ModelAndView("bhamasacardservice");
	}

	@RequestMapping(value = "/bhamasacardserviceHi")
	public ModelAndView bhamasaCardServiceProviderHi(Model model, HttpServletRequest req) {
		return new ModelAndView("bhamasacardserviceHi");
	}

	@RequestMapping(value = "/disableservice")
	public ModelAndView disableServiceProvider(Model model, HttpServletRequest req) {
		return new ModelAndView("disableservice");
	}

	@RequestMapping(value = "/disableserviceHi")
	public ModelAndView disableServiceProviderHi(Model model, HttpServletRequest req) {
		return new ModelAndView("disableserviceHi");
	}

	@RequestMapping(value = "/swasthyabimaservice")
	public ModelAndView swasthyaBimaServiceProvider(Model model, HttpServletRequest req) {
		return new ModelAndView("swasthyabimaservice");
	}

	@RequestMapping(value = "/swasthyabimaserviceHi")
	public ModelAndView swasthyaBimaServiceProviderHi(Model model, HttpServletRequest req) {
		return new ModelAndView("swasthyabimaserviceHi");
	}

	@RequestMapping(value = "/foodsecurityservice")
	public ModelAndView foodSecurityServiceProvider(Model model, HttpServletRequest req) {
		return new ModelAndView("foodsecurityservice");
	}

	@RequestMapping(value = "/foodsecurityserviceHi")
	public ModelAndView foodSecurityServiceProviderHi(Model model, HttpServletRequest req) {
		return new ModelAndView("foodsecurityserviceHi");
	}

}