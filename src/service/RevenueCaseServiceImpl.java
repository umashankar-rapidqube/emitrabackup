package service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.RevenueCaseDao;
import model.RevenueCaseModel;

@Service("revenueCaseService")
public class RevenueCaseServiceImpl implements RevenueCaseService {

	@Autowired
	RevenueCaseDao revenueCaseDao;
	
	static final Logger logger = Logger.getLogger(RevenueCaseServiceImpl.class);
	private static final String REQUESTURL = "http://rcms.landrevenue.rajasthan.gov.in/rcmsscripts/ASBIViewRest.dll/datasnap/rest/TASBIViewREST/getiview";
	
	@SuppressWarnings({"unchecked" })
	@Override
	public JSONObject getRevenueMasterData(HttpServletRequest req) {
		// TODO Auto-generated method stub
		JSONObject requestObj = new JSONObject();
		JSONArray districtMaster = getMasterObj(req , "1" , "0" , "1");
		logger.debug("getRevenueMasterData, Parsed Data  (DISTRICT NAME) : " + districtMaster + " for fetching details.");
		JSONArray courtTypeMaster = getMasterObj(req , "6" , "100" , "1");
		logger.debug("getRevenueMasterData, Parsed Data  (COURT TYPE) : " + courtTypeMaster + " for fetching details.");
		
		requestObj.put("district", districtMaster);
		requestObj.put("courtType", courtTypeMaster);
		logger.debug("getRevenueMasterData, PARAMETER Data : "+requestObj+" for fetching details.");
		
		return requestObj;
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray getMasterObj(HttpServletRequest request , String mastId , String pageSize , String type) {
		// TODO Auto-generated method stub
		JSONObject paramObj = new JSONObject();
			paramObj.put("mastid" , mastId);
		JSONObject getParameterObj = getParameterObj("ivmdata" , pageSize , paramObj);
		JSONObject masterObj = getResponseFromApi(getParameterObj);
		logger.debug("getRevenueMasterData, Master Object : " + masterObj + " for fetching details.");
		JSONArray masterArr = parseData(request , masterObj , type);
		return masterArr;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getCourtName(HttpServletRequest request , String districtName , String courtType) {
		// TODO Auto-generated method stub
		JSONObject paramObj = new JSONObject();
		paramObj.put("dname" , districtName.trim());
		paramObj.put("ctype" , courtType.trim());
		JSONObject getParameterObj = getParameterObj("emcns" , "20" , paramObj);
		JSONObject courtNameObj = getResponseFromApi(getParameterObj);
		JSONArray courtNameArr = parseData(request , courtNameObj , "2");
		logger.debug("getRevenueMasterData, Parsed Data (COURT NAME) : " + courtNameArr + " for fetching details.");
		return courtNameArr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getCaseStatus(HttpServletRequest request , String courtName, String caseNo) {
		// TODO Auto-generated method stub
		JSONObject responseJson = new JSONObject();
		
		JSONObject paramObj = new JSONObject();
		paramObj.put("cname" , courtName.trim());
		paramObj.put("cno" , caseNo.trim());
		
		JSONObject getParameterObj = getParameterObj("ecss" , "20" , paramObj);
		JSONObject caseStatusObj = getResponseFromApi(getParameterObj);
		JSONArray caseStatusArr = parseData(request , caseStatusObj , "3");
		logger.debug("getRevenueMasterData, Parsed Data (CASE STATUS) : " + caseStatusArr + " for fetching details.");
		JSONObject caseStatus = (JSONObject) caseStatusArr.get(0);
		
		Set<String> keySet =  caseStatus.keySet();
		
		responseJson.put("data", caseStatus);
		responseJson.put("keys", keySet);
		logger.debug("getRevenueMasterData, Response Data For Case Status : " + responseJson + " for fetching details.");
		return responseJson;
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray parseData(HttpServletRequest request , JSONObject dataObj, String type) {
		// TODO Auto-generated method stub
		JSONArray response = new JSONArray();
		
		JSONArray row = (JSONArray) ((JSONObject)((JSONArray) dataObj.get("result")).get(0)).get("row");
		
		for (int i = 0; i < row.size(); i++) {
			JSONObject obj = (JSONObject) row.get(i);
			JSONObject tempObj = new JSONObject();
			if(type.equals("1")) {
				tempObj.put("valueid", String.valueOf(obj.get("valueid")));
				tempObj.put("valueeng", String.valueOf(obj.get("valueeng")));
				tempObj.put("valuenamehi", String.valueOf(obj.get("valuenamehi")));
			
			}else if(type.equals("2")) {
				tempObj.put("rowno", String.valueOf(obj.get("rowno")));
				tempObj.put("court_name", String.valueOf(obj.get("court_name")));
			
			}else if(type.equals("3")) {
				tempObj.put("applicantName", String.valueOf(obj.get("primary_appellant")));
				tempObj.put("appAdv", String.valueOf(obj.get("app_adv")));
				tempObj.put("respondentName", String.valueOf(obj.get("primary_respondent")));
				tempObj.put("respAdv", String.valueOf(obj.get("resp_adv")));
				tempObj.put("actName", String.valueOf(obj.get("act_name")));
				tempObj.put("secCode", String.valueOf(obj.get("section_code")));
				tempObj.put("caseType", String.valueOf(obj.get("case_type")));
				tempObj.put("caseReason", String.valueOf(obj.get("case_purpose_name")));
				tempObj.put("caseNo", String.valueOf(obj.get("case_no")));
				tempObj.put("manualCaseNo", String.valueOf(obj.get("manual_case_no")));
				tempObj.put("caseStartDate", String.valueOf(obj.get("institution_date")));
				tempObj.put("nxtHearingDate", String.valueOf(obj.get("next_hearingdate")));
				String directory = System.getProperty("catalina.base") + "\\webapps";
				String filaName = String.valueOf(obj.get("case_no")).replace("/", "");
				String url = String.valueOf(obj.get("doc"));
				JSONObject downloadData = downloadFile(request , url , directory , filaName); 
				tempObj.put("pdfUrl", downloadData.get("pdfUrl"));
				tempObj.put("fileSize", downloadData.get("fileSize"));
				tempObj.put("origPdfUrl", url);
				tempObj.put("finalBench", String.valueOf(obj.get("bench")));
			}
			response.add(tempObj);
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getParameterObj(String name , String pageSize , JSONObject paramsObj) {
		JSONObject getViewObj = new JSONObject();
		getViewObj.put("name", name);
		getViewObj.put("axpapp", "rcms");
		getViewObj.put("username", "mobileuser");
		getViewObj.put("password", "827ccb0eea8a706c4c34a16891f84e7b");
		getViewObj.put("seed", "");
		getViewObj.put("s", "");
		getViewObj.put("pageno", "1");
		getViewObj.put("pagesize", pageSize);
		getViewObj.put("sqlpagination", "true");
		getViewObj.put("params", paramsObj);
		
		JSONObject paramObj = new JSONObject();
		paramObj.put("getiview", getViewObj);
		JSONArray paramArr = new JSONArray();
		paramArr.add(paramObj);
		JSONObject requestObj = new JSONObject();
		requestObj.put("_parameters", paramArr);
		logger.debug("getParameterObj, Parameter Object : " + requestObj + " for fetching details.");
		return requestObj;
	}

	private JSONObject getResponseFromApi(JSONObject requestObj) {
		
		JSONObject responseJson = new JSONObject();
		StringBuilder responseString = new StringBuilder();
		try {
			logger.debug("getRevenueMasterData, REQUEST URL : " + REQUESTURL + " for fetching details.");
			URL url = new URL(REQUESTURL);
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Length", String.valueOf(requestObj.size()));
			connection.setRequestProperty("Content-Type", "raw;UTF-8");
			connection.setDoInput(true);
			connection.setDoOutput(true);

			byte[] postDataBytes = requestObj.toString().getBytes(StandardCharsets.UTF_8);
			OutputStream os = connection.getOutputStream();
			os.write(postDataBytes);
			os.flush();
			
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				logger.debug("getRevenueMasterData, response code : " + responseCode + " due to API server error");
			} else {
				logger.debug("getRevenueMasterData, response code : " + responseCode);
				
				Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				for (int c; (c = in.read()) >= 0;) {
					responseString.append((char) c);
				}
				
				logger.debug("getRevenueMasterData, response data : " + responseString);
				
				String OBJ_STRING = responseString.toString();
	            JSONParser parser = new JSONParser();
	            responseJson = (JSONObject) parser.parse(OBJ_STRING);
	            
	            logger.debug("getRevenueMasterData, response jsonafter conversion : " + responseJson);
			}
		} catch (Exception e) {
			logger.error("RevenueCaseServiceImpl, getRevenueMasterData, Exception msg :: " + e.getMessage());
			/*e.printStackTrace();*/
		}
		
		return responseJson;
	}
	
	public static void main(String[] args) {
		new RevenueCaseServiceImpl().getCaseStatus(null , "ADC, अजमेर" , "2018/00001");
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject downloadFile(HttpServletRequest req , String pdfUrl , String directory, String fileName) {
		JSONObject responseJson = new JSONObject();
		try {
			URL url = new URL(pdfUrl);
			directory = directory + File.separator + "revenuePdf";
			File f = new File(directory);
			if (!f.exists()) {
				f.mkdirs();
			} else {
				File allfile[] = f.listFiles();
				for (int i = 0; i < allfile.length; i++) {
					if (allfile[i].isFile()) {
						allfile[i].delete();
					}
				}
			}

			f = new File(directory + File.separator + fileName + ".pdf");
			if (!f.exists())
				f.createNewFile();
			InputStream in = new BufferedInputStream(url.openStream());
			FileOutputStream fos = new FileOutputStream(f);
			int length = -1;
			byte[] buffer = new byte[1024];
			while ((length = in.read(buffer)) > -1) {
				fos.write(buffer, 0, length);
			}
			fos.close();
			in.close();
			if (f.exists()) {
				directory = "http://localhost:" + req.getLocalPort() + "/revenuePdf/" + fileName + ".pdf";
				logger.debug("downloadFile, file URL : " + directory + " &&   file Size : " + f.length());
				responseJson.put("pdfUrl", directory);
				responseJson.put("fileSize", f.length());
			}
		}catch(Exception e) {
			responseJson.put("pdfUrl", "");
			responseJson.put("fileSize", "0");
		}
		logger.debug("downloadFile, response for downloaded dile : " + responseJson);
		return responseJson;
	}

	@Override
	public String saveRevenueCaseDetails(RevenueCaseModel revenueCaseModel) {
		// TODO Auto-generated method stub
		return revenueCaseDao.saveRevenueCaseDetails(revenueCaseModel);
	}
}