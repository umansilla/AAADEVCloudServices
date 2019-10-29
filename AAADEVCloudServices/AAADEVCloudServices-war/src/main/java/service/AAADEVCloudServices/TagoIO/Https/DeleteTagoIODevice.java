package service.AAADEVCloudServices.TagoIO.Https;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.util.logger.Logger;

import service.AAADEVCloudServices.Util.AttributeStore;
import service.AAADEVCloudServices.Util.Constants;

public class DeleteTagoIODevice {
	 private final String deviceID;
	 private final Logger logger = Logger.getLogger(getClass());
	    public DeleteTagoIODevice(String deviceID) {
	        this.deviceID = deviceID;
	    }

	    public void deleteDevice() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException {
	    	HttpsCustom httpsCustom = new HttpsCustom();
	    	JSONObject json = httpsCustom.makeDeleteRequestTagoIO(Constants.TAGOIO_DELETE_DEVICE + deviceID, "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
	    	if(json.getBoolean("status") == true){
	    		logger.fine(json.getString("result"));
	    	}else{
	    		logger.error(json);
	    		throw new IOException("Error al borrar Bucket");
	    	}
	    }
}
