/*
 * MeanArterialPressure.java
 *
 * Created on March 9, 2006, 5:25 PM
 */

package Formulas;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import net.dclausen.microfloat.*;
/**
 *
 * @author user
 */
public class MeanArterialPressure extends MIDlet 
        implements javax.microedition.lcdui.CommandListener,
                   javax.microedition.lcdui.ItemStateListener {
    
    /** Creates a new instance of MeanArterialPressure */
    public MeanArterialPressure() {
    }

    private Persistence.FormulaValue get_formulaValue() {
        return new Persistence.FormulaValue("Mean Arterial Pressure [mmHg]", get_result().getText());
    }
    
// --- This section is auto-generated by NetBeans IDE. Do not modify or you may lose your changes.//<editor-fold id="MVDMethods" defaultstate="collapsed" desc="This section is auto-generated by NetBeans IDE.">//GEN-BEGIN:MVDMethods
    /**
     * This method initializes UI of the application.
     */
    private void initialize() {
// For adding user code into this block, select "Design" item in the inspector and invoke property editor on Action property in Properties window.
        javax.microedition.lcdui.Display.getDisplay(this).setCurrent(get_mapForm());
    }
    
    /**
     * Called by the system to indicate that a command has been invoked on a particular displayable.
     * @param command the Command that ws invoked
     * @param displayable the Displayable on which the command was invoked
     **/
    public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable) {
        if (displayable == mapForm) {
            if (command == backCommand1) {
// For adding user code into this block, select "Design | Screens | mapForm [Form] | Assigned Commands | backCommand1" item in the inspector and invoke property editor on Action property in Properties window.
                javax.microedition.lcdui.Display.getDisplay(this).setCurrent(null);
                destroyApp(true);
                notifyDestroyed();
            } else if (command == saveCommand) {
// For adding user code into this block, select "Design | Screens | mapForm [Form] | Assigned Commands | saveCommand" item in the inspector and invoke property editor on Action property in Properties window.
                Persistence.PatientSuiteDispatcher.invoke(this, get_mapForm(), get_formulaValue());
            }
        }
    }
    
    /**
     * This method returns instance for mapForm component and should be called instead of accessing mapForm field directly.
     * @return Instance for mapForm component
     **/
    private javax.microedition.lcdui.Form get_mapForm() {
        if (mapForm == null) {
            mapForm = new javax.microedition.lcdui.Form("Mean Arterial Pressure", new javax.microedition.lcdui.Item[] {
                get_systolic(),
                        get_diastolic(),
                        get_result()
            });
            mapForm.addCommand(get_backCommand1());
            mapForm.addCommand(get_saveCommand());
            mapForm.setCommandListener(this);
        }
        return mapForm;
    }
    
    /**
     * This method returns instance for backCommand1 component and should be called instead of accessing backCommand1 field directly.
     * @return Instance for backCommand1 component
     **/
    private javax.microedition.lcdui.Command get_backCommand1() {
        if (backCommand1 == null) {
            backCommand1 = new javax.microedition.lcdui.Command("Back", javax.microedition.lcdui.Command.BACK, 1);
        }
        return backCommand1;
    }
    
    /**
     * This method returns instance for systolic component and should be called instead of accessing systolic field directly.
     * @return Instance for systolic component
     **/
    private javax.microedition.lcdui.TextField get_systolic() {
        if (systolic == null) {
            systolic = new javax.microedition.lcdui.TextField("Systolic [mmHg]", "", 20, 0x0);
        }
        return systolic;
    }
    
    /**
     * This method returns instance for diastolic component and should be called instead of accessing diastolic field directly.
     * @return Instance for diastolic component
     **/
    private javax.microedition.lcdui.TextField get_diastolic() {
        if (diastolic == null) {
            diastolic = new javax.microedition.lcdui.TextField("Diastolic [mmHg]", "", 20, 0x0);
        }
        return diastolic;
    }
    
    /**
     * This method returns instance for result component and should be called instead of accessing result field directly.
     * @return Instance for result component
     **/
    private javax.microedition.lcdui.StringItem get_result() {
        if (result == null) {
            result = new javax.microedition.lcdui.StringItem("M.A.P. [mmHg] =", "");
        }
        return result;
    }
    
    /**
     * This method returns instance for saveCommand component and should be called instead of accessing saveCommand field directly.
     * @return Instance for saveCommand component
     **/
    private javax.microedition.lcdui.Command get_saveCommand() {
        if (saveCommand == null) {
            saveCommand = new javax.microedition.lcdui.Command("Save", javax.microedition.lcdui.Command.ITEM, 1);
        }
        return saveCommand;
    }
    
    javax.microedition.lcdui.Form mapForm;
    javax.microedition.lcdui.Command backCommand1;
    javax.microedition.lcdui.TextField systolic;
    javax.microedition.lcdui.TextField diastolic;
    javax.microedition.lcdui.StringItem result;
    javax.microedition.lcdui.Command saveCommand;
// --- This is the end of auto-generated section.//</editor-fold>//GEN-END:MVDMethods
    
    public void startApp() {
        initialize();
        get_mapForm().setItemStateListener(this);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void itemStateChanged(javax.microedition.lcdui.Item item) {
        if (!allItemsValid()) {
            get_result().setText("<all fields must be valid and non empty>");
        } else {
            // MAP = (P.sys + 2* P.dia) /3
            String pSys = get_systolic().getString();
            String pDia = get_diastolic().getString();
            INumeric numerator = new Float(pSys).add(new Float("2").mul(pDia));
            get_result().setText(numerator.div("3").format(2));
        }
    }
    
    private boolean allItemsValid() {
        try {
            MicroFloat.parseFloat(get_systolic().getString());
            MicroFloat.parseFloat(get_diastolic().getString());
            return true;
        } catch (Exception e) {
            return false;
        }
         
    }
}
