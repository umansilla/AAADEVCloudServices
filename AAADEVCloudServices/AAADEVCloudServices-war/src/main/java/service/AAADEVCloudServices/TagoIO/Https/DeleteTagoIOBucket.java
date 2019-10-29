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

public class DeleteTagoIOBucket {
    private final String bucketID;
    private final Logger logger = Logger.getLogger(getClass());
    public DeleteTagoIOBucket(String bucketID) {
        this.bucketID = bucketID;
    }

    public void deleteBucket() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException {
    	HttpsCustom httpsCustom = new HttpsCustom();
    	JSONObject json = httpsCustom.makeDeleteRequestTagoIO(Constants.TAGOIO_DELETE_BUCKET + bucketID, "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
    	if(json.getBoolean("status") == true){
    		logger.fine(json.getString("result"));
    	}else{
    		logger.error(json);
    		throw new IOException("Error al borrar Bucket");
    	}
    }
}
