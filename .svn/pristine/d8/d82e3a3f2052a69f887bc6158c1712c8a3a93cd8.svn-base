package webServicesRepository.utility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import model.Login;

public class EncrptDesryptDataService {

	static final Logger logger = Logger.getLogger(EncrptDesryptDataService.class);

	BufferedReader in;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	private final String USER_AGENT = "Mozilla/5.0";

	// encrypt data required URL below
	private final String encryptURL = "https://emitraapp.rajasthan.gov.in/webServicesRepository/emitraAESEncryption";

	private final String billInfoURL = "https://emitraapp.rajasthan.gov.in/webServicesRepository/getFetchDetailsEncryptedBySso";

	// encrypt data required URL below
	private final String decryptURL = "https://emitraapp.rajasthan.gov.in/webServicesRepository/emitraAESDecryption";

	private final String checkSumURL = "https://emitraapp.rajasthan.gov.in/webServicesRepository/emitraMD5Checksum";

	private final String backToBackURL = "https://emitraapp.rajasthan.gov.in/webServicesRepository/backtobackTransactionWithEncryptionA";

	private final String discomdetailsURL = "https://emitraapp.rajasthan.gov.in/webServicesRepository/getFetchDetailsEncryptedBySso";

	private final String pheddetailsURL = "https://emitraapp.rajasthan.gov.in/webServicesRepository/getFetchDetailsEncryptedBySso";

	private final String cancelTransacationURL = " https://emitraapp.rajasthan.gov.in/webServicesRepository/backendTransCancelByDepartmentWithEncryption";

	private final String verifyURL = "https://emitraapp.rajasthan.gov.in/webServicesRepository/getTokenVerifyNewProcessByRequestIdWithEncryption";

	// All URL for refund are as below
	private final String SSOIDURL = "https://emitraapp.rajasthan.gov.in/webServicesRepository/getKioskDetailsJSON";

	public static final String CLIENTID_UID = "MEmVkcpNLahCE-9skCRMK36S_ufQGPaCiNFAZ33o_ICd01JIE6IBLpU"; // "9063b7b2-3a8d-4efb-8422-0572fff44ab2";
																											// // PROD

	public static final String URL_OTP_GEN = "https://api.sewadwaar.rajasthan.gov.in/app/live/api/aua/otp/request?client_id="
			+ CLIENTID_UID;

	public static final String URL_OTP_AUTH = "https://api.sewadwaar.rajasthan.gov.in/app/live/api/aua/otp/auth/encr?client_id="
			+ CLIENTID_UID; // PROD

	// HTTP GET request
	@SuppressWarnings("unused")
	private String sendGetEncryptData(String param) throws Exception {
		String methodName = "sendGetEncryptData";
		return getGETResponse(methodName, encryptURL);

	}

	public String sendPostForEncryptData(String param) throws Exception {
		String methodName = "sendPostForEncryptData";
		String requestMethod = "POST";
		return getPOSTResponse(requestMethod, methodName, param, "toBeEncrypt", encryptURL);
	}

	public String getCheckSUM(String checkSum) {
		StringBuffer response = new StringBuffer();

		try {
			URL obj = new URL(checkSumURL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String urlParameters = "toBeCheckSumString=" + checkSum;

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			logger.info("getCheckSUM, Response Code : " + responseCode);
			if (responseCode == 200) {
				/*BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
*/
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				for (String inputLine = in.readLine(); inputLine != null; inputLine = in.readLine()) {
					response.append(inputLine);
				}					
			}
		} catch (Exception e) {
			logger.error("EncrptDesryptDataService, Exception in getCheckSUM : " + e.getMessage());
			/*e.printStackTrace();*/
		}
		finally
		{
			try {
			in.close();
			}
			catch (IOException e) {
				logger.error("IO Exception");
		}
		}
	
		return response.toString();
	}

	public String getBillInformationData(String param) {

		String methodName = "getBillInformationData";
		String requestMethod = "POST";
		return getPOSTResponse(requestMethod, methodName, param, "encData", billInfoURL);
	}

	public String sendPostCheckSum(String param) {

		String methodName = "sendPostCheckSum";
		String requestMethod = "POST";
		return getPOSTResponse(requestMethod, methodName, param, "toBeCheckSumString", checkSumURL);
	}

	public String sendPostForBacktoBack(String param) {

		String methodName = "sendPostForBacktoBack";
		String requestMethod = "POST";
		return getPOSTResponse(requestMethod, methodName, param, "encData", backToBackURL);
	}

	public String sendPostForDecryptData(String param) {
		String methodName = "sendPostForDecryptData";
		String requestMethod = "POST";
		return getPOSTResponse(requestMethod, methodName, param, "toBeDecrypt", decryptURL);
	}

	public String getPhedInformationData(String param) throws Exception {

		String methodName = "getPhedInformationData";
		String requestMethod = "POST";
		return getPOSTResponse(requestMethod, methodName, param, "encData", pheddetailsURL);
	}

	public String getDiscomInformationData(String param) throws Exception {

		String methodName = "getDiscomInformationData";
		String requestMethod = "POST";
		return getPOSTResponse(requestMethod, methodName, param, "encData", discomdetailsURL);
	}

	public String sendPostForJamabandiData(String param) throws Exception {

		String methodName = "sendPostForJamabandiData";
		String requestMethod = "GET";
		return getPOSTResponse(requestMethod, methodName, param, "", param);
	}

	public String sendPostForCancelTranscation(String param) throws Exception {

		String methodName = "sendPostForCancelTranscation";
		String requestMethod = "POST";
		return getPOSTResponse(requestMethod, methodName, param, "encData", cancelTransacationURL);
	}

	public String sendPostForVerifyTranscation(String param) throws Exception {

		String methodName = "sendPostForVerifyTranscation";
		String requestMethod = "POST";
		return getPOSTResponse(requestMethod, methodName, param, "encData", verifyURL);
	}

	static public String checkSSOID() {
		return (null == Login.SSOID || "".equals(Login.SSOID)) ? "no" : "yes";
	}

	public int getCashMachineAmount(double amount) {
		int result = (int) amount;
		int y = result % 10;
		double z = amount - result;
		if (y == 0) {
			if (z > 0)
				result = result + 1;
			return result;
		}

		switch (y) {

		case 1:
			result = result + 9;
			break;
		case 2:
			result = result + 8;
			break;
		case 3:
			result = result + 7;
			break;
		case 4:
			result = result + 6;
			break;
		case 5:
			result = result + 5;
			break;
		case 6:
			result = result + 4;
			break;
		case 7:
			result = result + 3;
			break;
		case 8:
			result = result + 2;
			break;
		case 9:
			result = result + 1;
			break;

		}
		return result;
	}

	public String getPOSTResponse(String requestMethod, String methodName, String param, String toBeEnOrDe,
			String URL) {

		StringBuffer response = new StringBuffer();
		String res = null;
		try {
			URL obj = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod(requestMethod);
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = null;

			if ("getMachineAuth".equalsIgnoreCase(methodName) || "onlineverify".equalsIgnoreCase(methodName))
				urlParameters = param;
			else
				urlParameters = toBeEnOrDe + "=" + param;

			con.setDoOutput(true);

			if (!"sendPostForJamabandiData".equalsIgnoreCase(methodName)) {
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
			}

			int responseCode = con.getResponseCode();
			logger.info(methodName + ", Post parameters : " + urlParameters);
			logger.info(methodName + ", Response Code : " + responseCode);
			if (responseCode == 200) {
				/*BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();*/				
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				for (String inputLine = in.readLine(); inputLine != null; inputLine = in.readLine()) {
					response.append(inputLine);
				}
				
			}
			res = response.toString();

		} catch (Exception e) {
			logger.error("EncrptDesryptDataService : Exception in " + methodName + " : " + e.getMessage());
			/*e.printStackTrace();*/
		}
		finally
		{
			try {
			in.close();
			}
			catch (IOException e) {
				logger.error("IO Exception");
		}
		}

		return res;
	}

	public String getGETResponse(String methodName, String URL) {

		StringBuffer response = null;
		try {
			URL obj = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			logger.info(methodName + " Response Code : " + responseCode);
			if (responseCode == 200) {

				/*BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;

				response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}

				in.close();
*/
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				response = new StringBuffer();
				for (String inputLine = in.readLine(); inputLine != null; inputLine = in.readLine()) {
					response.append(inputLine);
				}
			}
		} catch (Exception e) {
			logger.error("EncrptDesryptDataService : Exception in " + methodName + " " + e.getMessage());
			/*e.printStackTrace();*/
		}
		finally
		{
			try {
			in.close();
			}
			catch (IOException e) {
				logger.error("IO Exception");
		}
		}
		return response.toString();
	}

	// use to get aadhar number by using SSOID and Marchant Code
	public String sendPostSSOIDAndMarchant(String param) {

		String methodName = "sendPostSSOIDAndMarchant";
		String requestMethod = "POST";
		return getPOSTResponse(requestMethod, methodName, param, "encData", SSOIDURL);
	}
}