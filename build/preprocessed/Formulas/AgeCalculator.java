/*
 * AgeCalculator.java
 *
 * Created on March 7, 2006, 9:54 PM
 */

package Formulas;

import java.util.Date;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import net.dclausen.microfloat.*;
/**
 *
 * @author user
 */
public class AgeCalculator extends MIDlet 
        implements javax.microedition.lcdui.CommandListener,
                   javax.microedition.lcdui.ItemStateListener {

    public static final String DAYS_PER_MONTH = "30.4368";
    public static final String DAYS_PER_YEAR = "365.2422";
    
    /** Creates a new instance of AgeCalculator */
    public AgeCalculator() {
    }
    
    private Persistence.FormulaValue get_formulaValue() {
        return new Persistence.FormulaValue("Age", get_ageResult().getText());
    }
    
// --- This section is auto-generated by NetBeans IDE. Do not modify or you may lose your changes.//<editor-fold id="MVDMethods" defaultstate="collapsed" desc="This section is auto-generated by NetBeans IDE.">//GEN-BEGIN:MVDMethods
    /**
     * This method initializes UI of the application.
     */
    private void initialize() {
// For adding user code into this block, select "Design" item in the inspector and invoke property editor on Action property in Properties window.
        javax.microedition.lcdui.Display.getDisplay(this).setCurrent(get_ageForm());
    }
    
    /**
     * Called by the system to indicate that a command has been invoked on a particular displayable.
     * @param command the Command that ws invoked
     * @param displayable the Displayable on which the command was invoked
     **/
    public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable) {
        if (displayable == ageForm) {
            if (command == backCommand1) {
// For adding user code into this block, select "Design | Screens | ageForm [Form] | Assigned Commands | backCommand1" item in the inspector and invoke property editor on Action property in Properties window.
                javax.microedition.lcdui.Display.getDisplay(this).setCurrent(null);
                destroyApp(true);
                notifyDestroyed();
            } else if (command == saveCommand) {
// For adding user code into this block, select "Design | Screens | ageForm [Form] | Assigned Commands | saveCommand" item in the inspector and invoke property editor on Action property in Properties window.
                Persistence.PatientSuiteDispatcher.invoke(this, get_ageForm(), get_formulaValue());
            }
        }
    }
    
    /**
     * This method returns instance for ageForm component and should be called instead of accessing ageForm field directly.
     * @return Instance for ageForm component
     **/
    private javax.microedition.lcdui.Form get_ageForm() {
        if (ageForm == null) {
            ageForm = new javax.microedition.lcdui.Form("Age Calculator", new javax.microedition.lcdui.Item[] {
                get_dateOfBirth(),
                        get_currentDate(),
                        get_ageResult()
            });
            ageForm.addCommand(get_backCommand1());
            ageForm.addCommand(get_saveCommand());
            ageForm.setCommandListener(this);
        }
        return ageForm;
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
     * This method returns instance for dateOfBirth component and should be called instead of accessing dateOfBirth field directly.
     * @return Instance for dateOfBirth component
     **/
    private javax.microedition.lcdui.DateField get_dateOfBirth() {
        if (dateOfBirth == null) {
            dateOfBirth = new javax.microedition.lcdui.DateField("Date of Birth", javax.microedition.lcdui.DateField.DATE);
        }
        return dateOfBirth;
    }
    
    /**
     * This method returns instance for currentDate component and should be called instead of accessing currentDate field directly.
     * @return Instance for currentDate component
     **/
    private javax.microedition.lcdui.DateField get_currentDate() {
        if (currentDate == null) {
            currentDate = new javax.microedition.lcdui.DateField("Current Date", javax.microedition.lcdui.DateField.DATE);
        }
        return currentDate;
    }
    
    /**
     * This method returns instance for ageResult component and should be called instead of accessing ageResult field directly.
     * @return Instance for ageResult component
     **/
    private javax.microedition.lcdui.StringItem get_ageResult() {
        if (ageResult == null) {
            ageResult = new javax.microedition.lcdui.StringItem("Age = ", "<enter your date of birth>");
        }
        return ageResult;
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
    
    javax.microedition.lcdui.Form ageForm;
    javax.microedition.lcdui.Command backCommand1;
    javax.microedition.lcdui.DateField dateOfBirth;
    javax.microedition.lcdui.DateField currentDate;
    javax.microedition.lcdui.StringItem ageResult;
    javax.microedition.lcdui.Command saveCommand;
// --- This is the end of auto-generated section.//</editor-fold>//GEN-END:MVDMethods
    
    public void startApp() {
        initialize();
        get_ageForm().setItemStateListener(this);
        get_currentDate().setDate(new Date(System.currentTimeMillis()));
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }

    public void itemStateChanged(javax.microedition.lcdui.Item item) {
        if (item.equals(get_currentDate()) || item.equals(get_dateOfBirth())) {
            if (get_currentDate().getDate() != null && get_dateOfBirth().getDate() != null) {
                StringBuffer sb = new StringBuffer();
                sb.append(calculateAgeInYearsAndMonths());
                sb.append("\nor " + calculateAgeInMonths() + " months");
                sb.append("\nor " + calculateAgeInWeeksAndDays());        // append weeks + days 
                sb.append("\nor " + calculateAgeInDays() + " day(s)");      // append days
                get_ageResult().setText(sb.toString());
            }
        }
    }
    
    private long calculateAgeInDays() {
        return dateOffsetInSeconds() / 86400;
    }
    
    private String calculateAgeInYearsAndMonths() {
        INumeric ageInDays = new Double(MicroDouble.longToDouble(calculateAgeInDays()));
        INumeric exactNumInYears = ageInDays.div(DAYS_PER_YEAR);
        
        String numOfYears = exactNumInYears.format(0);

        INumeric remainingDays = ageInDays.mod(DAYS_PER_YEAR);
        String numOfMonths = remainingDays.div(DAYS_PER_MONTH).format(0);
        
        StringBuffer sb = new StringBuffer(numOfYears + " year(s)");
        if (numOfMonths != "0") {
            sb.append(" and " + numOfMonths + " month(s) "); 
        }
        
        sb.append("(" + exactNumInYears.format(2) + ")");
        return sb.toString();
    }

    private String calculateAgeInMonths() {
        Double ageInDays = new Double(MicroDouble.longToDouble(calculateAgeInDays()));
        long daysPerMonth = MicroDouble.parseDouble(DAYS_PER_MONTH);
        
        return ageInDays.div(DAYS_PER_MONTH).format(1);
    }
    
    private String calculateAgeInWeeksAndDays() {
        long ageInDays = calculateAgeInDays();
        long numOfWeeks = calculateAgeInDays() / 7;
        long daysRemainder = ageInDays % 7;
        
        return numOfWeeks + " weeks and " + daysRemainder + " day(s)";
    }
    
    private long dateOffsetInSeconds() {
        long unixCurrentDate = get_currentDate().getDate().getTime();
        long unixDateOfBirth = get_dateOfBirth().getDate().getTime();
        
        return (unixCurrentDate - unixDateOfBirth) / 1000;
    }    
}
