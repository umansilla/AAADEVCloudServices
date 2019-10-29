package service.AAADEVCloudServices.TagoIO.Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import service.AAADEVCloudServices.Util.Constants;

import com.avaya.collaboration.ssl.util.SSLProtocolType;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.ssl.util.SSLUtilityFactory;
import com.avaya.collaboration.util.logger.Logger;

/**
 *
 * @author umansilla
 */
@WebServlet(name = "TagoIO", urlPatterns = {"/Admin/TagoIO"})
public class TagoIO extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TagoIO.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println(new JSONObject().put("status", "ok"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            JSONArray payloadJSON = new JSONArray(buffer.toString());
            initWorkFlow(payloadJSON);
            out.println(new JSONObject().put("status", "ok"));
        } catch (Exception e) {
            logger.error("Error: " + e.toString());
            out.println(new JSONObject().put("status", "error"));
        }
    }

    private void initWorkFlow(JSONArray payloadJSON) throws IOException, SSLUtilityException {
		final SSLProtocolType protocolType = SSLProtocolType.TLSv1_2;
		final SSLContext sslContext = SSLUtilityFactory
				.createSSLContext(protocolType);
		final HttpClient client = HttpClients.custom()
				.setSSLContext(sslContext)
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();  
        HttpPost postMethod = new HttpPost(Constants.ENGAGEMENT_DESIGNER_ENDPOINT);
        
        StringBody apiKeyBody = new StringBody(Constants.ENGAGEMENT_DESIGNER_FAMILY, ContentType.TEXT_PLAIN);
        StringBody textBody = new StringBody(Constants.ENGAGEMENT_DESIGNER_TYPE, ContentType.TEXT_PLAIN);
        StringBody voiceBody = new StringBody(Constants.ENGAGEMENT_DESIGNER_VERSION, ContentType.TEXT_PLAIN);
        StringBody voiceNameBody = new StringBody(payloadJSON.getJSONObject(0).toString(), ContentType.TEXT_PLAIN);

        reqEntity.addPart("family", apiKeyBody);
        reqEntity.addPart("type", textBody);
        reqEntity.addPart("version", voiceBody);
        reqEntity.addPart("eventBody", voiceNameBody);
        HttpEntity entity = reqEntity.build();

        postMethod.setEntity(entity);

        final HttpResponse response = client.execute(postMethod);

        final BufferedReader inputStream = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        final StringBuilder result = new StringBuilder();
        while ((line = inputStream.readLine()) != null) {
            result.append(line);
        }
        logger.info("Response: " + result.toString());

    }

}
