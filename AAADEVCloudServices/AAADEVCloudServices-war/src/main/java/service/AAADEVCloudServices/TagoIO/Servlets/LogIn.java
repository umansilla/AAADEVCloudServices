package service.AAADEVCloudServices.TagoIO.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.avaya.collaboration.util.logger.Logger;

import service.AAADEVCloudServices.TagoIO.Actions.VerifyLogInUser;
import service.AAADEVCloudServices.Util.PartToString;

/**
 *
 * @author umansilla
 */
@MultipartConfig
@WebServlet(name = "LogIn", urlPatterns = {"/LogIn"})
public class LogIn extends HttpServlet {
	private final Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		logger.info("LogIn");
        try {
            PrintWriter out = response.getWriter();
            JSONObject json = new JSONObject();
            String actionString = new PartToString().getStringValue(request.getPart("action"));
            response.setContentType("application/json");
            switch (actionString) {
                case "LogIn":
                    VerifyLogInUser credentials = new VerifyLogInUser(request);
                    json = credentials.verify();
                    break;
                case "GetUser":
                    break;
            }
            out.println(json);
        } catch (JSONException ex) {
            logger.error("Error: " + ex.toString());
        }
    }
    
}
