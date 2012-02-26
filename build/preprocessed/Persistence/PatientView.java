/*
 * PatientView.java
 *
 * Created on March 17, 2006, 10:49 PM
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
public class PatientView extends Form implements CommandListener {
    MIDlet caller;
    Displayable referer;
    int patientId;
    int[] formulaMapping;
    /** Creates a new instance of PatientView */
    public PatientView(MIDlet caller, Displayable referer, int patientId) {
        super("Showing Patient");
        this.caller = caller;
        this.referer = referer;
        this.patientId = patientId;
        
        initPatientInfo();
        initFormulaList();
        
        append(get_fullName());
        append(get_gender());
        append(get_dateOfBirth());
        append(get_formulaList());
        addCommand(get_backCommand());
        addCommand(get_editCommand());
        addCommand(get_deleteSelectedCommand());
        setCommandListener(this);
    }
    
    public void commandAction(Command command, Displayable disp) {
        if (command == backCommand) {
            Display.getDisplay(caller).setCurrent(referer);
        } else if (command == editCommand) {
            Form form = PatientForm.getForm(caller, this, patientId);
            Display.getDisplay(caller).setCurrent(form);
        } else if (command == deleteSelectedCommand) {
            boolean[] selectedFormulas = new boolean[formulaList.size()];
            formulaList.getSelectedFlags(selectedFormulas);
            
            for (int i = 0; i < selectedFormulas.length; i++) {
                if (selectedFormulas[i]) {
                    if (new FormulaDao().delete(formulaMapping[i])) {
                        formulaList.delete(i);
                    }
                }
            }
            
            if (formulaList.size() == 0) {
                setNoFormulaLabel();
            }
        }
    }
    
    private void initFormulaList() {
        FormulaValue[] fvs = new FormulaDao().findByPatientId(patientId);
        formulaMapping = new int[fvs.length];
        
        if (fvs.length == 0) {
            setNoFormulaLabel();
            return;
        }
        for (int i = 0; i < fvs.length; i++) {
            get_formulaList().append(fvs[i].toString(), null);
            formulaMapping[i] = fvs[i].id;
        }
    }

    private void setNoFormulaLabel() {
        formulaList.setLabel("<No Formulas Found>");
    }
    
    private void initPatientInfo() {
        Patient patient = new PatientDao().find(patientId);
        setTitle("Showing patient " + patient.getFullName());
        get_fullName().setText(patient.getFullName());
        get_gender().setText(patient.getGenderInWords());
        get_dateOfBirth().setDate(patient.dateOfBirth);
    }
    
    private StringItem get_fullName() {
        if (fullName == null) {
            fullName = new StringItem("Patient Name: ", "");
        }
        return fullName;
    }
   
    private StringItem get_gender() {
        if (gender == null) {
            gender = new StringItem("Gender: ", "");
        }
        return gender;
    }
    
    private DateField get_dateOfBirth() {
        if (dateOfBirth == null) {
            dateOfBirth = new DateField("Date of Birth :", DateField.DATE);
        }
        return dateOfBirth;
    }
    
    private Command get_backCommand() {
        if (backCommand == null) {
            backCommand = new Command("Back", Command.BACK, 1);
        }
        return backCommand;
    }
    
    private ChoiceGroup get_formulaList() {
        if (formulaList == null) {
            formulaList = new ChoiceGroup("List of Formulas: ", ChoiceGroup.MULTIPLE);
        }
        return formulaList;
    }
    
    private Command get_deleteSelectedCommand() {
        if (deleteSelectedCommand == null) {
            deleteSelectedCommand = new Command("Delete Checked Formulas", Command.SCREEN, 3);
        }
        return deleteSelectedCommand;
    }
    
    private Command get_editCommand() {
        if (editCommand == null) {
            editCommand = new Command("Edit Patient", Command.SCREEN, 1);
        }
        return editCommand;
    }
    
    private Command backCommand;
    private Command deleteSelectedCommand;
    private Command editCommand;
    
    private StringItem fullName;
    private StringItem gender;
    private DateField dateOfBirth;
    private ChoiceGroup formulaList;
}
