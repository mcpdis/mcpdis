/*
 * FormulaValue.java
 *
 * Created on March 11, 2006, 2:08 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package Persistence;
import java.util.Date;
/**
 *
 * @author user
 */
public class FormulaValue {
    private String name;
    private String result;
    private int timeStamp;
    private int patientId;
    public int id;
    
    /** Creates a new instance of FormulaValue */
    public FormulaValue(String name, String result) {
        this.name = name;
        this.result = result;
        this.timeStamp = (int)(new Date().getTime() / 1000);
    }
    
    public void setPatientID(int id) {
        this.patientId = id;
    }
    
    public int getPatientID() {
        return this.patientId;
    }
    
    public String getName() {
        return name;
    }

    public String getResult() {
        return result;
    }
    
    public int getTimeStamp() {
        //return (int)(new Date().getTime() / 1000);
        return timeStamp;
    }
    
    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public String toString() {
        return getName() + "= " + getResult();
    }
}
