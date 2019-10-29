package service.AAADEVCloudServices.TagoIO.Https;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.util.logger.Logger;

import service.AAADEVCloudServices.TagoIO.Bean.ServicioMedico;
import service.AAADEVCloudServices.Util.Constants;

public class AvayaSpaces {
	private final String patientName;
	private final ServicioMedico doctorResponsableBean;
	private final ServicioMedico nurseResponsableBean;
	private final ServicioMedico technicalServiceResponsableBean;
	private final Logger logger = Logger.getLogger(getClass());
	public AvayaSpaces(String patientName,
			ServicioMedico doctorResponsableBean,
			ServicioMedico nurseResponsableBean,
			ServicioMedico technicalServiceResponsableBean) {
		super();
		this.patientName = patientName;
		this.doctorResponsableBean = doctorResponsableBean;
		this.nurseResponsableBean = nurseResponsableBean;
		this.technicalServiceResponsableBean = technicalServiceResponsableBean;
	}

	public String createAvayaSpace() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		JSONObject json = new JSONObject();
		HttpsCustom htttpCustom = new HttpsCustom();
		json = htttpCustom.makeRequestAvayaSpaces(Constants.AVAYA_SPACES_CREATE_SPACE_END_POINT, jsonPayloadRequest().toString());
		String idSpace = null;
        if (json.has("data")) {
            JSONArray jsonArray = json.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                idSpace = jsonArray.getJSONObject(i).getString("topicId");
            }
            logger.fine(idSpace);
        } else {
        	logger.error("createAvayaSpace Error: " + json.toString());
            throw new IOException("Error al crear el espacio");
        }
        return idSpace;
	}
	
	private JSONObject jsonPayloadRequest(){
		JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("topic", new JSONObject()
                .put("id", "")
                .put("title", "Paciente " + patientName)
                .put("description", "Space created for the internal segmentation of the patient " + patientName)
                .put("type", "group"));
        jsonPayload.put("invitees", new JSONArray()
                .put(new JSONObject().put("inviteeType", "email").put("invitee", doctorResponsableBean.getCorreoelectronico()).put("role", "member"))
                .put(new JSONObject().put("inviteeType", "email").put("invitee", nurseResponsableBean.getCorreoelectronico()).put("role", "member"))
                .put(new JSONObject().put("inviteeType", "email").put("invitee", technicalServiceResponsableBean.getCorreoelectronico()).put("role", "member")));
        return jsonPayload;
	}
}
