package io.spreadsheet.calculator.caching

import io.spreadsheet.calculator.model.DataItem
import io.spreadsheet.calculator.model.SpreadCell
import org.mockito.Mock

import static org.mockito.Mockito.mock

class DataCacheTest extends GroovyTestCase {

    @Mock
    DataCache dataCache;

    @Override
    void setUp() {
        dataCache = mock(DataCache)
        super.setUp()
    }

    void testAdd() {
        dataCache.add(DataItem.of(0,0,""))
    }

    void testFindById() {
        DataItem item = dataCache.findById("A0")
        assertEquals(null, item)
    }

    void testFindByItem() {
        SpreadCell item = SpreadCell.of("A0")
        SpreadCell item2 = dataCache.findByItem(item);
    }
}
