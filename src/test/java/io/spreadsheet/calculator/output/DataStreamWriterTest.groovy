package io.spreadsheet.calculator.output

import io.spreadsheet.calculator.data.TestDataProvider
import io.spreadsheet.calculator.model.DataItem
import io.spreadsheet.calculator.model.Header
import io.spreadsheet.calculator.model.SpreadCell
import io.spreadsheet.calculator.model.SpreadSheet

class DataStreamWriterTest extends GroovyTestCase {
    void testOf() {
        DataWriter dataWriter = DataStreamWriter.of(System.out)
        dataWriter.writeHeader(Header.of(10, 10))
    }

    void testOf1() {
        SpreadSheet sp1 = SpreadSheet.from(TestDataProvider.createknownDataReader())
        DataWriter dataWriter = DataStreamWriter.of(System.out, sp1)
    }

    void testWriteHeader() {
        DataWriter dataWriter = DataStreamWriter.of(System.out)
        dataWriter.writeHeader(Header.of(1, 2))
    }

    void testWriteData() {
        DataWriter dataWriter = DataStreamWriter.of(System.out)
        dataWriter.writeHeader(Header.of(1, 1))
        dataWriter.writeData(DataItem.of(0,1, "A2"))
    }

    void testWriteData1() {
        DataWriter dataWriter = DataStreamWriter.of(System.out)
        dataWriter.writeHeader(Header.of(3, 1))

        SpreadCell sc = SpreadCell.of("A1")
        sc = SpreadCell.exprOf(sc, "A2")
        sc = SpreadCell.valueOf(sc, "10.0012", true)

        dataWriter.writeData(sc)

        SpreadCell sc1 = SpreadCell.of("A2")
        sc1 = SpreadCell.exprOf(sc1, "A2")
        sc1 = SpreadCell.valueOf(sc1, "ERROR", "TEST ERROR VALUE")

        dataWriter.writeData(sc1)
    }
}
