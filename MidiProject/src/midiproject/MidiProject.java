/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midiproject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

/**
 *
 * @author admin
 */
public class MidiProject {
    MidiFile midifile;          /** The midi file to play */
    static String tempSoundFile = "demo.mid"; /** The filename to play sound from */
    MidiOptions options;        /** The sound options for playing the midi file */
    double pulsesPerMsec;       /** The number of pulses per millisec */
    long startTime;             /** Absolute time when music started playing (msec) */
    double startPulseTime;      /** Time (in pulses) when music started playing */
    double currentPulseTime;    /** Time (in pulses) music is currently at */
    double prevPulseTime;       /** Time (in pulses) music was last at */
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String fileName = tempSoundFile;
        byte[] data=null;
        try{
            File info = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(fileName);
            
            data = new byte[(int)info.length()];
            int offset =0;
            int length = (int)info.length();
            
            while (true){
                if (offset==length){
                    break;
                }
                int n = fileInputStream.read(data, offset, length-offset);
                if (n<=0){
                    break;
                }
                offset+=n;
            }
            fileInputStream.close();
        }
        catch (IOException e){
            System.out.println(e);
        }
        
        MidiFile midiFile = new MidiFile(data,"");
        System.out.println(midiFile.toString());

        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            InputStream is = new BufferedInputStream(new FileInputStream(new File(tempSoundFile)));
            sequencer.setSequence(is);
            sequencer.start();
        }
        catch (Exception e) {
            System.out.println("Can't create");
        } 
    }   
    
}
