/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.go;

import com.opensudoku.go.exception.GoBadException;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author mark
 */
public class Go implements Coordinate {

    private Core core;

    private int playingColor = BLACK;
    Writer log;

//    public Go() throws FileNotFoundException {
//        log = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log.txt")));
//
//    }
    public void changeTurn() {
        if (playingColor == BLACK) {
            playingColor = WHITE;
        } else {
            playingColor = BLACK;

        }

    }

    public void logGtp(String str) throws IOException {
//        System.out.println(str);
        log.append("logging..." + str + "\n\n"); // append instead of write
        log.flush();
    }

    public void sendGtp(String str) throws IOException {
        Writer osr
                = new BufferedWriter(new OutputStreamWriter(System.out));
        osr.write(str + "\n\n");
        osr.flush();

    }

    public boolean gtpCommand(String cmd) throws IOException {

        switch (cmd.trim()) {
            case "name": {
                sendGtp("= OpenSudokuGo ");
                logGtp(" OpenSudoku");
                return true;
            }
            case "protocol_version": {
                sendGtp("= 2 ");
                return true;
            }
            case "version": {
                sendGtp("= 1.0 ");
                return true;
            }
            case "list_commands": {
                StringBuilder sb = new StringBuilder();
                sb.append("= name\n");
                sb.append("protocol_version\n");
                sb.append("version\n");
                sb.append("boardsize\n");
                sb.append("clear_board\n");
                sendGtp(sb.toString());
                return true;
            }
            case "boardsize 19": {
                sendGtp("= \n");
                return true;
            }
            case "clear_board": {
                sendGtp("= \n");
                return true;
            }
            case "komi 6.5": {
                sendGtp("= \n");
                return true;
            }
            case "genmove b": {
                sendGtp("= q16");
                return true;
            }

            default:
                return false;
        }
    }

    public void test1() throws GoBadException {
        core = new Core();
        core.setStone(0, 1, BLACK);
        core.setStone(1, 0, BLACK);
        core.setStone(0, 2, WHITE);
        core.setStone(1, 1, WHITE);
        core.setStone(2, 0, WHITE);
        core.setStone(0, 0, WHITE); //capture
        core.show();
////        go1.setStone(0, 0, BLACK); // suicide
    }

    public void test2() throws GoBadException {
        core = new Core();
        core.setStone(0, 1, BLACK);
        core.setStone(1, 0, BLACK);
        core.setStone(0, 2, WHITE);
        core.setStone(1, 1, WHITE);
        core.setStone(2, 0, WHITE);
//        core.setStone(0, 0, WHITE); //capture
        core.show();
        core.setStone(0, 0, BLACK); // suicide

    }

    public Core getCore() {
        return core;
    }

    public void setCore(Core core) {
        this.core = core;
    }

    public Go(Core core) throws FileNotFoundException {
        this.core = core;
        log = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log.txt")));
//
    }

    public Go() throws GoBadException, FileNotFoundException {
        core = new Core();
        log = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log.txt")));
//
    }

}
