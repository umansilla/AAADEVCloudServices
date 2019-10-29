package service.AAADEVCloudServices.TagoIO.Actions;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import service.AAADEVCloudServices.Security.XSSPrevent;
import service.AAADEVCloudServices.TagoIO.Bean.AvayaSpacesBean;
import service.AAADEVCloudServices.TagoIO.Bean.Paciente;
import service.AAADEVCloudServices.TagoIO.Bean.ServicioMedico;
import service.AAADEVCloudServices.TagoIO.Bean.TagoIOBean;
import service.AAADEVCloudServices.TagoIO.Dao.PacientesDao;
import service.AAADEVCloudServices.TagoIO.Dao.ServicioMedicoDao;
import service.AAADEVCloudServices.TagoIO.Https.AvayaSpaces;
import service.AAADEVCloudServices.TagoIO.Https.TagoIO;
import service.AAADEVCloudServices.Util.PartToString;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.util.logger.Logger;

public class CreatePatient {
	private final Logger logger = Logger.getLogger(getClass());
    private final HttpServletRequest request;
    public CreatePatient(HttpServletRequest request) {
        this.request = request;
    }

	public JSONObject createPatient() throws IOException, ServletException, SQLException, SSLUtilityException, NoAttributeFoundException, ServiceNotFoundException{
		logger.info("createPatient");
		String patientName = XSSPrevent.stripXSS(new PartToString().getStringValue(request.getPart("nombre")));
        String patientAddress = XSSPrevent.stripXSS(new PartToString().getStringValue(request.getPart("address")));
        String patientBithDay = XSSPrevent.stripXSS(new PartToString().getStringValue(request.getPart("birthDay")));
        String patientAge = getAgePatient(patientBithDay);
        String patientGenre = XSSPrevent.stripXSS(new PartToString().getStringValue(request.getPart("genreValue")));

        String patientNSS = XSSPrevent.stripXSS(new PartToString().getStringValue(request.getPart("nss")));
        String patientPhone = XSSPrevent.stripXSS(new PartToString().getStringValue(request.getPart("phone")));

        String idDoctor = XSSPrevent.stripXSS(new PartToString().getStringValue(request.getPart("doctor")));
        String idNurse = XSSPrevent.stripXSS(new PartToString().getStringValue(request.getPart("nurse")));
        String idTechnicalService = XSSPrevent.stripXSS(new PartToString().getStringValue(request.getPart("technicalService")));
        ServicioMedicoDao dao = new ServicioMedicoDao();
        ServicioMedico doctorResponsableBean = dao.getResonsablePorId(Integer.parseInt(idDoctor));
        ServicioMedico nurseResponsableBean = dao.getResonsablePorId(Integer.parseInt(idNurse));
        ServicioMedico technicalServiceResponsableBean = dao.getResonsablePorId(Integer.parseInt(idTechnicalService));
        //Petición HTTPS AvayaSpaces, Recuperación de SpaceID
        AvayaSpacesBean avayaSpacesBean = new AvayaSpacesBean();
        avayaSpacesBean.setAvayaSpaceID(new AvayaSpaces(patientName, doctorResponsableBean, nurseResponsableBean, technicalServiceResponsableBean).createAvayaSpace());
        
        TagoIOBean tagoIOBean = new TagoIOBean();
        TagoIO tagoIO = new TagoIO(patientName);
        tagoIOBean = tagoIO.createDevice();
        tagoIO.insertData();
        tagoIO.createAction();
        tagoIO.createActionAvayaBot();
        tagoIO.createDashBoard(avayaSpacesBean.getAvayaSpaceID());
        tagoIO.createImageElectroCardiogram();
        tagoIO.createInputElectroCardiogram();
        tagoIO.createImageLight();
        tagoIO.createInputLight();
        tagoIO.createIconTemperature();
        tagoIO.createInputTemperature();
        tagoIO.createIconHumidiity();
        tagoIO.createInputHumidity();
        tagoIO.createAvayaBotButton();
        tagoIO.createTimeBreakWidget();
        tagoIO.insertData();
        
        PacientesDao daoPaciente = new PacientesDao();

        daoPaciente.post(new Paciente(patientName, patientAddress, patientBithDay, patientAge, patientGenre, patientNSS, patientPhone, avayaSpacesBean.getAvayaSpaceID(), tagoIOBean.getDeviceID(), tagoIOBean.getBucketID(), doctorResponsableBean.getCorreoelectronico(), tagoIOBean.getDashBoardID(), tagoIOBean.getTokenID()));
        return new JSONObject().put("status", "ok");
	}
	
    private String getAgePatient(String patientBithDay) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNac = LocalDate.parse(patientBithDay, fmt);
        LocalDate ahora = LocalDate.now();
        Period periodo = Period.between(fechaNac, ahora);;
        return Integer.toString(periodo.getYears());
    }
}
