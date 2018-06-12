package utils;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import utils.Constants.HTTP;

import com.fasterxml.jackson.databind.ObjectMapper;


import model.DBServiceResponse;
import model.QueryDetails;

@Component
public class DBHTTPUtils {

    @Value("${db.api.server.pull.url}")
    private String dbApiServerPullURL;

    @Value("${db.api.server.push.url}")
    private String dbApiServerPushURL;

    private static CloseableHttpClient httpClient;
    private static final ObjectMapper objMapper = new ObjectMapper();
    
    final static Logger logger = Logger.getLogger(DBHTTPUtils.class);

    @PostConstruct
    public void init() throws Exception {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(HTTP.MAX_OPEN_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(HTTP.MAX_CONCURRENT_CONNECTION_PER_ROUTE);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(HTTP.SOCKET_TIMEOUT)
                .setConnectTimeout(HTTP.CONNECTION_TIMEOUT).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager).build();
        //        This check is to RAJ NETWORK IP is giving response or not.
        try {
        	 pushPullDataFromDBApiServer(dbApiServerPullURL+Constants.FORWARD_SLASH,new QueryDetails());
        }
        //        In Case if RAJ NETWORK IP get fails it will switch to public IP.
        catch(ConnectTimeoutException e) {
        	logger.error("DBHTTPUtils, Exception in init : "+e.getMessage());

        }
        catch(Exception e) {
        	logger.error("DBHTTPUtils, Exception in init : "+e.getMessage());
        
        	//do nothing
        }
        if (!StringUtils.endsWith(dbApiServerPullURL, Constants.FORWARD_SLASH)) {
            dbApiServerPullURL += (Constants.FORWARD_SLASH);
        }
        
        if (!StringUtils.endsWith(dbApiServerPushURL, Constants.FORWARD_SLASH)) {
            dbApiServerPushURL += (Constants.FORWARD_SLASH);
        }
    }

    // We are intentionally not handling the exceptions here. The Caller must handle the exceptions and act accordingly.
    public DBServiceResponse pullDataFromDBApiServer(QueryDetails queryDetails) throws Exception {
        return pushPullDataFromDBApiServer(dbApiServerPullURL, queryDetails);
    }

    // We are intentionally not handling the exceptions here. The Caller must handle the exceptions and act accordingly.
    public DBServiceResponse pushDataToDBApiServer(QueryDetails queryDetails) throws Exception {
        return pushPullDataFromDBApiServer(dbApiServerPushURL, queryDetails);
    }

    private DBServiceResponse pushPullDataFromDBApiServer(String dbApiServerURL, QueryDetails queryDetails) throws Exception {
        HttpPost httpPost = new HttpPost(dbApiServerURL);
        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        httpPost.setEntity(new StringEntity(objMapper.writeValueAsString(queryDetails)));

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        DBServiceResponse dbServiceResponse = new DBServiceResponse(httpResponse);
        if (null != httpResponse) {
            try {
                httpResponse.close();
            } catch (IOException ioEx) {
                // NO-OP
            	
            	logger.error("DBHTTPUtils, Exception in pushPullDataFromDBApiServer : "+ioEx.getMessage());
            }
        }
        return dbServiceResponse;
    }
}