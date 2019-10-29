package service.AAADEVCloudServices.TagoIO.Actions;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import service.AAADEVCloudServices.TagoIO.Bean.Paciente;
import service.AAADEVCloudServices.TagoIO.Dao.PacientesDao;
import service.AAADEVCloudServices.TagoIO.Https.DeleteAvayaSpace;
import service.AAADEVCloudServices.TagoIO.Https.DeleteTagoIOAction;
import service.AAADEVCloudServices.TagoIO.Https.DeleteTagoIOBucket;
import service.AAADEVCloudServices.TagoIO.Https.DeleteTagoIODashBoard;
import service.AAADEVCloudServices.TagoIO.Https.DeleteTagoIODevice;
import service.AAADEVCloudServices.Util.PartToString;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.util.logger.Logger;

public class DeletePatient {
	private final HttpServletRequest request;
	private final Logger logger = Logger.getLogger(getClass());
    public DeletePatient(HttpServletRequest request) {
        this.request = request;
    }
    public JSONObject deletePatient() throws IOException, ServletException, SQLException, SSLUtilityException, NoAttributeFoundException, ServiceNotFoundException {
        int idpatient = Integer.parseInt(new PartToString().getStringValue(request.getPart("idpatient")));
        logger.info("deletePatient");
        Paciente pacienteBean = new PacientesDao().getById(idpatient);
        new DeleteAvayaSpace(pacienteBean.getSpaceid()).deleteSpace();
        new DeleteTagoIOBucket(pacienteBean.getBucketid()).deleteBucket();
        new DeleteTagoIODevice(pacienteBean.getDeviceid()).deleteDevice();
        new DeleteTagoIOAction(pacienteBean.getNombrepaciente()).deletePatientAction();
        new DeleteTagoIODashBoard(pacienteBean.getDashboardid()).deleteDashBoard();
        new PacientesDao().delete(pacienteBean);
        return new JSONObject().put("status", "ok");

    }
}
