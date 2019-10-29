package service.AAADEVCloudServices.Google.TTS.Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;

import service.AAADEVCloudServices.Security.AES;
import service.AAADEVCloudServices.Util.AttributeStore;
import service.AAADEVCloudServices.Util.Constants;

/**
 *
 * @author umansilla
 */
@MultipartConfig
@WebServlet(name = "ExampleTTS", urlPatterns = {"/ExampleTTS"})
public class ExampleTTS extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		setAccessControlHeaders(response);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        AES aes = new AES();
        
        String api = null;
		try {
			api = aes.encrypt(AttributeStore.INSTANCE.getAttributeValue(Constants.API_KEY_GOOGLE_CLOUD_TTS));
		} catch (NoAttributeFoundException | ServiceNotFoundException e) {

		}
        String text = aes.encrypt(getStringValue(request.getPart("text")));
        String voice = aes.encrypt(getStringValue(request.getPart("voice")));
        String voiceName = aes.encrypt(getStringValue(request.getPart("voiceName")));
        
        out.println(new JSONObject()
            .put("apikey", api)
            .put("text", text)
            .put("voice", voice)
            .put("voiceName", voiceName));
        
    }

	private String getStringValue(final Part part) {
        BufferedReader bufferedReader = null;
        final StringBuilder stringBuilder = new StringBuilder();
        String line;
        final String partName = part.getName();
        try {
            final InputStream inputStream = part.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (final IOException e) {
            System.out.println("getStringValue - IOException while reading inputStream. Part name : " + partName);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (final IOException e) {
                    System.out.println("getStringValue - IOException while closing bufferedReader. Part name : " + partName);
                }
            }
        }
        return stringBuilder.toString();
    }
    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
    }
}
