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

public class DeleteTagoIODashBoard {
	  private final String dashboardID;
	  private final Logger logger = Logger.getLogger(getClass());
	    public DeleteTagoIODashBoard(String dashboardID) {
	        this.dashboardID = dashboardID;
	    }

	    public void deleteDashBoard() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
	    	HttpsCustom httpsCustom = new HttpsCustom();
	    	JSONObject json = httpsCustom.makeDeleteRequestTagoIO(Constants.TAGOIO_DELETE_DASHOARD + dashboardID, "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
	    	if(json.getBoolean("status") == true){
	    		logger.fine(json.getString("result"));
	    	}else{
	    		logger.error(json);
	    		throw new IOException("Error al borrar Bucket");
	    	}
	    }
}
