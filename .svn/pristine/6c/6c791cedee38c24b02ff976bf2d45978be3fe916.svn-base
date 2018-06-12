package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import model.InfoKioskDetail;
import model.Login;
import model.MachineAuth;
import service.BillService;

@EnableWebMvc
@Controller
public class InfoPanchayatController {
	
	static final Logger logger = Logger.getLogger(InfoPanchayatController.class);
	
	@Autowired
	BillService billService;
	
	@RequestMapping(value = "/KioskDetaiInfo")
	public ModelAndView KioskDetaiInfo(HttpServletRequest req) {
		String langCode = req.getParameter("langCode");
		MachineAuth machi = billService.getMachineAuthenticationDetails(Login.SSOID);
		String machineId = machi.getMachineId();
		if (machineId == null) {
			req.setAttribute("langCode", langCode);
			return new ModelAndView("InfoPanchayatError");
		}
		HttpURLConnection conn = null;

		String urlParameters = "SSOIDGOVT=" + "" + "&MACHINESERIALNO=" + machineId;

		String requestUrl = "https://emitraapp.rajasthan.gov.in/webServicesRepository/mgetMachineDetailEmitraPlus";
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
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				logger.debug("KioskDetaiInfo, response code : " + conn.getResponseCode() + " due to API server error");
			} else {

				try (Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
 					for (int c; (c = in.read()) >= 0;) {
						data.append((char) c);
					}
 					req = kioskDetaiInfoDetail(data.toString() , req);
				}
			}
		} catch (Exception e) {
			logger.error("InfoPanchayatController, Exception in KioskDetaiInfo : "+e.getMessage());
			return new ModelAndView("InfoPanchayatError");
		
			/*e.printStackTrace();*/
		}
		if (langCode.equals("0")) {
			return new ModelAndView("KioskDetaiInfo");
		}else {
			return new ModelAndView("KioskDetaiInfoHi");
		}
	}
	
	public HttpServletRequest kioskDetaiInfoDetail(String data , HttpServletRequest req) {
	
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
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				logger.debug("kioskDetaiInfoDetail, response code : " + conn.getResponseCode() + " due to API server error");
			} else {

				try (Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
					for (int c; (c = in.read()) >= 0;) {
						data1.append((char) c);
					}

				}
				logger.debug("kioskDetaiInfoDetail, response code : " + conn.getResponseCode() );
			}
			obj = (org.json.simple.JSONArray) parser.parse(data1.toString());
			JSONObject json1 = new JSONObject();

			int length1 = obj.size();
			for (int i = 0; i < length1; i++) {
				InfoKioskDetail Info = new InfoKioskDetail();
				json1 = (JSONObject) obj.get(i);
				String vendorName = String.valueOf(json1.get("vendorName"));
				String LSPNAME = String.valueOf(json1.get("LSPNAME"));
				String email = String.valueOf(json1.get("email"));
				String district = String.valueOf(json1.get("district"));
				String phoneNumber1 = String.valueOf(json1.get("phoneNumber1"));
				String phoneNumber2 = String.valueOf(json1.get("phoneNumber2"));
				String Address = String.valueOf(json1.get("Address"));

				Info.setVendorName(vendorName);
				Info.setLspName(LSPNAME);
				Info.setEmail(email);
				Info.setDistrict(district);
				Info.setPhoneNumber1(phoneNumber1);
				Info.setPhoneNumber2(phoneNumber2);
				Info.setAddress(Address);
				list.add(Info);
			}
			req.setAttribute("list", list);
		} catch (Exception e) {
			logger.error("InfoPanchayatController, Exception in kioskDetaiInfoDetail : "+e.getMessage());
			return (HttpServletRequest) new ModelAndView("InfoPanchayatError");
			/*e.printStackTrace();*/
		}
		return req;
	}
	
	//@SuppressWarnings("static-access")
	@RequestMapping(value = "/InformationPanchayatService")
	public  ModelAndView InformationPanchayatService(MachineAuth machineAuth, HttpServletRequest req) {
		return new ModelAndView("InformationPanchayatService");
	}
	
	@RequestMapping(value = "/InformationPanchayatServiceHi")
	public ModelAndView InformationPanchayatServicehindi(Model model, HttpServletRequest req) {
		return new ModelAndView("InformationPanchayatServiceHi");
	}
}