package io.spreadsheet.calculator.input;

import io.spreadsheet.calculator.model.DataItem;
import io.spreadsheet.calculator.model.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Scanner;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.spreadsheet.calculator.constants.DefaultValues.*;

public class DataStreamReader implements DataReader {

    final Logger logger = LoggerFactory.getLogger(DataStreamReader.class);

    private Scanner dataScanner;

    private Integer currentCol;
    private Integer currentRow;
    private Header currentHeader;

    private DataStreamReader(InputStream inputStream) {
        currentCol = Integer.valueOf(0);
        currentRow = Integer.valueOf(0);
        this.dataScanner = new Scanner(inputStream);
    }

    public static DataStreamReader from(InputStream inputStream) {
        checkNotNull(inputStream, "specified inputStream is null");
        return new DataStreamReader(inputStream);
    }

    public Header readHeader() {
        checkNotNull(dataScanner, "DataScanner is null, api usage is invalid");
        dataScanner = dataScanner.reset();
        if(dataScanner.hasNext()) {
            String headerLine = dataScanner.nextLine();
            logger.info(headerLine);
            if(headerLine!=null) {
                String [] dataDim = headerLine.split(DEFAULT_SEPARATOR);
                if(2==dataDim.length) {
                    currentHeader = Header.of(Integer.valueOf(dataDim[0]), Integer.valueOf(dataDim[1]));
                    if(logger.isInfoEnabled()) {
                        logger.info(currentHeader.toString());
                    }
                    return currentHeader;
                }
            }
        }
        return DEFAULT_HEADER;
    }

    public DataItem readData() {
        checkNotNull(dataScanner, "DataScanner is null, api usage is invalid");
        String cellValue ="";
        if(dataScanner.hasNext()) {
            cellValue = dataScanner.nextLine();
            logger.info(cellValue);
        }
        if(currentCol < currentHeader.getColumns()) {
            ++currentCol;
        }
        try {
            return (cellValue.isEmpty())
                    ? DEFAULT_DATA
                    : DataItem.of(currentRow, currentCol, cellValue);
        } finally {
            if(currentCol>=currentHeader.getColumns() &&
                    currentRow < currentHeader.getRows()) {
                ++currentRow;
                currentCol = 0;
            }
        }
    }

    public boolean hasNext() {
        return Boolean.valueOf(currentRow < currentHeader.getRows()) && (currentCol < currentHeader.getColumns());
    }
}
