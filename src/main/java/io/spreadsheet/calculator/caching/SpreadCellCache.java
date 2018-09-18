package io.spreadsheet.calculator.caching;

import io.spreadsheet.calculator.model.DataItem;
import io.spreadsheet.calculator.model.Header;
import io.spreadsheet.calculator.model.SpreadCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class SpreadCellCache implements DataCache {

    private final Logger logger = LoggerFactory.getLogger(SpreadCellCache.class);

    private SpreadCell [][] dataItems;
    private Header header;

    private SpreadCellCache(Header header) {
        this.header = header;
        //adjust to add +1 row and +1 col
        this.dataItems = new SpreadCell[1+rowsCount()][1+columnsCount()];

    }

    public static DataCache of(Header header) {
        checkNotNull(header);
        return new SpreadCellCache(header);
    }

    @Override
    public void add(DataItem item) {
        checkNotNull(item, "specified item is null");
        checkArgument(item.getRow() <=header.getRows(), "specified item row is out of bounds");
        checkArgument(item.getCol() <=header.getColumns(), "specified item col is out of bounds");
        if(logger.isInfoEnabled()) {
            logger.info(item.toString());
        }
        dataItems[item.getRow()][item.getCol()] = SpreadCell.from(item);
    }

    @Override
    public SpreadCell findById(String id) {
        return dataItems[SpreadCell.row(id)][SpreadCell.column(id)];
    }

    @Override
    public SpreadCell find(Integer row, Integer col) {
        return dataItems[row][col];
    }

    @Override
    public SpreadCell findByItem(SpreadCell item) {
        checkNotNull(item);
        return findById(item.getExpr());
    }

    @Override
    public Boolean checkBounds(Integer row, Integer col) {
        return Boolean.valueOf(row > -1 && col > -1 && row <= header.getRows() && col <= header.getColumns());
    }

    @Override
    public Integer rowsCount() { return header.getRows(); }

    @Override
    public Integer columnsCount() { return header.getColumns(); }
}
