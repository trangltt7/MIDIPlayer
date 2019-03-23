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
/** @class MidiEvent
 * A MidiEvent represents a single event (such as EventNoteOn) in the
 * Midi file. It includes the delta time of the event.
 */
public class MidiEvent implements Comparator<MidiEvent> {

    public int    DeltaTime;     /** The time between the previous event and this on */
    public int    StartTime;     /** The absolute time this event occurs */
    public boolean HasEventflag; /** False if this is using the previous event flag */
    public byte   EventFlag;     /** NoteOn, NoteOff, etc.  Full list is in class MidiFile */
    public byte   Channel;       /** The channel this event occurs on */ 

    public byte   Notenumber;    /** The note number  */
    public byte   Velocity;      /** The volume of the note */
    public byte   Instrument;    /** The instrument */
    public byte   KeyPressure;   /** The key pressure */
    public byte   ChanPressure;  /** The channel pressure */
    public byte   ControlNum;    /** The controller number */
    public byte   ControlValue;  /** The controller value */
    public short PitchBend;      /** The pitch bend value */
    public byte   Numerator;     /** The numerator, for TimeSignature meta events */
    public byte   Denominator;   /** The denominator, for TimeSignature meta events */
    public int    Tempo;         /** The tempo, for Tempo meta events */
    public byte   Metaevent;     /** The meta event, used if event flag is MetaEvent */
    public int    Metalength;    /** The meta event length  */
    public byte[] Value;         /** The raw byte value, for Sysex and meta events */

    public MidiEvent() {
    }

    /** Return a copy of this event */
    public MidiEvent Clone() {
        MidiEvent mevent= new MidiEvent();
        mevent.DeltaTime = DeltaTime;
        mevent.StartTime = StartTime;
        mevent.HasEventflag = HasEventflag;
        mevent.EventFlag = EventFlag;
        mevent.Channel = Channel;
        mevent.Notenumber = Notenumber;
        mevent.Velocity = Velocity;
        mevent.Instrument = Instrument;
        mevent.KeyPressure = KeyPressure;
        mevent.ChanPressure = ChanPressure;
        mevent.ControlNum = ControlNum;
        mevent.ControlValue = ControlValue;
        mevent.PitchBend = PitchBend;
        mevent.Numerator = Numerator;
        mevent.Denominator = Denominator;
        mevent.Tempo = Tempo;
        mevent.Metaevent = Metaevent;
        mevent.Metalength = Metalength;
        mevent.Value = Value;
        return mevent;
    }

    /** Compare two MidiEvents based on their start times. */
    public int compare(MidiEvent x, MidiEvent y) {
        if (x.StartTime == y.StartTime) {
            if (x.EventFlag == y.EventFlag) {
                return x.Notenumber - y.Notenumber;
            }
            else {
                return x.EventFlag - y.EventFlag;
            }
        }
        else {
            return x.StartTime - y.StartTime;
        }
    }

}
