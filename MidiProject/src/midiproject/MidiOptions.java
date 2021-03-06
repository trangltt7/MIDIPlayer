/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midiproject;

import java.io.Serializable;

/**
 *
 * @author admin
 */
/** @class MidiOptions
 * The MidiOptions class contains the available options for
 * modifying the sheet music and sound.  These options are collected
 * from the SettingsActivity, and are passed to the SheetMusic and
 * MidiPlayer classes.
 */
public class MidiOptions implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1821126252336680208L;
	// The possible values for showNoteLetters
    public static final int NoteNameNone           = 0;
    public static final int NoteNameLetter         = 1;
    public static final int NoteNameFixedDoReMi    = 2;
    public static final int NoteNameMovableDoReMi  = 3;
    public static final int NoteNameFixedNumber    = 4;
    public static final int NoteNameMovableNumber  = 5;

    public boolean showPiano;        /** Display the piano */
    public boolean[] tracks;         /** Which tracks to display (true = display) */
    public int[] instruments;        /** Which instruments to use per track */
    public boolean useDefaultInstruments; /** If true, don't change instruments */
    public boolean scrollVert;       /** Whether to scroll vertically or horizontally */
    public boolean largeNoteSize;    /** Display large or small note sizes */
    public boolean twoStaffs;        /** Combine tracks into two staffs ? */
    public int showNoteLetters;      /** Show the letters (A, A#, etc) next to the notes */
    public boolean showLyrics;       /** Show the lyrics under each note */
    public boolean showMeasures;     /** Show the measure numbers for each staff */
    public int shifttime;            /** Shift note starttimes by the given amount */
    public int transpose;            /** Shift note key up/down by given amount */
    public int key;                  /** Use the given KeySignature (NoteScale) */
    public TimeSignature time;       /** Use the given time signature (null for default) */
    public TimeSignature defaultTime;  /** The default time signature */
    public int combineInterval;      /** Combine notes within given time interval (msec) */
    public int shade1Color;   /** The color to use for shading */
    public int shade2Color;   /** The color to use for shading the left hand piano */

    public boolean[] mute;    /** Which tracks to mute (true = mute) */
    public int  tempo;        /** The tempo, in microseconds per quarter note */
    public int  pauseTime;    /** Start the midi music at the given pause time */

    public boolean playMeasuresInLoop; /** Play the selected measures in a loop */
    public int     playMeasuresInLoopStart; /** Start measure to play in loop */
    public int     playMeasuresInLoopEnd;   /** End measure to play in loop */
    public int     lastMeasure;             /** The last measure in the song */


    public MidiOptions() {
    	
    }

    /* Initialize the default settings/options for the given MidiFile */
    public MidiOptions(MidiFile midifile) {
        showPiano = true;
        int num_tracks = midifile.getTracks().size();
        tracks = new boolean[num_tracks];
        mute = new boolean[num_tracks];
        for (int i = 0; i < tracks.length; i++) {
            tracks[i] = true;
            mute[i] = false;
            if (midifile.getTracks().get(i).getInstrumentName().equals("Percussion")) {
                tracks[i] = false;
                mute[i] = true;
            }
        }
        useDefaultInstruments = true;
        instruments = new int[num_tracks];
        for (int i = 0; i < instruments.length; i++) {
            instruments[i] = midifile.getTracks().get(i).getInstrument();
        }
        scrollVert = false;
        largeNoteSize = true;
        if (tracks.length != 2) {
            twoStaffs = true;
        }
        else {
            twoStaffs = false;
        }
        showNoteLetters = NoteNameNone;
        showMeasures = false;
        showLyrics = true;
        shifttime = 0;
        transpose = 0;
        time = null;
        defaultTime = midifile.getTime();
        key = -1;
        combineInterval = 40;       

        tempo = midifile.getTime().getTempo();
        pauseTime = 0;
        lastMeasure = midifile.EndTime() / midifile.getTime().getMeasure();
        playMeasuresInLoop = false;
        playMeasuresInLoopStart = 0;
        playMeasuresInLoopEnd = lastMeasure;
    }
}
