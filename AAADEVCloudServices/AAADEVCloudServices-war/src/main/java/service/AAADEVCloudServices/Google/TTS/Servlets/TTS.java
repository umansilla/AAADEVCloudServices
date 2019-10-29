package service.AAADEVCloudServices.Google.TTS.Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import service.AAADEVCloudServices.Security.AES;
import service.AAADEVCloudServices.Util.AttributeStore;
import service.AAADEVCloudServices.Util.Constants;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.ssl.util.SSLProtocolType;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.ssl.util.SSLUtilityFactory;
import com.avaya.collaboration.util.logger.Logger;



@MultipartConfig
@WebServlet(name = "TTS", urlPatterns = {"/TTS"})
public class TTS extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TTS.class);
	

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		setAccessControlHeaders(response);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            String responseFrom = "{\n"
                    + "  \"audioContent\": \"UklGRvgvAABXQVZFZm10IBAAAAABAAEAQB8AAIA+AAACABAAZGF0YdQvAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP////////3//f/9//3//f/7//r/+//7//n/+f/5//r/+v/5//r/+v/5//r/+v/6//r/+v/7//z/+//7//z//v/+//7////+//7/AQACAAAA//8AAAIAAwAAAAEAAQAAAAEA/////wEABAADAAIAAQABAAIAAQAAAAAAAgADAAEAAgACAP7//f/+/////v/+//7//P8AAAIA///9/wAA///8////AAAEAAQAAgAFAAMABAAFAAUABgAHAAgABgAJAAgAAwAGAAUAAgAHAAYAAgAAAAIACAAIAAEA//8BAAEAAQADAAMAAgADAP//AAD+//r//v/+//z//f8AAAEA/v///wAA/f/7//7/AAD//wIAAAD//wAAAQD/////AwABAP//AgADAAMAAwABAAIAAgACAAYAAgADAAQAAwAIAAkABQAEAAQABQAHAAgABQABAP/////+//3/AQAEAP7/+f////z/+/8EAAMA/v/9/wMABQADAAAA/P8BAAUAAQAAAAIAAgAAAP//AAAAAAAA/////wAA/v/+//3//v////7/+v/8//7///8CAAAA/v///wAAAgD///3/AQABAP7/AQACAAEAAgADAAMA//8CAAMAAQABAAEAAwAAAAMAAgABAAUABQAEAAAAAAAEAAUAAwABAAQAAwABAAIAAQABAAYABgACAAEAAgAAAP7///8AAAEAAQD+//z/+v/+/wMAAwAAAP7/AAD7//v//v/9//z/+v/3//n//v///wAA/f/8/wIAAwABAAAA/v8AAAIA//8CAAUA/v/+//z//P8GAAEA/P///wAAAgASAOL/pQDdAvMBwwBTAwkFgALbAGkEBwUQAvkAFP+S/IL9xP4J/Xf8vf64/9n91vuJ/Fr9Gv1i/uv+s/68/g/+5P1L/Wn8NP3T/V797fwN/WT99P3Z/qr+6f5cANYAcQD3ALsCoAOTAxIEtwSgBX8GygXZBNkEHARoAs0Afv7D/MX8ZPvm+eP5bPcZ9eP03vNu8a73OApLEAsJYgh3DdkT6BUqDowH/AueD6AKiQKY/tICEAXq/cH2vfSl9FTyEu4N7PvoXOOr2VfU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Z/+L/6QA1QAGAOr+rf4mACABVQCV/7v/eACoAHH/aP7p/nkAZAHkAM3/I/+n/1IABgCL/9P/dADBAGwAkf9f/8j/EgBHAEoAEAAOACgA4P+//8f/5P9LAHYAOwDg/7r/4f/z/w8AWQBEAOP/8/8wABIA5P+q/6r/OwBnAAQA2//l/wMADADo/9r/tP/l/0kAEwDd//j/KQBRAD0A5v+E/2//vf9TAKwAcwD4/8D/2P/U/9H/EQAnABYAHwD3/7j/rP/f/0MAWAD5/8P/yf/K/+H/+/8SAC8ALQAPAP//5f/Z////EAANAA0AAgAOACoAMAAnABUAEwAfAOX/qP/f/zIAOQDi/4//wP8TAEwAUwDy/8v/5//C/9D/HAA3AE4ALQDq/+H/0f/8/zMAHwAZABEAAwAXAAoA2f/e/wQAOABDAAcA/f8TAP3/1f+2/9r/LgBPACwA4P+x/8T/5v8CABIA/f/1/w0AGAAEANr/0v/7/yMAHwABAOv/8f8MAB8AFwD5/+L/3/8DABoACQD2//L/CAAhAAsA1//H/9//BwANAPr/BQAYACAADADs/+b/9/8GAAIA9f/7/xcAHwAGAOb/3v/7/xkAEgD8//n/CQAUAAYA8//6/w8AEgABAO7/9f8MABAAAADr//H/BgALAP//9v/8/wgACAD2//L//f8KAA8ABQAEAAsAEwASAAIA+P/5/wMADwAKAAEAAQACAAQAAwD9//n//P///wAAAAAAAAAA+v/1//X/+f///wQAAQD8//r//P8AAAIA///7//j/+/8BAAIAAgD///3//v/+//r/+P/7/wEAAwADAP3/+f/8/wQADgAJAP7///8BAAAAAAACAAQABQAFAAEAAgAFAAMAAwADAAMAAgACAAIAAQABAAEAAQAAAAAAAAAAAP////////7//v/+//7//v/+/////v/9//7//v/+//7//v////7///////7/AAABAAAA//8BAAEAAAABAAAA//8AAAEAAgADAAEAAAACAAAA/v///wAAAwABAP3////////////+/////////////f/+//7///////7//////////////wAA//8AAAAAAAAAAAAAAAAAAAEAAAABAAEAAQABAAEAAQABAAEAAQABAAEAAQABAAEAAQABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\"\n"
                    + "}";
            out.println(new JSONObject(responseFrom));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setAccessControlHeaders(response);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        Part apiKeyPart = request.getPart("apiKey");
        Part textPart = request.getPart("text");
        Part voicePart = request.getPart("voice");
        Part voiceNamePart = request.getPart("voiceName");
        AES aes = new AES();
        
        String apiKey = aes.decrypt(getStringValue(apiKeyPart));
        String text = aes.decrypt(getStringValue(textPart));
        String voice = aes.decrypt(getStringValue(voicePart));
        String voiceName = aes.decrypt(getStringValue(voiceNamePart));
        
        
        try {
        	String route = AttributeStore.INSTANCE.getAttributeValue(Constants.ROUTE);
        	String responseGoogle = null;
        	if(route.equals("Breeze")){
        		responseGoogle = makeGoogleRequest(apiKey, createJsonPayLoadRequest(text, voice, voiceName));
        	}
        	if(route.equals("VPS")){
        		responseGoogle = makeVPSRequest(getStringValue(apiKeyPart), getStringValue(textPart), getStringValue(voicePart), getStringValue(voiceNamePart));
        	}
            
            out.println(responseGoogle);
        } catch (Exception ex) {
            out.println(new JSONObject().put("error", "error"));
            logger.error("Error : " + ex.toString());
        }

    }
    
    public String makeVPSRequest(String apiKey, String text, String voice,
			String voiceName) throws ClientProtocolException, IOException,
			NoAttributeFoundException, ServiceNotFoundException {

		final HttpClient client = HttpClients.createDefault();
		String vpsPostFQDN = AttributeStore.INSTANCE
				.getAttributeValue(Constants.VPS_FQDN);
		HttpPost postMethod = new HttpPost("http://" + vpsPostFQDN
				+ "/AAADEVURIEL_PRUEBAS_WATSON-war-1.0.0.0.0/TTS");
		MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
		StringBody apiKeyBody = new StringBody(apiKey, ContentType.TEXT_PLAIN);
		StringBody textBody = new StringBody(text, ContentType.TEXT_PLAIN);
		StringBody voiceBody = new StringBody(voice, ContentType.TEXT_PLAIN);
		StringBody voiceNameBody = new StringBody(voiceName, ContentType.TEXT_PLAIN);

    	reqEntity.addPart("apiKey", apiKeyBody);
    	reqEntity.addPart("text", textBody);
    	reqEntity.addPart("voice", voiceBody);
    	reqEntity.addPart("voiceName", voiceNameBody);
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

		return result.toString();
	}
    
    public String makeGoogleRequest(String apiKey, String body) throws SSLUtilityException, ClientProtocolException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		final SSLProtocolType protocolTypeAssistant = SSLProtocolType.TLSv1_2;
		final SSLContext sslContextAssistant = SSLUtilityFactory
				.createSSLContext(protocolTypeAssistant);
		apiKey = AttributeStore.INSTANCE.getAttributeValue(Constants.API_KEY_GOOGLE_CLOUD_TTS);
		final String URI = "https://texttospeech.googleapis.com/v1/text:synthesize?key="+apiKey;
		
		final HttpClient client = HttpClients.custom()
				.setSSLContext(sslContextAssistant)
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

		final HttpPost postMethod = new HttpPost(URI);
		postMethod.addHeader("Content-Type", "application/json");
		
		final StringEntity ttsEntity = new StringEntity(body);
		
		postMethod.setEntity(ttsEntity);
		final HttpResponse response = client.execute(postMethod);

		final BufferedReader inputStream = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		String line = "";
		final StringBuilder result = new StringBuilder();
		while ((line = inputStream.readLine()) != null) {
			result.append(line);
		}
		
    	return result.toString();
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
    
    private String createJsonPayLoadRequest(String text, String voice, String voiceName) {
        JSONObject json = new JSONObject();

        JSONObject jsonAudioConfig = new JSONObject()
                .put("audioEncoding", "LINEAR16")
                .put("sampleRateHertz", 8000)
                .put("effectsProfileId", new JSONArray().put("telephony-class-application"));
        JSONObject jsonInput = new JSONObject()
                .put("text", text);
        JSONObject jsonVoice = new JSONObject()
                .put("languageCode", voice)
                .put("name", voiceName);

        json.put("audioConfig", jsonAudioConfig);
        json.put("input", jsonInput);
        json.put("voice", jsonVoice);

        return json.toString();
    }
    
    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
    }
}
