package service.AAADEVCloudServices.TagoIO.Actions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import service.AAADEVCloudServices.Security.XSSPrevent;
import service.AAADEVCloudServices.TagoIO.Bean.UsuarioTagoIO;
import service.AAADEVCloudServices.TagoIO.Https.GetFileAccess;
import service.AAADEVCloudServices.Util.PartToString;

import com.avaya.collaboration.util.logger.Logger;


/**
 *
 * @author umansilla
 */
public class VerifyLogInUser {

    private final HttpServletRequest request;
    private final Logger logger = Logger.getLogger(getClass());
    public VerifyLogInUser(HttpServletRequest request) {
        this.request = request;
    }

    public JSONObject verify() throws IOException, ServletException, JSONException {
        String emailString = new PartToString().getStringValue(request.getPart("Email"));
        String passString = new PartToString().getStringValue(request.getPart("Pass"));
        Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mat = pat.matcher(emailString);
        if (mat.find()) {
            if (validateUserAndPass(XSSPrevent.stripXSS(emailString), decryptText(passString, "secret"), request)) {
                return new JSONObject().put("status", "ok");
            } else {
                logger.error("El usuario no se ha encontrado dentro del archivo");
                return new JSONObject().put("status", "error");
            }
        } else {
            logger.error("El email no es un formato válido");
            return new JSONObject().put("status", "error");
        }
    }

    public Boolean validateUserAndPass(String email, String password, HttpServletRequest request) throws JSONException {
        String jsonData = new GetFileAccess().fileHttp();
        JSONArray jobj = new JSONArray(jsonData);

        for (int i = 0; i < jobj.length(); i++) {
            String userName = jobj.getJSONObject(i).getString("username");
            String passwordFile = jobj.getJSONObject(i).getString("password");
            if (userName.equals(email) && password.equals(passwordFile)) {
                String name = jobj.getJSONObject(i).has("name") ? jobj.getJSONObject(i).getString("name") : "";
                String hora = jobj.getJSONObject(i).has("hora") ? jobj.getJSONObject(i).getString("hora") : "";
                String fecha = jobj.getJSONObject(i).has("fecha") ? jobj.getJSONObject(i).getString("fecha") : "";
                String phone = jobj.getJSONObject(i).has("phone") ? jobj.getJSONObject(i).getString("phone") : "";
                String country = jobj.getJSONObject(i).has("country") ? jobj.getJSONObject(i).getString("country") : "";
                UsuarioTagoIO user = new UsuarioTagoIO(jobj.getJSONObject(i).getInt("id"), name, userName, fecha, hora, phone, country, "es");

                HttpSession userSession = (HttpSession) request.getSession();
                userSession.setMaxInactiveInterval(15 * 60);
                userSession.setAttribute("userActive", user);
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    public static String decryptText(String cipherText, String secret) {

        String decryptedText = null;
        byte[] cipherData = java.util.Base64.getDecoder().decode(cipherText);
        byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
            SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
            IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

            byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
            Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
            aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] decryptedData = aesCBC.doFinal(encrypted);
            decryptedText = new String(decryptedData, StandardCharsets.UTF_8);
            return decryptedText;
        } catch (Exception ex) {
            return decryptedText;
        }
    }

    public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

        int digestLength = md.getDigestLength();
        int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
        byte[] generatedData = new byte[requiredLength];
        int generatedLength = 0;

        try {
            md.reset();

            // Repeat process until sufficient data has been generated
            while (generatedLength < keyLength + ivLength) {

                // Digest data (last digest if available, password data, salt if available)
                if (generatedLength > 0) {
                    md.update(generatedData, generatedLength - digestLength, digestLength);
                }
                md.update(password);
                if (salt != null) {
                    md.update(salt, 0, 8);
                }
                md.digest(generatedData, generatedLength, digestLength);

                // additional rounds
                for (int i = 1; i < iterations; i++) {
                    md.update(generatedData, generatedLength, digestLength);
                    md.digest(generatedData, generatedLength, digestLength);
                }

                generatedLength += digestLength;
            }

            // Copy key and IV into separate byte arrays
            byte[][] result = new byte[2][];
            result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
            if (ivLength > 0) {
                result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);
            }

            return result;

        } catch (DigestException e) {
            throw new RuntimeException(e);

        } finally {
            // Clean out temporary data
            Arrays.fill(generatedData, (byte) 0);
        }
    }

}