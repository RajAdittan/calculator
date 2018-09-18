package io.spreadsheet.calculator.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class DataItem {
    private Integer row;
    private Integer col;
    private String value;

    private DataItem(Integer row, Integer col, String value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public static DataItem of(Integer row, Integer col, String value) {
        checkNotNull(row);
        checkArgument(row > -1);
        checkNotNull(col);
        checkArgument(col > -1);
        checkNotNull(value);
        return new DataItem(row, col, value);
    }

    public static DataItem valueOf(DataItem item, String value) {
        checkNotNull(value);
        item.setValue(value);
        return item;
    }

    public static String idOf(DataItem item) {
        return SpreadCell.generateId(item.getRow(), item.getCol());
    }

    public Integer getRow() {
        return row;
    }

    public Integer getCol() {
        return col;
    }

    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "row=" + row +
                ", col=" + col +
                ", value='" + value + '\'' +
                '}';
    }
}
