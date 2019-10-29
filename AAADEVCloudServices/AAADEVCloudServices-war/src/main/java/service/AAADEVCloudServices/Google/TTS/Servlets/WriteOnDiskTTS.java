package service.AAADEVCloudServices.Google.TTS.Servlets;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.security.CodeSource;

import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;
import javax.net.ssl.SSLContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

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
import service.AAADEVCloudServices.Util.Encoder;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.ssl.util.SSLProtocolType;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.ssl.util.SSLUtilityFactory;
import com.avaya.collaboration.util.logger.Logger;

@MultipartConfig
@WebServlet(name = "WriteOnDiskTTS", urlPatterns = {"/WriteOnDiskTTS"})
public class WriteOnDiskTTS extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(WriteOnDiskTTS.class);
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		/*
		 * Determinar el path de almacenamiento
		 */
		String realPath = getApplcatonPath();
		String [] split = realPath.split("/");
		StringBuilder path = new StringBuilder();
	       for(int k = 1 ; k < split.length - 1; k++){
	    	   path.append("/");
	    	   path.append(split[k]);
	       }
	     logger.info("path" + path.toString());
        final String contextPath = path.toString();
        final String filename = "PruebaDesdeWebService.wav";

        if (filename != null) {
            final File audioFile = new File(contextPath, filename);
            if (audioFile.exists()) {

                String base64 = Encoder.encoder(audioFile.getAbsolutePath());

                final PrintWriter out = response.getWriter();
                out.println(base64);

            } else {
                response.setStatus(404);
                response.sendError(404, "audio file does not exist");
            }

        }
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

            JSONObject json = new JSONObject(responseGoogle);
            if (json.has("audioContent")) {
        		/*
        		 * Determinar el path de almacenamiento
        		 */
        		String realPath = getApplcatonPath();
        		String [] split = realPath.split("/");
        		StringBuilder path = new StringBuilder();
        	       for(int k = 1 ; k < split.length - 1; k++){
        	    	   path.append("/");
        	    	   path.append(split[k]);
        	       }
        	     logger.info("path" + path.toString());
        	     
                String base64String = json.getString("audioContent");
                final FileOutputStream saveAudioFile = new FileOutputStream(path.toString() + "/PruebaDesdeWebService.wav");
                InputStream audioInput = new ByteArrayInputStream(base64String.getBytes());
                final byte audioBytes[] = base64String.getBytes("UTF-8");

                while ((audioInput.read(audioBytes)) != -1) {
                    InputStream byteAudioStream = new ByteArrayInputStream(decode(audioBytes));
                    final AudioFormat audioFormat = getAudioFormat();
                    AudioInputStream audioInputStream = new AudioInputStream(byteAudioStream, audioFormat, audioBytes.length);

                    if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.WAVE,
                            audioInputStream)) {
                        AudioSystem.write(audioInputStream,
                                AudioFileFormat.Type.WAVE, saveAudioFile);
                    }

                }
                audioInput.close();
                saveAudioFile.flush();
                saveAudioFile.close();
                out.println(new JSONObject().put("status", "ok"));
            } else {
                System.out.println("Error NO existe audioContent");
                out.println(new JSONObject().put("error", "sin Audio Content"));
            }
        } catch (Exception ex) {
            out.println(new JSONObject().put("error", "error"));
            System.out.println("Error : " + ex.toString());
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
			
			final StringEntity ttsEntity = new StringEntity(
					body);
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
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
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
	    

	    /*
		 * Avaya recommends that audio played by Avaya Aura MS be encoded as 16-bit,
		 * 8 kHz, single channel, PCM files. Codecs other than PCM or using higher
		 * sampling rates for higher quality recordings can also be used, however,
		 * with reduced system performance. Multiple channels, like stereo, are not
		 * supported.
		 * 
		 * @see Using Web Services on Avaya Aura Media Server Release 7.7, Issue 1,
		 * August 2015 on support.avaya.com
	     */
	    private static AudioFormat getAudioFormat() {
	        final float sampleRate = 8000.0F;
	        // 8000,11025,16000,22050,44100
	        final int sampleSizeInBits = 16;
	        // 8,16
	        final int channels = 1;
	        // 1,2
	        final boolean signed = true;
	        // true,false
	        final boolean bigEndian = false;
	        // true,false
	        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
	                bigEndian);
	    }
	    
	    public static String getApplcatonPath(){
	        CodeSource codeSource = WriteOnDiskTTS.class.getProtectionDomain().getCodeSource();
	        File rootPath = null;
	        try {
	            rootPath = new File(codeSource.getLocation().toURI().getPath());
	        } catch (URISyntaxException e) {
	          return e.toString();
	        }           
	        return rootPath.getParentFile().getPath();
	    }//end of getApplcatonPath()
	    
		public static byte[] decode(byte[] encodedAudioBytes)
				throws MessagingException, IOException {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					encodedAudioBytes);
			InputStream b64InputStream = MimeUtility.decode(byteArrayInputStream,
					"base64");

			byte[] tmpAudioBytes = new byte[encodedAudioBytes.length];
			int numberOfBytes = b64InputStream.read(tmpAudioBytes);
			byte[] decodedAudioBytes = new byte[numberOfBytes];

			System.arraycopy(tmpAudioBytes, 0, decodedAudioBytes, 0, numberOfBytes);

			return decodedAudioBytes;
		}
	    
	    private void setAccessControlHeaders(HttpServletResponse response) {
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
	    }

}
