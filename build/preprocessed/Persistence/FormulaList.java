/*
 * FormulaList.java
 *
 * Created on March 13, 2006, 4:28 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package Persistence;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
/**
 *
 * @author user
 */
public class FormulaList extends List implements CommandListener {
    Patient patient;
    MIDlet caller;
    Displayable referer;
    public FormulaList(int patientId, MIDlet caller, Displayable referer) {
        super("", List.IMPLICIT);
        patient = new PatientDao().find(patientId);
        this.setTitle("List of Formulas for " + patient.getFullName());
        this.caller = caller;
        this.referer = referer;
        populateFormulaList();
        this.addCommand(get_backCommand());
        this.setCommandListener(this);
    }
    
    private void populateFormulaList() {
        FormulaValue[] results = new FormulaDao().findByPatientId(patient.id);
        
        int totalFormulas = results.length;
        
        for (int i = 0; i < totalFormulas; i++) {
            try {
                this.append(results[i].getName() + "  =  " + results[i].getResult(), null);
            } catch (Exception e) {
                
            }
        }
    }
    
    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            Display.getDisplay(caller).setCurrent(referer);
        }
    }
    
    private Command get_backCommand() {
        if (backCommand == null) {
            backCommand = new Command("Back", Command.BACK, 2);
        } 
        return backCommand;
    }
    
    private Command backCommand;
}
