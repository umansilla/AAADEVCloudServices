package service.AAADEVCloudServices.TagoIO.Actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.avaya.collaboration.util.logger.Logger;

/**
 *
 * @author umansilla
 */
public class CloseSession {
	private final HttpServletRequest request;
	private static final Logger logger = Logger.getLogger(CloseSession.class);

	public CloseSession(HttpServletRequest request) {
		this.request = request;
	}

	public JSONObject closeSession() {
		logger.info("CloseSession");
		HttpSession userSession = (HttpSession) request.getSession();
		userSession.removeAttribute("userActive");
		userSession.removeAttribute("Usuario");
		userSession.removeAttribute("Pacientes");
		userSession.removeAttribute("ServicioMedico");

		return new JSONObject().put("status", "ok");
	}
}
