package com.hack;

import java.util.List;

class wrongInstructionTypeException extends Exception {}

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
    private void reset() {
        this._currentLineNumber = -1;
    }
    public int currentLineNumber() {
        return this._currentLineNumber;
    }
    public boolean hasNextInstruction() {
        if (this._currentLineNumber < this._file.size() - 1) {
            return true;
        } else {
            this.reset();
            return false;
        }
    }
    public String getNextInstruction() {
        this.currentInstruction = this._file.get(this._currentLineNumber + 1);
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
    public String getDest() throws wrongInstructionTypeException {
        if (this.getInstructionType().equals(Instruction.C)) {
            return this.currentInstruction.contains("=") ? "" : this.currentInstruction.substring(0, this.currentInstruction.indexOf("=") - 1);
        } else {
            throw new wrongInstructionTypeException();
        }
    }
    public String getComp() throws wrongInstructionTypeException {
        if (this.getInstructionType().equals(Instruction.C)) {
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
        } else {
            throw new wrongInstructionTypeException();
        }
    }
    public String getJump() throws wrongInstructionTypeException {
        if (this.getInstructionType().equals(Instruction.C)) {
            if (this.currentInstruction.contains(";")) {
                return this.currentInstruction.substring(this.currentInstruction.indexOf(";") + 1);
            } else {
                return "";
            }
        } else {
            throw new wrongInstructionTypeException();
        }
    }
}
