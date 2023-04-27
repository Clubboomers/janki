package main.deckoptions;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class IntegerListFormat extends Format {
    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj instanceof int[]) {
            int[] intArray = (int[]) obj;
            for (int i = 0; i < intArray.length; i++) {
                toAppendTo.append(intArray[i]);
                if (i < intArray.length - 1) {
                    toAppendTo.append(" ");
                }
            }
        }
        return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        String[] stringArray = source.split(" ");
        int[] intArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            try {
                intArray[i] = Integer.parseInt(stringArray[i]);
            } catch (NumberFormatException e) {
                pos.setErrorIndex(i);
                return null;
            }
        }
        pos.setIndex(source.length());
        return intArray;
    }
}