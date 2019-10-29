package service.AAADEVCloudServices.TagoIO.Bean;

import java.io.Serializable;

/**
 *
 * @author umansilla
 */
public class ServicioMedico implements Serializable{

	private static final long serialVersionUID = 1L;
	private int idpersonalmedico;
    private String nombre;
    private String puesto;
    private String correoelectronico;

    public ServicioMedico() {
    }

    public ServicioMedico(int idpersonalmedico) {
        this.idpersonalmedico = idpersonalmedico;
    }    
    
    public ServicioMedico(int idpersonalmedico, String nombre, String puesto, String correoelectronico) {
        this.idpersonalmedico = idpersonalmedico;
        this.nombre = nombre;
        this.puesto = puesto;
        this.correoelectronico = correoelectronico;
    }

    public ServicioMedico(String nombre, String puesto, String correoelectronico) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.correoelectronico = correoelectronico;
    }

    public int getIdpersonalmedico() {
        return idpersonalmedico;
    }

    public void setIdpersonalmedico(int idpersonalmedico) {
        this.idpersonalmedico = idpersonalmedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getCorreoelectronico() {
        return correoelectronico;
    }

    public void setCorreoelectronico(String correoelectronico) {
        this.correoelectronico = correoelectronico;
    }
    
    
}
