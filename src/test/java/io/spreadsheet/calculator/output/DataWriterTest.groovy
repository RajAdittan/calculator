package io.spreadsheet.calculator.output

import io.spreadsheet.calculator.model.DataItem
import io.spreadsheet.calculator.model.Header
import io.spreadsheet.calculator.model.SpreadCell
import org.mockito.Mock

import static org.mockito.Mockito.mock

class DataWriterTest extends GroovyTestCase {

    @Mock
    DataWriter dataWriter;

    @Override
    void setUp() {
        dataWriter = mock(DataWriter);
        super.setUp()
    }

    void testWriteHeader() {
        dataWriter.writeHeader(Header.of(Integer.valueOf(0), Integer.valueOf(0)))
    }

    void testWriteData() {

        dataWriter.writeData(DataItem.of(Integer.valueOf(0), Integer.valueOf(0), "interface value"))
    }

    void testWriteData1() {
        dataWriter.writeData(SpreadCell.from(
                DataItem.of(Integer.valueOf(0), Integer.valueOf(0), "interface value")
        ))
    }
}
