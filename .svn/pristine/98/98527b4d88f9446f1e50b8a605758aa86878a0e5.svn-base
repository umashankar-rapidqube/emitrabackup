package controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import model.Bill;
import model.Login;
import model.RevenueCaseModel;
import service.RevenueCaseService;

@EnableWebMvc
@Controller
public class RevenueCaseController {

	@Autowired
	RevenueCaseService revenueCaseService;
	
	static final Logger logger = Logger.getLogger(RevenueCaseController.class);
	
	@RequestMapping(value = "/revenueCaseService" ,method = RequestMethod.POST)
	public String revenueCase(Model model , Bill bill , HttpServletRequest req) {
		int langCode = bill.getLangCode();
		bill.setLangCode(langCode);
		bill.setSsoID(Login.SSOID);
		
		JSONObject masterData = revenueCaseService.getRevenueMasterData(req);
		
		req.setAttribute("district", masterData.get("district"));
		req.setAttribute("courtType", masterData.get("courtType"));
		logger.debug("revenueCase, Master Data for court case(onload) : " + masterData);
		model.addAttribute("serviceProvider", bill);
		return bill.getServiceProviderPage();
	}
	
	@RequestMapping(value = "/revenueCaseCourtName" ,method = RequestMethod.POST)
	public ResponseEntity<JSONArray> getCourtName(HttpServletRequest req , @RequestParam ("districtKey") String districtKey , @RequestParam ("ctypeKey") String ctypeKey) {
		
		JSONArray masterData = revenueCaseService.getCourtName(req , districtKey , ctypeKey);
		logger.debug("revenueCase, Master Data for court name : " + masterData);
		return new ResponseEntity<JSONArray>(masterData, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/revenueCaseStatus" ,method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getCaseStatus(HttpServletRequest req , @RequestParam ("cnameKey") String cnameKey , @RequestParam ("caseNo") String caseNo) {
		
		JSONObject masterData = revenueCaseService.getCaseStatus(req , cnameKey , caseNo);
		logger.debug("revenueCase, Master Data for court case status : " + masterData);
		return new ResponseEntity<JSONObject>(masterData, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/revenueCaseSaveDetails" ,method = RequestMethod.POST)
	public ModelAndView saveDetails(HttpServletRequest req , RevenueCaseModel revenueCaseModel) {
		
		String transId = revenueCaseService.saveRevenueCaseDetails(revenueCaseModel);
		revenueCaseModel.setTransId(transId);
		req.setAttribute("transId", revenueCaseModel.getTransId());
		req.setAttribute("langCode", revenueCaseModel.getLangCode());
		req.setAttribute("pdfUrl", revenueCaseModel.getPdfUrl()+"#toolbar=0&navpanes=0&scrollbar=0");
		
		logger.debug("revenueCase, transaction id generated for insertion of data : " + revenueCaseModel.getTransId());
		logger.debug("revenueCase, pdfUrl to show : " + revenueCaseModel.getPdfUrl()+"#toolbar=0&navpanes=0&scrollbar=0");
		return new ModelAndView("RevenuePrint");
	}
}