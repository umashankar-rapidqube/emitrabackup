package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import model.Bill;

public interface InfoPanchayatService {

	public HttpServletRequest kioskInfoDetail(HttpServletRequest req, String machineId);

	public ModelAndView rationDetails(HttpServletRequest req , HttpServletResponse res , Bill bill);

	public HttpServletRequest bhamasaInfoDetail(HttpServletRequest req, String machineId);
	
	public HttpServletRequest SwasthyaBimaInfoDetail(HttpServletRequest req, String machineId);
}