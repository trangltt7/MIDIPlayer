/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midiproject;

import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class MidiTrack {
    private int trackNum;
    private ArrayList<MidiNote> notes;
    private int instrument;
    private ArrayList<MidiEvent> events;
    
    public MidiTrack(int trackNum){
        this.trackNum = trackNum;
        notes = new ArrayList<MidiNote>(20);
        instrument=0;
    }
    
    
    /** Create a MidiTrack based on the Midi events.  Extract the NoteOn/NoteOff
     *  events to gather the list of MidiNotes.
     */
    public MidiTrack(ArrayList<MidiEvent> events, int trackNum) {
        this.trackNum = trackNum;
        notes = new ArrayList<MidiNote>(events.size());
        instrument = 0;
 
        for (MidiEvent mevent : events) {
            if (mevent.EventFlag == MidiFile.EventNoteOn && mevent.Velocity > 0) {
                MidiNote note = new MidiNote(mevent.StartTime, mevent.Channel, mevent.Notenumber, 0);
                AddNote(note);
            }
            else if (mevent.EventFlag == MidiFile.EventNoteOn && mevent.Velocity == 0) {
                NoteOff(mevent.Channel, mevent.Notenumber, mevent.StartTime);
            }
            else if (mevent.EventFlag == MidiFile.EventNoteOff) {
                NoteOff(mevent.Channel, mevent.Notenumber, mevent.StartTime);
            }
            else if (mevent.EventFlag == MidiFile.EventProgramChange) {
                instrument = mevent.Instrument;
            }
            else if (mevent.Metaevent == MidiFile.MetaEventLyric) {
                AddLyric(mevent);
                if (events == null) {
                    events = new ArrayList<MidiEvent>();
                }
                events.add(mevent);
            }
        }
        if (notes.size() > 0 && notes.get(0).getChannel() == 9)  {
            instrument = 128;  /* Percussion */
        }
    }

    public int trackNumber() { return trackNum; }

    public ArrayList<MidiNote> getNotes() { return notes; }

    public int getInstrument() { return instrument; }
    public void setInstrument(int value) { instrument = value; }

    public ArrayList<MidiEvent> getLyrics() { return events; }
    public void setLyrics(ArrayList<MidiEvent> value) { events = value; }


    public String getInstrumentName() { 
        if (instrument >= 0 && instrument <= 128)
            return MidiFile.Instruments[instrument];
        else
            return "";
    }

    /** Add a MidiNote to this track.  This is called for each NoteOn event */
    public void AddNote(MidiNote m) {
        notes.add(m);
    }

    /** A NoteOff event occured.  Find the MidiNote of the corresponding
     * NoteOn event, and update the duration of the MidiNote.
     */
    public void NoteOff(int channel, int notenumber, int endtime) {
        for (int i = notes.size()-1; i >= 0; i--) {
            MidiNote note = notes.get(i);
            if (note.getChannel() == channel && note.getNumber() == notenumber &&
                note.getDuration() == 0) {
                note.NoteOff(endtime);
                return;
            }
        }
    }

    /** Add a lyric event to this track */
    public void AddLyric(MidiEvent mevent) { 
        if (events == null) {
            events = new ArrayList<MidiEvent>();
        }
        events.add(mevent);
    }

    /** Return a deep copy clone of this MidiTrack. */
    public MidiTrack Clone() {
        MidiTrack track = new MidiTrack(trackNumber());
        track.instrument = instrument;
        for (MidiNote note : notes) {
            track.notes.add( note.Clone() );
        }
        if (events != null) {
            track.events = new ArrayList<MidiEvent>();
            for (MidiEvent ev : events) {
                track.events.add(ev);
            }
        }
        return track;
    }

    @Override
    public String toString() {
        String result = "Track number=" + trackNum + " instrument=" + instrument + "\n";
        for (MidiNote n : notes) {
           result = result + n + "\n";
        }
        result += "End Track\n";
        return result;
    }
}
