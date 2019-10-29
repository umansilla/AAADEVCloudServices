package service.AAADEVCloudServices.TagoIO.Https;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.util.logger.Logger;

import service.AAADEVCloudServices.Util.AttributeStore;
import service.AAADEVCloudServices.Util.Constants;

public class DeleteTagoIOAction {
	 private final String patientName;
	    private String actionID;
	    private final Logger logger = Logger.getLogger(getClass());
	    public DeleteTagoIOAction(String patientName) {
	        this.patientName = patientName;
	    }
	    
	    public void deletePatientAction() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
	    	HttpsCustom httpsCustom = new HttpsCustom();
	    	JSONObject json = httpsCustom.makeGetRequestTagoIO(Constants.TAGOIO_GET_ACTIONS, "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
	    	if (json.getBoolean("status") == true) {
	            JSONArray jsonArray = json.getJSONArray("result");
	            for (int i = 0; i < jsonArray.length(); i++) {
	                JSONObject jsonResults = jsonArray.getJSONObject(i);
	                if (jsonResults.getString("name").equals("Patient " + patientName + " Action")) {
	                    setActionID(jsonResults.getString("id"));
	                }
	            }
	        }
	    	
	    	JSONObject jsonResponse = httpsCustom.makeDeleteRequestTagoIO(Constants.TAGOIO_GET_ACTIONS + getActionID(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
	    	if(jsonResponse.getBoolean("status") == true){
	    		logger.fine(jsonResponse.getString("result"));
	    	}else{
	    		logger.error(json);
	    		throw new IOException("Error al borrar Bucket");
	    	}
	    }

		public String getActionID() {
			return actionID;
		}

		public void setActionID(String actionID) {
			this.actionID = actionID;
		}
	    
}
