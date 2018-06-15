package DeviceConfiguration;

import java.io.File;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import jpos.util.JposPropertiesConst;
import service.BillService;

@WebServlet("/printReceipt")
public class Config extends HttpServlet {
 
	static final Logger logger = Logger.getLogger("PrintReceipt Config");
	
	private static final long serialVersionUID = 1L;

	@Autowired
	BillService billService;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		String name=request.getParameter("name");
		String trnsid=request.getParameter("transactionId");
		
		String bankRefNumber= request.getParameter("bankRefNumber");
		String mobile=request.getParameter("billMobileNo");
		String actualAmount=request.getParameter("billActualAmount");
		String payableAmount=request.getParameter("billAmount");
		String trnsDate=request.getParameter("transcationDate");
		String sname=request.getParameter("serviceProviderName");
		String paymentMode=request.getParameter("paymentMode");
		logger.debug("BankRefNumber::"+bankRefNumber+"name::"+name+" trnsid:::"+trnsid+" mobile::"+mobile+" acutual::"+actualAmount+" payable::"+payableAmount+" Date::"+trnsDate+" serviceProviderName::"+sname);
		
		String printData="\r\n TranscationId:"+trnsid+"\r\n Name:"+name+"\r\n Service Name:"+sname+"\r\n Consumer Key:"+mobile+" \r\n Bill Amount:"+actualAmount+" \r\n Payment Mode:"+paymentMode+" \r\n Paid Amount:"+payableAmount+
				"\r\n Reference Number:"+bankRefNumber+"\r\n Date:"+trnsDate+"\r\n\r\n\r\n\r\n\r\n";
		
		logger.debug("Transcation print details ::"+printData);
		String printLogoPath= null, absoluteDiskPath=null;
		try {
			absoluteDiskPath = getServletContext().getRealPath("/resources/img");
			//failIfDirectoryTraversal(absoluteDiskPath);
			File file = new File(absoluteDiskPath, "javapos.bmp");
			printLogoPath = file.getCanonicalPath();
			logger.debug("Receipt_Printer Logo Path :: "+printLogoPath+" absoluteDiskPath : "+absoluteDiskPath);
			
		} catch (IOException e) {
			logger.error("Config, Transcation print exception ::"+e.getMessage());
			/*e.printStackTrace();*/
		}
		
		printReceipt(printData,absoluteDiskPath);
	}
	
	public static void printReceipt(String str, String absoluteDiskPath) {
		MyEvents evnt = new MyEvents();
		POSPrinter ptr = null;
		ptr = new POSPrinter();
		try {
			logger.debug("JposPropertiesConst.JPOS_POPULATOR_FILE_PROP_NAME :: "+JposPropertiesConst.JPOS_POPULATOR_FILE_PROP_NAME);
			
			System.setProperty(JposPropertiesConst.JPOS_POPULATOR_FILE_PROP_NAME, "C:\\BIXOLONJavaPOS\\jpos.xml");
		//	logger.info("Receipt_Printer Logo Path 111111");
			ptr.open("SRP-330");
//			ptr.open("SRP-350III");
		//	logger.info("Receipt_Printer Logo Path 222222");
			
			ptr.claim(0);																						
		//	logger.info("Receipt_Printer Logo Path 33333");
			
			ptr.setDeviceEnabled(true);
		//	logger.info("Receipt_Printer Logo Path 444444");
			
			ptr.addStatusUpdateListener(evnt);
		//	logger.info("Receipt_Printer Logo Path 55555");
			
			ptr.printBitmap(POSPrinterConst.PTR_S_RECEIPT, absoluteDiskPath+"\\javapos.bmp",
					POSPrinterConst.PTR_BM_ASIS, POSPrinterConst.PTR_BM_CENTER);
			
			ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, str);
		//	logger.info("Receipt_Printer Logo Path 77777");
			
			 ptr.cutPaper(90);
		//	 logger.info("Receipt_Printer Logo Path 88888");
				
			 ptr.setDeviceEnabled(false);
		//	 logger.info("Receipt_Printer Logo Path 99999");
				
			 ptr.release();
			// ptr.close();
			 
		} catch (JposException e) {
			logger.error("Config, Receipt Printer Exception ::"+e.getMessage());
			/*e.printStackTrace();*/
		}
		finally {
			try {
				ptr.close();
			} catch (JposException e1) {
				logger.error("Config, Receipt Printer Exception");
			}
		}
	}
	//method to avoid path traversal
	public void failIfDirectoryTraversal(String relativePath)
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