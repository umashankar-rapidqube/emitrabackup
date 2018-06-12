package webServicesRepository.utility;

import org.apache.log4j.Logger;

import CCNET.Answer;
import CCNET.CCNET;
import CCNET.CCNETCommand;
import CCNET.CCNET_Sub_Command;
import CCNET.DEVICE;
import CCNET.IConnection;
import CCNET.PC.sPort;

public class DeviceInfo {
	static final Logger logger = Logger.getLogger(DeviceInfo.class);

	public static CCNET _ccnet = null;
	public static Answer answer = null;
	public static int totalAmount = 0;

	public static void main(String[] args) {
		totalAmount = 30;
		int i = InitializeDevice();
		if (i == 0) {
			StartPoll(totalAmount);
		} else {
		}
	}

	public static void StartPoll(int Amount) {
		try {
			boolean Start = true;
			logger.debug("CASH ACCEPT TRANSACTION START");
			logger.debug("Application want to Accept Rupees \t" + Amount);
			//System.out.println("CASH ACCEPT TRANSACTION START");
			//System.out.println("Application want to Accept Rupees \t" + Amount);
			int NoteValue = 0;
			int TotalAmount = Amount;

			DeviceInfo deviceInfo = new DeviceInfo();

			while (Start) {
				answer = _ccnet.RunCommand(CCNETCommand.Poll);
				NoteValue = answer.ReceivedData[4];
				logger.debug("event status :: " + answer.ReceivedData[3]);
				//System.out.println("event status :: " + answer.ReceivedData[3]);
				switch (NoteValue) {
				case 1: // 10 rupees
					Start = deviceInfo.startPollCalculation(TotalAmount, 10);
					break;

				case 2: // 20 rupees
					Start = deviceInfo.startPollCalculation(TotalAmount, 20);
					break;

				case 3: // 50 rupees
					Start = deviceInfo.startPollCalculation(TotalAmount, 50);
					break;

				case 4: // 100 rupees
					Start = deviceInfo.startPollCalculation(TotalAmount, 100);
					break;

				case 5: // 500 rupees
					Start = deviceInfo.startPollCalculation(TotalAmount, 500);
					break;

				case 7: // 2000 rupees
					Start = deviceInfo.startPollCalculation(TotalAmount, 2000);
					break;
				}
				Thread.sleep(1000);
			}
			logger.debug("complete");
			//System.out.println("complete");
		} catch (Exception e1) {
			logger.error("DeviceInfo, Exception Received:" + e1.getMessage());
			//System.out.println("Exception Received:" + e1.getMessage());
		}
	}

	private boolean startPollCalculation(int totalAmount, int amount) {

		if (answer.ReceivedData[3] == 0x80) // 0x80 mean ESCROW Event
		{
			logger.debug("Hold Fire");
			//System.out.println("Hold Fire");
			answer = _ccnet.RunCommand(CCNETCommand.HOLD);
			logger.debug(amount + " Rupee Escrow ");
			logger.debug("Hold Command Answer is: " + answer.Message);
			//System.out.println(amount + " Rupee Escrow ");
			//System.out.println("Hold Command Answer is: " + answer.Message);
			if (totalAmount >= amount) {
				totalAmount = totalAmount - amount;
				logger.debug("Stack Fire");
				//System.out.println("Stack Fire");
				answer = _ccnet.RunCommand(CCNETCommand.STACK);
				logger.debug(amount + " Rupee Stacked");
				logger.debug("Stack Command Answer is: " + answer.Message);
				//System.out.println(amount + " Rupee Stacked");
				//System.out.println("Stack Command Answer is: " + answer.Message);
				answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit

				if (totalAmount <= 0) {
					logger.debug("Pending Amount is Zero");
					//System.out.println("Pending Amount is Zero");
					return false;
				}

			} else {
				logger.debug("Total Amount is less then " + amount);
				logger.debug("Return Fire");
				//System.out.println("Total Amount is less then " + amount);
				//System.out.println("Return Fire");
				answer = _ccnet.RunCommand(CCNETCommand.RETURN);
				//System.out.println(amount + " Rupee Return");
				logger.debug(amount + " Rupee Return");
				answer = _ccnet.RunCommand(CCNETCommand.ACK);// Amit
				//System.out.println("Return Command Answer is: " + answer.Message);
				logger.debug("Return Command Answer is: " + answer.Message);
			}
		}

		return true;
	}

	public static int InitializeDevice() {
		int x = 0;
		try {
			logger.debug("Initialize Cash Acceptor");
			//System.out.println("Initialize Cash Acceptor");
			IConnection comPort = new sPort();
			comPort.setPath("COM2");
			comPort.setBaudRate(9600);
			logger.debug("DEVICE.Bill_Validator called");
			//System.out.println("DEVICE.Bill_Validator called");
			_ccnet = new CCNET(DEVICE.Bill_Validator, comPort);
			// evt = new MyEvents();
			// _ccnet.addEventListener(evt);

			logger.debug("Reset Acceptor");
			//System.out.println("Reset Acceptor");
			answer = _ccnet.RunCommand(CCNETCommand.RESET);

			logger.debug("Reset Answer is:" + answer.Message);
			//System.out.println("Reset Answer is:" + answer.Message);
			
			logger.debug("Poll Called");
			//System.out.println("Poll Called");
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			logger.debug("Poll Answer is:" + answer.Message);
			logger.debug("ACK Called");
			//System.out.println("Poll Answer is:" + answer.Message);
			//System.out.println("ACK Called");
			answer = _ccnet.RunCommand(CCNETCommand.ACK);
			logger.debug("ACK Answer is:" + answer.Message);
			logger.debug("Get Status Called");
			//System.out.println("ACK Answer is:" + answer.Message);
			//System.out.println("Get Status Called");
			answer = _ccnet.RunCommand(CCNETCommand.GET_STATUS);
			logger.debug("Get Status Answer is:" + answer.Message);
			//System.out.println("Get Status Answer is:" + answer.Message);
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			answer = _ccnet.RunCommand(CCNETCommand.ACK);
			logger.debug("Device Identification Called ");
			//System.out.println("Device Identification Called ");
			answer = _ccnet.RunCommand(CCNETCommand.IDENTIFICATION);
			logger.debug("Device Identification Answer is:" + answer.Message);
			//System.out.println("Device Identification Answer is:" + answer.Message);
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			answer = _ccnet.RunCommand(CCNETCommand.ACK);
			logger.debug("Set Security Called");
			//System.out.println("Set Security Called");
			answer = _ccnet.RunCommand(CCNETCommand.SET_SECURITY);
			logger.debug("Set Security Answer is:" + answer.Message);
			//System.out.println("Set Security Answer is:" + answer.Message);
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			answer = _ccnet.RunCommand(CCNETCommand.ACK);
			logger.debug("Get Status Called");
			//System.out.println("Get Status Called");
			answer = _ccnet.RunCommand(CCNETCommand.GET_STATUS);
			logger.debug("Get Status Answer is:" + answer.Message);
			//System.out.println("Get Status Answer is:" + answer.Message);
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			answer = _ccnet.RunCommand(CCNETCommand.ACK);
			logger.debug("Get Bill Table Called");
			//System.out.println("Get Bill Table Called");
			answer = _ccnet.RunCommand(CCNETCommand.GET_BILL_TABLE);
			
			logger.debug("Get Bill Table Answer is:" + answer.Message);
			//System.out.println("Get Bill Table Answer is:" + answer.Message);
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			answer = _ccnet.RunCommand(CCNETCommand.ACK);
			logger.debug("Get Status Called");
			//System.out.println("Get Status Called");
			answer = _ccnet.RunCommand(CCNETCommand.GET_STATUS);
			logger.debug("Get Status Answer is:" + answer.Message);
			//System.out.println("Get Status Answer is:" + answer.Message);
			answer = _ccnet.RunCommand(CCNETCommand.Poll);
			answer = _ccnet.RunCommand(CCNETCommand.ACK);
			logger.debug("Enable Bill Types Called");
			//System.out.println("Enable Bill Types Called");
			answer = _ccnet.RunCommand(CCNETCommand.ENABLE_BILL_TYPES,
					CCNET_Sub_Command.ENABLE_BILL_TYPES_to_escrow_mode);
		} catch (Exception e) {
			logger.error("DeviceInfo, Exception Received:" + e.getMessage());
			//System.out.println("Exception Received:" + e.getMessage());
			x = 1;
		}
		return x;
	}
}