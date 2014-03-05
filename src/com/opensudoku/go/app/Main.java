/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.go.app;

import com.opensudoku.go.Coordinate;
import com.opensudoku.go.Go;
import com.opensudoku.go.GoBadException;

/**
 *
 * @author mark
 */
public class Main implements Coordinate{

    public static void show(String str) {
        System.out.println(str);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
            throws GoBadException, CloneNotSupportedException {
        // TODO code application logic here
        Go go1 = new Go();
        Go go2 = new Go();
        show("============= OpenSudoku's Go ==============");
        go1.setStone(3,4, 1);
        go1.setStone(3,3, 2);
        show(go1.toString());
        go1.show();
        go2=go1.copy();
        go2.setStone(5, 5, BLACK);
        go1.show();
        go2.show();
//        go2.setKo(22);
        go2.setStone(22,BLACK);
        go2.removeStone(22);
        go2.removeStone(22);
        

    }

}
