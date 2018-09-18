package io.spreadsheet.calculator;

import io.spreadsheet.calculator.input.DataReader;
import io.spreadsheet.calculator.input.DataStreamReader;
import io.spreadsheet.calculator.model.SpreadSheet;
import io.spreadsheet.calculator.output.DataStreamWriter;
import io.spreadsheet.calculator.output.DataWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Calculator {

    static final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);

    public static void calculate(InputStream inputStream, OutputStream outputStream) {
        System.out.println("------------------in---------------------");
        //1. read input
        DataReader dataReader = DataStreamReader.from(inputStream);
        SpreadSheet spreadSheet = SpreadSheet.from(dataReader);

        //2. process evaluate cells
        spreadSheet.calculateSpread();

        System.out.println("------------------out---------------------");

        //3. write output
        DataWriter dataWriter = DataStreamWriter.of(outputStream);
        SpreadSheet.to(spreadSheet, dataWriter);
    }

    public static void calculate(String file, OutputStream outputStream) {
        if(!Files.exists(Paths.get(file))) {
            System.out.println("specified file [" + file + "] does not exist");
            System.exit(-1);
        }
        try {
            calculate(Files.newInputStream(Paths.get(file)), outputStream);
        } catch (IOException e) {
            if(LOGGER.isErrorEnabled()) {
                LOGGER.error("ERR:-", "IOException occurred on calculate with input file, STDOUT" + System.lineSeparator(), e);
            }
            System.out.println("io exception occurred, check log for more details");
            System.exit(-1);
        }
    }

    public static void calculate(String inputFile, String outputFile) {
        if(!Files.exists(Paths.get(inputFile))) {
            System.out.println("specified file [" + inputFile + "] does not exist");
            System.exit(-1);
        }
        try {
            calculate(Files.newInputStream(Paths.get(inputFile)), Files.newOutputStream(Paths.get(outputFile)));
        } catch (IOException e) {
            if(LOGGER.isErrorEnabled()) {
                LOGGER.error("ERR:-", "IOException occurred on calculate with input and output file" + System.lineSeparator(), e);
            }
            System.out.println("io exception occurred, check log for more details");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                calculate(System.in, System.out);
            } else if (args.length == 1) {
                if (args[0].equals("--help")) {
                    help();
                }
                calculate(args[0], System.out);
            } else if (args.length == 2) {
                calculate(args[0], args[1]);
            }
        } catch (NullPointerException npe) {
            if(LOGGER.isErrorEnabled()) {
                LOGGER.error("ERR:-", "a null pointer exception occured" + System.lineSeparator(), npe);
            }
            System.out.println("a null pointer exception occured");
            System.exit(-1);
        } catch (RuntimeException rte) {
            if(LOGGER.isErrorEnabled()) {
                LOGGER.error("ERR:-", "a runtime exception occured" + System.lineSeparator(), rte);
            }
            System.out.println("a runtime exception occured");
            System.exit(-1);
        } catch (StackOverflowError sofe) {
            if(LOGGER.isErrorEnabled()) {
                LOGGER.error("ERR:-", "a stack overflow exception occured" + System.lineSeparator(), sofe);
            }
            System.out.println("a stack overflow exception occured");
            System.exit(-1);
        } catch (Exception e) {
            if(LOGGER.isErrorEnabled()) {
                LOGGER.error("ERR:-", "an exception occured" + System.lineSeparator(), e);
            }
            System.out.println("an exception occured, more details can be found in log file");
            System.exit(-1);
        }
    }

    private static void help() {
        System.out.println("java -jar calculator.jar <input> <output>");
        System.out.println("\t input - [--help] prints this help message");
        System.out.println("\t       - [file, optional] input file, if not specified then STDIN is taken as input file");
        System.out.println("\t output- [file, optional] output file, if not specified then STDOUT is taken as output file");
        System.exit(0);
    }
}
