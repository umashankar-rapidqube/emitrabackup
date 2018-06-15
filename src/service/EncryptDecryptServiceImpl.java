package service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.EncryptDecryptDAO;
import model.Bill;
import model.Discom;
import model.Phed;
import webServicesRepository.utility.EncrptDesryptDataService;

@Service("encryptDecryptService")
public class EncryptDecryptServiceImpl implements EncryptDecryptService {

	private final String decryptURL = "https://emitraapp.rajasthan.gov.in/webServicesRepository/emitraAESDecryption";

	@Autowired
	private EncryptDecryptDAO encryptDecryptDAO;

	static final Logger logger = Logger.getLogger(EncryptDecryptServiceImpl.class);

	@Override
	@Transactional
	public List<Bill> sendPostForDecryptDetail(String encryptDetails, Bill bill) {

		EncrptDesryptDataService service = new EncrptDesryptDataService();
		String methodName = "sendPostForDecryptDetail";
		String requestMethod = "POST";
		String decryptResponse = service.getPOSTResponse(requestMethod, methodName, encryptDetails, "toBeDecrypt",
				decryptURL);
		List<Bill> details = new ArrayList<Bill>();
		logger.debug(methodName + ", SERVICE PROVIDER ID :" + bill.getServiceProviderID());
		logger.debug(methodName + ", API Result:" + decryptResponse);

		switch (bill.getServiceProviderID()) {
		case 1214:
			logger.debug(methodName + ", AIRTEL BACKTOBACK API Result:" + decryptResponse);
			details = encryptDecryptDAO.getAirtelDetails(decryptResponse, bill);
			break;
		case 1220:
			logger.debug(methodName + ", IDEA BACKTOBACK API Result:" + decryptResponse);
			details = encryptDecryptDAO.getIdeaDetails(decryptResponse, bill);
			break;
		case 1219:
			logger.debug(methodName + ", Vodaphone BACKTOBACK API Result:" + decryptResponse);
			details = encryptDecryptDAO.getVodafoneDetails(decryptResponse, bill);
			break;
		case 2575:
			logger.debug(methodName + ", BSNL BACKTOBACK API Result:" + decryptResponse);
			details = encryptDecryptDAO.getBsnlDetails(decryptResponse, bill);
			break;
		}

		return details;
	}

	@Override
	@Transactional
	public List<Discom> sendPostForDiscomDecryptData(String encryptDetails, Bill bill, Discom discom) {

		EncrptDesryptDataService service = new EncrptDesryptDataService();
		String methodName = "sendPostForDiscomDecryptData";
		String requestMethod = "POST";
		String discomDecryptResponse = service.getPOSTResponse(requestMethod, methodName, encryptDetails, "toBeDecrypt",
				decryptURL);

		List<Discom> discomdetail = new ArrayList<Discom>();
		logger.debug(methodName + ", Discom BACKTOBACK API Result:" + discomDecryptResponse);
		logger.debug(methodName + ", SERVICE PROVIDER ID :" + bill.getServiceProviderID());
		discomdetail = encryptDecryptDAO.getDiscomDetails(discomDecryptResponse, bill, discom);

		return discomdetail;
	}

	@Override
	@Transactional
	public List<Phed> sendPostForPhedDecryptData(String encryptDetails, Bill bill, Phed ph) {

		List<Phed> pheddetail = new ArrayList<Phed>();
		EncrptDesryptDataService service = new EncrptDesryptDataService();
		String methodName = "sendPostForPhedDecryptData";
		String requestMethod = "POST";
		String phedDecryptResponse = service.getPOSTResponse(requestMethod, methodName, encryptDetails, "toBeDecrypt",
				decryptURL);
		logger.debug("sendPostForPhedDecryptData, PHED BACKTOBACK API Details : " + phedDecryptResponse.toString());
		logger.debug("sendPostForPhedDecryptData, SERVICE PROVIDER ID :" + bill.getServiceProviderID());
		pheddetail = encryptDecryptDAO.getPhedDetails(phedDecryptResponse, bill, ph);
		return pheddetail;
	}

	@Override
	@Transactional
	public String getGovtServiceDetail(String encryptURL) {

		EncrptDesryptDataService service = new EncrptDesryptDataService();
		String methodName = "getGovtServiceDetail";
		return service.getGETResponse(methodName, encryptURL);
	}

	@Override
	@Transactional
	public String getPDFPrinted(String encryptURL) {

		EncrptDesryptDataService service = new EncrptDesryptDataService();
		String methodName = "getPDFPrinted";
		return service.getGETResponse(methodName, encryptURL);
	}

	@Override
	@Transactional
	public Bill tokenVerficationDetails(String tokenVerfiyURl, String transId) {

		EncrptDesryptDataService service = new EncrptDesryptDataService();
		String methodName = "tokenVerficationDetails";
		String param = "{\"TRANSACTIONID\":\"" + transId + "\",\"MERCHANTCODE\":\"EMITRAPLUS\"}";
		String requestMethod = "POST";
		String tokenVerficationResponse = service.getPOSTResponse(requestMethod, methodName, param, "data",
				tokenVerfiyURl);
		Bill bill = null;

		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(tokenVerficationResponse);
			String transactionDate = (String) json.get("TRANSACTIONDATE");
			String RECEIPTNO = (String) json.get("RECEIPTNO");
			String TRANSACTIONID = (String) json.get("TRANSACTIONID");
			String AMT = (String) json.get("AMT");
			String EMITRATIMESTAMP = (String) json.get("EMITRATIMESTAMP");
			String TRANSACTIONSTATUS = (String) json.get("TRANSACTIONSTATUS");
			String VERIFYSTATUSCODE = (String) json.get("VERIFYSTATUSCODE");
			String MSG = (String) json.get("MSG");
			String DEPTNAME = (String) json.get("DEPTNAME");
			String SRVNAME = (String) json.get("SRVNAME");

			bill = new Bill();
			bill.setTransactionDate(transactionDate);
			bill.setrECEIPTNO(RECEIPTNO);
			bill.setTransactionId(TRANSACTIONID);
			bill.setaMT(AMT);
			bill.seteMITRATIMESTAMP(EMITRATIMESTAMP);
			bill.settRANSACTIONSTATUS(TRANSACTIONSTATUS);
			bill.setvERIFYSTATUSCODE(VERIFYSTATUSCODE);
			bill.setmSG(MSG);
			bill.setdEPTNAME(DEPTNAME);
			bill.setsRVNAME(SRVNAME);

		} catch (Exception e) {
			logger.error(
					"Exception in tokenVerficationDetails while parsing response in Json or Stroing response data :"
							+ e.getMessage());
			/*e.printStackTrace();*/
		}
		return bill;
	}

	@Override
	@Transactional
	public String getAadharNumber(String response) {

		String aadharNumber = null;
		try {

			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(response);
			aadharNumber = (String) json.get("AADHARNUMBER");

		} catch (Exception e) {
			logger.error("EncryptDecryptServiceImpl, Exception in getAadharNumber : "+e.getMessage());
		}

		return aadharNumber;
	}
}