package ua.anakin;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyMatcher {

    public static LinkedHashMap<String, String> getAllItems(String string) {
        LinkedHashMap<String, String> items = new LinkedHashMap<>();

        //remove all spaces and TABs from begin of line
        string = string.replaceAll("^[\\t ]+", "");

        //finding <nodes> and values in string
        Pattern p = Pattern.compile("<([^</>]+)>([^<>]+)</\\1>");
        Matcher m = p.matcher(string);
        while(m.find()) {
            items.put(m.group(1), m.group(2));
        }
        // finding keys and values in string
        Pattern p1 = Pattern.compile("([^=&]+)=([^&=]+)");
        Matcher m1 = p1.matcher(string);
        while(m1.find()) {
            items.put(m1.group(1), m1.group(2));
        }
        return items;
    }

    public static LinkedHashMap<String, String> getResultMap (LinkedHashMap<String, String> map1, LinkedHashMap <String, String> map2) {
        LinkedHashMap<String, String> items = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : map1.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if(map2.containsKey(key)) {
                if(areValuesEqual(value, map2.get(key))) {
                    //do NOTHING
                }
                else items.put(key, value);

            }
            else {
                items.put(key, value);
            }
        }
        return items;
    }

    private static boolean areValuesEqual(String value1, String value2) {
        boolean areEqual = false;

        if ((value1.equals(value2)) || compareContent(value1, value2)) {
            areEqual = true;
        }

        return areEqual;
    }

    private static boolean compareContent(String string1, String string2) {
        
        boolean equality = false;

        if (digitsAndPoint(string1, string2)) {
            return equality = makeNumbersAndCompare(string1, string2);
        }
        else if (digitsPointAndComma(string1, string2)) {
            return equality = makeArraysAndCompare(string1, string2);
        }
        else if (digitsPointCommaAndColon(string1, string2)) {
            return equality = parseTwoDimArrayAndCompare(string1, string2);
        }
        else return equality;
    }

    private static boolean digitsAndPoint(String s1, String s2) {
        //checking if string contains only digits and point
        Pattern p = Pattern.compile("[\\-0-9\\.]*");
        Matcher m1 = p.matcher(s1);
        Matcher m2 = p.matcher(s2);
        return (m1.matches() && m2.matches());                    
    }

    private static boolean digitsPointAndComma(String s1, String s2) {
        //checking if string contains only digits, point and comma
        Pattern p = Pattern.compile("[\\-0-9\\.,]*");
        Matcher m1 = p.matcher(s1);
        Matcher m2 = p.matcher(s2);
        return (m1.matches() && m2.matches());        
    }

    private static boolean digitsPointCommaAndColon(String s1, String s2) {
        //checking if string contains only digits, point, comma and colon
        Pattern p = Pattern.compile("[\\-0-9\\.,:]*");
        Matcher m1 = p.matcher(s1);
        Matcher m2 = p.matcher(s2);
        return (m1.matches() && m2.matches());
    }

    public static boolean areLinesEqual(String s1, String s2){
        //checking if lines are completely equal
        return s1.equals(s2);
    }

    public static boolean makeNumbersAndCompare (String s1, String s2){
        Pattern numberPattern = Pattern.compile("-?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)");// \d+\.?\d*
        Matcher m1 = numberPattern.matcher(s1);
        Matcher m2 = numberPattern.matcher(s2);
        boolean equality = false;

        if (m1.matches() && m2.matches()) {
            double value1 = Double.parseDouble(s1);
            double value2 = Double.parseDouble(s2);
            equality = value1 == value2;
        }
        return equality;
    }

    public static boolean makeArraysAndCompare (String s1, String s2){
        Pattern arrayPattern = Pattern.compile("(,?-?([0-9]+(\\.[0-9]*)?|\\.[0-9]+),?)*");// (,?\d*\.?\d*,?)*
        Matcher m1 = arrayPattern.matcher(s1);
        Matcher m2 = arrayPattern.matcher(s2);

        boolean equality = false;

        if (m1.matches() && m2.matches()) {
            String[] stringArray1 = s1.split(",");
            String[] stringArray2 = s2.split(",");
            double[] array1 = new double[stringArray1.length];
            double[] array2 = new double[stringArray2.length];

            for (int i = 0; i < stringArray1.length; i++) {
                array1[i] = Double.parseDouble(stringArray1[i]);
            }

            for (int i = 0; i < stringArray2.length; i++) {
                array2[i] = Double.parseDouble(stringArray2[i]);
            }

            Arrays.sort(array1);
            Arrays.sort(array2);
            equality = Arrays.equals(array1, array2);
        }
        return equality;
    }

    private static boolean parseTwoDimArrayAndCompare(String s1, String s2) {
        return Arrays.equals(parseeTwoDimArray(s1), parseeTwoDimArray(s2));
    }

    private static double[] parseeTwoDimArray(String string) {
        String[] rows = string.split(":");
        String[][] twoDimArray = new String[rows.length][];

        int r = 0;
        for (String row : rows) {
            twoDimArray[r++] = row.split(",");
        }

        int count = 0;
        for (int i = 0; i < twoDimArray.length; i++) {
            for (int j = 0; j < twoDimArray[i].length; j++) {
                count++;
            }
        }

        double[] array = new double[count];
        count = 0;
        for (int i = 0; i < twoDimArray.length; i++) {
            for (int j = 0; j < twoDimArray[i].length; j++) {
                array[count++] = Double.parseDouble(twoDimArray[i][j]);
            }
        }
        Arrays.sort(array);
        return array;
    }

}