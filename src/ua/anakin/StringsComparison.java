package ua.anakin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class StringsComparison {

    public static void main(String[] args) throws IOException {

        boolean fileEqualFlag = true;
        int lineNumber = 0;

        LinkedHashMap<Integer, LinkedHashMap<String, String>> notEqualLines = new LinkedHashMap<>();

        BufferedReader bufferFile1 = new BufferedReader(new FileReader("resources\\first.txt"));
        BufferedReader bufferFile2 = new BufferedReader(new FileReader("resources\\second.txt"));

        while(true){
            String line1 = bufferFile1.readLine();
            String line2 = bufferFile2.readLine();

            if(line1 == null || line2 == null) break; //if any of files is ended then finish parsing

            lineNumber++;

            //checking equality of lines
            if (!MyMatcher.areLinesEqual(line1, line2)) {
                fileEqualFlag = false;

                //if lines are NOT equal, then find not equal elements
                notEqualLines.put(lineNumber, MyMatcher.getResultMap(
                        MyMatcher.getAllItems(line1), MyMatcher.getAllItems(line2)));
            }
        }

        if(fileEqualFlag) {
            System.out.println("Compared files are completely equals");
        }
        else {
            System.out.println("Compared files has unequal elements in next lines:");
            for (Map.Entry<Integer, LinkedHashMap<String,String>> entry: notEqualLines.entrySet()) {
                System.out.println("Line number {" + entry.getKey() + "}: ");

                LinkedHashMap<String, String> nodesAndValues = entry.getValue();
                for (Map.Entry<String, String> innerEntry : nodesAndValues.entrySet()) {
                    System.out.println("{" + innerEntry.getKey() + "} = {" + innerEntry.getValue() + "}");
                }
                System.out.println("");
            }
        }
    }
}
