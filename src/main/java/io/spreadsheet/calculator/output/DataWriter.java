package io.spreadsheet.calculator.output;

import io.spreadsheet.calculator.model.DataItem;
import io.spreadsheet.calculator.model.Header;
import io.spreadsheet.calculator.model.SpreadCell;

public interface DataWriter {
    /**
     * Writes header into underlying output stream
     * @param header Header
     */
    void writeHeader(Header header);

    /**
     * Writes input value into underlying output stream to reproduce input
     * @param item DataItem
     */
    void writeData(DataItem item);

    /**
     * Writes output value of the specified SpreadCell into underlying output stream
     * @param spreadsheetCell SpreadCell
     */
    void writeData(SpreadCell spreadsheetCell);
}
