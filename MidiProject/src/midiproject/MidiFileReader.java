/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midiproject;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author admin
 */
public class MidiFileReader {
    private byte[] data;
    private int parse_offset;

    public MidiFileReader(byte[] data) {
        this.data = data;
        this.parse_offset=0;
    }
    
    /** Check that the given number of bytes doesn't exceed the file size **/
    private void checkRead(int amount){
        if (parse_offset + amount > data.length){
            throw  new MidiException("File is truncated", parse_offset);
        }
    }
    
    /** Read the next byte in the file, but don't increment the parse offset **/
    public byte Peek(){
        checkRead(1);
        return data[parse_offset];
    }
    
    /** Read byte from file **/
    public byte ReadByte(){
        checkRead(1);
        byte n = data[parse_offset];
        parse_offset++;
        return n;
    }
    
    /** Read number of bytes from file **/
    public byte[] ReadBytes(int amount){
        checkRead(amount);
        byte[] results = new byte[amount];
        for (int i=0; i<amount; i++){
            results[i] = (byte)(data[i+parse_offset]);
        }
        parse_offset += amount;
        return results;
    }

    /** Read a 16-bit short from the file */
    public int ReadShort() {
        checkRead(2);
        int x = ((data[parse_offset] & 0xFF) << 8) | (data[parse_offset + 1] & 0xFF);
        parse_offset += 2;
        return x;
    }

    /** Read a 32-bit INT from the file */
    public int ReadInt() {
        checkRead(4);
        int x = ((data[parse_offset] & 0xFF) << 24) | ((data[parse_offset + 1] & 0xFF) << 16)
                | ((data[parse_offset + 2] & 0xFF) << 8) | (data[parse_offset + 3] & 0xFF);
        parse_offset += 4;
        return x;
    }

    /** Read an ASCII String with the given length */
    public String ReadAscii(int len) {
        checkRead(len);
        String s = "";
        try {
            s = new String(data, parse_offset, len, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            s = new String(data, parse_offset, len);
        }
        parse_offset += len;
        return s;
    }

    /**
     * Read a variable-length integer (1 to 4 bytes). The integer ends when you
     * encounter a byte that doesn't have the 8th bit set (a byte less than 0x80).
     */
    public int ReadVarlen() {
        int result = 0;
        byte b;

       b = ReadByte();
        result = (int) (b & 0x7f);

        for (int i = 0; i < 3; i++) {
            if ((b & 0x80) != 0) {
                b = ReadByte();
                result = (int) ((result << 7) + (b & 0x7f));
            } else {
                break;
            }
        }
        return (int) result;
    }

    /** Skip over the given number of bytes */
    public void Skip(int amount) {
        checkRead(amount);
        parse_offset += amount;
    }

    /** Return the current parse offset */
    public int GetOffset() {
        return parse_offset;
    }

    /** Return the raw midi file byte data */
    public byte[] GetData() {
        return data;
    }
    
    
    
}
