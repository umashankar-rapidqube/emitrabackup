package dao;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.Bill;
import model.CertificateInfo;
import model.DBServiceResponse;
import model.Login;
import model.QueryDetails;
import utils.DBHTTPUtils;

@Repository("certificateDao")
public class CertificateDAOImpl implements CertificateDAO {

	static final Logger logger = Logger.getLogger(BillDAOImpl.class);

	@Autowired
	DBHTTPUtils dbHTTPUtils;

	@Override
	public String insertCertificateDetails(CertificateInfo certiInfo) {
		String dbTransID = null;
		try {
			QueryDetails queryDetails = new QueryDetails("Proc_PrintCertificate");
			queryDetails.setAutoCommit(false);

			queryDetails.addQueryParams(1, "Insert");
			queryDetails.addQueryParams(2, certiInfo.getRegistrationNo());
			queryDetails.addQueryParams(3, certiInfo.getYear());
			queryDetails.addQueryParams(4, "");
			queryDetails.addQueryParams(5, Login.SSOID);
			queryDetails.addQueryParams(6,
					((certiInfo.getServiceID() == "") || (certiInfo.getServiceID() == null)) ? "2289"
							: certiInfo.getServiceID());
			queryDetails.addQueryParams(7, certiInfo.getSubServiceID());
			queryDetails.addQueryParams(8, certiInfo.getServiceName());

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);

			JSONArray resultSet = serviceResponse.getJsonArray();
			if (null != resultSet && resultSet.length() > 0) {
				org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
				dbTransID = rs.optString("TransId");
			}
		} catch (Exception ex) {
			logger.error("CertificateDAOImpl, Exception in insertCertificateDetails : ", ex);
		}

		return dbTransID;
	}

	@Override
	public String getCertificateTransID(String certiInfo) {
		String trnsid = "";
		try {
			QueryDetails queryDetails = new QueryDetails("Proc_PrintCertificate");
			queryDetails.setAutoCommit(false);

			queryDetails.addQueryParams(1, "Select");
			queryDetails.addQueryParams(2, certiInfo);

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);

			JSONArray resultSet = serviceResponse.getJsonArray();
			if (null != resultSet && resultSet.length() > 0) {
				org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
				trnsid = rs.optString("TranId");
			}
		} catch (Exception ex) {
			logger.error("CertificateDAOImpl, Exception in getCertificateTransID : ", ex);
		}

		return trnsid;
	}

	@Override
	public Bill getCertiPrintBillDetails(Bill bill) {

		Bill printCertiBillInfo = new Bill();
		logger.debug("getCertiPrintBillDetails, serviceProviderID : " + bill.getServiceProviderID());
		try {
			QueryDetails queryDetails = new QueryDetails("proc_kiosk");

			queryDetails.addQueryParams(1, "ShowCertiPrintRerceipt");
			queryDetails.addQueryParams(2, bill.getTransactionId());

			DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
			JSONArray resultSet = serviceResponse.getJsonArray();

			if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
				org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
				printCertiBillInfo.setBillAmount(rs.optString("TRANSAMT"));
				printCertiBillInfo.setBillMobileNo(rs.optString("MobileNo"));
				printCertiBillInfo.setrECEIPTNO(rs.optString("RECEIPTNO"));
				printCertiBillInfo.setCertiBackToBackTransactionID(rs.optString("TRANSACTIONID"));
				printCertiBillInfo.setCertiServiceID(rs.optString("ServiceId"));
				printCertiBillInfo.setCertiSubServiceID(rs.optString("SubServiceId"));
				printCertiBillInfo.setTransactionDate(rs.optString("TransactionDate"));
				printCertiBillInfo.setmSG(rs.optString("MSG"));
				printCertiBillInfo.setPaymentMode(rs.optString("PaymentMode"));
			}
		} catch (Exception ex) {
			logger.error("CertificateDAOImpl, Exception in getCertiPrintBillDetails : ", ex);
		}

		return printCertiBillInfo;
	}
}