/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midiproject;

/**
 *
 * @author admin
 */
public class MidiException extends RuntimeException{
    public MidiException(String s, int offset){
        super(s + " at offset " + Integer.toString(offset));
    }
}
