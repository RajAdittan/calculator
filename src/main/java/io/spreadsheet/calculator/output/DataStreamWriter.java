package io.spreadsheet.calculator.output;

import io.spreadsheet.calculator.model.*;

import java.io.OutputStream;
import java.io.PrintWriter;

import static com.google.common.base.Preconditions.checkNotNull;


public class DataStreamWriter implements DataWriter {
    private PrintWriter writer;
    private SpreadSheet spreadSheet;

    private DataStreamWriter(OutputStream outputStream) {
        this.writer = new PrintWriter(outputStream, true);
    }

    private DataStreamWriter(OutputStream outputStream, SpreadSheet spreadSheet) {
        this.writer = new PrintWriter(outputStream, true);
        this.spreadSheet = spreadSheet;
    }

    public static DataStreamWriter of(OutputStream outputStream) {
        checkNotNull(outputStream, "specified outputStream is null");
        return new DataStreamWriter(outputStream);
    }

    public static DataStreamWriter of(OutputStream outputStream, SpreadSheet spreadsheet) {
        checkNotNull(outputStream, "specified outputStream is null");
        checkNotNull(spreadsheet, "specified spreadSheet is null");
        return new DataStreamWriter(outputStream, spreadsheet);
    }

    @Override
    public void writeHeader(Header header) {
        checkNotNull(header, "specified header is null");
        try {
            writer.printf("%s %s%s", header.getRows(), header.getColumns(), System.lineSeparator());
        } finally {
            writer.flush();
        }
    }

    /**
     * creates original input stream
     * @param item
     */
    @Override
    public void writeData(DataItem item) {
        try {
            writer.println(item.getValue());
        } finally {
            writer.flush();
        }
    }

    /**
     * Writes calculated value into stream
     * @param spreadSheetCell
     */
    @Override
    public void writeData(SpreadCell spreadSheetCell) {
        try {
            checkNotNull(spreadSheetCell, "specified spreadSheetCell is null");
            if (!spreadSheetCell.isValidated()) {
                spreadSheetCell = spreadSheet.calculateSpread(spreadSheetCell);
            }
            writer.println(spreadSheetCell.getValue());
        } finally {
            writer.flush();
        }
    }
}
