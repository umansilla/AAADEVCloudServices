package service.AAADEVCloudServices.TagoIO.Actions;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.avaya.collaboration.util.logger.Logger;

import service.AAADEVCloudServices.Security.XSSPrevent;
import service.AAADEVCloudServices.TagoIO.Bean.ServicioMedico;
import service.AAADEVCloudServices.TagoIO.Dao.ServicioMedicoDao;
import service.AAADEVCloudServices.Util.PartToString;

/**
 *
 * @author umansilla
 */
public class CreateMedicalService {

    private final HttpServletRequest request;
    private final Logger logger = Logger.getLogger(getClass());
    public CreateMedicalService(HttpServletRequest request) {
        this.request = request;
    }

    public JSONObject createMedicalService() throws IOException, ServletException, JSONException, SQLException {
    	logger.info("CreateMedicalService");
        String nombre = XSSPrevent.stripXSS(new PartToString().getStringValue(request.getPart("nombre")));
        String puesto = XSSPrevent.stripXSS(new PartToString().getStringValue(request.getPart("puesto")));
        Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        String correoelEctronico = XSSPrevent.stripXSS(new PartToString().getStringValue(request.getPart("correoelEctronico")));
        Matcher mat = pat.matcher(correoelEctronico);
        if (mat.find()) {
            ServicioMedicoDao dao = new ServicioMedicoDao();
            return dao.post(new ServicioMedico(nombre, puesto, correoelEctronico));
        } else {
            logger.error("El correo electrónico no tiene un formáto válido");
            throw new IOException("El correo electrónico no tiene un formáto válido");
        }

    }
}

