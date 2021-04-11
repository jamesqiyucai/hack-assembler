package com.hack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Assembler {
    public static void main(String[] args) {
        Symbol symbolTable = new Symbol();
        List<String> file = new ArrayList<>();
        List<String> output = new ArrayList<>();
        try {
            Scanner s = new Scanner(new File(args[0]));
            while (s.hasNextLine()) {
                file.add(s.nextLine());
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        Parser parser = new Parser(file);
        // first pass: turn (xxx) labels into instruction memory positions
        while (parser.hasNextInstruction()) {
            int lines = 0;
            String instruction = parser.getNextInstruction();
            if (parser.getInstructionType().equals(Instruction.L)) {
                symbolTable.addEntry(instruction.substring(1, instruction.length() -2), lines);
            } else {
                lines ++;
            }
        }
        // second pass: put @xxx variables into memory locations
        while (parser.hasNextInstruction()) {
            String instruction = parser.getNextInstruction();
            try {
                Integer.parseInt(instruction.substring(1));
            }
            catch (Exception e) {
                if (!symbolTable.contains(instruction.substring(1))) {
                    symbolTable.addEntry(instruction.substring(1), symbolTable.useMemoryLocation());
                }
            }
        }

    }
}
