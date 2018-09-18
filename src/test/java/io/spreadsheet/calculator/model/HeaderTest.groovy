package io.spreadsheet.calculator.model

class HeaderTest extends GroovyTestCase {
    void testOf() {
        Header header = Header.of(Integer.valueOf(1), Integer.valueOf(1))
        assertNotNull(header)
    }

    void testGetColumns() {
        Header header = Header.of(Integer.valueOf(1), Integer.valueOf(1))
        assertEquals(1, header.getColumns())
    }

    void testGetRows() {
        Header header = Header.of(Integer.valueOf(1), Integer.valueOf(1))
        assertEquals(1, header.getRows())
    }

    void testToString() {
        Header header = Header.of(Integer.valueOf(1), Integer.valueOf(1))
        assertNotNull(header.toString())
    }
}
