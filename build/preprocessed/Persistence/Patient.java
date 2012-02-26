/*
 * Patient.java
 *
 * Created on March 12, 2006, 12:45 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package Persistence;
import com.sun.cldc.io.DateParser;
import java.util.Date;
/**
 *
 * @author user
 */
public class Patient {
    public int id = -1;
    public String firstName;
    public String lastName;
    public boolean gender; // true for male, false for female
    public Date dateOfBirth;
    
    /** Creates a new instance of Patient */
    public Patient() {
    }

    public String getCapitalizedFirstName() {
        if (firstName.length() <= 1) {
            return firstName;
        }
        
        return firstName.substring(0, 1).toUpperCase() + 
               firstName.substring(1, firstName.length());
    }
    
    public String getCapitalizedLastName() {
        if (lastName.length() <= 1) {
            return lastName;
        }
        return lastName.substring(0, 1).toUpperCase() + 
               lastName.substring(1, lastName.length());
    }
    
    public String getFullName() {
        return getCapitalizedLastName() + ", " + getCapitalizedFirstName();
    }
    
    public int getDateOfBirthInSeconds() {
        return (int)(dateOfBirth.getTime() / 1000);
    }
    
    public void setDateOfBirthInSeconds(int seconds) {
        dateOfBirth = new Date(((long)seconds) * 1000);
    }
    
    public String getGenderInWords() {
        return (gender) ? "male" : "female";
    }
}
