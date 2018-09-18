package io.spreadsheet.calculator.model

class DataItemTest extends GroovyTestCase {
    void testOf() {
        DataItem dataItem = DataItem.of(Integer.valueOf(1), Integer.valueOf(1), "A1 A2 +")
        assertNotNull(dataItem)
    }

    void testGetRow() {
        DataItem dataItem = DataItem.of(Integer.valueOf(1), Integer.valueOf(1), "A1 A2 +")
        assertEquals(1, dataItem.getRow());
    }

    void testGetCol() {
        DataItem dataItem = DataItem.of(Integer.valueOf(1), Integer.valueOf(1), "A1 A2 +")
        assertEquals(1, dataItem.getCol())
    }

    void testGetValue() {
        DataItem dataItem = DataItem.of(Integer.valueOf(1), Integer.valueOf(1), "A1 A2 +")
        assertNotNull(dataItem.getValue())
    }

    void testToString() {
        DataItem dataItem = DataItem.of(Integer.valueOf(1), Integer.valueOf(1), "A1 A2 +")
        assertNotNull(dataItem.toString())
    }

    void testIdOf() {
        DataItem dataItem = DataItem.of(Integer.valueOf(1), Integer.valueOf(1), "A1 A2 +")
        assertNotNull(DataItem.idOf(dataItem))
    }

    void testSetValue() {
        DataItem dataItem = DataItem.of(Integer.valueOf(1), Integer.valueOf(1), "A1 A2 +")
        dataItem = DataItem.valueOf(dataItem, "B1 B2 -")
        assertNotNull(dataItem)
    }
}
