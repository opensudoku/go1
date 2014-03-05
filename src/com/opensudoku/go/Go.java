/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.go;

/**
 *
 * @author mark
 */
public final class Go implements Coordinate {

    private int[] go;

    public int getGo(int id) {
        return go[id];
    }

    public int getGo(int row, int col) {
        return go[row * 19 + col];
    }

    public int[] getGo() {
        return go;
    }

    public void setGo(int[] go) {
        this.go = go;
    }

    public void setGo(int id, int val) {
        go[id] = val;
    }

    public void setGo(int row, int col, int val) {
        go[row * 19 + col] = val;
    }

    public Go() {
        init();
    }

    public void init() {
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
                
                
                sb.append(" .");

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

}
