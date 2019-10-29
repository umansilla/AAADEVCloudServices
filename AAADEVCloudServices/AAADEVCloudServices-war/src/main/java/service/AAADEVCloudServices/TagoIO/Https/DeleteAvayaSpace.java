package service.AAADEVCloudServices.TagoIO.Https;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.util.logger.Logger;

import service.AAADEVCloudServices.Util.Constants;

public class DeleteAvayaSpace {
	private final String idDasboard;
	private final Logger logger = Logger.getLogger(getClass());
    public DeleteAvayaSpace(String idDasboard) {
        this.idDasboard = idDasboard;
    }

    public void deleteSpace() throws ClientProtocolException, SSLUtilityException, NoAttributeFoundException, ServiceNotFoundException, IOException {
    	HttpsCustom httpsCustom = new HttpsCustom();
    	JSONObject json = httpsCustom.makeRequestDeleteAvayaSpace(Constants.AVAYA_SPACES_DELETE_SPACE + idDasboard);
    	logger.info(json);
    }
}
