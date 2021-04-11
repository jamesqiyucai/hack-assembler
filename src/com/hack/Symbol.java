package com.hack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Symbol {
    private Map<String, Integer> symbolMap;
    private int availableMemoryLocation = 16;
    public int useMemoryLocation() {
        this.availableMemoryLocation ++;
        return this.availableMemoryLocation - 1;
    };
    public Symbol() {
        this.symbolMap = new HashMap();
        this.symbolMap.put("SP", 0);
        this.symbolMap.put("LCL", 1);
        this.symbolMap.put("ARG", 2);
        this.symbolMap.put("THIS", 3);
        this.symbolMap.put("THAT", 4);
        this.symbolMap.put("SCREEN", 16384);
        this.symbolMap.put("KBD", 24576);
        this.symbolMap.put("R0", 0);
        this.symbolMap.put("R1", 1);
        this.symbolMap.put("R2", 2);
        this.symbolMap.put("R3", 3);
        this.symbolMap.put("R4", 4);
        this.symbolMap.put("R5", 5);
        this.symbolMap.put("R6", 6);
        this.symbolMap.put("R7", 7);
        this.symbolMap.put("R8", 8);
        this.symbolMap.put("R9", 9);
        this.symbolMap.put("R10", 10);
        this.symbolMap.put("R11", 11);
        this.symbolMap.put("R12", 12);
        this.symbolMap.put("R13", 13);
        this.symbolMap.put("R14", 14);
        this.symbolMap.put("R15", 15);
    }
    public void addEntry(String symbol, int address) {
        this.symbolMap.put(symbol, address);
    }
    public boolean contains(String symbol) {
        return this.symbolMap.containsKey(symbol);
    }
    public int getAddress(String symbol) {
        return this.symbolMap.get(symbol);
    }
}
