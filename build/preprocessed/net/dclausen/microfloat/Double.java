/*
 * Double.java
 *
 * Created on March 7, 2006, 1:03 AM
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
public class Double implements INumeric {
    long _value;
    /** Creates a new instance of Double */
    public Double(String value) {
        _value = MicroDouble.parseDouble(value);
    }
    
    public Double(long d) {
        _value = d;
    }

    public INumeric add(String d) {
        long dToLong = MicroDouble.parseDouble(d);
        return add(dToLong);
    }

    public INumeric add(INumeric d) {
        return add(d.getRawValue());
    }
    private INumeric add(long d) {
        return new Double(MicroDouble.add(_value, d));
    }
    
    public INumeric sub(String d) {
        long dToLong = MicroDouble.parseDouble(d);
        return sub(dToLong);
    }

    public INumeric sub(INumeric d) {
        return sub(d.getRawValue());
    }
    
    private INumeric sub(long d) {
        return new Double(MicroDouble.sub(_value, d));
    }


    public INumeric mul(String d) {
        long dToLong = MicroDouble.parseDouble(d);
        return mul(dToLong);
    }
    
    public INumeric mul(INumeric d) {
        return mul(d.getRawValue());
    }

    private INumeric mul(long d) {
        return new Double(MicroDouble.mul(_value, d));
    }
    
    public INumeric div(String d) {
        long dToLong = MicroDouble.parseDouble(d);
        return div(dToLong);
    }

    public INumeric div(INumeric d) {
        return div(d.getRawValue());
    }
    
    private INumeric div(long d) {
        return new Double(MicroDouble.div(_value, d));
    }
    
    public INumeric mod(INumeric d) {
        return mod(d.getRawValue());
    }
    
    public INumeric mod(String d) {
        long dToLong = MicroDouble.parseDouble(d);
        return mod(dToLong);
    }

    private INumeric mod(long d) {
        return new Double(MicroDouble.mod(_value, d));
    }

    public INumeric pow(String d) {
        return pow(MicroDouble.parseDouble(d));
    }

    private INumeric pow(long d) {
        return new Double(MicroDouble.pow(_value, d));
    }
    
    public INumeric sqrt() {
        return new Double(MicroDouble.sqrt(_value));
    }
    
    public String format(int numOfDecimals) {
        int offSet = 1;
        if (numOfDecimals == 0) {
            offSet = 0;
        }
        
        String toString = toString();
        int decimalPosition = toString.indexOf('.');
        
        if (decimalPosition == -1) {
            return toString();
        }
        
        int lengthOfDecimals = decimalPosition + numOfDecimals + offSet;
        
        if (lengthOfDecimals > toString.length()) {
            return toString;
        } else {
            return toString.substring(0, lengthOfDecimals);    
        }
    }
    
    public String toString() {
        return MicroDouble.toString(_value);
    }
    
    public long getRawValue() {
        return _value;
    }
    
    public INumeric ceil() {
        return new Double(MicroDouble.ceil(_value));
    }
    
    public INumeric log10() {
        return new Double(MicroDouble.log10(_value));
    }
    
    public boolean lessThan(String d) {
        return lessThan(MicroDouble.parseDouble(d));
    }
    
    private boolean lessThan(long d) {
        return MicroDouble.lt(_value,  d);
    }
}
