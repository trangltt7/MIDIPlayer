/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midiproject;

import java.util.Comparator;

/**
 *
 * @author admin
 */
/** @class MidiNote
 * A MidiNote contains
 *
 * startTime - The time (measured in pulses) when the note is pressed.
 * channel   - The channel the note is from.  This is used when matching
 *             NoteOff events with the corresponding NoteOn event.
 *             The channels for the NoteOn and NoteOff events must be
 *             the same.
 * noteNumber - The note number, from 0 to 127.  Middle C is 60.
 * duration  - The time duration (measured in pulses) after which the 
 *             note is released.
 *
 * A MidiNote is created when we encounter a NoteOff event.  The duration
 * is Init unknown (set to 0).  When the corresponding NoteOff event
 * is found, the duration is set by the method NoteOff().
 */
public class MidiNote implements Comparator<MidiNote> {
    private int starttime;   /** The start time, in pulses */
    private int channel;     /** The channel */
    private int notenumber;  /** The note, from 0 to 127. Middle C is 60 */
    private int duration;    /** The duration, in pulses */


    /* Create a new MidiNote.  This is called when a NoteOn event is
     * encountered in the MidiFile.
     */
    public MidiNote(int starttime, int channel, int notenumber, int duration) {
        this.starttime = starttime;
        this.channel = channel;
        this.notenumber = notenumber;
        this.duration = duration;
    }


    public int getStartTime() { return starttime; }
    public void setStartTime(int value) { starttime = value; }

    public int getEndTime() { return starttime + duration; }

    public int getChannel() { return channel; }
    public void setChannel(int value) { channel = value; }

    public int getNumber() { return notenumber; }
    public void setNumber(int value) { notenumber = value; }

    public int getDuration() { return duration; }
    public void setDuration(int value) { duration = value; }

    /* A NoteOff event occurs for this note at the given time.
     * Calculate the note duration based on the noteoff event.
     */
    public void NoteOff(int endtime) {
        duration = endtime - starttime;
    }

    /** Compare two MidiNotes based on their start times.
     *  If the start times are equal, compare by their numbers.
     */
    public int compare(MidiNote x, MidiNote y) {
        if (x.getStartTime() == y.getStartTime())
            return x.getNumber() - y.getNumber();
        else
            return x.getStartTime() - y.getStartTime();
    }


    public MidiNote Clone() {
        return new MidiNote(starttime, channel, notenumber, duration);
    }

    @Override
    public 
    String toString() {
        String[] scale = new String[]{ "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
        return String.format("MidiNote channel=%1$s number=%2$s %3$s start=%4$s duration=%5$s",
                             channel, notenumber, scale[(notenumber + 3) % 12], starttime, duration);

    }

}
