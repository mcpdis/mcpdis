/*
 * BaseMidlet.java
 *
 * Created on March 11, 2006, 1:17 PM
 */

package Persistence;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author  user
 * @version
 */
public class BaseMidlet extends MIDlet {
    public void startApp() {
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void baseSaveCommand(javax.microedition.midlet.MIDlet caller, Displayable referer, FormulaValue fv) {
        Display.getDisplay(this).setCurrent(get_PatientForm(caller, referer, fv));
        String formulaResult = fv.getResult();
    }

    private Form get_PatientForm(javax.microedition.midlet.MIDlet caller, Displayable referer, FormulaValue fv) {
        patientForm = Persistence.PatientForm.getForm(caller, referer, fv);
        return patientForm;
    }
    
    javax.microedition.lcdui.Form patientForm;
}
