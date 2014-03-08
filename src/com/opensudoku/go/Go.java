/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.go;

import com.opensudoku.go.exception.GoBadException;
import java.io.BufferedWriter;
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

    public void changeTurn() {
        if (playingColor == BLACK) {
            playingColor = WHITE;
        } else {
            playingColor = BLACK;

        }

    }

    public static void show(String str) {
        System.out.println(str);
    }

    public void sendGpt(String str) throws IOException {
        Writer osr
                = new BufferedWriter(new OutputStreamWriter(System.out));
        osr.write(str+"\n\n");
        osr.flush();

    }

    public boolean gtpCommand(String cmd) throws IOException {

        switch (cmd.trim()) {
            case "name": {
                sendGpt("= OpenSudokuGo ");
                return true;
            }
            case "protocol_version": {
                sendGpt("= 2 ");
                return true;
            }
            case "version": {
                sendGpt("= 1.0 ");
                return true;
            }
            case "list_commands": {
                StringBuilder sb=new StringBuilder();
                sb.append("= name\n");
                sb.append("protocol_version\n");
                sb.append("version\n");
                sb.append("boardsize\n");
                sb.append("clear_board\n");
                sendGpt(sb.toString());
                return true;
            }
            case "boardsize 19": {
                sendGpt("= \n");
                return true;
            }
            case "clear_board": {
                sendGpt("= \n");
                return true;
            }
            case "komi 6.5": {
                sendGpt("= \n");
                return true;
            }
            case "genmove b": {
                sendGpt("= q16");
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

    public Go(Core core) {
        this.core = core;
    }

    public Go() throws GoBadException {
        core = new Core();
    }

}
