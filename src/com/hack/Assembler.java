package com.hack;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Assembler {
    public static void main(String[] args) {
        Symbol symbolTable = new Symbol();
        Code codeTable = new Code();
        List<String> file = new ArrayList<>();
        List<String> output = new ArrayList<>();
        try {
            Scanner s = new Scanner(new File(args[0]));
            while (s.hasNextLine()) {
                file.add(s.nextLine());
            }
        }
        catch (Exception ignored) {
        }
        Parser parser = new Parser(file);
        // first pass: turn (xxx) labels into instruction memory positions
        int lines = 1;
        while (parser.hasNextInstruction()) {
            String instruction = parser.getNextInstruction();
            if (parser.getInstructionType().equals(Instruction.L)) {
                symbolTable.addEntry(instruction.substring(1, instruction.length() -2), lines);
            } else {
                lines ++;
            }
        }
        parser.reset();
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
        parser.reset();
        // third pass: mapping the list of assembly code to machine code
        while (parser.hasNextInstruction()) {
            String instruction = parser.getNextInstruction();
            switch (parser.getInstructionType()) {
                case A:
                    if (symbolTable.contains(instruction.substring(1))) {
                        StringBuilder binaryString = new StringBuilder(Integer.toBinaryString(symbolTable.getAddress(instruction.substring(1))));
                        while (binaryString.length() < 16) {
                            binaryString.insert(0, "0");
                        }
                        output.add(binaryString.toString());
                    } else {
                        StringBuilder binaryString = new StringBuilder(Integer.toBinaryString(Integer.parseInt(instruction.substring(1))));
                        while (binaryString.length() < 16) {
                            binaryString.insert(0, "0");
                        }
                        output.add(binaryString.toString());
                    }
                    break;
                case C:
                    String code = "111" + codeTable.comp(parser.getComp()) + codeTable.dest(parser.getDest()) + codeTable.jump(parser.getJump());
                    output.add(code);
                    break;
            }
        }
        parser.reset();
        // write to file
        Path out = Paths.get("machine_code_output");
        try {
            Files.write(out, output, Charset.defaultCharset());
        }
        catch (Exception ignored) {
        }
    }
}
