package controller;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.RepaintManager;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


public class PrintPage implements Printable {
	
	static final Logger logger = Logger.getLogger(PrintPage.class);
 
    private Component print_component;
 
    public static void main(String args[]) {
    	System.out.print(new PrintPage().postClient());
    }
 
    public void doPrint() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);
        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (PrinterException pe) {
            	logger.debug("PrintPage, Error printing: " + pe.getMessage());
                //System.out.println("Error printing: " + pe);
            }
        }
    }
 
    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            return (NO_SUCH_PAGE);
        } else {
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            disableDoubleBuffering(print_component);
            print_component.paint(g2d);
            enableDoubleBuffering(print_component);
            return (PAGE_EXISTS);
        }
    }
 
    public static void disableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }
 
    public static void enableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }
    
    public String postClient() {
	String response=null;
		try {
			ClientConfig config = new DefaultClientConfig();
			com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create(config);
			String t1="{\r\n" + "\"username\":\"9413387309\",  \r\n" + 
			"\"amount\":533,\r\n" + 
			"\"orderId\":\"19267by\",\r\n" + 
			"\"externalRef2\":\"\",\r\n" + 
			"\"externalRef3\":\"\",\r\n" + 
			"\"customerMobile\":\"8107357705\"\r\n" + 
			"}\r\n" + 
			"";

			client.setFollowRedirects(true);
			
			WebResource resource = client
					.resource("http://localhost:9001/ezeapi/cardpayment");
			ClientResponse responseFrom = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,
					t1);
			response = responseFrom.getEntity(String.class);
			logger.debug("Response: " + responseFrom.getEntity(String.class));
			 //System.out.println(responseFrom.getEntity(String.class));

			return response;
		} catch (Exception e) {
			logger.error("PrintPage, Exception in postClient :"+e.getMessage());
			/*e.printStackTrace();*/
			return response;
		}
	}
}