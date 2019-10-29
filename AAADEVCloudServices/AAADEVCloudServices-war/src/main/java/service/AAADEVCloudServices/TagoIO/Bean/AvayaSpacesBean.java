package service.AAADEVCloudServices.TagoIO.Bean;

import java.io.Serializable;

public class AvayaSpacesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String avayaSpaceID;

	public AvayaSpacesBean() {
		super();
	}

	public AvayaSpacesBean(String avayaSpaceID) {
		super();
		this.avayaSpaceID = avayaSpaceID;
	}

	public String getAvayaSpaceID() {
		return avayaSpaceID;
	}

	public void setAvayaSpaceID(String avayaSpaceID) {
		this.avayaSpaceID = avayaSpaceID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
