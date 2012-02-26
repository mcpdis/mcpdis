/*
 * PatientList.java
 *
 * Created on March 12, 2006, 5:41 PM
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
public class PatientList extends List implements CommandListener{
    MIDlet caller;
    Displayable referer;
    FormulaValue formulaValue;
    int[] idLookup;
    
    public PatientList(MIDlet caller, Displayable referer, FormulaValue fv) {
        super("Select a Patient", List.IMPLICIT);
        this.caller = caller;
        this.referer = referer;
        this.formulaValue = fv;
        String formulaResult = formulaValue.getResult();
        populatePatientList();
        this.setCommandListener(this);
        this.addCommand(get_assignCommand());
        this.addCommand(get_viewCommand());
        this.addCommand(get_archiveCommand());
        this.addCommand(get_deleteCommand());
        this.addCommand(get_syncAllCommand());
        this.addCommand(get_backCommand());
    }
    
    private void populatePatientList() {
        Patient[] patients = new PatientDao().findAll();
        
        int numOfPatients = patients.length;
        idLookup = new int[numOfPatients];
        for (int i = 0; i < numOfPatients; i++) {
            idLookup[i] = patients[i].id;
            this.append(patients[i].getFullName(), null);
        }
    }
    
    public void commandAction(Command command, Displayable displayable) {
        if (command == assignCommand) {
            int patientId = idLookup[this.getSelectedIndex()];
            formulaValue.setPatientID(patientId);
            String formulaResult = formulaValue.getResult();
            String message;
            if (new FormulaDao().save(formulaValue)) {
                message = "Formula result saved successfully";
            } else {
                message = "Error in saving formula result";
            }
            Alert alert = new Alert("Saving Result", message,  
                null, AlertType.INFO);
            alert.setTimeout(Alert.FOREVER);
            Display.getDisplay(caller).setCurrent(alert, referer);
        } else if (command == backCommand) {
            Display.getDisplay(caller).setCurrent(referer);
        } else if (command == archiveCommand) {
            int patientId = idLookup[this.getSelectedIndex()];
            archivePatient(patientId);
        } else if (command == viewCommand) {
            int patientId = idLookup[this.getSelectedIndex()];
            PatientView patientView = new PatientView(caller, this, patientId);
            Display.getDisplay(caller).setCurrent(patientView);
        } else if (command == deleteCommand) {
            int patientid = idLookup[this.getSelectedIndex()];

            Alert alert = new Alert("Patient Deleted");
            alert.setType(AlertType.CONFIRMATION);
            Patient patient = new PatientDao().find(patientid);
            alert.setString("The patient records for " + patient.getFullName() + " was deleted successfully");
            alert.setTimeout(Alert.FOREVER);
            if (new PatientDao().delete(patientid)) {
                Display.getDisplay(caller).setCurrent(alert, referer);
            }
        } else if (command == syncAllCommand) {
            archiveAll();
        }
    }
    
    private void archiveAll() {
//        RemotePatientDao remotePatient = new RemotePatientDao(caller);
//        remotePatient.setOperation(RemotePatientDao.INSERT_OP);
        Patient[] allPatients = new PatientDao().findAll();
        
        for (int i = 0; i < allPatients.length; i++) {
            archivePatient(allPatients[i].id);
//            remotePatient.setPatient(allPatients[i]);
//            remotePatient.start();
        }
    }
    private void archivePatient(int patientId) {
        RemotePatientDao remotePatient = new RemotePatientDao(caller);
        remotePatient.setOperation(RemotePatientDao.INSERT_OP);
        remotePatient.setPatient(new PatientDao().find(patientId));
        remotePatient.start();

        FormulaValue[] fvs = new FormulaDao().findByPatientId(patientId);
        RemoteFormulaDao remoteFormula = new RemoteFormulaDao(caller);
        remoteFormula.setFormulaValues(fvs);
        remoteFormula.setOperation(RemoteFormulaDao.MASS_INSERT_OP);
        remoteFormula.start();
    }
    private Command get_assignCommand() {
        if (assignCommand == null) {
            assignCommand = new Command("Assign Result", Command.SCREEN, 1);
        }
        return assignCommand;
    }
    private Command get_viewCommand() {
        if (viewCommand == null) {
            viewCommand = new Command("View", Command.SCREEN, 2);
        }
        return viewCommand;
    }
    
    private Command get_backCommand() {
        if (backCommand == null) {
            backCommand = new Command("Back", Command.BACK, 2);
        }
        return backCommand;
    }
    
    private Command get_archiveCommand() {
        if (archiveCommand == null) {
            archiveCommand = new Command("Archive", Command.SCREEN, 1);
        }
        return archiveCommand;
    }
    
    private Command get_deleteCommand() {
        if (deleteCommand == null) {
            deleteCommand = new Command("Delete", Command.SCREEN, 3);
        }
        return deleteCommand;
    }

    private Command get_syncAllCommand() {
        if (syncAllCommand == null) {
            syncAllCommand = new Command("Archive All", Command.SCREEN, 4);
        }
        return syncAllCommand;
    }
    
    private Command syncAllCommand;
    private Command assignCommand;
    private Command viewCommand;
    private Command backCommand;
    private Command archiveCommand;
    private Command deleteCommand;
}
