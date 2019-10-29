package service.AAADEVCloudServices.Arduino;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import service.AAADEVCloudServices.Util.Constants;

import com.avaya.collaboration.util.logger.Logger;

/**
 *
 * @author umansilla
 */
@WebServlet(name = "ArduinoReadFile", urlPatterns = {"/ArduinoReadFile"})
public class ArduinoReadFile extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(getClass());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	setAccessControlHeaders(response);
        PrintWriter out = response.getWriter();
        try (FileReader reader = new FileReader(Constants.PATH_TO_WEB_APP + "/sample_txt_file_wifi.txt");
                BufferedReader br = new BufferedReader(reader)) {

            // read line by line
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
            	logger.info(line);
                sb.append(line);
                sb.append("\n");
            }
            out.println(sb.toString());
        } catch (IOException e) {
        	logger.error("Error: " + e.toString());
            out.println("IOException:" + e.toString());
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	logger.info("DoPost ArduinoReadFile");
        PrintWriter out = response.getWriter();
        try{
        	
            try (FileWriter fichero = new FileWriter(Constants.PATH_TO_WEB_APP + "/sample_txt_file_wifi.txt", false); PrintWriter pw = new PrintWriter(fichero)) {
                pw.println("05,"+request.getParameter("Led05"));
                pw.println("06,"+request.getParameter("Led06"));
                pw.println("07,"+request.getParameter("Led07"));
                pw.println("");  
            }
        }catch(IOException e){
            logger.error("Error: " + e.toString());
            out.println(new JSONObject().put("status", "error"));
        }
        out.println(new JSONObject().put("status", "ok"));
    }
    
    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods",
                "GET, POST, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, Accept, X-Requested-With");
    }

}
