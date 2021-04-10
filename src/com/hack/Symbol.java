package com.hack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Symbol {
    private Map<String, Integer> symbolMap;
    private List<String> _file;
    public Symbol(List<String> file) {
        this._file = file
                .stream()
                .filter(i -> !i.equals(""))
                .filter(i -> !i.startsWith("/"))
                .toList();
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
    public void parsePseudoCommand() {
        for (int i = 0; i < this._file.size(); i++) {
            String line = this._file.get(i);
            if (line.startsWith("(")) {
                this.symbolMap.put(line.substring(line.indexOf("(") + 1, line.indexOf(")") - 1), i + 1);
            }
        }
    }
    public void parse
}
