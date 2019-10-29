package service.AAADEVCloudServices.TagoIO.Interfaz;


import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import service.AAADEVCloudServices.TagoIO.Bean.Paciente;

/**
 *
 * @author umansilla
 */
public interface PacientesDaoInterface {	
    public List<Paciente> getAll();
    public JSONObject post(Paciente paciente) throws JSONException;
    public String put(Paciente paciente);
    public Paciente getById(int paciente) throws SQLException;
    public void delete(Paciente paciente) throws SQLException;
    public Paciente obtenerPacientePorDeviceToken(String deviceToken) throws SQLException;
}
