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
public class Go {
    private Core core;

    public Go(Core core) {
        this.core = core;
    }

    public Go() throws GoBadException {
        core=new Core();
    }
    
}
