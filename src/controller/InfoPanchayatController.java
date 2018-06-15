package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import model.Bill;
import model.Login;
import model.MachineAuth;
import service.BillService;
import service.InfoPanchayatService;

@EnableWebMvc
@Controller
public class InfoPanchayatController {
	
	static final Logger logger = Logger.getLogger(InfoPanchayatController.class);
	
	@Autowired
	BillService billService;
	
	@Autowired
	InfoPanchayatService infoPanchayatService;
	
	@RequestMapping(value = "/kioskDetaiInfo")
	public ModelAndView kioskInfoDetail(HttpServletRequest req , Bill bill) {
		
		String machineId = getMachineId(); 
		if (machineId == null) {
			req.setAttribute("langCode", bill.getLangCode());
			return new ModelAndView("InfoPanchayatError");
		}
		
		req = infoPanchayatService.kioskInfoDetail(req , machineId);
		req.setAttribute("langCode", bill.getLangCode());
		
		return new ModelAndView(bill.getServiceProviderPage());
	}
	
	@RequestMapping(value = "/fetchRation", method = RequestMethod.GET)
	public ModelAndView rationInfoDetail(HttpServletRequest req, HttpServletResponse res , Bill bill){

		ModelAndView modelAndView = infoPanchayatService.rationDetails(req , res , bill);
		return modelAndView;
	}
	
	@RequestMapping(value = "/bhamasaInfoDetail" , method = RequestMethod.POST)
	public ModelAndView bhamasaInfoDetail(HttpServletRequest req , Bill bill) {
		String machineId = getMachineId(); 
		if (machineId == null) {
			req.setAttribute("langCode", bill.getLangCode());
			return new ModelAndView("InfoPanchayatError");
		}
		req = infoPanchayatService.bhamasaInfoDetail(req , machineId);
		req.setAttribute("langCode", bill.getLangCode());
		req.setAttribute("serviceProviderPage", bill.getServiceProviderPage());
		return new ModelAndView(bill.getServiceProviderPage());
	}
	
	@RequestMapping(value = "/BhamashahSwasthyaBima" , method = RequestMethod.POST)
	public ModelAndView SwasthyaBimaInfoDetail(HttpServletRequest req , Bill bill) {
		String machineId = getMachineId(); 
		if (machineId == null) {
			req.setAttribute("langCode", bill.getLangCode());
			return new ModelAndView("InfoPanchayatError");
		}
		req = infoPanchayatService.SwasthyaBimaInfoDetail(req , machineId);
		req.setAttribute("langCode", bill.getLangCode());
		req.setAttribute("serviceProviderPage", bill.getServiceProviderPage());
		return new ModelAndView(bill.getServiceProviderPage());
	}
	
	
	
	private String getMachineId(){
		MachineAuth machi = billService.getMachineAuthenticationDetails(Login.SSOID);
		String machineId = machi.getMachineId();
		return machineId;
	}
}