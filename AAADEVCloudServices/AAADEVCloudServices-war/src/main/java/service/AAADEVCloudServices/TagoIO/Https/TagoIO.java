package service.AAADEVCloudServices.TagoIO.Https;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;

import service.AAADEVCloudServices.TagoIO.Bean.TagoIOBean;
import service.AAADEVCloudServices.Util.AttributeStore;
import service.AAADEVCloudServices.Util.Constants;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.util.logger.Logger;

public class TagoIO {
	
	private final Logger logger = Logger.getLogger(getClass());
	private final String patientName;
	private TagoIOBean tagoIOBeanLocal;
	
	public TagoIO(String patientName) {
		super();
		this.patientName = patientName;
		this.tagoIOBeanLocal = new TagoIOBean();
	}

	public TagoIOBean createDevice() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("createDevice");
		HttpsCustom httpsCustom = new HttpsCustom();
		JSONObject json = httpsCustom.makePostRequestTagoIO(Constants.TAGOIO_CREATE_DEVICE, createDevicePayLoad().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
			tagoIOBeanLocal.setDeviceID(json.getJSONObject("result").getString("device_id"));
			tagoIOBeanLocal.setBucketID(json.getJSONObject("result").getString("bucket_id"));
			tagoIOBeanLocal.setTokenID(json.getJSONObject("result").getString("token"));
			return tagoIOBeanLocal;
		}else{
			logger.error(json);
			throw new IOException("Error al crear Device en TagoIO");
		}	
	}
	
	public void insertData() throws ClientProtocolException, SSLUtilityException, IOException{
		logger.info("insertData");
		HttpsCustom httpsCustom = new HttpsCustom();
		JSONObject json = httpsCustom.makePostRequestTagoIO(Constants.TAGOIO_SENDING_DATA, sendingDataPayLoad().toString(), "Device-Token", tagoIOBeanLocal.getTokenID());
		if(json.getBoolean("status") == true){
			logger.fine(json);
		}else{
			logger.error(json);
			throw new IOException("Error al insertar data en Device");
		}
	}
	
	public void createAction() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("createAction");
		HttpsCustom httpsCustom = new HttpsCustom();
		JSONObject json = httpsCustom.makePostRequestTagoIO(Constants.TAGOIO_CREATE_ACTION, createActionPayLoad(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
		}else{
			logger.error(json);
			throw new IOException("Error al crear Accion data en Device");
		}
	}
	
	public void createActionAvayaBot() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("createAction");
		HttpsCustom httpsCustom = new HttpsCustom();
		JSONObject json = httpsCustom.makePostRequestTagoIO(Constants.TAGOIO_CREATE_ACTION, createActionPayLoadAvayaBot(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
		}else{
			logger.error(json);
			throw new IOException("Error al crear Accion data en Device");
		}
	}
	
	public TagoIOBean createDashBoard(String avayaSpaceID) throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("createDashBoard");
		HttpsCustom httpsCustom = new HttpsCustom();
		JSONObject json = httpsCustom.makePostRequestTagoIO(Constants.TAGOIO_CREATE_DASHBOARD, createDashBoardPayLoad(avayaSpaceID), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
			tagoIOBeanLocal.setDashBoardID(json.getJSONObject("result").getString("dashboard"));
			return tagoIOBeanLocal;
		}else{
			logger.error(json);
			throw new IOException("Error al crear DashBoard data en Device");
		}
	}
	
	public void createImageElectroCardiogram() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("createImageElectroCardiogram");
		HttpsCustom httpsCustom = new HttpsCustom();
		String URL = Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID() + "/widget";
		JSONObject json = httpsCustom.makePostRequestTagoIO(URL, createImageElectrocardiogramPayLoad().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
			tagoIOBeanLocal.setImagecardioelectrogramID(json.getJSONObject("result").getString("widget"));
			JSONObject jsonPut = httpsCustom.makePutRequestTagoIO(Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID(), updateImageElectrocardiogramPayLoad().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
			if(jsonPut.getBoolean("status") == true){
				logger.fine(jsonPut);
			}else{
				logger.error(json);
				throw new IOException("Error al crear input Electro Cardiogram");
			}
		}else{
			logger.error(json);
			throw new IOException("Error al crear Imagen de Electro Cardiograma");
		}
	}
	
	public void createInputElectroCardiogram() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("createInputElectroCardiogram");
		HttpsCustom httpsCustom = new HttpsCustom();
		String URL = Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID() + "/widget";
		JSONObject json = httpsCustom.makePostRequestTagoIO(URL, createInputElectroCardiogramPayLoad().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
			tagoIOBeanLocal.setInputCardioelectrogramID(json.getJSONObject("result").getString("widget"));
			JSONObject jsonPut = httpsCustom.makePutRequestTagoIO(Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID(), updateInputElectrocardiogramPayLoad().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
			if(jsonPut.getBoolean("status") == true){
				logger.fine(jsonPut);
			}else{
				logger.error(json);
				throw new IOException("Error al crear input Electro Cardiogram");
			}
		}else{
			logger.error(json);
			throw new IOException("Error al crear Imagen de Electro Cardiograma");
		}
	}
	
	public void createImageLight() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("createImageLight");
		HttpsCustom httpsCustom = new HttpsCustom();
		String URL = Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID() + "/widget";
		JSONObject json = httpsCustom.makePostRequestTagoIO(URL, createImageLightPayLoad().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
			tagoIOBeanLocal.setImageLightID(json.getJSONObject("result").getString("widget"));
			JSONObject jsonPut = httpsCustom.makePutRequestTagoIO(Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID(), updateImageLightPayLoad().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
			if(jsonPut.getBoolean("status") == true){
				logger.fine(jsonPut);
			}else{
				logger.error(json);
				throw new IOException("Error al crear Imagen de luz");
			}
		}else{
			logger.error(json);
			throw new IOException("Error al crear Imagen de luz");
		}
	}
	
	
	public void createInputLight() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("createInputLight");
		HttpsCustom httpsCustom = new HttpsCustom();
		String URL = Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID() + "/widget";
		JSONObject json = httpsCustom.makePostRequestTagoIO(URL, createInputLightPayLoad().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
			tagoIOBeanLocal.setInputLightID(json.getJSONObject("result").getString("widget"));
			JSONObject jsonPut = httpsCustom.makePutRequestTagoIO(Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID(), updateInputLightPayLoad().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
			if(jsonPut.getBoolean("status") == true){
				logger.fine(jsonPut);
			}else{
				logger.error(json);
				throw new IOException("Error al crear Input de luz");
			}
		}else{
			logger.error(json);
			throw new IOException("Error al crear Input de luz");
		}
	}
	
	public void createIconTemperature() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("createTemperatureIcon");
		HttpsCustom httpsCustom = new HttpsCustom();
		String URL = Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID() + "/widget";
		JSONObject json = httpsCustom.makePostRequestTagoIO(URL, createTemperatureIconPayLoad(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
			tagoIOBeanLocal.setImageTemperatureID(json.getJSONObject("result").getString("widget"));
			JSONObject jsonPut = httpsCustom.makePutRequestTagoIO(Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID(), updateIconTemperaturPayLoadString().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
			if(jsonPut.getBoolean("status") == true){
				logger.fine(jsonPut);
			}else{
				logger.error(json);
				throw new IOException("Error al crear Icon Temperatura");
			}
		}else{
			logger.error(json);
			throw new IOException("Error al crear Icon Temperatura");
		}
	}
	
	public void createInputTemperature() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("createInputTemperature");
		HttpsCustom httpsCustom = new HttpsCustom();
		String URL = Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID() + "/widget";
		JSONObject json = httpsCustom.makePostRequestTagoIO(URL, createInputTemperaturePayLoad().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
			tagoIOBeanLocal.setInputTemperatureID(json.getJSONObject("result").getString("widget"));
			JSONObject jsonPut = httpsCustom.makePutRequestTagoIO(Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID(), updateInputTemperaturPayLoadString().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
			if(jsonPut.getBoolean("status") == true){
				logger.fine(jsonPut);
			}else{
				logger.error(json);
				throw new IOException("Error al crear Input Temperatura");
			}
		}else{
			logger.error(json);
			throw new IOException("Error al crear Input Temperatura");
		}
	}
	
	public void createIconHumidiity() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("createIconHumidiity");
		HttpsCustom httpsCustom = new HttpsCustom();
		String URL = Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID() + "/widget";
		JSONObject json = httpsCustom.makePostRequestTagoIO(URL, createIconHumidityPayLoad(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
			tagoIOBeanLocal.setImageHumidityID(json.getJSONObject("result").getString("widget"));
			JSONObject jsonPut = httpsCustom.makePutRequestTagoIO(Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID(), updateIconHumidityPayLoadString().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
			if(jsonPut.getBoolean("status") == true){
				logger.fine(jsonPut);
			}else{
				logger.error(json);
				throw new IOException("Error al crear Icon Humidity");
			}
		}else{
			logger.error(json);
			throw new IOException("Error al crear Icon Humidity");
		}
	}
	
	public void createInputHumidity() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("createInputHumidity");
		HttpsCustom httpsCustom = new HttpsCustom();
		String URL = Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID() + "/widget";
		JSONObject json =  httpsCustom.makePostRequestTagoIO(URL, createInputHumidityPayLoad().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
			tagoIOBeanLocal.setInputHumidityID(json.getJSONObject("result").getString("widget"));
			JSONObject jsonPut = httpsCustom.makePutRequestTagoIO(Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID(), updateInputHumidityPayLoadString().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
			if(jsonPut.getBoolean("status") == true){
				logger.fine(jsonPut);
			}else{
				logger.error(json);
				throw new IOException("Error al crear Input Humidity");
			}
		}else{
			logger.error(json);
			throw new IOException("Error al crear Input Humidity");
		}
	}
	
	private JSONObject updateInputHumidityPayLoadString(){
		JSONObject jsonPutPayload = new JSONObject();
        jsonPutPayload.put("id", tagoIOBeanLocal.getDashBoardID());
        jsonPutPayload.put("tags", new JSONArray());
        jsonPutPayload.put("label", "Patient " + patientName + " DashBoard");
        jsonPutPayload.put("setup", new JSONObject());
        jsonPutPayload.put("arrangement", new JSONArray()
                .put(new JSONObject()//Image Cardiogram
                        .put("widget_id", tagoIOBeanLocal.getImagecardioelectrogramID())
                        .put("x", 0)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 4)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()//Input HumidityIcon
                        .put("widget_id", tagoIOBeanLocal.getImageHumidityID())
                        .put("x", 0.6666666666666666)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 4)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()//Temperature Icon
                        .put("widget_id", tagoIOBeanLocal.getImageTemperatureID())
                        .put("x", 1.3333333333333333)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 4)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()//Light Image
                        .put("widget_id", tagoIOBeanLocal.getImageLightID())////////////////////OK
                        .put("x", 2)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 4)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()//input Cardio Gram
                        .put("widget_id", tagoIOBeanLocal.getInputCardioelectrogramID())
                        .put("x", 0)
                        .put("y", 8)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()//input Humidity
                        .put("widget_id", tagoIOBeanLocal.getInputHumidityID())
                        .put("x", 0.6666666666666666)
                        .put("y", 8)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject() // inout Temperature
                        .put("widget_id", tagoIOBeanLocal.getInputTemperatureID())
                        .put("x", 1.3333333333333333)
                        .put("y", 8)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject() // input light
                        .put("widget_id", tagoIOBeanLocal.getInputLightID())
                        .put("x", 2)
                        .put("y", 8)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740")));
        return jsonPutPayload;
	}
	
	public void createTimeBreakWidget() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("create Time Brake Widget");
		HttpsCustom httpsCustom = new HttpsCustom();
		String URL = Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID() + "/widget";
		JSONObject json =  httpsCustom.makePostRequestTagoIO(URL, createTimeBreakPayLoad().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
			tagoIOBeanLocal.setTimeBrakeID(json.getJSONObject("result").getString("widget"));
			JSONObject jsonPut = httpsCustom.makePutRequestTagoIO(Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID(), updateStyle().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
			if(jsonPut.getBoolean("status") == true){
				logger.fine(jsonPut);
			}else{
				logger.error(json);
				throw new IOException("Error al crear Input Humidity");
			}
		}else{
			logger.error(json);
			throw new IOException("Error al crear Input Humidity");
		}
	}
	
	public void createAvayaBotButton() throws ClientProtocolException, SSLUtilityException, IOException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("create Time Brake Widget");
		HttpsCustom httpsCustom = new HttpsCustom();
		String URL = Constants.TAGOIO_CREATE_DASHBOARD + "/" + tagoIOBeanLocal.getDashBoardID() + "/widget";
		JSONObject json =  httpsCustom.makePostRequestTagoIO(URL, createInitAvayaBotButton().toString(), "Account-Token", AttributeStore.INSTANCE.getAttributeValue(Constants.TAGOIO_ACCOUNT_TOKEN));
		if(json.getBoolean("status") == true){
			logger.fine(json);
			tagoIOBeanLocal.setAvayaBotButttonID(json.getJSONObject("result").getString("widget"));
		}else{
			logger.error(json);
			throw new IOException("Error al crear Input Humidity");
		}
	}
	
	private JSONObject updateStyle(){
		JSONObject jsonPutPayload = new JSONObject();
        jsonPutPayload.put("id", tagoIOBeanLocal.getDashBoardID());
        jsonPutPayload.put("tags", new JSONArray());
        jsonPutPayload.put("label", "Patient " + patientName + " DashBoard");
        jsonPutPayload.put("setup", new JSONObject());
        jsonPutPayload.put("arrangement", new JSONArray()
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImagecardioelectrogramID())
                        .put("x", 0)
                        .put("y", 0)
                        .put("width", 1.3333333333333333)
                        .put("height", 5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getInputCardioelectrogramID())
                        .put("x", 1.3333333333333333)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImageLightID())
                        .put("x", 2)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getInputLightID())
                        .put("x", 1.3333333333333333)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImageTemperatureID())
                        .put("x", 2.6666666666666665)
                        .put("y", 0)
                        .put("width", 1)
                        .put("height", 2)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getInputTemperatureID())
                        .put("x", 0)
                        .put("y", 3)
                        .put("width", 1)
                        .put("height", 3)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImageHumidityID())
                        .put("x", 1)
                        .put("y", 6)
                        .put("width", 1)
                        .put("height", 2)
                        .put("tab", "1567015162740"))
		        .put(new JSONObject()
				        .put("widget_id", tagoIOBeanLocal.getAvayaBotButttonID())
				        .put("x", 0)
				        .put("y", 11)
				        .put("width", 1)
				        .put("height", 1.5)
				        .put("tab", "1567015162740"))
		        .put(new JSONObject()
				        .put("widget_id", tagoIOBeanLocal.getTimeBrakeID())
				        .put("x", 0)
				        .put("y", 14)
				        .put("width", 1)
				        .put("height", 3.5)
				        .put("tab", "1567015162740")));
        return jsonPutPayload;
	}
	
	private JSONObject createInputHumidityPayLoad(){
		JSONObject jsonInputHumidity = new JSONObject();
        jsonInputHumidity.put("display", new JSONObject()
                .put("input_type", "control")
                .put("controls", new JSONArray()
                        .put(new JSONObject()
                                .put("name", "humidity")
                                .put("variable", "humidity")
                                .put("origin", tagoIOBeanLocal.getDeviceID())
                                .put("bucket", tagoIOBeanLocal.getBucketID())))
                .put("help", "")
                .put("watermark", false));
        jsonInputHumidity.put("label", "Humidity input");
        jsonInputHumidity.put("type", "input");
        jsonInputHumidity.put("data", new JSONArray()
                .put(new JSONObject()
                        .put("bucket", tagoIOBeanLocal.getBucketID())
                        .put("origin", tagoIOBeanLocal.getDeviceID())
                        .put("timezone", "America/Mexico_City")
                        .put("query", "last_value")
                        .put("variables", new JSONArray()
                                .put("humidity"))));
        return jsonInputHumidity;
	}
	
	private JSONObject updateIconHumidityPayLoadString(){
		JSONObject jsonPutPayload = new JSONObject();
        jsonPutPayload.put("id", tagoIOBeanLocal.getDashBoardID());
        jsonPutPayload.put("tags", new JSONArray());
        jsonPutPayload.put("label", "Patient " + patientName + " DashBoard");
        jsonPutPayload.put("setup", new JSONObject());
        jsonPutPayload.put("arrangement", new JSONArray()
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImagecardioelectrogramID())
                        .put("x", 0)
                        .put("y", 0)
                        .put("width", 1.3333333333333333)
                        .put("height", 5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getInputCardioelectrogramID())
                        .put("x", 1.3333333333333333)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImageLightID())
                        .put("x", 2)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getInputLightID())
                        .put("x", 1.3333333333333333)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImageTemperatureID())
                        .put("x", 2.6666666666666665)
                        .put("y", 0)
                        .put("width", 1)
                        .put("height", 2)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getInputTemperatureID())
                        .put("x", 0)
                        .put("y", 3)
                        .put("width", 1)
                        .put("height", 3)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImageHumidityID())
                        .put("x", 1)
                        .put("y", 6)
                        .put("width", 1)
                        .put("height", 2)
                        .put("tab", "1567015162740")));
        return jsonPutPayload;
	}
	
	private String createIconHumidityPayLoad(){
		return "{  \n"
                + "   \"label\":\"Humidity\",\n"
                + "   \"type\":\"icon\",\n"
                + "   \"display\":{  \n"
                + "      \"vars_labels\":{  \n"
                + "         \""+tagoIOBeanLocal.getDeviceID()+"humidity\":\"humidity\"\n"
                + "      },\n"
                + "      \"vars_format\":{  \n"
                + "         \""+tagoIOBeanLocal.getDeviceID()+"humidity\":{  \n"
                + "            \"0\":\"h\",\n"
                + "            \"1\":\"u\",\n"
                + "            \"2\":\"m\",\n"
                + "            \"3\":\"i\",\n"
                + "            \"4\":\"d\",\n"
                + "            \"5\":\"i\",\n"
                + "            \"6\":\"t\",\n"
                + "            \"7\":\"y\",\n"
                + "            \"decimals\":\"1\"\n"
                + "         }\n"
                + "      },\n"
                + "      \"layout\":{  \n"
                + "         \""+tagoIOBeanLocal.getDeviceID()+"humidity\":{  \n"
                + "            \"row\":0,\n"
                + "            \"column\":0\n"
                + "         }\n"
                + "      },\n"
                + "      \"conditions\":{  \n"
                + "         \""+tagoIOBeanLocal.getDeviceID()+"humidity\":[  \n"
                + "            {  \n"
                + "               \"condition\":\"*\",\n"
                + "               \"url\":\"https://dl.dropboxusercontent.com/s/r7kt1heorw9virb/humi.svg?dl=0\",\n"
                + "               \"color\":\"#0080ff\"\n"
                + "            }\n"
                + "         ]\n"
                + "      },\n"
                + "      \"numberformat\":\"n/a\",\n"
                + "      \"hide_variables\":false,\n"
                + "      \"hide_values\":false,\n"
                + "      \"show_unit\":true,\n"
                + "      \"help\":\"\",\n"
                + "      \"watermark\":false\n"
                + "   },\n"
                + "   \"data\":[  \n"
                + "      {  \n"
                + "         \"bucket\":\""+tagoIOBeanLocal.getBucketID()+"\",\n"
                + "         \"origin\":\""+tagoIOBeanLocal.getDeviceID()+"\",\n"
                + "         \"timezone\":\"America/Mexico_City\",\n"
                + "         \"query\":\"last_value\",\n"
                + "         \"variables\":[  \n"
                + "            \"humidity\"\n"
                + "         ]\n"
                + "      }\n"
                + "   ]\n"
                + "}";
	}
	
	private JSONObject updateInputTemperaturPayLoadString(){
		JSONObject jsonPutPayload = new JSONObject();
        jsonPutPayload.put("id", tagoIOBeanLocal.getDashBoardID());
        jsonPutPayload.put("tags", new JSONArray());
        jsonPutPayload.put("label", "Patient " + patientName + " DashBoard");
        jsonPutPayload.put("setup", new JSONObject());
        jsonPutPayload.put("arrangement", new JSONArray()
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImagecardioelectrogramID())
                        .put("x", 0)
                        .put("y", 0)
                        .put("width", 1.3333333333333333)
                        .put("height", 5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getInputCardioelectrogramID())
                        .put("x", 1.3333333333333333)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImageLightID())
                        .put("x", 2)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getInputLightID())
                        .put("x", 1.3333333333333333)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImageTemperatureID())
                        .put("x", 2.6666666666666665)
                        .put("y", 0)
                        .put("width", 1)
                        .put("height", 2)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getInputTemperatureID())
                        .put("x", 0)
                        .put("y", 3)
                        .put("width", 1)
                        .put("height", 3)
                        .put("tab", "1567015162740")));
        return jsonPutPayload;
	}
	
	private JSONObject createInputTemperaturePayLoad(){
		 JSONObject jsonInputTemperature = new JSONObject();
	        jsonInputTemperature.put("display", new JSONObject()
	                .put("input_type", "control")
	                .put("controls", new JSONArray()
	                        .put(new JSONObject()
	                                .put("name", "temperature")
	                                .put("variable", "temperature")
	                                .put("origin", ""+tagoIOBeanLocal.getDeviceID()+"")
	                                .put("bucket", ""+tagoIOBeanLocal.getBucketID()+"")))
	                .put("help", "")
	                .put("watermark", false));
	        jsonInputTemperature.put("label", "Temperature input");
	        jsonInputTemperature.put("type", "input");
	        jsonInputTemperature.put("data", new JSONArray()
	                .put(new JSONObject()
	                        .put("bucket", ""+tagoIOBeanLocal.getBucketID()+"")
	                        .put("origin", ""+tagoIOBeanLocal.getDeviceID()+"")
	                        .put("timezone", "America/Mexico_City")
	                        .put("query", "last_value")
	                        .put("variables", new JSONArray()
	                                .put("temperature"))));
	        return jsonInputTemperature;
	}
	
	private JSONObject updateIconTemperaturPayLoadString(){
		 JSONObject jsonPutPayload = new JSONObject();
	        jsonPutPayload.put("id", tagoIOBeanLocal.getDashBoardID());
	        jsonPutPayload.put("tags", new JSONArray());
	        jsonPutPayload.put("label", "Patient " + patientName + " DashBoard");
	        jsonPutPayload.put("setup", new JSONObject());
	        jsonPutPayload.put("arrangement", new JSONArray()
	                .put(new JSONObject()
	                        .put("widget_id", tagoIOBeanLocal.getImagecardioelectrogramID())
	                        .put("x", 0)
	                        .put("y", 0)
	                        .put("width", 1.3333333333333333)
	                        .put("height", 5)
	                        .put("tab", "1567015162740"))
	                .put(new JSONObject()
	                        .put("widget_id", tagoIOBeanLocal.getInputCardioelectrogramID())
	                        .put("x", 1.3333333333333333)
	                        .put("y", 0)
	                        .put("width", 0.6666666666666666)
	                        .put("height", 1.5)
	                        .put("tab", "1567015162740"))
	                .put(new JSONObject()
	                        .put("widget_id", tagoIOBeanLocal.getImageLightID())
	                        .put("x", 2)
	                        .put("y", 0)
	                        .put("width", 0.6666666666666666)
	                        .put("height", 1.5)
	                        .put("tab", "1567015162740"))
	                .put(new JSONObject()
	                        .put("widget_id", tagoIOBeanLocal.getInputLightID())
	                        .put("x", 1.3333333333333333)
	                        .put("y", 0)
	                        .put("width", 0.6666666666666666)
	                        .put("height", 1.5)
	                        .put("tab", "1567015162740"))
	                .put(new JSONObject()
	                        .put("widget_id", tagoIOBeanLocal.getImageTemperatureID())
	                        .put("x", 2.6666666666666665)
	                        .put("y", 0)
	                        .put("width", 1)
	                        .put("height", 2)
	                        .put("tab", "1567015162740")));
	        return jsonPutPayload;
	}
	
	private String createTemperatureIconPayLoad(){
        return "{  \n"
                + "   \"label\":\"Temperature\",\n"
                + "   \"type\":\"icon\",\n"
                + "   \"display\":{  \n"
                + "      \"vars_labels\":{  \n"
                + "         \"" + tagoIOBeanLocal.getDeviceID() + "temperature\":\"temperature\"\n"
                + "      },\n"
                + "      \"vars_format\":{  \n"
                + "         \"" + tagoIOBeanLocal.getDeviceID() + "temperature\":{  \n"
                + "            \"0\":\"t\",\n"
                + "            \"1\":\"e\",\n"
                + "            \"2\":\"m\",\n"
                + "            \"3\":\"p\",\n"
                + "            \"4\":\"e\",\n"
                + "            \"5\":\"r\",\n"
                + "            \"6\":\"a\",\n"
                + "            \"7\":\"t\",\n"
                + "            \"8\":\"u\",\n"
                + "            \"9\":\"r\",\n"
                + "            \"10\":\"e\",\n"
                + "            \"decimals\":\"1\"\n"
                + "         }\n"
                + "      },\n"
                + "      \"layout\":{  },\n"
                + "      \"conditions\":{  \n"
                + "         \"" + tagoIOBeanLocal.getDeviceID() + "temperature\":[  \n"
                + "            {  \n"
                + "               \"condition\":\"*\",\n"
                + "               \"url\":\"https://svg.internal.tago.io/thermometer.svg\",\n"
                + "               \"color\":\"#ff0000\"\n"
                + "            }\n"
                + "         ]\n"
                + "      },\n"
                + "      \"numberformat\":\"n/a\",\n"
                + "      \"hide_variables\":false,\n"
                + "      \"hide_values\":false,\n"
                + "      \"show_unit\":true,\n"
                + "      \"help\":\"\",\n"
                + "      \"watermark\":false\n"
                + "   },\n"
                + "   \"data\":[  \n"
                + "      {  \n"
                + "         \"bucket\":\"" + tagoIOBeanLocal.getBucketID() + "\",\n"
                + "         \"origin\":\"" + tagoIOBeanLocal.getDeviceID() + "\",\n"
                + "         \"timezone\":\"America/Mexico_City\",\n"
                + "         \"query\":\"last_value\",\n"
                + "         \"variables\":[  \n"
                + "            \"temperature\"\n"
                + "         ]\n"
                + "      }\n"
                + "   ]\n"
                + "}";
	}
	
	private JSONObject updateInputLightPayLoad(){
		 JSONObject jsonPutPayload = new JSONObject();
	        jsonPutPayload.put("id", tagoIOBeanLocal.getDashBoardID());
	        jsonPutPayload.put("tags", new JSONArray());
	        jsonPutPayload.put("label", "Patient " + patientName + " DashBoard");
	        jsonPutPayload.put("setup", new JSONObject());
	        jsonPutPayload.put("arrangement", new JSONArray()
	                .put(new JSONObject()
	                        .put("widget_id", tagoIOBeanLocal.getImagecardioelectrogramID())
	                        .put("x", 0)
	                        .put("y", 0)
	                        .put("width", 1.3333333333333333)
	                        .put("height", 5)
	                        .put("tab", "1567015162740"))
	                .put(new JSONObject()
	                        .put("widget_id", tagoIOBeanLocal.getInputCardioelectrogramID())
	                        .put("x", 1.3333333333333333)
	                        .put("y", 0)
	                        .put("width", 0.6666666666666666)
	                        .put("height", 1.5)
	                        .put("tab", "1567015162740"))
	                .put(new JSONObject()
	                        .put("widget_id", tagoIOBeanLocal.getImageLightID())
	                        .put("x", 2)
	                        .put("y", 0)
	                        .put("width", 0.6666666666666666)
	                        .put("height", 1.5)
	                        .put("tab", "1567015162740"))
	                .put(new JSONObject()
	                        .put("widget_id", tagoIOBeanLocal.getInputLightID())
	                        .put("x", 1.3333333333333333)
	                        .put("y", 0)
	                        .put("width", 0.6666666666666666)
	                        .put("height", 1.5)
	                        .put("tab", "1567015162740")));
	        return jsonPutPayload;
	}
	
	
	private JSONObject createInputLightPayLoad(){
		JSONObject jsonInputPayLoad = new JSONObject()
        .put("display", new JSONObject()
                .put("input_type", "control")
                .put("controls", new JSONArray()
                        .put(new JSONObject()
                                .put("name", "input")
                                .put("variable", "light")
                                .put("origin", tagoIOBeanLocal.getDeviceID())
                                .put("bucket", tagoIOBeanLocal.getBucketID())
                                .put("type", "switch")
                                .put("label_yes", "ON")
                                .put("label_no", "OFF")))
                .put("help", "")
                .put("watermark", false))
        .put("label", "Light input")
        .put("type", "input")
        .put("data", new JSONArray()
                .put(new JSONObject()
                        .put("bucket", tagoIOBeanLocal.getBucketID())
                        .put("origin", tagoIOBeanLocal.getDeviceID())
                        .put("timezone", "America/Mexico_City")
                        .put("query", "last_value")
                        .put("variables", new JSONArray()
                                .put("light"))));
        return jsonInputPayLoad;
	}
	
	
	
	private JSONObject updateImageLightPayLoad(){
		JSONObject jsonPutPayload = new JSONObject();
        jsonPutPayload.put("id", tagoIOBeanLocal.getDashBoardID());
        jsonPutPayload.put("tags", new JSONArray());
        jsonPutPayload.put("label", "Patient " + patientName + " DashBoard");
        jsonPutPayload.put("setup", new JSONObject());
        jsonPutPayload.put("arrangement", new JSONArray()
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImagecardioelectrogramID())
                        .put("x", 0)
                        .put("y", 0)
                        .put("width", 1.3333333333333333)
                        .put("height", 5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getInputCardioelectrogramID())
                        .put("x", 1.3333333333333333)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImageLightID())
                        .put("x", 2)
                        .put("y", 0)
                        .put("width", 0.6666666666666666)
                        .put("height", 1.5)
                        .put("tab", "1567015162740")));
        return jsonPutPayload;
	}
	
	private JSONObject createImageLightPayLoad(){
		 JSONObject json = new JSONObject();
	        json.put("label", "Light");
	        json.put("type", "image");
	        json.put("realtime", true);
	        json.put("display", new JSONObject()
	                .put("type_media", "conditional")
	                .put("type_image", "conditional")
	                .put("static_media", "")
	                .put("static_image", "")
	                .put("help", "")
	                .put("watermark", false)
	                .put("conditions", new JSONArray()
	                        .put(new JSONObject()
	                                .put("condition", "=")
	                                .put("media_url", "https://images-na.ssl-images-amazon.com/images/I/51DqemzcW6L._SX425_.jpg")
	                                .put("value", "true")
	                                .put("image", "https://images-na.ssl-images-amazon.com/images/I/51DqemzcW6L._SX425_.jpg"))
	                        .put(new JSONObject()
	                                .put("condition", "=")
	                                .put("value", "false")
	                                .put("static_media", "")
	                                .put("media_url", "https://http2.mlstatic.com/foco-led-con-sensor-de-luz-encendido-automatico-8w-60w-D_NQ_NP_911006-MLM26235613111_102017-F.jpg")
	                                .put("image", "https://http2.mlstatic.com/foco-led-con-sensor-de-luz-encendido-automatico-8w-60w-D_NQ_NP_911006-MLM26235613111_102017-F.jpg"))));
	        json.put("data", new JSONArray()
	                .put(new JSONObject()
	                        .put("bucket", tagoIOBeanLocal.getBucketID())
	                        .put("origin", tagoIOBeanLocal.getDeviceID())
	                        .put("timezone", "America/Mexico_City")
	                        .put("query", "last_value")
	                        .put("variables", new JSONArray()
	                                .put("light"))));
	        return json;
	}
	
	
	
	
	private JSONObject createInputElectroCardiogramPayLoad(){
        JSONObject jsonInputPayLoad = new JSONObject()
        .put("display", new JSONObject()
                .put("input_type", "control")
                .put("controls", new JSONArray()
                        .put(new JSONObject()
                                .put("name", "input")
                                .put("variable", "input")
                                .put("origin", tagoIOBeanLocal.getDeviceID())
                                .put("bucket", tagoIOBeanLocal.getBucketID())
                                .put("type", "switch")
                                .put("label_yes", "ON")
                                .put("label_no", "OFF")))
                .put("help", "")
                .put("watermark", false))
        .put("label", "ElectroCardiogram input")
        .put("type", "input")
        .put("data", new JSONArray()
                .put(new JSONObject()
                        .put("bucket", tagoIOBeanLocal.getBucketID())
                        .put("origin", tagoIOBeanLocal.getDeviceID())
                        .put("timezone", "America/Mexico_City")
                        .put("query", "last_value")
                        .put("variables", new JSONArray()
                                .put("input"))));
        return jsonInputPayLoad;
	}
	
	private JSONObject updateInputElectrocardiogramPayLoad(){
		JSONObject jsonPutPayload = new JSONObject();
        jsonPutPayload.put("id", tagoIOBeanLocal.getDashBoardID());
        jsonPutPayload.put("tags", new JSONArray());
        jsonPutPayload.put("label", "Patient " + patientName + " DashBoard");
        jsonPutPayload.put("setup", new JSONObject());
        jsonPutPayload.put("arrangement", new JSONArray()
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImagecardioelectrogramID())
                        .put("x", 0)
                        .put("y", 0)
                        .put("width", 1.3333333333333333)
                        .put("height", 5)
                        .put("tab", "1567015162740"))
                .put(new JSONObject()
                    .put("widget_id", tagoIOBeanLocal.getInputCardioelectrogramID())
                    .put("x", 0)
                    .put("y", 0)
                    .put("width", 1.3333333333333333)
                    .put("height", 5)
                    .put("tab", "1567015162740"))    
        		);
        return jsonPutPayload;
	}
	
	private JSONObject updateImageElectrocardiogramPayLoad(){
		JSONObject jsonPutPayload = new JSONObject();
        jsonPutPayload.put("id", tagoIOBeanLocal.getDashBoardID());
        jsonPutPayload.put("tags", new JSONArray());
        jsonPutPayload.put("label", "Patient " + patientName + " DashBoard");
        jsonPutPayload.put("setup", new JSONObject());
        jsonPutPayload.put("arrangement", new JSONArray()
                .put(new JSONObject()
                        .put("widget_id", tagoIOBeanLocal.getImagecardioelectrogramID())
                        .put("x", 0)
                        .put("y", 0)
                        .put("width", 1.3333333333333333)
                        .put("height", 5)
                        .put("tab", "1567015162740")));
        return jsonPutPayload;
	}
	
	private JSONObject createImageElectrocardiogramPayLoad(){
        JSONObject json = new JSONObject();
        json.put("label", "Electrocardiogram");
        json.put("type", "image");
        json.put("realtime", true);
        json.put("display", new JSONObject()
                .put("type_media", "conditional")
                .put("type_image", "conditional")
                .put("static_media", "")
                .put("static_image", "")
                .put("help", "")
                .put("watermark", false)
                .put("conditions", new JSONArray()
                        .put(new JSONObject()
                                .put("condition", "=")
                                .put("media_url", "https://i.pinimg.com/originals/d6/bb/e7/d6bbe7771086d4702b2668119ea35282.gif")
                                .put("value", "true")
                                .put("image", "https://i.pinimg.com/originals/d6/bb/e7/d6bbe7771086d4702b2668119ea35282.gif"))
                        .put(new JSONObject()
                                .put("condition", "=")
                                .put("value", "false")
                                .put("static_media", "")
                                .put("media_url", "https://media.giphy.com/media/WRWyfIWXpnoXgK2Pcj/giphy.gif")
                                .put("image", "https://media.giphy.com/media/WRWyfIWXpnoXgK2Pcj/giphy.gif"))));
        json.put("data", new JSONArray()
                .put(new JSONObject()
                        .put("bucket", tagoIOBeanLocal.getBucketID())
                        .put("origin", tagoIOBeanLocal.getDeviceID())
                        .put("timezone", "America/Mexico_City")
                        .put("query", "last_value")
                        .put("variables", new JSONArray()
                                .put("input"))));
        return json;
	}
	
	private String createDashBoardPayLoad(String avayaSpaceID){
		return "{  \n"
                + "   \"label\":\"Patient " + patientName + "\",\n"
                + "   \"visible\":true,\n"
                + "   \"tags\":[  \n"
                + "\n"
                + "   ],\n"
                + "   \"group_by\":[  \n"
                + "\n"
                + "   ],\n"
                + "   \"tabs\":[  \n"
                + "      {  \n"
                + "         \"key\":\"1567015162740\",\n"
                + "         \"value\":\"Patient Space\",\n"
                + "         \"link\":\"https://spaces.zang.io/spaces/"+avayaSpaceID+"\",\n"
                + "         \"type\":\"link\"\n"
                + "      }\n"
                + "   ],\n"
                + "   \"arrangement\":[  \n"
                + "\n"
                + "   ],\n"
                + "   \"icon\":{  \n"
                + "      \"url\":\"https://svg.internal.tago.io/ambulance.svg\"\n"
                + "   },\n"
                + "   \"background\":{  \n"
                + "      \"type\":\"image\",\n"
                + "      \"media\":\"medisec.ie/clipart/post/banner-medical-indemnity.jpg\"\n"
                + "   }\n"
                + "}";
	}
	
	private String createActionPayLoadAvayaBot(){
		return "{\"name\":\"Patient "+patientName+" Action\",\"active\":true,\"to\":\"\",\"subject\":\"\",\"message\":\"\",\"url\":\""+Constants.ENGAGEMENT_DESIGNER_GATEWAY_AVAYA_BOT +"\",\"type\":\"post\",\"script\":\"\",\"topic\":\"home/office/temperature\",\"payload\":\"{\\n      \\\"variable\\\": \\\"$VARIABLE$\\\",\\n      \\\"unit\\\": \\\"$UNIT$\\\",\\n      \\\"value\\\": \\\"$VALUE$\\\",\\n      \\\"time\\\": \\\"$TIME$\\\",\\n      \\\"location\\\": \\\"$LOCATION$\\\"\\n    }\",\"bucket\":\"\",\"lock\":false,\"tags\":[],\"when_set_bucket\":\""+tagoIOBeanLocal.getBucketID()+"\",\"when_set_origin\":\""+tagoIOBeanLocal.getDeviceID()+"\",\"when_set_variable\":\"avaya_bot\",\"when_set_condition\":\"=\",\"when_set_type\":\"boolean\",\"when_set_value\":\"false\",\"when_reset_bucket\":\"\",\"when_reset_origin\":\"\",\"when_reset_variable\":\"\",\"when_reset_condition\":\"\",\"when_reset_type\":\"\",\"when_reset_value\":\"\"}\r\n";
	}
	
	private String createActionPayLoad(){
		return "{\"name\":\"Patient "+patientName+" Action\",\"active\":true,\"to\":\"\",\"subject\":\"\",\"message\":\"\",\"url\":\""+Constants.ENGAGEMENT_DESIGNER_GATEWAY+"\",\"type\":\"post\",\"script\":\"\",\"topic\":\"home/office/temperature\",\"payload\":\"{\\n      \\\"variable\\\": \\\"$VARIABLE$\\\",\\n      \\\"unit\\\": \\\"$UNIT$\\\",\\n      \\\"value\\\": \\\"$VALUE$\\\",\\n      \\\"time\\\": \\\"$TIME$\\\",\\n      \\\"location\\\": \\\"$LOCATION$\\\"\\n    }\",\"bucket\":\"\",\"lock\":false,\"tags\":[],\"when_set_bucket\":\""+tagoIOBeanLocal.getBucketID()+"\",\"when_set_origin\":\""+tagoIOBeanLocal.getDeviceID()+"\",\"when_set_variable\":\"input\",\"when_set_condition\":\"=\",\"when_set_type\":\"boolean\",\"when_set_value\":\"false\",\"when_reset_bucket\":\"\",\"when_reset_origin\":\"\",\"when_reset_variable\":\"\",\"when_reset_condition\":\"\",\"when_reset_type\":\"\",\"when_reset_value\":\"\"}\r\n";
	}
	
	private JSONArray sendingDataPayLoad(){
		JSONArray jsonArrayPayLoad = new JSONArray();
		jsonArrayPayLoad.put(new JSONObject().put("variable", "input").put("value", true));
		jsonArrayPayLoad.put(new JSONObject().put("variable", "light").put("value", true));
		jsonArrayPayLoad.put(new JSONObject().put("variable", "temperature").put("value", 50));
		jsonArrayPayLoad.put(new JSONObject().put("variable", "humidity").put("value", 20));
		jsonArrayPayLoad.put(new JSONObject().put("variable", "timebreak").put("value", 0));
		jsonArrayPayLoad.put(new JSONObject().put("variable", "avaya_bot").put("value", true));
		return jsonArrayPayLoad;
	}
	
	private JSONObject createDevicePayLoad(){
		JSONObject jsonCreateDevicePayLoad = new JSONObject();
		jsonCreateDevicePayLoad.put("name", "Patient " + patientName);
		jsonCreateDevicePayLoad.put("description", "Device for patient " + patientName);
		jsonCreateDevicePayLoad.put("active", true);
		jsonCreateDevicePayLoad.put("visible", true);
		return jsonCreateDevicePayLoad;
	}
	
	
	private JSONObject createTimeBreakPayLoad(){
		JSONObject jsonTimeBreakPayLoad = new JSONObject();
		//DISPLAY
		jsonTimeBreakPayLoad.put("display", new JSONObject()
						.put("gauge_type", "dial")
						.put("unit_type", "fixed")
						.put("vars_labels", new JSONObject()
							.put(tagoIOBeanLocal.getDeviceID()+"timebreak", "timebreak"))
						.put("vars_formula", new JSONObject()
							.put(tagoIOBeanLocal.getDeviceID()+"timebreak", ""))
						.put("vars_format", new JSONObject())
						.put("minimum", "0")
						.put("maximum", 15)
						.put("unit", "Minutes")
						.put("numberformat", "n/a")
						.put("hide_variables", true)
						.put("help", "")
						.put("watermark", false));
		//LABEL
		jsonTimeBreakPayLoad.put("label", "Time To Break AVAYA Bot");
		//TYPE
		jsonTimeBreakPayLoad.put("type", "dial");
		//DATA
		jsonTimeBreakPayLoad.put("data", new JSONArray()
						.put(new JSONObject()
							.put("bucket", tagoIOBeanLocal.getBucketID())
							.put("origin", tagoIOBeanLocal.getDeviceID())
							.put("timezone", "America/Mexico_City")
							.put("query", "last_value")
							.put("variables", new JSONArray().put("timebreak"))));
		return jsonTimeBreakPayLoad;
	}
	
	private JSONObject createInitAvayaBotButton(){
		JSONObject initAvayaBotButton = new JSONObject();
		//LABEL
		initAvayaBotButton.put("label", "AVAYA BOT Status");
		//TYPE
		initAvayaBotButton.put("type", "push_button");
		//ANALYSIS RUN
		initAvayaBotButton.put("analysis_run", "");
		//DISPLAY
		initAvayaBotButton.put("display", new JSONObject()
								.put("vars_labels", new JSONObject()
										.put(tagoIOBeanLocal.getDeviceID()+"avaya_bot", "avaya_bot"))
								.put("hide_variables", true)
								.put("help", "")
								.put("state_one", new JSONObject()
										.put("type", "icon")
										.put("label", "Started")
										.put("button_color", "hsl(236, 99%, 51%)")
										.put("text_color", "hsl(215, 0%, 100%)")
										.put("url", "https://svg.internal.tago.io/power-button-off.svg")
										.put("value", "true"))
								.put("state_two", new JSONObject()
										.put("type", "icon")
										.put("button_color", "hsl(359, 100%, 51%)")
										.put("url", "https://svg.internal.tago.io/cross-mark-on-a-black-circle-background.svg")
										.put("text_color", "hsl(0, 0%, 100%)")
										.put("value", "false"))
								.put("button_type", "bi-stable")
								.put("override_color", true));
		//DATA
		initAvayaBotButton.put("data", new JSONArray()
								.put(new JSONObject()
									.put("bucket", tagoIOBeanLocal.getBucketID())
									.put("origin", tagoIOBeanLocal.getTokenID())
									.put("timezone", "America/Mexico_City")
									.put("query", "last_value")
									.put("variables", new JSONArray().put("avaya_bot"))));
		return initAvayaBotButton;
	}
}
