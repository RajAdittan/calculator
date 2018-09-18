package io.spreadsheet.calculator.input

import io.spreadsheet.calculator.input.DataReader
import io.spreadsheet.calculator.input.DataStreamReader
import io.spreadsheet.calculator.model.DataItem
import io.spreadsheet.calculator.model.Header
import org.apache.tools.ant.filters.StringInputStream

class DataStreamReaderTest extends GroovyTestCase {

    InputStream inputStream

    @Override
    void setUp() {
        inputStream = new StringInputStream(knownInput())
        super.setUp()
    }

    @Override
    void tearDown() {
        inputStream = null
        super.tearDown()
    }

    String knownInput() {
        StringBuilder knownInputBuilder = new StringBuilder(512)
        //write header 3 2 = 3x2 cells {(A1, A2, A3), (B1, B2, B3)}
        knownInputBuilder.append("3 2")        // HEADER
        knownInputBuilder.append(System.lineSeparator())
        knownInputBuilder.append("A2")         // A1
        knownInputBuilder.append(System.lineSeparator())
        knownInputBuilder.append("4 5 *")      // A2
        knownInputBuilder.append(System.lineSeparator())
        knownInputBuilder.append("A1")         // A3
        knownInputBuilder.append(System.lineSeparator())
        knownInputBuilder.append("A1 B2 / 2 +")// B1
        knownInputBuilder.append(System.lineSeparator())
        knownInputBuilder.append("3")          // B2
        knownInputBuilder.append(System.lineSeparator())
        knownInputBuilder.append("39 B1 B2 * /")// B3
        return knownInputBuilder.toString()
    }

    void testFrom() {
        DataReader dataReader = DataStreamReader.from(System.in)
        assertNotNull(dataReader)
    }

    void testReadHeader() {
        DataReader dataReader = DataStreamReader.from(inputStream)
        assertNotNull(dataReader)
        Header header = dataReader.readHeader()
        assertNotNull(header)
        assertEquals(3, header.getColumns())
        assertEquals(2, header.getRows())
    }

    void testReadData() {
        DataReader dataReader = DataStreamReader.from(inputStream)
        assertNotNull(dataReader)
        Header header = dataReader.readHeader()
        assertNotNull(header)
        println header
        while(dataReader.hasNext()) {
            DataItem dataItem = dataReader.readData()
            println dataItem
        }
    }

    void testKnownInputAndParsedInput() {
        println "Known input data:"
        println knownInput()
        println "parsed input data:"
        DataReader dataReader = DataStreamReader.from(inputStream)
        assertNotNull(dataReader)
        Header header = dataReader.readHeader()
        assertNotNull(header)
        println header
        while(dataReader.hasNext()) {
            DataItem dataItem = dataReader.readData()
            println dataItem
        }
    }
}
