/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.go.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SplitDemo {
    
    private static final String REGEX = ";";
//    private static final String INPUT =        "one:two:three:four:five";
    private String INPUT;
    
    public SplitDemo() throws IOException {
        Path file = Paths.get("/home/mark");
        INPUT = new HelperJoseki().getSgfData(file);
    }
    
    public static void main(String[] args) throws IOException {
        SplitDemo sd = new SplitDemo();
        String str = sd.getJosekiData(sd.INPUT);
        System.out.println(str);
        
    }
    
    public String getJosekiData(String str) {
        Pattern p = Pattern.compile(REGEX);
        String[] items = p.split(str);
        StringBuilder sb = new StringBuilder();
        for (String s : items) {
            if ((s.startsWith("B[")) || (s.startsWith("W["))) {
//                System.out.print(s.substring(2, 4));
                sb.append(s.substring(2, 4));
            }
        }
        return sb.toString();
    }
}
