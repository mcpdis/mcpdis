/*
 * RemotePatientDao.java
 *
 * Created on March 14, 2006, 2:49 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package Persistence;

import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author user
 */
public class RemotePatientDao implements Runnable {
    
    /** Creates a new instance of RemotePatientDao */
    public static final String INSERT_OP = "insert";
    public static final String SEARCH_OP = "search";
    
    private MIDlet MIDlet;
    private Patient patient;
    private String operation;
    
    public RemotePatientDao(MIDlet midlet) {
        MIDlet = midlet;
    }
    
    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public void run() {
        try {
            transmit();
        } catch (Exception error) {
            System.err.println(error.toString());
        }
    }
    
    public void start() {
        Thread thread = new Thread(this);
        try {
            thread.start();
        } catch (Exception error) {
            
        }
    }
    
    private void transmit() {
        if (operation == INSERT_OP) {
            archivePatient(patient);
        }
    }
    
    public void archivePatient(Patient patient) {
        try {
            String url = getPatientUrl() + "?" + new PatientDao().serialize(patient);
            StreamConnection connection = (StreamConnection)Connector.open(url);
            InputStream in = connection.openInputStream();
            Alert alert = new Alert("Patient Archived", "The patient was archived successfully", null, null);
            alert.setTimeout(3000);
            Display.getDisplay(MIDlet).setCurrent(alert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void archiveByPatientId(int patientId) {
        Patient patient = new PatientDao().find(patientId);
        archivePatient(patient);
    }
    
    public String getPatientUrl() {
        return RemoteHelper.getPatientArchiveUrl();
    }
    
}
