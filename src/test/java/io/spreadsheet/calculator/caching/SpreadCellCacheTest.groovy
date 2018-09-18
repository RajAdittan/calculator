package io.spreadsheet.calculator.caching

import io.spreadsheet.calculator.model.DataItem
import io.spreadsheet.calculator.model.Header
import io.spreadsheet.calculator.model.SpreadCell

class SpreadCellCacheTest extends GroovyTestCase {
    void testOf() {
        Header header = Header.of(10, 10)
        DataCache dataCache = SpreadCellCache.of(header)
        assertNotNull(dataCache)
    }

    void testAdd() {
        Header header = Header.of(1, 1)
        DataCache dataCache = SpreadCellCache.of(header)
        assertNotNull(dataCache)
        dataCache.add(DataItem.of(0,0,""))
    }

    void testFindById() {
        Header header = Header.of(1, 1)
        DataCache dataCache = SpreadCellCache.of(header)
        dataCache.add(DataItem.of(0,0,"A1"))
        SpreadCell a0 = dataCache.findById("A0")
        assertNotNull(a0)
    }

    void testFindByItem() {
        Header header = Header.of(3, 1)
        DataCache dataCache = SpreadCellCache.of(header)

        dataCache.add(DataItem.of(0,1,"A2"))
        dataCache.add(DataItem.of(0,2,"A1"))
        dataCache.add(DataItem.of(0,3,"A1"))

        SpreadCell a0 = dataCache.findById("A1")
        assertNotNull(a0)

        SpreadCell a1 = dataCache.findById("A2")
        assertNotNull(a1)

        SpreadCell a00 = dataCache.findByItem(a1)
        assertNotNull(a00)

        assert a0.getValue()==a00.getValue()
    }

    void testCheckBounds() {
        Header header = Header.of(3, 1)
        DataCache dataCache = SpreadCellCache.of(header)

        dataCache.add(DataItem.of(0,1,"A2"))
        dataCache.add(DataItem.of(0,2,"A1"))
        dataCache.add(DataItem.of(0,3,"A1"))

        Boolean valid = dataCache.checkBounds(0, 1)
        assertEquals(true, valid)

        Boolean invalid = dataCache.checkBounds(3, 4)
        assertEquals(false, invalid)

    }
}
