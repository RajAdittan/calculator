package io.spreadsheet.calculator

import io.spreadsheet.calculator.data.TestDataProvider
import io.spreadsheet.calculator.input.DataReader
import io.spreadsheet.calculator.input.DataStreamReader
import io.spreadsheet.calculator.model.SpreadSheet
import io.spreadsheet.calculator.output.DataStreamWriter
import io.spreadsheet.calculator.output.DataWriter

class ApplicationTest extends GroovyTestCase {

    private DataReader dataReader
    private DataReader cyclicDataReader
    private DataReader negativeDataReader
    private DataReader extensionsDataReader
    private DataWriter dataWriter;

    @Override
    void setUp() {

        InputStream inputStream = TestDataProvider.createKnownInputStream()
        InputStream cyclicStream = TestDataProvider.createCylicInputStream()
        InputStream negativeStream = TestDataProvider.createNegativeInputStream()
        InputStream extensionsStream = TestDataProvider.createExtensionsInputStream()

        dataReader = DataStreamReader.from(inputStream);
        cyclicDataReader = DataStreamReader.from(cyclicStream)
        negativeDataReader = DataStreamReader.from(negativeStream)
        extensionsDataReader = DataStreamReader.from(extensionsStream)

        dataWriter = DataStreamWriter.of(System.out);

        super.setUp()
    }

    @Override
    void tearDown() {
        dataReader = null;
        dataWriter = null;

        super.tearDown();
    }

     /**
     * This is same as main method
     */
    void testMain() {

        println "1. --- known input output"
        println()
        long timepoint1 = System.nanoTime()
        println "reading input ..."
        //1. input
        SpreadSheet spreadSheet = SpreadSheet.from(dataReader)
        long timepoint2 = System.nanoTime()
        println "processing..."
        //2. process
        spreadSheet.calculateSpread();
        long timepoint3 = System.nanoTime()
        println "wrting output..."
        //3. output
        SpreadSheet.to(spreadSheet, dataWriter)
        long timepoint4 = System.nanoTime()

        println "reading time :=" +  (timepoint2 - timepoint1) + " nano"
        println "processing time :=" + (timepoint3 - timepoint2) + " nano"
        println "printing time :=" + (timepoint4 - timepoint3) + " nano"

        println "2. --- cyclic redundency check"
        println()
        SpreadSheet spreadSheet1 = SpreadSheet.from(cyclicDataReader)
        spreadSheet1.calculateSpread();
        SpreadSheet.to(spreadSheet1, dataWriter)

        println "3. --- negative input check"
        println()
        SpreadSheet spreadSheet2 = SpreadSheet.from(negativeDataReader)
        spreadSheet2.calculateSpread()
        SpreadSheet.to(spreadSheet2, dataWriter)

        println "4. --- extensions (++/--) input check"
        println()
        SpreadSheet spreadSheet3 = SpreadSheet.from(extensionsDataReader)
        spreadSheet3.calculateSpread()
        SpreadSheet.to(spreadSheet3, dataWriter)

    }

}
