package io.spreadsheet.calculator.caching;

import io.spreadsheet.calculator.model.DataItem;
import io.spreadsheet.calculator.model.SpreadCell;

public interface DataCache {
    Integer rowsCount();
    Integer columnsCount();

    void add(DataItem item);

    SpreadCell find(Integer row, Integer col);
    SpreadCell findById(String id);
    SpreadCell findByItem(SpreadCell item);

    Boolean checkBounds(Integer row, Integer col);
}
