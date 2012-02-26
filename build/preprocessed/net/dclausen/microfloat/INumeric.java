/*
 * INumeric.java
 *
 * Created on March 7, 2006, 4:16 PM
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
public interface INumeric {
    public INumeric add(String d);
    public INumeric sub(String d);
    public INumeric mul(String d);
    public INumeric div(String d);
    public INumeric mod(String d);

    public INumeric add(INumeric d);
    public INumeric sub(INumeric d);
    public INumeric mul(INumeric d);
    public INumeric div(INumeric d);
    public INumeric mod(INumeric d);

    public INumeric ceil();
    public INumeric log10();
    public boolean lessThan(String d);
    
    public String format(int numOfDecimals);
    public String toString();
    public long getRawValue();
}
