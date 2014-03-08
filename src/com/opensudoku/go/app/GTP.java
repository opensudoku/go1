/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.go.app;

import com.opensudoku.go.Go;
import com.opensudoku.go.exception.GoBadException;
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Scanner;

/**
 *
 * @author mark
 */
public class GTP {

    public static void main(String[] args) throws GoBadException, IOException {
        GTP gtp = new GTP();

//        gtp.run();
        gtp.std();

    }

//    static String readInput() {
//        StringBuffer buffer = new StringBuffer();
//        try {
////        FileInputStream fis = new FileInputStream("test.txt");
////        InputStreamReader isr = new InputStreamReader(fis, "UTF8");
//            InputStreamReader isr = new InputStreamReader(System.in);
//            Reader in = new BufferedReader(isr);
//            int ch;
//            while ((ch = in.read()) > -1) {
//                buffer.append((char) ch);
//            }
//            in.close();
//            return buffer.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public void std() throws GoBadException, IOException {
        Go go = new Go();

        InputStreamReader cin = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(cin);
      
       
//        String cmd = c.readLine("");
        String cmd = "";
//        char[] cbuf=new char[5012];
        //   Character c=new Character();
        while (true) {
            cmd = in.readLine();
//            System.out.println("= MyNameIsMark");
//            if (cmd.equals("name")) {
//                System.out.println("= OpenSudokuGo");
//                continue;
//            }

            if (!go.gtpCommand(cmd)) {
                go.sendGpt("? unknown command\n\n");
            }
        }

    }

    public void run() throws GoBadException, IOException {

        Go go = new Go();
        Scanner scan = new Scanner(System.in);
        //   scan.nextLine();

        while (true) {
            String cmd = scan.nextLine();
            if (!go.gtpCommand(cmd)) {
                Go.show("? unknown command\n");
            }
        }

    }
}
