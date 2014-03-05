/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.go;

import com.opensudoku.go.GoBadException;

/**
 *
 * @author mark
 */
public final class Go implements Coordinate, Cloneable {

    private int[] go;
    private int ko; // the location of KO, if no KO, then it's NO_KO

    public boolean isKo() {
        if (ko == NO_KO) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return 0 to 360, indicates KO's location, or NO_KO
     */
    public int getKo() {
        return ko;
    }

    public void setKo(int ko) throws GoBadException {
        if (ko != NO_KO) {
            if (ko < 0 || ko > 360) {
                throw new GoBadException("wrong KO location ");
            }

        }
        this.ko = ko;
    }

    public int getStone(int id) {
        return go[id];
    }

    public int getStone(int row, int col) {
        return go[row * 19 + col];
    }

    public int[] getGo() {
        return go;
    }

    public void setGo(int[] go) {
        this.go = go;
    }


    /**
     * Basic operation to put stone (BLACK or WHITE)
     * @param id
     * @param val
     * @throws GoBadException 
     */
    public void setStone(int id, int val) throws GoBadException {
        
        if (id<0 || id>360){
            throw new GoBadNotOnBoardException();
        }


// must set stone on emppty point
        if (go[id] != EMPTY) {
            throw new GoBadException("playing point is not empty");
        }
        
        // stone must be BLACK or WHITE
        if ((val != BLACK) && (val != WHITE)) {
            throw new  GoBadNotValidStoneException();
        }
        
        if (id==getKo()){
            throw new GoBadViolateKORuleException();
        }
        
        go[id] = val;
    }

    public void removeStone(int id) throws GoBadException {
        if (id<0 || id>360){
            throw new GoBadNotOnBoardException();
        }
        // must set stone on emppty point
        if (go[id] == EMPTY) {
            throw new GoBadException("Removing not existing stone");
        }
        
        go[id] = EMPTY;
    }

    
    public void setStone(int row, int col, int val) throws GoBadException {
        setStone(row * 19 + col, val);
        // go[row * 19 + col] = val;
    }

    public Go() throws GoBadException {
        init();
    }

    public void init() throws GoBadException {
        setKo(NO_KO);
        go = new int[361];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < 361; k++) {
            sb.append(go[k]);
        }
        return sb.toString();
    }

    public void show() {

        StringBuilder sb = new StringBuilder();
        int id = 0;
        sb.append("\n    ");

        for (int n = 0; n < 19; n++) {
            sb.append(COL_NAME[n]).append(" ");
        }
        sb.append("\n");

        for (int m = 0; m < 19; m++) {
            sb.append(" ").append(ROW_NAME[m]);
            for (int n = 0; n < 19; n++) {
                id = m * 19 + n;
                switch (go[id]) {
                    case EMPTY:
                        if (isStar(m, n)) {
                            sb.append(" +");

                        } else {
                            sb.append(" .");
                        }
                        break;
                    case BLACK:
                        sb.append(" ").append(BLACK_CHAR);
                        break;
                    case WHITE:
                        sb.append(" ").append(WHITE_CHAR);
                        break;
                }
            }
            sb.append(ROW_NAME[m]);
            sb.append("\n");

        }
        sb.append("   ");
        for (int n = 0; n < 19; n++) {
            sb.append(" ").append(COL_NAME[n]);
        }
        System.out.println(sb.toString());
    }

    private boolean isStar(int m, int n) {
        if (m == 3 || m == 9 || m == 15) {
            if (n == 3 || n == 9 || n == 15) {
                return true;
            }

        }

        return false;
    }

    public Go copy() throws GoBadException {
        Go x = new Go();
        x.go = go.clone();

        return x; //To change body of generated methods, choose Tools | Templates.
    }
}
