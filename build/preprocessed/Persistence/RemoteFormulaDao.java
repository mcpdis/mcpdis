/*
 * RemoteFormulaDao.java
 *
 * Created on March 14, 2006, 10:13 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package Persistence;

import com.sun.midp.Configuration;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author user
 */
public class RemoteFormulaDao implements Runnable {
    
    /** Creates a new instance of RemotePatientDao */
    public static final String INSERT_OP = "insert";
    public static final String MASS_INSERT_OP = "mass_insert";
    public static final String SEARCH_OP = "search";
    
    private MIDlet MIDlet;
    private FormulaValue formulaValue;
    private String operation;
    private FormulaValue[] formulaValues;
    
    public RemoteFormulaDao(MIDlet midlet) {
        MIDlet = midlet;
    }
    
    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    public void setFormulaValue(FormulaValue formulaValue) {
        this.formulaValue = formulaValue;
    }
    
    public void setFormulaValues(FormulaValue[] formulaValues) {
        this.formulaValues = formulaValues;
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
            archiveFormulaValue(formulaValue);
        } else if (operation == MASS_INSERT_OP) {
            archiveFormulaValues(formulaValues);
        }
    }
    
    private boolean archiveFormulaValues(FormulaValue[] formulaValues) {
        int num = formulaValues.length;
        for (int i = 0; i < num; i++) {
            archiveFormulaValue(formulaValues[i]);
        }
        return true;
    }
    
    private boolean archiveFormulaValue(FormulaValue formulaValue) {
        try {
            String serialized = new FormulaDao().serialize(formulaValue);
            if (serialized == "") {
                return false;
            }
            String url = getFormulaUrl() + "?" + serialized;
            StreamConnection connection = (StreamConnection)Connector.open(url);
            InputStream in = connection.openInputStream();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String getFormulaUrl() {
        return RemoteHelper.getFormulaArchiveUrl();
    }
    
}
