package commonServices;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;

import DeviceConfiguration.Config;

public class ReceiptPrint {

	static final Logger logger = Logger.getLogger(ReceiptPrint.class);

	public static void printReciept(HttpServletRequest request, org.json.JSONObject recieptData , String serviceType) {

		String printData = null;
		String printLogoPath = null;
		String absoluteDiskPath = null;
		try {
			String trnsid = String.valueOf(recieptData.get("TRANSACTIONID"));
			String consumerName = null;
			String bankRefNumber = null;
			if(serviceType.equals("utility")) {
				consumerName = String.valueOf(recieptData.get("CONSUMERNAME"));
				bankRefNumber = String.valueOf(recieptData.get("REFERENCE_NO"));
			}
			String sname = String.valueOf(recieptData.get("Service_Name"));
			String consumerKey = String.valueOf(recieptData.get("consumerKey"));
			String actualAmount = String.valueOf(recieptData.get("Amount"));
			String paymentMode = String.valueOf(recieptData.get("PaymentMode"));
			String payableAmount = String.valueOf(recieptData.get("TRANSAMT"));
			if(payableAmount.contains(".")) {
				payableAmount = payableAmount.substring(0, payableAmount.length() - 2);
			}
			String trnsDate = convertDate(String.valueOf(recieptData.get("EMITRATIMESTAMP")) , "yyyyMMddHHmmssSSS" , "yyyy-MM-dd HH:mm:ss.SSS");

			logger.info("BankRefNumber::" + bankRefNumber + " trnsid:::" + trnsid + " mobile::"
					+ consumerKey + " acutual::" + actualAmount + " payable::" + payableAmount + " Date::" + trnsDate
					+ " serviceProviderName::" + sname);

			printData = "\r\n TranscationId:" + trnsid;
			
			if(serviceType.equals("utility")) {
				printData += "\r\n Name:" + consumerName;
			}
			
			printData += "\r\n Service Name:" + sname
					+ "\r\n Consumer Key:" + consumerKey + " \r\n Bill Amount:" + actualAmount + " \r\n Payment Mode:"
					+ paymentMode + " \r\n Paid Amount:" + payableAmount;
			
			if(serviceType.equals("utility")) {
				printData += "\r\n Reference Number:" + bankRefNumber;
			}
			
			printData +=  "\r\n Date:" + trnsDate + "\r\n\r\n\r\n\r\n\r\n";
			
			logger.info("Transcation print details ::" + printData);
			
			absoluteDiskPath = request.getSession().getServletContext().getRealPath("/resources/img");
			//failIfDirectoryTraversal(absoluteDiskPath);
			File file = new File(absoluteDiskPath, "javapos.bmp");
			printLogoPath = file.getCanonicalPath();
			logger.info("Receipt_Printer Logo Path :: " + printLogoPath + " absoluteDiskPath : " + absoluteDiskPath);

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			logger.error("Transcation print exception ::" + e1.getMessage());
			/*e1.printStackTrace();*/
		} catch (IOException e) {
			logger.error("Transcation print exception ::" + e.getMessage());
			/*e.printStackTrace();*/
		}

		Config.printReceipt(printData, absoluteDiskPath);
	}

	public static String convertDate(String date , String SimpleDateFormat , String convertedDateFormat) {
		// TODO Auto-generated method stub
		String convertedDate = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat(SimpleDateFormat).parse(date));
			SimpleDateFormat sdf = new SimpleDateFormat(convertedDateFormat);
			convertedDate = sdf.format(cal.getTime());
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			logger.error("ReceiptPrint, Exception in convertDate : " + e.getMessage());
			/*e.printStackTrace();*/
		}		
		return convertedDate;
	}
	
	public static void main(String date[]) {
		// TODO Auto-generated method stub
		
		//System.out.println(convertDate("2018-05-31T12:37:36.540+0000" , "yyyy-MM-dd'T'hh:mm:ss.SSSZ" , "yyyyMMddHHmmssSSS"));
		logger.debug(convertDate("2018-05-31T12:37:36.540+0000" , "yyyy-MM-dd'T'hh:mm:ss.SSSZ" , "yyyyMMddHHmmssSSS"));
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
		Date result;
		    try {
				result = df.parse("2018-05-31T12:37:36.540+0000");
			logger.debug("date:"+result);
		    //System.out.println("date:"+result); //prints date in current locale
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		    logger.debug(sdf.format(result));
		    //System.out.println(sdf.format(result)); //prints date in the format sdf
		    } catch (ParseException e) {
				// TODO Auto-generated catch block
		    	logger.error("ReceiptPrint, Exception in main : " +e.getMessage());
				/*e.printStackTrace();*/
			}
	}
	
	//method to avoid path traversal
	
		public static void failIfDirectoryTraversal(String relativePath)
		{
		    File file = new File(relativePath);

		    if (file.isAbsolute())
		    {
		        throw new RuntimeException("Directory traversal attempt - absolute path not allowed");
		    }

		    String pathUsingCanonical;
		    String pathUsingAbsolute;
		    try
		    {
		        pathUsingCanonical = file.getCanonicalPath();
		        pathUsingAbsolute = file.getAbsolutePath();
		    }
		    catch (IOException e)
		    {
		        throw new RuntimeException("Directory traversal attempt?", e);
		    }


		    // Require the absolute path and canonicalized path match.
		    // This is done to avoid directory traversal 
		    // attacks, e.g. "1/../2/" 
		    if (! pathUsingCanonical.equals(pathUsingAbsolute))
		    {
		        throw new RuntimeException("Directory traversal attempt?");
		    }
		}
}