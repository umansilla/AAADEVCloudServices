package service.AAADEVCloudServices.TagoIO.Interfaz;


import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import service.AAADEVCloudServices.TagoIO.Bean.ServicioMedico;

/**
 *
 * @author umansilla
 */
public interface ServicioMedicoInterfaz {
      public List<ServicioMedico> getAll() throws SQLException;
    public JSONObject post(ServicioMedico servicioMedico) throws JSONException, SQLException;
}
