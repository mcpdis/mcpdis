/*
 * CardiacOutputEcho.java
 *
 * Created on March 7, 2006, 11:43 PM
 */

package Formulas;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import net.dclausen.microfloat.*;
/**
 *
 * @author user
 */
public class CardiacOutputEcho extends MIDlet 
        implements javax.microedition.lcdui.CommandListener,
                   javax.microedition.lcdui.ItemStateListener {
    
    /** Creates a new instance of CardiacOutputEcho */
    public CardiacOutputEcho() {
    }
    
    private Persistence.FormulaValue get_formulaValue() {
        return new Persistence.FormulaValue("Cardiac Output (echo) [L/min]", get_result().getText());
    }
// --- This section is auto-generated by NetBeans IDE. Do not modify or you may lose your changes.//<editor-fold id="MVDMethods" defaultstate="collapsed" desc="This section is auto-generated by NetBeans IDE.">//GEN-BEGIN:MVDMethods
    /**
     * This method initializes UI of the application.
     */
    private void initialize() {
// For adding user code into this block, select "Design" item in the inspector and invoke property editor on Action property in Properties window.
        javax.microedition.lcdui.Display.getDisplay(this).setCurrent(get_cardiacOutputForm());
    }
    
    /**
     * Called by the system to indicate that a command has been invoked on a particular displayable.
     * @param command the Command that ws invoked
     * @param displayable the Displayable on which the command was invoked
     **/
    public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable) {
        if (displayable == cardiacOutputForm) {
            if (command == backCommand1) {
// For adding user code into this block, select "Design | Screens | cardiacOutputForm [Form] | Assigned Commands | backCommand1" item in the inspector and invoke property editor on Action property in Properties window.
                javax.microedition.lcdui.Display.getDisplay(this).setCurrent(null);
                destroyApp(true);
                notifyDestroyed();
            } else if (command == saveCommand) {
// For adding user code into this block, select "Design | Screens | cardiacOutputForm [Form] | Assigned Commands | saveCommand" item in the inspector and invoke property editor on Action property in Properties window.
                Persistence.PatientSuiteDispatcher.invoke(this, get_cardiacOutputForm(), get_formulaValue());
            }
        }
    }
    
    /**
     * This method returns instance for cardiacOutputForm component and should be called instead of accessing cardiacOutputForm field directly.
     * @return Instance for cardiacOutputForm component
     **/
    private javax.microedition.lcdui.Form get_cardiacOutputForm() {
        if (cardiacOutputForm == null) {
            cardiacOutputForm = new javax.microedition.lcdui.Form(" Cardiac Output (echo)", new javax.microedition.lcdui.Item[] {
                get_lvotDiameter(),
                        get_tvi(),
                        get_heartRate(),
                        get_result()
            });
            cardiacOutputForm.addCommand(get_backCommand1());
            cardiacOutputForm.addCommand(get_saveCommand());
            cardiacOutputForm.setCommandListener(this);
        }
        return cardiacOutputForm;
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
     * This method returns instance for lvotDiameter component and should be called instead of accessing lvotDiameter field directly.
     * @return Instance for lvotDiameter component
     **/
    private javax.microedition.lcdui.TextField get_lvotDiameter() {
        if (lvotDiameter == null) {
            lvotDiameter = new javax.microedition.lcdui.TextField("LVOT diameter [cm]", "", 20, 0x0);
        }
        return lvotDiameter;
    }
    
    /**
     * This method returns instance for tvi component and should be called instead of accessing tvi field directly.
     * @return Instance for tvi component
     **/
    private javax.microedition.lcdui.TextField get_tvi() {
        if (tvi == null) {
            tvi = new javax.microedition.lcdui.TextField("TVI [cm/sec]", "", 20, 0x0);
        }
        return tvi;
    }
    
    /**
     * This method returns instance for heartRate component and should be called instead of accessing heartRate field directly.
     * @return Instance for heartRate component
     **/
    private javax.microedition.lcdui.TextField get_heartRate() {
        if (heartRate == null) {
            heartRate = new javax.microedition.lcdui.TextField("Heart Rate [bpm]", "", 20, 0x0);
        }
        return heartRate;
    }
    
    /**
     * This method returns instance for result component and should be called instead of accessing result field directly.
     * @return Instance for result component
     **/
    private javax.microedition.lcdui.StringItem get_result() {
        if (result == null) {
            result = new javax.microedition.lcdui.StringItem("Cardiac Output [L/min]", "");
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
    
    javax.microedition.lcdui.Form cardiacOutputForm;
    javax.microedition.lcdui.Command backCommand1;
    javax.microedition.lcdui.TextField lvotDiameter;
    javax.microedition.lcdui.TextField tvi;
    javax.microedition.lcdui.TextField heartRate;
    javax.microedition.lcdui.StringItem result;
    javax.microedition.lcdui.Command saveCommand;
// --- This is the end of auto-generated section.//</editor-fold>//GEN-END:MVDMethods
    
    public void startApp() {
        initialize();
        get_cardiacOutputForm().setItemStateListener(this);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void itemStateChanged(javax.microedition.lcdui.Item item) {
        if (!allItemsValid()) {
            get_result().setText("<fields must not be empty and must be valid>");
        } else {
            String hr = get_heartRate().getString();
            String diam = get_lvotDiameter().getString();
            String tvi = get_tvi().getString();
            String co = new Float("3.14").mul(hr).mul(diam).mul(diam).mul(tvi).div("4").div("1000").format(2);
            get_result().setText(co);
        }
    }
    
    private boolean allItemsValid() {
        try {
            MicroFloat.parseFloat(get_lvotDiameter().getString());
            MicroFloat.parseFloat(get_tvi().getString());
            MicroFloat.parseFloat(get_heartRate().getString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
