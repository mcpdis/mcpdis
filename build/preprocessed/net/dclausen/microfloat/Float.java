/*
 * Float.java
 *
 * Created on March 7, 2006, 3:49 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package net.dclausen.microfloat;

/**
 *
 * @author user
 */
public class Float extends Double implements INumeric {

    public Float(int d) {
        super(d);
    }
    
    public Float(String d) {
        super(d);
    }
}
