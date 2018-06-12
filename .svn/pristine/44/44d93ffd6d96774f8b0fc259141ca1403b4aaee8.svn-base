package dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.Bill;
import model.DBServiceResponse;
import model.Discom;
import model.Phed;
import model.QueryDetails;
import utils.DBHTTPUtils;

@Repository("encryptDecryptDao")
public class EncryptDecryptDAOImpl implements EncryptDecryptDAO {

	static final Logger logger = Logger.getLogger(EncryptDecryptDAOImpl.class);

	@Autowired
	DBHTTPUtils dbHTTPUtils;

	@Override
	public List<Bill> getAirtelDetails(String apiResult, Bill bill) {
		List<Bill> airtelBillDetails = new ArrayList<Bill>();
		String dbTransID = null;

		try {
			String x[] = apiResult.split("TransactionDetails");
			String x1[] = x[1].split("BillDetails");
			String x2[] = x1[0].split(":");
			String x3[] = x1[1].split(":");

			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "transactioninsert");
			queryDetails.addQueryParams(2, x2[2].split(",")[0].substring(1, x2[2].split(",")[0].length() - 1));
			queryDetails.addQueryParams(3, x2[3].split(",")[0].substring(1, x2[3].split(",")[0].length() - 1));
			queryDetails.addQueryParams(4, x2[4].split(",")[0].substring(1, x2[4].split(",")[0].length() - 1));
			queryDetails.addQueryParams(5, x2[5].split(",")[0].substring(1, x2[5].split(",")[0].length() - 1));
			queryDetails.addQueryParams(6, x2[6].split(",")[0].substring(1, x2[6].split(",")[0].length() - 1));
			queryDetails.addQueryParams(7, x2[7].split(",")[0].substring(1, x2[7].split(",")[0].length() - 1));
			queryDetails.addQueryParams(8, x2[8].split(",")[0].substring(1, x2[8].split(",")[0].length() - 1));
			queryDetails.addQueryParams(9, x2[9].split(",")[0].substring(1, x2[9].split(",")[0].length() - 1));
			queryDetails.addQueryParams(10, x2[10].split(",")[0].substring(1, x2[10].split(",")[0].length() - 2));
			queryDetails.addQueryParams(11, x3[3].split(",")[0].substring(1, x3[3].split(",")[0].length() - 2));
			queryDetails.addQueryParams(12, x3[5].split(",")[0].substring(1, x3[5].split(",")[0].length() - 2));
			queryDetails.addQueryParams(13, x3[7].split(",")[0].substring(1, x3[7].split(",")[0].length() - 2));
			queryDetails.addQueryParams(14, x3[9].split(",")[0].substring(1, x3[9].split(",")[0].length() - 2));
			queryDetails.addQueryParams(15, x3[11].split(",")[0].substring(1, x3[11].split(",")[0].length() - 2));
			queryDetails.addQueryParams(16, x3[15].split(",")[0].substring(1, x3[15].split(",")[0].length() - 2));
			queryDetails.addQueryParams(17, x3[17].split(",")[0].substring(1, x3[17].split(",")[0].length() - 2));
			queryDetails.addQueryParams(18, x3[19].split(",")[0].substring(1, x3[19].split(",")[0].length() - 5));
			queryDetails.addQueryParams(19, bill.getSsoID());
			queryDetails.addQueryParams(20, bill.getServiceProviderID());
			queryDetails.addQueryParams(21, bill.getBillEmail());

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
			JSONArray resultSet = serviceResponse.getJsonArray();
			if (null != resultSet && resultSet.length() > 0) {
				org.json.JSONObject rs1 = (org.json.JSONObject) resultSet.get(0);
				dbTransID = rs1.optString("TransId");
			}
		} catch (Exception ex) {
			logger.error("EncryptDecryptDAOImpl, Exception in insert trasaction AirtelDetails : ", ex);
		}

		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "selectbills");
			queryDetails.addQueryParams(2, dbTransID);

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
			JSONArray resultSet = serviceResponse.getJsonArray();
			if (null != resultSet && resultSet.length() > 0) {
				org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
				Bill bill1 = new Bill();
				bill1.setBillAmount(rs.optString("amountafterduedate"));
				bill1.setBillMobileNo(rs.optString("mobilenumber"));
				bill1.setDueDate(rs.optString("duedate"));
				bill1.setName(rs.optString("consumername"));
				bill1.setServiceProviderName(rs.optString("servicename"));
				bill1.setBillEmail(rs.optString("EmailId"));
				bill1.setTransactionId(rs.optString("TransId"));
				bill1.setCreatedDate(rs.optString("gettimestamp"));

				if (rs.getInt("partpaymentallow") == 1) {
					bill1.setPartpaymentallow(true);
				} else {
					bill1.setPartpaymentallow(false);
				}
				bill1.setPartpaymenttype(rs.optString("partpaymenttype"));
				airtelBillDetails.add(bill1);
			}
		} catch (Exception ex) {
			logger.error("EncryptDecryptDAOImpl, Exception in getAirtelDetails : ", ex);
		}

		return airtelBillDetails;
	}

	@Override
	public List<Bill> getIdeaDetails(String apiResult, Bill bill) {
		List<Bill> ideaBillDetails = new ArrayList<Bill>();
		String dbTransID = null;
		try {
			String x[] = apiResult.split("TransactionDetails");
			String x1[] = x[1].split("BillDetails");
			String x2[] = x1[0].split(":");
			String x3[] = x1[1].split(":");

			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "transactioninsertidea");
			queryDetails.addQueryParams(2, x2[2].split(",")[0].substring(1, x2[2].split(",")[0].length() - 1));
			queryDetails.addQueryParams(3, x2[3].split(",")[0].substring(1, x2[3].split(",")[0].length() - 1));
			queryDetails.addQueryParams(4, x2[4].split(",")[0].substring(1, x2[4].split(",")[0].length() - 1));
			queryDetails.addQueryParams(5, x2[5].split(",")[0].substring(1, x2[5].split(",")[0].length() - 1));
			queryDetails.addQueryParams(6, x2[6].split(",")[0].substring(1, x2[6].split(",")[0].length() - 1));
			queryDetails.addQueryParams(7, x2[7].split(",")[0].substring(1, x2[7].split(",")[0].length() - 1));
			queryDetails.addQueryParams(8, x2[8].split(",")[0].substring(1, x2[8].split(",")[0].length() - 1));
			queryDetails.addQueryParams(9, x2[9].split(",")[0].substring(1, x2[9].split(",")[0].length() - 1));
			queryDetails.addQueryParams(10, x2[10].split(",")[0].substring(1, x2[10].split(",")[0].length() - 2));
			queryDetails.addQueryParams(11, x3[3].split(",")[0].substring(1, x3[3].split(",")[0].length() - 2));
			queryDetails.addQueryParams(12, x3[5].split(",")[0].substring(1, x3[5].split(",")[0].length() - 2));
			queryDetails.addQueryParams(13, x3[7].split(",")[0].substring(1, x3[7].split(",")[0].length() - 2));
			queryDetails.addQueryParams(14, x3[9].split(",")[0].substring(1, x3[9].split(",")[0].length() - 2));
			queryDetails.addQueryParams(15, x3[11].split(",")[0].substring(1, x3[11].split(",")[0].length() - 2));
			queryDetails.addQueryParams(16, x3[13].substring(1, x3[13].length() - 5));
			queryDetails.addQueryParams(17, null);
			queryDetails.addQueryParams(18, null);
			queryDetails.addQueryParams(19, bill.getSsoID());
			queryDetails.addQueryParams(20, bill.getServiceProviderID());
			queryDetails.addQueryParams(21, bill.getBillEmail());

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
			JSONArray resultSet = serviceResponse.getJsonArray();
			if (null != resultSet && resultSet.length() > 0) {
				org.json.JSONObject rs1 = (org.json.JSONObject) resultSet.get(0);
				dbTransID = rs1.optString("TransId");
			}
		} catch (Exception ex) {
			logger.error("EncryptDecryptDAOImpl, Exception in insert transaction IdeaDetails : ", ex);
		}

		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "selectbillsidea");
			queryDetails.addQueryParams(2, dbTransID);

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
			JSONArray resultSet = serviceResponse.getJsonArray();
			if (null != resultSet && resultSet.length() > 0) {
				org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
				Bill bill1 = new Bill();
				bill1.setBillAmount(rs.optString("AmountPayable"));
				bill1.setBillMobileNo(rs.optString("MobileNumber"));
				bill1.setDueDate(rs.optString("BillDate"));
				bill1.setName(rs.optString("ConsumerName"));
				bill1.setServiceProviderName(rs.optString("servicename"));
				bill1.setBillEmail(rs.optString("EmailId"));
				bill1.setTransactionId(rs.optString("TransId"));
				bill1.setCreatedDate(rs.optString("gettimestamp"));
				// bill1.setPartpaymentallow(rs.getBoolean("partpaymentallow"));
				if (rs.getInt("partpaymentallow") == 1) {
					bill1.setPartpaymentallow(true);
				} else {
					bill1.setPartpaymentallow(false);
				}
				bill1.setPartpaymenttype(rs.optString("partpaymenttype"));
				ideaBillDetails.add(bill1);
			}

		} catch (Exception ex) {
			logger.error("EncryptDecryptDAOImpl, Exception in getIdeaDetails : ", ex);
		}

		return ideaBillDetails;
	}

	@Override
	public List<Bill> getVodafoneDetails(String apiResult, Bill bill) {
		List<Bill> vodafoneBillDetails = new ArrayList<Bill>();
		String dbTransID = null;

		try {
			JSONObject json = new JSONObject(apiResult);
			JSONObject fetchobj = json.getJSONObject("FetchDetails");
			JSONObject transobj = fetchobj.getJSONObject("TransactionDetails");
			JSONArray billarray = fetchobj.getJSONArray("BillDetails");

			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "transactioninsertVoda");
			queryDetails.addQueryParams(2, transobj.get("ServiceName").toString());
			queryDetails.addQueryParams(3, transobj.get("officeID").toString());
			queryDetails.addQueryParams(4, transobj.get("BillAmount").toString());
			queryDetails.addQueryParams(5, transobj.get("ConsumerName").toString());
			queryDetails.addQueryParams(6, transobj.get("consumerKeysValues").toString());
			queryDetails.addQueryParams(7, transobj.get("partPaymentAllow").toString());
			queryDetails.addQueryParams(8, transobj.get("partPaymentType").toString());
			queryDetails.addQueryParams(9, transobj.get("lookUpId").toString());
			queryDetails.addQueryParams(10, transobj.get("officeCodeValue").toString());

			int temp = 11;
			for (int index = 0; index < billarray.length(); index++) {
				JSONObject jsonLineItem = billarray.getJSONObject(index);

				String value = (String) jsonLineItem.get("LableValue");

				if (value == null || value.equals(""))
					queryDetails.addQueryParams(temp, null);
				else
					queryDetails.addQueryParams(temp, value);

				temp++;
				if (temp == 18)
					break;
			}

			queryDetails.addQueryParams(18, null);
			queryDetails.addQueryParams(19, bill.getSsoID());
			queryDetails.addQueryParams(20, bill.getServiceProviderID());
			queryDetails.addQueryParams(21, bill.getBillEmail());

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
			JSONArray resultSet = serviceResponse.getJsonArray();
			if (null != resultSet && resultSet.length() > 0) {
				org.json.JSONObject rs1 = (org.json.JSONObject) resultSet.get(0);
				dbTransID = rs1.optString("TransId");
			}
		} catch (Exception ex) {
			logger.error("EncryptDecryptDAOImpl, Exception in inserting transaction VodafoneDetails : ", ex);
		}

		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "selectbillsvodafone");
			queryDetails.addQueryParams(2, dbTransID);

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);

			JSONArray resultSet = serviceResponse.getJsonArray();
			if (null != resultSet && resultSet.length() > 0) {
				org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);

				Bill bill1 = new Bill();
				bill1.setBillAmount(
						rs.optString("amountbeforeduedate").equals("NA") ? "0" : rs.optString("amountbeforeduedate"));
				bill1.setBillMobileNo(rs.optString("mobilenumber"));
				bill1.setDueDate(rs.optString("duedate"));
				bill1.setName(rs.optString("consumername"));
				bill1.setServiceProviderName(rs.optString("servicename"));
				bill1.setBillEmail(rs.optString("EmailId"));
				bill1.setTransactionId(rs.optString("TransId"));
				bill1.setCreatedDate(rs.optString("gettimestamp"));

				if (rs.getInt("partpaymentallow") == 1) {
					bill1.setPartpaymentallow(true);
				} else {
					bill1.setPartpaymentallow(false);
				}

				bill1.setPartpaymenttype(rs.optString("partpaymenttype"));
				vodafoneBillDetails.add(bill1);
			}
		} catch (Exception ex) {
			logger.error("EncryptDecryptDAOImpl, Exception in getVodafoneDetails : ", ex);
		}

		return vodafoneBillDetails;
	}

	public List<Discom> getDiscomDetails(String apiResult, Bill bill, Discom discom) {

		List<Discom> discomDetails = new ArrayList<Discom>();
		String dbTransID = null;
		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(apiResult);

			JSONObject fetchobj = jsonObject.getJSONObject("FetchDetails");
			JSONObject transobj = (JSONObject) fetchobj.get("TransactionDetails");

			if (transobj != null && transobj.length() > 0) {
				JSONArray billarray = fetchobj.getJSONArray("BillDetails");

				QueryDetails queryDetails = new QueryDetails("proc_kiosk");

				queryDetails.addQueryParams(1, "transactioninsertElectricity");
				queryDetails.addQueryParams(2, transobj.get("ServiceName").toString());
				queryDetails.addQueryParams(3, transobj.get("officeID").toString());
				queryDetails.addQueryParams(4, transobj.get("BillAmount").toString());

				String ConsumerName = transobj.get("ConsumerName").toString();
				try {
					ConsumerName = StringEscapeUtils.unescapeJava(StringEscapeUtils
							.unescapeHtml4(URLDecoder.decode(ConsumerName.replaceAll("%u", "\\\\u"), "UTF-8")));
				} catch (UnsupportedEncodingException ex) {
					logger.error("EncryptDecryptDAOImpl, Exception in getDiscomDetails : "+ex.getMessage());
					/*ex.printStackTrace();*/
				}

				queryDetails.addQueryParams(5, ConsumerName);

				queryDetails.addQueryParams(6, transobj.get("consumerKeysValues").toString());
				queryDetails.addQueryParams(7, transobj.get("partPaymentAllow").toString());
				queryDetails.addQueryParams(8, transobj.get("partPaymentType").toString());
				queryDetails.addQueryParams(9, transobj.get("lookUpId").toString());
				queryDetails.addQueryParams(10, transobj.get("officeCodeValue").toString());

				for (int index = 0; index < billarray.length(); index++) {
					JSONObject jsonLineItem = billarray.getJSONObject(index);
					String key = (String) jsonLineItem.get("LableName");

					if (key.equalsIgnoreCase("K Number") || key.equalsIgnoreCase("KNO")) {
						String value = (String) jsonLineItem.get("LableValue");
						if (value == null || value.equals(""))
							queryDetails.addQueryParams(11, null);
						else
							queryDetails.addQueryParams(11, value);
					}

					if (key.equalsIgnoreCase("Name") || key.equalsIgnoreCase("APPLICANTNAME")
							|| key.equalsIgnoreCase("Applicant Name") || key.equalsIgnoreCase("APPLICANT_NAME")) {
						String value = (String) jsonLineItem.get("LableValue");
						if (value == null || value.equals(""))
							queryDetails.addQueryParams(12, null);
						else
							queryDetails.addQueryParams(12, value);
					}
					if (key.equalsIgnoreCase("ChequeDueDate") || key.equalsIgnoreCase("Bill Due Date")
							|| key.equalsIgnoreCase("DUE_DATE")) {
						String value = (String) jsonLineItem.get("LableValue");
						if (value == null || value.equals(""))
							queryDetails.addQueryParams(13, null);
						else
							queryDetails.addQueryParams(13, value);
					}
					if (key.equalsIgnoreCase("Bill Year") || key.equalsIgnoreCase("BILL_YEAR")
							|| key.equalsIgnoreCase("BILLYEAR")) {
						String value = (String) jsonLineItem.get("LableValue");
						if (value == null || value.equals(""))
							queryDetails.addQueryParams(14, null);
						else
							queryDetails.addQueryParams(14, value);
					}
					if (key.equalsIgnoreCase("BillNo") || key.equalsIgnoreCase("Bill Number")
							|| key.equalsIgnoreCase("Bill_No") || key.equalsIgnoreCase("BILL_NUMBER")) {
						String value = (String) jsonLineItem.get("LableValue");
						if (value == null || value.equals(""))
							queryDetails.addQueryParams(16, null);
						else
							queryDetails.addQueryParams(16, value);
					}
					if (key.equalsIgnoreCase("A/C Number") || key.equalsIgnoreCase("ACCOUNTNO")
							|| key.equalsIgnoreCase("Account No") || key.equalsIgnoreCase("ACCOUNT_NUMBER")
							|| key.equalsIgnoreCase("Account_No")) {
						String value = (String) jsonLineItem.get("LableValue");
						if (value == null || value.equals(""))
							queryDetails.addQueryParams(17, null);
						else
							queryDetails.addQueryParams(17, value);
					}
					if (key.equalsIgnoreCase("BillMonth") || key.equalsIgnoreCase("Bill Month")
							|| key.equalsIgnoreCase("BILL_MONTH")) {
						String value = (String) jsonLineItem.get("LableValue");
						if (value == null || value.equals(""))
							queryDetails.addQueryParams(18, null);
						else
							queryDetails.addQueryParams(18, value);
					}
					if (key.equalsIgnoreCase("CashDueDate") || key.equalsIgnoreCase("Bill Due Date")
							|| key.equalsIgnoreCase("DUE_DATE")) {
						String value = (String) jsonLineItem.get("LableValue");
						if (value == null || value.equals(""))
							queryDetails.addQueryParams(19, null);
						else
							queryDetails.addQueryParams(19, value);
					}
					if (key.equalsIgnoreCase("OFFICE_CODE") || key.equalsIgnoreCase("OFFICECODE")
							|| key.equalsIgnoreCase("Office Code")) {
						String value = (String) jsonLineItem.get("LableValue");
						if (value == null || value.equals(""))
							queryDetails.addQueryParams(20, null);
						else
							queryDetails.addQueryParams(20, value);
					}
					if (key.equalsIgnoreCase("ADDRESS")) {
						String value = (String) jsonLineItem.get("LableValue");
						if (value == null || value.equals(""))
							queryDetails.addQueryParams(21, null);
						else
							queryDetails.addQueryParams(21, value);
					}
					if (key.equalsIgnoreCase("BinderNo") || key.equalsIgnoreCase("Binder Number")
							|| key.equalsIgnoreCase("BINDER_NO") || key.equalsIgnoreCase("BINDER_NUMBER")) {
						String value = (String) jsonLineItem.get("LableValue");
						if (value == null || value.equals(""))
							queryDetails.addQueryParams(23, null);
						else
							queryDetails.addQueryParams(23, value);
					}
					if (key.equalsIgnoreCase("TotalAmount") || key.equalsIgnoreCase("AMOUNT")
							|| key.equalsIgnoreCase("BILL_AMOUNT") || key.equalsIgnoreCase("Bill Amount")) {
						String value = (String) jsonLineItem.get("LableValue");
						if (value == null || value.equals(""))
							queryDetails.addQueryParams(24, null);
						else
							queryDetails.addQueryParams(24, value);
					}
				}

				logger.info("1::" + bill.getSsoID() + " 2::" + String.valueOf(bill.getServiceProviderID()) + " 3::"
						+ bill.getBillEmail());
				queryDetails.addQueryParams(15, null);
				queryDetails.addQueryParams(22, "Full Payment");
				queryDetails.addQueryParams(25, bill.getSsoID());
				queryDetails.addQueryParams(26, String.valueOf(bill.getServiceProviderID()));
				queryDetails.addQueryParams(27, bill.getBillEmail());
				queryDetails.addQueryParams(28, bill.getBillMobileNo());
				queryDetails.addQueryParams(29, bill.getBillEmail());

				DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
				JSONArray resultSet = serviceResponse.getJsonArray();
				if (null != resultSet && resultSet.length() > 0) {
					org.json.JSONObject rs1 = (org.json.JSONObject) resultSet.get(0);
					dbTransID = rs1.optString("TransId");
				}
			} else {
				logger.error("GETDISCOMDETAILS : " + "NO DETAILS AVAILABLE");
				return discomDetails;
			}
		} catch (Exception ex) {
			logger.error("EncryptDecryptDAOImpl, Exception in getDiscomDetails transactioninsertElectricity: ", ex);
		}

		try {
			if (dbTransID != null && !dbTransID.equals("")) {
				QueryDetails queryDetails = new QueryDetails("proc_kiosk");

				queryDetails.addQueryParams(1, "selectbillsDiscom");
				queryDetails.addQueryParams(2, dbTransID);

				DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
				JSONArray resultSet = serviceResponse.getJsonArray();
				if (null != resultSet && resultSet.length() > 0) {
					org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
					Discom dis = new Discom();
					dis.setK_Number(rs.optString("K_Number"));
					dis.setName(rs.optString("Name"));
					dis.setChequeDueDate(rs.optString("ChequeDueDate"));
					dis.setBillYear(rs.optString("BillYear"));
					dis.setMessage(rs.optString("Message"));
					dis.setBillNo(rs.optString("BillNo"));
					dis.setAccount_number(rs.optString("ACNumber"));
					dis.setBillMonth(rs.optString("BillMonth"));
					dis.setCashDueDate(rs.optString("CashDueDate"));
					dis.setOfficeCode(rs.optString("OfficeCode"));
					dis.setAddress(rs.optString("Address"));
					dis.setPaymentType(rs.optString("PaymentType"));
					dis.setBinderNo(rs.optString("BinderNo"));
					dis.setTotalAmount(rs.optString("TotalAmount"));
					dis.setTransactionId(rs.optString("TransId"));
					dis.setEmail(rs.optString("EmailId"));
					dis.setMobile(rs.optString("Mobile"));
					dis.setConsumerKeyValue(rs.optString("consumerkeyvalue"));
					dis.setCreatedDate(rs.optString("gettimestamp"));
					discomDetails.add(dis);
				}
			}
		} catch (Exception ex) {
			logger.error("EncryptDecryptDAOImpl, Exception in getDiscomDetails : ", ex);
		}

		return discomDetails;
	}

	@Override
	public List<Bill> getBsnlDetails(String apiResult, Bill bill) {
		List<Bill> bsnlBillDetails = new ArrayList<Bill>();
		String dbTransID = null;
		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(apiResult);
			JSONObject fetchobj = jsonObject.getJSONObject("FetchDetails");
			JSONObject transobj = (JSONObject) fetchobj.get("TransactionDetails");
			JSONArray billarray = fetchobj.getJSONArray("BillDetails");

			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "transactioninsertBSNL");
			queryDetails.addQueryParams(2, transobj.get("ServiceName").toString());
			queryDetails.addQueryParams(3, transobj.get("officeID").toString());
			queryDetails.addQueryParams(4,
					transobj.get("BillAmount").toString() == null || transobj.get("BillAmount").toString().equals("NA")
							? "0"
							: transobj.get("BillAmount").toString());
			queryDetails.addQueryParams(5, transobj.get("ConsumerName").toString());
			queryDetails.addQueryParams(6, transobj.get("consumerKeysValues").toString());
			queryDetails.addQueryParams(7, transobj.get("partPaymentAllow").toString());
			queryDetails.addQueryParams(8, transobj.get("partPaymentType").toString());
			queryDetails.addQueryParams(9, transobj.get("lookUpId").toString());
			queryDetails.addQueryParams(10, transobj.get("officeCodeValue").toString());
			queryDetails.addQueryParams(11, bill.getSsoID());
			queryDetails.addQueryParams(12, bill.getServiceProviderID());
			queryDetails.addQueryParams(13, bill.getBillEmail());

			int temp = 14;
			for (int index = 0; index < billarray.length(); index++) {
				JSONObject jsonLineItem = billarray.getJSONObject(index);
				String value = (String) jsonLineItem.get("LableValue");

				if (value == null || value.equals(""))
					queryDetails.addQueryParams(temp, null);
				else
					queryDetails.addQueryParams(temp, value);

				temp++;
				if (temp == 30)
					break;
			}

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
			JSONArray resultSet = serviceResponse.getJsonArray();
			if (null != resultSet && resultSet.length() > 0) {
				org.json.JSONObject rs1 = (org.json.JSONObject) resultSet.get(0);
				dbTransID = rs1.optString("TransId");
			}
		} catch (Exception ex) {
			logger.error("EncryptDecryptDAOImpl, Exception in inserting transaction BsnlDetails : ", ex);
		}

		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "selectbillsbsnl");
			queryDetails.addQueryParams(2, dbTransID);

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
			JSONArray resultSet = serviceResponse.getJsonArray();

			if (null != resultSet && resultSet.length() > 0) {
				org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
				Bill bill1 = new Bill();
				bill1.setBillAmount(rs.optString("Amount").equals("NA") ? "0" : rs.optString("Amount"));
				bill1.setBillMobileNo(rs.optString("Mobile_Landline_Number"));
				bill1.setDueDate(rs.optString("Due_Date"));
				bill1.setName(rs.optString("Customer_Name"));
				bill1.setServiceProviderName(rs.optString("servicename"));
				bill1.setBillEmail(rs.optString("EmailId"));
				bill1.setTransactionId(rs.optString("TransId"));
				bill1.setCreatedDate(rs.optString("gettimestamp"));

				if (rs.getInt("partpaymentallow") == 1) {
					bill1.setPartpaymentallow(true);
				} else {
					bill1.setPartpaymentallow(false);
				}

				bill1.setPartpaymenttype(rs.optString("partpaymenttype"));
				bsnlBillDetails.add(bill1);
			}
		} catch (Exception ex) {
			logger.error("EncryptDecryptDAOImpl, Exception in getBsnlDetails : ", ex);
		}

		return bsnlBillDetails;
	}

	@Override
	public List<Phed> getPhedDetails(String apiResult, Bill bill, Phed phed) {
		String dbTransID = null;
		List<Phed> details = new ArrayList<Phed>();
		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(apiResult);
			JSONObject fetchobj = jsonObject.getJSONObject("FetchDetails");
			JSONObject transobj = (JSONObject) fetchobj.get("TransactionDetails");
			JSONArray billarray = fetchobj.getJSONArray("BillDetails");

			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "transactioninsertWater");
			queryDetails.addQueryParams(2, transobj.get("ServiceName").toString());
			queryDetails.addQueryParams(3, transobj.get("officeID").toString());
			queryDetails.addQueryParams(4, transobj.get("BillAmount").toString());

			String ConsumerName = transobj.get("ConsumerName").toString();

			try {
				ConsumerName = StringEscapeUtils.unescapeJava(StringEscapeUtils
						.unescapeHtml4(URLDecoder.decode(ConsumerName.replaceAll("%u", "\\\\u"), "UTF-8")));
			} catch (UnsupportedEncodingException ex) {
				logger.error("EncryptDecryptDAOImpl, Exception in getPhedDetails : "+ex.getMessage());
				/*ex.printStackTrace();*/
			}

			queryDetails.addQueryParams(5, ConsumerName);

			queryDetails.addQueryParams(6, transobj.get("consumerKeysValues").toString());
			queryDetails.addQueryParams(7, transobj.get("partPaymentAllow").toString());
			queryDetails.addQueryParams(8, transobj.get("partPaymentType").toString());
			queryDetails.addQueryParams(9, transobj.get("lookUpId").toString());
			queryDetails.addQueryParams(10, transobj.get("officeCodeValue").toString());

			// 20 and 21
			int temp = 11;
			for (int index = 0; index < billarray.length(); index++) {
				JSONObject jsonLineItem = billarray.getJSONObject(index);
				String value = (String) jsonLineItem.get("LableValue");

				if (value == null || value.equals(""))
					queryDetails.addQueryParams(temp, null);
				else
					queryDetails.addQueryParams(temp, value);

				temp++;
				if (temp == 26) {
					break;
				}
			}

			queryDetails.addQueryParams(26, String.valueOf(bill.getServiceProviderID()));
			queryDetails.addQueryParams(27, bill.getBillEmail());
			queryDetails.addQueryParams(28, bill.getBillMobileNo());
			queryDetails.addQueryParams(29, bill.getBillEmail());
			queryDetails.addQueryParams(30, bill.getSsoID());

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
			JSONArray resultSet = serviceResponse.getJsonArray();
			if (null != resultSet && resultSet.length() > 0) {
				org.json.JSONObject rs1 = (org.json.JSONObject) resultSet.get(0);
				dbTransID = rs1.optString("TransId");
			}

			QueryDetails queryDetails1 = new QueryDetails("proc_kiosk");

			queryDetails1.addQueryParams(1, "selectbillsWater");
			queryDetails1.addQueryParams(2, dbTransID);
			Phed ph1 = null;
			DBServiceResponse serviceResponse1 = dbHTTPUtils.pullDataFromDBApiServer(queryDetails1);
			JSONArray resultSet1 = serviceResponse1.getJsonArray();
			if (null != resultSet1 && resultSet1.length() > 0) {
				org.json.JSONObject rs = (org.json.JSONObject) resultSet1.get(0);
				ph1 = new Phed();
				ph1.setEmitraCId(rs.optString("EmitraCId"));
				ph1.setSubDivision(rs.optString("SubDivision"));
				ph1.setChoukNumber(rs.optString("ChoukNumber"));
				ph1.setAccountNumber(rs.optString("AccountNumber"));
				ph1.setBillMonth(rs.optString("BillMonth"));
				ph1.setBillYear(rs.optString("BillYear"));
				ph1.setBillDate(rs.optString("BillDate"));
				ph1.setAmountBeforeDueDate(rs.optString("AmountBeforeDueDate"));
				ph1.setAmountAfterDueDate(rs.optString("AmountAfterDueDate"));
				ph1.setMobileNumber(rs.optString("MobileNumber"));
				ph1.setConsumerName(rs.optString("ConsumerName"));
				ph1.setCashDueDate(rs.optString("CashDueDate"));
				ph1.setChequeDueDate(rs.optString("ChequeDueDate"));
				ph1.setAddress(rs.optString("Address"));
				ph1.setAddress2(rs.optString("Address2"));
				ph1.setTranBillId(rs.optString("TranBillId"));
				ph1.setServiceName(rs.optString("servicename"));
				ph1.setBillAmount(rs.optString("billamount"));
				ph1.setConsumerKeyValue(rs.optString("consumerkeyvalue"));
				ph1.setPartPaymentType(rs.optString("partpaymenttype"));
				ph1.setLookUpID(rs.optString("lookupid"));
				ph1.setSsoID(rs.optString("SsoId"));
				ph1.setBillEmailId(rs.optString("EmailId"));
				ph1.setCreatedDate(rs.optString("gettimestamp"));
				details.add(ph1);
			}
			logger.info("PHED  list size :" + details.size());
		} catch (Exception ex) {
			logger.error("EncryptDecryptDAOImpl, Exception in phedDetails : " + ex.getMessage());
		}

		return details;
	}
}