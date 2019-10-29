package service.AAADEVCloudServices.TagoIO.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.avaya.collaboration.util.logger.Logger;

import service.AAADEVCloudServices.TagoIO.Bean.Paciente;
import service.AAADEVCloudServices.TagoIO.Interfaz.PacientesDaoInterface;
import service.AAADEVCloudServices.TagoIO.JDBC.BaseDatosPG;


/**
 *
 * @author umansilla
 */
public class PacientesDao implements PacientesDaoInterface {
	private final Logger logger = Logger.getLogger(getClass());
	
    @Override
    public List<Paciente> getAll() {
        Paciente paciente;
        List<Paciente> listPacientes = new ArrayList<>();
        try {
            //Código para acceder por JDB a registros  de categorias
            //de una Base de Datos
            BaseDatosPG base = new BaseDatosPG();
            String query = "SELECT * FROM pacientes;";
            PreparedStatement ps = base.getconnection().prepareCall(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                paciente = new Paciente(rs.getInt("idpaciente"),
                        rs.getString("nombrepaciente"),
                        rs.getString("direccion"),
                        rs.getString("fechadenacimiento"),
                        rs.getString("edad"),
                        rs.getString("sexo"),
                        rs.getString("nss"),
                        rs.getString("telefono"),
                        rs.getString("spaceid"),
                        rs.getString("deviceid"),
                        rs.getString("bucketid"),
                        rs.getString("responsable"),
                        rs.getString("dashboardid"),
                        rs.getString("tokenid"));
                listPacientes.add(paciente);
            }
            base.desconectarBD();
        } catch (SQLException ex) {
            logger.error("Error: " + ex.toString());
        }
        return listPacientes;
    }

    @Override
    public JSONObject post(Paciente paciente) throws JSONException {
        try {
            BaseDatosPG base = new BaseDatosPG();
            String sql = "INSERT INTO public.pacientes(nombrepaciente, direccion, fechadenacimiento, edad, sexo, nss, telefono, spaceid, deviceid, bucketid, responsable, dashboardid, tokenid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = base.getconnection().prepareStatement(sql);
            ps.setString(1, paciente.getNombrepaciente());
            ps.setString(2, paciente.getDireccion());
            ps.setString(3, paciente.getFechadenacimiento());
            ps.setString(4, paciente.getEdad());
            ps.setString(5, paciente.getSexo());
            ps.setString(6, paciente.getNss());
            ps.setString(7, paciente.getTelefono());
            ps.setString(8, paciente.getSpaceid());
            ps.setString(9, paciente.getDeviceid());
            ps.setString(10, paciente.getBucketid());
            ps.setString(11, paciente.getResponsable());
            ps.setString(12, paciente.getDashboardid());
            ps.setString(13, paciente.getTokenid());
            ps.executeUpdate();
            base.desconectarBD();

        } catch (SQLException ex) {
            logger.error("Error: " + ex.toString());
            return new JSONObject().put("status", "error");
        }
        return new JSONObject().put("status", "ok");
    }

    @Override
    public String put(Paciente paciente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Paciente getById(int idPaciente) throws SQLException {
        Paciente paciente = null;
        //Código para acceder por JDB a registros  de categorias
        //de una Base de Datos
        BaseDatosPG base = new BaseDatosPG();
        String query = "SELECT * FROM pacientes WHERE idpaciente = " + idPaciente + ";";
        PreparedStatement ps = base.getconnection().prepareCall(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            paciente = new Paciente(rs.getInt("idpaciente"),
                    rs.getString("nombrepaciente"),
                    rs.getString("direccion"),
                    rs.getString("fechadenacimiento"),
                    rs.getString("edad"),
                    rs.getString("sexo"),
                    rs.getString("nss"),
                    rs.getString("telefono"),
                    rs.getString("spaceid"),
                    rs.getString("deviceid"),
                    rs.getString("bucketid"),
                    rs.getString("responsable"),
                    rs.getString("dashboardid"),
                    rs.getString("tokenid"));
        }
        base.desconectarBD();
        return paciente;	
    }

    @Override
    public void delete(Paciente paciente) {
        BaseDatosPG base = new BaseDatosPG();
        try {
            String sql = "DELETE FROM pacientes WHERE idpaciente = ?";
            PreparedStatement ps = base.getconnection().prepareStatement(sql);
            ps.setInt(1, paciente.getIdpaciente());
            ps.executeQuery();
            base.desconectarBD();
        } catch (SQLException e) {
            base.desconectarBD();
            logger.info(e.toString());
        }
   }
    
    @Override
    public Paciente obtenerPacientePorDeviceToken(String deviceToken) throws SQLException {
        //Código para acceder por JDB a registros  de categorias
        //de una Base de Datos
        Paciente paciente = null;
        BaseDatosPG base = new BaseDatosPG();
        String query = "SELECT * FROM pacientes WHERE tokenid = ?;";
        PreparedStatement ps = base.getconnection().prepareCall(query);
        ps.setString(1, deviceToken);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            paciente = new Paciente(rs.getInt("idpaciente"),
                    rs.getString("nombrepaciente"),
                    rs.getString("direccion"),
                    rs.getString("fechadenacimiento"),
                    rs.getString("edad"),
                    rs.getString("sexo"),
                    rs.getString("nss"),
                    rs.getString("telefono"),
                    rs.getString("spaceid"),
                    rs.getString("deviceid"),
                    rs.getString("bucketid"),
                    rs.getString("responsable"),
                    rs.getString("dashboardid"),
                    rs.getString("tokenid"));
        }
        base.desconectarBD();

        return paciente;
    }

}
