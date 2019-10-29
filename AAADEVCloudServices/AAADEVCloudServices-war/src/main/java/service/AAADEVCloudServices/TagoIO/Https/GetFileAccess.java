package service.AAADEVCloudServices.TagoIO.Https;

/**
 *
 * @author umansilla
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.avaya.collaboration.util.logger.Logger;

public class GetFileAccess {
	private final Logger logger = Logger.getLogger(getClass());

	public String fileHttp() {
		final String URI = "http://breeze2-132.collaboratory.avaya.com/services/AAADEVLOGGER/InputLogger/web/LogIn/Access.txt";
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			HttpGet getMethod = new HttpGet(URI);

			final HttpResponse response = client.execute(getMethod);

			final BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			final StringBuilder result = new StringBuilder();
			while ((line = inputStream.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (IOException e) {
			logger.error("Error GetFileAccess: " + e.toString());
			return null;
		}
	}
}
