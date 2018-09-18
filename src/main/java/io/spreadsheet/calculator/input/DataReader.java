package io.spreadsheet.calculator.input;

import io.spreadsheet.calculator.model.DataItem;
import io.spreadsheet.calculator.model.Header;

/**
 * interface to read input data
 */
public interface DataReader {
    /**
     * read Header from the underlying stream
     * @return Header
     */
    Header readHeader();

    /**
     * read DataItem from the underlying stream
     * @return DataItem
     */
    DataItem readData();

    /**
     * check if there are more data items to read
     * @return true if more DataItem to read, false otherwise.
     */
    boolean hasNext();
}
