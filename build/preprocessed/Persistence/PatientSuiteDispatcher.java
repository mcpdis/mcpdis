/*
 * BaseMidlet.java
 *
 * Created on March 11, 2006, 1:17 PM
 */

package Persistence;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
/**
 *
 * @author  user
 * @version
 */
public class PatientSuiteDispatcher {
    public static void invokePatientSuite(MIDlet caller, Displayable referer, FormulaValue fv) {
        RemoteHelper.setCurrentMIDlet(caller);
        Display.getDisplay(caller).setCurrent(PatientForm.getForm(caller, referer, fv));
    }
    
    public static void invoke(MIDlet caller, Displayable referer, FormulaValue fv) {
        RemoteHelper.setCurrentMIDlet(caller);
        Display.getDisplay(caller).setCurrent(PatientForm.getForm(caller, referer, fv));
    }

}
