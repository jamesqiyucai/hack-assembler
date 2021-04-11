package com.hack;

import java.util.List;

public class Parser {
    private final List<String> _file;
    private String currentInstruction;
    private int _currentLineNumber = -1;
    public Parser(List<String> file) {
        this._file = file
                .stream()
                .filter(i -> !i.startsWith("/"))
                .filter(i -> !i.equals(""))
                .toList();
    }
    public void reset() {
        this._currentLineNumber = -1;
    }

    public boolean hasNextInstruction() {
        return this._currentLineNumber < this._file.size() - 1;
    }
    public String getNextInstruction() {
        this.currentInstruction = this._file.get(this._currentLineNumber + 1);
        this._currentLineNumber ++;
        return this.currentInstruction;
    }
    public Instruction getInstructionType() {
        if (this.currentInstruction.startsWith("@")) {
            return Instruction.A;
        } else if (this.currentInstruction.startsWith("(") && this.currentInstruction.contains(")")) {
            return Instruction.L;
        } else {
            return Instruction.C;
        }
    }
    public String getDest() {
        return this.currentInstruction.contains("=") ? "" : this.currentInstruction.substring(0, this.currentInstruction.indexOf("=") - 1);
    }
    public String getComp() {
        int startIndex, endIndex;
        if (this.currentInstruction.contains("=")) {
            startIndex = this.currentInstruction.indexOf("=") + 1;
            if (this.currentInstruction.contains(";")) {
                endIndex = this.currentInstruction.indexOf(";") -1;
                return this.currentInstruction.substring(startIndex, endIndex);
            } else {
                return this.currentInstruction.substring(startIndex);
            }
        } else {
            if (this.currentInstruction.contains(";")) {
                return this.currentInstruction.substring(0, this.currentInstruction.indexOf(";") - 1);
            } else {
                return this.currentInstruction;
            }
        }
    }
    public String getJump() {
        if (this.currentInstruction.contains(";")) {
            return this.currentInstruction.substring(this.currentInstruction.indexOf(";") + 1);
        } else {
            return "";
        }
    }
}
