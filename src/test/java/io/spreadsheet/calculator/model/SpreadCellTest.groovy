package io.spreadsheet.calculator.model

class SpreadCellTest extends GroovyTestCase {
    void testOf() {
        String id = "A1"
        SpreadCell cell = SpreadCell.of(id)
        assertNotNull(cell)

        SpreadCell nullCell = SpreadCell.of("")
        assert nullCell
    }

    void testFrom() {
        DataItem dataItem = DataItem.of(Integer.valueOf(1), Integer.valueOf(1), "A1 A2 +")
        SpreadCell cell = SpreadCell.from(dataItem)
        assertNotNull(cell)
    }

    void testIsValidated() {
        SpreadCell cell =
                SpreadCell.of("A1")
        assertEquals(false, cell.isValidated())
        cell = SpreadCell.valueOf(cell, "0", true)
        assert cell
        assertEquals(true, cell.isValidated())
    }

    void testGetId() {
        String id = SpreadCell.generateId(Integer.valueOf(0), Integer.valueOf(1))
        assertEquals("A1", id)

    }

    void testGetValue() {
        SpreadCell cell = SpreadCell.valueOf(SpreadCell.of("A1"), "10.0000", true)
        assertEquals("10.0000", cell.getValue())
    }


    void testValueOf() {
        SpreadCell cell = SpreadCell.valueOf(SpreadCell.of("A1"), "11.1234", true)
        assertEquals("11.1234", cell.getValue())
    }

    void testHashcode() {
        SpreadCell cell = SpreadCell.valueOf(SpreadCell.of("A1"), "11.1234", true)
        String hashcode = cell.hashCode()
        println hashcode
    }

    void testEquals() {
        SpreadCell cell1 = SpreadCell.valueOf(SpreadCell.of("A1"), "11.1234", true)
        SpreadCell cell2 = SpreadCell.valueOf(SpreadCell.of("A1"), "11.1234", true)
        assertEquals(true, cell1.equals(cell2))
    }
}
