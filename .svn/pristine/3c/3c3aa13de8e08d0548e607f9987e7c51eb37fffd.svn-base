package controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import commonServices.ReceiptPrint;
import model.Bill;
import model.Login;
import model.PUCModel;
import service.PUCService;
import webServicesRepository.utility.EncrptDesryptDataService;

@EnableWebMvc
@Controller
public class PUCController {

	static final Logger logger = Logger.getLogger(PUCController.class);
	
	@Autowired
	PUCService pucService;
	
	@RequestMapping(value = "/puc" ,method = RequestMethod.POST)
	public String puc(Model model , Bill bill , HttpServletRequest req) {
		int langCode = Integer.parseInt(req.getParameter("langCode"));
		bill.setLangCode(langCode);
		bill.setSsoID(Login.SSOID);
		model.addAttribute("serviceProvider", bill);
		return "PUCForm";
	}
	
	@RequestMapping(value = "/savePUCDetails" ,method = RequestMethod.POST)
	public ModelAndView bonafideHi(PUCModel pucModel , HttpServletRequest request) {
		
		JSONObject pucDetails = pucService.insertData(pucModel);
		
		try {
			logger.info("Actual Amount  :"+pucDetails.getString("Total_Amount")+" TransactionId : "+pucDetails.getString("Request_ID")+" Billamount : "+pucDetails.getString("Bill_Amount")+" ServiceProvider ID : "+pucDetails.getString("Service_ID"));
		
		    String checkSSO = EncrptDesryptDataService.checkSSOID();
		    if(checkSSO.equalsIgnoreCase("yes")) {
		    	request.setAttribute("amount", pucDetails.getString("Bill_Amount"));
				request.setAttribute("Id", pucDetails.getString("Request_ID"));
				request.setAttribute("acutalAmt", pucDetails.getString("Total_Amount"));
				request.setAttribute("serviceProviderID", pucDetails.getString("Service_ID"));
				request.setAttribute("name" , pucDetails.getString("Consumer_Name"));
				request.setAttribute("email" , pucDetails.getString("Email_ID"));
				request.setAttribute("mobile" , pucDetails.getString("Mobile_No"));
				request.setAttribute("consumerKeyValue" , pucDetails.getString("Consumer_Key"));
				String date = pucDetails.getString("Created_Date");
				date = ReceiptPrint.convertDate(date , "yyyy-MM-dd'T'hh:mm:ss.SSSZ" , "yyyyMMddHHmmssSSS");
				request.setAttribute("date" , date);
				
				/*logger.info("clickToPay :: Note Value, total10note :"+NoteType.total10Note+" total20note :"+NoteType.total20Note+" total50note:"+NoteType.total50Note
						+" total100note :"+NoteType.total100Note+" total200note :"+NoteType.total200Note+" total500note :"	+
						NoteType.total500Note+" total2000note :"+NoteType.total2000Note );
				NoteType.total10Note=0;
				NoteType.total20Note=0;
				NoteType.total50Note=0;
				NoteType.total100Note=0;
				NoteType.total200Note=0;
				NoteType.total500Note=0;
				NoteType.total2000Note=0;
				NoteType.cashTimer=0;
				NoteType.depositAmount = 0;
				
				logger.info("clickToPay ::After regreshing Note Value, total10note :"+NoteType.total10Note+" total20note :"+NoteType.total20Note+" total50note:"+NoteType.total50Note
						+" total100note :"+NoteType.total100Note+" total200note :"+NoteType.total200Note+" total500note :"	+
						NoteType.total500Note+" total2000note :"+NoteType.total2000Note );*/
		    }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			logger.error("PUCController, Exception in bonafideHi : "+e.getMessage());
			/*e.printStackTrace();*/
		}
		
		if(pucModel.getLangCode()=="1")
			return new ModelAndView("PaymentmodeHi");
		else 
			return new ModelAndView("Paymentmode");
	}
}