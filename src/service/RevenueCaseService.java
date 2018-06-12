package service;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.RevenueCaseModel;

public interface RevenueCaseService {

	public JSONObject getRevenueMasterData(HttpServletRequest req);

	public JSONArray getCourtName(HttpServletRequest req , String districtKey, String ctypeKey);

	public JSONObject getCaseStatus(HttpServletRequest req , String courtKey, String caseNo);

	public String saveRevenueCaseDetails(RevenueCaseModel revenueCaseModel);

}
