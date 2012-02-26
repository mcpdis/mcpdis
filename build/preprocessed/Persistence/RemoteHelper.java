/*
 * RemoteHelper.java
 *
 * Created on March 14, 2006, 10:00 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package Persistence;

import com.sun.midp.Configuration;
import com.sun.midp.io.Base64;
import javax.microedition.midlet.MIDlet;


/**
 *
 * @author user
 */
public class RemoteHelper {
    private static MIDlet _midlet;
    /** Creates a new instance of RemoteHelper */
    public RemoteHelper() {
    }
    
    public static String urlEncode(String url) {
        byte[] urlInBytes = url.getBytes();
        return Base64.encode(urlInBytes, 0, urlInBytes.length);
    }
    
    public static String getUserEmail() {
        String email = _midlet.getAppProperty("UserEmail");
        return urlEncode(email);
    }
    
    public static String getHashcode() {
        String hashcode = _midlet.getAppProperty("HashCode");
        return urlEncode(hashcode);
    }

    public static void setCurrentMIDlet(MIDlet midlet) {
        _midlet = midlet;
    }
    
    public static String getFormulaArchiveUrl() {
        return _midlet.getAppProperty("FormulaArchiveUrl");
    }
    
    public static String getPatientArchiveUrl() {
        return _midlet.getAppProperty("PatientArchiveUrl");
    }
}
