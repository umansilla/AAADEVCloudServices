package service.AAADEVCloudServices.Util;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class Constants {
	public final static String SECRET_KEY = "AmericasInternationalPoCDevelopmentTeam";
	public final static String SALT = "MexicoTeam";
	// GOOGLE
	public final static String API_KEY_GOOGLE_CLOUD_TTS = "apiKey";
	public final static String ROUTE = "route";
	// VPS
	public final static String VPS_FQDN = "vps";
	// IBM
	public final static String IBM_TTS_USER_NAME = "IBMTTSUserName";
	public final static String IBM_TTS_PASSWORD = "IBMTTSPassword";
	// TAGO IO
	public final static String ENGAGEMENT_DESIGNER_ENDPOINT = "https://breeze2-132.collaboratory.avaya.com/services/EventingConnector/events";
	public final static String ENGAGEMENT_DESIGNER_GATEWAY = "http://breeze2-132.collaboratory.avaya.com/services/AAADEVCloudServices/Admin/TagoIO";
	public final static String ENGAGEMENT_DESIGNER_GATEWAY_AVAYA_BOT = "http://breeze2-132.collaboratory.avaya.com/services/AAADEVCloudServices/Admin/AvayaBot";
	public final static String ENGAGEMENT_DESIGNER_FAMILY = "AAADEVTagoIO";
	public final static String ENGAGEMENT_DESIGNER_TYPE = "AAADEVTagoIOType";
	public final static String ENGAGEMENT_DESIGNER_VERSION = "1.0";
	
	public final static String ENGAGEMENT_DESIGNER_FAMILY_ABSA = "AAADEVABSA";
	public final static String ENGAGEMENT_DESIGNER_TYPE_ABSA = "AAADEVABSAType";
	public final static String ENGAGEMENT_DESIGNER_VERSION_ABSA = "1.0";
	
	public final static String ENGAGEMENT_DESIGNER_FAMILY_AVAYA_BOT = "AAADEVAVAYABOTINI";
	public final static String ENGAGEMENT_DESIGNER_TYPE_AVAYA_BOT = "AAADEVAVAYABOTINITYPE";
	public final static String ENGAGEMENT_DESIGNER_VERSON_AVAYA_BOT = "1.0";
	
	
	public final static String TAGOIO_ACCOUNT_TOKEN = "TagoIOAccountToken";
	public final static String TAGOIO_CREATE_DEVICE = "https://api.tago.io/device";
	public final static String TAGOIO_SENDING_DATA = "https://api.tago.io/data";
	public final static String TAGOIO_CREATE_ACTION = "https://api.tago.io/action";
	public final static String TAGOIO_CREATE_DASHBOARD = "https://api.tago.io/dashboard";
	public final static String TAGOIO_DELETE_BUCKET = "https://api.tago.io/bucket/";
	public final static String TAGOIO_DELETE_DEVICE = "https://api.tago.io/device/";
	public final static String TAGOIO_DELETE_DASHOARD = "https://api.tago.io/dashboard/";
	public final static String TAGOIO_GET_ACTIONS = "https://api.tago.io/action/";
	
	//Avaya Spaces
	public final static String AVAYA_SPACES_TOKEN = "AvayaSpacesAccountToken";
	public final static String AVAYA_SPACES_CREATE_SPACE_END_POINT = "https://spacesapis.zang.io/api/spaces/invite";
	public final static String AVAYA_SPACES_DELETE_SPACE = "https://spacesapis.zang.io/api/spaces/";
	//JDBC
	public final static String JDBC_URL = "jdbc:postgresql://10.0.0.12:5432/postgres";
	public final static String JDBC_PASSWORD = "postgres";
	public final static String JDBC_USER = "postgres";
	//WEB APP
	public final static String PATH_TO_WEB_APP = getPathWebApp();
	private static String getPathWebApp() {
		String realPath = getApplcatonPath();
		String[] split = realPath.split("/");
		StringBuilder path = new StringBuilder();
		for (int k = 1; k < split.length - 1; k++) {
			path.append("/");
			path.append(split[k]);
		}
		return path.toString();
	}

	private static String getApplcatonPath() {
		CodeSource codeSource = Constants.class.getProtectionDomain()
				.getCodeSource();
		File rootPath = null;
		try {
			rootPath = new File(codeSource.getLocation().toURI().getPath());
		} catch (URISyntaxException e) {
			return e.toString();
		}
		return rootPath.getParentFile().getPath();
	}
	
}
