package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import CCNET.Answer;
import CCNET.CCNET;
import CCNET.CCNETCommand;
import CCNET.CCNET_Sub_Command;
import CCNET.DEVICE;
import CCNET.IConnection;
import CCNET.PC.sPort;
import Client.ClientExecution;
import finger.rdsample.RDSample;
import locallogs.LocalLogSyncer;
import model.ApplicationStatus;
import model.Bill;
import model.NoteType;
import model.Online;
import model.PrintReceipt;
import model.TransactionHistory;
import service.BillService;
import service.CertificateService;
import webServicesRepository.utility.EncrptDesryptDataService;
import webServicesRepository.utility.VedioConference;

@EnableWebMvc
@Controller
public class KioskServiceController {
	static final Logger logger = Logger.getLogger(KioskServiceController.class);

	CCNET _ccnet = null;
	Answer answer = null;

	@Autowired
	BillService billService;

	@Autowired
	CertificateService certiService;
	
	@Autowired
    LocalLogSyncer localLogSyncer;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String showHomePage() {
		return "index";
	}

	@RequestMapping("/paymentSuccessful")
	public String showPaymentSuccessful() {
		return "paymentSuccessful";
	}

	@RequestMapping("/paymentSuccessfulHi")
	public String showPaymentSuccessfulHi() {
		return "paymentSuccessfulHi";
	}
	
	@RequestMapping("/paymentError")
	public String showPaymentError() {
		return "paymentError";
	}

	@RequestMapping("/paymentErrorHi")
	public String showPaymentErrorHindi() {
		return "paymentErrorHi";
	}

	
	@RequestMapping("/mitra")
	public String showMitraPage() {
		return "mitra";
	}

	@RequestMapping(value = "/eCardPayment", method = RequestMethod.POST)
	@ResponseBody
	public String applicationVerification(@RequestBody Online online) {
		online.setOrderId("89612345");
		online.setAmount("100");
		online.setLoginUser("Kapil Dev");
		return new ClientExecution().
				postClient(online.getOrderId(), online.getAmount(), online.getLoginUser());

	}

	@RequestMapping(value = "/getStatus", method = RequestMethod.POST)
	@ResponseBody
	public String mgetApplicationsStatusMobile(@RequestBody ApplicationStatus applicationStatus) {
		return new ClientExecution().mgetApplicationsStatusMobile(applicationStatus.getReceiptNumber());
	}

	@RequestMapping(value = "/getTransactionHistory", method = RequestMethod.POST)
	@ResponseBody
	public String getPhedTransactionHistory(@RequestBody TransactionHistory transactionHistory) {
		return new ClientExecution().getPhedTransactionHistory(transactionHistory.getConsumerKey(),
				transactionHistory.getFromDate(), transactionHistory.getToDate(), transactionHistory.getSrvId());
	}

	@RequestMapping(value = "/printRec", method = RequestMethod.POST)
	@ResponseBody
	public String printReceipt(@RequestBody PrintReceipt printReceipt) {
		return printReceipt.getPrintData();
	}

	@RequestMapping(value = "/printRec/{finger}", method = RequestMethod.POST)
	@ResponseBody
	public String getFinger(@PathVariable(value = "finger") String finger) {
		RDSample rdSample = new RDSample();
		if (finger.equals("init")) {
			boolean init = rdSample.MyDiscoverRDService("Mantra");
			if (init) {
				// call update the Kiosk health file
				logger.debug("KioskHeathFile Finger_Scanner Machine Status :: SUCCESS");
				new VedioConference().updateKioskHeathFile("Connected","Finger_Scanner");
				return rdSample.MyFingerCapture();
			} else {
				// call update the Kiosk heath file
				logger.debug("KioskHeathFile Finger_Scanner Machine Status :: FAILURE");
				new VedioConference().updateKioskHeathFile("Disconnected","Finger_Scanner");
				return "device is not Recognize";
			}
		}
		return "No data Found";
	}

	@RequestMapping(value = "/swipe/{test}", method = RequestMethod.POST)
	@ResponseBody
	public String swipe(@PathVariable(value = "amt") String amt) {

		int i = InitializeDevice();

		if (i == 0) {
			StartPoll(Integer.parseInt(amt),"1");
		}
		return "No data Found";
	}

	@RequestMapping(value = "/cashAcceptor/{amt}/{token}", method = RequestMethod.POST)
	@ResponseBody
	public String cashAcceptor(@PathVariable(value = "amt") String amt,@PathVariable(value = "token") String token) {
		logger.debug("Start CashAccepter Device for collecting TRANSACTION Cash");
		logger.debug("cashAcceptor, AMT::"+amt+"token::"+token);
		String msg = null;
		NoteType.depositAmount = Integer.parseInt(amt);
		
		int i = InitializeDevice();
		if (i == 0) {
			
			msg = StartPoll(Integer.parseInt(amt),token);
			logger.debug("cash accepter result :: "+msg);
			disconnect();
		} else {
			msg = "NoData";
		}

		if(!token.equals("0"))
			billService.updateStatusCash(msg,amt,token);
		if(msg.equals("success"))
			billService.updateTransactionDetails(token);
		
		return msg;
	}
	
	// simple cash accepter method
	/*public void StartPoll(int Amount) {
		try {
			boolean Start = true;
		//	logger.debug("CASH ACCEPT TRANSACTION START");
			logger.debug("Application want to Accept Rupees \t" + Amount);
			int NoteValue = 0;
			int TotalAmount = Amount;
			while (Start) {
				answer = _ccnet.RunCommand(CCNETCommand.Poll);
				NoteValue = answer.ReceivedData[4];

				switch (NoteValue) {
				case 1: // 10 rupees
				{

					if (answer.ReceivedData[3] == 0x80) // 0x80 mean ESCROW Event
					{
						logger.debug("Hold Fire for 10");
						answer = _ccnet.RunCommand(CCNETCommand.HOLD);
						logger.debug("10 Rupee Escrow ");
						logger.debug("Hold Command Answer is: " + answer.Message + " TotalAmount : " + TotalAmount);
						if (TotalAmount >= 10) {
							logger.debug("Stack Fire");
							answer = _ccnet.RunCommand(CCNETCommand.STACK);
							logger.debug("10 Rupee Stacked");
							TotalAmount = TotalAmount - 10;
							// start show note value
							NoteType.total10Note++;
							logger.debug("Total10Note value :: " + NoteType.total10Note);
							// end show note value

							logger.debug("Stack Command Answer is: " + answer.Message);
							
							answer = _ccnet.RunCommand(CCNETCommand.ACK);

							if (TotalAmount <= 0) {
								logger.debug("Pending Amount is Zero");
								Start = false;
								break;
							}
						} else {
							logger.debug("Total Amount is less then 10 then Return Fire");
							answer = _ccnet.RunCommand(CCNETCommand.RETURN);
							logger.debug("10 Rupee Return");
							answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
							logger.debug("Return Command Answer is: " + answer.Message);
						}
					} 
				}
					break;
				case 2: // 20 rupees
				{
					if (answer.ReceivedData[3] == 0x80) // 0x80 mean ESCROW Event
					{
						logger.debug("Hold Fire for 20");
						answer = _ccnet.RunCommand(CCNETCommand.HOLD);
						logger.debug("20 Rupee Escrow ");
						logger.debug("Hold Command Answer is: " + answer.Message);
						if (TotalAmount >= 20) {
							logger.debug("Stack Fire");
							answer = _ccnet.RunCommand(CCNETCommand.STACK);
							logger.debug("20 Rupee Stacked");
							TotalAmount = TotalAmount - 20;
							// start show note value
							NoteType.total20Note++;
							logger.debug("Total20Note value :: " + NoteType.total20Note);
							// end show note value

							answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
							logger.debug("Stack Command Answer is: " + answer.Message);
							if (TotalAmount <= 0) {
								logger.debug("Pending Amount is Zero");
								Start = false;
								break;
							}
						} else {
							logger.debug("Total Amount is less then 20 then Return Fire");
							answer = _ccnet.RunCommand(CCNETCommand.RETURN);
							logger.debug("20 Rupee Return");
							answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
							logger.debug("Return Command Answer is: " + answer.Message);
						}
					}
				}
					break;
				case 3: // 50 rupees
				{
					if (answer.ReceivedData[3] == 0x80) // 0x80 mean ESCROW Event
					{
						logger.debug("Hold Fire for 50");
						answer = _ccnet.RunCommand(CCNETCommand.HOLD);
						logger.debug("50 Rupee Escrow ");
						logger.debug("Hold Command Answer is: " + answer.Message);
						if (TotalAmount >= 50) {
							logger.debug("Stack Fire");
							answer = _ccnet.RunCommand(CCNETCommand.STACK);
							logger.debug("50 Rupee Stacked");
							TotalAmount = TotalAmount - 50;
							// start show note value
							NoteType.total50Note++;
							logger.debug("Total50Note value :: " + NoteType.total50Note);
							// end show note value

							answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
							logger.debug("Stack Command Answer is: " + answer.Message);
							if (TotalAmount <= 0) {
								logger.debug("Pending Amount is Zero");
								Start = false;
								break;
							}
						} else {
							logger.debug("Total Amount is less then 50 then Return Fire");
							answer = _ccnet.RunCommand(CCNETCommand.RETURN);
							logger.debug("50 Rupee Return");
							answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
							logger.debug("Return Command Answer is: " + answer.Message);
						}
					}
				}
					break;
				case 4: // 100 rupees
				{
					if (answer.ReceivedData[3] == 0x80) // 0x80 mean ESCROW Event
					{
						logger.debug("Hold Fire for 100");
						answer = _ccnet.RunCommand(CCNETCommand.HOLD);
						logger.debug("100 Rupee Escrow ");
						logger.debug("Hold Command Answer is: " + answer.Message);
						if (TotalAmount >= 100) {
							logger.debug("Stack Fire");
							answer = _ccnet.RunCommand(CCNETCommand.STACK);
							logger.debug("100 Rupee Stacked");
							TotalAmount = TotalAmount - 100;
							// start show note value
							NoteType.total100Note++;
							logger.debug("Total100Note value :: " + NoteType.total100Note);
							// end show note value

							answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
							logger.debug("Stack Command Answer is: " + answer.Message);
							if (TotalAmount <= 0) {
								logger.debug("Pending Amount is Zero");
								Start = false;
								break;
							}
						} else {
							logger.debug("Total Amount is less then 100 then Return Fire");
							answer = _ccnet.RunCommand(CCNETCommand.RETURN);
							logger.debug("100 Rupee Return");
							answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
							logger.debug("Return Command Answer is: " + answer.Message);
						}
					}
				}
					break;
				case 6: // 500 rupees
				{
					if (answer.ReceivedData[3] == 0x80) // 0x80 mean ESCROW Event
					{
						logger.debug("Hold Fire for 500");
						answer = _ccnet.RunCommand(CCNETCommand.HOLD);
						logger.debug("500 Rupee Escrow ");
						logger.debug("Hold Command Answer is: " + answer.Message);
						if (TotalAmount >= 500) {
							logger.debug("Stack Fire");
							answer = _ccnet.RunCommand(CCNETCommand.STACK);
							logger.debug("500 Rupee Stacked");
							TotalAmount = TotalAmount - 500;
							// start show note value
							NoteType.total500Note++;
							logger.debug("Total500Note value :: " + NoteType.total500Note);
							// end show note value

							answer = _ccnet.RunCommand(CCNETCommand.ACK);
							logger.debug("Stack Command Answer is: " + answer.Message);
							if (TotalAmount <= 0) {
								logger.debug("Pending Amount is Zero");
								Start = false;
								break;
							}
						} else {
							logger.debug("Total Amount is less then 500 then Return Fire");
							answer = _ccnet.RunCommand(CCNETCommand.RETURN);
							logger.debug("500 Rupee Return");
							answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
							logger.debug("Return Command Answer is: " + answer.Message);
						}
					}
				}
					break;
				case 7: // 2000 rupees
				{
					if (answer.ReceivedData[3] == 0x80) // 0x80 mean ESCROW Event
					{
						logger.debug("Hold Fire for 2000");
						answer = _ccnet.RunCommand(CCNETCommand.HOLD);
						logger.debug("2000 Rupee Escrow ");
						logger.debug("Hold Command Answer is: " + answer.Message);
						if (TotalAmount >= 2000) {
							logger.debug("Stack Fire");
							answer = _ccnet.RunCommand(CCNETCommand.STACK);
							logger.debug("2000 Rupee Stacked");
							TotalAmount = TotalAmount - 2000;
							// start show note value
							NoteType.total2000Note++;
							logger.debug("Total2000Note value :: " + NoteType.total2000Note);
							// end show note value

							answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
							logger.debug("Stack Command Answer is: " + answer.Message);
							if (TotalAmount <= 0) {
								logger.debug("Pending Amount is Zero");
								Start = false;
								break;
							}
						} else {
							logger.debug("Total Amount is less then 2000 then Return Fire");
							answer = _ccnet.RunCommand(CCNETCommand.RETURN);
							logger.debug("2000 Rupee Return");
							answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
							logger.debug("Return Command Answer is: " + answer.Message);
						}
					}
				}
					break;
				}

				Thread.sleep(100);

			}
			logger.debug("Complete Collecting TRANSACTION CASH");

		} catch (Exception e1) {
			logger.error("Exception in TRANSACTION CASH :" + e1.getMessage());
		}

	}*/

// cash accepter with timer	
	public String StartPoll(int Amount, String tranID) {
		String result = "";
		try {
			boolean Start = true;
			logger.info("CASH ACCEPT TRANSACTION START");
			logger.debug("Application want to Accept Rupees \t" + Amount);
			logger.info("Entering in Cash Acceptor Timer....");
			int NoteValue = 0;
			int timeFlag = 0;
			int timerVal=250;
			int pollCounter=0;
			int TotalAmount = Amount;
			NoteType.cashTimer = 30;
			while (Start) {
				
				answer = _ccnet.RunCommand(CCNETCommand.Poll);
				if(NoteType.cashTimer==0) {
					logger.debug("Cash Timer is Zero");
					Start = false;
					timeFlag = 1;
					break;
				}

				if(answer.ReceivedData[3]==20) {
					Thread.sleep(timerVal);
					if(pollCounter==3) {
					NoteType.cashTimer--;
					pollCounter=0;
					}
					pollCounter++;
				}else{
					NoteValue = answer.ReceivedData[4];
				
				logger.debug("NoteValue" + NoteValue);
				
					switch (NoteValue) {
					case 1: // 10 rupees
					{
						if (answer.ReceivedData[3] == 0x80) // 0x80 mean ESCROW Event
						{
							logger.info("Hold Fire for 10");
							answer = _ccnet.RunCommand(CCNETCommand.HOLD);
							logger.info("10 Rupee Escrow ");
							logger.debug("Hold Command Answer is: " + answer.Message + " TotalAmount : " + TotalAmount);
							if (TotalAmount >= 10) {
								logger.info("Stack Fire");
								answer = _ccnet.RunCommand(CCNETCommand.STACK);
								logger.info("10 Rupee Stacked");
								TotalAmount = TotalAmount - 10;
								// start show note value
								NoteType.total10Note++;
								
								// save machine accepted notes details 
								billService.accpetMachineAmountDetails(tranID,String.valueOf(Amount),"10");
								
								logger.debug("Total10Note value :: " + NoteType.total10Note);
								// end show note value
								NoteType.cashTimer = 30;
								logger.debug("Stack Command Answer is: " + answer.Message);
								
								answer = _ccnet.RunCommand(CCNETCommand.ACK);
	
								if (TotalAmount <= 0) {
									logger.info("Pending Amount is Zero");
									NoteType.cashTimer = 0;
									
									Start = false;
									break;
								}
							} else {
								logger.info("Total Amount is less then 10 then Return Fire");
								answer = _ccnet.RunCommand(CCNETCommand.RETURN);
								logger.info("10 Rupee Return");
								answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
								logger.debug("Return Command Answer is: " + answer.Message);
							}
						} 
					}
						break;
					case 2: // 20 rupees
					{
						if (answer.ReceivedData[3] == 0x80) // 0x80 mean ESCROW Event
						{
							logger.info("Hold Fire for 20");
							answer = _ccnet.RunCommand(CCNETCommand.HOLD);
							logger.debug("20 Rupee Escrow ");
							logger.debug("Hold Command Answer is: " + answer.Message);
							if (TotalAmount >= 20) {
								logger.info("Stack Fire");
								answer = _ccnet.RunCommand(CCNETCommand.STACK);
								logger.info("20 Rupee Stacked");
								TotalAmount = TotalAmount - 20;
								// start show note value
								NoteType.total20Note++;
								
								// save machine accepted notes details 
								billService.accpetMachineAmountDetails(tranID,String.valueOf(Amount),"20");
								
								logger.debug("Total20Note value :: " + NoteType.total20Note);
								// end show note value
								NoteType.cashTimer = 30;
								answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
								logger.debug("Stack Command Answer is: " + answer.Message);
								if (TotalAmount <= 0) {
									logger.debug("Pending Amount is Zero");
									NoteType.cashTimer = 0;
									Start = false;
									break;
								}
							} else {
								logger.info("Total Amount is less then 20 then Return Fire");
								answer = _ccnet.RunCommand(CCNETCommand.RETURN);
								logger.info("20 Rupee Return");
								answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
								logger.debug("Return Command Answer is: " + answer.Message);
							}
						}
					}
						break;
					case 3: // 50 rupees
					{
						if (answer.ReceivedData[3] == 0x80) // 0x80 mean ESCROW Event
						{
							logger.info("Hold Fire for 50");
							answer = _ccnet.RunCommand(CCNETCommand.HOLD);
							logger.info("50 Rupee Escrow ");
							logger.debug("Hold Command Answer is: " + answer.Message);
							if (TotalAmount >= 50) {
								logger.debug("Stack Fire");
								answer = _ccnet.RunCommand(CCNETCommand.STACK);
								logger.info("50 Rupee Stacked");
								TotalAmount = TotalAmount - 50;
								// start show note value
								NoteType.total50Note++;
								
								// save machine accepted notes details 
								billService.accpetMachineAmountDetails(tranID,String.valueOf(Amount),"50");
								
								logger.debug("Total50Note value :: " + NoteType.total50Note);
								// end show note value
								NoteType.cashTimer = 30;
								answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
								logger.debug("Stack Command Answer is: " + answer.Message);
								if (TotalAmount <= 0) {
									logger.debug("Pending Amount is Zero");
									NoteType.cashTimer = 0;
									Start = false;
									break;
								}
							} else {
								logger.info("Total Amount is less then 50 then Return Fire");
								answer = _ccnet.RunCommand(CCNETCommand.RETURN);
								logger.info("50 Rupee Return");
								answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
								logger.debug("Return Command Answer is: " + answer.Message);
							}
						}
					}
						break;
					case 4: // 100 rupees
					{
						if (answer.ReceivedData[3] == 0x80) // 0x80 mean ESCROW Event
						{
							logger.info("Hold Fire for 100");
							answer = _ccnet.RunCommand(CCNETCommand.HOLD);
							logger.debug("100 Rupee Escrow ");
							logger.debug("Hold Command Answer is: " + answer.Message);
							if (TotalAmount >= 100) {
								logger.debug("Stack Fire");
								answer = _ccnet.RunCommand(CCNETCommand.STACK);
								logger.debug("100 Rupee Stacked");
								TotalAmount = TotalAmount - 100;
								// start show note value
								NoteType.total100Note++;
								
								// save machine accepted notes details 
								billService.accpetMachineAmountDetails(tranID,String.valueOf(Amount),"100");
								
								logger.debug("Total100Note value :: " + NoteType.total100Note);
								// end show note value
								NoteType.cashTimer = 30;
								answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
								logger.debug("Stack Command Answer is: " + answer.Message);
								if (TotalAmount <= 0) {
									logger.debug("Pending Amount is Zero");
									NoteType.cashTimer = 0;
									Start = false;
									break;
								}
							} else {
								logger.info("Total Amount is less then 100 then Return Fire");
								answer = _ccnet.RunCommand(CCNETCommand.RETURN);
								logger.debug("100 Rupee Return");
								answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
								logger.debug("Return Command Answer is: " + answer.Message);
							}
						}
					}
						break;
					case 6: // 500 rupees
					{
						if (answer.ReceivedData[3] == 0x80) // 0x80 mean ESCROW Event
						{
							logger.info("Hold Fire for 500");
							answer = _ccnet.RunCommand(CCNETCommand.HOLD);
							logger.debug("500 Rupee Escrow ");
							logger.debug("Hold Command Answer is: " + answer.Message);
							if (TotalAmount >= 500) {
								logger.debug("Stack Fire");
								answer = _ccnet.RunCommand(CCNETCommand.STACK);
								logger.debug("500 Rupee Stacked");
								TotalAmount = TotalAmount - 500;
								// start show note value
								NoteType.total500Note++;
								
								// save machine accepted notes details 
								billService.accpetMachineAmountDetails(tranID,String.valueOf(Amount),"500");
								
								logger.debug("Total500Note value :: " + NoteType.total500Note);
								// end show note value
								NoteType.cashTimer = 30;
								answer = _ccnet.RunCommand(CCNETCommand.ACK);
								logger.debug("Stack Command Answer is: " + answer.Message);
								if (TotalAmount <= 0) {
									logger.debug("Pending Amount is Zero");
									NoteType.cashTimer = 0;
									Start = false;
									break;
								}
							} else {
								logger.info("Total Amount is less then 500 then Return Fire");
								answer = _ccnet.RunCommand(CCNETCommand.RETURN);
								logger.info("500 Rupee Return");
								answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
								logger.info("Return Command Answer is: " + answer.Message);
							}
						}
					}
						break;
					case 7: // 2000 rupees
					{
						if (answer.ReceivedData[3] == 0x80) // 0x80 mean ESCROW Event
						{
							logger.info("Hold Fire for 2000");
							answer = _ccnet.RunCommand(CCNETCommand.HOLD);
							logger.info("2000 Rupee Escrow ");
							logger.debug("Hold Command Answer is: " + answer.Message);
							if (TotalAmount >= 2000) {
								logger.debug("Stack Fire");
								answer = _ccnet.RunCommand(CCNETCommand.STACK);
								logger.debug("2000 Rupee Stacked");
								TotalAmount = TotalAmount - 2000;
								// start show note value
								NoteType.total2000Note++;

								// save machine accepted notes details 
								billService.accpetMachineAmountDetails(tranID,String.valueOf(Amount),"2000");
								
								logger.debug("Total2000Note value :: " + NoteType.total2000Note);
								// end show note value
								NoteType.cashTimer = 30;
								answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
								logger.debug("Stack Command Answer is: " + answer.Message);
								if (TotalAmount <= 0) {
									logger.info("Pending Amount is Zero");
									NoteType.cashTimer = 0;
									Start = false;
									break;
								}
							} else {
								logger.info("Total Amount is less then 2000 then Return Fire");
								answer = _ccnet.RunCommand(CCNETCommand.RETURN);
								logger.info("2000 Rupee Return");
								answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
								logger.debug("Return Command Answer is: " + answer.Message);
							}
						}
					}
						break;
					}
				}
				Thread.sleep(100);
			}
			
			//logger.debug(" timeFlag:: "+ timeFlag);

			//System.out.println(" timeFlag:: "+ timeFlag);
			
			if(timeFlag==1)
				result = "cancel";
			else
				result = "success";
			
			logger.debug("Complete Collecting TRANSACTION CASH , timeFlag::"+ timeFlag+" result "+ result);

		} catch (Exception e1) {
			result = "cancel";
			logger.error("Exception in TRANSACTION CASH :" + e1.getMessage());
		}

		return result;
	}
	
	public int InitializeDevice() {
		int x = 0;
		String machinestatus = "";
		try {
			logger.debug("Initialize Cash Acceptor");
			IConnection comPort = new sPort();
			comPort.setPath("COM2");
			comPort.setBaudRate(9600);
			_ccnet = new CCNET(DEVICE.Bill_Validator, comPort);

			logger.info("Reset Acceptor");
			answer = _ccnet.RunCommand(CCNETCommand.RESET);

			logger.debug("Reset Answer is:" + answer.Message);

			logger.info("Poll Called");
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			logger.debug("Poll Answer is:" + answer.Message);
			logger.info("ACK Called");
			answer = _ccnet.RunCommand(CCNETCommand.ACK);
			logger.debug("ACK Answer is:" + answer.Message);
			logger.info("Get Status Called");
			answer = _ccnet.RunCommand(CCNETCommand.GET_STATUS);
			logger.debug("Get Status Answer is:" + answer.Message);
			
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			answer = _ccnet.RunCommand(CCNETCommand.ACK);
			logger.debug("Device Identification Called ");
			answer = _ccnet.RunCommand(CCNETCommand.IDENTIFICATION);
			
			if(CCNETCommand.IDENTIFICATION!=null) 
				machinestatus = "Connected";
			else 
				machinestatus = "Disconnected";
			
			logger.debug("Device Identification Answer is:" + answer.Message);
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			answer = _ccnet.RunCommand(CCNETCommand.ACK);
			logger.info("Set Security Called");
			answer = _ccnet.RunCommand(CCNETCommand.SET_SECURITY);
			logger.debug("Set Security Answer is:" + answer.Message);
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			answer = _ccnet.RunCommand(CCNETCommand.ACK);
			logger.info("Get Status Called");
			answer = _ccnet.RunCommand(CCNETCommand.GET_STATUS);
			logger.debug("Get Status Answer is:" + answer.Message);
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			answer = _ccnet.RunCommand(CCNETCommand.ACK);
			logger.info("Get Bill Table Called");
			answer = _ccnet.RunCommand(CCNETCommand.GET_BILL_TABLE);
			logger.debug("Get Bill Table Answer is:" + answer.Message);
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			answer = _ccnet.RunCommand(CCNETCommand.ACK);
			logger.info("Get Status Called");
			answer = _ccnet.RunCommand(CCNETCommand.GET_STATUS);
			logger.debug("Get Status Answer is:" + answer.Message);
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			answer = _ccnet.RunCommand(CCNETCommand.ACK);

			logger.info("Enable Bill Types Called");
			answer = _ccnet.RunCommand(CCNETCommand.ENABLE_BILL_TYPES,
					CCNET_Sub_Command.ENABLE_BILL_TYPES_to_escrow_mode);
						
			// call update the Kiosk health file
			logger.debug("KioskHeathFile Cash_Acceptor Machine Status :: "+machinestatus);
			new VedioConference().updateKioskHeathFile(machinestatus,"Cash_Acceptor");
			
		} catch (Exception e) {
			logger.error("KioskServiceController, Exception in Initialize Cash Acceptor :" + e.getMessage());
			x = 1;
		}

		return x;
	}

	public void disconnect() {
		_ccnet.RunCommand(CCNETCommand.RESET);
		_ccnet = null;
	     logger.debug("Disconnect Cash Acceptor");
	 }
	
	@RequestMapping("/paymentSuccess")
	public ModelAndView paymentSuccess(HttpServletRequest req, Bill bill) {
		List<Bill> details = new ArrayList<Bill>();
		logger.debug("PaymentSuccess, TransactionId::" + bill.getTransactionId()+" serviceId :"+bill.getServiceProviderID()+ "  transid : "+bill.getTransactionId());
		Bill printBill = billService.getPrintBillDetails(bill);
		
		billService.updateTransactionDetails(bill.getTransactionId());
		details.add(printBill);
		req.setAttribute("details", details);
		req.setAttribute("transID", bill.getTransactionId());
		if(bill.getLangCode()==0)
			return new ModelAndView("paymentSuccessful");
		else
			return new ModelAndView("paymentSuccessfulHi");
	}
	
	@RequestMapping(value = "/backtoback/{amt}/{trnsid}/{serviceId}", method = RequestMethod.POST)
	@ResponseBody
	public String backToBackTrans(@PathVariable(value = "amt") String amt,
		@PathVariable(value = "trnsid") String transactionId, @PathVariable(value = "serviceId") String serviceId)
			throws Exception {
		logger.debug("backToBackTrans, trnsid :" + transactionId + "amount :" + amt + "service id::" + serviceId);

		if (StringUtils.isBlank(transactionId)) {
			logger.error("TransactionId is empty.");
			return null;
		}

		billService.updatePaymentMode(transactionId , "CASH");
		
		String returnStatusStr = null;
		try {
			String backToBackurl = billService.getbackToBackurl(amt, transactionId, serviceId, "cash");
			logger.debug("backToBackTrans, BackToBackurl :" + backToBackurl);

			EncrptDesryptDataService eds = new EncrptDesryptDataService();

			String encryptedBackTOBackUrl = eds.sendPostForEncryptData(backToBackurl);
			logger.info("backToBackTrans, encriptBackTOBackUrl :" + encryptedBackTOBackUrl);

			String encryptedResponse = eds.sendPostForBacktoBack(encryptedBackTOBackUrl);
			logger.info("backToBackTrans, backtoBackUrl :" + encryptedResponse);

			String decryptedResponse = eds.sendPostForDecryptData(encryptedResponse);
			logger.info("backToBackTrans, decriptBackTOBackUrl :" + decryptedResponse);

			// Assuming the fact that if we get SUCCESS & TRANSACTIONID in the response
			// It was a successful transaction at emitra's end and we would need to
			// track this in our system OR if we fail to do so, we'll revert it.
			org.json.JSONObject json = new org.json.JSONObject(decryptedResponse);
			String transactionStatus = json.optString("TRANSACTIONSTATUS");
			String emitraTransactionId = json.optString("TRANSACTIONID");

			if (transactionStatus.equalsIgnoreCase("SUCCESS"))
				returnStatusStr = transactionStatus + "##" + emitraTransactionId;
			else
				returnStatusStr = transactionStatus + "##" + (String) json.get("MSG");

			try {
				billService.saveBackToBackTransaction(decryptedResponse, "CASH");

				Bill transactionDetails = new Bill();
				transactionDetails.setBackToBackTransactionID(emitraTransactionId);
				transactionDetails.setBackToBackTransactionStatus(transactionStatus);

				String emitraTransactionFlag = "";

				if (transactionStatus.equalsIgnoreCase("SUCCESS"))
					emitraTransactionFlag = "1";
				else
					emitraTransactionFlag = "0";

				billService.updateTransactionDetails(emitraTransactionFlag, transactionDetails, transactionId);
				logger.info("backToBackTrans, Transaction Status :" + encryptedResponse);
			} catch (Exception inEx) {
				logger.error("Caught an exception while inserting backToBack transaction details in the DB for transactionId: "+ transactionId,
						inEx);
				if (transactionStatus.equalsIgnoreCase("SUCCESS")) {
					localLogSyncer.writeToLocalLogs(emitraTransactionId, transactionId, serviceId, amt);
				}
				String message = "There was some updating the Emitra+ DB, Please try again after some-time.";
				returnStatusStr = "FAILURE##" + message;
			}
					} catch (Exception ex) {
			logger.error("Caught an exception while processing emitra backToBackTransaction for transactionId: " + transactionId, ex);

			String message = "There was error while getting data from Emitra, Please try again after some-time.";
			returnStatusStr = "FAILURE##" + message;
		}
		return returnStatusStr;
	}
	
	@RequestMapping(value = "/certificatebacktoback/{amt}/{transid}/{serviceID}", method = RequestMethod.POST)
    @ResponseBody
    public String certificateBackToBackTrans(@PathVariable(value = "amt") String amt,
            @PathVariable(value = "transid") String transactionId, @PathVariable(value = "serviceID") String serviceID) {
        logger.debug("certificateBackToBackTrans, trnsid :" + transactionId + "amount :" + amt + "service id::" + serviceID);

        if (StringUtils.isBlank(transactionId)) {
            logger.error("TransactionId is null or empty.");
            return null;
        }

	    billService.updatePaymentMode(transactionId , "CASH");
        
        String returnStatusStr = null; 
        try {
            String backToBackurl = billService.getcertibackToBackurl(amt, transactionId, serviceID, "cash");
            logger.debug("certificateBackToBackTrans, BackToBackurl :" + backToBackurl);

            EncrptDesryptDataService eds = new EncrptDesryptDataService();

	        String encryptedB2BUrl = eds.sendPostForEncryptData(backToBackurl);
            logger.debug("certificateBackToBackTrans, encryptedBackTOBackUrl :" + encryptedB2BUrl);

	        String backtoBackResponse = eds.sendPostForBacktoBack(encryptedB2BUrl);
            logger.debug("certificateBackToBackTrans, backtoBackResponse :" + backtoBackResponse);

	        String decryptB2BResponse = eds.sendPostForDecryptData(backtoBackResponse);
            logger.info("certificateBackToBackTrans, decriptBackTOBackUrl :" + decryptB2BResponse);

            org.json.JSONObject json = new org.json.JSONObject(decryptB2BResponse);

            String transactionStatus = json.optString("TRANSACTIONSTATUS");
            String emitraTransactionId = json.optString("TRANSACTIONID");
            
            if (transactionStatus.equalsIgnoreCase("SUCCESS")) 
                returnStatusStr = transactionStatus + "##" + emitraTransactionId;
            else
            	returnStatusStr = transactionStatus+ "##" +(String) json.get("MSG");

            try {
                billService.saveBackToBackTransaction(decryptB2BResponse, "CASH");

	            Bill transactionDetails = new Bill();
                transactionDetails.setBackToBackTransactionID(emitraTransactionId);
                transactionDetails.setBackToBackTransactionStatus(transactionStatus);

                String emitraTransactionFlag = "";
                
                if (transactionStatus.equalsIgnoreCase("SUCCESS")) 
                    emitraTransactionFlag = "1";
                else
                    emitraTransactionFlag = "0";

                billService.updateTransactionDetails(emitraTransactionFlag, transactionDetails, transactionId);
                logger.info("certificateBackToBackTrans, Transaction Status :" + backtoBackResponse);
            } catch (Exception ex) {
                logger.error("Caught an exception while inserting certificateBackToBack transaction details in the DB for transactionId: " + transactionId, ex);

                if (transactionStatus.equalsIgnoreCase("SUCCESS")) {
                    localLogSyncer.writeToLocalLogs(emitraTransactionId, transactionId, serviceID, amt);
                }
                String message = "There was some updating the Emitra+ DB, Please try again after some-time.";
                returnStatusStr = "FAILURE##" + message;
            }
        } catch (Exception ex) {
            logger.error("Caught an exception while processing emitra certificateBackToBackTransaction for transactionId: " + transactionId, ex);
            
            String message = "There was error while getting data from Emitra, Please try again after some-time.";
            returnStatusStr = "FAILURE##" + message;
        }
        return returnStatusStr;
    }
	
	@RequestMapping(value = "/verifyTransaction/{requestid}/{serviceid}", method = RequestMethod.POST)
	@ResponseBody
	public String verifyTransaction(@PathVariable(value = "requestid") String requestID, @PathVariable(value = "serviceid") String serviceID) {
		String ssoToken = "0";
		logger.debug("verifyTransaction, requestID :" + requestID + " ,serviceID::" + serviceID);
		String verifyStatus = null;
		if (requestID != null && serviceID != null) {

			String checkSumURL = "{\"MERCHANTCODE\":\"EMITRAPLUS\",\"REQUESTID\":\"" + requestID + "\",\"SSOTOKEN\":\""
					+ ssoToken + "\"}";
			logger.debug("verifyTransaction, checksumurl :" + checkSumURL);

			EncrptDesryptDataService eds = new EncrptDesryptDataService();
			String checkSum = eds.getCheckSUM(checkSumURL);
			logger.debug("verifyTransaction, checkSum :" + checkSum);
			String toBeEncrypt = "{\"MERCHANTCODE\":\"EMITRAPLUS\",\"REQUESTID\":\"" + requestID + "\",\"SERVICEID\":\""
					+ serviceID + "\",\"CHECKSUM\":\"" + checkSum + "\",\"SSOTOKEN\":\"" + ssoToken + "\"}";

			String encriptVerifyString = null;
			try {
				encriptVerifyString = eds.sendPostForEncryptData(toBeEncrypt);
			} catch (Exception e1) {
				logger.error("KioskServiceController, Exception in verifyTransaction for sendPostForEncryptData : "+e1.getMessage());
				/*e1.printStackTrace();*/
			}
			logger.debug("verifyTransaction, encriptVerifyString :" + encriptVerifyString);

			String verifyTransactionResponse = null;
			try {
				verifyTransactionResponse = eds.sendPostForVerifyTranscation(encriptVerifyString);
			} catch (Exception e1) {
				logger.error("KioskServiceController, Exception in verifyTransaction for sendPostForVerifyTranscation : "+e1.getMessage());
				/*e1.printStackTrace();*/
			}
			logger.debug("verifyTransaction, verifyTransactionResponse :" + verifyTransactionResponse);

			String decriptVerifyTransactionResponse = null;
			try {
				decriptVerifyTransactionResponse = eds.sendPostForDecryptData(verifyTransactionResponse);
			} catch (Exception e1) {
				logger.error("KioskServiceController, Exception in verifyTransaction for sendPostForDecryptData : "+e1.getMessage());
				/*e1.printStackTrace();*/
			}
			logger.debug("verifyTransaction, decriptVerifyTransactionResponse :" + decriptVerifyTransactionResponse);
			billService.saveVerifiedData(decriptVerifyTransactionResponse);
		}
		return verifyStatus;
	}
	
	@RequestMapping(value = "/cancelTranscation/{emitraTransactionId}", method = RequestMethod.POST)
	@ResponseBody
	public String cancelTranscation(@PathVariable(value = "emitraTransactionId") String emitraTransactionId) {
		
		logger.debug("cancelTranscation, emitraTransactionId :"+emitraTransactionId);
		logger.info("Entering in Cancel Transaction....");
		String cancelStatus = null;
		if (emitraTransactionId != null) {

			String cancelTranscationurl = billService.getCancelTranscationurl(emitraTransactionId);
			logger.debug("cancelTranscation, cancelTranscationurl :"+cancelTranscationurl);

			EncrptDesryptDataService eds = new EncrptDesryptDataService();
			String encriptCancelTranscationUrl = null;
			try {
				encriptCancelTranscationUrl = eds.sendPostForEncryptData(cancelTranscationurl);
			} catch (Exception e1) {
				logger.error("KioskServiceController, Exception in cancelTranscation for sendPostForEncryptData : "+e1.getMessage());
				/*e1.printStackTrace();*/
			}
			logger.debug("cancelTranscation, encriptBackTOBackUrl :"+encriptCancelTranscationUrl);
			String cancelTranscationResponse = null;
			try {
				cancelTranscationResponse = eds.sendPostForCancelTranscation(encriptCancelTranscationUrl);
			} catch (Exception e1) {
				logger.error("KioskServiceController, Exception in cancelTranscation for sendPostForCancelTranscation : "+e1.getMessage());
				/*e1.printStackTrace();*/
			}
			
			logger.debug("cancelTranscation, cancelTranscationResponse :"+cancelTranscationResponse);
			String decriptCancelTranscationUrl = null;
			try {
				decriptCancelTranscationUrl = eds.sendPostForDecryptData(cancelTranscationResponse);
				logger.debug("cancelTranscation, decriptCancelTranscationUrl :"+decriptCancelTranscationUrl);
				
				if(decriptCancelTranscationUrl!=null && !decriptCancelTranscationUrl.equals("")) {
			        billService.saveCancelTranscationurl(decriptCancelTranscationUrl);
			        JSONParser parser = new JSONParser();
			        JSONObject json;
			        json = (JSONObject) parser.parse(decriptCancelTranscationUrl);
			        String  CANCELSTATUS= (String)json.get("CANCELSTATUS");
			        if(CANCELSTATUS.equalsIgnoreCase("SUCCESS"))
			        	billService.updateDeleteTransactionFlag("Yes", emitraTransactionId);
				}
			} catch (Exception e1) {
				logger.error("KioskServiceController, Exception in cancelTranscation : "+e1.getMessage());
				/*e1.printStackTrace();*/
			}
			logger.debug("cancelTranscation, decriptCancelTranscationUrl :"+decriptCancelTranscationUrl);
		}
		return cancelStatus;
	}
	
			@RequestMapping("/certiPaymentSuccess")
	public ModelAndView certiPaymentSuccess(HttpServletRequest req, Bill bill) {
		List<Bill> certiDetails = new ArrayList<Bill>();
		logger.debug("certiPaymentSuccess, TransactionId::" + bill.getTransactionId()+" serviceId :"+bill.getServiceProviderID()+ "  transid : "+bill.getTransactionId());
		Bill printBill = certiService.getCertiPrintBillDetails(bill);
		printBill.setBillActualAmount(bill.getBillAmount());
		printBill.setName("");
		printBill.setBillEmail("");
		printBill.setDueDate("");
		printBill.setServiceProviderName(bill.getCertiServiceName());
		billService.updateTransactionDetails(bill.getTransactionId());
		certiDetails.add(printBill);
		req.setAttribute("details", certiDetails);
		req.setAttribute("serviceID", bill.getServiceProviderID());
		req.setAttribute("tdate", printBill.getTransactionDate());
		req.setAttribute("tid", printBill.getCertiBackToBackTransactionID());
		req.setAttribute("receipt", printBill.getrECEIPTNO());
		if(bill.getLangCode()==0)
			return new ModelAndView("CertiPaymentSuccessful");
		else
			return new ModelAndView("CertiPaymentSuccessfulHi");
	}
	
	@RequestMapping(value = "/CashMachineAmount/{amount}/{paymentType}/{tid}", method = RequestMethod.POST)
	@ResponseBody
	public String getCashMachineAmount(@PathVariable(value = "amount") String amount,@PathVariable(value = "paymentType") String paymentType,@PathVariable(value = "tid") String tid, HttpServletRequest req) {
		logger.debug("paymentType : "+paymentType+" udpateAmount : "+amount+" tid: "+tid);
		int resultAmount =0;
		double d = Double.parseDouble(amount);
		resultAmount = new EncrptDesryptDataService().getCashMachineAmount(d);
		billService.updatedAmount(d,resultAmount,tid);
		return resultAmount+"";
	}
	
	@ResponseBody
    @RequestMapping(value = "/cancelTranscationLLSyncer/{transactionId}/{requestId}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> cancelTranscationLLSyncer(@PathVariable(value = "transactionId") String transactionId,
            @PathVariable(value = "requestId") String requestId) {

        try {
            logger.debug("cancelTranscationLLSyncer, transactionId: " + transactionId + ", requestId: " + requestId);

            EncrptDesryptDataService eds = new EncrptDesryptDataService();
            String checksumurl = "{\"MERCHANTCODE\":\"EMITRAPLUS\",\"REQUESTID\":\"" + requestId
                    + "\",\"SSOTOKEN\":\"0\"}";
            logger.debug("cancelTranscationLLSyncer, checkSUMURL" + checksumurl);
            String checksum = eds.sendPostCheckSum(checksumurl);

            String cancelTranscationUrl = "{\"MERCHANTCODE\":\"EMITRAPLUS\",\"REQUESTID\":\"" + requestId + "\","
                    + "\"EMITRATOKEN\":\"" + transactionId + "\",\"CANCELREMARK\":\"Cash Not Received\","
                    + "\"ENTITYTYPEID\":\"4\"," + "\"SSOTOKEN\":\"0\",\"CHECKSUM\":\"" + checksum + "\" }";

            logger.debug("cancelTranscationLLSyncer, cancelTranscationUrl :" + cancelTranscationUrl);

            String encryptedCTUrl = eds.sendPostForEncryptData(cancelTranscationUrl);
            logger.debug("cancelTranscationLLSyncer, encryptedCTUrl :" + encryptedCTUrl);

            String encryptedCTResponse = eds.sendPostForCancelTranscation(encryptedCTUrl);
            logger.debug("cancelTranscationLLSyncer, encryptedCTResponse :" + encryptedCTResponse);

            String dencryptedCTResponse = eds.sendPostForDecryptData(encryptedCTResponse);
            logger.debug("cancelTranscationLLSyncer, dencryptedCTResponse :" + dencryptedCTResponse);

            if (StringUtils.isNotBlank(dencryptedCTResponse)) {
                org.json.JSONObject jsonObj = new org.json.JSONObject(dencryptedCTResponse);

                int status = Integer.valueOf(jsonObj.optString("CANCELSTATUSCODE"));
                if(status >= HttpStatus.OK.value() && status < HttpStatus.BAD_REQUEST.value()) {
                    return ResponseEntity.ok(dencryptedCTResponse);
                }
            }
        } catch (Exception ex) {
            logger.error("Caught an exception while cancelling transactionId: " + transactionId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        // assuming the fact that the transactionID & requestId are Invalid
        return ResponseEntity.badRequest().build();
    }
}