package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import commonServices.ReceiptPrint;
import service.BhamashahCardService;

@EnableWebMvc
@Controller
public class PrintReceiptController {

	static final Logger logger = Logger.getLogger(PrintReceiptController.class);

	@Autowired
	BhamashahCardService bhamashahCardService;

	@RequestMapping(value = "/receiptPrintCertificate" ,method = RequestMethod.POST)
	public boolean receiptPrintOfCertificate(HttpServletRequest request , @RequestParam ("transId") String transId , @RequestParam ("pdfUrl") String pdfUrl) throws IOException {
		
		logger.debug("PDF path for printing Certificate" + pdfUrl);
		logger.info("Enter In Print Receipt..");
		
			JSONObject receiptData = new JSONObject();
		receiptData = bhamashahCardService.getPrintReceiptData("Proc_PrintCertificate" , "getPrintReceiptData" , transId);
		if(receiptData != null && receiptData.length() > 0) {
			logger.debug("Receipt Data for printing Receipt of Certificate services :: " + receiptData);
			ReceiptPrint.printReciept(request , receiptData , "certificate");
			return true;
		} else {
			logger.debug("Error while getting receipt data of Certificate services :: " + receiptData);
			return false;
		}
	}
	
	@RequestMapping(value = "/receiptPrint" ,method = RequestMethod.POST)
	public boolean receiptPrintOfUtility(HttpServletRequest request , @RequestParam ("transId") String transId) throws IOException {
		
		JSONObject receiptData = new JSONObject();
		receiptData = bhamashahCardService.getPrintReceiptData("proc_kiosk" , "getprintreceiptofutility" , transId);
		if(receiptData != null && receiptData.length() > 0) {
			logger.debug("Receipt Data for printing Receipt of other than certificate services :: " + receiptData);
			ReceiptPrint.printReciept(request , receiptData , "utility");
			return true;
		} else {
			logger.debug("Error while getting receipt data of other than certificate services :: " + receiptData);
			return false;
		}
	}
}