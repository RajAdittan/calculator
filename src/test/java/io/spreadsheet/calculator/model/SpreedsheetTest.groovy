package io.spreadsheet.calculator.model

import io.spreadsheet.calculator.data.TestDataProvider
import io.spreadsheet.calculator.input.DataReader
import io.spreadsheet.calculator.output.DataStreamWriter
import io.spreadsheet.calculator.output.DataWriter

class SpreadsheetTest extends GroovyTestCase {

    DataReader dataReader;
    DataWriter dataWriter;

    @Override
    void setUp() {
        dataReader = TestDataProvider.createknownDataReader()
        dataWriter = DataStreamWriter.of(System.out)
        super.setUp()
    }

    @Override
    void tearDown() {
        dataReader = null
        super.tearDown()
    }

    void testFrom() {
        SpreadSheet spreadSheet = SpreadSheet.from(dataReader)
        assertNotNull(spreadSheet)
    }

    void testTo() {
        SpreadSheet spreadSheet = SpreadSheet.from(dataReader)
        assertNotNull(spreadSheet)
        //perform spreadsheet calculation
        spreadSheet.calculateSpread()
        //write to console
        SpreadSheet.to(spreadSheet, dataWriter)
    }

    void testIsSpreedsheetCell() {
        SpreadSheet spreadSheet = SpreadSheet.from(dataReader)
        Boolean result = spreadSheet.isSpreedSheetCell("A1")
        assertEquals(true, result)
    }

    void testGetValue() {
        SpreadSheet spreadSheet = SpreadSheet.from(dataReader)
        assertEquals("A2", spreadSheet.getValue("A1"))
    }

    void testSetExpr() {
        SpreadSheet spreadSheet = SpreadSheet.from(dataReader)
        SpreadCell spreadCell = SpreadSheet.exprOf(spreadSheet, "A1", "A2")
        println spreadCell
    }

    void testSetValue() {
        SpreadSheet spreadSheet = SpreadSheet.from(dataReader)
        SpreadCell spreadCell = SpreadSheet.exprOf(spreadSheet, "A1", "123")
        spreadSheet.calculateSpread(spreadCell)
        assertEquals( "123", spreadSheet.getValue("A1"))
    }

    void testCalculateSpread() {
        SpreadSheet spreadSheet = SpreadSheet.from(dataReader)
        spreadSheet.calculateSpread()
        println spreadSheet
    }

    void testHasError() {
        SpreadSheet spreadSheet = SpreadSheet.from(dataReader)
        Boolean hasError = spreadSheet.hasErrors("A1")
        assertEquals(false, hasError)
        hasError = spreadSheet.hasErrors("Z1")
        assertEquals(true, hasError)
    }
}
