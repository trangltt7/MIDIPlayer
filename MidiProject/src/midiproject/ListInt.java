/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midiproject;

import java.util.Arrays;

/**
 *
 * @author admin
 */
/** @class ListInt
 * An ArrayList of int types.
 */

public class ListInt {
    private int[] data;    /** The list of ints */
    private int count;     /** The size of the list */

    public ListInt() {
        data = new int[11];
        count = 0;
    }

    public ListInt(int capacity) {
        data = new int[capacity];
        count = 0;
    }

    public int size() {
        return count;
    }
    public void add(int x) {
        if (data.length == count) {
            int[] newdata = new int[count * 2];
            for (int i = 0; i < count; i++) {
                newdata[i] = data[i];
            }
            data = newdata;
        }
        data[count] = x;
        count++;
    }

    public int get(int index) {
        return data[index];
    }

    public void set(int index, int x) {
        data[index] = x;
    }

    public boolean contains(int x) {
        for (int i = 0; i < count; i++) {
            if (data[i] == x) {
                return true;
            }
        }
        return false;
    }


    public void sort() {
        Arrays.sort(data, 0, count);
    }
}