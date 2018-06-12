package webServicesRepository.utility;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
public class EncryptedDataSource extends DriverManagerDataSource {
	
	static final Logger logger = Logger.getLogger(EncryptedDataSource.class);

	@Override
	public String getPassword() {
		String password = super.getPassword();
		return decode(password);
	}

	@Override
	public String getUsername() {
		String username = super.getUsername();
		return decode(username);
	}

	@Override
	public String getUrl() {
		String url = super.getUrl();
		return decode(url);
	}

	private String decode(String decode) {
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			decode = new String(decoder.decodeBuffer(decode));
		} catch (IOException e) {
			logger.error("EncryptedDataSource, Exception in decode : "+e.getMessage());
			/*e.printStackTrace();*/
		}
		return decode;
	}
}