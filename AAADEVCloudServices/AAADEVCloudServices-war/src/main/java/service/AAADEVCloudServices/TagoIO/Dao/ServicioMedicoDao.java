package service.AAADEVCloudServices.TagoIO.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import service.AAADEVCloudServices.TagoIO.Bean.ServicioMedico;
import service.AAADEVCloudServices.TagoIO.Interfaz.ServicioMedicoInterfaz;
import service.AAADEVCloudServices.TagoIO.JDBC.BaseDatosPG;

/**
 *
 * @author umansilla
 */
public class ServicioMedicoDao implements ServicioMedicoInterfaz {

    @Override
    public List<ServicioMedico> getAll() throws SQLException {
        ServicioMedico servicioMedicoBean;
        List<ServicioMedico> listServicioMedico = new ArrayList<>();
        //Código para acceder por JDB a registros  de categorias
        //de una Base de Datos
        BaseDatosPG base = new BaseDatosPG();
        String query = "SELECT * FROM personalmedico;";
        PreparedStatement ps = base.getconnection().prepareCall(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            servicioMedicoBean = new ServicioMedico(rs.getInt("idpersonalmedico"), rs.getString("personal"), rs.getString("puesto"), rs.getString("correoelectronico"));
            listServicioMedico.add(servicioMedicoBean);
        }
        base.desconectarBD();
        return listServicioMedico;
    }

    @Override
    public JSONObject post(ServicioMedico servicioMedico) throws JSONException, SQLException {
        BaseDatosPG base = new BaseDatosPG();
        String sql = "INSERT INTO personalmedico(personal,puesto,correoelectronico)VALUES (?, ?, ?);";
        PreparedStatement ps = base.getconnection().prepareStatement(sql);
        ps.setString(1, servicioMedico.getNombre());
        ps.setString(2, servicioMedico.getPuesto());
        ps.setString(3, servicioMedico.getCorreoelectronico());
        ps.executeUpdate();
        base.desconectarBD();

        BaseDatosPG baseGetID = new BaseDatosPG();
        ServicioMedico servicioMedicoBean = null;
        String query = "SELECT idpersonalmedico FROM personalmedico WHERE personal = \'"+servicioMedico.getNombre()+"\' AND puesto = \'"+servicioMedico.getPuesto()+"\' AND correoelectronico = \'"+servicioMedico.getCorreoelectronico()+"\';";
        PreparedStatement psGetId = baseGetID.getconnection().prepareStatement(query);
        ResultSet rs = psGetId.executeQuery();
        while (rs.next()) {
            servicioMedicoBean = new ServicioMedico(rs.getInt("idpersonalmedico"));
        }
        baseGetID.desconectarBD();
        return new JSONObject().put("status", "ok").put("newId", servicioMedicoBean.getIdpersonalmedico());
    }
    
    public ServicioMedico getResonsablePorId(int id) throws SQLException{
         BaseDatosPG baseGetID = new BaseDatosPG();
        ServicioMedico servicioMedicoBean = null;
        String query = "SELECT personal, puesto, correoelectronico FROM personalmedico WHERE idpersonalmedico = "+id+";";
        PreparedStatement psGetId = baseGetID.getconnection().prepareStatement(query);
        ResultSet rs = psGetId.executeQuery();
        while (rs.next()) {
            servicioMedicoBean = new ServicioMedico(id, rs.getString("personal"), rs.getString("puesto"), rs.getString("correoelectronico"));
        }
        baseGetID.desconectarBD();
        return servicioMedicoBean;
    }
    
}
