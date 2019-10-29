package service.AAADEVCloudServices.TagoIO.Https;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import service.AAADEVCloudServices.Util.AttributeStore;
import service.AAADEVCloudServices.Util.Constants;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.ssl.util.SSLProtocolType;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.ssl.util.SSLUtilityFactory;

public class HttpsCustom {
	public JSONObject makePostRequestTagoIO(String URL, String body, String header, String token) throws SSLUtilityException, ClientProtocolException, IOException {

		final SSLProtocolType protocolTypeAssistant = SSLProtocolType.TLSv1_2;
		final SSLContext sslContextAssistant = SSLUtilityFactory
				.createSSLContext(protocolTypeAssistant);

		final String URI = URL;

		final HttpClient client = HttpClients.custom()
				.setSSLContext(sslContextAssistant)
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

		final HttpPost postMethod = new HttpPost(URI);
		postMethod.addHeader("Content-Type", "application/json");
		postMethod.addHeader(header, token);

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
		JSONObject json = new JSONObject(result.toString());

		return json;
	}
	
	public JSONObject makeGetRequestTagoIO(String URL, String header, String token) throws SSLUtilityException, ClientProtocolException, IOException{
		final SSLProtocolType protocolTypeAssistant = SSLProtocolType.TLSv1_2;
		final SSLContext sslContextAssistant = SSLUtilityFactory
				.createSSLContext(protocolTypeAssistant);

		final String URI = URL;

		final HttpClient client = HttpClients.custom()
				.setSSLContext(sslContextAssistant)
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

		final HttpGet getMethod = new HttpGet(URI);
		getMethod.addHeader("Content-Type", "application/json");
		getMethod.addHeader(header, token);
		final HttpResponse response = client.execute(getMethod);

		final BufferedReader inputStream = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		String line = "";
		final StringBuilder result = new StringBuilder();
		while ((line = inputStream.readLine()) != null) {
			result.append(line);
		}
		JSONObject json = new JSONObject(result.toString());

		return json;
	}
	
	
	public JSONObject makeDeleteRequestTagoIO(String URL, String header, String token) throws SSLUtilityException, ClientProtocolException, IOException{
		final SSLProtocolType protocolTypeAssistant = SSLProtocolType.TLSv1_2;
		final SSLContext sslContextAssistant = SSLUtilityFactory
				.createSSLContext(protocolTypeAssistant);

		final String URI = URL;

		final HttpClient client = HttpClients.custom()
				.setSSLContext(sslContextAssistant)
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

		final HttpDelete deleteMethod = new HttpDelete(URI);
		deleteMethod.addHeader("Content-Type", "application/json");
		deleteMethod.addHeader(header, token);
		final HttpResponse response = client.execute(deleteMethod);

		final BufferedReader inputStream = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		String line = "";
		final StringBuilder result = new StringBuilder();
		while ((line = inputStream.readLine()) != null) {
			result.append(line);
		}
		JSONObject json = new JSONObject(result.toString());

		return json;
	}
	
	public JSONObject makePutRequestTagoIO(String URL, String body, String header, String token)throws SSLUtilityException, ClientProtocolException, IOException {

		final SSLProtocolType protocolTypeAssistant = SSLProtocolType.TLSv1_2;
		final SSLContext sslContextAssistant = SSLUtilityFactory
				.createSSLContext(protocolTypeAssistant);

		final String URI = URL;

		final HttpClient client = HttpClients.custom()
				.setSSLContext(sslContextAssistant)
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

		final HttpPut putMethod = new HttpPut(URI);
		putMethod.addHeader("Content-Type", "application/json");
		putMethod.addHeader(header, token);

		final StringEntity ttsEntity = new StringEntity(body);

		putMethod.setEntity(ttsEntity);
		final HttpResponse response = client.execute(putMethod);

		final BufferedReader inputStream = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		String line = "";
		final StringBuilder result = new StringBuilder();
		while ((line = inputStream.readLine()) != null) {
			result.append(line);
		}
		JSONObject json = new JSONObject(result.toString());

		return json;
	}
	
	public JSONObject makeRequestDeleteAvayaSpace(String URL) throws SSLUtilityException, NoAttributeFoundException, ServiceNotFoundException, ClientProtocolException, IOException{
		final SSLProtocolType protocolTypeAssistant = SSLProtocolType.TLSv1_2;
		final SSLContext sslContextAssistant = SSLUtilityFactory
				.createSSLContext(protocolTypeAssistant);

		final String URI = URL;

		final HttpClient client = HttpClients.custom()
				.setSSLContext(sslContextAssistant)
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

		final HttpDelete deleteMethod = new HttpDelete(URI);
		
		deleteMethod.addHeader("Authorization", "jwt " + AttributeStore.INSTANCE.getAttributeValue(Constants.AVAYA_SPACES_TOKEN));
		deleteMethod.addHeader("Content-Type", "application/json");
		final HttpResponse response = client.execute(deleteMethod);
		String line = "";
		final StringBuilder result = new StringBuilder();
		final BufferedReader inputStream = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
		while ((line = inputStream.readLine()) != null) {
			result.append(line);
		}
		JSONObject json = new JSONObject(result.toString());

		return json;
	}
	
	
	public JSONObject makeRequestAvayaSpaces(String URL, String body) throws SSLUtilityException, ClientProtocolException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		final SSLProtocolType protocolTypeAssistant = SSLProtocolType.TLSv1_2;
		final SSLContext sslContextAssistant = SSLUtilityFactory
				.createSSLContext(protocolTypeAssistant);

		final String URI = URL;

		final HttpClient client = HttpClients.custom()
				.setSSLContext(sslContextAssistant)
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

		final HttpPost postMethod = new HttpPost(URI);
		
		postMethod.addHeader("Authorization", "jwt " +AttributeStore.INSTANCE.getAttributeValue(Constants.AVAYA_SPACES_TOKEN));
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
		JSONObject json = new JSONObject(result.toString());

		return json;
	}
}
