/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.go.util;

/**
 *
 * @author mark
 */
public class Helper {

    public static void main(String[] args) {
        new Helper().createCoordinate();
    }

    public static void show(String str) {
        System.out.println(str);
    }

    public void createCoordinate() {
        StringBuilder sb = new StringBuilder();
        int id=0;
        for (int m = 0; m < 19; m++) {
            sb.append("{");
            for (int n = 0; n < 19; n++) {
                id=m*19+n;
                sb.append(id);
                if (n < 18) { // last one, no comma
                    sb.append(",");

                }
            }
            sb.append("}");
            if (m < 18) { // last one, no comma
                sb.append(",\n");

            }
            
        }
        System.out.println(sb.toString());
    }
}
