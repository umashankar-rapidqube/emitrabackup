package dao;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import commonServices.ReceiptPrint;
import model.DBServiceResponse;
import model.QueryDetails;
import model.RevenueCaseModel;
import utils.DBHTTPUtils;

@Repository("revenueCaseDao")
public class RevenueCaseDaoImpl implements RevenueCaseDao {

	@Autowired
    private DBHTTPUtils dbHTTPUtils;
	
	static final Logger logger = Logger.getLogger(RevenueCaseDaoImpl.class);
	
	@Override
	public String saveRevenueCaseDetails(RevenueCaseModel revenueCaseModel) {
		// TODO Auto-generated method stub
		String requestId = null;
		try {
            QueryDetails queryDetails = new QueryDetails("proc_kiosk");
            queryDetails.setAutoCommit(false);

            queryDetails.addQueryParams(1, "transactioninsertrevenuecourt");
            queryDetails.addQueryParams(2, revenueCaseModel.getLangCode());
			queryDetails.addQueryParams(3, revenueCaseModel.getDistrictName());
			queryDetails.addQueryParams(4, revenueCaseModel.getcName());
			queryDetails.addQueryParams(5, revenueCaseModel.getcType());
			queryDetails.addQueryParams(6, revenueCaseModel.getManualCaseNo());
			queryDetails.addQueryParams(7, revenueCaseModel.getApplicantName());
			queryDetails.addQueryParams(8, revenueCaseModel.getAppAdv());
			queryDetails.addQueryParams(9, revenueCaseModel.getRespondentName());
			queryDetails.addQueryParams(10, revenueCaseModel.getRespAdv());
			queryDetails.addQueryParams(11, revenueCaseModel.getActName());
			queryDetails.addQueryParams(12, revenueCaseModel.getCaseType());
			queryDetails.addQueryParams(13, revenueCaseModel.getCaseReason());
			queryDetails.addQueryParams(14, ReceiptPrint.convertDate(revenueCaseModel.getCaseStartDate(), "dd/MM/yyyy", "yyyy-MM-dd"));
			queryDetails.addQueryParams(15, ReceiptPrint.convertDate(revenueCaseModel.getNxtHearingDate(), "dd/MM/yyyy", "yyyy-MM-dd"));
			queryDetails.addQueryParams(16, revenueCaseModel.getFinalBench());
			queryDetails.addQueryParams(17, revenueCaseModel.getSecCode());
			queryDetails.addQueryParams(18, revenueCaseModel.getCaseNo());
			queryDetails.addQueryParams(19, revenueCaseModel.getOrigPdfUrl());
			logger.debug("Query Parameter of saveRevenueCaseDetails:" + queryDetails);
            DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
            logger.debug("Response status of saveRevenueCaseDetails:" + serviceResponse.getStatus());
            if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
                JSONArray resultSet = serviceResponse.getJsonArray();
                if (null != resultSet && resultSet.length() > 0) {
                	org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
                	requestId = rs.optString("transId");
                	logger.debug("RequestId Generated For of saveRevenueCaseDetails:" + requestId);
                }
            }
        } catch (Exception ex) {
            logger.error("RevenueCaseDaoImpl, Exception in insertBhamashahPaymentDetails : ", ex);
        }
		
		return requestId;
	}
}