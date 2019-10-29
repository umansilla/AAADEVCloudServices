package service.AAADEVCloudServices.IBM.TTS.Servlets;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;

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
import javax.xml.bind.DatatypeConverter;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
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

/**
 *
 * @author umansilla
 */
@MultipartConfig
@WebServlet(name = "EngagementDesignerIBM", urlPatterns = {"/IBM/TTS/EngagementDesignerIBM"})
public class EngagementDesignerIBM extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EngagementDesignerIBM.class);
	public static int filesize;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println(new JSONObject().put("EngagementDesignerIBM", "OK"));
	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		

		AES aes = new AES();
		String text = aes.decrypt(getStringValue(request.getPart("text")));
		String voice = aes.decrypt(getStringValue(request.getPart("voice")));
		
		try {
			makeIBMPost(text, voice);
			out.println(new JSONObject().put("status", "ok"));
		} catch (UnsupportedOperationException | SSLUtilityException | NoAttributeFoundException | ServiceNotFoundException e) {
			out.println(new JSONObject().put("status", "error"));
			logger.error("Error EngagementDesignerIBM: " + e.toString());
		}
		
    }
	
	private void makeIBMPost(String text, String voice) throws SSLUtilityException, NoAttributeFoundException, ServiceNotFoundException, UnsupportedOperationException, IOException{
		String user = AttributeStore.INSTANCE.getAttributeValue(Constants.IBM_TTS_USER_NAME);
		String password = AttributeStore.INSTANCE.getAttributeValue(Constants.IBM_TTS_PASSWORD);

		final SSLProtocolType protocolType = SSLProtocolType.TLSv1_2;
		final SSLContext sslContext = SSLUtilityFactory
				.createSSLContext(protocolType);
		final CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(user, password));

		final String URI = "https://stream.watsonplatform.net/text-to-speech/api/v1/synthesize?voice="
				+ voice;

		final HttpClient client = HttpClients.custom()
				.setSSLContext(sslContext)
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
		// final HttpClient clientTraductor = new DefaultHttpClient();

		final HttpPost postTTSpeech = new HttpPost(URI);
		postTTSpeech.addHeader("Accept", "audio/l16;rate=8000");
		postTTSpeech.addHeader("Content-Type", "application/json");

		final String authStringTTSpecch = user + ":" + password;
		final String authEncBytesTTSpeech = DatatypeConverter.printBase64Binary(authStringTTSpecch.getBytes());
		postTTSpeech.addHeader("Authorization", "Basic "+ authEncBytesTTSpeech);

		final String messageBodyTTSpeech = "{\"text\":\""+ text.toString().trim() + "\"}";
		final StringEntity conversationEntityTTSpeech = new StringEntity(
				messageBodyTTSpeech);
		postTTSpeech.setEntity(conversationEntityTTSpeech);

		final HttpResponse responseTTSpeech = client.execute(postTTSpeech);

		InputStream in = reWriteWaveHeader(responseTTSpeech.getEntity()
				.getContent());
		/*
		 * Determinar el path de almacenamiento
		 */
		String realPath = getApplcatonPath();
		String[] split = realPath.split("/");
		StringBuilder path = new StringBuilder();
		for (int k = 1; k < split.length - 1; k++) {
			path.append("/");
			path.append(split[k]);
		}
		
		
		OutputStream out = new FileOutputStream(path.toString() + "/EngagementDesignerIBMCloudTTS.wav");
		byte[] buffer = new byte[filesize + 8];
		while ((in.read(buffer)) > 0) {
			InputStream byteAudioStream = new ByteArrayInputStream(
					buffer);
			AudioFormat audioFormat = new AudioFormat(8000.0f, 16, 1,
					false, false);
			AudioInputStream audioInputStream = new AudioInputStream(
					byteAudioStream, audioFormat, buffer.length);
			if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.WAVE, audioInputStream)) {
				AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
			}
		}
		out.close();
		in.close();
	}
	
	private String getStringValue(final Part part) {
		BufferedReader bufferedReader = null;
		final StringBuilder stringBuilder = new StringBuilder();
		String line;
		final String partName = part.getName();
		try {
			final InputStream inputStream = part.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, StandardCharsets.ISO_8859_1));
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (final IOException e) {
			System.out
					.println("getStringValue - IOException while reading inputStream. Part name : "
							+ partName);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (final IOException e) {
					System.out
							.println("getStringValue - IOException while closing bufferedReader. Part name : "
									+ partName);
				}
			}
		}
		return stringBuilder.toString();
	}

	/*
	 * TextToSpeech Methods
	 */
	private static InputStream reWriteWaveHeader(InputStream is)
			throws IOException {
		byte[] audioBytes = toByteArray(is);
		filesize = audioBytes.length - 8;

		writeInt(filesize, audioBytes, 4);
		writeInt(filesize - 8, audioBytes, 74);

		return new ByteArrayInputStream(audioBytes);
	}
	
	private static byte[] toByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384]; // 4 kb

		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		return buffer.toByteArray();
	}
	
	private static void writeInt(int value, byte[] array, int offset) {
		for (int i = 0; i < 4; i++) {
			array[offset + i] = (byte) (value >>> (8 * i));
		}
	}
	
	public static String getApplcatonPath() {
		CodeSource codeSource = EngagementDesignerIBM.class.getProtectionDomain()
				.getCodeSource();
		File rootPath = null;
		try {
			rootPath = new File(codeSource.getLocation().toURI().getPath());
		} catch (URISyntaxException e) {
			return e.toString();
		}
		return rootPath.getParentFile().getPath();
	}// end of getApplcatonPath()
	
	private void setAccessControlHeaders(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods",
				"GET, POST, DELETE, PUT");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type, Accept, X-Requested-With");
	}
	
}
