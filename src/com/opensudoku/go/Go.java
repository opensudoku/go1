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
 * KO violation
 * when BLACK just play point p1 to capture p2, and p1 is atari 
 * which means p1 can be captured immediately.  
 * This is KO. By Go rules, WHITE is not allowed to play p2 to capture p1 immediately. 
 * WHITE can play other places or just pass for this turn.
 * Mostly we call p1 is "Hot Stone", or invincible during that move, it cannot be taken.
 * Or traditionally, we call WHITE play p2 is violating KO rule, just like suicide, is not allowed. 
 * 
 *
 * @author mark
 */
public class Go implements Coordinate {

    private Core core;
    
    
    
    private int lastMove; // for KO concern
    private int lastCapturePoint; // this will be KO
    
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
        log.append("logging..." + str + "\n"); // append instead of write
        log.flush();
    }

    public void sendGtp(String str) throws IOException {
        Writer osr
                = new BufferedWriter(new OutputStreamWriter(System.out));
        osr.write(str + "\n\n");
        osr.flush();

    }

    public boolean gtpCommand(String cmd) throws IOException, GoBadException {
        String cleanCmd=cmd.toLowerCase().trim();
        switch (cleanCmd) {
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
//                sendGtp("= 0.5 ");// genmove w
//                sendGtp("= 0.5.1 ");// change play to play
//                sendGtp("= 0.6 ");// fixing sucide 
//                sendGtp("= 0.7 ");// implements simple KO 
//                sendGtp("= 0.8 ");// implements Golden Rule, not to play to blind eye 
                sendGtp("= 0.9 ");// doing PASS
                return true;
            }
            case "list_commands": {
                StringBuilder sb = new StringBuilder();
                sb.append("= name\n");
                sb.append("protocol_version\n");
                sb.append("version\n");
                sb.append("genmove b\n");
                sb.append("genmove w\n");
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
                core.init();
                sendGtp("= \n");
                return true;
            }
            case "komi 6.5": {
                sendGtp("= \n");
                return true;
            }

            case "genmove b": {

                sendGtp(gtp_genmove_b());
                return true;
            }
            case "play b pass": {

                sendGtp(gtp_play_b_pass());
                return true;
            }
            case "play w pass": {

                sendGtp(gtp_play_w_pass());
                return true;
            }
            case "genmove w": {

                sendGtp(gtp_genmove_w());
                return true;
            }

            default:
                if (cmd.toLowerCase().startsWith("play b ")) {
                    sendGtp(gtp_play_b(cmd.substring(7)+core.getComment()));
                    return true;
                }
                if (cmd.toLowerCase().startsWith("play w ")) {
                    String temp = gtp_play_w(cmd.substring(7)+core.getComment());
                    sendGtp(temp);
                    return true;
                }
                

                return false;
        }
    }




    //public String gtp_play_w_d16()
    public String gtp_play_b(String d16) throws GoBadException, IOException {
        if (core.isLegal(BLACK, d16)) {
            core.play(d16, BLACK);
            return ("= ");
        }
        return ("? unknown command or illegal move, by gtp_play_b," + d16);

    }
    public String gtp_play_w(String colrow) throws GoBadException, IOException {
        if (core.isLegal(WHITE, colrow)) {
            core.play(colrow, WHITE);
            return ("= ");
        }
        return ("? unknown command or illegal move, by gtp_play_w," + colrow);

    }
    
    

    public int getFirstBlackLegalMove() throws GoBadException {
        for (int k = 0; k < 361; k++) {
            
            // if it's eye, skip
            if (core.isEye(BLACK, k)) {
                continue;
            }
            if (core.isLegal(BLACK, k)) {
                return k;
            }
         
        }
        return -1;
    }

     public int getFirstWhiteLegalMove() throws GoBadException {
        for (int k = 0; k < 361; k++) {
            // if it's eye, skip
            if (core.isEye(WHITE, k)) {
                continue;
            }
            if (core.isLegal(WHITE, k)) {
                return k;
            }
            
            
        }
        return -1;
    }

    public String gtp_genmove_b() throws GoBadException {
        //TODO...\
        int move = getFirstBlackLegalMove();
        if (move >= 0) {
            core.play(move, BLACK);
            return ("= " + T19[move]);
        }
        return ("? unknown command, q16");
    }

    public String gtp_play_b_pass() throws GoBadException {
   //TODO 
        return ("=");
    }
    public String gtp_play_w_pass() throws GoBadException {
   //TODO 
    
        return ("=");
    }
    
    public String gtp_genmove_w() throws GoBadException {
        //TODO...\
        int move = getFirstWhiteLegalMove();
        if (move >= 0) {
            core.play(move, WHITE);
            return ("= " + T19[move]);
        }
        return ("? unknown command, by gtp_genmove_w");
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
