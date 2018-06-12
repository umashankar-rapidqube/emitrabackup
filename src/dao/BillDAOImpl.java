package dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
// import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.Bill;
import model.CardPaymentDetails;
import model.DBServiceResponse;
import model.LatestVersionDetails;
import model.Login;
import model.MachineAuth;
import model.QueryDetails;
import utils.DBHTTPUtils;
import utils.TimeUtils;
import webServicesRepository.utility.EncrptDesryptDataService;

@Repository("billDao")
public class BillDAOImpl implements BillDAO {

	static final Logger logger = Logger.getLogger(BillDAOImpl.class);

	@Autowired
	private DBHTTPUtils dbHTTPUtils;

	public void updateBillInformation(Bill infoBill) {
		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");
			queryDetails.setAutoCommit(false);

			queryDetails.addQueryParams(1, "addPayAmount");
			queryDetails.addQueryParams(2, infoBill.getTransactionId());
			queryDetails.addQueryParams(3, infoBill.getBillAmount());

			DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
			logger.debug("Response status of updateBillInformation:" + serviceResponse.getStatus());
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in updateBillInformation : ", ex);
		}
	}

	@Override
	public Bill getPrintBillDetails(Bill bill) {

		Bill printBillInfo = new Bill();
		logger.debug("getPrintBillDetails, serviceProviderID : " + bill.getServiceProviderID());
		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "showprintreceiptofutility");
			queryDetails.addQueryParams(2, bill.getTransactionId());

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);

			if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
				JSONArray resultSet = serviceResponse.getJsonArray();
				if (null != resultSet && resultSet.length() > 0) {
					org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);

					printBillInfo.setName(rs.optString("consumername"));
					printBillInfo.setBillMobileNo(rs.optString("Mobile"));
					printBillInfo.setBillEmail(rs.optString("Email_ID"));
					printBillInfo.setServiceProviderName(rs.optString("servicename"));
					printBillInfo.setBillAmount(rs.optString("billamount"));
					printBillInfo.setBillActualAmount(rs.optString("payAmount").equals("") ? rs.optString("billamount")	: rs.optString("payAmount"));

					if (bill.getServiceProviderID() == 1223 || bill.getServiceProviderID() == 2354) {
						printBillInfo.setDueDate(rs.optString("Due_Date").equalsIgnoreCase("na") ? "NA"	: rs.optString("Due_Date"));
					} else {
						printBillInfo.setDueDate(rs.optString("Due_Date").equalsIgnoreCase("na") ? "NA" : rs.optString("Due_Date"));
					}

					printBillInfo.setPaymentMode(rs.optString("PaymentMode"));
					printBillInfo.setmSG(rs.optString("MSG"));
				}
			}
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in getPrintBillDetails : ", ex);
		}
		return printBillInfo;
	}
	
	@Override
	public List<Bill> getPaymentInfo(String PRN) {
		List<Bill> list = new ArrayList<Bill>();
		Bill bill1 = new Bill();
		try {
			QueryDetails queryDetails = new QueryDetails("Proc_CardBill");


			queryDetails.addQueryParams(1, "selectcarddetails");
			queryDetails.addQueryParams(2, PRN);

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);

			if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
				JSONArray resultSet = serviceResponse.getJsonArray();
				if (null != resultSet && resultSet.length() > 0) {
					org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
					
					bill1.setName(rs.optString("consumername"));
					bill1.setBillMobileNo(rs.optString("Mobile"));
					bill1.setBillEmail(rs.optString("Email_ID"));
					bill1.setServiceProviderName(rs.optString("servicename"));
					bill1.setBillAmount(rs.optString("billamount"));
					bill1.setBillActualAmount(rs.optString("PAYMENTAMOUNT"));
					bill1.setDueDate("");
					bill1.setPaymentMode(rs.optString("PAYMENTMODE"));
					bill1.setmSG(rs.optString("RESPONSEMESSAGE"));
					
					bill1.setTransactionId(rs.optString("TransId"));
					bill1.setServiceProviderID(Integer.parseInt(rs.optString("ServiceId")));
					
					list.add(bill1);
				}
			}
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in getPaymentInfo : ", ex);
		}
		return list;
	}

	public String getbackToBackurl(String amt, String trnsid, String serviceId, String flag) {

		String revenueHead = null;
		String backToBackurl = null;
		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			// Optimized code
			switch (serviceId) {
			case "1219":
				queryDetails.addQueryParams(1, "selectprintrerceiptVoda");
				break;

			case "1214":
				queryDetails.addQueryParams(1, "selectprintrerceipt");
				break;

			case "1220":
				queryDetails.addQueryParams(1, "selectprintrerceiptIdea");
				break;

			case "1223":
			case "2582":
			case "2615":
			case "2616":
			case "2531":
			case "3018":
				queryDetails.addQueryParams(1, "selectprintrerceiptDiscom");
				break;

			case "2354":
			case "1230":
			case "1693":
				queryDetails.addQueryParams(1, "selectprintrerceiptPhed");
				break;

			case "2575":
				queryDetails.addQueryParams(1, "selectprintrerceiptBSNL");
				break;
				
			case "2878":
				queryDetails.addQueryParams(1, "selectprintrerceiptPUC");
				break;
			}

			queryDetails.addQueryParams(2, trnsid);

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);

			if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
				JSONArray resultSet = serviceResponse.getJsonArray();
				if (null != resultSet && resultSet.length() > 0) {
					org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);

					String transid = rs.optString("TransId");
					String timestamp = rs.optString("gettimestamp");
					String consumerkey = rs.optString("consumerkeyvalue");
					String consumerName = rs.optString("consumername");
					String serviceid = rs.optString("ServiceId");
					String officeCode = rs.optString("officecodevalue");
					String lookupid = rs.optString("lookupid");
					String mobileNumber = rs.optString("MobileNumber") == null ? "" : rs.optString("MobileNumber");
					String emailId = rs.optString("EmailId") == null ? "" : rs.optString("EmailId");
					int COMMTYPE = 1;
					switch (serviceId) {

					case "1214":
						revenueHead = "212-" + amt + "|213-5.00";
						break;

					case "1220":
						revenueHead = "217-" + amt + "|219-5.00";
						break;

					case "1216":
						revenueHead = "214-" + amt + "|215-5.00";
						break;

					case "1219":
						revenueHead = "218-" + amt + "|220-5.00";
						break;

					case "1223":

						if (Integer.parseInt(amt) <= 2000)
							revenueHead = "230-" + amt + "|231-5.00";
						else if (Integer.parseInt(amt) > 2000 && Integer.parseInt(amt) <= 5000)
							revenueHead = "230-" + amt + "|231-8.00";
						else if (Integer.parseInt(amt) > 5000)
							revenueHead = "230-" + amt + "|231-10.00";

						break;
					
					case "2615":
						
						if (Integer.parseInt(amt) <= 2000)
							revenueHead = "1537-" + amt + "|1538-5.00";
						else if (Integer.parseInt(amt) > 2000 && Integer.parseInt(amt) <= 5000)
							revenueHead = "1537-" + amt + "|1538-8.00";
						else if (Integer.parseInt(amt) > 5000)
							revenueHead = "1537-" + amt + "|1538-10.00";
						
						break;
					case "2616":
						
						if (Integer.parseInt(amt) <= 2000)
							revenueHead = "1558-" + amt + "|1540-5.00";
						else if (Integer.parseInt(amt) > 2000 && Integer.parseInt(amt) <= 5000)
							revenueHead = "1558-" + amt + "|1540-8.00";
						else if (Integer.parseInt(amt) > 5000)
							revenueHead = "1558-" + amt + "|1540-10.00";
						
						break;
					case "2582":
						
						if (Integer.parseInt(amt) <= 2000)
							revenueHead = "1477-" + amt + "|1497-5.00";
						else if (Integer.parseInt(amt) > 2000 && Integer.parseInt(amt) <= 5000)
							revenueHead = "1477-" + amt + "|1497-8.00";
						else if (Integer.parseInt(amt) > 5000)
							revenueHead = "1477-" + amt + "|1497-10.00";
						
						break;
					case "3018":
						
						if (Integer.parseInt(amt) <= 2000)
							revenueHead = "1882-" + amt + "|1883-5.00";
						else if (Integer.parseInt(amt) > 2000 && Integer.parseInt(amt) <= 5000)
							revenueHead = "1882-" + amt + "|1883-8.00";
						else if (Integer.parseInt(amt) > 5000)
							revenueHead = "1882-" + amt + "|1883-10.00";
						
						break;
					case "2531":
						
						if (Integer.parseInt(amt) <= 2000)
							revenueHead = "1400-" + amt + "|1401-5.00";
						else if (Integer.parseInt(amt) > 2000 && Integer.parseInt(amt) <= 5000)
							revenueHead = "1400-" + amt + "|1401-8.00";
						else if (Integer.parseInt(amt) > 5000)
							revenueHead = "1400-" + amt + "|1401-10.00";
						
						break;

					case "2354":
						revenueHead = "260-" + amt + "|261-5.00";
						break;
					case "1230":
						revenueHead = "257-" + amt + "|256-5.00";
						break;
					case "1693":
						revenueHead = "464-" + amt + "|488-5.00";
						break;

					case "2575":
						revenueHead = "226-" + amt + "|228-5.00";
						break;
						
					case "2878":
						revenueHead = "877-" + amt + "|878-6.00";
						break;

					}
					logger.debug("getbackToBackurl, ServiceID" + serviceid + "ServiceHEAD" + revenueHead);

					EncrptDesryptDataService eds = new EncrptDesryptDataService();

					String checksumurl = "{\"SSOID\":\"" + Login.SSOID + "\",\"REQUESTID\":\"" + transid
							+ "\",\"REQTIMESTAMP\":\"" + timestamp + "\",\"SSOTOKEN\":\"0\"}";
					logger.debug("getbackToBackurl, checkSUMURL" + checksumurl + "flag, ::" + flag);

					String checksum = eds.sendPostCheckSum(checksumurl);

					int x = flag.indexOf(":");

					if (x != -1) {

						String s[] = flag.split(":");
						String paymentMode = "";

						if (s[1].equalsIgnoreCase("EMITRAPLUSEZETAP")) {
							paymentMode = "571";
						} else if (s[1].equalsIgnoreCase("EMITRAPLUSBILLDESK")) {
							paymentMode = "231";
						} else if (s[1].equalsIgnoreCase("EMITRAPLUSDIGITSECURE")) {
							paymentMode = "671";

						}
						backToBackurl = "{\"MERCHANTCODE\":\"EMITRAPLUS\",\"REQUESTID\":\"" + transid + "\","
								+ "\"REQTIMESTAMP\":\"" + timestamp + "\",\"SERVICEID\":\"" + serviceid
								+ "\",\"SUBSERVICEID\":\"\"," + "\"REVENUEHEAD\":\"" + revenueHead
								+ "\",\"CONSUMERKEY\":\"" + consumerkey + "\"," + "\"LOOKUPID\":\"" + lookupid + "\","
								+ "\"CONSUMERNAME\":\"" + consumerName + "\",\"COMMTYPE\":\"" + COMMTYPE + "\",\"SSOID\":\""
								+ Login.SSOID + "\",\"OFFICECODE\":\"" + officeCode + "\","
								+ "\"SSOTOKEN\":\"0\",\"CHECKSUM\":\"" + checksum + "\"," + "\"PAYMODE\":\""
								+ paymentMode + "\",\"BANKREFNUMBER\":\"" + s[2] + "\"," + "\"MOBILENUMBER\":\""
								+ mobileNumber + "\",\"EMAILID\":\"" + emailId + "\"}";
					} else
						backToBackurl = "{\"MERCHANTCODE\":\"EMITRAPLUS\",\"REQUESTID\":\"" + transid + "\","
								+ "\"REQTIMESTAMP\":\"" + timestamp + "\",\"SERVICEID\":\"" + serviceid
								+ "\",\"SUBSERVICEID\":\"\"," + "\"REVENUEHEAD\":\"" + revenueHead
								+ "\",\"CONSUMERKEY\":\"" + consumerkey + "\"," + "\"LOOKUPID\":\"" + lookupid + "\","
								+ "\"CONSUMERNAME\":\"" + consumerName + "\",\"COMMTYPE\":\"" + COMMTYPE + "\",\"SSOID\":\""
								+ Login.SSOID + "\",\"OFFICECODE\":\"" + officeCode + "\","
								+ "\"SSOTOKEN\":\"0\",\"CHECKSUM\":\"" + checksum + "\"," + "\"MOBILENUMBER\":\""
								+ mobileNumber + "\",\"EMAILID\":\"" + emailId + "\" }";
				}
				logger.debug("getbackToBackurl, backToBackurl : " + backToBackurl);
				if (backToBackurl != null) {
					queryDetails = new QueryDetails("proc_kiosk");
					queryDetails.addQueryParams(1, "updateTransInfoBBTB");
					queryDetails.addQueryParams(2, trnsid);
					DBServiceResponse serviceResponse1 = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
					logger.debug("getbackToBackurl, Response status of serviceResponse:" + serviceResponse1.getStatus());
				}
			}
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in getbackToBackurl : ", ex);
		}

		return backToBackurl;
	}

	@Override
	public void saveBackToBackTransaction(String decriptBackTOBackUrl, String paymentMode) throws Exception {
		JSONObject json = new JSONObject(decriptBackTOBackUrl);
		String REQUESTID = json.optString("REQUESTID");
		String TRANSACTIONSTATUSCODE = json.optString("TRANSACTIONSTATUSCODE");
		String RECEIPTNO = json.optString("RECEIPTNO");
		String TRANSACTIONID = json.optString("TRANSACTIONID");
		String TRANSAMT = json.optString("TRANSAMT");
		String REMAININGWALLET = json.optString("REMAININGWALLET");
		String EMITRATIMESTAMP = json.optString("EMITRATIMESTAMP");
		String TRANSACTIONSTATUS = json.optString("TRANSACTIONSTATUS");
		String MSG = json.optString("MSG");
		String CHECKSUM = json.optString("CHECKSUM");

		QueryDetails queryDetails = new QueryDetails("proc_kiosk");
		queryDetails.addQueryParams(1, "transactionStatusInsert");
		queryDetails.addQueryParams(2, REQUESTID);
		queryDetails.addQueryParams(3, TRANSACTIONSTATUSCODE);
		queryDetails.addQueryParams(4, RECEIPTNO);
		queryDetails.addQueryParams(5, TRANSACTIONID);
		queryDetails.addQueryParams(6, null);
		queryDetails.addQueryParams(7, REMAININGWALLET);
		queryDetails.addQueryParams(8, EMITRATIMESTAMP);
		queryDetails.addQueryParams(9, TRANSACTIONSTATUS);
		queryDetails.addQueryParams(10, MSG);
		queryDetails.addQueryParams(11, CHECKSUM);
		queryDetails.addQueryParams(12, TRANSAMT);
		queryDetails.addQueryParams(13, paymentMode);

		DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
		logger.debug("Response status of saveBackToBackTransaction:" + serviceResponse.getStatus());
	}

	@Override
	public void saveCardPaymentDetails(CardPaymentDetails cpd) {
		try {
			QueryDetails queryDetails = new QueryDetails("Proc_CardBill");
			queryDetails.setAutoCommit(false);

			queryDetails.addQueryParams(1, "insertAirtelCardBill");
			queryDetails.addQueryParams(2, cpd.getMERCHANTCODE());
			queryDetails.addQueryParams(3, cpd.getREQTIMESTAMP());
			queryDetails.addQueryParams(4, cpd.getPRN());
			queryDetails.addQueryParams(5, cpd.getRPPTXNID());
			queryDetails.addQueryParams(6, cpd.getAMOUNT());
			queryDetails.addQueryParams(7, cpd.getRPPTIMESTAMP());
			queryDetails.addQueryParams(8, cpd.getSTATUS());
			queryDetails.addQueryParams(9, cpd.getRESPONSECODE());
			queryDetails.addQueryParams(10, cpd.getRESPONSEMESSAGE());
			queryDetails.addQueryParams(11, cpd.getPAYMENTMODE());
			queryDetails.addQueryParams(12, cpd.getPAYMENTMODEBID());
			queryDetails.addQueryParams(13, cpd.getPAYMENTMODETIMESTAMP());
			queryDetails.addQueryParams(14, cpd.getPAYMENTAMOUNT());
			queryDetails.addQueryParams(15, cpd.getUDF1());
			queryDetails.addQueryParams(16, cpd.getUDF2());
			queryDetails.addQueryParams(17, cpd.getUDF3());
			queryDetails.addQueryParams(18, "");

			DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
			logger.debug("Response status of saveCardPaymentDetails:" + serviceResponse.getStatus());
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in saveCardPaymentDetails : ", ex);
		}
	}

	public String getCancelTranscationurl(String emitraTransactionId) {
		String cancelTranscationUrl = null;
		try {
			QueryDetails queryDetails = new QueryDetails("Proc_kiosk");

			queryDetails.addQueryParams(1, "selectEmitraTransId");
			queryDetails.addQueryParams(2, emitraTransactionId);

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);

			if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
				JSONArray resultSet = serviceResponse.getJsonArray();
				if (null != resultSet && resultSet.length() > 0) {
					org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);

					String requestID = rs.optString("REQUESTID");
					String EMITRATOKEN = rs.optString("TRANSACTIONID");
					String CANCELREMARK = "Cash Not Received";

					EncrptDesryptDataService eds = new EncrptDesryptDataService();

					String checksumurl = "{\"MERCHANTCODE\":\"EMITRAPLUS\",\"REQUESTID\":\"" + requestID
							+ "\",\"SSOTOKEN\":\"0\"}";

					logger.debug("getbackToBackurl, checkSUMURL" + checksumurl);
					String checksum = eds.sendPostCheckSum(checksumurl);

					cancelTranscationUrl = "{\"MERCHANTCODE\":\"EMITRAPLUS\",\"REQUESTID\":\"" + requestID + "\","
							+ "\"EMITRATOKEN\":\"" + EMITRATOKEN + "\",\"CANCELREMARK\":\"" + CANCELREMARK + "\","
							+ "\"ENTITYTYPEID\":\"4\"," + "\"SSOTOKEN\":\"0\",\"CHECKSUM\":\"" + checksum + "\" }";
				}
			}
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in getCancelTranscationurl, getbackToBackurl : ", ex);
		}

		return cancelTranscationUrl;
	}

	@Override
	public void savePaymentStatus(String token, String certType, int certificareYear) {
		try {
			QueryDetails queryDetails = new QueryDetails("Proc_CardBill");
			queryDetails.setAutoCommit(false);

			queryDetails.addQueryParams(1, "insertCertPaymentDetails");
			queryDetails.addQueryParams(2, token);
			queryDetails.addQueryParams(3, certType);
			queryDetails.addQueryParams(4, null);
			queryDetails.addQueryParams(5, null);
			queryDetails.addQueryParams(6, null);
			queryDetails.addQueryParams(7, null);
			queryDetails.addQueryParams(8, null);
			queryDetails.addQueryParams(9, null);
			queryDetails.addQueryParams(10, null);
			queryDetails.addQueryParams(11, null);
			queryDetails.addQueryParams(12, null);
			queryDetails.addQueryParams(13, null);
			queryDetails.addQueryParams(14, null);
			queryDetails.addQueryParams(15, null);
			queryDetails.addQueryParams(16, null);
			queryDetails.addQueryParams(17, null);
			queryDetails.addQueryParams(18, null);
			queryDetails.addQueryParams(19, certificareYear);

			DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
			logger.debug("Response status of savePaymentStatus:" + serviceResponse.getStatus());
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in insertCertPaymentDetails, savePaymentStatus : ", ex);
		}
	}

	@Override
	public void updateStatusCash(String status, String amt, String token) {
		try {
			QueryDetails queryDetails = new QueryDetails("Proc_CardBill");
			queryDetails.setAutoCommit(false);

			queryDetails.addQueryParams(1, "updateCertPaymentDetails");
			queryDetails.addQueryParams(2, token);
			queryDetails.addQueryParams(3, status);
			queryDetails.addQueryParams(4, amt);
			queryDetails.addQueryParams(5, "cash");

			DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
			logger.debug("Response status of updateStatusCash:" + serviceResponse.getStatus());
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in updateStatusCash: ", ex);
		}
	}

	@Override
	public Bill getTransactionDetails(String transactionid) {
		Bill bill1 = new Bill();
		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "selectTransactionDetails");
			queryDetails.addQueryParams(2, transactionid);

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);

			if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
				JSONArray resultSet = serviceResponse.getJsonArray();
				if (null != resultSet && resultSet.length() > 0) {
					org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
					bill1.setBackToBackTransactionID(rs.optString("TRANSACTIONID"));
					bill1.setBackToBackTransactionStatus(rs.optString("TRANSACTIONSTATUS"));
				}
			}
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in getTransactionDetails: ", ex);
		}

		return bill1;
	}

	@Override
	public void updateTransactionDetails(String emitraTransactionFlag, Bill transactionDetails, String trnsid)
			throws Exception {
		QueryDetails queryDetails = new QueryDetails("proc_kiosk");

		queryDetails.addQueryParams(1, "updateTransactionDetails");
		queryDetails.addQueryParams(2, transactionDetails.getBackToBackTransactionID());
		queryDetails.addQueryParams(3, transactionDetails.getBackToBackTransactionStatus());
		queryDetails.addQueryParams(4, emitraTransactionFlag);
		queryDetails.addQueryParams(5, trnsid);

		DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
		logger.debug("Response status of updateTransactionDetails:" + serviceResponse.getStatus());
	}

	@Override
	public void updateTransactionDetails(String transid) {
		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "updateTransactionpaymentDetails");
			queryDetails.addQueryParams(2, transid);

			DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
			logger.debug("Response status of updateTransactionPaymentDetails:" + serviceResponse.getStatus());
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in updateTransactionDetails, updateTransactionpaymentDetails: ", ex);
		}
	}

	@Override
	public String getBacktoBacktransactionId(String transid) {
		String getBacktoBacktransactionId = null;
		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "selectBackToBackTransaId");
			queryDetails.addQueryParams(2, transid);

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
			logger.debug("Response status of updateTransactionPaymentDetails:" + serviceResponse.getStatus());

			if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
				JSONArray resultSet = serviceResponse.getJsonArray();
				if (null != resultSet && resultSet.length() > 0) {
					org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
					getBacktoBacktransactionId = rs.optString("backtobacktransactionid");
				}
			}
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in getBacktoBacktransactionId : ", ex);
		}

		return getBacktoBacktransactionId;
	}

	@Override
	public void saveCancelTranscationurl(String decriptCancelTranscationUrl) {
		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");
			queryDetails.setAutoCommit(false);

			JSONObject json = new JSONObject(decriptCancelTranscationUrl);

			queryDetails.addQueryParams(1, "insertIntoCancelTransactionDetails");
			queryDetails.addQueryParams(2, json.optString("CANCELSTATUS"));
			queryDetails.addQueryParams(3, json.optString("MSG"));
			queryDetails.addQueryParams(4, "");
			queryDetails.addQueryParams(5, "");
			queryDetails.addQueryParams(6, "");
			queryDetails.addQueryParams(7, "");
			queryDetails.addQueryParams(8, "");
			queryDetails.addQueryParams(9, "");
			queryDetails.addQueryParams(10, "");
			queryDetails.addQueryParams(11, "");
			queryDetails.addQueryParams(12, "");
			queryDetails.addQueryParams(13, "");
			queryDetails.addQueryParams(14, "");
			queryDetails.addQueryParams(15, "");
			queryDetails.addQueryParams(16, "");
			queryDetails.addQueryParams(17, "");
			queryDetails.addQueryParams(18, "");
			queryDetails.addQueryParams(19, "");
			queryDetails.addQueryParams(20, "");
			queryDetails.addQueryParams(21, "");
			queryDetails.addQueryParams(22, "");
			queryDetails.addQueryParams(23, "");
			queryDetails.addQueryParams(24, "");
			queryDetails.addQueryParams(25, "");
			queryDetails.addQueryParams(26, "");
			queryDetails.addQueryParams(27, "");
			queryDetails.addQueryParams(28, "");
			queryDetails.addQueryParams(29, "");
			queryDetails.addQueryParams(30, json.optString("RECEIPTNO"));
			queryDetails.addQueryParams(31, json.optString("REQUESTID"));
			queryDetails.addQueryParams(32, (String) json.get("TRANSACTIONID"));
			queryDetails.addQueryParams(33, (String) json.get("EMITRATIMESTAMP"));
			queryDetails.addQueryParams(34, (String) json.get("CANCELSTATUSCODE"));
			queryDetails.addQueryParams(35, (String) json.get("SSOTOKEN"));
			queryDetails.addQueryParams(36, (String) json.get("AMT"));

			DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
			logger.debug("Response status of saveCancelTranscationurl:" + serviceResponse.getStatus());
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in saveCancelTranscationurl : ", ex);
		}
	}

	@Override
	public String getcertibackToBackurl(String amt, String trnsid, String serviceId, String flag) {
		String revenueHead = null;
		String backToBackurl = null;
		try {
			QueryDetails queryDetails = new QueryDetails("Proc_PrintCertificate");

			queryDetails.addQueryParams(1, "SelectPrintReceiptCerti");
			queryDetails.addQueryParams(2, trnsid);

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
			logger.debug("Response status of getcertibackToBackurl:" + serviceResponse.getStatus());

			if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
				JSONArray resultSet = serviceResponse.getJsonArray();
				if (null != resultSet && resultSet.length() > 0) {
					org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
					String registrationNo = rs.optString("ConsumerKeyValue1");
					String timestamp = rs.optString("gettimestamp");
					String subServiceID = rs.optString("SubServiceId");
					String mobileNumber = "";
					String emailId = "";
					if (serviceId.equals("3025")) {
						revenueHead = "277-0.00|276-10.00";
						mobileNumber = rs.getString("MobileNo");
					} else {
						revenueHead = "797-0.00|798-10.00";
					}

					logger.debug("getcertibackToBackurl, ServiceID" + serviceId + " revenueHead " + revenueHead);

					EncrptDesryptDataService eds = new EncrptDesryptDataService();

					String checksumurl = "{\"SSOID\":\"" + Login.SSOID + "\",\"REQUESTID\":\"" + trnsid
							+ "\",\"REQTIMESTAMP\":\"" + timestamp + "\",\"SSOTOKEN\":\"0\"}";
					logger.debug("getbackToBackurl, checkSUMURL" + checksumurl);
					String checksum = eds.sendPostCheckSum(checksumurl);

					backToBackurl = "{\"MERCHANTCODE\":\"EMITRAPLUS\",\"REQUESTID\":\"" + trnsid + "\","
							+ "\"REQTIMESTAMP\":\"" + timestamp + "\",\"SERVICEID\":\"" + serviceId
							+ "\",\"SUBSERVICEID\":\"" + subServiceID + "\"," + "\"REVENUEHEAD\":\"" + revenueHead
							+ "\",\"CONSUMERKEY\":\"" + registrationNo + "\"," + "\"LOOKUPID\":\"\","
							+ "\"CONSUMERNAME\":\"\",";
					if (serviceId.equals("3025")) {
						backToBackurl += "\"COMMTYPE\":\"1\",\"SSOID\":\"" + Login.SSOID
								+ "\",\"OFFICECODE\":\"BHAMA0001\",\"MOBILENUMBER\":\"" + mobileNumber
								+ "\",\"EMAILID\":\"" + emailId + "\"";
					} else {
						backToBackurl += "\"COMMTYPE\":\"2\",\"SSOID\":\"" + Login.SSOID
								+ "\",\"OFFICECODE\":\"HOREV\"";
					}
					backToBackurl += ",\"SSOTOKEN\":\"0\",\"CHECKSUM\":\"" + checksum + "\" }";
				}
				logger.debug("getbackToBackurl, backToBackurl : " + backToBackurl);
				if (backToBackurl != null) {
					queryDetails = new QueryDetails("proc_kiosk");
					queryDetails.addQueryParams(1, "updateTransInfoBBTB");
					queryDetails.addQueryParams(2, trnsid);
					DBServiceResponse serviceResponse1 = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
					logger.debug("getbackToBackurl, Response status of serviceResponse:" + serviceResponse1.getStatus());
				}
			}

		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in getcertibackToBackurl : ", ex);
		}

		return backToBackurl;
	}

	@Override
	public void updatedAmount(double d, int resultAmount, String tid) {
		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");
			queryDetails.setAutoCommit(false);

			queryDetails.addQueryParams(1, "updateAmount");
			queryDetails.addQueryParams(2, new Double(d).toString());
			queryDetails.addQueryParams(3, new Integer(resultAmount).toString());
			queryDetails.addQueryParams(4, tid);

			DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
			logger.debug("Response status of updatedAmount:" + serviceResponse.getStatus());
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in updatedAmount : ", ex);
		}
	}

	@Override
	public LatestVersionDetails versionCheck() {
		LatestVersionDetails latestVersionDetails = new LatestVersionDetails();
		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");
			queryDetails.setAutoCommit(false);

			queryDetails.addQueryParams(1, "selectVersionDetail");

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);

			if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
				JSONArray resultSet = serviceResponse.getJsonArray();
				if (null != resultSet && resultSet.length() > 0) {
					org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
					String vno = rs.optString("VersionNo");
					String createdDate = TimeUtils.getFormattedSQLDateStrFromDateStr(rs.optString("createdDate"));

					latestVersionDetails.setVersionNo(Double.parseDouble(vno.replaceAll("Ver", "")));
					latestVersionDetails.setCreatedDate(createdDate);
					logger.debug("Database Version NO : " + vno + " Version Date : " + createdDate);
				}
			}
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in versionCheck : ", ex);
		}

		return latestVersionDetails;
	}

	@Override
	public MachineAuth getMachineAuthenticationDetails(String ssoid) {
		MachineAuth machine_auth = new MachineAuth();
		try {
			QueryDetails queryDetails = new QueryDetails("Proc_Info");

			queryDetails.addQueryParams(1, "select");
			queryDetails.addQueryParams(2, 0);
			queryDetails.addQueryParams(3, 0);
			queryDetails.addQueryParams(4, 0);
			queryDetails.addQueryParams(5, ssoid);

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
			logger.debug("Response status of getMachineAuthenticationDetails:" + serviceResponse.getStatus());

			if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
				JSONArray resultSet = serviceResponse.getJsonArray();
				if (null != resultSet && resultSet.length() > 0) {
					org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
					machine_auth.setMachineId(rs.optString("MachineId"));
					machine_auth.setSsoId(rs.optString("SSOId"));
				}
			}
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in getMachineAuthenticationDetails : ", ex);
		}

		return machine_auth;
	}

	public void updateDeleteTransactionFlag(String msg, String emitraTransactionId) {
		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");
			queryDetails.setAutoCommit(false);

			queryDetails.addQueryParams(1, "updateDeleteTransactionFlag");
			queryDetails.addQueryParams(2, msg);
			queryDetails.addQueryParams(3, emitraTransactionId);

			DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
			logger.debug("Response status of updateDeleteTransactionFlag:" + serviceResponse.getStatus());
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in updateDeleteTransactionFlag : ", ex);
		}
	}

	@Override
	public void saveMachineAuth(MachineAuth mach, String SSOID, String versionNo) {
		try {
			QueryDetails queryDetails = new QueryDetails("Proc_Info");
			queryDetails.setAutoCommit(false);

			queryDetails.addQueryParams(1, "insertlogindetails");
			queryDetails.addQueryParams(2, SSOID);
			queryDetails.addQueryParams(3, mach.machineId);
			queryDetails.addQueryParams(4, versionNo);
			DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
			logger.debug("Response status of saveMachineAuth:" + serviceResponse.getStatus());
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Exception in saveMachineAuth : ", ex);
		}
	}

	@Override
	public boolean insertUntrackedEmitraTransaction(Bill bill) {
		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "insertUntrackedEmitraTransaction");
			queryDetails.addQueryParams(2, bill.getBackToBackTransactionID());
			queryDetails.addQueryParams(3, bill.getTransactionId());
			queryDetails.addQueryParams(4, bill.getSsoID());
			queryDetails.addQueryParams(5, bill.getServiceProviderID());
			queryDetails.addQueryParams(6, bill.getBillAmount());

			DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
			logger.debug("Response status of saveMachineAuth:" + serviceResponse.getStatus());

			if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
				return true;
			}
		} catch (Exception ex) {
			logger.error("BillDAOImpl, Caught an exception while inserting UntrackedEmitraTransaction !!", ex);
		}

		return false;
	}

	@Override
	public void accpetMachineAmountDetails(String transid, String totalAmount, String noteValue) {
		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");
			queryDetails.setAutoCommit(false);

			queryDetails.addQueryParams(1, "acceptAmount");
			queryDetails.addQueryParams(2, transid);
			queryDetails.addQueryParams(3, totalAmount);
			queryDetails.addQueryParams(4, noteValue);

			DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
			logger.debug("Response status of accpetMachineAmountDetails:" + serviceResponse.getStatus());

		} catch (Exception ex) {
			logger.error("BillDAOImpl, Caught an exception while accpetMachineAmountDetails !!", ex);
		}

	}

	@Override
	public void saveVerifiedData(String verifiedData) {
		JSONParser parser = new JSONParser();
		JSONObject json;
		try {
			json = (JSONObject) parser.parse(verifiedData);
			String TRANSACTIONDATE = (String) json.get("TRANSACTIONDATE");
			String RECEIPTNO = (String) json.get("RECEIPTNO");
			String REQUESTID = (String) json.get("REQUESTID");
			String TRANSACTIONID = (String) json.get("TRANSACTIONID");
			String AMT = (String) json.get("AMT");
			String EMITRATIMESTAMP = (String) json.get("EMITRATIMESTAMP");
			String TRANSACTIONSTATUS = (String) json.get("TRANSACTIONSTATUS");
			String VERIFYSTATUSCODE = (String) json.get("VERIFYSTATUSCODE");
			String MSG = (String) json.get("MSG");
			String SSOTOKEN = (String) json.get("SSOTOKEN");
			String CHECKSUM = (String) json.get("CHECKSUM");
			String SERVICEID = (String) json.get("SERVICEID");
			String CONSUMERKEY = (String) json.get("CONSUMERKEY");

			QueryDetails queryDetails = new QueryDetails("Proc_CardBill");

			queryDetails.addQueryParams(1, "verifyInsertDetails");
			queryDetails.addQueryParams(2, TRANSACTIONDATE);
			queryDetails.addQueryParams(3, RECEIPTNO);
			queryDetails.addQueryParams(4, REQUESTID);
			queryDetails.addQueryParams(5, TRANSACTIONID);
			queryDetails.addQueryParams(6, AMT);
			queryDetails.addQueryParams(7, EMITRATIMESTAMP);
			queryDetails.addQueryParams(8, TRANSACTIONSTATUS);
			queryDetails.addQueryParams(9, VERIFYSTATUSCODE);
			queryDetails.addQueryParams(10, MSG);
			queryDetails.addQueryParams(11, SSOTOKEN);
			queryDetails.addQueryParams(12, CHECKSUM);
			queryDetails.addQueryParams(13, SERVICEID);
			queryDetails.addQueryParams(14, CONSUMERKEY);

			DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
			logger.debug("saveVerifiedData, Response status of saveVerifiedData(Insertion):" + serviceResponse.getStatus());
			
			if (VERIFYSTATUSCODE.equals("200")) {
				
				queryDetails = new QueryDetails("Proc_CardBill");

				queryDetails.addQueryParams(1, "updateVerifiedData");
				queryDetails.addQueryParams(2, RECEIPTNO);
				queryDetails.addQueryParams(3, REQUESTID);
				queryDetails.addQueryParams(4, TRANSACTIONID);
				queryDetails.addQueryParams(5, AMT);
				queryDetails.addQueryParams(6, EMITRATIMESTAMP);
				queryDetails.addQueryParams(7, TRANSACTIONSTATUS);
				queryDetails.addQueryParams(8, VERIFYSTATUSCODE);
				queryDetails.addQueryParams(9, MSG);
				queryDetails.addQueryParams(10, CHECKSUM);
				
				serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
				logger.debug("saveVerifiedData, Response status of saveVerifiedData(Updation):" + serviceResponse.getStatus());
			}
		} catch (Exception e) {
			logger.error("BillDAOImpl, Exception in saveVerifiedData :" + e.getMessage());
		} 
	}

	@Override
    public boolean updateUntrackedEmitraTransaction(Bill billDetails) {
        try {
            QueryDetails queryDetails = new QueryDetails("proc_kiosk");

            queryDetails.addQueryParams(1, "updateUntrackedEmitraTransaction");
            queryDetails.addQueryParams(2, billDetails.getBackToBackTransactionID());
            queryDetails.addQueryParams(3, billDetails.getTransactionId());
            queryDetails.addQueryParams(4, billDetails.getSsoID());
            queryDetails.addQueryParams(5, billDetails.getServiceProviderID());
            queryDetails.addQueryParams(6, billDetails.getBillAmount());
            queryDetails.addQueryParams(7, billDetails.getrECEIPTNO());
            queryDetails.addQueryParams(8, billDetails.geteMITRATIMESTAMP());
            queryDetails.addQueryParams(9, billDetails.gettRANSACTIONSTATUS());
            queryDetails.addQueryParams(10, billDetails.getvERIFYSTATUSCODE());
            queryDetails.addQueryParams(11, billDetails.getmSG());

            DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
            logger.debug("Response status of saveMachineAuth:" + serviceResponse.getStatus());

            if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
                return true;
            }
        } catch (Exception ex) {
            logger.error("BillDAOImpl, Caught an exception while inserting UntrackedEmitraTransaction !!", ex);
        }

        return false;
    }

	@Override
	public String getSsoId(String machineId) {
		// TODO Auto-generated method stub
		String SSOId = null;
		try {
            QueryDetails queryDetails = new QueryDetails("Proc_info");
            queryDetails.setAutoCommit(false);

            queryDetails.addQueryParams(1, "selectlogindetails");
            queryDetails.addQueryParams(2, machineId);
            
            DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
            logger.info("Response status of getSsoId While checking for autologin:" + serviceResponse.getStatus());
            if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
                JSONArray resultSet = serviceResponse.getJsonArray();
                if (null != resultSet && resultSet.length() > 0) {
                	org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
                	SSOId = rs.optString("SSOId");
                }
            }
        } catch (Exception ex) {
            logger.error("BillDAOImpl, Exception in insertBhamashahPaymentDetails : ", ex);
        }
		
		return SSOId;
	}

	@Override
	public void updatePaymentMode(String transactionId, String paymentCode) {
		// TODO Auto-generated method stub
		try {
            QueryDetails queryDetails = new QueryDetails("proc_kiosk");

            queryDetails.addQueryParams(1, "updatepaymentmode");
            queryDetails.addQueryParams(2, transactionId);
            queryDetails.addQueryParams(3, paymentCode);

            DBServiceResponse serviceResponse = dbHTTPUtils.pushDataToDBApiServer(queryDetails);
            logger.info("Response status of saveMachineAuth:" + serviceResponse.getStatus());

        } catch (Exception ex) {
            logger.error("BillDAOImpl, Caught an exception while inserting UntrackedEmitraTransaction !!", ex);
        }
	}
}