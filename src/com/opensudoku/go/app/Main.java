/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.go.app;

import com.opensudoku.go.Go;

/**
 *
 * @author mark
 */
public class Main {

    public static void show(String str) {
        System.out.println(str);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Go go = new Go();
        show("============= OpenSudoku's Go ==============");
        go.setGo(3, 1);
        show(go.toString());
        go.show();

    }

}
