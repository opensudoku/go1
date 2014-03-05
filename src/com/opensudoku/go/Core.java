/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.go;

import com.opensudoku.go.GoBadException;
import static com.opensudoku.go.app.Main.show;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Standard 19x19 board with basic Go game operation 1.setStone: system can
 * automatically remove dead stones check 4 directions capturing if no any
 * capture but itself is dead, throw GoBadViolateSuicideRuleException
 * 2.removeStone MAYBE TO REVERSE, NEVER ALLOW IT??? 3.any illegal move, system
 * performs designed Exception GoBadNotOnBoardException for any given point is
 * not on the board coordinate GoBadNotValidStoneException for any given stone
 * is not BLACK or WHITE GoBadViolateNonEmptyRuleException
 * GoBadViolateSuicideRuleException GoBadViolateKORuleException
 *
 * @author mark
 */
public final class Core implements Coordinate, Cloneable {

    private int[] go;
    private int ko; // the location of KO, if no KO, then it's NO_KO
    private int tempGroupColor;
    private int[] tempGroupArray = new int[361];
    private int tempGroupCounter = 0;

    private Group group;
    private Group coat; // group's coat
    private Group liberty; // group's coat

    private HashSet<Integer> tempGroup;

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
     * Basic operation to put stone (BLACK or WHITE) on board and perform
     * capture
     *
     * @param id
     * @param val
     * @throws GoBadException
     */
    public void setStone(int id, int val) throws GoBadException {

        if (id < 0 || id > 360) {
            throw new GoBadNotOnBoardException();
        }

// must set stone on emppty point
        if (go[id] != EMPTY) {
            throw new GoBadException("playing point is not empty");
        }

        // stone must be BLACK or WHITE
        if ((val != BLACK) && (val != WHITE)) {
            throw new GoBadNotValidStoneException();
        }

        if (id == getKo()) {
            throw new GoBadViolateKORuleException();
        }

        go[id] = val;
        int captureCnt = capture(id);
    }

    /**
     * This is opponent's group. When its liberty is 0, it is being captured!
     *
     * @param id
     * @return
     */
    private int captureOpponent(int id) throws GoBadNoGroupForEmptyException, GoBadException {

        int cnt = 0;

        getGroup(id);
        getCoat(group.getList());
        getLiberty(coat.getList());
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$xxx ??? " + liberty.size());

        if (liberty.size() == 0) {
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$xxx ??? " + " removing...");

            for (int k = 0; k < group.size(); k++) {
                this.removeStone(group.get(k));
                System.out.println("xxx removing " + group.get(k));
            }
        }

        return group.size();
    }

    /**
     * Given point is to capture opponents
     *
     * @param id
     */
    private int capture(int id) throws GoBadException {
        int cnt = 0;
        int color = getStone(id);

        if (COL_NUM[id] <= 17) { // when not yet meet RIGHT BORDER
            if (go[id + 1] != color && go[id + 1] != EMPTY) {// next id
                captureOpponent(id + 1);
            }
        }
        // try LEFT
        if (COL_NUM[id] >= 1) { // when not yet meet RIGHT LEFT
            if (go[id - 1] != color && go[id - 1] != EMPTY) {// previous id
                captureOpponent(id - 1);
            }
        }
// try DOWN 
        if (ROW_NUM[id] <= 17) { // 
            if (go[id + 19] != color && go[id + 19] != EMPTY) {// 
                captureOpponent(id + 19);
            }
        }
        // try UP
        if (ROW_NUM[id] >= 1) { // 
            if (go[id - 19] != color && go[id - 19] != EMPTY) {// 
                captureOpponent(id - 19);
            }
        }

        return cnt;
    }

    /**
     * Returns given group's liberty
     *
     * @param id
     * @return
     */
    public Group getLiberty(List<Integer> list) throws GoBadException {
        int cnt = 0;

        if (list.size() == 0) {
            throw new GoBadException("EMPTY GROUP CANNOT HAVE liberty count!");
        }
        int color = getStone(list.get(0));
        liberty = new Group(color); //TODO

        System.out.print("DOING getCoat");
        for (Integer id : list) {
            // when not yet meet RIGHT BORDER
            if (go[id] == EMPTY) {// next id

                liberty.add(id);
            }
        }

        return liberty;
    }

    /**
     * Return given group's member list
     *
     * @param id
     * @return
     */
//    public Set<Integer> getGroup(int id) throws GoBadNoGroupForEmptyException {
//        if (go[id] == EMPTY) {
//            throw new GoBadNoGroupForEmptyException();
//        }
//
//        tempGroup = new HashSet();
//        tempGroup.add(id);
//        tempGroupColor = getStone(id);
//        int cnt = 0;
//        do {
//            cnt = tempGroup.size();
//            show("======= cnt:" + cnt);
//            formGroup();
//
//        } while (tempGroup.size() > cnt);
//
//        return tempGroup;
//        //  return null;
//    }
    public Group getCoat(List<Integer> list) throws GoBadException {
        if (list.size() == 0) {
            throw new GoBadException("EMPTY GROUP CANNOT HAVE COAT");
        }
        int color = getStone(list.get(0));
        coat = new Group(color); //TODO

        System.out.print("DOING getCoat");
        for (Integer id : list) {

            if (COL_NUM[id] <= 17) { // when not yet meet RIGHT BORDER
                if (go[id + 1] != color) {// next id
                    coat.add(id + 1);
                }
            }
            // try LEFT
            if (COL_NUM[id] >= 1) { // when not yet meet RIGHT LEFT
                if (go[id - 1] != color) {// previous id
                    coat.add(id - 1);
                }
            }
// try DOWN 
            if (ROW_NUM[id] <= 17) { // 
                if (go[id + 19] != color) {// 
                    coat.add(id + 19);
                }
            }
            // try UP
            if (ROW_NUM[id] >= 1) { // 
                if (go[id - 19] != color) {// 
                    coat.add(id - 19);
                }
            }

        }

        return coat;

    }

    public Group getGroup(int id) throws GoBadNoGroupForEmptyException {
        if (go[id] == EMPTY) {
            throw new GoBadNoGroupForEmptyException();
        }
        group = new Group(getStone(id));
        group.add(id);

        int cnt = 0;
        do {
            cnt = group.size();
            show("======= cnt:" + cnt);
            formGroup();

        } while (group.size() > cnt);

        return group;
        //  return null;
    }

    public static void show(String str) {
        System.out.println(str);
    }

    private void formGroup() {
        int id = 0;
        int color = group.getColor();
        for (int k = 0; k < group.size(); k++) {
            id = group.get(k);
            show("debug formGroup, member:" + id);
            //

            //
            // try RIGHT 
            if (COL_NUM[id] <= 17) { // when not yet meet RIGHT BORDER
                if (go[id + 1] == color) {// next id
                    group.add(id + 1);
                }
            }
            // try LEFT
            if (COL_NUM[id] >= 1) { // when not yet meet RIGHT LEFT
                if (go[id - 1] == color) {// previous id
                    group.add(id - 1);
                }
            }
// try DOWN 
            if (ROW_NUM[id] <= 17) { // 
                if (go[id + 19] == color) {// 
                    group.add(id + 19);
                }
            }
            // try UP
            if (ROW_NUM[id] >= 1) { // 
                if (go[id - 19] == color) {// 
                    group.add(id - 19);
                }
            }

            // try DOWN 
//            if (ROW_NUM[s] <= 16) { // when not yet meet RIGHT BORDER
//                show("debug formGroup:" + s + " ROW NUM:" + ROW_NUM[s]);
//
//                if (go[s + 19] == tempGroupColor) {// next 19 id
//                    show(" ... debug fromGroup:" + (s + 19) + " ROW NUM:" + ROW_NUM[s + 19]);
//
//                    tempGroup.add(s + 19);
//                show(" ......" +tempGroup.size());
//    
//                }
//            }
            // try UP 
//            if (ROW_NUM[s] >=1) { // when not yet meet RIGHT BORDER
//                if (go[s-19] == tempGroupColor) {// next 19 id
//                    tempGroup.add(s-19);
//                }
//            }
        }
    }

    public void removeStone(int id) throws GoBadException {
        if (id < 0 || id > 360) {
            throw new GoBadNotOnBoardException();
        }
        // must set stone on emppty point
        if (go[id] == EMPTY) {
            throw new GoBadException("Removing not existing stone");
        }

        go[id] = EMPTY;
    }

    /**
     * Sets stone to given point(row,col), call setStone in order to and
     * performs capturing in one method
     *
     * @param row
     * @param col
     * @param val
     * @throws GoBadException
     */
    public void setStone(int row, int col, int val) throws GoBadException {
        setStone(row * 19 + col, val);

        // go[row * 19 + col] = val;
    }

    public Core() throws GoBadException {
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
            sb.append(" ");
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

    public Core copy() throws GoBadException {
        Core x = new Core();
        x.go = go.clone();

        return x; //To change body of generated methods, choose Tools | Templates.
    }
}
