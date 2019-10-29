package service.AAADEVCloudServices.TagoIO.Bean;

import java.io.Serializable;

public class TagoIOBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String deviceID;
	private String bucketID;
	private String tokenID;
	private String dashBoardID;
	private String imagecardioelectrogramID;
	private String inputCardioelectrogramID;
	private String imageLightID;
	private String inputLightID;
	private String imageTemperatureID;
	private String inputTemperatureID;
	private String imageHumidityID;
	private String inputHumidityID;
    private String timeBrakeID;
    private String avayaBotButttonID;
   

    public String getTimeBrakeID() {
		return timeBrakeID;
	}

	public void setTimeBrakeID(String timeBrakeID) {
		this.timeBrakeID = timeBrakeID;
	}

	public String getAvayaBotButttonID() {
		return avayaBotButttonID;
	}

	public void setAvayaBotButttonID(String avayaBotButttonID) {
		this.avayaBotButttonID = avayaBotButttonID;
	}

	public TagoIOBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getBucketID() {
		return bucketID;
	}

	public void setBucketID(String bucketID) {
		this.bucketID = bucketID;
	}

	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	public String getDashBoardID() {
		return dashBoardID;
	}

	public void setDashBoardID(String dashBoardID) {
		this.dashBoardID = dashBoardID;
	}

	public String getImagecardioelectrogramID() {
		return imagecardioelectrogramID;
	}

	public void setImagecardioelectrogramID(String imagecardioelectrogramID) {
		this.imagecardioelectrogramID = imagecardioelectrogramID;
	}

	public String getInputCardioelectrogramID() {
		return inputCardioelectrogramID;
	}

	public void setInputCardioelectrogramID(String inputCardioelectrogramID) {
		this.inputCardioelectrogramID = inputCardioelectrogramID;
	}

	public String getImageLightID() {
		return imageLightID;
	}

	public void setImageLightID(String imageLightID) {
		this.imageLightID = imageLightID;
	}

	public String getInputLightID() {
		return inputLightID;
	}

	public void setInputLightID(String inputLightID) {
		this.inputLightID = inputLightID;
	}

	public String getImageTemperatureID() {
		return imageTemperatureID;
	}

	public void setImageTemperatureID(String imageTemperatureID) {
		this.imageTemperatureID = imageTemperatureID;
	}

	public String getInputTemperatureID() {
		return inputTemperatureID;
	}

	public void setInputTemperatureID(String inputTemperatureID) {
		this.inputTemperatureID = inputTemperatureID;
	}

	public String getImageHumidityID() {
		return imageHumidityID;
	}

	public void setImageHumidityID(String imageHumidityID) {
		this.imageHumidityID = imageHumidityID;
	}

	public String getInputHumidityID() {
		return inputHumidityID;
	}

	public void setInputHumidityID(String inputHumidityID) {
		this.inputHumidityID = inputHumidityID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
