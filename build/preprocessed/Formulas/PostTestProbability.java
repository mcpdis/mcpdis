/*
 * PostTestProbability.java
 *
 * Created on March 10, 2006, 12:25 AM
 */

package Formulas;

import net.dclausen.microfloat.*;
/**
 *
 * @author user
 */
public class PostTestProbability extends javax.microedition.midlet.MIDlet
        implements                    javax.microedition.lcdui.ItemStateListener, javax.microedition.lcdui.CommandListener {

    javax.microedition.lcdui.Form patientForm;
    
    /** Creates a new instance of PostTestProbability */
    public PostTestProbability() {
    }

    private Persistence.FormulaValue get_formulaValue() {
        String posOrNeg;
        if (get_testResult().getSelectedIndex() == 0) {
            posOrNeg = "(positive)";
        } else {
            posOrNeg = "(negative)";
        }
        return new Persistence.FormulaValue("Post Test Probability " + posOrNeg, get_result().getText());
    }
    
// --- This section is auto-generated by NetBeans IDE. Do not modify or you may lose your changes.//<editor-fold id="MVDMethods" defaultstate="collapsed" desc="This section is auto-generated by NetBeans IDE.">//GEN-BEGIN:MVDMethods
    /**
     * This method initializes UI of the application.
     */
    private void initialize() {
// For adding user code into this block, select "Design" item in the inspector and invoke property editor on Action property in Properties window.
        javax.microedition.lcdui.Display.getDisplay(this).setCurrent(get_postTestProbForm());
    }
    
    /**
     * Called by the system to indicate that a command has been invoked on a particular displayable.
     * @param command the Command that ws invoked
     * @param displayable the Displayable on which the command was invoked
     **/
    public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable) {
        if (displayable == postTestProbForm) {
            if (command == backCommand) {
// For adding user code into this block, select "Design | Screens | postTestProbForm [Form] | Assigned Commands | backCommand" item in the inspector and invoke property editor on Action property in Properties window.
                javax.microedition.lcdui.Display.getDisplay(this).setCurrent(null);
                destroyApp(true);
                notifyDestroyed();
            } else if (command == saveCommand) {
// For adding user code into this block, select "Design | Screens | postTestProbForm [Form] | Assigned Commands | saveCommand" item in the inspector and invoke property editor on Action property in Properties window.
                Persistence.PatientSuiteDispatcher.invoke(this, get_postTestProbForm(), get_formulaValue());
            }
        }
    }
    
    /**
     * This method returns instance for postTestProbForm component and should be called instead of accessing postTestProbForm field directly.
     * @return Instance for postTestProbForm component
     **/
    private javax.microedition.lcdui.Form get_postTestProbForm() {
        if (postTestProbForm == null) {
            postTestProbForm = new javax.microedition.lcdui.Form("Post-test probability", new javax.microedition.lcdui.Item[] {
                get_pretestProb(),
                        get_sensitivity(),
                        get_specificity(),
                        get_testResult(),
                        get_result()
            });
            postTestProbForm.addCommand(get_saveCommand());
            postTestProbForm.addCommand(get_backCommand());
            postTestProbForm.setCommandListener(this);
        }
        return postTestProbForm;
    }
    
    /**
     * This method returns instance for pretestProb component and should be called instead of accessing pretestProb field directly.
     * @return Instance for pretestProb component
     **/
    private javax.microedition.lcdui.TextField get_pretestProb() {
        if (pretestProb == null) {
            pretestProb = new javax.microedition.lcdui.TextField("Pretest prob [%]", "", 20, 0x0);
        }
        return pretestProb;
    }
    
    /**
     * This method returns instance for sensitivity component and should be called instead of accessing sensitivity field directly.
     * @return Instance for sensitivity component
     **/
    private javax.microedition.lcdui.TextField get_sensitivity() {
        if (sensitivity == null) {
            sensitivity = new javax.microedition.lcdui.TextField("Sensitivity [%]", "", 20, 0x0);
        }
        return sensitivity;
    }
    
    /**
     * This method returns instance for specificity component and should be called instead of accessing specificity field directly.
     * @return Instance for specificity component
     **/
    private javax.microedition.lcdui.TextField get_specificity() {
        if (specificity == null) {
            specificity = new javax.microedition.lcdui.TextField("Specificity [%]", "", 20, 0x0);
        }
        return specificity;
    }
    
    /**
     * This method returns instance for testResult component and should be called instead of accessing testResult field directly.
     * @return Instance for testResult component
     **/
    private javax.microedition.lcdui.ChoiceGroup get_testResult() {
        if (testResult == null) {
            testResult = new javax.microedition.lcdui.ChoiceGroup("Test result", javax.microedition.lcdui.Choice.EXCLUSIVE, new java.lang.String[] {
                "Positive",
                        "Negative"
            }, new javax.microedition.lcdui.Image[] {
                null,
                        null
            });
            testResult.setSelectedFlags(new boolean[] {
                true,
                        false
            });
        }
        return testResult;
    }
    
    /**
     * This method returns instance for result component and should be called instead of accessing result field directly.
     * @return Instance for result component
     **/
    private javax.microedition.lcdui.StringItem get_result() {
        if (result == null) {
            result = new javax.microedition.lcdui.StringItem("Post-test prob. [%] =", "");
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
    
    /**
     * This method returns instance for backCommand component and should be called instead of accessing backCommand field directly.
     * @return Instance for backCommand component
     **/
    private javax.microedition.lcdui.Command get_backCommand() {
        if (backCommand == null) {
            backCommand = new javax.microedition.lcdui.Command("Back", javax.microedition.lcdui.Command.BACK, 1);
        }
        return backCommand;
    }
    
    javax.microedition.lcdui.Form postTestProbForm;
    javax.microedition.lcdui.TextField pretestProb;
    javax.microedition.lcdui.TextField sensitivity;
    javax.microedition.lcdui.TextField specificity;
    javax.microedition.lcdui.ChoiceGroup testResult;
    javax.microedition.lcdui.StringItem result;
    javax.microedition.lcdui.Command saveCommand;
    javax.microedition.lcdui.Command backCommand;
// --- This is the end of auto-generated section.//</editor-fold>//GEN-END:MVDMethods
    
    public void startApp() {
        initialize();
        get_postTestProbForm().setItemStateListener(this);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void itemStateChanged(javax.microedition.lcdui.Item item) {
        if (allItemsValid()) {
            INumeric pretest = new Float(get_pretestProb().getString()).div("100");
            INumeric sens = new Float(get_sensitivity().getString()).div("100");
            INumeric spec = new Float(get_specificity().getString()).div("100");

            INumeric result;
            if (get_testResult().getSelectedIndex() == 0) {
                result = getPositiveRes(pretest, sens, spec);
            } else {
                result = getNegativeRes(pretest, sens, spec);
            }
            
            get_result().setText(result.mul("100").format(2));
        } else {
            get_result().setText("<all items must be non-empty and valid>");
        }
    }
    
    private INumeric getPositiveRes(INumeric pret, INumeric sens, INumeric spec) {
        INumeric pretXsens = pret.mul(sens);
        INumeric oneMinusPret = new Float("1").sub(pret);
        INumeric oneMinusSpec = new Float("1").sub(spec);
        INumeric denom = pretXsens.add(oneMinusPret.mul(oneMinusSpec));
        
        return pret.mul(sens.div(denom));
    }

    private INumeric getNegativeRes(INumeric pret, INumeric sens, INumeric spec) {
        INumeric oneMinusSens = new Float("1").sub(sens);
        INumeric oneMinusPret = new Float("1").sub(pret);
        
        INumeric denom = pret.mul(oneMinusSens).add(oneMinusPret.mul(spec));
        return pret.mul(oneMinusSens.div(denom));
    }
    
    private boolean allItemsValid() {
        try {
            MicroFloat.parseFloat(get_pretestProb().getString());
            MicroFloat.parseFloat(get_sensitivity().getString());
            MicroFloat.parseFloat(get_specificity().getString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
