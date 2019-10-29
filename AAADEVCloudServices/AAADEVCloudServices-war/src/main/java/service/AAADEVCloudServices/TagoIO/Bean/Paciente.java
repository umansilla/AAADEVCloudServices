package service.AAADEVCloudServices.TagoIO.Bean;

import java.io.Serializable;

/**
 *
 * @author umansilla
 */
public class Paciente implements Serializable{

	private static final long serialVersionUID = 1L;
	private int idpaciente;
    private String nombrepaciente;
    private String direccion;
    private String fechadenacimiento;
    private String edad;
    private String sexo;
    private String nss;
    private String telefono;
    private String spaceid;
    private String deviceid;
    private String bucketid;
    private String responsable;
    private String dashboardid;
    private String tokenid;

	public Paciente() {}

    public Paciente(int idpaciente, String nombrepaciente, String direccion, String fechadenacimiento, String edad, String sexo, String nss, String telefono, String spaceid, String deviceid, String bucketid, String responsable, String dashboardid) {
        this.idpaciente = idpaciente;
        this.nombrepaciente = nombrepaciente;
        this.direccion = direccion;
        this.fechadenacimiento = fechadenacimiento;
        this.edad = edad;
        this.sexo = sexo;
        this.nss = nss;
        this.telefono = telefono;
        this.spaceid = spaceid;
        this.deviceid = deviceid;
        this.bucketid = bucketid;
        this.responsable = responsable;
        this.dashboardid = dashboardid;
    }

    
    
    public Paciente(int idpaciente, String nombrepaciente, String direccion,
			String fechadenacimiento, String edad, String sexo, String nss,
			String telefono, String spaceid, String deviceid, String bucketid,
			String responsable, String dashboardid, String tokenid) {
		this.idpaciente = idpaciente;
		this.nombrepaciente = nombrepaciente;
		this.direccion = direccion;
		this.fechadenacimiento = fechadenacimiento;
		this.edad = edad;
		this.sexo = sexo;
		this.nss = nss;
		this.telefono = telefono;
		this.spaceid = spaceid;
		this.deviceid = deviceid;
		this.bucketid = bucketid;
		this.responsable = responsable;
		this.dashboardid = dashboardid;
		this.tokenid = tokenid;
	}

	public Paciente(String nombrepaciente, String direccion, String fechadenacimiento, String edad, String sexo, String nss, String telefono, String spaceid, String deviceid, String bucketid, String responsable, String dashboardid, String tokenID) {
        this.nombrepaciente = nombrepaciente;
        this.direccion = direccion;
        this.fechadenacimiento = fechadenacimiento;
        this.edad = edad;
        this.sexo = sexo;
        this.nss = nss;
        this.telefono = telefono;
        this.spaceid = spaceid;
        this.deviceid = deviceid;
        this.bucketid = bucketid;
        this.responsable = responsable;
        this.dashboardid = dashboardid;
        this.tokenid = tokenID;
    }

    
    
    
    public String getDashboardid() {
        return dashboardid;
    }

    public void setDashboardid(String dashboardid) {
        this.dashboardid = dashboardid;
    }
    


    public int getIdpaciente() {
        return idpaciente;
    }

    public void setIdpaciente(int idpaciente) {
        this.idpaciente = idpaciente;
    }

    public String getNombrepaciente() {
        return nombrepaciente;
    }

    public void setNombrepaciente(String nombrepaciente) {
        this.nombrepaciente = nombrepaciente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechadenacimiento() {
        return fechadenacimiento;
    }

    public void setFechadenacimiento(String fechadenacimiento) {
        this.fechadenacimiento = fechadenacimiento;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSpaceid() {
        return spaceid;
    }

    public void setSpaceid(String spaceid) {
        this.spaceid = spaceid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getBucketid() {
        return bucketid;
    }

    public void setBucketid(String bucketid) {
        this.bucketid = bucketid;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

        public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }
    
}
