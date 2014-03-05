/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.go.app;

import com.opensudoku.go.Coordinate;
import com.opensudoku.go.Core;
import com.opensudoku.go.Go;
import com.opensudoku.go.Group;

import com.opensudoku.go.exception.*;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static void main(String[] args) {
        // TODO code application logic here
        show("============= OpenSudoku's Go ==============");
        try {
            new Go().test1();
        } catch (GoBadException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            new Go().test2();
        } catch (GoBadViolateSuicideRuleException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Suicide is expected!");
            
        
         } catch (GoBadException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Suicide is expected!");
            
        }
    }

}
