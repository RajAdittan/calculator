package io.spreadsheet.calculator.model;

public class Header {
    private Integer columns;
    private Integer rows;

    private Header(Integer columns, Integer rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public static Header of(Integer columns, Integer rows) {
        return new Header(columns, rows);
    }

    public Integer getColumns() {
        return columns;
    }

    public Integer getRows() {
        return rows;
    }

    @Override
    public String toString() {
        return "Header{" +
                "columns=" + columns +
                ", rows=" + rows +
                '}';
    }
}
