package service.AAADEVCloudServices.TagoIO.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import service.AAADEVCloudServices.Util.Constants;

import com.avaya.collaboration.util.logger.Logger;

/**
 *
 * @author umansilla
 */
public class BaseDatosPG {
	private final Logger logger = Logger.getLogger(getClass());
    String urlDataBase = Constants.JDBC_URL;
    Connection conn = null;

    public BaseDatosPG(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            logger.error("Error: " + ex.toString());
        }
        try {
            conn = DriverManager.getConnection(urlDataBase, Constants.JDBC_USER, Constants.JDBC_PASSWORD);
        } catch (SQLException ex) {
           logger.error("Error: " + ex.toString());
        }
    }
    
    public Connection getconnection(){
        return this.conn;
    }

    public void desconectarBD() {
        logger.info("Cerrar conexión a Base de Datos");
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                logger.error("Error: " + ex.toString());
            }
        }
    }
}
