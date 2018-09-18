package io.spreadsheet.calculator.data

import io.spreadsheet.calculator.input.DataReader
import io.spreadsheet.calculator.input.DataStreamReader
import org.apache.tools.ant.filters.StringInputStream

class TestDataProvider {
    private TestDataProvider() {}

    static DataReader createknownDataReader() {
        return DataStreamReader.from(createKnownInputStream())
    }

    static InputStream createKnownInputStream() {
        return new StringInputStream(createKnownInputData())
    }

    static DataReader createCyclicDataReader() {
        return DataStreamReader.from(createCyclicInputStream())
    }

    static InputStream createCylicInputStream() {
        return new StringInputStream(createCyclicDepsInputData())
    }

    static DataReader createNegativeDataReader() {
        return DataStreamReader.from(createNegativeInputStream())
    }

    static InputStream createNegativeInputStream() {
        return new StringInputStream(createNegativeInputData())
    }

    static DataReader createExtensionsDataReader() {
        return DataStreamReader.from(createExtensionsInputStream())
    }

    static InputStream createExtensionsInputStream() {
        return new StringInputStream(createExtensionsInputData())
    }

    static String createKnownInputData() {
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

    static String createCyclicDepsInputData() {
        StringBuilder cyclicDepsBuilder = new StringBuilder(512);
        cyclicDepsBuilder.append("2 2");
        cyclicDepsBuilder.append(System.lineSeparator());
        cyclicDepsBuilder.append("A2");
        cyclicDepsBuilder.append(System.lineSeparator());
        cyclicDepsBuilder.append("B1");
        cyclicDepsBuilder.append(System.lineSeparator());
        cyclicDepsBuilder.append("B2");
        cyclicDepsBuilder.append(System.lineSeparator());
        cyclicDepsBuilder.append("A1");
        return cyclicDepsBuilder.toString();
    }

    static String createNegativeInputData() {
        StringBuilder negativeInputBuilder = new StringBuilder(512);
        negativeInputBuilder.append("2 3");
        negativeInputBuilder.append(System.lineSeparator());
        negativeInputBuilder.append("100");
        negativeInputBuilder.append(System.lineSeparator());
        negativeInputBuilder.append("A1 -50 +");
        negativeInputBuilder.append(System.lineSeparator());
        negativeInputBuilder.append("A2 B2 *");
        negativeInputBuilder.append(System.lineSeparator());
        negativeInputBuilder.append("-1");
        negativeInputBuilder.append(System.lineSeparator());
        negativeInputBuilder.append("-1 -2 -");
        negativeInputBuilder.append(System.lineSeparator());
        negativeInputBuilder.append("A1 B1 -");
        return negativeInputBuilder.toString();
    }

    static String createExtensionsInputData() {
        StringBuilder extnInputBuilder = new StringBuilder(512);
        extnInputBuilder.append("2 3");
        extnInputBuilder.append(System.lineSeparator());
        extnInputBuilder.append("1");
        extnInputBuilder.append(System.lineSeparator());
        extnInputBuilder.append("A1 ++");
        extnInputBuilder.append(System.lineSeparator());
        extnInputBuilder.append("A2 --");
        extnInputBuilder.append(System.lineSeparator());
        extnInputBuilder.append("B1 ++");
        extnInputBuilder.append(System.lineSeparator());
        extnInputBuilder.append("A1 B2 +");
        extnInputBuilder.append(System.lineSeparator());
        extnInputBuilder.append("A2 B1 -");
        return extnInputBuilder.toString();
    }
}
