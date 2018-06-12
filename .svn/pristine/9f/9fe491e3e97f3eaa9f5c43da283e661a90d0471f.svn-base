package dao;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.DBServiceResponse;
import model.PUCModel;
import model.QueryDetails;
import utils.DBHTTPUtils;

@Repository("PUCDao")
public class PUCDaoImpl implements PUCDao {

	static final Logger logger = Logger.getLogger(PUCDaoImpl.class);

	@Autowired
	private DBHTTPUtils dbHTTPUtils;
	
	@Override
	public JSONObject insertData(PUCModel pucModel) {
		// TODO Auto-generated method stub
		String requestId = null;
		try {
            QueryDetails queryDetails = new QueryDetails("proc_kiosk");
            queryDetails.setAutoCommit(false);

            queryDetails.addQueryParams(1, "transactioninsertPUC");
            queryDetails.addQueryParams(2, String.valueOf(pucModel.getFuelType()));
            queryDetails.addQueryParams(3, String.valueOf(pucModel.getPayableAmt()));
            queryDetails.addQueryParams(4, String.valueOf(pucModel.getTotalAmount()));
            queryDetails.addQueryParams(5, String.valueOf(pucModel.getServiceProviderName()));
            queryDetails.addQueryParams(6, String.valueOf(pucModel.getLangCode()));
            queryDetails.addQueryParams(7, String.valueOf(pucModel.getConsumerKey()));
            queryDetails.addQueryParams(8, String.valueOf(pucModel.getOthCharges()));
            queryDetails.addQueryParams(9, String.valueOf(pucModel.getExpDuration()));
            queryDetails.addQueryParams(10, String.valueOf(pucModel.getCommCharges()));
            queryDetails.addQueryParams(11, String.valueOf(pucModel.getVehicleType()));
            queryDetails.addQueryParams(12, String.valueOf(pucModel.getConsumerName()));
            queryDetails.addQueryParams(13, String.valueOf(pucModel.getVehicleRegNo()));
            queryDetails.addQueryParams(14, String.valueOf(pucModel.getSsoId()));
            queryDetails.addQueryParams(15, String.valueOf(pucModel.getServiceProviderID()));
            queryDetails.addQueryParams(16, pucModel.getEmailID() != null && pucModel.getEmailID() != "" ? String.valueOf(pucModel.getEmailID()) : "helpdesk.emitra@rajasthan.gov.in");
            queryDetails.addQueryParams(17, String.valueOf(pucModel.getMobileNo()));
            queryDetails.addQueryParams(18, "TRANSPORTHQ");
            logger.debug("Query Parameter of insertDataOfPUC:" + queryDetails);
            DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
            logger.debug("Response status of insertDataOfPUC:" + serviceResponse.getStatus());
            if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
                JSONArray resultSet = serviceResponse.getJsonArray();
                if (null != resultSet && resultSet.length() > 0) {
                	org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
                	requestId = rs.optString("TransId");
                	logger.debug("RequestId Generated For of insertDataOfPUC:" + requestId);
                }
            }
        } catch (Exception ex) {
            logger.error("PUCDaoImpl, Exception in insertDataOfPUC : ", ex);
        }
		
		JSONObject pucDetails = fetchedPUCDetails(requestId);
		
		
		return pucDetails;
	}

	private JSONObject fetchedPUCDetails(String requestId) {
		// TODO Auto-generated method stub
		org.json.JSONObject responseJson = new org.json.JSONObject();
		try {
            QueryDetails queryDetails = new QueryDetails("proc_kiosk");
            queryDetails.setAutoCommit(false);

            queryDetails.addQueryParams(1, "transactionselectPUC");
            queryDetails.addQueryParams(2, requestId);
            
            logger.debug("Query Parameter of fetchedPUCDetails:" + queryDetails);
            DBServiceResponse serviceResponse = dbHTTPUtils.pullDataFromDBApiServer(queryDetails);
            logger.debug("Response status of fetchedPUCDetails:" + serviceResponse.getStatus());
            if (HttpStatus.SC_OK == serviceResponse.getStatus()) {
                JSONArray resultSet = serviceResponse.getJsonArray();
                if (null != resultSet && resultSet.length() > 0) {
                	org.json.JSONObject rs = (org.json.JSONObject) resultSet.get(0);
                	responseJson = rs;
                	logger.debug("Response JSON of fetchedPUCDetails:" + responseJson);
                }
            }
        } catch (Exception ex) {
            logger.error("PUCDaoImpl, Exception in fetchedPUCDetails : ", ex);
        }
		
		return responseJson;
	}
}