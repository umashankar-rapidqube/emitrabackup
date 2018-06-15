package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import model.Bill;
import model.FamilyDetail;
import model.InfoKioskDetail;
import model.RationBean;

@Service("infoPanchayatService")
public class InfoPanchayatServiceImpl implements InfoPanchayatService {
	
	static final Logger logger = Logger.getLogger(InfoPanchayatServiceImpl.class);
	private static final String CLIENTID_EMITRA_ID_PROD = "d4c1624f-cc0b-40bf-af73-4779b5a9293f"; // PROD
	private final String USER_AGENT = "Mozilla/5.0";
	
	
	
private String getResponseFromServerAPI(HttpServletRequest req , String machineId) {
		
		HttpURLConnection conn = null;

		String urlParameters = "SSOIDGOVT=" + "" + "&MACHINESERIALNO=" + machineId;

		String requestUrl = "http://reportsemitraapp.rajasthan.gov.in/emitraReportsRepository/mgetMachineDetailEmitraPlus";
		StringBuilder data = new StringBuilder();

		try {
			URL obj = new URL(requestUrl);
			byte[] postDataBytes = urlParameters.getBytes(StandardCharsets.UTF_8);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;UTF-8");

			conn.setDoOutput(true);

			try (OutputStream os = conn.getOutputStream()) {
				os.write(postDataBytes);
				os.flush();
			}
			System.out.println("InfoPanchayatController.InformationPanchayatService()" + conn.getResponseCode());
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {

			} else {

				try (Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
					for (int c; (c = in.read()) >= 0;) {
						data.append((char) c);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data.toString();
	}
	
	
	@Override
	public HttpServletRequest kioskInfoDetail(HttpServletRequest req, String machineId) {
		// TODO Auto-generated method stub
		String data = getResponseFromServerAPI(req , machineId);
		try {
			HttpURLConnection conn = null;

			JSONParser parser = new JSONParser();
			org.json.simple.JSONArray obj = (org.json.simple.JSONArray) parser.parse(data.toString());
			List<InfoKioskDetail> list = new ArrayList<>();

			JSONObject json = (JSONObject) obj.get(0);
			String DISTRICT = String.valueOf(json.get("DISTRICTCODE"));
			String ISRURAL = String.valueOf(json.get("ISRURAL"));
			String WARD = "";
			String MUNCIPALITYID = "";
			String PANCHAYATSAMITIID = "";
			String GRAMPANCHAYAT = "";
			String addressType = "";
			if (ISRURAL.equalsIgnoreCase("N")) {
				WARD = "false";
				MUNCIPALITYID = String.valueOf(json.get("MUNCIPALITYID"));
				PANCHAYATSAMITIID = "false";
				GRAMPANCHAYAT = "false";
				addressType = "PROPOSED";
			} else if (ISRURAL.equalsIgnoreCase("Y")) {
				WARD = "false";
				MUNCIPALITYID = "false";
				PANCHAYATSAMITIID = String.valueOf(json.get("PANCHAYATSAMITIID"));
				GRAMPANCHAYAT = "false";
				addressType = "PROPOSED";
			}
			req.setAttribute("ISRURAL", new String(((String) json.get("ISRURAL")).getBytes(), "UTF-8").toUpperCase());
			req.setAttribute("DISTRICT", new String(((String) json.get("DISTRICT")).getBytes(), "UTF-8"));
			req.setAttribute("WARD", new String(((String) json.get("WARD")).getBytes(), "UTF-8"));
			req.setAttribute("MUNCIPALITY", new String(((String) json.get("MUNCIPALITY")).getBytes(), "UTF-8"));
			req.setAttribute("PANCHAYATSAMITI", new String(((String) json.get("PANCHAYATSAMITI")).getBytes(), "UTF-8"));
			req.setAttribute("GRAMPANCHAYAT", new String(((String) json.get("GRAMPANCHAYAT")).getBytes(), "UTF-8"));

			String urlParameters = "district=" + DISTRICT + "&ward=" + WARD + "&muncipality=" + MUNCIPALITYID
					+ "&panchayatSamiti=" + PANCHAYATSAMITIID + "&gramPanchayat=" + GRAMPANCHAYAT + "&addressType="
					+ addressType + "&isRural=" + ISRURAL;

			String requestUrl = "https://emitraapp.rajasthan.gov.in/webServicesRepository/mgetLocationKioskDistrictDetailWiseMobile";

			StringBuilder data1 = new StringBuilder();

			URL obj1 = new URL(requestUrl);
			byte[] postDataBytes = urlParameters.getBytes(StandardCharsets.UTF_8);
			conn = (HttpURLConnection) obj1.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;UTF-8");
			conn.setDoOutput(true);

			try (OutputStream os = conn.getOutputStream()) {
				os.write(postDataBytes);
				os.flush();
			}
			System.out.println("InfoPanchayatController.InformationPanchayatService()" + conn.getResponseCode());
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {

			} else {

				try (Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
					for (int c; (c = in.read()) >= 0;) {
						data1.append((char) c);
					}

				}
			}
			obj = (org.json.simple.JSONArray) parser.parse(data1.toString());
			JSONObject json1 = new JSONObject();

			int length1 = obj.size();
			for (int i = 0; i < length1; i++) {
				InfoKioskDetail Info = new InfoKioskDetail();
				json1 = (JSONObject) obj.get(i);
				String vendorName = String.valueOf(json1.get("vendorName"));
				String phoneNo = String.valueOf(json1.get("phoneNumber1"));
                String district = String.valueOf(json1.get("district"));
                String Address = String.valueOf(json1.get("Address"));
				
				Info.setVendorName(vendorName);
				Info.setPhoneNo(phoneNo);

				Info.setDistrict(district);

				Info.setAddress(Address);
				list.add(Info);
			}
			req.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return req;
	}

	

	@Override
	public HttpServletRequest bhamasaInfoDetail(HttpServletRequest req, String machineId) {
		// TODO Auto-generated method stub
		HttpURLConnection connection = null;
		String BLOCK_ID = req.getParameter("stateId");
		String gp_id = req.getParameter("gp_id");
		String village_id = req.getParameter("vill_id");

		String requestUrl;

		String data = getResponseFromServerAPI(req , machineId);
		try {
			JSONParser parser = new JSONParser();
			org.json.simple.JSONArray obj1 = (org.json.simple.JSONArray) parser.parse(data.toString());
			List<InfoKioskDetail> list = new ArrayList<>();

			JSONObject json = (JSONObject) obj1.get(0);
			String STATEREPODISTRICTID = String.valueOf(json.get("STATEREPODISTRICTID"));
	
			req.setAttribute("ISRURAL", new String(((String) json.get("ISRURAL")).getBytes(), "UTF-8").toUpperCase());
			req.setAttribute("DISTRICT", new String(((String) json.get("DISTRICT")).getBytes(), "UTF-8"));
			req.setAttribute("WARD", new String(((String) json.get("WARD")).getBytes(), "UTF-8"));
			req.setAttribute("MUNCIPALITY", new String(((String) json.get("MUNCIPALITY")).getBytes(), "UTF-8"));
			req.setAttribute("PANCHAYATSAMITI", new String(((String) json.get("PANCHAYATSAMITI")).getBytes(), "UTF-8"));
			req.setAttribute("GRAMPANCHAYAT", new String(((String) json.get("GRAMPANCHAYAT")).getBytes(), "UTF-8"));
			// staging client_id= 0d1c452f-5711-462b-8f58-bf5156ac3285
			
			if (BLOCK_ID != null && !BLOCK_ID.isEmpty()) {
				req.setAttribute("level2", "level2");
				requestUrl = "https://api.sewadwaar.rajasthan.gov.in/app/live/Bhamashah/Prod/Service/action/gpBlockDtl/S/0/" + BLOCK_ID + "/0/0?client_id=d4c1624f-cc0b-40bf-af73-4779b5a9293f";
			} else if (gp_id != null && !gp_id.isEmpty()) {
				
				req.setAttribute("level3", "level3");
				requestUrl = "https://api.sewadwaar.rajasthan.gov.in/app/live/Bhamashah/Prod/Service/action/gpBlockDtl/S/0/0/" + gp_id +"/0?client_id=d4c1624f-cc0b-40bf-af73-4779b5a9293f";
			}else if (village_id != null && !village_id.isEmpty()) {
				req.setAttribute("level4", "level4");
				requestUrl = "https://api.sewadwaar.rajasthan.gov.in/app/live/Bhamashah/Prod/Service/action/gpBlockDtl/D/0/0/0/"+village_id+"?client_id=d4c1624f-cc0b-40bf-af73-4779b5a9293f";
				
			} else {
				req.setAttribute("level1", "level1");
				requestUrl = "https://api.sewadwaar.rajasthan.gov.in/app/live/Bhamashah/Prod/Service/action/gpBlockDtl/S/" + STATEREPODISTRICTID+ "/0/0/0?client_id=d4c1624f-cc0b-40bf-af73-4779b5a9293f";
}
			
			StringBuilder data1 = new StringBuilder();
			URL obj2 = new URL(requestUrl);
			connection = (HttpURLConnection) obj2.openConnection();

			connection.setRequestMethod("GET");

			// add request header
			connection.setRequestProperty("User-Agent", USER_AGENT);
			connection.setDoOutput(true);
			int responseCode = connection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {

			} else {

				try (Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
					for (int c; (c = in.read()) >= 0;) {
						data1.append((char) c);
					}

				}
			}
			obj1 = (org.json.simple.JSONArray) parser.parse(data1.toString());
			JSONObject json1 = new JSONObject();

			int length1 = obj1.size();
			for (int i = 0; i < length1; i++) {
				InfoKioskDetail Info = new InfoKioskDetail();
				json1 = (JSONObject) obj1.get(i);
				String block_eng = String.valueOf(json1.get("BLOCK_ENG"));
				String GP_ENG = String.valueOf(json1.get("GP_ENG"));
				String village_eng = String.valueOf(json1.get("VILLAGE_ENG"));
				String FATHER_NAME_HND = String.valueOf(json1.get("FATHER_NAME_HND"));
				String HOF_NAME_HND = String.valueOf(json1.get("HOF_NAME_HND"));
				String ADDRESS = String.valueOf(json1.get("ADDRESS"));
				String count = String.valueOf(json1.get("COUNT(1)"));
				
				String BLOCK_ID1 = json1.get("BLOCK_ID") != null &&json1.get("BLOCK_ID") != ""? String.valueOf(json1.get("BLOCK_ID")) : null;
				String GP_ID1 =  json1.get("GP_ID") != null && json1.get("GP_ID") != ""? String.valueOf(json1.get("GP_ID")) : null;
				String VILLAGE_ID =  json1.get("VILLAGE_ID") != null && json1.get("VILLAGE_ID") != null? String.valueOf(json1.get("VILLAGE_ID")) : null;
				
				Info.setGp_eng(GP_ENG);
				Info.setBlock_eng(block_eng);
				Info.setVillage_eng(village_eng);
				Info.setCount(count);
				Info.setBlock_id(BLOCK_ID1);
				Info.setGp_id(GP_ID1);
                Info.setVill_id(VILLAGE_ID);
            	Info.setFather_name_hnd(FATHER_NAME_HND);
				Info.setHof_name_hnd(HOF_NAME_HND);

				

				Info.setAddress(ADDRESS);
				list.add(Info);
				
			}
			req.setAttribute("list", list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return req;
	}
	
	@Override
	public HttpServletRequest SwasthyaBimaInfoDetail(HttpServletRequest req, String machineId) {
		// TODO Auto-generated method stub
				HttpURLConnection connection = null;
				String BLOCK_ID = req.getParameter("stateId");
				String gp_id = req.getParameter("gp_id");
				String village_id = req.getParameter("vill_id");

				String requestUrl;

				String data = getResponseFromServerAPI(req , machineId);
				try {
					JSONParser parser = new JSONParser();
					org.json.simple.JSONArray obj1 = (org.json.simple.JSONArray) parser.parse(data.toString());
					List<InfoKioskDetail> list = new ArrayList<>();

					JSONObject json = (JSONObject) obj1.get(0);
					String STATEREPODISTRICTID = String.valueOf(json.get("STATEREPODISTRICTID"));
			
					req.setAttribute("ISRURAL", new String(((String) json.get("ISRURAL")).getBytes(), "UTF-8").toUpperCase());
					req.setAttribute("DISTRICT", new String(((String) json.get("DISTRICT")).getBytes(), "UTF-8"));
					req.setAttribute("WARD", new String(((String) json.get("WARD")).getBytes(), "UTF-8"));
					req.setAttribute("MUNCIPALITY", new String(((String) json.get("MUNCIPALITY")).getBytes(), "UTF-8"));
					req.setAttribute("PANCHAYATSAMITI", new String(((String) json.get("PANCHAYATSAMITI")).getBytes(), "UTF-8"));
					req.setAttribute("GRAMPANCHAYAT", new String(((String) json.get("GRAMPANCHAYAT")).getBytes(), "UTF-8"));
					// staging client_id= 0d1c452f-5711-462b-8f58-bf5156ac3285
					
					
					if (BLOCK_ID != null && !BLOCK_ID.isEmpty()) {
						req.setAttribute("level2", "level2");
						requestUrl = "https://api.sewadwaar.rajasthan.gov.in/app/live/Bhamashah/Prod/Service/action/gpBlockDtl/BD/0/" + BLOCK_ID + "/0/0?client_id=d4c1624f-cc0b-40bf-af73-4779b5a9293f";
					} else if (gp_id != null && !gp_id.isEmpty()) {
						
						req.setAttribute("level3", "level3");
						requestUrl = "https://api.sewadwaar.rajasthan.gov.in/app/live/Bhamashah/Prod/Service/action/gpBlockDtl/BD/0/0/" + gp_id +"/0?client_id=d4c1624f-cc0b-40bf-af73-4779b5a9293f";
					}else if (village_id != null && !village_id.isEmpty()) {
						req.setAttribute("level4", "level4");
						requestUrl = "https://api.sewadwaar.rajasthan.gov.in/app/live/Bhamashah/Prod/Service/action/gpBlockDtl/D/0/0/0/"+village_id+"?client_id=d4c1624f-cc0b-40bf-af73-4779b5a9293f";
						
					} else {
						req.setAttribute("level1", "level1");
						requestUrl = "https://api.sewadwaar.rajasthan.gov.in/app/live/Bhamashah/Prod/Service/action/gpBlockDtl/BD/" + STATEREPODISTRICTID+ "/0/0/0?client_id=d4c1624f-cc0b-40bf-af73-4779b5a9293f";
		}
						
					StringBuilder data1 = new StringBuilder();
					URL obj2 = new URL(requestUrl);
					connection = (HttpURLConnection) obj2.openConnection();

					connection.setRequestMethod("GET");

					// add request header
					connection.setRequestProperty("User-Agent", USER_AGENT);
					connection.setDoOutput(true);
					int responseCode = connection.getResponseCode();
					if (responseCode != HttpURLConnection.HTTP_OK) {

					} else {

						try (Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
							for (int c; (c = in.read()) >= 0;) {
								data1.append((char) c);
							}

						}
					}
					obj1 = (org.json.simple.JSONArray) parser.parse(data1.toString());
					JSONObject json1 = new JSONObject();

					int length1 = obj1.size();
					for (int i = 0; i < length1; i++) {
						InfoKioskDetail Info = new InfoKioskDetail();
						json1 = (JSONObject) obj1.get(i);
						String block_eng = String.valueOf(json1.get("BLOCK_ENG"));
						String GP_ENG = String.valueOf(json1.get("GP_ENG"));
						String village_eng = String.valueOf(json1.get("VILLAGE_ENG"));
						String FATHER_NAME_HND = String.valueOf(json1.get("FATHER_NAME_HND"));
						String HOF_NAME_HND = String.valueOf(json1.get("HOF_NAME_HND"));
						String ADDRESS = String.valueOf(json1.get("ADDRESS"));
						String count = String.valueOf(json1.get("COUNT(1)"));
						
						String BLOCK_ID1 = json1.get("BLOCK_ID") != null &&json1.get("BLOCK_ID") != ""? String.valueOf(json1.get("BLOCK_ID")) : null;
						String GP_ID1 =  json1.get("GP_ID") != null && json1.get("GP_ID") != ""? String.valueOf(json1.get("GP_ID")) : null;
						String VILLAGE_ID =  json1.get("VILLAGE_ID") != null && json1.get("VILLAGE_ID") != null? String.valueOf(json1.get("VILLAGE_ID")) : null;
						
						Info.setGp_eng(GP_ENG);
						Info.setBlock_eng(block_eng);
						Info.setVillage_eng(village_eng);
						Info.setCount(count);
						Info.setBlock_id(BLOCK_ID1);
						Info.setGp_id(GP_ID1);
		                Info.setVill_id(VILLAGE_ID);
		            	Info.setFather_name_hnd(FATHER_NAME_HND);
						Info.setHof_name_hnd(HOF_NAME_HND);

						

						Info.setAddress(ADDRESS);
						list.add(Info);
						
					}
					req.setAttribute("list", list);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return req;
	
	}
	@Override
	public ModelAndView rationDetails(HttpServletRequest req , HttpServletResponse res , Bill bill) {
		// TODO Auto-generated method stub
		logger.debug("calling fetch.....");

		ModelAndView modelAndView = new ModelAndView();
		BufferedReader br = null;
		String responseJsonString = null;

		String cardType = req.getParameter("cardType");
		String cardNumber = req.getParameter("cardNumber");

		String requestUrl = "https://api.sewadwaar.rajasthan.gov.in/app/live/epdsService/fetchration/ePDS/getDetail/"
				+ cardType + "/" + cardNumber + "/json?client_id=" + CLIENTID_EMITRA_ID_PROD;

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
				logger.debug("getDetails, response code : " + responseCode + " due to API server error");
			} else {
				logger.debug("getDetails, response code : " + responseCode);
				
				List<RationBean> list = new ArrayList<>();
				br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

				StringBuffer responseJsonBuffer = new StringBuffer();
				for (String inputLine = br.readLine(); inputLine != null; inputLine = br.readLine()) {
					responseJsonBuffer.append(inputLine);
				}

				JSONParser parser = new JSONParser();
				RationBean info = new RationBean();
				
				responseJsonString = new String(responseJsonBuffer);
				logger.debug("value--->" + responseJsonString);
				JSONObject responseJson = (JSONObject) parser.parse(responseJsonString);
				String flag = String.valueOf(responseJson.get("flag"));
				if(flag.equals("true")) {
					JSONObject detail = (JSONObject) responseJson.get("detail");
					
					JSONObject fpsDetail = (JSONObject) detail.get("fpsDetail");

					String fpsCode = String.valueOf(fpsDetail.get("fpsCode"));
					String fpsOwnerName = String.valueOf(fpsDetail.get("fpsOwnerName"));
					String fpsAddress = String.valueOf(fpsDetail.get("fpsAddress"));
					String fpsBlock = String.valueOf(fpsDetail.get("fpsBlock"));
					String fpsDistrict = String.valueOf(fpsDetail.get("fpsDistrict"));

					JSONObject rcDetail = (JSONObject) detail.get("rcDetail");

					String categoryName = String.valueOf(rcDetail.get("categoryName"));
					String nfsa = String.valueOf(rcDetail.get("nfsa"));
					String houseNameNo = String.valueOf(rcDetail.get("houseNameNo"));
					String colonyStreet = String.valueOf(rcDetail.get("colonyStreet"));
					String tehsil = String.valueOf(rcDetail.get("tehsil"));
					String district = String.valueOf(rcDetail.get("district"));
					String pin = String.valueOf(rcDetail.get("pin"));
					String state = String.valueOf(rcDetail.get("state"));
					String rationCardNumber = String.valueOf(rcDetail.get("rationCardNumber"));
					String rationName = String.valueOf(rcDetail.get("rationCardName"));

					List<FamilyDetail> familyDetailList = new ArrayList<FamilyDetail>();
					JSONArray familyDetail = (JSONArray) rcDetail.get("familyDetail");
					for (int counter = 0; counter < familyDetail.size(); counter++) {
						JSONObject family = new JSONObject();
						family = (JSONObject) familyDetail.get(counter);
						FamilyDetail familyDetailModel = new FamilyDetail();

						familyDetailModel.setAadharID(String.valueOf(family.get("aadharID")));
						familyDetailModel.setAge(String.valueOf(family.get("age")));
						familyDetailModel.setBhamashahID(String.valueOf(family.get("bhamashahID")));
						familyDetailModel.setGender(String.valueOf(family.get("gender")));
						familyDetailModel.setMemberNameEN(String.valueOf(family.get("memberNameEN")));
						familyDetailModel.setMemberNameHI(String.valueOf(family.get("memberNameHI")));
						familyDetailModel.setMemberRelationEN(String.valueOf(family.get("memberRelationEN")));
						familyDetailModel.setMemberRelationHI(String.valueOf(family.get("memberRelationHI")));
						familyDetailModel.setRunningNumber(String.valueOf(family.get("runningNumber")));

						familyDetailList.add(familyDetailModel);
					}

					info.setFpsCode(fpsCode);
					info.setFpsOwnerName(fpsOwnerName);
					info.setFpsAddress(fpsAddress);
					info.setFpsBlock(fpsBlock);
					info.setFpsDistrict(fpsDistrict);

					info.setCategoryName(categoryName);
					info.setNfsa(nfsa);
					info.setHouseNameNo(houseNameNo);

					info.setColonyStreet(colonyStreet);
					info.setTehsil(tehsil);
					info.setDistrict(district);
					info.setPin(pin);
					info.setState(state);

					info.setRationCardName(rationName);
					info.setRationCardNumber(rationCardNumber);
					info.setFamilyDetail(familyDetailList);
					list.add(info);

					req.setAttribute("list", list);
				} else {
					if(bill.getLangCode() == 0) {
						req.setAttribute("MSG", "No Details Found For The Given Details.");
					} else {
						req.setAttribute("MSG", "दिए गए विवरण के लिए कोई विवरण नहीं मिला।");
					}
				}
				req.setAttribute("flag", flag);
				req.setAttribute("langCode", bill.getLangCode());
				req.setCharacterEncoding("utf-8");
				res.setCharacterEncoding("utf-8");
			}
		} catch (Exception e) {
			logger.error("BillController, Exception in fetchRation :" + e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("IO Exception");
			}
		}

		modelAndView.addObject("s", responseJsonString);
		modelAndView.setViewName("FetchRationDetails");
		return modelAndView;
	}
	
}