package service.AAADEVCloudServices.TagoIO.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import service.AAADEVCloudServices.TagoIO.Actions.CloseSession;
import service.AAADEVCloudServices.TagoIO.Actions.CreateMedicalService;
import service.AAADEVCloudServices.TagoIO.Actions.CreatePatient;
import service.AAADEVCloudServices.TagoIO.Actions.DeletePatient;
import service.AAADEVCloudServices.TagoIO.Bean.UsuarioTagoIO;
import service.AAADEVCloudServices.TagoIO.Dao.PacientesDao;
import service.AAADEVCloudServices.TagoIO.Dao.ServicioMedicoDao;
import service.AAADEVCloudServices.Util.PartToString;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.util.logger.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author umansilla
 */
@MultipartConfig
@WebServlet(name = "TagoIOController", urlPatterns = {"/TagoIO/Patient/Administration"})
public class TagoIOController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(getClass());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession userSession = (HttpSession) request.getSession();
            UsuarioTagoIO usuario = (UsuarioTagoIO) userSession.getAttribute("userActive");
            String go = request.getParameter("CreatePatient");
            if (usuario == null) {
                logger.info("Ususario = null");
                request.getRequestDispatcher("../../LogInTagoIO.html").forward(request, response);
            } else {

                if (go != null) {
                    if (go.equals("go")) {
                        ServicioMedicoDao dao = new ServicioMedicoDao();
                        request.setAttribute("ServicioMedico", dao.getAll());
                        request.setAttribute("Usuario", usuario);
                        request.getRequestDispatcher("../../WEB-INF/CreatePatient.jsp").forward(request, response);
                    }

                } else {
                    usuario.setPassword(null);
                    request.setAttribute("Usuario", usuario);
                    PacientesDao dao = new PacientesDao();
                    request.setAttribute("Pacientes", dao.getAll());
                    request.getRequestDispatcher("../../WEB-INF/TagoIO.jsp").forward(request, response);
                }

            }
        } catch (IOException | SQLException | ServletException e) {
            logger.error("Error: " + e.toString());
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        setAccessControlHeaders(response);
        try {
            String action = new PartToString().getStringValue(request.getPart("action"));
            switch (action) {
                case "CreatePatient":
                    CreatePatient accionPatient = new CreatePatient(request);
                    json = accionPatient.createPatient();
                    break;
                case "CreateMedicalService":
                    CreateMedicalService actionMedicalService = new CreateMedicalService(request);
                    json = actionMedicalService.createMedicalService();
                    break;
                case "CloseSession":
                    CloseSession actionCloseSession = new CloseSession(request);
                    json = actionCloseSession.closeSession();
                    break;
                case "GetPatients":
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    PacientesDao dao = new PacientesDao();
                    json = new JSONObject().put("Patients", new JSONArray(gson.toJson(dao.getAll())));
                    break;  
                case "DeletePatient":
                    DeletePatient deletePatient = new DeletePatient(request);
                    json = deletePatient.deletePatient();
                    break;
                default:
                	logger.error("Default");
                    throw new AssertionError();
            }
            out.println(json);
        } catch (IOException | ServletException | JSONException | SQLException | SSLUtilityException | NoAttributeFoundException | ServiceNotFoundException e) {
            logger.error("Error: " + e.toString());
            out.println(new JSONObject().put("status", "error"));
        }
    }

    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods",
                "GET, POST, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, Accept, X-Requested-With");
    }

}
