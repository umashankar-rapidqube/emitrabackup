package webServicesRepository.utility;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.ini4j.Wini;

public class VedioConference {
	static final Logger logger = Logger.getLogger(VedioConference.class);

	static private final String filename = "C:\\RMS\\KioskHealth.ini";

	public static void main(String[] args) throws Exception {

		String vc_filePath = null;
		try {

			String fileName = "vedioconference.bat";
			ClassLoader classLoader = new VedioConference().getClass().getClassLoader();
			File file = new File(classLoader.getResource(fileName).getFile());
			vc_filePath = file.getCanonicalPath();
		} catch (IOException e1) {
			logger.error("VedioConference, Exception in main : "+e1.getMessage());
			/*e1.printStackTrace();*/
		}

		logger.info("vc file path :: " + vc_filePath);
		String[] cmd = { "cmd.exe", "/c", "Start", vc_filePath };

		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("VedioConference, Exception in main for executing command : "+e.getMessage());
			/*e.printStackTrace();*/
		}
	}

	public void getVedioConference() {
		String vc_filePath = null;
		try {
			String fileName = "vedioconference.bat";
			ClassLoader classLoader = new VedioConference().getClass().getClassLoader();
			File file = new File(classLoader.getResource(fileName).getFile());
			vc_filePath = file.getCanonicalPath();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("VedioConference, Exception in getVedioConference : "+e1.getMessage());
			/*e1.printStackTrace();*/
		}

		logger.info("VedioConference File Path :: " + vc_filePath);
		String[] cmd = { "cmd.exe", "/c", "Start", vc_filePath };
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			logger.error("VedioConference, Exception in VedioConference : " + e.getMessage());
			/*e.printStackTrace();*/
		}
	}

	public void updateKioskHeathFile(String statusvalue, String nodeValue) {
		try {
			logger.info("nodeValue : " + nodeValue + " statusvalue :" + statusvalue);

			File file = new File(filename);
			file.getParentFile().mkdirs(); // Will create parent directories for the file, if not exists
			file.createNewFile(); // Will create file in directory, if not exists

			Wini ini = new Wini(file);
			ini.put(nodeValue, "Status", statusvalue);
			ini.store();
		} catch (IOException e) {
			logger.error("VedioConference, Exception in updateKioskHeathFile, statusvalue :" + statusvalue + " nodeValue : " + nodeValue
					+ " exception msg :: " + e.getMessage());
			/*e.printStackTrace();*/
		}
	}
}