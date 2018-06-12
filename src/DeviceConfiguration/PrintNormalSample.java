package DeviceConfiguration;

import org.apache.log4j.Logger;

import jpos.JposConst;
import jpos.POSPrinterConst;
import jpos.events.StatusUpdateEvent;
import jpos.events.StatusUpdateListener;
import webServicesRepository.utility.VedioConference;

class MyEvents implements StatusUpdateListener {
	static final Logger logger = Logger.getLogger("Receipt_Printer Events");

	public void statusUpdateOccurred(jpos.events.StatusUpdateEvent e) {
		logger.info("Receipt_Printer status :: "+e.getStatus());
		String machinestatus = "";
		
		switch (e.getStatus()) {
		
		case POSPrinterConst.PTR_SUE_COVER_OK:
			//System.out.println("PTR_SUE_COVER_OK");
			logger.debug("PTR_SUE_COVER_OK");			
			break;
		case POSPrinterConst.PTR_SUE_COVER_OPEN:
			logger.debug("PTR_SUE_COVER_OPEN");
			//System.out.println("PTR_SUE_COVER_OPEN");
			break;
		case POSPrinterConst.PTR_SUE_REC_EMPTY:
			//System.out.println("PTR_SUE_REC_EMPTY");
			logger.debug("PTR_SUE_REC_EMPTY");
			break;
		case POSPrinterConst.PTR_SUE_REC_NEAREMPTY:
			logger.debug("PTR_SUE_REC_NEAREMPTY");
			//System.out.println("PTR_SUE_REC_NEAREMPTY");
			break;
		case POSPrinterConst.PTR_SUE_REC_PAPEROK:
			logger.debug("PTR_SUE_REC_PAPEROK");
			//System.out.println("PTR_SUE_REC_PAPEROK");
			break;
		case JposConst.JPOS_SUE_POWER_OFF_OFFLINE:
			logger.debug("JPOS_SUE_POWER_ONLINE");
			//System.out.println("JPOS_SUE_POWER_ONLINE");
			machinestatus="Disconnected";
			break;
		case JposConst.JPOS_SUE_POWER_ONLINE:
			logger.debug("JPOS_SUE_POWER_ONLINE");
			//System.out.println("JPOS_SUE_POWER_ONLINE");
			machinestatus="Connected";
			break;

		}
		
		// call update the Kiosk heath file
		logger.info("Receipt_Printer KioskHeathFile :: "+machinestatus);
		new VedioConference().updateKioskHeathFile(machinestatus,"Receipt_Printer");
		
	}
}

public class PrintNormalSample {

	public void statusUpdateOccurred(StatusUpdateEvent e) {
		switch (e.getStatus()) {
		case POSPrinterConst.PTR_SUE_COVER_OK:
			System.out.println("PTR_SUE_COVER_OK");
			break;
		case POSPrinterConst.PTR_SUE_COVER_OPEN:
			System.out.println("PTR_SUE_COVER_OPEN");
			break;
		case POSPrinterConst.PTR_SUE_REC_EMPTY:
			System.out.println("PTR_SUE_REC_EMPTY");
			break;
		case POSPrinterConst.PTR_SUE_REC_NEAREMPTY:
			System.out.println("PTR_SUE_REC_NEAREMPTY");
			break;
		case POSPrinterConst.PTR_SUE_REC_PAPEROK:
			System.out.println("PTR_SUE_REC_PAPEROK");
			break;
		case JposConst.JPOS_SUE_POWER_OFF_OFFLINE:
			System.out.println("JPOS_SUE_POWER_OFF_OFFLINE");
			break;
		case JposConst.JPOS_SUE_POWER_ONLINE:
			System.out.println("JPOS_SUE_POWER_ONLINE");
			break;
		}
	}
}