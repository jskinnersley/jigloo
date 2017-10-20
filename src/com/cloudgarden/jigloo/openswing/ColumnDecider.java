/*
 * Created on Nov 14, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cloudgarden.jigloo.openswing;

/**
 * @author jonathan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ColumnDecider {

    public static boolean isCompatible(String colType, Class attrType) {
        if(colType.equals("CheckBoxColumn")) {
            return (attrType.equals(Boolean.class) || attrType.equals(boolean.class));
        }
        if(colType.equals("DecimalColumn") || colType.equals("CurrencyColumn") 
                || colType.equals("IntegerColumn") || colType.equals("PercentageColumn")
                || colType.equals("ProgressBarColumn") || colType.equals("TimeColumn")) {
            return (attrType.equals(Integer.class) ||
                    attrType.equals(Long.class) ||
                    attrType.equals(Float.class) ||
                    attrType.equals(Double.class) ||
                    attrType.equals(java.math.BigDecimal.class) ||
                    attrType.equals(Long.class)
            );
        }
        if(colType.equals("DateColumn") || colType.equals("DateTimeColumn")) {
            return (attrType.equals(java.util.Date.class)
                    || attrType.equals(java.sql.Date.class) 
                    || attrType.equals(java.sql.Timestamp.class));
        }
        if(colType.equals("ImageColumn")) {
            return (attrType.equals(byte[].class));
        }
        if(colType.equals("ImageColumn")) {
            return (attrType.equals(byte[].class));
        }
        if(colType.equals("MultiLineTextColumn") || colType.equals("TextColumn")) {
            return (attrType.equals(String.class));
        }
        return true;
    }
    
}
