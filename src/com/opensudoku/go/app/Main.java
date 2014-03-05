/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.go.app;

import com.opensudoku.go.Coordinate;
import com.opensudoku.go.Core;
import com.opensudoku.go.Group;

import com.opensudoku.go.GoBadException;
import java.util.List;
import java.util.Set;

/**
 *
 * @author mark
 */
public class Main implements Coordinate {

    public static void show(String str) {
        System.out.println(str);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
            throws GoBadException, CloneNotSupportedException {
        // TODO code application logic here
        Core go1 = new Core();
        Core go2 = new Core();
        show("============= OpenSudoku's Go ==============");

//       for (int k=41;k<=45;k++){
//              go1.setStone(k, BLACK);
//        }
//       for (int k=46;k<=47;k++){
//              go1.setStone(k, WHITE);
//        }
        go1.setStone(0, 1, BLACK);
        go1.setStone(1, 0, BLACK);
        go1.setStone(0, 2, WHITE);
        go1.setStone(1, 1, WHITE);
        go1.setStone(2, 0, WHITE);
        go1.setStone(0,0, WHITE); //capture
//        go1.setStone(0, 0, BLACK); // suicide

        go1.show();

    }

}
