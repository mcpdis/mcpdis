/*
 * PatientForm.java
 *
 * Created on March 11, 2006, 12:45 PM
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
public class PatientForm implements CommandListener {
    
    /** Creates a new instance of PatientForm */
    public PatientForm() {
    }

    public static Form getForm(MIDlet caller, Displayable referer, int patientId) {
        PatientForm p = new PatientForm();
        p.setReferer(referer);
        p.setCaller(caller);
        p.setPatientId(patientId);
        
        if (patientId != -1) {
            Patient patient = new PatientDao().find(patientId);
            p.get_firstName().setString(patient.firstName);
            p.get_lastName().setString(patient.lastName);
            p.get_dateOfBirth().setDate(patient.dateOfBirth);
            if (patient.gender) {
                p.get_gender().setSelectedIndex(0, true);
            } else {
                p.get_gender().setSelectedIndex(1, true);
            }
        }
        Form form = new Form("Save Patient Info", new Item[] {
                p.get_firstName(),
                p.get_lastName(),
                p.get_dateOfBirth(),
                p.get_gender()
        });
        form.addCommand(p.get_backCommand());
        form.addCommand(p.get_saveCommand());
        
        if (new PatientDao().hasRecords()) { 
            form.addCommand(p.get_listCommand());
        }
        form.setCommandListener(p);
        return form;
        
    }
    
    public static Form getForm(MIDlet caller, Displayable referer, FormulaValue fv) {
        PatientForm p = new PatientForm();
        p.setReferer(referer);
        p.setCaller(caller);
        p.setFormulaValue(fv);
        p.setPatientId(-1);
        Form form = new Form("Save Patient Info", new Item[] {
                p.get_firstName(),
                p.get_lastName(),
                p.get_dateOfBirth(),
                p.get_gender()
        });
        form.addCommand(p.get_backCommand());
        form.addCommand(p.get_saveCommand());
        
        if (new PatientDao().hasRecords()) { 
            form.addCommand(p.get_listCommand());
        }
        form.setCommandListener(p);
        return form;
    }
    
    public Command get_backCommand() {
        if (backCommand == null) {
            backCommand = new Command("Back", Command.BACK, 2);
        } 
        return backCommand;
    }
    
    public Command get_saveCommand() {
        if (saveCommand == null) {
            saveCommand = new Command("Save Patient", Command.SCREEN, 1);
        }
        return saveCommand;
    }
    
    public Command get_listCommand() {
        if (listCommand == null) {
            listCommand = new Command("List Patients", Command.SCREEN, 2);
        }
        return listCommand;
    }
    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            Display.getDisplay(caller).setCurrent(referer);
        } else if (c == saveCommand) {
            Patient p = new Patient();
            p.id = patientId;
            p.firstName = get_firstName().getString();
            p.lastName = get_lastName().getString();
            p.gender = (get_gender().getSelectedIndex() == 0) ? true : false;
            p.dateOfBirth = get_dateOfBirth().getDate();
            Alert alert = new Alert("Saving Patient Data");
            int patientID = new PatientDao().save(p);
            if (patientID != -1) {
                if (formulaValue != null) {
                    formulaValue.setPatientID(patientID);
                    new FormulaDao().save(formulaValue);
                }
                alert.setString("The patient data was successfully saved.");
                alert.setTimeout(Alert.FOREVER);
            } else {
                alert.setString("The operation failed. Please try again later");
                alert.setTimeout(Alert.FOREVER);
            }
            if (referer instanceof PatientView) {
                Displayable refererOfPatientView = ((PatientView)referer).referer;
                referer = new PatientView(
                    caller, refererOfPatientView, patientID);
            }
            Display.getDisplay(caller).setCurrent(alert, referer);
        } else if (c == listCommand) {
            Display.getDisplay(caller).setCurrent(new PatientList(caller, referer, formulaValue));
        }
    }
    
    public void setCaller(javax.microedition.midlet.MIDlet caller) {
        this.caller = caller;
    }
    
    public void setReferer(Displayable referer) {
        this.referer = referer;
    }
    
    public void setFormulaValue(FormulaValue fv) {
        this.formulaValue = fv;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    
    public javax.microedition.lcdui.TextField get_firstName() {
        if (firstName == null) {
            firstName = new javax.microedition.lcdui.TextField("First Name", "", 100, 0x0);
        }
        return firstName;
    }

    public javax.microedition.lcdui.TextField get_lastName() {
        if (lastName == null) {
            lastName = new javax.microedition.lcdui.TextField("Last Name", "", 100, 0x0);
        }
        return lastName;
    }
    
    /**
     * This method returns instance for dateOfBirth component and should be called instead of accessing dateOfBirth field directly.
     * @return Instance for dateOfBirth component
     **/
    public javax.microedition.lcdui.DateField get_dateOfBirth() {
        if (dateOfBirth == null) {
            dateOfBirth = new javax.microedition.lcdui.DateField("Date of Birth", javax.microedition.lcdui.DateField.DATE);
        }
        return dateOfBirth;
    }
    
    /**
     * This method returns instance for gender component and should be called instead of accessing gender field directly.
     * @return Instance for gender component
     **/
    public javax.microedition.lcdui.ChoiceGroup get_gender() {
        if (gender == null) {
            gender = new ChoiceGroup("Gender", Choice.EXCLUSIVE, new java.lang.String[] {
                "Male", "Female"
            }, new Image[] {
                null, null
            });
            gender.setSelectedFlags(new boolean[] {
                true, false
            });
        }
        return gender;
    }

    TextField firstName;
    TextField lastName;
    DateField dateOfBirth;
    
    ChoiceGroup gender;
    Displayable referer;
    FormulaValue formulaValue = null;
    int patientId;
    
    Command backCommand;
    Command saveCommand;
    Command listCommand;
    javax.microedition.midlet.MIDlet caller;
}
