package io.spreadsheet.calculator.input

import io.spreadsheet.calculator.model.DataItem
import io.spreadsheet.calculator.model.Header
import org.mockito.Mock

import static org.mockito.Mockito.mock

class DataReaderTest extends groovy.util.GroovyTestCase {

    @Mock
    DataReader dataReader

    @Override
    void setUp() {
        dataReader = mock(DataReader)
        super.setUp()
    }

    void testReadHeader() {
        Header header = dataReader.readHeader()
        assertNull(header)
    }

    void testReadData() {
        DataItem data = dataReader.readData()
        assertNull(data)
    }

    void testHasNext() {
        assertEquals(false, dataReader.hasNext());
    }
}
